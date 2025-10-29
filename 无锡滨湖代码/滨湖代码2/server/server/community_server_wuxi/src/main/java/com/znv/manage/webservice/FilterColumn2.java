
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>FilterColumn2�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="FilterColumn2">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SiteName"/>
 *     &lt;enumeration value="SiteUri"/>
 *     &lt;enumeration value="SiteStatus"/>
 *     &lt;enumeration value="SiteType"/>
 *     &lt;enumeration value="DeviceSn"/>
 *     &lt;enumeration value="OrgId"/>
 *     &lt;enumeration value="ConfTemplateId"/>
 *     &lt;enumeration value="ConfTemplateType"/>
 *     &lt;enumeration value="ConfTemplateName"/>
 *     &lt;enumeration value="ConfTemplateAccessCode"/>
 *     &lt;enumeration value="ConfTemplateState"/>
 *     &lt;enumeration value="ConfTemplateRate"/>
 *     &lt;enumeration value="ConfTemplateBillcode"/>
 *     &lt;enumeration value="McuName"/>
 *     &lt;enumeration value="ConferId"/>
 *     &lt;enumeration value="ConferExterId"/>
 *     &lt;enumeration value="ConferName"/>
 *     &lt;enumeration value="ConfStatus"/>
 *     &lt;enumeration value="ConferChair"/>
 *     &lt;enumeration value="ConferBroadcast"/>
 *     &lt;enumeration value="ConferSpeaking"/>
 *     &lt;enumeration value="ConferPresentation"/>
 *     &lt;enumeration value="ConferIsLock"/>
 *     &lt;enumeration value="ConferIsAudioSwitch"/>
 *     &lt;enumeration value="ConferAudioSwitchParam"/>
 *     &lt;enumeration value="ConferBeginTime"/>
 *     &lt;enumeration value="ConferEndTime"/>
 *     &lt;enumeration value="ConferIsLockPresentation"/>
 *     &lt;enumeration value="ConferLockPresentation"/>
 *     &lt;enumeration value="ConferMode"/>
 *     &lt;enumeration value="RseName"/>
 *     &lt;enumeration value="ConferMediaType"/>
 *     &lt;enumeration value="ConferCreateruser"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "FilterColumn2")
@XmlEnum
public enum FilterColumn2 {

    @XmlEnumValue("SiteName")
    SITE_NAME("SiteName"),
    @XmlEnumValue("SiteUri")
    SITE_URI("SiteUri"),
    @XmlEnumValue("SiteStatus")
    SITE_STATUS("SiteStatus"),
    @XmlEnumValue("SiteType")
    SITE_TYPE("SiteType"),
    @XmlEnumValue("DeviceSn")
    DEVICE_SN("DeviceSn"),
    @XmlEnumValue("OrgId")
    ORG_ID("OrgId"),
    @XmlEnumValue("ConfTemplateId")
    CONF_TEMPLATE_ID("ConfTemplateId"),
    @XmlEnumValue("ConfTemplateType")
    CONF_TEMPLATE_TYPE("ConfTemplateType"),
    @XmlEnumValue("ConfTemplateName")
    CONF_TEMPLATE_NAME("ConfTemplateName"),
    @XmlEnumValue("ConfTemplateAccessCode")
    CONF_TEMPLATE_ACCESS_CODE("ConfTemplateAccessCode"),
    @XmlEnumValue("ConfTemplateState")
    CONF_TEMPLATE_STATE("ConfTemplateState"),
    @XmlEnumValue("ConfTemplateRate")
    CONF_TEMPLATE_RATE("ConfTemplateRate"),
    @XmlEnumValue("ConfTemplateBillcode")
    CONF_TEMPLATE_BILLCODE("ConfTemplateBillcode"),
    @XmlEnumValue("McuName")
    MCU_NAME("McuName"),
    @XmlEnumValue("ConferId")
    CONFER_ID("ConferId"),
    @XmlEnumValue("ConferExterId")
    CONFER_EXTER_ID("ConferExterId"),
    @XmlEnumValue("ConferName")
    CONFER_NAME("ConferName"),
    @XmlEnumValue("ConfStatus")
    CONF_STATUS("ConfStatus"),
    @XmlEnumValue("ConferChair")
    CONFER_CHAIR("ConferChair"),
    @XmlEnumValue("ConferBroadcast")
    CONFER_BROADCAST("ConferBroadcast"),
    @XmlEnumValue("ConferSpeaking")
    CONFER_SPEAKING("ConferSpeaking"),
    @XmlEnumValue("ConferPresentation")
    CONFER_PRESENTATION("ConferPresentation"),
    @XmlEnumValue("ConferIsLock")
    CONFER_IS_LOCK("ConferIsLock"),
    @XmlEnumValue("ConferIsAudioSwitch")
    CONFER_IS_AUDIO_SWITCH("ConferIsAudioSwitch"),
    @XmlEnumValue("ConferAudioSwitchParam")
    CONFER_AUDIO_SWITCH_PARAM("ConferAudioSwitchParam"),
    @XmlEnumValue("ConferBeginTime")
    CONFER_BEGIN_TIME("ConferBeginTime"),
    @XmlEnumValue("ConferEndTime")
    CONFER_END_TIME("ConferEndTime"),
    @XmlEnumValue("ConferIsLockPresentation")
    CONFER_IS_LOCK_PRESENTATION("ConferIsLockPresentation"),
    @XmlEnumValue("ConferLockPresentation")
    CONFER_LOCK_PRESENTATION("ConferLockPresentation"),
    @XmlEnumValue("ConferMode")
    CONFER_MODE("ConferMode"),
    @XmlEnumValue("RseName")
    RSE_NAME("RseName"),
    @XmlEnumValue("ConferMediaType")
    CONFER_MEDIA_TYPE("ConferMediaType"),
    @XmlEnumValue("ConferCreateruser")
    CONFER_CREATERUSER("ConferCreateruser");
    private final String value;

    FilterColumn2(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FilterColumn2 fromValue(String v) {
        for (FilterColumn2 c: FilterColumn2.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
