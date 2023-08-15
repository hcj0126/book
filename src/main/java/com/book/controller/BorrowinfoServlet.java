package com.book.controller;

import cn.hutool.json.JSONUtil;
import com.book.bean.PageBean;
import com.book.bean.TreeMenu;
import com.book.entity.Bookinfo;
import com.book.entity.Borrowinfo;
import com.book.service.IBookinfoService;
import com.book.service.IBorrowinfoService;
import com.book.service.impl.BookinfoServiceImpl;
import com.book.service.impl.BorrowinfoServiceImpl;
import com.book.vo.ResultVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * BorrowinfoServlet
 *
 * @author hcj
 * @date 2023-08-11
 */
@WebServlet("/borrowinfo")
public class BorrowinfoServlet  extends BaseServlet{

    private IBorrowinfoService borrowinfoService = new BorrowinfoServiceImpl();

    private IBookinfoService bookinfoService = new BookinfoServiceImpl();

    /**
     * 获取借阅信息列表
     */
    public void getBorrowinfoList(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 获取分页参数  当前页和每页条数
        String pageIndex = request.getParameter("pageIndex");
        String pageSize = request.getParameter("pageSize");

        PageBean<Borrowinfo> pb = borrowinfoService.findBorrowinfoList(pageIndex,pageSize);

        // 响应给服务器信息
        response.getWriter().write(JSONUtil.toJsonStr(pb));
    }

    /**
     * 获取图书种类和种类下的所有图书信息树
     */
    public void getTreeMenus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 调用service层
        List<TreeMenu> treeMenus = borrowinfoService.findTreeMenus();
        // 响应给服务器信息
        response.getWriter().write(JSONUtil.toJsonStr(treeMenus));
    }

    /**
     * 新增借阅信息
    */
    public void addBorrowinfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取页面提交过来的参数
        String data = request.getParameter("submitData");
        // System.out.println(data);
        // 把String转成Borrowinfo对象
        Borrowinfo borrowinfo = JSONUtil.toBean(data,Borrowinfo.class);
        // System.out.println(borrowinfo);
        ResultVo resultVo = new ResultVo();
        // 判断图书剩余数量是否大于1本
        int bookinfoRemain = bookinfoService.findBookinfoRemain(borrowinfo.getBookid());
        if(bookinfoRemain>=1){
            resultVo.setOk(true);
            int i = borrowinfoService.addBorrowinfo(borrowinfo);
            if(i!=-1){
                resultVo.setMsg("添加借阅信息成功");
                // 添加借阅成功后及时更新图书库存-1
                bookinfoService.updateBookinfoReduceRemain(borrowinfo.getBookid());
            }
        }else{
            resultVo.setOk(false);
            resultVo.setMsg("图书剩余数量不足");
        }
        // 响应给服务器信息
        response.getWriter().write(JSONUtil.toJsonStr(resultVo));
    }

    /**
     * 还书
    */
    public void returnBookinfoByBorrowid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String borrowid = request.getParameter("borrowid");
        // 更改还书状态  0==>1
        borrowinfoService.returnBorrowinfoStatus(borrowid);
        // 根据借阅编号查询借阅信息对象
        Borrowinfo borrowinfo = borrowinfoService.findBorrowinfoByBorrowid(borrowid);
        // 更新“图书信息表”中剩余数量+1
        int a = bookinfoService.updateBookinfoAddRemain(borrowinfo.getBookid());
        ResultVo resultVo = new ResultVo();
        if(a!=-1){
            resultVo.setOk(true);
            resultVo.setMsg("还书成功");
        }
        // 响应给服务器信息
        response.getWriter().write(JSONUtil.toJsonStr(resultVo));
    }

    /**
     * 删除借阅信息(说明借阅的书已经归还)
    */
    public void deleteBorrowinfoByBorrowid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String borrowid = request.getParameter("borrowid");
        // 根据借阅编号查询借阅信息对象
        Borrowinfo borrowinfo = borrowinfoService.findBorrowinfoByBorrowid(borrowid);
        // 根据借阅号删除借阅信息
        int a = borrowinfoService.deleteBorrowinfoByborrowid(borrowid,borrowinfo.getBack());
        ResultVo resultVo = new ResultVo();
        if(a!=-1){
            resultVo.setOk(true);
            resultVo.setMsg("删除成功");
        }
        response.getWriter().write(JSONUtil.toJsonStr(resultVo));
    }
}
