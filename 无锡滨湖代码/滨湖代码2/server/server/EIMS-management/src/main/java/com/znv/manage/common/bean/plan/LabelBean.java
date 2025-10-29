package com.znv.manage.common.bean.plan;

import lombok.Data;

import java.util.List;

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

    List<LabelBean> children;

    public static LabelBean buildLabelBean(String value, String label) {
        LabelBean labelBean = new LabelBean();
        labelBean.setLabel(label);
        labelBean.setValue(value);
        return labelBean;
    }
}
