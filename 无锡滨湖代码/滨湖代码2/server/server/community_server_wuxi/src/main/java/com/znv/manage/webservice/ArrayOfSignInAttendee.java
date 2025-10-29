
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfSignInAttendee complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfSignInAttendee">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SignInAttendee" type="{http://smc.huawei.com/}SignInAttendee" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSignInAttendee", propOrder = {
    "signInAttendee"
})
public class ArrayOfSignInAttendee {

    @XmlElement(name = "SignInAttendee", nillable = true)
    protected List<SignInAttendee> signInAttendee;

    /**
     * Gets the value of the signInAttendee property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the signInAttendee property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSignInAttendee().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SignInAttendee }
     * 
     * 
     */
    public List<SignInAttendee> getSignInAttendee() {
        if (signInAttendee == null) {
            signInAttendee = new ArrayList<SignInAttendee>();
        }
        return this.signInAttendee;
    }

}
