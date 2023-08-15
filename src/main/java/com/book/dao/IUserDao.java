package com.book.dao;

import com.book.entity.User;

/**
 * IUserDao
 *
 * @author hcj
 * @date 2023-08-08
 */
public interface IUserDao {
    // 登录
    User loginUser(User user);
    // 根据用户名查询
    User quryUserByUsername(String username);
    // 新增
    int insertUser(User user);
}
