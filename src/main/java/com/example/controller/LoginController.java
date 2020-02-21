package com.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mapper.UserInfoMapper;
import com.example.mapper.UserMapper;
import com.example.model.User;
import com.example.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author by admin, Email xx@xx.com, Date on 2019/8/25.
 * PS: Not easy to write code, please indicate.
 */

@Controller
public class LoginController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @PostMapping("/login")
    @ResponseBody
    private Object login(String username, String password , HttpServletResponse response, HttpServletRequest request){
        System.out.println(username+password);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("ACCOUNT_ID",username);
        User user =  userMapper.selectOne(wrapper);
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.eq("ACCOUNT_ID",user.getAccountId());
        UserInfo userInfo = userInfoMapper.selectOne(userInfoQueryWrapper);
        JSONObject jo = new JSONObject();
        if (user!=null){
            //3. 校验密码
            //使用security 校验密码！
            boolean checkpw = BCrypt.checkpw(password, user.getPassword());
           // boolean result = CodecUtils.passwordConfirm(username + password,user.getPassword());
            if (checkpw){
                request.getSession().setAttribute("user",user);
                request.getSession().setAttribute("userInfo",userInfo);
                jo.put("success",true);
                return jo;
            }
            else {
                jo.put("error", 1001);
            }
        }
        else {
            jo.put("error", 1000);
        }
        return jo;
    }
}
