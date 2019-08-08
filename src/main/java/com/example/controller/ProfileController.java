package com.example.controller;

import com.example.dto.PaginationDTO;
import com.example.model.User;
import com.example.service.Questionservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Author by admin, Email xx@xx.com, Date on 2019/8/7.
 * PS: Not easy to write code, please indicate.
 */
@Controller
public class ProfileController {
    @Autowired
    private Questionservice questionservice;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action , Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size){

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
            PaginationDTO paginationDTO = questionservice.list(user.getId(), page, size);
            model.addAttribute("pagination", paginationDTO);
        } else if ("replies".equals(action)) {
//            PaginationDTO paginationDTO = notificationService.list(user.getId(), page, size);
            model.addAttribute("section", "replies");
//           model.addAttribute("pagination", paginationDTO);
            model.addAttribute("sectionName", "最新回复");
        }
        return "profile";


    }
}
