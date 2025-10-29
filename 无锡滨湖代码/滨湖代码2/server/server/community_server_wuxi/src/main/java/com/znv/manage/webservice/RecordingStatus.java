
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>RecordingStatus�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="RecordingStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Recording"/>
 *     &lt;enumeration value="Paused"/>
 *     &lt;enumeration value="Unavailable"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RecordingStatus")
@XmlEnum
public enum RecordingStatus {

    @XmlEnumValue("Recording")
    RECORDING("Recording"),
    @XmlEnumValue("Paused")
    PAUSED("Paused"),
    @XmlEnumValue("Unavailable")
    UNAVAILABLE("Unavailable");
    private final String value;

    RecordingStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RecordingStatus fromValue(String v) {
        for (RecordingStatus c: RecordingStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
