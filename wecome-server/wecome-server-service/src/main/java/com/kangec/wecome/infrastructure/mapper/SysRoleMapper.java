package com.kangec.wecome.infrastructure.mapper;

import com.kangec.wecome.infrastructure.pojo.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @Author Ardien
 * @Date 10/8/2020 3:35 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@Component
@Mapper
public interface SysRoleMapper {

    @Select("select * from sys_role where id = #{id}")
    SysRole queryById(Integer id);
}
