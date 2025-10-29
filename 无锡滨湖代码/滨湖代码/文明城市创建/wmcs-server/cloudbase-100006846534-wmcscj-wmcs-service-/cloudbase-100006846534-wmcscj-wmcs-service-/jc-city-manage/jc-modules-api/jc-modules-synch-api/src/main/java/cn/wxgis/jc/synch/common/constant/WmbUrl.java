package cn.wxgis.jc.synch.common.constant;

public class WmbUrl {

    //获取周期
    public static String getCycleUrl = "/DCTB/api/check.ashx?action=list&name=&pageIndex=1&pageSize=10000&orderField=";

    //获取点位类型
    public static String getLoginUrl = "/api/user.ashx?action=login";

    //获取点位类型
    public static String getPointTypeListUrl = "/api/pointType.ashx?action=list&name=&pageIndex=1&pageSize=10000&orderField=";

    //获取点位类型下审核问题
    public static String getTypeCheckItemListUrl ="/api/typeCheckItem.ashx?action=list&name=&pageIndex=1&pageSize=10000&orderField=&typeid=%s";

    //获取点位
    public static String getCheckPointListUrl = "/DCTB/api/CheckPointDetail.ashx?action=GETPOINTS";

    //获取事件列表
    public static String getEventListUrl = "/DCTB/api/CheckPointDetail.ashx?action=checkList&state=&score=&checkid=&regionid=&departids=&typeid=&checkItemid=&pointName=&checkInfo=&isPublic=&pageIndex=%s&pageSize=%s&orderField=&typeName=";

}
