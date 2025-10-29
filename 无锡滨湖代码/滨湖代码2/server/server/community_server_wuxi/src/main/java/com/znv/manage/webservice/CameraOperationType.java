
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>CameraOperationType�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="CameraOperationType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="startCamera"/>
 *     &lt;enumeration value="stopCamera"/>
 *     &lt;enumeration value="switchCamera"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CameraOperationType")
@XmlEnum
public enum CameraOperationType {

    @XmlEnumValue("startCamera")
    START_CAMERA("startCamera"),
    @XmlEnumValue("stopCamera")
    STOP_CAMERA("stopCamera"),
    @XmlEnumValue("switchCamera")
    SWITCH_CAMERA("switchCamera");
    private final String value;

    CameraOperationType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CameraOperationType fromValue(String v) {
        for (CameraOperationType c: CameraOperationType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
