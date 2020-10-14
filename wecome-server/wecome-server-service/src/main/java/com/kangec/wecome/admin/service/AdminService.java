package com.kangec.wecome.admin.service;

import com.kangec.wecome.infrastructure.pojo.SysRole;
import com.kangec.wecome.infrastructure.pojo.SysUser;
import com.kangec.wecome.infrastructure.pojo.SysUserRole;
import com.kangec.wecome.infrastructure.pojo.User;

import java.util.List;

/**
 * @Author Ardien
 * @Date 10/8/2020 1:22 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/
public interface AdminService {
    public SysUser getSysUserInformation(Integer id);
    public SysUser getSysUserInformation(String id);
    public SysRole getSysRole(Integer id);
    public List<SysUserRole> getSysUserRoleList(Integer userId);
    public Integer getOnlineUsers();
}
