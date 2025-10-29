package cn.wxgis.jc.common.util.page;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;

/**
 * 查询参数(分页)
 */
public class QueryExt<T> extends Page<T> {
    private static final String PAGE = "pageNum";
    private static final String LIMIT = "pageSize";
    public static final String ORDER_BY_FIELD = "orderByField";
    public static final String IS_ASC = "isAsc";

    //自定义sql需统一定义主表别名
    private static final String ALIAS = "master.";
    private static final String DEFAULT_ORDER = "update_time";

    private Map condition;

    public QueryExt(Map<String, Object> params) {
        // 分页参数处理
        super(Integer.parseInt(params.getOrDefault(PAGE, 1).toString())
                , Integer.parseInt(params.getOrDefault(LIMIT, 10).toString()));
        params.remove(PAGE);
        params.remove(LIMIT);
    }

    public QueryExt(Map<String, Object> params , String type) {
        // 分页参数处理
        super(Integer.parseInt(params.getOrDefault(PAGE, 1).toString())
                , Integer.parseInt(params.getOrDefault(LIMIT, 10).toString()));

        //排序条件 默认update_time desc;
        String orderByField = ALIAS + params.getOrDefault(ORDER_BY_FIELD, DEFAULT_ORDER).toString();
        Boolean isAsc = Boolean.parseBoolean(params.getOrDefault(IS_ASC, Boolean.FALSE).toString());
        OrderItem orderItem = null;
        if (isAsc) {
            orderItem = OrderItem.asc(orderByField);
        } else {
            orderItem = OrderItem.desc(orderByField);
        }
        this.addOrder(orderItem);
        params.remove(PAGE);
        params.remove(LIMIT);
        params.remove(ORDER_BY_FIELD);
        params.remove(IS_ASC);
        this.setCondition(params);
    }

    public Map getCondition() {
        return condition;
    }

    public void setCondition(Map condition) {
        this.condition = condition;
    }
}
