package com.cqyb.wechat.intercepors;

import afu.org.checkerframework.checker.nullness.qual.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: yanbiao
 * @Date: 2019/10/19 21:41
 * @Description:
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    //这个方法是在访问接口之前执行的，我们只需要在这里写验证登陆状态的业务逻辑，就可以在用户调用指定接口之前验证登陆状态了
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        request.getRemoteAddr();//获取ip
//        request.getRemotePort();//获取端口号
//        request.getServletPath();//获取请求地址
        String reqUrl = request.getRequestURI();
        System.out.println("拦截器============"+reqUrl);
        Cookie[] cookies = request.getCookies();
        String openId=null;
        //判断cookie中是否存在openid 若存在则直接跳过，不存在则获取一次
        if(cookies!=null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("openId")){
                    openId = cookie.getValue();
                }
            }
        }
        System.out.println("openid============"+openId);
        if (StringUtils.isEmpty(openId)) {
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                String methodName = handlerMethod.getMethod().getName();

                //  if (checkList(arrQueController, methodName)) {
                // System.out.println("执行了");
                response.sendRedirect("/wechat/auth?reqUrl="+ reqUrl);
                return false;
                //  }
            }
            return true;
        } else {
            return true;
        }

    }


}
