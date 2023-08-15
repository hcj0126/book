package com.book.controller;

import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.json.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * VerifyCodeServlet
 *
 * @author hcj
 * @date 2023-08-07
 */
@WebServlet("/verifyCodeServlet")
public class VerifyCodeServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 定义验证码图片，长、宽、验证码的字符数、有无干扰元素等
        CircleCaptcha captcha = new CircleCaptcha(130,60,4,20);
        // 获取四位正确的验证码
        String verifyCode = captcha.getCode();
        // 把服务器生成的验证码存储起来
        request.getSession().setAttribute("verifyCode",verifyCode);
        // 以图片形式响应给浏览器
        captcha.write(response.getOutputStream());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
