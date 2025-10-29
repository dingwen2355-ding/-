
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
    "adhocConfTemplateId"
})
@XmlRootElement(name = "DelAdhocConfTemplate")
public class DelAdhocConfTemplate {

    protected long adhocConfTemplateId;

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
