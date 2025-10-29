
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
 *         &lt;element name="DelSiteInScheduledConfResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
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
    "delSiteInScheduledConfResult"
})
@XmlRootElement(name = "DelSiteInScheduledConfResponse")
public class DelSiteInScheduledConfResponse {

    @XmlElement(name = "DelSiteInScheduledConfResult")
    protected SmcErr2 delSiteInScheduledConfResult;

    /**
     * ��ȡdelSiteInScheduledConfResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getDelSiteInScheduledConfResult() {
        return delSiteInScheduledConfResult;
    }

    /**
     * ����delSiteInScheduledConfResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setDelSiteInScheduledConfResult(SmcErr2 value) {
        this.delSiteInScheduledConfResult = value;
    }

}
