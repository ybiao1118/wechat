package com.cqyb.wechat.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cqyb.wechat.common.WeChatCommon;
import com.cqyb.wechat.entity.material.ImageAndTextMaterial;
import com.cqyb.wechat.service.MaterialService;
import com.cqyb.wechat.util.CheckFileTypeUtil;
import com.cqyb.wechat.util.FileUtil;
import com.cqyb.wechat.util.HttpClientUtil;
import com.cqyb.wechat.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @Auther: yanbiao
 * @Date: 2019/10/22 09:24
 * @Description:
 */
@Service
public class MaterialServiceImpl implements MaterialService {
    private final static String IMAGE_TYPE="image";
    private final static String VOICE_TYPE="voice";
    private final static String VIDEO_TYPE="video";
    private final static String THUMB_TYPE="thumb";
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public String uploadImage(MultipartFile multipartFile) {
        String accessToken=redisUtil.get(WeChatCommon.ACCESS_TOKEN).toString();
        String url="https://api.weixin.qq.com/cgi-bin/material/add_material?access_token="
                +accessToken+"&type="+IMAGE_TYPE;
        String result=null;
        try {
            File file= FileUtil.multipartFileToFile(multipartFile);
            if(checkFileSize(file,IMAGE_TYPE)){
                result= HttpClientUtil.uploadFile(file,url);
                JSONObject jsonObject= JSON.parseObject(result);
                if(null!=jsonObject.get("errcode")){
                    System.out.println("上传素材失败!");
                }else{
                    if(null!=jsonObject.get("media_id")){
                        redisUtil.set(WeChatCommon.WELCOME_IMAGE,jsonObject.get("media_id"));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String uploadImageAndText(ImageAndTextMaterial material) {
//        String mediaId=redisUtil.get("").toString();
        String accessToken=redisUtil.get(WeChatCommon.ACCESS_TOKEN).toString();
        String url="https://api.weixin.qq.com/cgi-bin/material/add_news?access_token="+accessToken;
        JSONObject json1=new JSONObject();
        JSONObject json2=new JSONObject();
        json2.put("title",material.getTitle());
        json2.put("thumb_media_id",material.getThumb_media_id());
        json2.put("show_cover_pic","1");
        json2.put("content",material.getContent());
        JSONArray jsonArray=new JSONArray();
        jsonArray.add(json2);
        json1.put("articles",jsonArray);
        String result=HttpClientUtil.doPostJson(url,json1.toJSONString());
        JSONObject resultJson=JSON.parseObject(result);
        if(null!=resultJson.get("media_id")){
            redisUtil.set(WeChatCommon.WELCOME_IMAGE_AND_TEXT,resultJson.get("media_id"));
        }
        return result;
    }

    //检验上传文件类型和大小，是否符合微信上传规定
    private boolean checkFileSize(File file,String type){
        boolean flag=false;
        String fileType= CheckFileTypeUtil.getFileType(file);
        if(("bmp"==fileType||"png"==fileType||"jpeg"==fileType||"jpg"==fileType||"gif"==fileType)&&type==IMAGE_TYPE){
            flag=FileUtil.checkFileSize(file.length(),2,"M");
        }
        if(("mp3"==fileType||"wma"==fileType||"wav"==fileType||"amr"==fileType)&&type==VOICE_TYPE){
            flag=FileUtil.checkFileSize(file.length(),2,"M");
        }
        if("MP4"==fileType&&type==VIDEO_TYPE){
            flag=FileUtil.checkFileSize(file.length(),10,"M");
        }
        if("jpg"==fileType&&type==THUMB_TYPE){
            flag=FileUtil.checkFileSize(file.length(),64,"K");
        }
        return flag;
    }
}
