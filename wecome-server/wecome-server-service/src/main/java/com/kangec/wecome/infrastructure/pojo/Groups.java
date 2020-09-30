package com.kangec.wecome.infrastructure.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Groups {
    private Long id;
    private String groupId;
    private String groupName;
    private String avatar;
    private Date createTime;
    private Date updateTime;
}
