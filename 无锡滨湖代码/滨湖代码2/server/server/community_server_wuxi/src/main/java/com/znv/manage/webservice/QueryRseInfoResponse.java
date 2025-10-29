
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
 *         &lt;element name="QueryRseInfoResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="rses" type="{http://smc.huawei.com/}ArrayOfRseInfo" minOccurs="0"/>
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
    "queryRseInfoResult",
    "rses",
    "pageInfo"
})
@XmlRootElement(name = "QueryRseInfoResponse")
public class QueryRseInfoResponse {

    @XmlElement(name = "QueryRseInfoResult")
    protected SmcErr2 queryRseInfoResult;
    protected ArrayOfRseInfo rses;
    protected PagesInfo pageInfo;

    /**
     * ��ȡqueryRseInfoResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getQueryRseInfoResult() {
        return queryRseInfoResult;
    }

    /**
     * ����queryRseInfoResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setQueryRseInfoResult(SmcErr2 value) {
        this.queryRseInfoResult = value;
    }

    /**
     * ��ȡrses���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRseInfo }
     *     
     */
    public ArrayOfRseInfo getRses() {
        return rses;
    }

    /**
     * ����rses���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRseInfo }
     *     
     */
    public void setRses(ArrayOfRseInfo value) {
        this.rses = value;
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
