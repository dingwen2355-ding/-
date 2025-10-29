
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
 *         &lt;element name="AddConfTemplateResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="confTemplateId" type="{http://www.w3.org/2001/XMLSchema}long"/>
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
    "addConfTemplateResult",
    "confTemplateId"
})
@XmlRootElement(name = "AddConfTemplateResponse")
public class AddConfTemplateResponse {

    @XmlElement(name = "AddConfTemplateResult")
    protected SmcErr2 addConfTemplateResult;
    protected long confTemplateId;

    /**
     * ��ȡaddConfTemplateResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getAddConfTemplateResult() {
        return addConfTemplateResult;
    }

    /**
     * ����addConfTemplateResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setAddConfTemplateResult(SmcErr2 value) {
        this.addConfTemplateResult = value;
    }

    /**
     * ��ȡconfTemplateId���Ե�ֵ��
     * 
     */
    public long getConfTemplateId() {
        return confTemplateId;
    }

    /**
     * ����confTemplateId���Ե�ֵ��
     * 
     */
    public void setConfTemplateId(long value) {
        this.confTemplateId = value;
    }

}
