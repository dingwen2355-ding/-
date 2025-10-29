
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfConfStructure complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfConfStructure">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ConfStructure" type="{http://smc.huawei.com/}ConfStructure" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfConfStructure", propOrder = {
    "confStructure"
})
public class ArrayOfConfStructure {

    @XmlElement(name = "ConfStructure", nillable = true)
    protected List<ConfStructure> confStructure;

    /**
     * Gets the value of the confStructure property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the confStructure property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConfStructure().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConfStructure }
     * 
     * 
     */
    public List<ConfStructure> getConfStructure() {
        if (confStructure == null) {
            confStructure = new ArrayList<ConfStructure>();
        }
        return this.confStructure;
    }

}
