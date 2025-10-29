package com.znv.manage.common.bean.information;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

/**
 * 新增我的信息
 *
 * @author chd
 * @date 2022/6/6
 */
@Data
public class MesInfoBean {
    String id;

    String mesName;

    String typeId;

    String typeName;

    String relId;

    String relName;

    String relObjectId;

    String relObjectName;

    String relTime;

    String relPerson;

    String createId;

    String createName;

    String status;

    String process;

    String auditOpinion;

    String auditorId;

    String auditorName;

    String processTime;

    String createTime;

    String modifyTime;

    String remark;

    List<String> personIds;

    JSONObject jsonObject;

    String allPersonIds;

    String userId;
}
