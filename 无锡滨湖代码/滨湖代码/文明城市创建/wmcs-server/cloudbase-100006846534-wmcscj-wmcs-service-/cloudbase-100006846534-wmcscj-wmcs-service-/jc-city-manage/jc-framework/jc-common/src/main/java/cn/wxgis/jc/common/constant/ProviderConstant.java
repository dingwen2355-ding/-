package cn.wxgis.jc.common.constant;

/**
 * 接口敞亮
 */
public interface ProviderConstant {

    /**
     * 远程调用公共前缀
     */
    String PROVIDER = "/provider";

    String USER_FIND_USERNAME = PROVIDER + "/user/findByUserName";
    String USER_SAVE = PROVIDER + "/user/save";

    String LOGININFOR_SAVE = PROVIDER + "/logininfor/save";

    String OPERLOG_SAVE = PROVIDER + "/operLog/save";

    String FILE_LIST_BYIDS = PROVIDER + "/file/listByIds";
    String FILE_DELETE = PROVIDER + "/file/delete";
    String FILE_DOWN_TI_FILE = PROVIDER + "/file/downTiFile";

    String CHECKITEM_FIND_BY_ID = PROVIDER + "/checkItem/findById";

    String CREATE_WORKFLOW = PROVIDER +  "/start/report";
    String FINISH_WORKFLOW = PROVIDER +  "/finish/report";
}
