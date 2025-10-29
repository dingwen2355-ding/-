
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>SiteNetInfo complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="SiteNetInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SiteNetParam" type="{http://smc.huawei.com/}SiteNetParam" minOccurs="0"/>
 *         &lt;element name="NLogData" type="{http://smc.huawei.com/}NLogData" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SiteNetInfo", propOrder = {
    "siteNetParam",
    "nLogData"
})
public class SiteNetInfo {

    @XmlElement(name = "SiteNetParam")
    protected SiteNetParam siteNetParam;
    @XmlElement(name = "NLogData")
    protected NLogData nLogData;

    /**
     * ��ȡsiteNetParam���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SiteNetParam }
     *     
     */
    public SiteNetParam getSiteNetParam() {
        return siteNetParam;
    }

    /**
     * ����siteNetParam���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SiteNetParam }
     *     
     */
    public void setSiteNetParam(SiteNetParam value) {
        this.siteNetParam = value;
    }

    /**
     * ��ȡnLogData���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link NLogData }
     *     
     */
    public NLogData getNLogData() {
        return nLogData;
    }

    /**
     * ����nLogData���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link NLogData }
     *     
     */
    public void setNLogData(NLogData value) {
        this.nLogData = value;
    }

}
