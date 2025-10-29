
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>McuSiteId complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="McuSiteId">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SiteId" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="SiteGuid" type="{http://microsoft.com/wsdl/types/}guid"/>
 *         &lt;element name="SiteMid" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="SiteTid" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "McuSiteId", propOrder = {
    "siteId",
    "siteGuid",
    "siteMid",
    "siteTid"
})
public class McuSiteId {

    @XmlElement(name = "SiteId")
    @XmlSchemaType(name = "unsignedInt")
    protected long siteId;
    @XmlElement(name = "SiteGuid", required = true)
    protected String siteGuid;
    @XmlElement(name = "SiteMid")
    @XmlSchemaType(name = "unsignedInt")
    protected long siteMid;
    @XmlElement(name = "SiteTid")
    @XmlSchemaType(name = "unsignedInt")
    protected long siteTid;

    /**
     * ��ȡsiteId���Ե�ֵ��
     * 
     */
    public long getSiteId() {
        return siteId;
    }

    /**
     * ����siteId���Ե�ֵ��
     * 
     */
    public void setSiteId(long value) {
        this.siteId = value;
    }

    /**
     * ��ȡsiteGuid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiteGuid() {
        return siteGuid;
    }

    /**
     * ����siteGuid���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiteGuid(String value) {
        this.siteGuid = value;
    }

    /**
     * ��ȡsiteMid���Ե�ֵ��
     * 
     */
    public long getSiteMid() {
        return siteMid;
    }

    /**
     * ����siteMid���Ե�ֵ��
     * 
     */
    public void setSiteMid(long value) {
        this.siteMid = value;
    }

    /**
     * ��ȡsiteTid���Ե�ֵ��
     * 
     */
    public long getSiteTid() {
        return siteTid;
    }

    /**
     * ����siteTid���Ե�ֵ��
     * 
     */
    public void setSiteTid(long value) {
        this.siteTid = value;
    }

}
