package com.znv.manage.common.bean.information;

import lombok.Data;

/**
 * 审核我的信息
 *
 * @author chd
 * @date 2022/6/6
 */
@Data
public class AuditMesBean {
    String id;

    String status;

    String auditorId;

    String auditorName;

    String auditOpinion;

    String processTime;

    String userId;

    String mesName;
}
