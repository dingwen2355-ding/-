
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="siteUri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="videoSourceUri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isLock" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "siteUri",
    "videoSourceUri",
    "isLock"
})
@XmlRootElement(name = "SetVideoSource")
public class SetVideoSource {

    protected ConferenceId confId;
    protected String siteUri;
    protected String videoSourceUri;
    protected boolean isLock;

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
     * ��ȡvideoSourceUri���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVideoSourceUri() {
        return videoSourceUri;
    }

    /**
     * ����videoSourceUri���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVideoSourceUri(String value) {
        this.videoSourceUri = value;
    }

    /**
     * ��ȡisLock���Ե�ֵ��
     * 
     */
    public boolean isIsLock() {
        return isLock;
    }

    /**
     * ����isLock���Ե�ֵ��
     * 
     */
    public void setIsLock(boolean value) {
        this.isLock = value;
    }

}
