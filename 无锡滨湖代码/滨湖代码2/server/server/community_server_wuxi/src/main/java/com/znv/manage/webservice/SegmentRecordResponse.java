
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
 *         &lt;element name="SegmentRecordResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="recordAddr" type="{http://smc.huawei.com/}ConfRecordAddr" minOccurs="0"/>
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
    "segmentRecordResult",
    "recordAddr"
})
@XmlRootElement(name = "SegmentRecordResponse")
public class SegmentRecordResponse {

    @XmlElement(name = "SegmentRecordResult")
    protected SmcErr2 segmentRecordResult;
    protected ConfRecordAddr recordAddr;

    /**
     * ��ȡsegmentRecordResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getSegmentRecordResult() {
        return segmentRecordResult;
    }

    /**
     * ����segmentRecordResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setSegmentRecordResult(SmcErr2 value) {
        this.segmentRecordResult = value;
    }

    /**
     * ��ȡrecordAddr���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ConfRecordAddr }
     *     
     */
    public ConfRecordAddr getRecordAddr() {
        return recordAddr;
    }

    /**
     * ����recordAddr���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ConfRecordAddr }
     *     
     */
    public void setRecordAddr(ConfRecordAddr value) {
        this.recordAddr = value;
    }

}
