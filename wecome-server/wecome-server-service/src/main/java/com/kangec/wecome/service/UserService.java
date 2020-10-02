package com.kangec.wecome.service;

import com.kangec.wecome.infrastructure.pojo.User;


import java.util.List;

public interface UserService {
    /**
     * 登陆校验
     *
     * @param userId    用户ID
     * @param password  用户密码
     * @return 登陆状态
     */
    boolean checkAuth(String userId, String password);

    /**
     * 查找用户信息
     *
     * @param userId userId
     * @return {@link User}
     */
    User getUserInfo(String userId);


    /**
     * 查找 userId 下的群组列表Id
     * @param userId userId
     * @return GroupIdList
     */
    List<String> getGroupIds(String userId);
}
