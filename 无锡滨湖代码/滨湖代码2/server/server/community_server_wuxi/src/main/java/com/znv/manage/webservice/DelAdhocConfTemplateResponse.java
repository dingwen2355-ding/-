
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
 *         &lt;element name="DelAdhocConfTemplateResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
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
    "delAdhocConfTemplateResult"
})
@XmlRootElement(name = "DelAdhocConfTemplateResponse")
public class DelAdhocConfTemplateResponse {

    @XmlElement(name = "DelAdhocConfTemplateResult")
    protected SmcErr2 delAdhocConfTemplateResult;

    /**
     * ��ȡdelAdhocConfTemplateResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getDelAdhocConfTemplateResult() {
        return delAdhocConfTemplateResult;
    }

    /**
     * ����delAdhocConfTemplateResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setDelAdhocConfTemplateResult(SmcErr2 value) {
        this.delAdhocConfTemplateResult = value;
    }

}
