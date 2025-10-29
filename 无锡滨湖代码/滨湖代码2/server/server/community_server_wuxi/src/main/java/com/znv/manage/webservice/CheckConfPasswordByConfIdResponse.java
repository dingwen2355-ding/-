
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
 *         &lt;element name="CheckConfPasswordByConfIdResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="confAccessInfo" type="{http://smc.huawei.com/}ConfAccessInfo" minOccurs="0"/>
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
    "checkConfPasswordByConfIdResult",
    "confAccessInfo"
})
@XmlRootElement(name = "CheckConfPasswordByConfIdResponse")
public class CheckConfPasswordByConfIdResponse {

    @XmlElement(name = "CheckConfPasswordByConfIdResult")
    protected SmcErr2 checkConfPasswordByConfIdResult;
    protected ConfAccessInfo confAccessInfo;

    /**
     * ��ȡcheckConfPasswordByConfIdResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getCheckConfPasswordByConfIdResult() {
        return checkConfPasswordByConfIdResult;
    }

    /**
     * ����checkConfPasswordByConfIdResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setCheckConfPasswordByConfIdResult(SmcErr2 value) {
        this.checkConfPasswordByConfIdResult = value;
    }

    /**
     * ��ȡconfAccessInfo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ConfAccessInfo }
     *     
     */
    public ConfAccessInfo getConfAccessInfo() {
        return confAccessInfo;
    }

    /**
     * ����confAccessInfo���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ConfAccessInfo }
     *     
     */
    public void setConfAccessInfo(ConfAccessInfo value) {
        this.confAccessInfo = value;
    }

}
