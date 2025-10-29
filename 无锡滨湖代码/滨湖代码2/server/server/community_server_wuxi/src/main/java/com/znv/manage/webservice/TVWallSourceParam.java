
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>TVWallSourceParam complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="TVWallSourceParam">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ConfId" type="{http://smc.huawei.com/}ConferenceId" minOccurs="0"/>
 *         &lt;element name="SiteUri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MediaType" type="{http://smc.huawei.com/}StreamType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TVWallSourceParam", propOrder = {
    "confId",
    "siteUri",
    "mediaType"
})
public class TVWallSourceParam {

    @XmlElement(name = "ConfId")
    protected ConferenceId confId;
    @XmlElement(name = "SiteUri")
    protected String siteUri;
    @XmlElement(name = "MediaType", required = true)
    @XmlSchemaType(name = "string")
    protected StreamType mediaType;

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
     * ��ȡsiteUri���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiteUri() {
        return siteUri;
    }

    /**
     * ����siteUri���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiteUri(String value) {
        this.siteUri = value;
    }

    /**
     * ��ȡmediaType���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link StreamType }
     *     
     */
    public StreamType getMediaType() {
        return mediaType;
    }

    /**
     * ����mediaType���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link StreamType }
     *     
     */
    public void setMediaType(StreamType value) {
        this.mediaType = value;
    }

}
