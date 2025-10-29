
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
 *         &lt;element name="QuerySitesResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="sites" type="{http://smc.huawei.com/}ArrayOfSiteParamEx" minOccurs="0"/>
 *         &lt;element name="pageInfo" type="{http://smc.huawei.com/}PagesInfo" minOccurs="0"/>
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
    "querySitesResult",
    "sites",
    "pageInfo"
})
@XmlRootElement(name = "QuerySitesResponse")
public class QuerySitesResponse {

    @XmlElement(name = "QuerySitesResult")
    protected SmcErr2 querySitesResult;
    protected ArrayOfSiteParamEx sites;
    protected PagesInfo pageInfo;

    /**
     * ��ȡquerySitesResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getQuerySitesResult() {
        return querySitesResult;
    }

    /**
     * ����querySitesResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setQuerySitesResult(SmcErr2 value) {
        this.querySitesResult = value;
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

    /**
     * ��ȡpageInfo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link PagesInfo }
     *     
     */
    public PagesInfo getPageInfo() {
        return pageInfo;
    }

    /**
     * ����pageInfo���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link PagesInfo }
     *     
     */
    public void setPageInfo(PagesInfo value) {
        this.pageInfo = value;
    }

}
