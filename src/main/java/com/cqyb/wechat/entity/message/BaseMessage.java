package com.cqyb.wechat.entity.message;

import lombok.Data;

/**
 * @Auther: yanbiao
 * @Date: 2019/9/19 09:24
 * @Description:微信消息基类
 */
@Data
public abstract class BaseMessage {
    // 开发者微信号
    private String ToUserName;
    // 发送方帐号（一个OpenID）
    private String FromUserName;
    // 消息创建时间 （整型）
    private long CreateTime;
    // 消息id，64位整型
    private String MsgType;
}
