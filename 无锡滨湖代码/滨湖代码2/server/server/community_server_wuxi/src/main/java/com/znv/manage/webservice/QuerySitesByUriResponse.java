
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
 *         &lt;element name="QuerySitesByUriResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="sites" type="{http://smc.huawei.com/}ArrayOfSiteParamEx" minOccurs="0"/>
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
    "querySitesByUriResult",
    "sites"
})
@XmlRootElement(name = "QuerySitesByUriResponse")
public class QuerySitesByUriResponse {

    @XmlElement(name = "QuerySitesByUriResult")
    protected SmcErr2 querySitesByUriResult;
    protected ArrayOfSiteParamEx sites;

    /**
     * ��ȡquerySitesByUriResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getQuerySitesByUriResult() {
        return querySitesByUriResult;
    }

    /**
     * ����querySitesByUriResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setQuerySitesByUriResult(SmcErr2 value) {
        this.querySitesByUriResult = value;
    }

    /**
     * ��ȡsites���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSiteParamEx }
     *     
     */
    public ArrayOfSiteParamEx getSites() {
        return sites;
    }

    /**
     * ����sites���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSiteParamEx }
     *     
     */
    public void setSites(ArrayOfSiteParamEx value) {
        this.sites = value;
    }

}
