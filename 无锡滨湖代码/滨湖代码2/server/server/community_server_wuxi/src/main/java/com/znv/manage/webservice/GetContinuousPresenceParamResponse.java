
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
 *         &lt;element name="GetContinuousPresenceParamResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="cpParam" type="{http://smc.huawei.com/}ContinuousPresenceParam" minOccurs="0"/>
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
    "getContinuousPresenceParamResult",
    "cpParam"
})
@XmlRootElement(name = "GetContinuousPresenceParamResponse")
public class GetContinuousPresenceParamResponse {

    @XmlElement(name = "GetContinuousPresenceParamResult")
    protected SmcErr2 getContinuousPresenceParamResult;
    protected ContinuousPresenceParam cpParam;

    /**
     * ��ȡgetContinuousPresenceParamResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getGetContinuousPresenceParamResult() {
        return getContinuousPresenceParamResult;
    }

    /**
     * ����getContinuousPresenceParamResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setGetContinuousPresenceParamResult(SmcErr2 value) {
        this.getContinuousPresenceParamResult = value;
    }

    /**
     * ��ȡcpParam���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ContinuousPresenceParam }
     *     
     */
    public ContinuousPresenceParam getCpParam() {
        return cpParam;
    }

    /**
     * ����cpParam���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ContinuousPresenceParam }
     *     
     */
    public void setCpParam(ContinuousPresenceParam value) {
        this.cpParam = value;
    }

}
