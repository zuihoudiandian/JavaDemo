package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.dto.QuestionByuser;
import com.example.dto.ResultDTO;
import com.example.dto.userDTO;
import com.example.model.Question;
import com.example.model.User;
import com.example.model.UserInfo;
import com.example.service.Questionservice;
import com.example.service.UserInfoService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Author by admin, Email xx@xx.com, Date on 2020/2/7.
 * PS: Not easy to write code, please indicate.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private Questionservice questionservice;
    @GetMapping("/page")
    public String admin(){
        return "admin";
    }

    @ResponseBody
    @GetMapping("/getuserlist")
     public ResultDTO getUserList(userDTO userDTO){
        Map<String, Object> result = new HashMap<>();
        IPage<User> usersList = userService.getUsersList(userDTO);
        result.put("ROWS", usersList.getRecords());
        result.put("TOTAL", usersList.getTotal());
       return ResultDTO.okOf(result);
    }

    @ResponseBody
    @PostMapping("/deleteuser")
    public ResultDTO deleteById(String id){
        User user = userService.getById(id);
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.eq("ACCOUNT_ID",user.getAccountId());
        userInfoService.remove(userInfoQueryWrapper);
        boolean delquestionByuser = questionservice.delquestionByuser(user);
        boolean reuser =userService.removeById(id);
        if (reuser&&delquestionByuser){
            return ResultDTO.okOf("账号及关联问题删除成功！");
        }
        return ResultDTO.okOf("账号删除成功！");
    }

    @ResponseBody
    @GetMapping("/getquestionbyluser")
    public ResultDTO getQuestionByUser(QuestionByuser questionByuser){
        Map<String, Object> result = new HashMap<>();
        IPage<Question> questionIPage =questionservice.getQuestionList(questionByuser);
        result.put("ROWS", questionIPage.getRecords());
        result.put("TOTAL", questionIPage.getTotal());
        return ResultDTO.okOf(result);
    }

    @ResponseBody
    @PostMapping("/delquestion")
    public ResultDTO delquestionById(String id){
        boolean b = questionservice.removeById(id);
        if (b){
            return ResultDTO.okOf("问题删除成功！");
        }
        return ResultDTO.errorOf(2019,"请求失败");
    }



}
