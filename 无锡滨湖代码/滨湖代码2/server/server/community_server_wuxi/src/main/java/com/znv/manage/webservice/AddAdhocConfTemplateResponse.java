
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
 *         &lt;element name="AddAdhocConfTemplateResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="adhocConfTemplateId" type="{http://www.w3.org/2001/XMLSchema}long"/>
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
    "addAdhocConfTemplateResult",
    "adhocConfTemplateId"
})
@XmlRootElement(name = "AddAdhocConfTemplateResponse")
public class AddAdhocConfTemplateResponse {

    @XmlElement(name = "AddAdhocConfTemplateResult")
    protected SmcErr2 addAdhocConfTemplateResult;
    protected long adhocConfTemplateId;

    /**
     * ��ȡaddAdhocConfTemplateResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getAddAdhocConfTemplateResult() {
        return addAdhocConfTemplateResult;
    }

    /**
     * ����addAdhocConfTemplateResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setAddAdhocConfTemplateResult(SmcErr2 value) {
        this.addAdhocConfTemplateResult = value;
    }

    /**
     * ��ȡadhocConfTemplateId���Ե�ֵ��
     * 
     */
    public long getAdhocConfTemplateId() {
        return adhocConfTemplateId;
    }

    /**
     * ����adhocConfTemplateId���Ե�ֵ��
     * 
     */
    public void setAdhocConfTemplateId(long value) {
        this.adhocConfTemplateId = value;
    }

}
