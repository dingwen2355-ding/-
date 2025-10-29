
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>MediaEncryptType�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="MediaEncryptType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="AutoEncrypt"/>
 *     &lt;enumeration value="ForceEncrypt"/>
 *     &lt;enumeration value="None"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "MediaEncryptType")
@XmlEnum
public enum MediaEncryptType {

    @XmlEnumValue("AutoEncrypt")
    AUTO_ENCRYPT("AutoEncrypt"),
    @XmlEnumValue("ForceEncrypt")
    FORCE_ENCRYPT("ForceEncrypt"),
    @XmlEnumValue("None")
    NONE("None");
    private final String value;

    MediaEncryptType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MediaEncryptType fromValue(String v) {
        for (MediaEncryptType c: MediaEncryptType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
