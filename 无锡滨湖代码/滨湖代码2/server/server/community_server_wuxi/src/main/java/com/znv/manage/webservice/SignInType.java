
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>SignInType�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="SignInType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="JoinSignIn"/>
 *     &lt;enumeration value="FaceSignIn"/>
 *     &lt;enumeration value="MatchSignIn"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SignInType")
@XmlEnum
public enum SignInType {

    @XmlEnumValue("JoinSignIn")
    JOIN_SIGN_IN("JoinSignIn"),
    @XmlEnumValue("FaceSignIn")
    FACE_SIGN_IN("FaceSignIn"),
    @XmlEnumValue("MatchSignIn")
    MATCH_SIGN_IN("MatchSignIn");
    private final String value;

    SignInType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SignInType fromValue(String v) {
        for (SignInType c: SignInType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
