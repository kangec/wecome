package com.kangec.wecome.infrastructure.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Contacts {
    private Long id;
    private String userId;
    private String contactId;
    private Date createTime;
    private Date updateTime;
}
