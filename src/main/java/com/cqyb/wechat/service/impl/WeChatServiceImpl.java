package com.cqyb.wechat.service.impl;

import com.cqyb.wechat.config.WeChatConfig;
import com.cqyb.wechat.service.WeChatService;
import com.cqyb.wechat.util.SHA1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

/**
 * @Auther: yanbiao
 * @Date: 2019/9/18 21:11
 * @Description:
 */
@Service
public class WeChatServiceImpl implements WeChatService {
    @Autowired
    private WeChatConfig config;

    @Override
    public boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] str = new String[]{config.token,timestamp,nonce};
        StringBuffer buffer = new StringBuffer();
        if(str!=null){
            //排序
            Arrays.sort(str);
            //拼接字符串
            for(int i =0 ;i<str.length;i++){
                buffer.append(str[i]);
            }
        }
        //进行sha1加密
        String temp = SHA1.encode(buffer.toString());
        //与微信提供的signature进行匹对
        return signature.equals(temp);

    }

}
