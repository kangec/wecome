package com.kangec.wecome.infrastructure.pojo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class Message {
    private Long id;
    private String userId;
    private String contactId;
    private Integer chatType;
    private String body;
    private Date msgTime;
}
