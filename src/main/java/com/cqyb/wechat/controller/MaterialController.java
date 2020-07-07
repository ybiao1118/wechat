package com.cqyb.wechat.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cqyb.wechat.common.WeChatCommon;
import com.cqyb.wechat.entity.material.ImageAndTextMaterial;
import com.cqyb.wechat.entity.result.JsonResult;
import com.cqyb.wechat.entity.result.OkResult;
import com.cqyb.wechat.service.MaterialService;
import com.cqyb.wechat.util.RedisUtil;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Auther: yanbiao
 * @Date: 2019/10/22 16:43
 * @Description:
 */
@Api(value = "文件管理", tags = {"文件管理"})
@RestController
@RequestMapping("/file")
public class MaterialController {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private MaterialService materialService;

    @ApiOperation(value = "上传欢迎图片素材")
    @PostMapping("/uploadWelcomImage")
    public JsonResult uploadWelcomImage(@RequestParam(value = "image", required = false) MultipartFile image) {
        String result= materialService.uploadImage(image);
        if(null==result){
            return new OkResult(null);
        }
        JSONObject object= JSON.parseObject(result);
        return new OkResult(object);
    }
    @ApiOperation(value = "上传欢迎图文素材")
    @PostMapping("/uploadWelcomImageAndText")
    public JsonResult uploadWelcomImageAndText(ImageAndTextMaterial material) {
        String mediaId=redisUtil.get(WeChatCommon.WELCOME_IMAGE).toString();
        material.setThumb_media_id(mediaId);
        String result= materialService.uploadImageAndText(material);
        if(null==result){
            return new OkResult(null);
        }
        JSONObject object= JSON.parseObject(result);
        return new OkResult(object);
    }
}
