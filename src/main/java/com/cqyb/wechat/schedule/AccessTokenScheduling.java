package com.cqyb.wechat.schedule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cqyb.wechat.common.WeChatCommon;
import com.cqyb.wechat.config.WeChatConfig;
import com.cqyb.wechat.util.HttpClientUtil;
import com.cqyb.wechat.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Auther: yanbiao
 * @Date: 2019/10/18 16:26
 * @Description:
 */
@Component
public class AccessTokenScheduling {
    @Autowired
    private WeChatConfig config;
    @Autowired
    public RedisUtil redis;
    /**
     * 每两个小时更新一次accessToken
     * @return
     */
   @Scheduled(fixedRate = 2*60*60*1000)
//    @Scheduled(fixedRate = 2*60*1000)
    private void  setAccessToken(){
        String url=config.accessTokenUrl+"&appid="+config.appId+"&secret="+config.appSecret;
        String result= HttpClientUtil.doGet(url);
        JSONObject object= JSON.parseObject(result);
        String accessToken=object.getString("access_token");
        if(redis.hasKey(WeChatCommon.ACCESS_TOKEN)){
            redis.set(WeChatCommon.ACCESS_TOKEN,accessToken);
            System.out.println("access_token更新成功===========");
        }else{
            redis.set(WeChatCommon.ACCESS_TOKEN,accessToken);
            System.out.println("access_token新增成功===========");
        }
    }

}
