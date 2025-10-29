
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>SortItem complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="SortItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ItemIndex" type="{http://smc.huawei.com/}FilterColumn2"/>
 *         &lt;element name="Sort" type="{http://smc.huawei.com/}SortType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SortItem", propOrder = {
    "itemIndex",
    "sort"
})
public class SortItem {

    @XmlElement(name = "ItemIndex", required = true)
    @XmlSchemaType(name = "string")
    protected FilterColumn2 itemIndex;
    @XmlElement(name = "Sort", required = true)
    @XmlSchemaType(name = "string")
    protected SortType sort;

    /**
     * ��ȡitemIndex���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link FilterColumn2 }
     *     
     */
    public FilterColumn2 getItemIndex() {
        return itemIndex;
    }

    /**
     * ����itemIndex���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link FilterColumn2 }
     *     
     */
    public void setItemIndex(FilterColumn2 value) {
        this.itemIndex = value;
    }

    /**
     * ��ȡsort���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SortType }
     *     
     */
    public SortType getSort() {
        return sort;
    }

    /**
     * ����sort���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SortType }
     *     
     */
    public void setSort(SortType value) {
        this.sort = value;
    }

}
