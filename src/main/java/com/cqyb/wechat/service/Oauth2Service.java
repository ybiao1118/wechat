package com.cqyb.wechat.service;

import com.cqyb.wechat.entity.user.UserInfo;

/**
 * @Auther: yanbiao
 * @Date: 2019/10/19 22:06
 * @Description:获取用户信息接口
 */
public interface Oauth2Service {
    String getUserInfoCode();
//    String getBaseCode();
//    String getOpenId();
    UserInfo getUserInfo(String code);
}
