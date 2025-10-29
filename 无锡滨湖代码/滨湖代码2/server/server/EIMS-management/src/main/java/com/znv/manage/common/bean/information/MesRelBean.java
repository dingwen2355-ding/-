package com.znv.manage.common.bean.information;

import lombok.Data;

/**
 * 信息发布方式
 *
 * @author chd
 * @date 2022/6/1
 */
@Data
public class MesRelBean {
    String id;

    String relName;

    String typeId;

    String typeName;

    String createId;

    String createName;

    String createTime;

    String modifyTime;

    String remark;
}
