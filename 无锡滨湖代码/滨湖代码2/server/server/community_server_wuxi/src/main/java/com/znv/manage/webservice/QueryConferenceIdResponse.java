
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
 *         &lt;element name="QueryConferenceIdResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="confIds" type="{http://smc.huawei.com/}ArrayOfConferenceId" minOccurs="0"/>
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
    "queryConferenceIdResult",
    "confIds"
})
@XmlRootElement(name = "QueryConferenceIdResponse")
public class QueryConferenceIdResponse {

    @XmlElement(name = "QueryConferenceIdResult")
    protected SmcErr2 queryConferenceIdResult;
    protected ArrayOfConferenceId confIds;

    /**
     * ��ȡqueryConferenceIdResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getQueryConferenceIdResult() {
        return queryConferenceIdResult;
    }

    /**
     * ����queryConferenceIdResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setQueryConferenceIdResult(SmcErr2 value) {
        this.queryConferenceIdResult = value;
    }

    /**
     * ��ȡconfIds���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfConferenceId }
     *     
     */
    public ArrayOfConferenceId getConfIds() {
        return confIds;
    }

    /**
     * ����confIds���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfConferenceId }
     *     
     */
    public void setConfIds(ArrayOfConferenceId value) {
        this.confIds = value;
    }

}
