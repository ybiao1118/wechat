package com.cqyb.wechat.entity.menu;

import lombok.Data;

/**
 * @Auther: yanbiao
 * @Date: 2019/10/18 19:57
 * @Description:
 */
@Data
public class BaseButton {
    // 菜单的响应动作类型，view表示网页类型，click表示点击类型，miniprogram表示小程序类型
    private String type;
    // 菜单标题，不超过16个字节，子菜单不超过60个字节
    private String name;
    // 二级菜单数组，个数应为1~5个
    private BaseButton[] sub_button;
}
