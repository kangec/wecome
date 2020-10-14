package com.kangec.wecome.admin.controller;

import com.kangec.wecome.infrastructure.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
        return "register";
    }

    @RequestMapping("/doRegister")
    public ModelAndView doRegister(User user) {

        return null;
    }
}
