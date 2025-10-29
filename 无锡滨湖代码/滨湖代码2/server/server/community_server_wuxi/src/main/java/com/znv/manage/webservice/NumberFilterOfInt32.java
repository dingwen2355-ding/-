
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>NumberFilterOfInt32 complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="NumberFilterOfInt32">
 *   &lt;complexContent>
 *     &lt;extension base="{http://smc.huawei.com/}FilterBase">
 *       &lt;sequence>
 *         &lt;element name="GreaterThanValue" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="LessThanValue" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="EqualToValue" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NumberFilterOfInt32", propOrder = {
    "greaterThanValue",
    "lessThanValue",
    "equalToValue"
})
@XmlSeeAlso({
    IntFilter.class
})
public class NumberFilterOfInt32
    extends FilterBase
{

    @XmlElement(name = "GreaterThanValue", required = true, type = Integer.class, nillable = true)
    protected Integer greaterThanValue;
    @XmlElement(name = "LessThanValue", required = true, type = Integer.class, nillable = true)
    protected Integer lessThanValue;
    @XmlElement(name = "EqualToValue", required = true, type = Integer.class, nillable = true)
    protected Integer equalToValue;

    /**
     * ��ȡgreaterThanValue���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getGreaterThanValue() {
        return greaterThanValue;
    }

    /**
     * ����greaterThanValue���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setGreaterThanValue(Integer value) {
        this.greaterThanValue = value;
    }

    /**
     * ��ȡlessThanValue���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLessThanValue() {
        return lessThanValue;
    }

    /**
     * ����lessThanValue���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLessThanValue(Integer value) {
        this.lessThanValue = value;
    }

    /**
     * ��ȡequalToValue���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getEqualToValue() {
        return equalToValue;
    }

    /**
     * ����equalToValue���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setEqualToValue(Integer value) {
        this.equalToValue = value;
    }

}
