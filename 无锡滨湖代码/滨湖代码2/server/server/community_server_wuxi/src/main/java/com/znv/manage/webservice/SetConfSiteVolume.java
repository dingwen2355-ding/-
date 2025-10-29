
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
 *         &lt;element name="siteVolumes" type="{http://smc.huawei.com/}ArrayOfSiteVolume" minOccurs="0"/>
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
    "siteVolumes"
})
@XmlRootElement(name = "SetConfSiteVolume")
public class SetConfSiteVolume {

    protected ConferenceId confId;
    protected ArrayOfSiteVolume siteVolumes;

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
     * ��ȡsiteVolumes���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSiteVolume }
     *     
     */
    public ArrayOfSiteVolume getSiteVolumes() {
        return siteVolumes;
    }

    /**
     * ����siteVolumes���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSiteVolume }
     *     
     */
    public void setSiteVolumes(ArrayOfSiteVolume value) {
        this.siteVolumes = value;
    }

}
