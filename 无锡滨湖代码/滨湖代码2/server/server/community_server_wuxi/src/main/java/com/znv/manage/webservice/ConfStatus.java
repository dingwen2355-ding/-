
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ConfStatus�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="ConfStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Unknow"/>
 *     &lt;enumeration value="Noexist"/>
 *     &lt;enumeration value="Scheduled"/>
 *     &lt;enumeration value="Ongoing"/>
 *     &lt;enumeration value="Nopermission"/>
 *     &lt;enumeration value="End"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ConfStatus")
@XmlEnum
public enum ConfStatus {

    @XmlEnumValue("Unknow")
    UNKNOW("Unknow"),
    @XmlEnumValue("Noexist")
    NOEXIST("Noexist"),
    @XmlEnumValue("Scheduled")
    SCHEDULED("Scheduled"),
    @XmlEnumValue("Ongoing")
    ONGOING("Ongoing"),
    @XmlEnumValue("Nopermission")
    NOPERMISSION("Nopermission"),
    @XmlEnumValue("End")
    END("End");
    private final String value;

    ConfStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ConfStatus fromValue(String v) {
        for (ConfStatus c: ConfStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
