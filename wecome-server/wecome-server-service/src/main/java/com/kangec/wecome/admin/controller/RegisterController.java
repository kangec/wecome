package com.kangec.wecome.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author Ardien
 * @Date 10/8/2020 4:16 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@Controller
@Slf4j
public class RegisterController {
    @RequestMapping("/register")
    public String doShowRegister() {
        return "login";
    }

}
