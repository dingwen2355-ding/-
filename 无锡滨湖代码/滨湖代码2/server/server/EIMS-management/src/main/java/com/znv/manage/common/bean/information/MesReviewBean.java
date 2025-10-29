package com.znv.manage.common.bean.information;

import lombok.Data;

/**
 * 状态巡检
 *
 * @author chd
 * @date 2022/6/1
 */
@Data
public class MesReviewBean {
    String id;

    String relId;

    String relName;

    String adviceId;

    String adviceName;

    String status;

    String typeName;

    String reviewTime;

    String remark;
}
