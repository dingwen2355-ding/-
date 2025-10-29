
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfAdhocConfFreeBusyStateEx2 complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfAdhocConfFreeBusyStateEx2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AdhocConfFreeBusyStateEx2" type="{http://smc.huawei.com/}AdhocConfFreeBusyStateEx2" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfAdhocConfFreeBusyStateEx2", propOrder = {
    "adhocConfFreeBusyStateEx2"
})
public class ArrayOfAdhocConfFreeBusyStateEx2 {

    @XmlElement(name = "AdhocConfFreeBusyStateEx2", nillable = true)
    protected List<AdhocConfFreeBusyStateEx2> adhocConfFreeBusyStateEx2;

    /**
     * Gets the value of the adhocConfFreeBusyStateEx2 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the adhocConfFreeBusyStateEx2 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdhocConfFreeBusyStateEx2().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AdhocConfFreeBusyStateEx2 }
     * 
     * 
     */
    public List<AdhocConfFreeBusyStateEx2> getAdhocConfFreeBusyStateEx2() {
        if (adhocConfFreeBusyStateEx2 == null) {
            adhocConfFreeBusyStateEx2 = new ArrayList<AdhocConfFreeBusyStateEx2>();
        }
        return this.adhocConfFreeBusyStateEx2;
    }

}
