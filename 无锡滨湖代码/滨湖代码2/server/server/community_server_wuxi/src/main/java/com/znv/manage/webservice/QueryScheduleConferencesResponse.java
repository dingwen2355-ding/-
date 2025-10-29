
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
 *         &lt;element name="QueryScheduleConferencesResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="conf" type="{http://smc.huawei.com/}ArrayOfConferenceStatus" minOccurs="0"/>
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
    "queryScheduleConferencesResult",
    "conf",
    "pageInfo"
})
@XmlRootElement(name = "QueryScheduleConferencesResponse")
public class QueryScheduleConferencesResponse {

    @XmlElement(name = "QueryScheduleConferencesResult")
    protected SmcErr2 queryScheduleConferencesResult;
    protected ArrayOfConferenceStatus conf;
    protected PagesInfo pageInfo;

    /**
     * ��ȡqueryScheduleConferencesResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getQueryScheduleConferencesResult() {
        return queryScheduleConferencesResult;
    }

    /**
     * ����queryScheduleConferencesResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setQueryScheduleConferencesResult(SmcErr2 value) {
        this.queryScheduleConferencesResult = value;
    }

    /**
     * ��ȡconf���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfConferenceStatus }
     *     
     */
    public ArrayOfConferenceStatus getConf() {
        return conf;
    }

    /**
     * ����conf���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfConferenceStatus }
     *     
     */
    public void setConf(ArrayOfConferenceStatus value) {
        this.conf = value;
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
