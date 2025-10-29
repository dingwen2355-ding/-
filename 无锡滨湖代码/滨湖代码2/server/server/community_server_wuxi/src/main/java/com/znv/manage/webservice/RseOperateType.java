
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>RseOperateType�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="RseOperateType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Start"/>
 *     &lt;enumeration value="Pause"/>
 *     &lt;enumeration value="End"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RseOperateType")
@XmlEnum
public enum RseOperateType {

    @XmlEnumValue("Start")
    START("Start"),
    @XmlEnumValue("Pause")
    PAUSE("Pause"),
    @XmlEnumValue("End")
    END("End");
    private final String value;

    RseOperateType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RseOperateType fromValue(String v) {
        for (RseOperateType c: RseOperateType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
