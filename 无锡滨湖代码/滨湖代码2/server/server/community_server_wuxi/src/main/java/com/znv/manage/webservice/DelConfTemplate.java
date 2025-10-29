
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
    "confTemplateId"
})
@XmlRootElement(name = "DelConfTemplate")
public class DelConfTemplate {

    protected long confTemplateId;

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
