package com.cqyb.wechat.entity.user;

import lombok.Data;

/**
 * @Auther: yanbiao
 * @Date: 2019/10/21 11:31
 * @Description:
 */
@Data
public class UserOpenIdList {
    private Integer total;//关注该公众账号的总用户数

    private Integer count;//拉取的OPENID个数，最大值为10000

    private OpenIdInfo data;//列表数据，OPENID的列表

    private String next_openid;//拉取列表的最后一个用户的OPENID

    private int errcode;//错误编码

    private String errmsg="ok";//错误提示
}
