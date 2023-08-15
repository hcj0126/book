package com.book.service;

import com.book.entity.User;

/**
 * IUserService
 *
 * @author hcj
 * @date 2023-08-08
 */
public interface IUserService {

    // 登录
    User loginUser(User user);
    // 根据用户名查询
    User findUserByUsername(String username);
    // 新增
    int addUser(User user);
}
