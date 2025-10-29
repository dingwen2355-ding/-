
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfScheduleSiteParam2 complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfScheduleSiteParam2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ScheduleSiteParam2" type="{http://smc.huawei.com/}ScheduleSiteParam2" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfScheduleSiteParam2", propOrder = {
    "scheduleSiteParam2"
})
public class ArrayOfScheduleSiteParam2 {

    @XmlElement(name = "ScheduleSiteParam2", nillable = true)
    protected List<ScheduleSiteParam2> scheduleSiteParam2;

    /**
     * Gets the value of the scheduleSiteParam2 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the scheduleSiteParam2 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getScheduleSiteParam2().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ScheduleSiteParam2 }
     * 
     * 
     */
    public List<ScheduleSiteParam2> getScheduleSiteParam2() {
        if (scheduleSiteParam2 == null) {
            scheduleSiteParam2 = new ArrayList<ScheduleSiteParam2>();
        }
        return this.scheduleSiteParam2;
    }

}
