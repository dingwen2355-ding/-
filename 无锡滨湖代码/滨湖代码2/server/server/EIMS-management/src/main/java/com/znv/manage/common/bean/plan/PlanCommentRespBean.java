package com.znv.manage.common.bean.plan;

import com.github.pagehelper.PageInfo;
import lombok.Data;

/**
 * 预案评论回复
 *
 * @author chd
 * @date 2022/5/11
 */
@Data
public class PlanCommentRespBean {
    String id;

    String respContent;

    String operatorId;

    String operatorName;

    String recipientId;

    String recipientName;

    String createTime;

    String commentsId;

    String respType;

    String planNumId;

    PageInfo<PlanCommentRespBean> planCommentResp;
}
