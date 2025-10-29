
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>anonymous complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="confId" type="{http://smc.huawei.com/}ConferenceId" minOccurs="0"/>
 *         &lt;element name="siteParam" type="{http://smc.huawei.com/}ScheduleSiteParam2" minOccurs="0"/>
 *         &lt;element name="beginTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="IsUseAnonymousSite" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "confId",
    "siteParam",
    "beginTime",
    "isUseAnonymousSite"
})
@XmlRootElement(name = "AddSiteInScheduledConf")
public class AddSiteInScheduledConf {

    protected ConferenceId confId;
    protected ScheduleSiteParam2 siteParam;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar beginTime;
    @XmlElement(name = "IsUseAnonymousSite", required = true, type = Boolean.class, nillable = true)
    protected Boolean isUseAnonymousSite;

    /**
     * ��ȡconfId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ConferenceId }
     *     
     */
    public ConferenceId getConfId() {
        return confId;
    }

    /**
     * ����confId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ConferenceId }
     *     
     */
    public void setConfId(ConferenceId value) {
        this.confId = value;
    }

    /**
     * ��ȡsiteParam���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ScheduleSiteParam2 }
     *     
     */
    public ScheduleSiteParam2 getSiteParam() {
        return siteParam;
    }

    /**
     * ����siteParam���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ScheduleSiteParam2 }
     *     
     */
    public void setSiteParam(ScheduleSiteParam2 value) {
        this.siteParam = value;
    }

    /**
     * ��ȡbeginTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBeginTime() {
        return beginTime;
    }

    /**
     * ����beginTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBeginTime(XMLGregorianCalendar value) {
        this.beginTime = value;
    }

    /**
     * ��ȡisUseAnonymousSite���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsUseAnonymousSite() {
        return isUseAnonymousSite;
    }

    /**
     * ����isUseAnonymousSite���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsUseAnonymousSite(Boolean value) {
        this.isUseAnonymousSite = value;
    }

}
