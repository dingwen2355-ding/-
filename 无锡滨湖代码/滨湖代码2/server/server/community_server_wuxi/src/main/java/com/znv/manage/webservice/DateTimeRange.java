
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>DateTimeRange complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="DateTimeRange">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Start" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Span" type="{http://smc.huawei.com/}TimeSpanEx" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DateTimeRange", propOrder = {
    "start",
    "span"
})
public class DateTimeRange {

    @XmlElement(name = "Start", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar start;
    @XmlElement(name = "Span")
    protected TimeSpanEx span;

    /**
     * ��ȡstart���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStart() {
        return start;
    }

    /**
     * ����start���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStart(XMLGregorianCalendar value) {
        this.start = value;
    }

    /**
     * ��ȡspan���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link TimeSpanEx }
     *     
     */
    public TimeSpanEx getSpan() {
        return span;
    }

    /**
     * ����span���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link TimeSpanEx }
     *     
     */
    public void setSpan(TimeSpanEx value) {
        this.span = value;
    }

}
