
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>StreamType�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="StreamType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="VideoMain"/>
 *     &lt;enumeration value="Audio"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StreamType")
@XmlEnum
public enum StreamType {

    @XmlEnumValue("VideoMain")
    VIDEO_MAIN("VideoMain"),
    @XmlEnumValue("Audio")
    AUDIO("Audio");
    private final String value;

    StreamType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StreamType fromValue(String v) {
        for (StreamType c: StreamType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
