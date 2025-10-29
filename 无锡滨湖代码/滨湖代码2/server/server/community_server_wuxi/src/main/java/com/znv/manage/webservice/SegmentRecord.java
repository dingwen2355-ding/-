
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
 *         &lt;element name="recordType" type="{http://smc.huawei.com/}RseOperateType"/>
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
    "recordType"
})
@XmlRootElement(name = "SegmentRecord")
public class SegmentRecord {

    protected ConferenceId confId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected RseOperateType recordType;

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
     * ��ȡrecordType���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link RseOperateType }
     *     
     */
    public RseOperateType getRecordType() {
        return recordType;
    }

    /**
     * ����recordType���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link RseOperateType }
     *     
     */
    public void setRecordType(RseOperateType value) {
        this.recordType = value;
    }

}
