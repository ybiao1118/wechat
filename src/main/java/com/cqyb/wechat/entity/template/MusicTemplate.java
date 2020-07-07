package com.cqyb.wechat.entity.template;

import lombok.Data;

/**
 * @Auther: yanbiao
 * @Date: 2019/10/19 19:37
 * @Description:
 */
@Data
public class MusicTemplate implements  TemplateInterface {
    private TemplateData userName;
    private TemplateData music;
    private TemplateData url;
}
