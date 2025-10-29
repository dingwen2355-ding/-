
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>FilterBase complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="FilterBase">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ColumnIndex" type="{http://smc.huawei.com/}FilterColumn2"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FilterBase", propOrder = {
    "columnIndex"
})
@XmlSeeAlso({
    BooleanFilter.class,
    NumberFilterOfInt32.class,
    StringFilter.class,
    IPAddressFilter.class,
    EnumFilter.class,
    DateTimeFilter.class,
    NumberFilterOfDouble.class
})
public class FilterBase {

    @XmlElement(name = "ColumnIndex", required = true)
    @XmlSchemaType(name = "string")
    protected FilterColumn2 columnIndex;

    /**
     * ��ȡcolumnIndex���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link FilterColumn2 }
     *     
     */
    public FilterColumn2 getColumnIndex() {
        return columnIndex;
    }

    /**
     * ����columnIndex���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link FilterColumn2 }
     *     
     */
    public void setColumnIndex(FilterColumn2 value) {
        this.columnIndex = value;
    }

}
