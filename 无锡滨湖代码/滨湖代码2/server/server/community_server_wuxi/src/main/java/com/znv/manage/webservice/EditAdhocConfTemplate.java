
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="adhocConfTemplate" type="{http://smc.huawei.com/}AdhocConfTempplateParam" minOccurs="0"/>
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
    "adhocConfTemplate"
})
@XmlRootElement(name = "EditAdhocConfTemplate")
public class EditAdhocConfTemplate {

    protected AdhocConfTempplateParam adhocConfTemplate;

    /**
     * ��ȡadhocConfTemplate���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link AdhocConfTempplateParam }
     *     
     */
    public AdhocConfTempplateParam getAdhocConfTemplate() {
        return adhocConfTemplate;
    }

    /**
     * ����adhocConfTemplate���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link AdhocConfTempplateParam }
     *     
     */
    public void setAdhocConfTemplate(AdhocConfTempplateParam value) {
        this.adhocConfTemplate = value;
    }

}
