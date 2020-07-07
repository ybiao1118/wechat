package com.cqyb.wechat.entity.result;

/**
 * @Auther: yanbiao
 * @Date: 2019/10/22 16:52
 * @Description:
 */
public class OkResult extends JsonResult {
    public OkResult() {
        super(200, (String) null, (Object) null);
    }

    public OkResult(Object data) {
        super(200, (String) null, data);
    }

    public OkResult(String message, Object data) {
        super(200, message, data);
    }
}