
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
 *         &lt;element name="QueryAdhocConferencesResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="adhocConferences" type="{http://smc.huawei.com/}ArrayOfAdhocConferenceEx2" minOccurs="0"/>
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
    "queryAdhocConferencesResult",
    "adhocConferences",
    "pageInfo"
})
@XmlRootElement(name = "QueryAdhocConferencesResponse")
public class QueryAdhocConferencesResponse {

    @XmlElement(name = "QueryAdhocConferencesResult")
    protected SmcErr2 queryAdhocConferencesResult;
    protected ArrayOfAdhocConferenceEx2 adhocConferences;
    protected PagesInfo pageInfo;

    /**
     * ��ȡqueryAdhocConferencesResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getQueryAdhocConferencesResult() {
        return queryAdhocConferencesResult;
    }

    /**
     * ����queryAdhocConferencesResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setQueryAdhocConferencesResult(SmcErr2 value) {
        this.queryAdhocConferencesResult = value;
    }

    /**
     * ��ȡadhocConferences���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAdhocConferenceEx2 }
     *     
     */
    public ArrayOfAdhocConferenceEx2 getAdhocConferences() {
        return adhocConferences;
    }

    /**
     * ����adhocConferences���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAdhocConferenceEx2 }
     *     
     */
    public void setAdhocConferences(ArrayOfAdhocConferenceEx2 value) {
        this.adhocConferences = value;
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
