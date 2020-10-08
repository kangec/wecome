package com.kangec.wecome.admin.service;

import com.kangec.wecome.infrastructure.pojo.SysRole;
import com.kangec.wecome.infrastructure.pojo.SysUser;
import com.kangec.wecome.infrastructure.pojo.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author Ardien
 * @Date 10/8/2020 3:55 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        SysUser user = adminService.getSysUserInformation(username);
        if (user == null)
            throw new UsernameNotFoundException("用户名不存在");

        List<SysUserRole> userRoles = adminService.getSysUserRoleList(user.getId());
        userRoles.forEach(userRole -> {
            SysRole sysRole = adminService.getSysRole(userRole.getRoleId());
            authorities.add(new SimpleGrantedAuthority(sysRole.getName()));
        });
        return new User(user.getName(), user.getPassword(), authorities);
    }

    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }
}
