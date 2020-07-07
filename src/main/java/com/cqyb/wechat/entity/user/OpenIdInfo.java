package com.cqyb.wechat.entity.user;

import lombok.Data;

import java.util.List;

/**
 * @Auther: yanbiao
 * @Date: 2019/10/21 11:32
 * @Description:
 */
@Data
public class OpenIdInfo {
    private List<String> openid;
}
