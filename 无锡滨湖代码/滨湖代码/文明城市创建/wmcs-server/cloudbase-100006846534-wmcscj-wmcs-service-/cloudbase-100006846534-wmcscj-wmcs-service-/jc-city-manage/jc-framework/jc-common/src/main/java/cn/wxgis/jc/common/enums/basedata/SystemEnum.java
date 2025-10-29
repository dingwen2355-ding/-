package cn.wxgis.jc.common.enums.basedata;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SystemEnum {

    /**
     * 登录登出枚举
     */
    @Getter
    @AllArgsConstructor
    public enum LoginEnum {
        LOGOUT(0, "登出"),
        SYSTEM_LOGIN(1, "系统登录"),
        THIRD_LOGIN(2, "授权登录"),
        WECHAT_LOGIN(3, "政务微信登陆");

        private Integer code;
        private String value;
    }

}
