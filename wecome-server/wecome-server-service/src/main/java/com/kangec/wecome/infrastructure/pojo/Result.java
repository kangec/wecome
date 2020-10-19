package com.kangec.wecome.infrastructure.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * @Author kangec 10/19/2020 8:06 PM
 * @Email ardien@126.com
 **/

@Data
@Builder
public class Result {
    private String userId;
    private boolean result = false;
}
