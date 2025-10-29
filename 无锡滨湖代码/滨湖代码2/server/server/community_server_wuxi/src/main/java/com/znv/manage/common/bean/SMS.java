package com.znv.manage.common.bean;

import lombok.Data;

@Data
public class SMS {

    private String phone;//接收短信的电话号码，11位数字型字符串
    private String msg_content;//短消息内容，长度为：1至512个字符
    private String sub_port;//用户自行扩展的子端口号，长度为：0至5位数字型字符串
    /**
     * 0：预留
     * 1：实时（包含验证码、紧急预警、政务大厅叫号等）
     * 2：OA及流程跟踪类(包含会议通知、流程通知等)
     * 3：日常群发送(单日任务在10万条以内的短信)
     * 4：大规模群发(单日任务在10万以上的短信)
     */
    private String msg_type;
    /**
     * 短消息唯一编号（要求在用户自己系统内唯一），用户短消息成功状态的异步查询,长度要求不超过32个字符
     */
    private String msg_id;
}
