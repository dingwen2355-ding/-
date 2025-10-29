package com.ruoyi.common.vo.cas.token;

import com.ruoyi.common.vo.CommonRes;
import lombok.Data;


@Data
public class GetTokenRes extends CommonRes {

    private String jti;

    private String accessToken;

    private UserInfo userInfo;

    /**
     * 用户信息
     */
    @Data
    public static class UserInfo {

        private String account;

        private String name;

        private String email;

        private String phone;

        private String exp;

        private String refreshToken;
    }
}
