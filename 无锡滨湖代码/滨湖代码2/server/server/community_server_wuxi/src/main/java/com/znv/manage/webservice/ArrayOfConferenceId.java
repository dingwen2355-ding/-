
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfConferenceId complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfConferenceId">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ConferenceId" type="{http://smc.huawei.com/}ConferenceId" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfConferenceId", propOrder = {
    "conferenceId"
})
public class ArrayOfConferenceId {

    @XmlElement(name = "ConferenceId", nillable = true)
    protected List<ConferenceId> conferenceId;

    /**
     * Gets the value of the conferenceId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the conferenceId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConferenceId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConferenceId }
     * 
     * 
     */
    public List<ConferenceId> getConferenceId() {
        if (conferenceId == null) {
            conferenceId = new ArrayList<ConferenceId>();
        }
        return this.conferenceId;
    }

}
