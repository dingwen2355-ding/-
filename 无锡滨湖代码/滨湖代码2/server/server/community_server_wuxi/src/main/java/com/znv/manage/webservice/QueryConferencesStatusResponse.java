
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
 *         &lt;element name="QueryConferencesStatusResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="confsStatus" type="{http://smc.huawei.com/}ArrayOfConferenceStatus" minOccurs="0"/>
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
    "queryConferencesStatusResult",
    "confsStatus"
})
@XmlRootElement(name = "QueryConferencesStatusResponse")
public class QueryConferencesStatusResponse {

    @XmlElement(name = "QueryConferencesStatusResult")
    protected SmcErr2 queryConferencesStatusResult;
    protected ArrayOfConferenceStatus confsStatus;

    /**
     * ��ȡqueryConferencesStatusResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getQueryConferencesStatusResult() {
        return queryConferencesStatusResult;
    }

    /**
     * ����queryConferencesStatusResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setQueryConferencesStatusResult(SmcErr2 value) {
        this.queryConferencesStatusResult = value;
    }

    /**
     * ��ȡconfsStatus���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfConferenceStatus }
     *     
     */
    public ArrayOfConferenceStatus getConfsStatus() {
        return confsStatus;
    }

    /**
     * ����confsStatus���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfConferenceStatus }
     *     
     */
    public void setConfsStatus(ArrayOfConferenceStatus value) {
        this.confsStatus = value;
    }

}
