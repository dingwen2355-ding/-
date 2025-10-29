
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfDayOfWeek complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfDayOfWeek">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DayOfWeek" type="{http://smc.huawei.com/}DayOfWeek" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDayOfWeek", propOrder = {
    "dayOfWeek"
})
public class ArrayOfDayOfWeek {

    @XmlElement(name = "DayOfWeek")
    @XmlSchemaType(name = "string")
    protected List<DayOfWeek> dayOfWeek;

    /**
     * Gets the value of the dayOfWeek property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dayOfWeek property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDayOfWeek().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DayOfWeek }
     * 
     * 
     */
    public List<DayOfWeek> getDayOfWeek() {
        if (dayOfWeek == null) {
            dayOfWeek = new ArrayList<DayOfWeek>();
        }
        return this.dayOfWeek;
    }

}
