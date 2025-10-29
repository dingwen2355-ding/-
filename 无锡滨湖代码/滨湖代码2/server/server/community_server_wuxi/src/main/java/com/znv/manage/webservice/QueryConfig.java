
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>QueryConfig complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="QueryConfig">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SortItems" type="{http://smc.huawei.com/}ArrayOfSortItem" minOccurs="0"/>
 *         &lt;element name="Filters" type="{http://smc.huawei.com/}ArrayOfFilterBase" minOccurs="0"/>
 *         &lt;element name="FocusItem" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
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
@XmlType(name = "QueryConfig", propOrder = {
    "sortItems",
    "filters",
    "focusItem",
    "pageParam"
})
public class QueryConfig {

    @XmlElement(name = "SortItems")
    protected ArrayOfSortItem sortItems;
    @XmlElement(name = "Filters")
    protected ArrayOfFilterBase filters;
    @XmlElement(name = "FocusItem", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long focusItem;
    @XmlElement(name = "PageParam")
    protected PageParam pageParam;

    /**
     * ��ȡsortItems���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSortItem }
     *     
     */
    public ArrayOfSortItem getSortItems() {
        return sortItems;
    }

    /**
     * ����sortItems���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSortItem }
     *     
     */
    public void setSortItems(ArrayOfSortItem value) {
        this.sortItems = value;
    }

    /**
     * ��ȡfilters���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfFilterBase }
     *     
     */
    public ArrayOfFilterBase getFilters() {
        return filters;
    }

    /**
     * ����filters���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfFilterBase }
     *     
     */
    public void setFilters(ArrayOfFilterBase value) {
        this.filters = value;
    }

    /**
     * ��ȡfocusItem���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getFocusItem() {
        return focusItem;
    }

    /**
     * ����focusItem���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setFocusItem(Long value) {
        this.focusItem = value;
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
