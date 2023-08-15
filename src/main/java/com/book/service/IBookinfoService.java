package com.book.service;

import com.book.bean.PageBean;
import com.book.entity.Bookinfo;
import com.book.entity.Booktype;

import java.util.List;

/**
 * IBookinfoService
 *
 * @author hcj
 * @date 2023-08-09
 */
public interface IBookinfoService {
    // 查询所有图书列表
    PageBean<Bookinfo> findBookinfoList(String bookname,String booktype,String pageIndex,String pageSize);

    // 新增图书信息
    int addBookinfo(Bookinfo bookinfo);

    // 查询所有图书类别
    List<Booktype> findAllBooktype();

    // 同一位作者的书名不能重复
    boolean repeatAuthorByBookname(String bookname,String author);

    // 根据bookid删除图书信息
    int deleteBookinfoByBookid(String bookid);

    // 根据id查询Bookinfo
    Bookinfo findBookinfoByBookid(String bookid);

    // 新增图书信息
    int updateBookinfo(Bookinfo bookinfo);

    // 根据图书编号查询该图书剩余数量
    int findBookinfoRemain(String bookid);

    // 更新图书信息剩余数量-1
    int updateBookinfoReduceRemain(String bookid);

    // 更新“图书信息表”中剩余数量+1
    int updateBookinfoAddRemain(String bookid);
}
