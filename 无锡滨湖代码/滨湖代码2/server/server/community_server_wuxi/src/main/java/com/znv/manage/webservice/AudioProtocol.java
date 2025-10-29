
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>AudioProtocol�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="AudioProtocol">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="G711a"/>
 *     &lt;enumeration value="G722_48k"/>
 *     &lt;enumeration value="G722_56k"/>
 *     &lt;enumeration value="G728"/>
 *     &lt;enumeration value="G711u"/>
 *     &lt;enumeration value="G722_64k"/>
 *     &lt;enumeration value="G723_53k"/>
 *     &lt;enumeration value="G723_63k"/>
 *     &lt;enumeration value="G729"/>
 *     &lt;enumeration value="AacldSin"/>
 *     &lt;enumeration value="AacldDbl"/>
 *     &lt;enumeration value="Hwwb"/>
 *     &lt;enumeration value="Amr"/>
 *     &lt;enumeration value="AaclcSin"/>
 *     &lt;enumeration value="AaclcDbl"/>
 *     &lt;enumeration value="G7221c_32"/>
 *     &lt;enumeration value="G7221c_24"/>
 *     &lt;enumeration value="G7221c_48"/>
 *     &lt;enumeration value="HwaldSin"/>
 *     &lt;enumeration value="HwaldDbl"/>
 *     &lt;enumeration value="SelfFit"/>
 *     &lt;enumeration value="AUDIO_Invalid"/>
 *     &lt;enumeration value="AUDIO_ANY"/>
 *     &lt;enumeration value="AacldTri"/>
 *     &lt;enumeration value="G722"/>
 *     &lt;enumeration value="iLBC"/>
 *     &lt;enumeration value="G719"/>
 *     &lt;enumeration value="G7221c"/>
 *     &lt;enumeration value="Opus"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AudioProtocol")
@XmlEnum
public enum AudioProtocol {

    @XmlEnumValue("G711a")
    G_711_A("G711a"),
    @XmlEnumValue("G722_48k")
    G_722_48_K("G722_48k"),
    @XmlEnumValue("G722_56k")
    G_722_56_K("G722_56k"),
    @XmlEnumValue("G728")
    G_728("G728"),
    @XmlEnumValue("G711u")
    G_711_U("G711u"),
    @XmlEnumValue("G722_64k")
    G_722_64_K("G722_64k"),
    @XmlEnumValue("G723_53k")
    G_723_53_K("G723_53k"),
    @XmlEnumValue("G723_63k")
    G_723_63_K("G723_63k"),
    @XmlEnumValue("G729")
    G_729("G729"),
    @XmlEnumValue("AacldSin")
    AACLD_SIN("AacldSin"),
    @XmlEnumValue("AacldDbl")
    AACLD_DBL("AacldDbl"),
    @XmlEnumValue("Hwwb")
    HWWB("Hwwb"),
    @XmlEnumValue("Amr")
    AMR("Amr"),
    @XmlEnumValue("AaclcSin")
    AACLC_SIN("AaclcSin"),
    @XmlEnumValue("AaclcDbl")
    AACLC_DBL("AaclcDbl"),
    @XmlEnumValue("G7221c_32")
    G_7221_C_32("G7221c_32"),
    @XmlEnumValue("G7221c_24")
    G_7221_C_24("G7221c_24"),
    @XmlEnumValue("G7221c_48")
    G_7221_C_48("G7221c_48"),
    @XmlEnumValue("HwaldSin")
    HWALD_SIN("HwaldSin"),
    @XmlEnumValue("HwaldDbl")
    HWALD_DBL("HwaldDbl"),
    @XmlEnumValue("SelfFit")
    SELF_FIT("SelfFit"),
    @XmlEnumValue("AUDIO_Invalid")
    AUDIO_INVALID("AUDIO_Invalid"),
    AUDIO_ANY("AUDIO_ANY"),
    @XmlEnumValue("AacldTri")
    AACLD_TRI("AacldTri"),
    @XmlEnumValue("G722")
    G_722("G722"),
    @XmlEnumValue("iLBC")
    I_LBC("iLBC"),
    @XmlEnumValue("G719")
    G_719("G719"),
    @XmlEnumValue("G7221c")
    G_7221_C("G7221c"),
    @XmlEnumValue("Opus")
    OPUS("Opus");
    private final String value;

    AudioProtocol(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AudioProtocol fromValue(String v) {
        for (AudioProtocol c: AudioProtocol.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
