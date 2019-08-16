package com.example.model;

import lombok.Data;

import java.util.Date;

/**
 * Author by admin, Email xx@xx.com, Date on 2019/8/5.
 * PS: Not easy to write code, please indicate.
 */

@Data
public class Question {

        private Integer id;

        private String title;

        private String description;
        private Date gmtCreate;

        private Long gmtModified;

        private Integer creator;

        private Integer commentCount;

        private Integer viewCount;

        private Integer likeCount;
        private String tag;

}

