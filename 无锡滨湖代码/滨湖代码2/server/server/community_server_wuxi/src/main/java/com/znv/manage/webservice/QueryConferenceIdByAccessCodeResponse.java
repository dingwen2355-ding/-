
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
 *         &lt;element name="QueryConferenceIdByAccessCodeResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
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
    "queryConferenceIdByAccessCodeResult",
    "confID"
})
@XmlRootElement(name = "QueryConferenceIdByAccessCodeResponse")
public class QueryConferenceIdByAccessCodeResponse {

    @XmlElement(name = "QueryConferenceIdByAccessCodeResult")
    protected SmcErr2 queryConferenceIdByAccessCodeResult;
    protected ConferenceId confID;

    /**
     * ��ȡqueryConferenceIdByAccessCodeResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getQueryConferenceIdByAccessCodeResult() {
        return queryConferenceIdByAccessCodeResult;
    }

    /**
     * ����queryConferenceIdByAccessCodeResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setQueryConferenceIdByAccessCodeResult(SmcErr2 value) {
        this.queryConferenceIdByAccessCodeResult = value;
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
