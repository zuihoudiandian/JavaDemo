package com.example.controller;

import com.example.dto.PaginationDTO;
import com.example.dto.QuestionDto;
import com.example.service.Questionservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class HelloController {

    @Autowired
    private Questionservice questionservice;
    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page" ,defaultValue = "1")   Integer page  ,
                        @RequestParam(name = "size",defaultValue ="8" ) Integer size,
                        @RequestParam(name = "search", required = false) String search
                       )

    {
        PaginationDTO pagination = questionservice.Allselect(search,page ,size);
        List<QuestionDto> hotquestions = questionservice.selectquestionByView();
        model.addAttribute("search", search);
        model.addAttribute("pagination",pagination);
        model.addAttribute("hotquestions",hotquestions);
        return "index";
    }
}

