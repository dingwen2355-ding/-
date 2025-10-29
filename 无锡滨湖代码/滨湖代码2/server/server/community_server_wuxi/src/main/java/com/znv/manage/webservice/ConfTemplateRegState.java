
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ConfTemplateRegState�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="ConfTemplateRegState">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="RegGkFailed"/>
 *     &lt;enumeration value="RegGkSuccess"/>
 *     &lt;enumeration value="NotNeedReg"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ConfTemplateRegState")
@XmlEnum
public enum ConfTemplateRegState {

    @XmlEnumValue("RegGkFailed")
    REG_GK_FAILED("RegGkFailed"),
    @XmlEnumValue("RegGkSuccess")
    REG_GK_SUCCESS("RegGkSuccess"),
    @XmlEnumValue("NotNeedReg")
    NOT_NEED_REG("NotNeedReg");
    private final String value;

    ConfTemplateRegState(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ConfTemplateRegState fromValue(String v) {
        for (ConfTemplateRegState c: ConfTemplateRegState.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
