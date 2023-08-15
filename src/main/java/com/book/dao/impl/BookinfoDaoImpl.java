package com.book.dao.impl;

import cn.hutool.core.util.StrUtil;
import com.book.dao.IBookinfoDao;
import com.book.entity.Bookinfo;
import com.book.entity.Booktype;
import com.book.entity.User;
import com.book.util.DruidUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
/**
 * BookinfoDaoImpl
 *
 * @author hcj
 * @date 2023-08-09
 */
public class BookinfoDaoImpl implements IBookinfoDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());


    @Override
    public List<Bookinfo> queryBookinfoList(String bookname,String booktype,Integer start,Integer pageSize) {
        // 创建sql语句
        String sql = "select * from bookinfo where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        List<Object> params = new ArrayList<>();
        if(StrUtil.isNotEmpty(bookname)){
            // 添加参数param
            params.add("%"+bookname+"%");
            sb.append(" and bookname like ?");
        }
        if(StrUtil.isNotEmpty(booktype)){
            // 添加参数param
            params.add("%"+booktype+"%");
            sb.append(" and booktype like ?");
        }
        // 拼接分页查询  limit start,rows
        sb.append(" limit ?,?");
        // 设置实际参数
        params.add(start);
        params.add(pageSize);
        return jdbcTemplate.query(sb.toString(),new BeanPropertyRowMapper<>(Bookinfo.class),params.toArray());
    }

    @Override
    public long queryBookinfoTotalCount(String bookname,String booktype) {
        String sql = "select count(*) from bookinfo where 1=1";
        // 创建StringBuilder拼接sql语句
        StringBuilder sb = new StringBuilder(sql);
        // 定义一个参数集合
        List<Object> params = new ArrayList<>();
        if(StrUtil.isNotEmpty(bookname)){
            // 添加参数param
            params.add("%"+bookname+"%");
            sb.append(" and bookname like ?");
        }
        if(StrUtil.isNotEmpty(booktype)){
            // 添加参数param
            params.add("%"+booktype+"%");
            sb.append(" and booktype like ?");
        }
        return jdbcTemplate.queryForObject(sb.toString(), Integer.class,params.toArray());
    }

    @Override
    public int insertBookinfo(Bookinfo bookinfo) {
        String sql = "insert into bookinfo values(?,?,?,?,?,?)";
        // 设置实际参数
        Object[] params = {bookinfo.getBookid(),bookinfo.getBookname(),bookinfo.getPublisher(),bookinfo.getAuthor(),bookinfo.getBooktype(),bookinfo.getRemain()};
        return jdbcTemplate.update(sql,params);
    }

    @Override
    public List<Booktype> queryAllBooktype() {
        String sql = "select * from booktype";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Booktype.class));
    }

    @Override
    public Bookinfo repeatAuthorByBookname(String bookname, String author) {

        try{
            // 创建sql语句
            String sql = "select * from bookinfo where bookname=? and author=?";
            Bookinfo bookinfo =
                    jdbcTemplate.queryForObject(sql,
                            new BeanPropertyRowMapper<>(Bookinfo.class),bookname,author);
            return bookinfo;
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public int deleteBookinfoByBookid(String bookid) {
        String sql = "delete from bookinfo where bookid=?";
        return jdbcTemplate.update(sql,bookid);
    }

    @Override
    public Bookinfo queryBookinfoByBookid(String bookid) {
        try{
            // 创建sql语句
            String sql = "select * from bookinfo where bookid=?";
            Bookinfo bookinfo =
                    jdbcTemplate.queryForObject(sql,
                            new BeanPropertyRowMapper<>(Bookinfo.class),bookid);
            return bookinfo;
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public int updateBookinfo(Bookinfo bookinfo) {
        String sql = "update bookinfo set publisher=?,booktype=? where bookid=?";
        Object[] params = {bookinfo.getPublisher(),bookinfo.getBooktype(),bookinfo.getBookid()};
        return jdbcTemplate.update(sql,params);
    }

    @Override
    public int queryBookinfoRemain(String bookid) {
        String sql = "select remain from bookinfo where bookid=?";
        return jdbcTemplate.queryForObject(sql,Integer.class,bookid);
    }

    @Override
    public int updateBookinfoReduceRemain(String bookid) {
        String sql = "update bookinfo set remain=remain-1 where bookid=?";
        return jdbcTemplate.update(sql,bookid);
    }

    @Override
    public int updateBookinfoAddRemain(String bookid) {
        String sql = "update bookinfo set remain=remain+1 where bookid=?";
        return jdbcTemplate.update(sql,bookid);
    }

}
