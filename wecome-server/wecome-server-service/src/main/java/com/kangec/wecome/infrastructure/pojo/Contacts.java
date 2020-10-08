package com.kangec.wecome.infrastructure.pojo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class Contacts {
    private Long id;
    private String userId;
    private String contactId;
    private Date createTime;
    private Date updateTime;
}
