package cn.wxgis.jc.common.constant;

public class CacheConstants {

    /**
     * 缓存有效期，默认720（分钟）
     */
    public final static long EXPIRATION = 1440;

    /**
     * 缓存刷新时间，默认120（分钟）
     */
    public final static long REFRESH_TIME = 120;

    /**
     * 远程调用过期事件
     */
    public final static Integer TIMEOUT = 60000;

    /**
     * 密码最大错误次数
     */
    public final static long PASSWORD_MAX_RETRY_COUNT = 10;

    /**
     * 密码锁定时间，默认10（分钟）
     */
    public final static long PASSWORD_LOCK_TIME = 10l;

    /**
     * 视频汇聚token存储
     */
    public static final String VIDEO_TOKEN = "video_token:";

    /**
     * 事件中心token存储
     */
    public static final String EVENT_CENTER_TOKEN = "event_center_token:";

    /**
     * 密码输入错误次数
     */
    public static final String USERINFO_INPUT_PWD_ERROR = "user_info_inputPwdError:";
    /**
     * 在线统计总数
     */
    public static final String USER_LOGIN_COUNT = "user_info_count:";

    /**
     * 权限缓存前缀
     */
    public final static String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 政务微信token
     */
    public final static String WECHAT_TOKEN_KEY = "wechat_login_tokens:";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * 事件编码 cache key
     */
    public static final String WF_EVENT_CODE_KEY = "wf_event_code:";


}
