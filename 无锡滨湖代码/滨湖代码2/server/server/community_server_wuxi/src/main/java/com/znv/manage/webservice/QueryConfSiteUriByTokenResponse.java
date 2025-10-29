
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
 *         &lt;element name="QueryConfSiteUriByTokenResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="siteCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "queryConfSiteUriByTokenResult",
    "siteCode"
})
@XmlRootElement(name = "QueryConfSiteUriByTokenResponse")
public class QueryConfSiteUriByTokenResponse {

    @XmlElement(name = "QueryConfSiteUriByTokenResult")
    protected SmcErr2 queryConfSiteUriByTokenResult;
    protected String siteCode;

    /**
     * ��ȡqueryConfSiteUriByTokenResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getQueryConfSiteUriByTokenResult() {
        return queryConfSiteUriByTokenResult;
    }

    /**
     * ����queryConfSiteUriByTokenResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setQueryConfSiteUriByTokenResult(SmcErr2 value) {
        this.queryConfSiteUriByTokenResult = value;
    }

    /**
     * ��ȡsiteCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiteCode() {
        return siteCode;
    }

    /**
     * ����siteCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiteCode(String value) {
        this.siteCode = value;
    }

}
