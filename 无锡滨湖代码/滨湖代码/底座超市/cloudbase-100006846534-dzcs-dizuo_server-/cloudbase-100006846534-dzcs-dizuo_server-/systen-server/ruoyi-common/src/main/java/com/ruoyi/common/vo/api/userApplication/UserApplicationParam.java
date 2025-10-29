package com.ruoyi.common.vo.api.userApplication;

import lombok.Data;

/**
 * 获取用户授权的应用信息
 */
@Data
public class UserApplicationParam {


    private String token;

    /**
     * 账号
     */
    private String account;
}
