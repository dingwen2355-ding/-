
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ConfStructure complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ConfStructure">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ConfId" type="{http://smc.huawei.com/}ConferenceId" minOccurs="0"/>
 *         &lt;element name="ConfName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LowConfs" type="{http://smc.huawei.com/}ArrayOfConfStructure" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConfStructure", propOrder = {
    "confId",
    "confName",
    "lowConfs"
})
public class ConfStructure {

    @XmlElement(name = "ConfId")
    protected ConferenceId confId;
    @XmlElement(name = "ConfName")
    protected String confName;
    @XmlElement(name = "LowConfs")
    protected ArrayOfConfStructure lowConfs;

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
     * ��ȡconfName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfName() {
        return confName;
    }

    /**
     * ����confName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfName(String value) {
        this.confName = value;
    }

    /**
     * ��ȡlowConfs���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfConfStructure }
     *     
     */
    public ArrayOfConfStructure getLowConfs() {
        return lowConfs;
    }

    /**
     * ����lowConfs���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfConfStructure }
     *     
     */
    public void setLowConfs(ArrayOfConfStructure value) {
        this.lowConfs = value;
    }

}
