package com.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.mapper.UserMapper;
import com.example.model.User;
import com.example.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Author by admin, Email xx@xx.com, Date on 2019/8/25.
 * PS: Not easy to write code, please indicate.
 */

@Controller
public class LoginController {
    @Autowired
    private UserMapper userMapper;
    @PostMapping("/login")
    @ResponseBody
    private Object login(String username, String password, Model model , HttpServletResponse response){
        System.out.println(username+password);
        User user = userMapper.checkByUsername(username);
        JSONObject jo = new JSONObject();
        if (user!=null){
            UserExample userExample = new UserExample();
            userExample.createCriteria().andPasswordEqualTo(password);
            List<User> users = userMapper.selectByExample(userExample);
            if (users.size() != 0){
                String token = user.getToken();
                response.addCookie(new Cookie("token",token));
                jo.put("success",true);
            }
            else
                jo.put("error",1001);
            return jo;
        }
        else
            jo.put("error",1000);
        return jo;
    }

}
