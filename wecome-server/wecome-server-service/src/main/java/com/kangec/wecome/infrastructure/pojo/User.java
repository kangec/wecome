package com.kangec.wecome.infrastructure.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private long id;
    private String userId;
    private String nickName;
    private String password;
    private Date createTime;
    private Date updateTime;
}
