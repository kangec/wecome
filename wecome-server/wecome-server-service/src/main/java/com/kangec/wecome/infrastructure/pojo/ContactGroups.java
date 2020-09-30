package com.kangec.wecome.infrastructure.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class ContactGroups {
    private Long id;
    private String userId;
    private String groupId;
    private Date createTime;
    private Date updateTime;
}
