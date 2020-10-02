package com.kangec.wecome.infrastructure.mapper;

import com.kangec.wecome.infrastructure.pojo.Groups;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface GroupsMapper {
    Groups queryGroupsById(String groupId);
}
