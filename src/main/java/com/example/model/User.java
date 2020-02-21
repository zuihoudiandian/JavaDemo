package com.example.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id" ,type= IdType.AUTO)
    private Long id;

    /**
     * account_id
     */
    private String accountId;

    /**
     * token
     */
    private String token;

    /**
     * gmt_create
     */
    private Date gmtCreate;

    /**
     * gmt_modified
     */
    private Date gmtModified;

    /**
     * type
     */
    private String type;

    /**
     * password
     */
    private String password;

}
