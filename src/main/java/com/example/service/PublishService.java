package com.example.service;

import com.example.mapper.QuestionMapper;
import com.example.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Author by admin, Email xx@xx.com, Date on 2020/4/7.
 * PS: Not easy to write code, please indicate.
 */
@Service
public class PublishService {

    @Autowired
    private QuestionMapper questionMapper;

    @Cacheable(value = "getQuestions",key = "#p0")
    public Question getquetionByid(Integer id) {
        return questionMapper.getquetionByid(id);
    }

    //方法调用前清空所有缓存
    @CacheEvict(value = "allQuestions",allEntries = true)
    public void creat(Question question) {
        questionMapper.creat(question);
    }

    @CachePut(value = "getQuestions",key = "#p0.id")
    public Question updateQuestion(Question question) {
       return questionMapper.updateQuestion(question);
    }
}
