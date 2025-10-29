
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>PointToPointCDRQueryConfig complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="PointToPointCDRQueryConfig">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TimeRange" type="{http://smc.huawei.com/}DateTimeRange" minOccurs="0"/>
 *         &lt;element name="EndPoint" type="{http://smc.huawei.com/}CallEndPoint" minOccurs="0"/>
 *         &lt;element name="PageParam" type="{http://smc.huawei.com/}PageParam" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PointToPointCDRQueryConfig", propOrder = {
    "timeRange",
    "endPoint",
    "pageParam"
})
public class PointToPointCDRQueryConfig {

    @XmlElement(name = "TimeRange")
    protected DateTimeRange timeRange;
    @XmlElement(name = "EndPoint")
    protected CallEndPoint endPoint;
    @XmlElement(name = "PageParam")
    protected PageParam pageParam;

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

    /**
     * ��ȡendPoint���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link CallEndPoint }
     *     
     */
    public CallEndPoint getEndPoint() {
        return endPoint;
    }

    /**
     * ����endPoint���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link CallEndPoint }
     *     
     */
    public void setEndPoint(CallEndPoint value) {
        this.endPoint = value;
    }

    /**
     * ��ȡpageParam���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link PageParam }
     *     
     */
    public PageParam getPageParam() {
        return pageParam;
    }

    /**
     * ����pageParam���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link PageParam }
     *     
     */
    public void setPageParam(PageParam value) {
        this.pageParam = value;
    }

}
