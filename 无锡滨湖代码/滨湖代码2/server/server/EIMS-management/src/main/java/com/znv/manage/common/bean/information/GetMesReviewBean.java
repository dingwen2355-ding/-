package com.znv.manage.common.bean.information;

import lombok.Data;

/**
 * 查询状态巡检
 *
 * @author chd
 * @date 2022/6/1
 */
@Data
public class GetMesReviewBean {
    String relName;

    String beginTime;

    String endTime;

    Integer pageNum;

    Integer pageSize;
}
