package com.example.controller;

import com.example.cache.TagCache;
import com.example.exception.CustomizeErrorCode;
import com.example.exception.CustomizeException;
import com.example.mapper.QuestionMapper;
import com.example.mapper.UserMapper;
import com.example.model.Question;
import com.example.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Author by admin, Email xx@xx.com, Date on 2019/8/4.
 * PS: Not easy to write code, please indicate.
 */
@Controller

public class publishController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @GetMapping("/publish")
    public String publish(Model model)
    {

        model.addAttribute("tags", TagCache.get());
        return "/publish";
    }

    @GetMapping("/publish/{id}")
    public  String edit(@PathVariable(name = "id")Integer id,Model model){
        Question question = questionMapper.getquetionByid(id);

        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("tags", TagCache.get());
        return "/publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "id", required = false)Integer id,
            HttpServletRequest request,
            Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", TagCache.get());


        if (title == null || title == "") {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == null || description == "") {
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }
        String invalid = TagCache.filterInvalid(tag);
        if (StringUtils.isNotBlank(invalid)) {
            model.addAttribute("error", "输入非法标签:" + invalid);
            return "publish";
        }

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        Question isnotquestion = questionMapper.getquetionByid(id);
            //不存在创建新 的 问题
        if (isnotquestion==null)
            {
                Question question = new Question();
                question.setTitle(title);
                question.setDescription(description);
                question.setTag(tag);
                question.setCreator(user.getId());
                question.setId(id);
                question.setGmtCreate(new Date());
                question.setLikeCount(0);
                question.setCommentCount(0);
                question.setViewCount(0);
                questionMapper.creat(question);
            }
        //存在就修改
        else {
                Question question = new Question();
                question.setTitle(title);
                question.setDescription(description);
                question.setTag(tag);
                question.setId(id);
            int i = questionMapper.updateQuestion(question);
           if(i!=1){
               throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);

           }
        }

        return "redirect:/";
    }
}
