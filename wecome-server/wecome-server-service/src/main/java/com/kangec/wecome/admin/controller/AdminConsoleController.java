package com.kangec.wecome.admin.controller;

import com.kangec.wecome.admin.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
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

    private AdminService adminService;

    @RequestMapping("/")
    public ModelAndView doShowHome(ModelAndView mvn) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Integer onlineUsersNumber = adminService.getOnlineUsers();
        log.info("当前登录用户：{}", name);
        mvn.setViewName("index");
        mvn.addObject("current_login_user", name);
        mvn.addObject("online_users_number", onlineUsersNumber);
        return mvn;
    }

    @RequestMapping(value = "/login")
    public String doShowLogin() {
        return "login";
    }


    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }
}
