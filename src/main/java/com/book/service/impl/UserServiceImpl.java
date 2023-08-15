package com.book.service.impl;

import com.book.dao.IUserDao;
import com.book.dao.impl.UserDaoImpl;
import com.book.entity.User;
import com.book.service.IUserService;
import com.book.util.MD5Util;

import java.util.UUID;

/**
 * UserServiceImpl
 *
 * @author hcj
 * @date 2023-08-08
 */
public class UserServiceImpl implements IUserService {
    private IUserDao userDao;

    public UserServiceImpl() {
        userDao = new UserDaoImpl();
    }

    @Override
    public User loginUser(User user) {
        // 密码解密
        user.setPassword(MD5Util.getMD5(user.getPassword()));
        return userDao.loginUser(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userDao.quryUserByUsername(username);
    }

    @Override
    public int addUser(User user) {
        // 生成UUID
        user.setUid(UUID.randomUUID().toString());
        // 密码加密
        user.setPassword(MD5Util.getMD5(user.getPassword()));
        return userDao.insertUser(user);
    }
}
