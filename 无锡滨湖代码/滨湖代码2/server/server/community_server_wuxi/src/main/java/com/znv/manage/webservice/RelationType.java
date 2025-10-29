
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>RelationType�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="RelationType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="None"/>
 *     &lt;enumeration value="Belong"/>
 *     &lt;enumeration value="Bubordinate"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RelationType")
@XmlEnum
public enum RelationType {

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("Belong")
    BELONG("Belong"),
    @XmlEnumValue("Bubordinate")
    BUBORDINATE("Bubordinate");
    private final String value;

    RelationType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RelationType fromValue(String v) {
        for (RelationType c: RelationType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
