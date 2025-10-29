
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfAdhocConferenceEx2 complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfAdhocConferenceEx2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AdhocConferenceEx2" type="{http://smc.huawei.com/}AdhocConferenceEx2" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfAdhocConferenceEx2", propOrder = {
    "adhocConferenceEx2"
})
public class ArrayOfAdhocConferenceEx2 {

    @XmlElement(name = "AdhocConferenceEx2", nillable = true)
    protected List<AdhocConferenceEx2> adhocConferenceEx2;

    /**
     * Gets the value of the adhocConferenceEx2 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the adhocConferenceEx2 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdhocConferenceEx2().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AdhocConferenceEx2 }
     * 
     * 
     */
    public List<AdhocConferenceEx2> getAdhocConferenceEx2() {
        if (adhocConferenceEx2 == null) {
            adhocConferenceEx2 = new ArrayList<AdhocConferenceEx2>();
        }
        return this.adhocConferenceEx2;
    }

}
