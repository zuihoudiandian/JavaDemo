package com.example.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Author by admin, Email xx@xx.com, Date on 2019/8/3.
 * PS: Not easy to write code, please indicate.
 */
@Data
public class GithubUser implements Serializable {
    private String name;
    private Long id;
    private String  bio;
    private String avatar_url;


}
