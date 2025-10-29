
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfScheduledConfInfo complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfScheduledConfInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ScheduledConfInfo" type="{http://smc.huawei.com/}ScheduledConfInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfScheduledConfInfo", propOrder = {
    "scheduledConfInfo"
})
public class ArrayOfScheduledConfInfo {

    @XmlElement(name = "ScheduledConfInfo", nillable = true)
    protected List<ScheduledConfInfo> scheduledConfInfo;

    /**
     * Gets the value of the scheduledConfInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the scheduledConfInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getScheduledConfInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ScheduledConfInfo }
     * 
     * 
     */
    public List<ScheduledConfInfo> getScheduledConfInfo() {
        if (scheduledConfInfo == null) {
            scheduledConfInfo = new ArrayList<ScheduledConfInfo>();
        }
        return this.scheduledConfInfo;
    }

}
