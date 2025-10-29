
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfMcuResourceItem complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfMcuResourceItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="McuResourceItem" type="{http://smc.huawei.com/}McuResourceItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfMcuResourceItem", propOrder = {
    "mcuResourceItem"
})
public class ArrayOfMcuResourceItem {

    @XmlElement(name = "McuResourceItem", nillable = true)
    protected List<McuResourceItem> mcuResourceItem;

    /**
     * Gets the value of the mcuResourceItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mcuResourceItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMcuResourceItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link McuResourceItem }
     * 
     * 
     */
    public List<McuResourceItem> getMcuResourceItem() {
        if (mcuResourceItem == null) {
            mcuResourceItem = new ArrayList<McuResourceItem>();
        }
        return this.mcuResourceItem;
    }

}
