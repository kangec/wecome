package com.kangec.wecome.infrastructure.pojo;

import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Pattern;
import java.util.Date;

@Builder
@Data
public class Chat {
    private Long id;
    private String userId;
    private String chatId;

    /**
     * 数据校验，0好友，1群组
     */
    @Pattern(regexp = "[0-1]")
    private int chatType;
    private Date createTime;
    private Date updateTime;
}
