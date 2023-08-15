package com.book.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * DruidUtils
 *  封装从Druid连接池中获取连接
 * @author hcj
 * @date 2023-07-25
 */
public class DruidUtils {
    // 创建连接池
    private static DataSource dataSource;
    // 加载druid.properties中的配置数据
    static{
        try {
            // dbcp连接池要自己去读取dbcp.properties中的配置数据
            Properties prop = new Properties();
            // Reader r = new FileReader("src/druid.properties");
            InputStream is = DruidUtils.class.getClassLoader().getResourceAsStream("druid.properties");
            prop.load(is);
            // 获得连接池(数据源)
            dataSource = DruidDataSourceFactory.createDataSource(prop);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得连接池(数据源)
    */
    public static DataSource getDataSource(){
        return dataSource;
    }
    /**
     * 获得连接
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    // 释放资源
    public static void closeResources(ResultSet rs , PreparedStatement ps , Connection con){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(ps!=null){
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(con!=null){
            try {
                con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
