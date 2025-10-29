package cn.wxgis.jc.common.constant;

/**
 * 常量
 */
public class Constant {

    /**
     * 隐藏字符
     */
    public static final String HIDDEN = "******";

    /**
     * 空字符串
     */
    public static final String NULL = "null";

    /**
     * 空字符串
     */
    public static final String UNDEFINED = "undefined";

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";
    /**
     * 验证码有效期（分钟）
     */
    public static final long CAPTCHA_EXPIRATION = 2;

    /**
     * 分页最大显示条数，防止部分循环数据赋值卡死
     */
    public static final Integer DEFAULT_MAX_PAGE_SIZE = 80;

    /**
     * 默认系统管理员id
     */
    public static String DEFAULT_USER_ID = "1";

    /**
     * 返回列表数据和page保持统一
     */
    public static String resultKey = "records";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 注册
     */
    public static final String REGISTER = "Register";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 默认PID
     */
    public static final String DEFAULT_PID = "0";

    /**
     * 默认区划
     */
    public static final String DEFAULT_REGION_CODE = "320211";
    /**
     * 默认区划
     */
    public static final String DEFAULT_REGION_NAME = "滨湖区";

    /**
     * 排序字段
     */
    String ORDER_FIELD = "orderField";
    /**
     * 排序方式
     */
    String ORDER = "order";

    /**
     * 通用表字段常量
     */
    public interface ColumnField {
        //ID
        String ID = "id";
        //创建时间
        String CREATE_TIME = "create_time";
        //排序
        String ORDER_NUM = "order_num";
        //操作日志操作时间
        String OPER_TIME = "oper_time";
        String SELECT_TYPE = "select_type";
        String STATUS = "status";
        String NUMBER = "number";
        String SORT = "sort";
        String APPLYDATE = "apply_date";
        String APPEALTIME = "appeal_time";

        String SENDTIME = "send_time";

        String CYCLE_VALUE = "check_value";

        String SCORE= "score";

        String REGION_CODE = "region_code";

        String SELECT_DATE = "select_date";

        String LEVEL = "level";
    }

}
