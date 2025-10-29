package com.znv.manage.common.bean.plan;

import lombok.Data;

/**
 * 时间类型标签
 *
 * @author chd
 * @date 2022/5/7
 */
@Data
public class LabelBean {
    String value;

    String label;

    public static LabelBean buildLabelBean(String value, String label) {
        LabelBean labelBean = new LabelBean();
        labelBean.setLabel(label);
        labelBean.setValue(value);
        return labelBean;
    }
}
