
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
 *         &lt;element name="mcus" type="{http://smc.huawei.com/}ArrayOfUnsignedInt" minOccurs="0"/>
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
    "mcus",
    "timeRange"
})
@XmlRootElement(name = "QueryMcuFutureResource")
public class QueryMcuFutureResource {

    protected ArrayOfUnsignedInt mcus;
    protected DateTimeRange timeRange;

    /**
     * ��ȡmcus���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfUnsignedInt }
     *     
     */
    public ArrayOfUnsignedInt getMcus() {
        return mcus;
    }

    /**
     * ����mcus���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfUnsignedInt }
     *     
     */
    public void setMcus(ArrayOfUnsignedInt value) {
        this.mcus = value;
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
