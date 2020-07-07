package com.cqyb.wechat.util;

import com.cqyb.wechat.config.WeChatConfig;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: yanbiao
 * @Date: 2019/9/19 09:29
 * @Description:xml工具类
 */
@Component
public class XMLUtil {

    @Autowired
    private WeChatConfig config;
    /**
     * 将xml转为map
     * @param request
     * @return
     */
    public  Map<String,String> xmlToMap(HttpServletRequest request){
        Map<String,String> map = new HashMap<String,String>();
        SAXReader reader = new SAXReader();
        InputStream in = null;
        try {
            in = request.getInputStream();
            Document doc = reader.read(in);
            Element root = doc.getRootElement();
            List<Element> list = root.elements();
            for (Element element : list) {
                map.put(element.getName(), element.getText());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return map;
    }
    /**
     * 将map转为xml
     * @param map
     * @return
     */
    public  String mapToXml(Map<String,String> map){
       return "";
    }

    /**
     * 将加密xml转为map
     * @param request
     * @return
     */
    public  Map<String,String> xmlToMapWithEncrypt(HttpServletRequest request){
        Map<String,String> map = new HashMap<String,String>();
        Map<String,String> paramMap=xmlToMap(request);
        String msg_signature = request.getParameter("msg_signature");
//        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String toUserName = paramMap.get("ToUserName");
        String encrypt = paramMap.get("Encrypt");
        /* 拼接格式传入解密方法进行解密 */
        StringBuffer fromXML = new StringBuffer();
        fromXML.append("<xml><ToUserName><![CDATA[").append(toUserName)
                .append("]]></ToUserName><Encrypt><![CDATA[")
                .append(encrypt)
                .append("]]></Encrypt></xml>");

        /* 把加密的XML进行解密返回一个解密后的XML字符串 */
        WXBizMsgCrypt pc = null;
        try {
            pc = new WXBizMsgCrypt(config.token, config.aesKey, config.appId);
            String decrypXML = pc.decryptMsg(msg_signature, timestamp, nonce,
                    fromXML.toString());
            /* 把解密后的XML字符串转换为Map */
            Document parseDoc = DocumentHelper.parseText(decrypXML.toString());
            Element parseRoot = parseDoc.getRootElement();
            List<Element> parseElements = parseRoot.elements();
            for (Element element : parseElements) {
                map.put(element.getName(), element.getText());
            }

        } catch (AesException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return map;
    }
    /**
     * 将map转为xml，并加密
     * @param map
     * @return
     */
    public  String mapToXmlWithEncrypt(String replyMsg,HttpServletRequest request){
        String mingwen = null;
        try {
            WXBizMsgCrypt pc = new WXBizMsgCrypt(config.token, config.aesKey, config.appId);
            String timestamp=request.getParameter("timestamp");
            String nonce=request.getParameter("nonce");
            mingwen = pc.encryptMsg(replyMsg, timestamp, nonce);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mingwen;
    }

}
