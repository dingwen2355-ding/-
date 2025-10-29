
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>VideoProtocol�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="VideoProtocol">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="H261"/>
 *     &lt;enumeration value="H263"/>
 *     &lt;enumeration value="H264"/>
 *     &lt;enumeration value="H264HP"/>
 *     &lt;enumeration value="H265"/>
 *     &lt;enumeration value="Auto"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "VideoProtocol")
@XmlEnum
public enum VideoProtocol {

    @XmlEnumValue("H261")
    H_261("H261"),
    @XmlEnumValue("H263")
    H_263("H263"),
    @XmlEnumValue("H264")
    H_264("H264"),
    @XmlEnumValue("H264HP")
    H_264_HP("H264HP"),
    @XmlEnumValue("H265")
    H_265("H265"),
    @XmlEnumValue("Auto")
    AUTO("Auto");
    private final String value;

    VideoProtocol(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VideoProtocol fromValue(String v) {
        for (VideoProtocol c: VideoProtocol.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
