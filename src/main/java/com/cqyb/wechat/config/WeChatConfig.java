package com.cqyb.wechat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Auther: yanbiao
 * @Date: 2019/9/18 21:29
 * @Description:
 */
@Configuration
@PropertySource("classpath:wx.properties")
public class WeChatConfig {
    @Value("${wx.token}")
    public String token;
    @Value("${wx.appId}")
    public String appId;
    @Value("${wx.appSecret}")
    public String appSecret;
    @Value("${wx.aesKey}")
    public String aesKey;
    @Value("${wx.mchId}")
    public String mchId;
    @Value("${wx.accessTokenUrl}")
    public String accessTokenUrl;
    @Value("${wx.createMenuUrl}")
    public String createMenuUrl;
    @Value("${wx.isEncryption}")
    public String isEncryption;
    @Value("${wx.sendTemplateUrl}")
    public String sendTemplateUrl;
    @Value("${wx.musicTemplateId}")
    public String musicTemplateId;

}
