package com.book.dao;

import com.book.entity.Bookinfo;
import com.book.entity.Booktype;

import java.util.List;

/**
 * IBookinfoDao
 *
 * @author hcj
 * @date 2023-08-09
 */
public interface IBookinfoDao {

    // 查询所有图书列表
    List<Bookinfo> queryBookinfoList(String bookname,String booktype,Integer start,Integer pageSize);

    // 根据条件查询总记录数
    long queryBookinfoTotalCount(String bookname,String booktype);

    // 新增图书信息
    int insertBookinfo(Bookinfo bookinfo);

    // 查询所有图书类别
    List<Booktype> queryAllBooktype();

    // 同一位作者的书名不能重复
    Bookinfo repeatAuthorByBookname(String bookname,String author);

    // 根据bookid删除图书信息
    int deleteBookinfoByBookid(String bookid);

    // 根据id查询Bookinfo
    Bookinfo queryBookinfoByBookid(String bookid);

    // 新增图书信息
    int updateBookinfo(Bookinfo bookinfo);

    // 根据图书编号获取借阅信息
    int queryBookinfoRemain(String bookid);

    // 更新图书信息剩余数量-1
    int updateBookinfoReduceRemain(String bookid);

    // 更新图书信息剩余数量+1
    int updateBookinfoAddRemain(String bookid);
}
