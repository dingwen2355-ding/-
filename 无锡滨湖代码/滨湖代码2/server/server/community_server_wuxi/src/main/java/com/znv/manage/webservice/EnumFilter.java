
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>EnumFilter complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="EnumFilter">
 *   &lt;complexContent>
 *     &lt;extension base="{http://smc.huawei.com/}FilterBase">
 *       &lt;sequence>
 *         &lt;element name="Values" type="{http://smc.huawei.com/}ArrayOfInt" minOccurs="0"/>
 *         &lt;element name="EnumName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EnumFilter", propOrder = {
    "values",
    "enumName"
})
public class EnumFilter
    extends FilterBase
{

    @XmlElement(name = "Values")
    protected ArrayOfInt values;
    @XmlElement(name = "EnumName")
    protected String enumName;

    /**
     * ��ȡvalues���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfInt }
     *     
     */
    public ArrayOfInt getValues() {
        return values;
    }

    /**
     * ����values���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfInt }
     *     
     */
    public void setValues(ArrayOfInt value) {
        this.values = value;
    }

    /**
     * ��ȡenumName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnumName() {
        return enumName;
    }

    /**
     * ����enumName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnumName(String value) {
        this.enumName = value;
    }

}
