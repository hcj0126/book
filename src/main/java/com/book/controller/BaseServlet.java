package com.book.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 解决乱码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        // 获取接口方法参数
        String methodName = request.getParameter("method");
        // 反射获取 Method
        try {
            // 获的Method类 需要明确方法名和参数列表
            Method method = this.getClass().getMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
            // 打破权限
            // method.setAccessible(true);
            // 执行
            method.invoke(this,request,response);
        } catch (Exception e) {
            System.out.println("你请求的接口方法"+methodName+"不存在！");
            e.printStackTrace();
        }
    }
}
