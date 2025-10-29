
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfAdhocConfTempplateParam complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfAdhocConfTempplateParam">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AdhocConfTempplateParam" type="{http://smc.huawei.com/}AdhocConfTempplateParam" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfAdhocConfTempplateParam", propOrder = {
    "adhocConfTempplateParam"
})
public class ArrayOfAdhocConfTempplateParam {

    @XmlElement(name = "AdhocConfTempplateParam", nillable = true)
    protected List<AdhocConfTempplateParam> adhocConfTempplateParam;

    /**
     * Gets the value of the adhocConfTempplateParam property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the adhocConfTempplateParam property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdhocConfTempplateParam().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AdhocConfTempplateParam }
     * 
     * 
     */
    public List<AdhocConfTempplateParam> getAdhocConfTempplateParam() {
        if (adhocConfTempplateParam == null) {
            adhocConfTempplateParam = new ArrayList<AdhocConfTempplateParam>();
        }
        return this.adhocConfTempplateParam;
    }

}
