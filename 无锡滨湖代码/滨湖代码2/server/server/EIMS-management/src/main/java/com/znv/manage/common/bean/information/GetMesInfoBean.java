package com.znv.manage.common.bean.information;

import lombok.Data;

/**
 * 查询我的信息
 *
 * @author chd
 * @date 2022/6/6
 */
@Data
public class GetMesInfoBean {
    String text;

    /**
     * 信息状态：0：草稿 1：待审核 2：通过 3: 退回，可传多个用逗号分隔
     */
    String status;

    String typeId;

    String relBeginTime;

    String relEndTime;

    Integer pageNum;

    Integer pageSize;
}
