package com.znv.manage.common.bean.plan;

import com.github.pagehelper.PageInfo;
import lombok.Data;

/**
 * 预案评论
 *
 * @author chd
 * @date 2022/5/11
 */
@Data
public class PlanCommentBean {
    String id;

    String planNumId;

    String content;

    String operatorId;

    String operatorName;

    String createTime;

    PageInfo<PlanCommentRespBean>  planCommentResp;
}
