
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
 *         &lt;element name="confAccessCodes" type="{http://smc.huawei.com/}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="timeRange" type="{http://smc.huawei.com/}DateTimeRange" minOccurs="0"/>
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
    "confAccessCodes",
    "timeRange"
})
@XmlRootElement(name = "SynchAdhocConfFreeBusy")
public class SynchAdhocConfFreeBusy {

    protected ArrayOfString confAccessCodes;
    protected DateTimeRange timeRange;

    /**
     * ��ȡconfAccessCodes���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getConfAccessCodes() {
        return confAccessCodes;
    }

    /**
     * ����confAccessCodes���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setConfAccessCodes(ArrayOfString value) {
        this.confAccessCodes = value;
    }

    /**
     * ��ȡtimeRange���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link DateTimeRange }
     *     
     */
    public DateTimeRange getTimeRange() {
        return timeRange;
    }

    /**
     * ����timeRange���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link DateTimeRange }
     *     
     */
    public void setTimeRange(DateTimeRange value) {
        this.timeRange = value;
    }

}
