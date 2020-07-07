package com.cqyb.wechat.service;

import com.cqyb.wechat.entity.template.BaseTemplate;

import java.util.Map;

/**
 * @Auther: yanbiao
 * @Date: 2019/10/19 19:53
 * @Description:
 */
public interface TemplateService {
    void sendMusicTemplate(Map<String,String> map);
}
