package cn.wxgis.jc.synch.video.vo;

import lombok.Data;

@Data
public class AuthorizeTokenVo {

    /**
     * 有效时间，由服务端指定，单位秒，建议3/4 duration时就进行会话更新
     */
    private int duration;

    /**
     * 返回登陆令牌，令牌字符串由服务端发布。之后的其它请求，在HTTP头的 X-Subject-Token: 带上这个令牌进行鉴权
     */
    private String token;

    /**
     * 创建该会话的用户名
     */
    private String userName;

    /**
     * 。用户名对应的用户Id，如果需要对该用户信息进行修改，使用用户Id
     */
    private String userId;

    /**
     * 上次登陆的IP
     */
    private String lastLoginIp;

    /**
     * 视频云的版本
     */
    private String version;

}
