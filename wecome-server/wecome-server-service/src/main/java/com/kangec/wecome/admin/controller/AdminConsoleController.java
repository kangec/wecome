package com.kangec.wecome.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sun.security.util.SecurityConstants;

/**
 * @Author Ardien
 * @Date 10/5/2020 4:54 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@Controller
@Slf4j
public class AdminConsoleController {

    @RequestMapping("/")
    public String doShowHome() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("当前登录用户：{}", name);
        return "home";
    }

    @RequestMapping(value = "/login")
    public String doShowLogin() {
        return "login";
    }

}
