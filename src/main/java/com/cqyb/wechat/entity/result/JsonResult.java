package com.cqyb.wechat.entity.result;

import java.io.Serializable;

/**
 * @Auther: yanbiao
 * @Date: 2019/10/22 16:51
 * @Description:
 */
public class JsonResult implements Serializable {
    public static final int OK_STATUS = 200;
    public static final int BAD_REQUEST_STATUS = 400;
    public static final int UNAUTHORIZED_STATUS = 401;
    public static final int FORBIDDEN_STATUS = 403;
    public static final int NOT_FOUND_STATUS = 404;
    public static final int TOO_MANY_REQUESTS_STATUS = 429;
    public static final int INTERVAL_SERVER_ERROR_STATUS = 500;
    private int status;
    private String message;
    private Object data;

    public int getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    public Object getData() {
        return this.data;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public JsonResult(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
