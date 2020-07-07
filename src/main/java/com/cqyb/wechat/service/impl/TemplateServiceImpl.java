package com.cqyb.wechat.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cqyb.wechat.common.WeChatCommon;
import com.cqyb.wechat.config.WeChatConfig;
import com.cqyb.wechat.entity.template.BaseTemplate;
import com.cqyb.wechat.entity.template.MusicTemplate;
import com.cqyb.wechat.entity.template.TemplateData;
import com.cqyb.wechat.service.TemplateService;
import com.cqyb.wechat.util.HttpClientUtil;
import com.cqyb.wechat.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Auther: yanbiao
 * @Date: 2019/10/19 19:54
 * @Description:
 */
@Service
public class TemplateServiceImpl implements TemplateService {
    @Autowired
    private WeChatConfig config;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public void sendMusicTemplate(Map<String, String> map) {
        TemplateData userName=new TemplateData();
        userName.setValue(map.get("userName"));
        userName.setColor("#0000FF");
        TemplateData music=new TemplateData();
        music.setValue(map.get("music"));
        music.setColor("#0000FF");
        TemplateData url=new TemplateData();
        url.setValue(map.get("url"));
        url.setColor("#0000FF");

        MusicTemplate musicTemplate=new MusicTemplate();
        musicTemplate.setUserName(userName);
        musicTemplate.setMusic(music);
        musicTemplate.setUrl(url);

        BaseTemplate template=new BaseTemplate();
        template.setTouser("oMOTR59ik4OuU0d2Ji6tUDcr1tJw");
        template.setUrl("www.baidu.com");
        template.setTopcolor("#000000");
        template.setTemplate_id(config.musicTemplateId);
        template.setData(musicTemplate);
        // 把template对象转为JSON字符串格式，作为POST的参数
        String jsonParam = JSON.toJSONString(template);
        // 从redis中获取token
        String token=redisUtil.get(WeChatCommon.ACCESS_TOKEN).toString();
        String result=HttpClientUtil.doPostJson(config.sendTemplateUrl+token,jsonParam);
        JSONObject json= JSON.parseObject(result);
        String errCode=json.get("errcode").toString();
        if("0".equals(errCode)){
            System.out.println("模版消息发送成功===========");
        }else{
            System.out.println("模版消息发送失败===========");
        }
    }
}
