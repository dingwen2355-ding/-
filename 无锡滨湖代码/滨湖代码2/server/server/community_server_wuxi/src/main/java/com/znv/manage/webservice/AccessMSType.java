
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>AccessMSType�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="AccessMSType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="MCU"/>
 *     &lt;enumeration value="SBC"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AccessMSType")
@XmlEnum
public enum AccessMSType {

    MCU,
    SBC;

    public String value() {
        return name();
    }

    public static AccessMSType fromValue(String v) {
        return valueOf(v);
    }

}
