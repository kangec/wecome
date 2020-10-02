package com.kangec.wecome.infrastructure.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author Ardien
 * @Date 10/2/2020 12:53 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/
@Component
@Mapper
public interface ContactGroupsMapper {
    List<String> queryGroupIdList(@Param("userId") String userId);
}
