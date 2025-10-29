
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


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
 *         &lt;element name="QueryConfSitesStatusResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="sitesStatus" type="{http://smc.huawei.com/}ArrayOfSiteStatus" minOccurs="0"/>
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
    "queryConfSitesStatusResult",
    "sitesStatus"
})
@XmlRootElement(name = "QueryConfSitesStatusResponse")
public class QueryConfSitesStatusResponse {

    @XmlElement(name = "QueryConfSitesStatusResult")
    protected SmcErr2 queryConfSitesStatusResult;
    protected ArrayOfSiteStatus sitesStatus;

    /**
     * ��ȡqueryConfSitesStatusResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getQueryConfSitesStatusResult() {
        return queryConfSitesStatusResult;
    }

    /**
     * ����queryConfSitesStatusResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setQueryConfSitesStatusResult(SmcErr2 value) {
        this.queryConfSitesStatusResult = value;
    }

    /**
     * ��ȡsitesStatus���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSiteStatus }
     *     
     */
    public ArrayOfSiteStatus getSitesStatus() {
        return sitesStatus;
    }

    /**
     * ����sitesStatus���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSiteStatus }
     *     
     */
    public void setSitesStatus(ArrayOfSiteStatus value) {
        this.sitesStatus = value;
    }

}
