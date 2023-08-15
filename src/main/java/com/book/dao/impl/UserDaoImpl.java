package com.book.dao.impl;

import com.book.dao.IUserDao;
import com.book.entity.User;
import com.book.util.DruidUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * UserDaoImpl
 *
 * @author hcj
 * @date 2023-08-08
 */
public class UserDaoImpl implements IUserDao {
    private JdbcTemplate jt = new JdbcTemplate(DruidUtils.getDataSource());

    /**
     * 登录
     * 注意：如果查询的是一个对象 要捕获异常EmptyResultDataAccessException
     * 处理null对象给调用者
    */
    @Override
    public User loginUser(User user) {
        try{
            // 创建sql语句
            String sql = "select * from user where username=? and password=?";
            User u = jt.queryForObject(sql,new BeanPropertyRowMapper<>(User.class),user.getUsername(),user.getPassword());
            return u;
        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    @Override
    public User quryUserByUsername(String username) {
        try{
            // 创建sql语句
            String sql = "select * from user where username=?";
            User u = jt.queryForObject(sql,new BeanPropertyRowMapper<>(User.class),username);
            return u;
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public int insertUser(User user) {
        // 创建sql语句
        String sql = "insert into user (uid,username,password) values(?,?,?)";
        // 设置实际参数
        Object[] params = {user.getUid(),user.getUsername(),user.getPassword()};
        // 执行
        int i = -1;
        i = jt.update(sql,params);
        return i;
    }
}
