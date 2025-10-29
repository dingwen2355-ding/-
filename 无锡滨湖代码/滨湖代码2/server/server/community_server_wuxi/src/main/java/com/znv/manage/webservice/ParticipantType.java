
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ParticipantType�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="ParticipantType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Site"/>
 *     &lt;enumeration value="UpperLevelParticipant"/>
 *     &lt;enumeration value="LowerLevelParticipant"/>
 *     &lt;enumeration value="CGW"/>
 *     &lt;enumeration value="VSD"/>
 *     &lt;enumeration value="WebVirtual"/>
 *     &lt;enumeration value="MulCascade"/>
 *     &lt;enumeration value="Data"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ParticipantType")
@XmlEnum
public enum ParticipantType {

    @XmlEnumValue("Site")
    SITE("Site"),
    @XmlEnumValue("UpperLevelParticipant")
    UPPER_LEVEL_PARTICIPANT("UpperLevelParticipant"),
    @XmlEnumValue("LowerLevelParticipant")
    LOWER_LEVEL_PARTICIPANT("LowerLevelParticipant"),
    CGW("CGW"),
    VSD("VSD"),
    @XmlEnumValue("WebVirtual")
    WEB_VIRTUAL("WebVirtual"),
    @XmlEnumValue("MulCascade")
    MUL_CASCADE("MulCascade"),
    @XmlEnumValue("Data")
    DATA("Data");
    private final String value;

    ParticipantType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ParticipantType fromValue(String v) {
        for (ParticipantType c: ParticipantType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
