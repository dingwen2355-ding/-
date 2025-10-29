
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>CameraActionType�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="CameraActionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CAMERA_TILTUP"/>
 *     &lt;enumeration value="CAMERA_TILTDOWN"/>
 *     &lt;enumeration value="CAMERA_PANLEFT"/>
 *     &lt;enumeration value="CAMERA_PANRIGHT"/>
 *     &lt;enumeration value="CAMERA_ZOOMOUT"/>
 *     &lt;enumeration value="CAMERA_ZOOMIN"/>
 *     &lt;enumeration value="CAMERA_FOCUSIN"/>
 *     &lt;enumeration value="CAMERA_FOCUSOUT"/>
 *     &lt;enumeration value="CAMERA_AUTOFOCUS"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CameraActionType")
@XmlEnum
public enum CameraActionType {

    CAMERA_TILTUP,
    CAMERA_TILTDOWN,
    CAMERA_PANLEFT,
    CAMERA_PANRIGHT,
    CAMERA_ZOOMOUT,
    CAMERA_ZOOMIN,
    CAMERA_FOCUSIN,
    CAMERA_FOCUSOUT,
    CAMERA_AUTOFOCUS;

    public String value() {
        return name();
    }

    public static CameraActionType fromValue(String v) {
        return valueOf(v);
    }

}
