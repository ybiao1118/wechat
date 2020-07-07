package com.cqyb.wechat.controller;

import com.cqyb.wechat.entity.user.UserInfo;
import com.cqyb.wechat.service.Oauth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @Auther: yanbiao
 * @Date: 2019/10/19 22:13
 * @Description:
 */
@Controller
@RequestMapping("/auth")
public class Oauth2Controller {
    @Autowired
    private Oauth2Service service;
    @GetMapping
    public void getCode(HttpServletRequest request, HttpServletResponse response){
        try {
            request.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            String reqUrl=request.getParameter("reqUrl");
            Cookie cookie=new Cookie("reqUrl",reqUrl);
            cookie.setMaxAge(-1);
            cookie.setPath("/");
            response.addCookie(cookie);
            String url=service.getUserInfoCode();
            response.sendRedirect(url);// 跳转到要访问的地址
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @GetMapping("/getUserInfo")
    public void getUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        //判断cookie中是否存在openid 若存在则直接跳过，不存在则获取一次
        String reqUrl =null;
        if(cookies!=null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("reqUrl")){
                    reqUrl = cookie.getValue();
                }
            }
        }
        String code = request.getParameter("code");
        UserInfo user= service.getUserInfo(code);
        String openId=user.getOpenid();
        Cookie cookie=new Cookie("openId",openId);
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        response.addCookie(cookie);
       response.sendRedirect("http://www.cqyb.top"+reqUrl);
    }
}
