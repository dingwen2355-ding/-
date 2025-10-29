
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfTempURI complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfTempURI">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TempURI" type="{http://smc.huawei.com/}TempURI" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfTempURI", propOrder = {
    "tempURI"
})
public class ArrayOfTempURI {

    @XmlElement(name = "TempURI", nillable = true)
    protected List<TempURI> tempURI;

    /**
     * Gets the value of the tempURI property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tempURI property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTempURI().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TempURI }
     * 
     * 
     */
    public List<TempURI> getTempURI() {
        if (tempURI == null) {
            tempURI = new ArrayList<TempURI>();
        }
        return this.tempURI;
    }

}
