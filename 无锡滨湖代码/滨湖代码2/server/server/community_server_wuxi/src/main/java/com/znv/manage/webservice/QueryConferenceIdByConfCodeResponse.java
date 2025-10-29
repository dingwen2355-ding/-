
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
 *         &lt;element name="QueryConferenceIdByConfCodeResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="confID" type="{http://smc.huawei.com/}ConferenceId" minOccurs="0"/>
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
    "queryConferenceIdByConfCodeResult",
    "confID"
})
@XmlRootElement(name = "QueryConferenceIdByConfCodeResponse")
public class QueryConferenceIdByConfCodeResponse {

    @XmlElement(name = "QueryConferenceIdByConfCodeResult")
    protected SmcErr2 queryConferenceIdByConfCodeResult;
    protected ConferenceId confID;

    /**
     * ��ȡqueryConferenceIdByConfCodeResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getQueryConferenceIdByConfCodeResult() {
        return queryConferenceIdByConfCodeResult;
    }

    /**
     * ����queryConferenceIdByConfCodeResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setQueryConferenceIdByConfCodeResult(SmcErr2 value) {
        this.queryConferenceIdByConfCodeResult = value;
    }

    /**
     * ��ȡconfID���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ConferenceId }
     *     
     */
    public ConferenceId getConfID() {
        return confID;
    }

    /**
     * ����confID���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ConferenceId }
     *     
     */
    public void setConfID(ConferenceId value) {
        this.confID = value;
    }

}
