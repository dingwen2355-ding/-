
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>VideoFormat�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="VideoFormat">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="QCif"/>
 *     &lt;enumeration value="Cif"/>
 *     &lt;enumeration value="SQCif"/>
 *     &lt;enumeration value="Cif2"/>
 *     &lt;enumeration value="Cif4"/>
 *     &lt;enumeration value="P720"/>
 *     &lt;enumeration value="P1080"/>
 *     &lt;enumeration value="I108"/>
 *     &lt;enumeration value="P480"/>
 *     &lt;enumeration value="I480"/>
 *     &lt;enumeration value="P720_60f"/>
 *     &lt;enumeration value="P1080_60f"/>
 *     &lt;enumeration value="Auto"/>
 *     &lt;enumeration value="P360"/>
 *     &lt;enumeration value="VGA"/>
 *     &lt;enumeration value="K4K2"/>
 *     &lt;enumeration value="P576"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "VideoFormat")
@XmlEnum
public enum VideoFormat {

    @XmlEnumValue("QCif")
    Q_CIF("QCif"),
    @XmlEnumValue("Cif")
    CIF("Cif"),
    @XmlEnumValue("SQCif")
    SQ_CIF("SQCif"),
    @XmlEnumValue("Cif2")
    CIF_2("Cif2"),
    @XmlEnumValue("Cif4")
    CIF_4("Cif4"),
    @XmlEnumValue("P720")
    P_720("P720"),
    @XmlEnumValue("P1080")
    P_1080("P1080"),
    @XmlEnumValue("I108")
    I_108("I108"),
    @XmlEnumValue("P480")
    P_480("P480"),
    @XmlEnumValue("I480")
    I_480("I480"),
    @XmlEnumValue("P720_60f")
    P_720_60_F("P720_60f"),
    @XmlEnumValue("P1080_60f")
    P_1080_60_F("P1080_60f"),
    @XmlEnumValue("Auto")
    AUTO("Auto"),
    @XmlEnumValue("P360")
    P_360("P360"),
    VGA("VGA"),
    @XmlEnumValue("K4K2")
    K_4_K_2("K4K2"),
    @XmlEnumValue("P576")
    P_576("P576");
    private final String value;

    VideoFormat(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VideoFormat fromValue(String v) {
        for (VideoFormat c: VideoFormat.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
