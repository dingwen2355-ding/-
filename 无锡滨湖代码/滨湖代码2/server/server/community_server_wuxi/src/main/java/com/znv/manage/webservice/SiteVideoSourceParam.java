
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>SiteVideoSourceParam complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="SiteVideoSourceParam">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SiteUri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IsLocked" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SiteVideoSourceParam", propOrder = {
    "siteUri",
    "isLocked"
})
public class SiteVideoSourceParam {

    @XmlElement(name = "SiteUri")
    protected String siteUri;
    @XmlElement(name = "IsLocked")
    protected boolean isLocked;

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
     * ��ȡisLocked���Ե�ֵ��
     * 
     */
    public boolean isIsLocked() {
        return isLocked;
    }

    /**
     * ����isLocked���Ե�ֵ��
     * 
     */
    public void setIsLocked(boolean value) {
        this.isLocked = value;
    }

}
