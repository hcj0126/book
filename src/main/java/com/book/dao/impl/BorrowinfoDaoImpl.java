package com.book.dao.impl;

import com.book.bean.TreeMenu;
import com.book.dao.IBorrowinfoDao;
import com.book.entity.Bookinfo;
import com.book.entity.Booktype;
import com.book.entity.Borrowinfo;
import com.book.util.DateUtil;
import com.book.util.DruidUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * BorrowinfoDaoImpl
 *
 * @author hcj
 * @date 2023-08-11
 */
public class BorrowinfoDaoImpl implements IBorrowinfoDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());

    @Override
    public List<Borrowinfo> queryBorrowinfoList(Integer start, Integer pageSize) {

        // 创建sql语句
        String sql = "SELECT bw.borrowid,b.bookname as bookid,bw.borrower,bw.phone,bw.borrowtime,bw.returntime,bw.back FROM borrowinfo bw left join bookinfo b on bw.bookid=b.bookid order by bw.borrowtime desc";
        StringBuilder sb = new StringBuilder(sql);
        List<Object> params = new ArrayList<>();
        // 拼接分页查询  limit start,rows
        sb.append(" limit ?,?");
        // 设置实际参数
        params.add(start);
        params.add(pageSize);
        return jdbcTemplate.query(sb.toString(),new BeanPropertyRowMapper<>(Borrowinfo.class),params.toArray());
    }

    @Override
    public long queryBorrowinfoTotalCount() {
        String sql = "select count(*) from borrowinfo";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public List<TreeMenu> queryTreeMenus() {
        // 查询出所有图书种类
        String sql = "select * from booktype";
        // 创建菜单树集合
        List<TreeMenu> treeMenuList = new ArrayList<>();
        // 查询所有图书类别信息
        List<Booktype> booktypeList = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Booktype.class));
        // 遍历图书类别集合
        for (Booktype booktype : booktypeList) {
            // 创建菜单树对象
            TreeMenu treeMenu = new TreeMenu();
            treeMenu.setId(booktype.getId().toString());
            treeMenu.setText(booktype.getName());
            treeMenu.setExpanded(false);

            // 根据图书类别编号查询出当前类别下的所有图书信息
            String id = booktype.getId().toString();
            String sql2 = "select * from bookinfo where booktype=?";
            List<Bookinfo> bookinfoList = jdbcTemplate.query(sql2,new BeanPropertyRowMapper<>(Bookinfo.class),id);
            // 创建子节点集合
            List<TreeMenu> childrenList = new ArrayList<>();
            // 遍历前类别下的所有图书信息
            for (Bookinfo bookinfo : bookinfoList) {
                TreeMenu children = new TreeMenu();
                children.setId(bookinfo.getBookid());
                children.setText(bookinfo.getBookname());
                children.setExpanded(false);
                childrenList.add(children);
            }
            // 存入当前节点的子节点集合
            treeMenu.setChildren(childrenList);
            // 存入菜单树集合中
            treeMenuList.add(treeMenu);
        }
        return treeMenuList;
    }

    @Override
    public int insertBorrowinfo(Borrowinfo borrowinfo) {
        String sql = "insert into borrowinfo(bookid,borrower,phone,borrowtime,back) values(?,?,?,?,?)";
        Object[] params = {borrowinfo.getBookid(),borrowinfo.getBorrower(),borrowinfo.getPhone(),borrowinfo.getBorrowtime(),borrowinfo.getBack()};
        return jdbcTemplate.update(sql,params);
    }

    @Override
    public void updateBorrowinfoStatus(String borrowid) {
        String sql = "update borrowinfo set back=1,returntime=? where borrowid=? ";
        String returntime = DateUtil.dateFormat(new Date(),"yyyy-MM-dd hh:mm:ss");
        jdbcTemplate.update(sql,returntime,borrowid);
    }

    @Override
    public Borrowinfo queryBorrowinfoByBorrowid(String borrowid) {

        try{
            // 创建sql语句
            String sql = "select * from borrowinfo where borrowid=?";
            Borrowinfo borrowinfo =
                    jdbcTemplate.queryForObject(sql,
                            new BeanPropertyRowMapper<>(Borrowinfo.class),borrowid);
            return borrowinfo;
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public int deleteBorrowinfoByborrowid(String borrowid,int backStatus) {
        String sql = "delete from borrowinfo where borrowid=? and back=?";
        return jdbcTemplate.update(sql,borrowid,backStatus);
    }

    @Override
    public List<Borrowinfo> queryBorrowinfoList(String bookid) {
        String sql = "select * from borrowinfo where bookid=?";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Borrowinfo.class),bookid);
    }

}
