
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>MergeNodeInfo complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="MergeNodeInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UpConfId" type="{http://smc.huawei.com/}ConferenceId" minOccurs="0"/>
 *         &lt;element name="LowConfId" type="{http://smc.huawei.com/}ConferenceId" minOccurs="0"/>
 *         &lt;element name="CascadeNum" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MergeNodeInfo", propOrder = {
    "upConfId",
    "lowConfId",
    "cascadeNum"
})
public class MergeNodeInfo {

    @XmlElement(name = "UpConfId")
    protected ConferenceId upConfId;
    @XmlElement(name = "LowConfId")
    protected ConferenceId lowConfId;
    @XmlElement(name = "CascadeNum", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long cascadeNum;

    /**
     * ��ȡupConfId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ConferenceId }
     *     
     */
    public ConferenceId getUpConfId() {
        return upConfId;
    }

    /**
     * ����upConfId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ConferenceId }
     *     
     */
    public void setUpConfId(ConferenceId value) {
        this.upConfId = value;
    }

    /**
     * ��ȡlowConfId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ConferenceId }
     *     
     */
    public ConferenceId getLowConfId() {
        return lowConfId;
    }

    /**
     * ����lowConfId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ConferenceId }
     *     
     */
    public void setLowConfId(ConferenceId value) {
        this.lowConfId = value;
    }

    /**
     * ��ȡcascadeNum���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCascadeNum() {
        return cascadeNum;
    }

    /**
     * ����cascadeNum���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCascadeNum(Long value) {
        this.cascadeNum = value;
    }

}
