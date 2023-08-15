package com.book.controller;

import cn.hutool.json.JSONUtil;
import com.book.entity.User;
import com.book.service.IUserService;
import com.book.service.impl.UserServiceImpl;
import com.book.util.FormToBeanUtil;
import com.book.vo.ResultVo;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    // 创建UserService对象
    private IUserService userService = new UserServiceImpl();

    /**
     * 登录
    */
    public void login(HttpServletRequest request,HttpServletResponse response) throws IOException {
        ResultVo resultVo= new ResultVo();
        // 获取浏览器输入的验证码
        String myCode = request.getParameter("code");
        // 获取服务器生成的验证码
        String verifyCode = (String)request.getSession().getAttribute("verifyCode");
        // 比较
        if(!myCode.equalsIgnoreCase(verifyCode)){
            // 响应给服务器错误信息 {"flag":"true","msg":"验证码错误"}
            // response.getWriter().println("");
            resultVo.setOk(false);
            resultVo.setMsg("验证码错误");
        }else{
            // 验证用户名和密码
            User user = FormToBeanUtil.formToBean(request.getParameterMap(),User.class);
            user = userService.loginUser(user);
            if(user!=null) {
                resultVo.setOk(true);
                resultVo.setT(user);
                request.getSession().setAttribute("user",user);
            }else{
                // 用户名和密码不对
                resultVo.setMsg("用户名或密码错误");
            }
        }
        // 把返回给前端的结果集转成json对象
        String json = JSONUtil.toJsonStr(resultVo);
        // 响应给服务器错误信息
        response.getWriter().println(json);
    }

    /**
     * 注册
     */
    public void register(HttpServletRequest request,HttpServletResponse response) throws IOException {
        User user = FormToBeanUtil.formToBean(request.getParameterMap(),User.class);
        int i = userService.addUser(user);
        ResultVo resultVo= new ResultVo();
        if(i!=-1){
            resultVo.setOk(true);
            resultVo.setMsg("注册成功");
        }
        // 把返回给前端的结果集转成json对象
        String json = JSONUtil.toJsonStr(resultVo);
        // 响应给服务器错误信息
        response.getWriter().println(json);
    }
    /**
     * 检验用户名是否被占用
     */
    public void verifyUsername(HttpServletRequest request,HttpServletResponse response) throws IOException {
        ResultVo resultVo= new ResultVo();
        // 获取页面的请求参数
        // String username = request.getParameter("username");
        String username = new String(request.getParameter("username").getBytes("ISO-8859-1"),"utf-8");
        //System.out.println(username);
        User user = userService.findUserByUsername(username);
        if(user!=null){
            // 说明用户名存在
            resultVo.setOk(true);
            resultVo.setMsg("用户名已存在");
        }
        // 把返回给前端的结果集转成json对象
        String json = JSONUtil.toJsonStr(resultVo);
        // 响应给服务器错误信息
        response.getWriter().println(json);
    }

    /**
     * 获取登录成功后的用户名
     */
    public void getLoginUsername(HttpServletRequest request,HttpServletResponse response) throws IOException{
        // 获取user,从登录的方法中存入session容器中获取
        User user = (User)request.getSession().getAttribute("user");
        ResultVo resultVo= new ResultVo();
        resultVo.setT(user);
        // 把返回给前端的结果集转成json对象
        String json = JSONUtil.toJsonStr(resultVo);
        // 响应给服务器错误信息
        response.getWriter().println(json);
    }
    /**
     * 登录注销
     */
    public void loginOut(HttpServletRequest request,HttpServletResponse response) throws IOException{
        // 注销session
        request.getSession().invalidate();
        // 注销成功后重定向到login.html
        response.sendRedirect("/login.html");
    }
}
