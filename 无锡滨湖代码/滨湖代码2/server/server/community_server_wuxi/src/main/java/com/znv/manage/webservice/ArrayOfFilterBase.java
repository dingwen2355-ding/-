
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfFilterBase complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfFilterBase">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FilterBase" type="{http://smc.huawei.com/}FilterBase" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfFilterBase", propOrder = {
    "filterBase"
})
public class ArrayOfFilterBase {

    @XmlElement(name = "FilterBase", nillable = true)
    protected List<FilterBase> filterBase;

    /**
     * Gets the value of the filterBase property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the filterBase property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFilterBase().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FilterBase }
     * 
     * 
     */
    public List<FilterBase> getFilterBase() {
        if (filterBase == null) {
            filterBase = new ArrayList<FilterBase>();
        }
        return this.filterBase;
    }

}
