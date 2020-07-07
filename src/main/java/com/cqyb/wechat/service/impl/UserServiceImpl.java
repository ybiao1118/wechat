package com.cqyb.wechat.service.impl;

import com.alibaba.fastjson.JSON;
import com.cqyb.wechat.common.WeChatCommon;
import com.cqyb.wechat.config.WeChatConfig;
import com.cqyb.wechat.entity.user.OpenIdInfo;
import com.cqyb.wechat.entity.user.UserOpenIdList;
import com.cqyb.wechat.service.UserService;
import com.cqyb.wechat.util.HttpClientUtil;
import com.cqyb.wechat.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: yanbiao
 * @Date: 2019/10/21 11:39
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private WeChatConfig config;
    public  List<String> getUserOpenIdList(){
        List<String> resultList=new ArrayList<>();
        //用户openid列表信息
        UserOpenIdList openIdListInfo = null;
        String nextOpenid=null;
        String accessToken=redisUtil.get(WeChatCommon.ACCESS_TOKEN).toString();
        synchronized(this){
            try {
                //循环获取用户openid列表
                do{
                    //微信公众号获取用户列表信息接口地址
                    String requestUrl = null;
                    if(StringUtils.isBlank(nextOpenid)){
                        requestUrl = new StringBuffer().append("https://api.weixin.qq.com/cgi-bin/user/get?access_token=").append(accessToken).toString();
                    }else {
                        requestUrl = new StringBuffer().append("https://api.weixin.qq.com/cgi-bin/user/get?access_token=")
                                .append(accessToken).append("&next_openid=").append(nextOpenid).toString();
                    }
                    String result= HttpClientUtil.doGet(requestUrl);
                    openIdListInfo= JSON.parseObject(result,UserOpenIdList.class);
                    if(openIdListInfo != null && openIdListInfo.getErrcode() == 0){
                        //获取用户openid列表对象
                        OpenIdInfo openIdInfo = openIdListInfo.getData();
                        if(openIdInfo != null){
                            List<String> openids = openIdInfo.getOpenid();
                            if(openids != null && openids.size() > 0){
                                for (String openid : openids) {
                                    //生成数据的内容
                                    resultList.add(openid);
                                }
                            }
                            //拉取列表的最后一个用户的OPENID
                            nextOpenid = openIdListInfo.getNext_openid();
                        }
                    }
                }
                while (openIdListInfo.getCount() == 10000);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
       redisUtil.set(WeChatCommon.USER_OPENID_LIST,resultList.toString());
        return resultList;
    }
}
