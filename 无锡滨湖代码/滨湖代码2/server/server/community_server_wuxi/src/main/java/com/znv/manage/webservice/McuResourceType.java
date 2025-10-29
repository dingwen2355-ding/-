
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>McuResourceType�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="McuResourceType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ContinuousPresence"/>
 *     &lt;enumeration value="RateAdpter"/>
 *     &lt;enumeration value="Capacity"/>
 *     &lt;enumeration value="VideoPort"/>
 *     &lt;enumeration value="AudioPort"/>
 *     &lt;enumeration value="Core"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "McuResourceType")
@XmlEnum
public enum McuResourceType {

    @XmlEnumValue("ContinuousPresence")
    CONTINUOUS_PRESENCE("ContinuousPresence"),
    @XmlEnumValue("RateAdpter")
    RATE_ADPTER("RateAdpter"),
    @XmlEnumValue("Capacity")
    CAPACITY("Capacity"),
    @XmlEnumValue("VideoPort")
    VIDEO_PORT("VideoPort"),
    @XmlEnumValue("AudioPort")
    AUDIO_PORT("AudioPort"),
    @XmlEnumValue("Core")
    CORE("Core");
    private final String value;

    McuResourceType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static McuResourceType fromValue(String v) {
        for (McuResourceType c: McuResourceType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
