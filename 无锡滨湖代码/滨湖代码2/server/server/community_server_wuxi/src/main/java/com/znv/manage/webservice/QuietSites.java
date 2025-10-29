
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
 *         &lt;element name="isQuiet" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="siteUris" type="{http://smc.huawei.com/}ArrayOfString" minOccurs="0"/>
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
    "isQuiet",
    "siteUris"
})
@XmlRootElement(name = "QuietSites")
public class QuietSites {

    protected ConferenceId confId;
    protected boolean isQuiet;
    protected ArrayOfString siteUris;

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
     * ��ȡisQuiet���Ե�ֵ��
     * 
     */
    public boolean isIsQuiet() {
        return isQuiet;
    }

    /**
     * ����isQuiet���Ե�ֵ��
     * 
     */
    public void setIsQuiet(boolean value) {
        this.isQuiet = value;
    }

    /**
     * ��ȡsiteUris���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getSiteUris() {
        return siteUris;
    }

    /**
     * ����siteUris���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setSiteUris(ArrayOfString value) {
        this.siteUris = value;
    }

}
