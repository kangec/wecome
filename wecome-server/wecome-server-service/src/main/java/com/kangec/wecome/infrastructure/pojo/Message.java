package com.kangec.wecome.infrastructure.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private Long id;
    private String userId;
    private String contactId;
    private Integer chatType;
    private String body;
    private Date msgTime;
}
