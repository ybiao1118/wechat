package com.cqyb.wechat.entity.menu;

import lombok.Data;

/**
 * @Auther: yanbiao
 * @Date: 2019/10/18 19:59
 * @Description:
 */
@Data
public class ViewButton extends BaseButton  {
    // 网页 链接，用户点击菜单可打开链接，不超过1024字节。 type为miniprogram时，不支持小程序的老版本客户端将打开本url。
    private String url;

    public ViewButton(){
        this.setType("view");
    }

}
