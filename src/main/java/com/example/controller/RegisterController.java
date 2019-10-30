package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import com.example.utils.CodecUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Author by admin, Email xx@xx.com, Date on 2019/8/23.
 * PS: Not easy to write code, please indicate.
 */
@Controller
    public class RegisterController {
    @Autowired
    private UserService userService;
    static String Registertype ="1";

    @PostMapping ("/register")
    public String register(@RequestParam(value = "username", required = false) String username,
                           @RequestParam(value = "password", required = false) String password,
                           @RequestParam(value = "email", required = false) String email,
                           HttpServletResponse response )
    {

        User user = new User();
        user.setAccountId(username);
        user.setEmail(email);
        //密码加密
        String encodePassword = CodecUtils.passwordBcryptEncode(username.trim(),password.trim());
        user.setPassword(encodePassword);
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        user.setType(Registertype);
        user.setName(username);
        userService.createOrUpdate(user);
        response.addCookie(new Cookie("token",token));
        return "redirect:/";
    }
    @PostMapping("/checkUsername")
    @ResponseBody
    public Object checkeusername(@RequestBody String username)
    {
        String s = StringUtils.substringAfterLast(username, "=");
//        String[] split = username.split("=");
//        username=split[1];
        Object checkUsername = userService.checkUsername(s);
        return checkUsername;
    }
}
