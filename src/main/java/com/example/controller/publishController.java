package com.example.controller;

import com.example.mapper.QuestionMapper;
import com.example.mapper.UserMapper;
import com.example.model.Question;
import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
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
    public String publish()
    {
        return "/publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "id", required = false) Long id,
            HttpServletRequest request,
            Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);


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

        User user =null;
        Cookie[] cookies = request.getCookies();
            if (cookies!=null&&cookies.length!=0)
            {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("token"))
                    {
                        String token = cookie.getValue();
                        user = userMapper.findByToken(token);
                        if (user!=null){
                            request.getSession().setAttribute("user",user);
                        }
                        else {
                            model.addAttribute("error","用户未登录");
                            return "publish";
                        }

                    }

                }

            }

            Question question = new Question();
            question.setTitle(title);
            question.setDescription(description);
            question.setTag(tag);
            question.setCreator(user.getId());
            question.setId(id);
            question.setGmtCreate(new Date());
            questionMapper.creat(question);

        return "redirect:/";
    }
}
