package com.example.dto;

import com.example.model.PaginationModel;
import com.example.model.User;
import com.example.model.UserInfo;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Author by admin, Email xx@xx.com, Date on 2019/8/6.
 * PS: Not easy to write code, please indicate.
 */
@Data
public class QuestionDto  extends PaginationModel  {
    private Long id;
    private String title;

    private String description;
    private Date gmtCreate;

    private Long gmtModified;

    private Long creator;

    private Integer commentCount;

    private Integer viewCount;

    private Integer likeCount;
    private String tag;
    private List <User>   users;
    private  User user;
    private List<UserInfo> userInfos;
    private UserInfo userInfo;
}
