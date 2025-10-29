
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfFreeBusy2 complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfFreeBusy2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FreeBusy2" type="{http://smc.huawei.com/}FreeBusy2" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfFreeBusy2", propOrder = {
    "freeBusy2"
})
public class ArrayOfFreeBusy2 {

    @XmlElement(name = "FreeBusy2", nillable = true)
    protected List<FreeBusy2> freeBusy2;

    /**
     * Gets the value of the freeBusy2 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the freeBusy2 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFreeBusy2().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FreeBusy2 }
     * 
     * 
     */
    public List<FreeBusy2> getFreeBusy2() {
        if (freeBusy2 == null) {
            freeBusy2 = new ArrayList<FreeBusy2>();
        }
        return this.freeBusy2;
    }

}
