
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>DateTimeFilter complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="DateTimeFilter">
 *   &lt;complexContent>
 *     &lt;extension base="{http://smc.huawei.com/}FilterBase">
 *       &lt;sequence>
 *         &lt;element name="GreaterThanValue" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="LessThanValue" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DateTimeFilter", propOrder = {
    "greaterThanValue",
    "lessThanValue"
})
public class DateTimeFilter
    extends FilterBase
{

    @XmlElement(name = "GreaterThanValue", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar greaterThanValue;
    @XmlElement(name = "LessThanValue", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lessThanValue;

    /**
     * ��ȡgreaterThanValue���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getGreaterThanValue() {
        return greaterThanValue;
    }

    /**
     * ����greaterThanValue���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setGreaterThanValue(XMLGregorianCalendar value) {
        this.greaterThanValue = value;
    }

    /**
     * ��ȡlessThanValue���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLessThanValue() {
        return lessThanValue;
    }

    /**
     * ����lessThanValue���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLessThanValue(XMLGregorianCalendar value) {
        this.lessThanValue = value;
    }

}
