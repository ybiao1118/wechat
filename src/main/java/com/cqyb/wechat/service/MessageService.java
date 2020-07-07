package com.cqyb.wechat.service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: yanbiao
 * @Date: 2019/9/19 22:03
 * @Description:
 */
public interface MessageService {
    /**
     * 将消息转发至客服系统
     *
     * @param map
     * @return
     */
    public String sendTextMessage(Map<String, String> map);

    /**
     * 发送一个关注微信的文字欢迎信息
     *
     * @param map
     * @return
     */
    public String subscribeForText(Map<String, String> map);

    /**
     * 根据openId，群发文本消息
     * @param list
     * @return
     */
    public String sendTextToOpenid(List<String> list,String content);

    /**
     * 根据openId，群发图文消息
     *
     * @param list
     * @param mediaId
     * @return
     */
    public String sendTextAndImageToOpenid(List<String> list,String mediaId);
}
