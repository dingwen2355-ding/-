package com.znv.manage.common.bean.plan;

import lombok.Data;

/**
 * 查询预案评论
 *
 * @author chd
 * @date 2022/5/13
 */
@Data
public class QueryPlanCommentBean {
    /**
     * 当respType为1时，id为预案id， 当为2时表示评论的主键id
     */
    String id;

    /**
     * 1:预案评论 2:预案评论回复下的评论, 3:预案评论的回复下的回复
     */
    String respType;

    Integer pageNum = 1;

    Integer pageSize = 10;
}
