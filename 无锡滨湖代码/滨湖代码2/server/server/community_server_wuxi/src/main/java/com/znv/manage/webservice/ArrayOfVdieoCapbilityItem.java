
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfVdieoCapbilityItem complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfVdieoCapbilityItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VdieoCapbilityItem" type="{http://smc.huawei.com/}VdieoCapbilityItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfVdieoCapbilityItem", propOrder = {
    "vdieoCapbilityItem"
})
public class ArrayOfVdieoCapbilityItem {

    @XmlElement(name = "VdieoCapbilityItem", nillable = true)
    protected List<VdieoCapbilityItem> vdieoCapbilityItem;

    /**
     * Gets the value of the vdieoCapbilityItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vdieoCapbilityItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVdieoCapbilityItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VdieoCapbilityItem }
     * 
     * 
     */
    public List<VdieoCapbilityItem> getVdieoCapbilityItem() {
        if (vdieoCapbilityItem == null) {
            vdieoCapbilityItem = new ArrayList<VdieoCapbilityItem>();
        }
        return this.vdieoCapbilityItem;
    }

}
