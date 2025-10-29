
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
 *         &lt;element name="QueryMcuInfoResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="mcus" type="{http://smc.huawei.com/}ArrayOfMcuInfo" minOccurs="0"/>
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
    "queryMcuInfoResult",
    "mcus",
    "pageInfo"
})
@XmlRootElement(name = "QueryMcuInfoResponse")
public class QueryMcuInfoResponse {

    @XmlElement(name = "QueryMcuInfoResult")
    protected SmcErr2 queryMcuInfoResult;
    protected ArrayOfMcuInfo mcus;
    protected PagesInfo pageInfo;

    /**
     * ��ȡqueryMcuInfoResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getQueryMcuInfoResult() {
        return queryMcuInfoResult;
    }

    /**
     * ����queryMcuInfoResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setQueryMcuInfoResult(SmcErr2 value) {
        this.queryMcuInfoResult = value;
    }

    /**
     * ��ȡmcus���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfMcuInfo }
     *     
     */
    public ArrayOfMcuInfo getMcus() {
        return mcus;
    }

    /**
     * ����mcus���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfMcuInfo }
     *     
     */
    public void setMcus(ArrayOfMcuInfo value) {
        this.mcus = value;
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
