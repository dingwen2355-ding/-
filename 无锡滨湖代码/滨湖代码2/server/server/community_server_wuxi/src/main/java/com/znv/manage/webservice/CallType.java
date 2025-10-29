
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>CallType�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="CallType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Calling"/>
 *     &lt;enumeration value="Called"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CallType")
@XmlEnum
public enum CallType {

    @XmlEnumValue("Calling")
    CALLING("Calling"),
    @XmlEnumValue("Called")
    CALLED("Called");
    private final String value;

    CallType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CallType fromValue(String v) {
        for (CallType c: CallType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
