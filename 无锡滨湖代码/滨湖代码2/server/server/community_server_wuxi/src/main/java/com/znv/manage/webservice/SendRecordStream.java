
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
 *         &lt;element name="confId" type="{http://smc.huawei.com/}ConferenceId" minOccurs="0"/>
 *         &lt;element name="operType" type="{http://smc.huawei.com/}ConfCtrlOperationType"/>
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
    "confId",
    "operType"
})
@XmlRootElement(name = "SendRecordStream")
public class SendRecordStream {

    protected ConferenceId confId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected ConfCtrlOperationType operType;

    /**
     * ��ȡconfId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ConferenceId }
     *     
     */
    public ConferenceId getConfId() {
        return confId;
    }

    /**
     * ����confId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ConferenceId }
     *     
     */
    public void setConfId(ConferenceId value) {
        this.confId = value;
    }

    /**
     * ��ȡoperType���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ConfCtrlOperationType }
     *     
     */
    public ConfCtrlOperationType getOperType() {
        return operType;
    }

    /**
     * ����operType���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ConfCtrlOperationType }
     *     
     */
    public void setOperType(ConfCtrlOperationType value) {
        this.operType = value;
    }

}
