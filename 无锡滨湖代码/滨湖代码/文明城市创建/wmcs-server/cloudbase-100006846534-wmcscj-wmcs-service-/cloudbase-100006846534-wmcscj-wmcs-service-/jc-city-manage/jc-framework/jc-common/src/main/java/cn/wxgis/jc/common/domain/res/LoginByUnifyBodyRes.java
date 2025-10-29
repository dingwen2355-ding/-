package cn.wxgis.jc.common.domain.res;

import lombok.Data;

@Data
public class LoginByUnifyBodyRes {

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 授权token
     */
    private String code;
}
