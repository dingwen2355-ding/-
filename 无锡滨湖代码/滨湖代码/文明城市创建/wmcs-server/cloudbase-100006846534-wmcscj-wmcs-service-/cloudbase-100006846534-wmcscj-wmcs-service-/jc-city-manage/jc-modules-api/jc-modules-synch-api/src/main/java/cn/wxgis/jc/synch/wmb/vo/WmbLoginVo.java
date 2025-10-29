package cn.wxgis.jc.synch.wmb.vo;

import lombok.Data;

@Data
public class WmbLoginVo {

    private String userid;

    private String username;

    private String avatar;

    private String phone;

    private String sex;

    private String email;

    private String mark;

    private String token;

    private WmbRoleVo role;

}
