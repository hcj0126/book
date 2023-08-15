package com.book.dao;

import com.book.bean.TreeMenu;
import com.book.entity.Borrowinfo;

import java.util.List;

/**
 * IBorrowinfoDao
 *
 * @author hcj
 * @date 2023-08-11
 */
public interface IBorrowinfoDao {
    // 查询所有借阅信息列表
    List<Borrowinfo> queryBorrowinfoList(Integer start, Integer pageSize);
    // 查询总记录数
    long queryBorrowinfoTotalCount();

    // 获取图书种类和种类下的所有图书信息树
    List<TreeMenu> queryTreeMenus();

    // 新增借阅信息
    int insertBorrowinfo(Borrowinfo borrowinfo);

    // 更新还书状态 0==》1
    void updateBorrowinfoStatus(String borrowid);

    // 根据借阅编号查询借阅信息对象
    Borrowinfo queryBorrowinfoByBorrowid(String borrowid);

    // 根据借阅号删除借阅信息
    int deleteBorrowinfoByborrowid(String borrowid,int backStatus);

    // 根据图书编号查询借阅表里的列表
    List<Borrowinfo> queryBorrowinfoList(String bookid);

}
