package com.example.controller;

import com.example.Provider.GithubProvider;
import com.example.dto.AccessToken;
import com.example.dto.GithubUser;
import com.example.mapper.UserMapper;
import com.example.model.User;
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
    private UserMapper userMapper;

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
                String token = UUID.randomUUID().toString();
                user.setToken(token);
                user.setName(githubUser.getName());
                user.setAvatarUrl(githubUser.getAvatar_url());
                user.setAccountId(String.valueOf(githubUser.getId()));
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                user.setBio(githubUser.getBio());
                userMapper.insert(user);
                response.addCookie(new Cookie("token",token));

//                request.getSession().setAttribute("user", githubUser);
                return "redirect:/";
            }  else{
                return "redirect:/";
            }

    }

}
