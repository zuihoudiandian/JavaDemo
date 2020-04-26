package com.example.controller;

import com.example.dto.CommentCreateDTO;
import com.example.dto.CommentDTO;
import com.example.dto.ResultDTO;
import com.example.enums.CommentTypeEnum;
import com.example.exception.CustomizeErrorCode;
import com.example.model.Comment;
import com.example.model.User;
import com.example.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by codedrinker on 2019/5/30.
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @ResponseBody
    @PostMapping("/comment")
    public ResultDTO post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())) {
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(user.getId());
//        comment.setCommentCount(commentCreateDTO.get);
        comment.setLikeCount(0L);
        commentService.insert(comment, user);
        return ResultDTO.okOf("请求成功");
    }

    @ResponseBody
    @GetMapping(value = "/comment/{id}")
    public ResultDTO comments(@PathVariable(name = "id") Integer id) {
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
      return ResultDTO.okOf(commentDTOS);
    }
}
