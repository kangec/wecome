package com.kangec.wecome.admin.controller;

import com.alibaba.fastjson.JSON;
import com.kangec.wecome.infrastructure.pojo.Result;
import com.kangec.wecome.infrastructure.pojo.User;
import com.kangec.wecome.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Ardien
 * @Date 10/8/2020 4:16 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@Controller
@Slf4j
public class RegisterController {
    @Autowired
    public UserService userService;

    @RequestMapping("/register")
    public String doShowRegister() {
        return "register";
    }

    @PostMapping(value = "/doRegister")
    @ResponseBody
    public Map<String, Result> doRegister(@RequestBody User user) {
        log.info(JSON.toJSONString(user));
        Map<String, Result> mv = new HashMap<>();
        User result = userService.doRegister(user);
        Result res =  Result.builder().build();
        if (result != null) {
            res.setResult(true);
            res.setUserId(result.getUserId());
            mv.put("res", res);
        }
        return mv;
    }
}
