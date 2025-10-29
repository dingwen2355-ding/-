
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>DeviceStatusEnum�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="DeviceStatusEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NoStatus"/>
 *     &lt;enumeration value="DeviceStatus_Failed"/>
 *     &lt;enumeration value="DeviceStatus_Ok"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DeviceStatusEnum")
@XmlEnum
public enum DeviceStatusEnum {

    @XmlEnumValue("NoStatus")
    NO_STATUS("NoStatus"),
    @XmlEnumValue("DeviceStatus_Failed")
    DEVICE_STATUS_FAILED("DeviceStatus_Failed"),
    @XmlEnumValue("DeviceStatus_Ok")
    DEVICE_STATUS_OK("DeviceStatus_Ok");
    private final String value;

    DeviceStatusEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DeviceStatusEnum fromValue(String v) {
        for (DeviceStatusEnum c: DeviceStatusEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
