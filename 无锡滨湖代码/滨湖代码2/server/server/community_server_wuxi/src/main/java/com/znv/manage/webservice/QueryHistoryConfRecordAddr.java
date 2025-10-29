
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
 *         &lt;element name="confIds" type="{http://smc.huawei.com/}ArrayOfConferenceId" minOccurs="0"/>
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
    "confIds"
})
@XmlRootElement(name = "QueryHistoryConfRecordAddr")
public class QueryHistoryConfRecordAddr {

    protected ArrayOfConferenceId confIds;

    /**
     * ��ȡconfIds���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfConferenceId }
     *     
     */
    public ArrayOfConferenceId getConfIds() {
        return confIds;
    }

    /**
     * ����confIds���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfConferenceId }
     *     
     */
    public void setConfIds(ArrayOfConferenceId value) {
        this.confIds = value;
    }

}
