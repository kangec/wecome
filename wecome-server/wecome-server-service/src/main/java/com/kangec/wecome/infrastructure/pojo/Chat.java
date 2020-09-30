package com.kangec.wecome.infrastructure.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Chat {
    private Long id;
    private String userId;
    private String chatId;
    private Integer chatType;
    private Date createTime;
    private Date updateTime;
}
