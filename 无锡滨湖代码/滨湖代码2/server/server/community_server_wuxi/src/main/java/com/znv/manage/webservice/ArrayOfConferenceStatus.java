
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfConferenceStatus complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfConferenceStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ConferenceStatus" type="{http://smc.huawei.com/}ConferenceStatus" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfConferenceStatus", propOrder = {
    "conferenceStatus"
})
public class ArrayOfConferenceStatus {

    @XmlElement(name = "ConferenceStatus", nillable = true)
    protected List<ConferenceStatus> conferenceStatus;

    /**
     * Gets the value of the conferenceStatus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the conferenceStatus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConferenceStatus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConferenceStatus }
     * 
     * 
     */
    public List<ConferenceStatus> getConferenceStatus() {
        if (conferenceStatus == null) {
            conferenceStatus = new ArrayList<ConferenceStatus>();
        }
        return this.conferenceStatus;
    }

}
