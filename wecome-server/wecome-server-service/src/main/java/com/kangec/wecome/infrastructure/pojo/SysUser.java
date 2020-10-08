package com.kangec.wecome.infrastructure.pojo;

import lombok.Data;

/**
 * @Author Ardien
 * @Date 10/8/2020 1:16 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@Data
public class SysUser {
    private Integer id;
    private String name;
    private String password;
}
