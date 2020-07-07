package com.cqyb.wechat.controller;

import com.cqyb.wechat.common.MessageType;
import com.cqyb.wechat.config.WeChatConfig;
import com.cqyb.wechat.service.MessageService;
import com.cqyb.wechat.service.WeChatService;
import com.cqyb.wechat.util.XMLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @Auther: yanbiao
 * @Date: 2019/9/18 21:56
 * @Description:
 */
@RestController
@RequestMapping("/wx")
public class WeChatController {
    @Autowired
    private WeChatService weChatService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private  XMLUtil xmlUtil;
    @Autowired
    private WeChatConfig config;
    @GetMapping
    public void check(HttpServletRequest request,HttpServletResponse response) {
        System.out.println("success");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            if(weChatService.checkSignature(signature, timestamp, nonce)){
                out.write(echostr);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            out.close();
        }
    }

    @PostMapping
    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String message=null;
        try {
           //非加密模式
            if("false".equals(config.isEncryption)){
                Map<String,String> map = xmlUtil.xmlToMap(request);
                message=sendMessage(map);
                out = response.getWriter();
                out.write(message);
            }
            //加密模式
            if("true".equals(config.isEncryption)){
                //将微信请求xml转为map格式，获取所需的参数
                Map<String,String> map = xmlUtil.xmlToMapWithEncrypt(request);
                message=sendMessage(map);
                message=xmlUtil.mapToXmlWithEncrypt(message,request);
                out = response.getWriter();
                out.write(message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }

    }
    //发送不加密信息
    public String sendMessage( Map<String,String> map){
        String message="";
        String msgType = map.get("MsgType");//消息类型
        //事件消息
        if(MessageType.REQ_MESSAGE_TYPE_EVENT.equals(msgType)){
            String eventType = map.get("Event");//事件类型
            if(MessageType.EVENT_TYPE_SUBSCRIBE.equals(eventType)){//订阅事件
                message=messageService.subscribeForText(map);
            }else if(MessageType.EVENT_TYPE_UNSUBSCRIBE.equals(eventType)){//取消订阅事件
                System.out.println("用户："+ map.get("FromUserName") +"取消关注~");
            }

        }
        //普通文本事件
        if(MessageType.TEXT_MESSAGE.equals(msgType)){
            message=messageService.sendTextMessage(map);
        }
        return message;
    }
}
