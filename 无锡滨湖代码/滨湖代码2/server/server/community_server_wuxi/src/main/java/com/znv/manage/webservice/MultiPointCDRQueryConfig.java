
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>MultiPointCDRQueryConfig complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="MultiPointCDRQueryConfig">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TimeRange" type="{http://smc.huawei.com/}DateTimeRange" minOccurs="0"/>
 *         &lt;element name="SiteUri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PageParam" type="{http://smc.huawei.com/}PageParam" minOccurs="0"/>
 *         &lt;element name="LeaveTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MultiPointCDRQueryConfig", propOrder = {
    "timeRange",
    "siteUri",
    "pageParam",
    "leaveTime"
})
public class MultiPointCDRQueryConfig {

    @XmlElement(name = "TimeRange")
    protected DateTimeRange timeRange;
    @XmlElement(name = "SiteUri")
    protected String siteUri;
    @XmlElement(name = "PageParam")
    protected PageParam pageParam;
    @XmlElement(name = "LeaveTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar leaveTime;

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
     * ��ȡsiteUri���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiteUri() {
        return siteUri;
    }

    /**
     * ����siteUri���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiteUri(String value) {
        this.siteUri = value;
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

    /**
     * ��ȡleaveTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLeaveTime() {
        return leaveTime;
    }

    /**
     * ����leaveTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLeaveTime(XMLGregorianCalendar value) {
        this.leaveTime = value;
    }

}
