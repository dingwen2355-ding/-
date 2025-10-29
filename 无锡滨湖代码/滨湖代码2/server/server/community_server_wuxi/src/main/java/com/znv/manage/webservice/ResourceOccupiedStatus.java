
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ResourceOccupiedStatus complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ResourceOccupiedStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FreeCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TotalCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="FreeCore" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="TotalCore" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="TimeRange" type="{http://smc.huawei.com/}DateTimeRange" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResourceOccupiedStatus", propOrder = {
    "freeCount",
    "totalCount",
    "freeCore",
    "totalCore",
    "timeRange"
})
public class ResourceOccupiedStatus {

    @XmlElement(name = "FreeCount")
    protected int freeCount;
    @XmlElement(name = "TotalCount")
    protected int totalCount;
    @XmlElement(name = "FreeCore", required = true, type = Double.class, nillable = true)
    protected Double freeCore;
    @XmlElement(name = "TotalCore", required = true, type = Double.class, nillable = true)
    protected Double totalCore;
    @XmlElement(name = "TimeRange")
    protected DateTimeRange timeRange;

    /**
     * ��ȡfreeCount���Ե�ֵ��
     * 
     */
    public int getFreeCount() {
        return freeCount;
    }

    /**
     * ����freeCount���Ե�ֵ��
     * 
     */
    public void setFreeCount(int value) {
        this.freeCount = value;
    }

    /**
     * ��ȡtotalCount���Ե�ֵ��
     * 
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * ����totalCount���Ե�ֵ��
     * 
     */
    public void setTotalCount(int value) {
        this.totalCount = value;
    }

    /**
     * ��ȡfreeCore���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getFreeCore() {
        return freeCore;
    }

    /**
     * ����freeCore���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setFreeCore(Double value) {
        this.freeCore = value;
    }

    /**
     * ��ȡtotalCore���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTotalCore() {
        return totalCore;
    }

    /**
     * ����totalCore���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTotalCore(Double value) {
        this.totalCore = value;
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
