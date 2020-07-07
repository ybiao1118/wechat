package com.cqyb.wechat.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cqyb.wechat.common.MessageType;
import com.cqyb.wechat.common.WeChatCommon;
import com.cqyb.wechat.entity.message.TextMessage;
import com.cqyb.wechat.service.MessageService;
import com.cqyb.wechat.util.HttpClientUtil;
import com.cqyb.wechat.util.RedisUtil;
import com.thoughtworks.xstream.XStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: yanbiao
 * @Date: 2019/9/19 22:03
 * @Description:
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public String sendTextMessage(Map<String, String> map) {
        System.out.println("用户："+ map.get("FromUserName") +"发送消息");
        return setMessage(map,"测试内容");
    }

    @Override
    public String subscribeForText(Map<String, String> map) {
        System.out.println("用户："+ map.get("FromUserName") +"关注~");
        return setMessage(map,"欢迎关注，精彩内容不容错过");
    }

    @Override
    public String sendTextToOpenid(List<String> list,String content) {
        String accessToken=redisUtil.get(WeChatCommon.ACCESS_TOKEN).toString();
        String url="https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token="+accessToken;
        JSONObject jsonObject=new JSONObject();
        JSONObject contentJson=new JSONObject();
        contentJson.put("content",content);
        jsonObject.put("touser",list);
        jsonObject.put("msgtype","text");
        jsonObject.put("text",contentJson);
        String result= HttpClientUtil.doPostJson(url,jsonObject.toJSONString());
        JSONObject object= JSON.parseObject(result);
        if("0".equals(object.get("errcode"))){
            System.out.println("群发消息成功");
        }else{
            System.out.println("群发消息失败");
        }
        return result;
    }

    @Override
    public String sendTextAndImageToOpenid(List<String> list, String mediaId) {
        String accessToken=redisUtil.get(WeChatCommon.ACCESS_TOKEN).toString();
        String url="https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token="+accessToken;
        JSONObject jsonObject=new JSONObject();
        JSONObject mpnewsJson=new JSONObject();
        mpnewsJson.put("media_id",mediaId);

        jsonObject.put("touser",list);
        jsonObject.put("msgtype","mpnews");
        jsonObject.put("mpnews",mpnewsJson);
        jsonObject.put("send_ignore_reprint",0);
        String result= HttpClientUtil.doPostJson(url,jsonObject.toJSONString());
        JSONObject object= JSON.parseObject(result);
        if("0".equals(object.get("errcode"))){
            System.out.println("群发图文消息成功");
        }else{
            System.out.println("群发图文消息失败");
        }
        return result;
    }


    public String setMessage(Map<String, String> map,String text){
        TextMessage msg = new TextMessage();
        msg.setFromUserName(map.get("ToUserName"));
        msg.setToUserName(map.get("FromUserName"));
        msg.setMsgType(MessageType.TEXT_MESSAGE);
        msg.setCreateTime(new Date().getTime());
        msg.setContent(text);
        XStream xstream  = new XStream();
        xstream.alias("xml", msg.getClass());
        return xstream.toXML(msg);
    }
}
