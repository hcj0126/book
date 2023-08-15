package com.book.service.impl;

import cn.hutool.core.util.StrUtil;
import com.book.bean.PageBean;
import com.book.dao.IBookinfoDao;
import com.book.dao.impl.BookinfoDaoImpl;
import com.book.entity.Bookinfo;
import com.book.entity.Booktype;
import com.book.service.IBookinfoService;

import java.util.List;
import java.util.UUID;

/**
 * BookinfoServiceImpl
 *
 * @author hcj
 * @date 2023-08-09
 */
public class BookinfoServiceImpl implements IBookinfoService {

    private IBookinfoDao bookinfoDao;

    public BookinfoServiceImpl() {
        bookinfoDao = new BookinfoDaoImpl();
    }

    @Override
    public PageBean<Bookinfo> findBookinfoList(String bookname,String booktype,String pageIndexStr,String pageSizeStr) {
        // String==>Integer
        Integer pageIndex = Integer.parseInt(pageIndexStr);
        Integer pageSize = Integer.parseInt(pageSizeStr);
        // 创建PageBean
        PageBean<Bookinfo> pageBean = new PageBean<>();
        // 计算分页start,pageSize
        int start = pageSize*pageIndex;
        List<Bookinfo> list = bookinfoDao.queryBookinfoList(bookname,booktype,start,pageSize);
        pageBean.setData(list);
        // 存入总记录数
        long totalCount = bookinfoDao.queryBookinfoTotalCount(bookname,booktype);
        int totalCountInt = (int)totalCount;
        pageBean.setTotal(totalCountInt);
        return pageBean;
    }

    @Override
    public int addBookinfo(Bookinfo bookinfo) {
        // bookid自动生成
        bookinfo.setBookid(UUID.randomUUID().toString());
        return bookinfoDao.insertBookinfo(bookinfo);
    }

    @Override
    public List<Booktype> findAllBooktype() {
        return bookinfoDao.queryAllBooktype();
    }

    @Override
    public boolean repeatAuthorByBookname(String bookname, String author) {
        Bookinfo bookinfo = bookinfoDao.repeatAuthorByBookname(bookname, author);
        if(bookinfo==null){
            return true;
        }
        return false;
    }

    @Override
    public int deleteBookinfoByBookid(String bookid) {
        return bookinfoDao.deleteBookinfoByBookid(bookid);
    }

    @Override
    public Bookinfo findBookinfoByBookid(String bookid) {
        return bookinfoDao.queryBookinfoByBookid(bookid);
    }

    @Override
    public int updateBookinfo(Bookinfo bookinfo) {
        return bookinfoDao.updateBookinfo(bookinfo);
    }

    @Override
    public int findBookinfoRemain(String bookid) {
        return bookinfoDao.queryBookinfoRemain(bookid);
    }

    @Override
    public int updateBookinfoReduceRemain(String bookid) {
        return bookinfoDao.updateBookinfoReduceRemain(bookid);
    }

    @Override
    public int updateBookinfoAddRemain(String bookid) {
        return bookinfoDao.updateBookinfoAddRemain(bookid);
    }
}
