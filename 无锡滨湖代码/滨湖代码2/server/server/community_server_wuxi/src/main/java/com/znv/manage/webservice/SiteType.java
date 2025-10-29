
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>SiteType�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="SiteType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SiteType_E1"/>
 *     &lt;enumeration value="SiteType_Isdn"/>
 *     &lt;enumeration value="SiteType_Ip"/>
 *     &lt;enumeration value="SiteType_Pstn"/>
 *     &lt;enumeration value="SiteType_4E1"/>
 *     &lt;enumeration value="SiteType_Sip"/>
 *     &lt;enumeration value="SiteType_VoIpSip"/>
 *     &lt;enumeration value="SiteType_VoIpH323"/>
 *     &lt;enumeration value="SiteType_Auto"/>
 *     &lt;enumeration value="SiteType_Sip_TP"/>
 *     &lt;enumeration value="SiteType_H323_TP"/>
 *     &lt;enumeration value="None"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SiteType")
@XmlEnum
public enum SiteType {

    @XmlEnumValue("SiteType_E1")
    SITE_TYPE_E_1("SiteType_E1"),
    @XmlEnumValue("SiteType_Isdn")
    SITE_TYPE_ISDN("SiteType_Isdn"),
    @XmlEnumValue("SiteType_Ip")
    SITE_TYPE_IP("SiteType_Ip"),
    @XmlEnumValue("SiteType_Pstn")
    SITE_TYPE_PSTN("SiteType_Pstn"),
    @XmlEnumValue("SiteType_4E1")
    SITE_TYPE_4_E_1("SiteType_4E1"),
    @XmlEnumValue("SiteType_Sip")
    SITE_TYPE_SIP("SiteType_Sip"),
    @XmlEnumValue("SiteType_VoIpSip")
    SITE_TYPE_VO_IP_SIP("SiteType_VoIpSip"),
    @XmlEnumValue("SiteType_VoIpH323")
    SITE_TYPE_VO_IP_H_323("SiteType_VoIpH323"),
    @XmlEnumValue("SiteType_Auto")
    SITE_TYPE_AUTO("SiteType_Auto"),
    @XmlEnumValue("SiteType_Sip_TP")
    SITE_TYPE_SIP_TP("SiteType_Sip_TP"),
    @XmlEnumValue("SiteType_H323_TP")
    SITE_TYPE_H_323_TP("SiteType_H323_TP"),
    @XmlEnumValue("None")
    NONE("None");
    private final String value;

    SiteType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SiteType fromValue(String v) {
        for (SiteType c: SiteType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
