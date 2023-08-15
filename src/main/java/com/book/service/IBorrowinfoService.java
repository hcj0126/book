package com.book.service;

import com.book.bean.PageBean;
import com.book.bean.TreeMenu;
import com.book.entity.Borrowinfo;

import java.util.List;

/**
 * IBorrowinfoService
 *
 * @author hcj
 * @date 2023-08-11
 */
public interface IBorrowinfoService {
    // 查询所有图书列表
    PageBean<Borrowinfo> findBorrowinfoList(String pageIndex, String pageSize);

    // 获取图书种类和种类下的所有图书信息树
    List<TreeMenu> findTreeMenus();

    // 新增借阅信息
    int addBorrowinfo(Borrowinfo borrowinfo);

    // 更改还书状态  0==>1
    void returnBorrowinfoStatus(String borrowid);

    // 根据借阅编号查询借阅信息对象
    Borrowinfo findBorrowinfoByBorrowid(String borrowid);

    // 根据借阅号删除借阅信息
    int deleteBorrowinfoByborrowid(String borrowid,int backStatus);

    // 根据图书编号查询借阅表里的列表
    List<Borrowinfo> findBorrowinfoList(String bookid);
}
