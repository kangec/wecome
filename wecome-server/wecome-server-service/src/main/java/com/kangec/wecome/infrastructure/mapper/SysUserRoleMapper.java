package com.kangec.wecome.infrastructure.mapper;

import com.kangec.wecome.infrastructure.pojo.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author Ardien
 * @Date 10/8/2020 3:37 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@Component
@Mapper
public interface SysUserRoleMapper {

    @Select("select * from sys_user_role where user_id = #{userId}")
    List<SysUserRole> listByUserId(Integer userId);
}
