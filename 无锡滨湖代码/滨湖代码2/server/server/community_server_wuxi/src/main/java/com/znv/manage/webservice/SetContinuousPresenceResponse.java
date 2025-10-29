
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
 *         &lt;element name="SetContinuousPresenceResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
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
    "setContinuousPresenceResult"
})
@XmlRootElement(name = "SetContinuousPresenceResponse")
public class SetContinuousPresenceResponse {

    @XmlElement(name = "SetContinuousPresenceResult")
    protected SmcErr2 setContinuousPresenceResult;

    /**
     * ��ȡsetContinuousPresenceResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getSetContinuousPresenceResult() {
        return setContinuousPresenceResult;
    }

    /**
     * ����setContinuousPresenceResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setSetContinuousPresenceResult(SmcErr2 value) {
        this.setContinuousPresenceResult = value;
    }

}
