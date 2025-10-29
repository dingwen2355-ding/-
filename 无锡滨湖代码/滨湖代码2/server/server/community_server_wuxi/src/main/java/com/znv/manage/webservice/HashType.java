
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>HashType�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="HashType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="MD5"/>
 *     &lt;enumeration value="SHA256"/>
 *     &lt;enumeration value="SHA256_SALT"/>
 *     &lt;enumeration value="PBKDF2"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "HashType")
@XmlEnum
public enum HashType {

    @XmlEnumValue("MD5")
    MD_5("MD5"),
    @XmlEnumValue("SHA256")
    SHA_256("SHA256"),
    @XmlEnumValue("SHA256_SALT")
    SHA_256_SALT("SHA256_SALT"),
    @XmlEnumValue("PBKDF2")
    PBKDF_2("PBKDF2");
    private final String value;

    HashType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static HashType fromValue(String v) {
        for (HashType c: HashType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
