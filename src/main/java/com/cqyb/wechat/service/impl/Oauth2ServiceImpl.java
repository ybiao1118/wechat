package com.cqyb.wechat.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cqyb.wechat.config.WeChatConfig;
import com.cqyb.wechat.entity.user.UserInfo;
import com.cqyb.wechat.service.Oauth2Service;
import com.cqyb.wechat.util.HttpClientUtil;
import com.cqyb.wechat.util.RedisUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Auther: yanbiao
 * @Date: 2019/10/19 22:07
 * @Description:
 */
@Service
public class Oauth2ServiceImpl implements Oauth2Service {
    @Autowired
    private WeChatConfig config;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public String getUserInfoCode() {
        String url=null;
        try {
            // 授权后重定向的回调链接地址，请使用urlencode对链接进行处理（文档要求）
            String redirect_uri = URLEncoder.encode("http://www.cqyb.top/wechat/auth/getUserInfo", "UTF-8");
//            System.out.println("redirect_uri===="+redirect_uri);
            // 按照文档要求拼接访问地址
            url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
                    + config.appId
                    + "&redirect_uri="
                    + redirect_uri
                    + "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

//    @Override
//    public String getBaseCode() {
//        String url=null;
//        try {
//            // 授权后重定向的回调链接地址，请使用urlencode对链接进行处理（文档要求）
//            String redirect_uri = URLEncoder.encode("http://www.cqyb.top/wechat/auth/getOpenId", "UTF-8");
////            System.out.println("redirect_uri===="+redirect_uri);
//            // 按照文档要求拼接访问地址
//            url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
//                    + config.appId
//                    + "&redirect_uri="
//                    + redirect_uri
//                    + "&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return url;
//    }
//
//    @Override
//    public String getOpenId() {
//        return null;
//    }

    @Override
    public UserInfo getUserInfo(String code) {
        String param=getAccessToken(code);
        JSONObject jsonObject= JSON.parseObject(param);
        String openId=jsonObject.get("openid").toString();
        String accessToken=jsonObject.get("access_token").toString();
        String url="https://api.weixin.qq.com/sns/userinfo?access_token="
                + accessToken
                + "&openid="
                + openId + "&lang=zh_CN";
        String  result= HttpClientUtil.doGet(url);
        JSONObject object=JSON.parseObject(result);
        System.out.println(object.toString());
        UserInfo userInfo=JSON.parseObject(result, UserInfo.class);
        redisUtil.set(openId,result);
        return userInfo;
    }

    public String getAccessToken(String code){
        // 拼接请求地址
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?"
                + "appid=" + config.appId + "&secret="
                + config.appSecret
                + "&code=" + code
                + "&grant_type=authorization_code";
        String  result= HttpClientUtil.doGet(url);
        return result;
    }
}
