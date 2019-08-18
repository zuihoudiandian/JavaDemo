package com.example.dto;

import lombok.Data;

/**
 * Created by codedrinker on 2019/7/1.
 */
@Data
public class QuestionQueryDTO {
    private String search;
    private String tag;
    private Integer page;
    private Integer size;
}
