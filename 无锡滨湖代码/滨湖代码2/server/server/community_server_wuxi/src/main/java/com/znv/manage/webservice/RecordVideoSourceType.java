
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>RecordVideoSourceType�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="RecordVideoSourceType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Auto"/>
 *     &lt;enumeration value="ConfCp"/>
 *     &lt;enumeration value="Custom"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RecordVideoSourceType")
@XmlEnum
public enum RecordVideoSourceType {

    @XmlEnumValue("Auto")
    AUTO("Auto"),
    @XmlEnumValue("ConfCp")
    CONF_CP("ConfCp"),
    @XmlEnumValue("Custom")
    CUSTOM("Custom");
    private final String value;

    RecordVideoSourceType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RecordVideoSourceType fromValue(String v) {
        for (RecordVideoSourceType c: RecordVideoSourceType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
