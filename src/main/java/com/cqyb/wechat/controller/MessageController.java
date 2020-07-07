package com.cqyb.wechat.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cqyb.wechat.common.WeChatCommon;
import com.cqyb.wechat.entity.result.JsonResult;
import com.cqyb.wechat.entity.result.OkResult;
import com.cqyb.wechat.entity.user.UserInfo;
import com.cqyb.wechat.service.MessageService;
import com.cqyb.wechat.service.UserService;
import com.cqyb.wechat.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;


import java.util.List;


/**
 * @Auther: yanbiao
 * @Date: 2019/10/9 21:26
 * @Description:
 */
@Api(value = "消息管理", tags = {"消息管理"})
@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;
//    @GetMapping("/userInfo")
//    public String test(HttpServletRequest request, Model model){
//        Cookie[] cookies=request.getCookies();
//        String openId=null;
//        UserInfo userInfo=null;
//        if(null!=cookies){
//            for(Cookie cookie:cookies){
//                if(cookie.getName().equals("openId")){
//                    openId = cookie.getValue();
//                }
//            }
//        }
//
//        String result=redisUtil.get(openId).toString();
//        if(result!=null){
//             userInfo=JSON.parseObject(result, UserInfo.class);
//             model.addAttribute("user",userInfo);
//
//        }
//        return "success";
//    }
    @ApiOperation(value = "群发文本消息")
    @PostMapping("/sendTextMsg")
    public JsonResult sendTextMsg(String content){
        List<String> list=userService.getUserOpenIdList();
        String result=messageService.sendTextToOpenid(list,content);
        if(null==result){
            return new OkResult(null);
        }
        JSONObject object= JSON.parseObject(result);
        return new OkResult(object);

    }
    @ApiOperation(value = "群发图文消息")
    @PostMapping("/sendTextAndImageMsg")
    public JsonResult sendTextAndImageMsg(){
        List<String> list=userService.getUserOpenIdList();
        String mediaId=redisUtil.get(WeChatCommon.WELCOME_IMAGE_AND_TEXT).toString();
        String result=messageService.sendTextAndImageToOpenid(list,mediaId);
        if(null==result){
            return new OkResult(null);
        }
        JSONObject object= JSON.parseObject(result);
        return new OkResult(object);

    }
}
