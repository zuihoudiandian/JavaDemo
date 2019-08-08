package com.example.controller;

import com.example.dto.PaginationDTO;
import com.example.mapper.UserMapper;
import com.example.model.User;
import com.example.service.Questionservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class HelloController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private Questionservice questionservice;

    @GetMapping("/")
    public String index(HttpServletRequest request , Model model,
                        @RequestParam(name = "page" ,defaultValue = "1")   Integer page  ,
                        @RequestParam(name = "size",defaultValue ="5" ) Integer size)  {
        Cookie[] cookies = request.getCookies();
        if (cookies!=null&&cookies.length!=0)
        {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token"))
                {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user!=null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }

        }
        PaginationDTO pagination = questionservice.Allselect(page ,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }
}

