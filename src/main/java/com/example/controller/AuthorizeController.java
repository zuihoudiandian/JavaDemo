package com.example.controller;

import com.example.Provider.GithubProvider;
import com.example.dto.AccessToken;
import com.example.dto.GithubUser;
import com.example.model.User;
import com.example.model.UserInfo;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


/**
 *
 */
@Controller
@Slf4j
public class AuthorizeController {
    @Autowired
    private  GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String Clientid;

    @Value("${github.client.secret}")
    private String Clientsecret;

    @Value("${github.redirect.url}")
    private  String Redirecturl;

    @Autowired
    private UserService userService;

     @GetMapping ("/callback")
    public String callback(@RequestParam(name = "code")String code ,
                           @RequestParam(name = "state")String state,
                           HttpServletRequest request , HttpServletResponse response) {
            AccessToken accessToken = new AccessToken();
            accessToken.setClient_id(Clientid);
            accessToken.setClient_secret(Clientsecret);
            accessToken.setCode(code);
            accessToken.setState(state);
            accessToken.setRedirect_url(Redirecturl);
            String BackaccessToken = githubProvider.GetAccessToken(accessToken);
            GithubUser githubUser = githubProvider.getUser(BackaccessToken);
            if (githubUser!=null&&githubUser.getId()!=null) {
                User user = new User();
                UserInfo userInfo = new UserInfo();
                String token = UUID.randomUUID().toString();
                user.setToken(token);
                user.setAccountId(String.valueOf(githubUser.getId()));
                userInfo.setAccountId(String.valueOf(githubUser.getId()));
                userInfo.setName(githubUser.getName());
                userInfo.setAvatarUrl(githubUser.getAvatar_url());
                userInfo.setBio(githubUser.getBio());
                userService.createOrUpdate(user,userInfo);
                response.addCookie(new Cookie("token",token));
                return "redirect:/";
            }  else{
                log.error("callback  get github for error,{}",githubUser);
                return "redirect:/";
            }

    }
    @GetMapping("/logout")
    public  String logout(HttpServletRequest request,HttpServletResponse response)
    {
        request.getSession().removeAttribute("user");
        request.getSession().removeAttribute("userInfo");
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        return "redirect:/";
    }


}
