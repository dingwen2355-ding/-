
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
 *         &lt;element name="QueryConfSitesStatus4UCResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
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
    "queryConfSitesStatus4UCResult",
    "sitesStatus"
})
@XmlRootElement(name = "QueryConfSitesStatus4UCResponse")
public class QueryConfSitesStatus4UCResponse {

    @XmlElement(name = "QueryConfSitesStatus4UCResult")
    protected SmcErr2 queryConfSitesStatus4UCResult;
    protected ArrayOfSiteStatus sitesStatus;

    /**
     * ��ȡqueryConfSitesStatus4UCResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getQueryConfSitesStatus4UCResult() {
        return queryConfSitesStatus4UCResult;
    }

    /**
     * ����queryConfSitesStatus4UCResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setQueryConfSitesStatus4UCResult(SmcErr2 value) {
        this.queryConfSitesStatus4UCResult = value;
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
