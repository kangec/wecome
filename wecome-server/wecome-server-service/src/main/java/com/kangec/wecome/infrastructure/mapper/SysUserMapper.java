package com.kangec.wecome.infrastructure.mapper;

import com.kangec.wecome.infrastructure.pojo.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @Author Ardien
 * @Date 10/8/2020 1:17 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@Component
@Mapper
public interface SysUserMapper {
    @Select("select * from sys_user where name = #{username}")
    SysUser queryAdminByUsername(String username);

    @Select("select * from sys_user where id = #{id}")
    SysUser queryAdminById(Integer id);
}
