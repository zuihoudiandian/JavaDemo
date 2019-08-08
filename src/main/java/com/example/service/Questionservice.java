package com.example.service;

import com.example.dto.PaginationDTO;
import com.example.dto.QuestionDto;
import com.example.mapper.QuestionMapper;
import com.example.mapper.UserMapper;
import com.example.model.Question;
import com.example.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Author by admin, Email xx@xx.com, Date on 2019/8/6.
 * PS: Not easy to write code, please indicate.
 */
@Service
public class Questionservice {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    public PaginationDTO Allselect(Integer page, Integer size) {
    PaginationDTO PaginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        Integer totalPage;

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        Integer nowrow= size*(page-1);
        PaginationDTO.setPagination(totalPage,page);
        List<Question> allselect  = questionMapper.Allselect(nowrow,size);
           List<QuestionDto> questionDtoslist = new ArrayList<>();
        for (Question question : allselect) {
           User user = userMapper.fiandByid(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            //将question中的属性拷贝到questionDta中
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoslist.add(questionDto);
        }
        PaginationDTO.setQuestionDto(questionDtoslist);

        return PaginationDTO;

    }
    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;

        Integer totalCount =  questionMapper.count();

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage, page);

        //size*(page-1)
        Integer offset = size * (page - 1);
        List<Question> allselect  = questionMapper.myquestion(userId, offset,size);
        List<QuestionDto> questionDtoslist = new ArrayList<>();
        for (Question question : allselect) {
            User user = userMapper.fiandByid(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            //将question中的属性拷贝到questionDta中
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoslist.add(questionDto);
        }
        paginationDTO.setQuestionDto(questionDtoslist);

        return paginationDTO;
    }



}
