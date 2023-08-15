package com.book.controller;

import cn.hutool.json.JSONUtil;
import com.book.bean.PageBean;
import com.book.entity.Bookinfo;
import com.book.entity.Booktype;
import com.book.entity.Borrowinfo;
import com.book.entity.User;
import com.book.service.IBookinfoService;
import com.book.service.IBorrowinfoService;
import com.book.service.impl.BookinfoServiceImpl;
import com.book.service.impl.BorrowinfoServiceImpl;
import com.book.vo.ResultVo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.print.Book;
import java.io.IOException;
import java.util.List;

/**
 * BookinfoServlet
 *
 * @author hcj
 * @date 2023-08-09
 */
@WebServlet("/bookinfo")
public class BookinfoServlet extends BaseServlet{

    // 创建IBookinfoService
    private IBookinfoService bookinfoService = new BookinfoServiceImpl();

    // 创建IBorrowinfoService
    private IBorrowinfoService borrowinfoService = new BorrowinfoServiceImpl();

    /**
     * 获取图书信息列表
     */
    public void getBookinfoList(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 获取页面查询请求参数
        String bookname = request.getParameter("bookname");
        String booktype = request.getParameter("booktype");;
        // 获取分页参数  当前页和每页条数
        String pageIndex = request.getParameter("pageIndex");
        String pageSize = request.getParameter("pageSize");

        PageBean<Bookinfo> pageBean = bookinfoService.findBookinfoList(bookname,booktype,pageIndex,pageSize);

        // 把返回给前端的结果集转成json对象
        String json = JSONUtil.toJsonStr(pageBean);
        // 响应给服务器错误信息
        response.getWriter().println(json);
    }


    /**
     * 新增图书信息
    */
    public void addBookinfo(HttpServletRequest request, HttpServletResponse response) throws IOException{
        // 获取页面提交过来的参数
        String data = request.getParameter("submitData");
        // System.out.println(data);
        // 把Stringokin转成Bookinfo对象
        Bookinfo bookinfo = JSONUtil.toBean(data,Bookinfo.class);
        // System.out.println(bookinfo);
        ResultVo resultVo = new ResultVo();
        // 把图书名称和作者传入，去数据库验证同一位作者的书名不能重复
        boolean flag = bookinfoService.repeatAuthorByBookname(bookinfo.getBookname(),bookinfo.getAuthor());
        if(flag){
            // 调用bookinfoService
            int a = bookinfoService.addBookinfo(bookinfo);
            if(a!=-1){
                resultVo.setOk(true);
                resultVo.setMsg("新增图书信息成功");
            }
        }else{
            // 证同一位作者的书名重复
            resultVo.setOk(false);
            resultVo.setMsg("同一位作者的书名重复");
        }
        response.getWriter().write(JSONUtil.toJsonStr(resultVo));


    }


    /**
     * 获取图书类别
     */
    public void getAllBooktype(HttpServletRequest request, HttpServletResponse response) throws IOException{
        List<Booktype> booktypeList = bookinfoService.findAllBooktype();
        response.getWriter().write(JSONUtil.toJsonStr(booktypeList));
    }

    /**
     * 批量删除图书信息
     */
    public void deleteBookinfoByBookid(HttpServletRequest request, HttpServletResponse response) throws IOException{
        // 获取页面传入的bookid
        String bookid = request.getParameter("id");
        // System.out.println(bookid);
        ResultVo resultVo = new ResultVo();
        // 根据图书编号查询借阅表里的列表
        List<Borrowinfo> borrowinfoList = borrowinfoService.findBorrowinfoList(bookid);
        // 遍历borrowinfoList，判断被删除的图书有没有未归还的
        // true:已归还
        boolean returnStatus = true;
        for (Borrowinfo borrowinfo : borrowinfoList) {
            if(borrowinfo.getBack()==0){
                returnStatus = false;
            }
        }
        if(!returnStatus){
            resultVo.setOk(false);
            resultVo.setMsg("还有未归还的图书");
        }else{
            // 图书全部归还完毕
            int a = bookinfoService.deleteBookinfoByBookid(bookid);
            if(a!=-1){
                resultVo.setOk(true);
                resultVo.setMsg("删除成功");
            }else{
                resultVo.setOk(false);
                resultVo.setMsg("删除失败");
            }
        }
        response.getWriter().write(JSONUtil.toJsonStr(resultVo));
    }

    /**
     * 根据bookid获取图书信息
     */
    public void getBookinfoByBookid(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String bookid = request.getParameter("bookid");
        //
        Bookinfo bookinfo = bookinfoService.findBookinfoByBookid(bookid);
        response.getWriter().write(JSONUtil.toJsonStr(bookinfo));
    }

    /**
     * 根据bookid修改图书信息
     */
    public void updateBookinfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取页面提交过来的参数
        String data = request.getParameter("submitData");
        // System.out.println(data);
        // 把Stringokin转成Bookinfo对象
        Bookinfo bookinfo = JSONUtil.toBean(data,Bookinfo.class);
        // System.out.println(bookinfo);
        ResultVo resultVo = new ResultVo();
        int a = bookinfoService.updateBookinfo(bookinfo);
        if(a!=-1){
            resultVo.setOk(true);
            resultVo.setMsg("修改图书信息成功");
        }else{
            resultVo.setOk(false);
            resultVo.setMsg("修改图书信息失败");
        }
        response.getWriter().write(JSONUtil.toJsonStr(resultVo));
    }

}
