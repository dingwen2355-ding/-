
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
 *         &lt;element name="SetVSAttrCtrlResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
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
    "setVSAttrCtrlResult"
})
@XmlRootElement(name = "SetVSAttrCtrlResponse")
public class SetVSAttrCtrlResponse {

    @XmlElement(name = "SetVSAttrCtrlResult")
    protected SmcErr2 setVSAttrCtrlResult;

    /**
     * ��ȡsetVSAttrCtrlResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getSetVSAttrCtrlResult() {
        return setVSAttrCtrlResult;
    }

    /**
     * ����setVSAttrCtrlResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setSetVSAttrCtrlResult(SmcErr2 value) {
        this.setVSAttrCtrlResult = value;
    }

}
