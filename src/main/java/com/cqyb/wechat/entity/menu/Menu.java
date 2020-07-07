package com.cqyb.wechat.entity.menu;

import lombok.Data;

/**
 * @Auther: yanbiao
 * @Date: 2019/10/18 20:00
 * @Description:
 */
@Data
public class Menu {
    // 一级菜单数组，个数应为1~3个
    private BaseButton[] button;
}
