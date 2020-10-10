package com.kangec.wecome.admin.service.impl;


import com.kangec.wecome.admin.service.AdminService;
import com.kangec.wecome.config.ChannelBeansCache;
import com.kangec.wecome.infrastructure.mapper.SysRoleMapper;
import com.kangec.wecome.infrastructure.mapper.SysUserMapper;
import com.kangec.wecome.infrastructure.mapper.SysUserRoleMapper;
import com.kangec.wecome.infrastructure.pojo.SysRole;
import com.kangec.wecome.infrastructure.pojo.SysUser;
import com.kangec.wecome.infrastructure.pojo.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Ardien
 * @Date 10/8/2020 1:22 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@Service
public class AdminServiceImpl implements AdminService {
    private SysUserMapper sysUserMapper;
    private SysRoleMapper sysRoleMapper;
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public SysUser getSysUserInformation(Integer id) {
        return sysUserMapper.queryAdminById(id);
    }

    @Override
    public SysUser getSysUserInformation(String username) {
        return sysUserMapper.queryAdminByUsername(username);
    }

    @Override
    public SysRole getSysRole(Integer id) {
        return sysRoleMapper.queryById(id);
    }

    @Override
    public List<SysUserRole> getSysUserRoleList(Integer userId) {
        return sysUserRoleMapper.listByUserId(userId);
    }

    @Override
    public Integer getOnlineUsers() {
        return ChannelBeansCache.getUserChannelSize() - 1;
    }

    @Autowired
    public void setSysRoleMapper(SysRoleMapper sysRoleMapper) {
        this.sysRoleMapper = sysRoleMapper;
    }

    @Autowired
    public void setSysUserMapper(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    @Autowired
    public void setSysUserRoleMapper(SysUserRoleMapper sysUserRoleMapper) {
        this.sysUserRoleMapper = sysUserRoleMapper;
    }
}
