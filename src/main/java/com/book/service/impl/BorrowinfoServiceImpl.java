package com.book.service.impl;

import com.book.bean.PageBean;
import com.book.bean.TreeMenu;
import com.book.dao.IBorrowinfoDao;
import com.book.dao.impl.BorrowinfoDaoImpl;
import com.book.entity.Bookinfo;
import com.book.entity.Borrowinfo;
import com.book.service.IBorrowinfoService;

import java.util.List;
import java.util.UUID;

/**
 * BorrowinfoServiceImpl
 *
 * @author hcj
 * @date 2023-08-11
 */
public class BorrowinfoServiceImpl implements IBorrowinfoService {

    private IBorrowinfoDao borrowinfoDao;

    public BorrowinfoServiceImpl() {
        borrowinfoDao = new BorrowinfoDaoImpl();
    }

    @Override
    public PageBean<Borrowinfo> findBorrowinfoList(String pageIndexStr, String pageSizeStr) {
        // String==>Integer
        Integer pageIndex = Integer.parseInt(pageIndexStr);
        Integer pageSize = Integer.parseInt(pageSizeStr);
        // 创建PageBean
        PageBean<Borrowinfo> pageBean = new PageBean<>();
        // 计算分页start,pageSize
        int start = pageSize*pageIndex;
        List<Borrowinfo> list = borrowinfoDao.queryBorrowinfoList(start,pageSize);
        pageBean.setData(list);
        // 存入总记录数
        long totalCount = borrowinfoDao.queryBorrowinfoTotalCount();
        int totalCountInt = (int)totalCount;
        pageBean.setTotal(totalCountInt);
        return pageBean;
    }

    @Override
    public List<TreeMenu> findTreeMenus() {
        return borrowinfoDao.queryTreeMenus();
    }

    @Override
    public int addBorrowinfo(Borrowinfo borrowinfo) {
        // 自动生成borrowid 通过数据库的触发器自动生成
        borrowinfo.setBack(0); // 0:未归还  1：已归还
        return borrowinfoDao.insertBorrowinfo(borrowinfo);
    }

    @Override
    public void returnBorrowinfoStatus(String borrowid) {
        borrowinfoDao.updateBorrowinfoStatus(borrowid);
    }

    @Override
    public Borrowinfo findBorrowinfoByBorrowid(String borrowid) {
        return borrowinfoDao.queryBorrowinfoByBorrowid(borrowid);
    }

    @Override
    public int deleteBorrowinfoByborrowid(String borrowid,int backStatus) {
        return borrowinfoDao.deleteBorrowinfoByborrowid(borrowid,backStatus);
    }

    @Override
    public List<Borrowinfo> findBorrowinfoList(String bookid) {
        return borrowinfoDao.queryBorrowinfoList(bookid);
    }

}
