
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>NumberFilterOfDouble complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="NumberFilterOfDouble">
 *   &lt;complexContent>
 *     &lt;extension base="{http://smc.huawei.com/}FilterBase">
 *       &lt;sequence>
 *         &lt;element name="GreaterThanValue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="LessThanValue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="EqualToValue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NumberFilterOfDouble", propOrder = {
    "greaterThanValue",
    "lessThanValue",
    "equalToValue"
})
@XmlSeeAlso({
    DoubleFilter.class
})
public class NumberFilterOfDouble
    extends FilterBase
{

    @XmlElement(name = "GreaterThanValue", required = true, type = Double.class, nillable = true)
    protected Double greaterThanValue;
    @XmlElement(name = "LessThanValue", required = true, type = Double.class, nillable = true)
    protected Double lessThanValue;
    @XmlElement(name = "EqualToValue", required = true, type = Double.class, nillable = true)
    protected Double equalToValue;

    /**
     * ��ȡgreaterThanValue���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getGreaterThanValue() {
        return greaterThanValue;
    }

    /**
     * ����greaterThanValue���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setGreaterThanValue(Double value) {
        this.greaterThanValue = value;
    }

    /**
     * ��ȡlessThanValue���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLessThanValue() {
        return lessThanValue;
    }

    /**
     * ����lessThanValue���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLessThanValue(Double value) {
        this.lessThanValue = value;
    }

    /**
     * ��ȡequalToValue���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getEqualToValue() {
        return equalToValue;
    }

    /**
     * ����equalToValue���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setEqualToValue(Double value) {
        this.equalToValue = value;
    }

}
