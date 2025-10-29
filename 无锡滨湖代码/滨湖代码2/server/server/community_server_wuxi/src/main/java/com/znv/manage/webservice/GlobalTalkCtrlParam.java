
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>GlobalTalkCtrlParam complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="GlobalTalkCtrlParam">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MainSiteId" type="{http://smc.huawei.com/}McuSiteId" minOccurs="0"/>
 *         &lt;element name="MainSiteUri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FloorSiteId" type="{http://smc.huawei.com/}McuSiteId" minOccurs="0"/>
 *         &lt;element name="FloorSiteUri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IsPrivate" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GlobalTalkCtrlParam", propOrder = {
    "mainSiteId",
    "mainSiteUri",
    "floorSiteId",
    "floorSiteUri",
    "isPrivate"
})
public class GlobalTalkCtrlParam {

    @XmlElement(name = "MainSiteId")
    protected McuSiteId mainSiteId;
    @XmlElement(name = "MainSiteUri")
    protected String mainSiteUri;
    @XmlElement(name = "FloorSiteId")
    protected McuSiteId floorSiteId;
    @XmlElement(name = "FloorSiteUri")
    protected String floorSiteUri;
    @XmlElement(name = "IsPrivate")
    protected boolean isPrivate;

    /**
     * ��ȡmainSiteId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link McuSiteId }
     *     
     */
    public McuSiteId getMainSiteId() {
        return mainSiteId;
    }

    /**
     * ����mainSiteId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link McuSiteId }
     *     
     */
    public void setMainSiteId(McuSiteId value) {
        this.mainSiteId = value;
    }

    /**
     * ��ȡmainSiteUri���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMainSiteUri() {
        return mainSiteUri;
    }

    /**
     * ����mainSiteUri���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMainSiteUri(String value) {
        this.mainSiteUri = value;
    }

    /**
     * ��ȡfloorSiteId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link McuSiteId }
     *     
     */
    public McuSiteId getFloorSiteId() {
        return floorSiteId;
    }

    /**
     * ����floorSiteId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link McuSiteId }
     *     
     */
    public void setFloorSiteId(McuSiteId value) {
        this.floorSiteId = value;
    }

    /**
     * ��ȡfloorSiteUri���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFloorSiteUri() {
        return floorSiteUri;
    }

    /**
     * ����floorSiteUri���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFloorSiteUri(String value) {
        this.floorSiteUri = value;
    }

    /**
     * ��ȡisPrivate���Ե�ֵ��
     * 
     */
    public boolean isIsPrivate() {
        return isPrivate;
    }

    /**
     * ����isPrivate���Ե�ֵ��
     * 
     */
    public void setIsPrivate(boolean value) {
        this.isPrivate = value;
    }

}
