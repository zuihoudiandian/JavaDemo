package com.example.controller;

import com.example.dto.CommentDTO;
import com.example.dto.QuestionDto;
import com.example.enums.CommentTypeEnum;
import com.example.service.CommentService;
import com.example.service.Questionservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Author by admin, Email xx@xx.com, Date on 2019/8/10.
 * PS: Not easy to write code, please indicate.
 */
@Controller
public class QuestionController {
    //访问问题详细
    @Autowired
    private Questionservice questionservice;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    private String questionInfo(@PathVariable(name = "id")Integer id, Model model)
    {
        QuestionDto questionDto =questionservice.getInfoByid(id);

        List<QuestionDto> relatedQuestions = questionservice.selectRelated(questionDto);
        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        //累加阅读数
        questionservice.incview(id);
        model.addAttribute("question",questionDto);
        model.addAttribute("comments", comments);
        model.addAttribute("relatedQuestions",relatedQuestions);

        return "questionInfo";
    }

}
