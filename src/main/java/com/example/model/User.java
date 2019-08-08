package com.example.model;

import lombok.Data;

/**
 * Author by admin, Email xx@xx.com, Date on 2019/8/4.
 * PS: Not easy to write code, please indicate.
 */
@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private  String bio;
    private  String avatarUrl;


}
