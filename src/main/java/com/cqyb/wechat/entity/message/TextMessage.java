package com.cqyb.wechat.entity.message;

import com.cqyb.wechat.common.MessageType;
import com.cqyb.wechat.entity.message.BaseMessage;
import lombok.Data;

/**
 * @Auther: yanbiao
 * @Date: 2019/9/19 09:25
 * @Description:
 */
@Data
public class TextMessage extends BaseMessage {
    private String Content;
    private String MsgId;
}
