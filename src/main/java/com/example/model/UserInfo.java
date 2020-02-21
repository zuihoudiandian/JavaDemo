package com.example.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * id
    */
    @TableId(value = "id" ,type= IdType.AUTO)
    private Long id;
    private String accountId;

    /**
    * bio
    */
    private String bio;

    /**
    * avatar_url
    */
    private String avatarUrl;

    /**
    * email
    */
    private String email;

    /**
    * sex
    */
    private Integer sex;

    /**
    * name
    */
    private String name;

    /**
    * gmt_create
    */
    private Date gmtCreate;

    /**
    * gmt_modified
    */
    private Date gmtModified;


}
