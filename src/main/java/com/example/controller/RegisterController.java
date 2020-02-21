package com.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.dto.ResultDTO;
import com.example.dto.VerifyCode;
import com.example.model.User;
import com.example.model.UserInfo;
import com.example.service.IVerifyCodeGen;
import com.example.service.SimpleCharVerifyCodeGenImpl;
import com.example.service.UserService;
import com.example.utils.TimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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

    @ResponseBody
    @PostMapping ("/register")
    public ResultDTO register(@RequestParam(value = "username", required = false) String username,
                              @RequestParam(value = "password", required = false) String password,
                              HttpServletResponse response )
    {

        User user = new User();
        user.setAccountId(username);
        //使用security密码加密
        String hashpw = BCrypt.hashpw(password.trim(),BCrypt.gensalt());
        user.setPassword(hashpw);
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        user.setType(Registertype);
        UserInfo userInfo = new UserInfo();
        userInfo.setAccountId(username);
        userInfo.setGmtCreate(TimeUtils.formatNow("yyyy-MM-dd HH:mm:ss"));
        response.addCookie(new Cookie("token",token));
        try {
            userService.createOrUpdate(user, userInfo);
            return  ResultDTO.okOf("注册成功！！！");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.errorOf(400,"请求失败！！");
        }
    }


    @PostMapping("/checkUsername")
    @ResponseBody
    public Object checkeusername(@RequestBody String username)
    {
        String s = StringUtils.substringAfterLast(username, "=");
        Object checkUsername = userService.checkUsername(s);
        return checkUsername;
    }
    @GetMapping("/verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse response) {
        IVerifyCodeGen iVerifyCodeGen = new SimpleCharVerifyCodeGenImpl();
        try {
            //设置长宽
            VerifyCode verifyCode = iVerifyCodeGen.generate(80, 28);
            String code = verifyCode.getCode();
            //将VerifyCode绑定session
            request.getSession().setAttribute("VerifyCode", code);
            //设置响应头
            response.setHeader("Pragma", "no-cache");
            //设置响应头
            response.setHeader("Cache-Control", "no-cache");
            //在代理服务器端防止缓冲
            response.setDateHeader("Expires", 0);
            //设置响应内容类型
            response.setContentType("image/jpeg");
            response.getOutputStream().write(verifyCode.getImgBytes());
            response.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @PostMapping(value = "/checkVerify")
    @ResponseBody
    public Object checkVerify(@RequestBody String code , HttpSession session) {
        JSONObject jo = new JSONObject();
        try{
            String inputStr =  StringUtils.substringAfterLast(code.toLowerCase(), "=");
            System.out.println(inputStr);
            String random = (String) session.getAttribute("VerifyCode");
            String paramCode=random.toLowerCase();
            System.out.println(random+"----12312132");
            if (paramCode.equals(inputStr)){
                jo.put("valid",true);
                return jo;
            }
        }catch (Exception e){
         e.printStackTrace();
        }
        jo.put("valid",false);
        return jo;
    }

}
