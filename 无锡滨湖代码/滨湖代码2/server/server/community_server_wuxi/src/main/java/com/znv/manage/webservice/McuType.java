
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>McuType�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="McuType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Mcu9660"/>
 *     &lt;enumeration value="Mcu9650"/>
 *     &lt;enumeration value="Mcu9630"/>
 *     &lt;enumeration value="Mcu8660"/>
 *     &lt;enumeration value="Mcu8650"/>
 *     &lt;enumeration value="Mcu8650C"/>
 *     &lt;enumeration value="CloudMcu"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "McuType")
@XmlEnum
public enum McuType {

    @XmlEnumValue("Mcu9660")
    MCU_9660("Mcu9660"),
    @XmlEnumValue("Mcu9650")
    MCU_9650("Mcu9650"),
    @XmlEnumValue("Mcu9630")
    MCU_9630("Mcu9630"),
    @XmlEnumValue("Mcu8660")
    MCU_8660("Mcu8660"),
    @XmlEnumValue("Mcu8650")
    MCU_8650("Mcu8650"),
    @XmlEnumValue("Mcu8650C")
    MCU_8650_C("Mcu8650C"),
    @XmlEnumValue("CloudMcu")
    CLOUD_MCU("CloudMcu");
    private final String value;

    McuType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static McuType fromValue(String v) {
        for (McuType c: McuType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
