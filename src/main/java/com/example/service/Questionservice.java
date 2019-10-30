package com.example.service;

import com.example.dto.PaginationDTO;
import com.example.dto.QuestionDto;
import com.example.dto.QuestionQueryDTO;
import com.example.exception.CustomizeErrorCode;
import com.example.exception.CustomizeException;
import com.example.mapper.QuestionMapper;
import com.example.mapper.UserMapper;
import com.example.model.Question;
import com.example.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public PaginationDTO Allselect(String search,Integer page, Integer size) {
        if (StringUtils.isNotBlank(search)) {
            String[] tags = StringUtils.split(search, " ");
            search = Arrays
                    .stream(tags)
                    .filter(StringUtils::isNotBlank)
                    .map(t -> t.replace("+", "").replace("*", ""))
                    .filter(StringUtils::isNotBlank)
                    .collect(Collectors.joining("|"));
        }
        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);

        PaginationDTO PaginationDTO = new PaginationDTO();
        Integer totalCount=questionMapper.countbysearch(search);
        // Integer totalCount = questionMapper.count();
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
        //List<Question> allselect  = questionMapper.Allselect(nowrow,size);
            List<Question> allselect  = questionMapper.AllselectBysearch(questionQueryDTO.getSearch(),nowrow,size);
           List<QuestionDto> questionDtoslist = new ArrayList<>();
        for (Question question : allselect) {
           // User user  = userMapper.listfiandByAccountID(question.getCreator());
            User user = userMapper.selectById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            //将question中的属性拷贝到questionDta中
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoslist.add(questionDto);
        }
        PaginationDTO.setData(questionDtoslist);
        return PaginationDTO;

    }
    public PaginationDTO list(User user, Integer page, Integer size) {
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
        List<Question> allselect  = questionMapper.myquestion(user.getId(), offset,size);
        List<QuestionDto> questionDtoslist = new ArrayList<>();
        for (Question question : allselect) {
            User users  = userMapper.listfiandByAccountID(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            //将question中的属性拷贝到questionDta中
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(users);
            questionDtoslist.add(questionDto);
        }
        paginationDTO.setData(questionDtoslist);

        return paginationDTO;
    }


    public QuestionDto getInfoByid(Integer id) {
        Question question = questionMapper.getquetionByid(id);
        if (question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question,questionDto);
        User user = userMapper.findUserByAccountID(question.getCreator());
        questionDto.setUser(user);
        return questionDto;

    }

    public void incview(Integer id) {
        questionMapper.addview(id);
    }

    public List<QuestionDto> selectRelated(QuestionDto questionDto) {
        if (StringUtils.isBlank(questionDto.getTag())) {

            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(questionDto.getTag(), ",");
        String regexpTag = Arrays
                .stream(tags)
                .filter(StringUtils::isNotBlank)
                .map(t -> t.replace("+", "").replace("*", ""))
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(questionDto.getId().longValue());
        question.setTag(regexpTag);

        List<Question> questions = questionMapper.selectRelated(question);
        List<QuestionDto> questionDTOS = questions.stream().map(q -> {
            QuestionDto questionDTO = new QuestionDto();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;
    }

    public List<QuestionDto>  selectquestionByView() {
        List<Question> questions = questionMapper.selectquestionByView();
        List<QuestionDto> hotquestionDto = new ArrayList<>();
        for (Question question : questions) {
            QuestionDto questionDto = new QuestionDto();
            //将question中的属性拷贝到questionDta中
            BeanUtils.copyProperties(question,questionDto);
            hotquestionDto.add(questionDto);
        }
        return hotquestionDto;
    }


}
