
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>CameraSequence�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="CameraSequence">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CAMERA_FIRST"/>
 *     &lt;enumeration value="CAMERA_SECOEND"/>
 *     &lt;enumeration value="CAMERA_THIRD"/>
 *     &lt;enumeration value="CAMERA_FOURTH"/>
 *     &lt;enumeration value="CAMERA_FIFTH"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CameraSequence")
@XmlEnum
public enum CameraSequence {

    CAMERA_FIRST,
    CAMERA_SECOEND,
    CAMERA_THIRD,
    CAMERA_FOURTH,
    CAMERA_FIFTH;

    public String value() {
        return name();
    }

    public static CameraSequence fromValue(String v) {
        return valueOf(v);
    }

}
