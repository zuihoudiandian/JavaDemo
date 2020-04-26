package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.*;
import com.example.exception.CustomizeErrorCode;
import com.example.exception.CustomizeException;
import com.example.mapper.QuestionMapper;
import com.example.mapper.UserInfoMapper;
import com.example.mapper.UserMapper;
import com.example.model.Question;
import com.example.model.User;
import com.example.model.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
public class Questionservice extends ServiceImpl<QuestionMapper, Question> {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Cacheable(value = "allQuestions",key = "#p1")
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
        if (totalCount==0){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
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
            List<Question> allselect  = questionMapper.AllselectBysearch(questionQueryDTO.getSearch(),nowrow,size);
            List<QuestionDto> questionDtoslist = new ArrayList<>();
            if (allselect.size()>0){
                for (Question question : allselect) {
                    User user = userMapper.selectById(question.getCreator());
                    if (user!=null){
                        QueryWrapper<UserInfo> Wrapper = new QueryWrapper<>();
                        Wrapper.eq(StringUtils.isNotEmpty(user.getAccountId()),"ACCOUNT_ID",user.getAccountId());
                        UserInfo userInfo = userInfoMapper.selectOne(Wrapper);
                        QuestionDto questionDto = new QuestionDto();
                        //将question中的属性拷贝到questionDta中
                        BeanUtils.copyProperties(question,questionDto);
                        questionDto.setUser(user);
                        questionDto.setUserInfo(userInfo);
                        questionDtoslist.add(questionDto);
                    }
                }
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
        List<Question> myquestion  = questionMapper.myquestion(user.getId(), offset,size);
        paginationDTO.setData(myquestion);
        return paginationDTO;
    }


    public QuestionDto getInfoByid(Integer id) {
        Question question = questionMapper.getquetionByid(id);
        if (question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question,questionDto);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("ID",question.getCreator());
        User user  =   userMapper.selectOne(wrapper);
        QueryWrapper<UserInfo> infoQueryWrapper = new QueryWrapper<>();
        infoQueryWrapper.eq("ACCOUNT_ID",user.getAccountId());
        UserInfo userInfo = userInfoMapper.selectOne(infoQueryWrapper);
        questionDto.setUserInfo(userInfo);
        questionDto.setUser(user);
        return questionDto;

    }

    //添加问题点击次数
    public void incview(Integer id) {
        questionMapper.addview(id);
    }

    @Cacheable(value = "RelatedQuestions",key = "#p0.id")
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

    public boolean delquestionByuser(User user) {
        QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
        questionQueryWrapper.eq("CREATOR",user.getId());
        return lambdaUpdate().eq(Question::getCreator, user.getId()).remove();
    }

    //根据用户查询所有问题
    public IPage<Question> getQuestionList(QuestionByuser questionByuser) {
        Page<Question> page = new Page<>(questionByuser.getPageNumber(), questionByuser.getPageSize());
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.eq("CREATOR",questionByuser.getId());
        IPage<Question> iPage = questionMapper.selectPage(page, wrapper);
        return iPage;
    }
}
