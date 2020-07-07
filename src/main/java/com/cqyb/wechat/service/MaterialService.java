package com.cqyb.wechat.service;

import com.cqyb.wechat.entity.material.ImageAndTextMaterial;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Auther: yanbiao
 * @Date: 2019/10/22 09:24
 * @Description:
 */
public interface MaterialService {
    //上传图片信息到微信素材库
    String uploadImage(MultipartFile multipartFile);
    //上传图文素材到素材库
    String uploadImageAndText(ImageAndTextMaterial material);
}
