package com.cqyb.wechat.entity.template;

import lombok.Data;

import java.util.Map;

/**
 * @Auther: yanbiao
 * @Date: 2019/10/19 19:28
 * @Description:
 */
@Data
public class BaseTemplate {
    private String template_id;//模板ID
    private String touser;//目标客户
    private String url;//用户点击模板信息的跳转页面
    private String topcolor;//字体颜色
    private TemplateInterface data;//模板里的数据

}
