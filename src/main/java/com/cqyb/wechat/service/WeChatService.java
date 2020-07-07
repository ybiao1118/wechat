package com.cqyb.wechat.service;

import java.util.Map;

/**
 * @Auther: yanbiao
 * @Date: 2019/9/18 21:11
 * @Description:
 */
public interface WeChatService {
    boolean checkSignature(String signature,String timestamp,String nonce);

}
