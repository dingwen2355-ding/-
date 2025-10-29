
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfMcuResource complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfMcuResource">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="McuResource" type="{http://smc.huawei.com/}McuResource" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfMcuResource", propOrder = {
    "mcuResource"
})
public class ArrayOfMcuResource {

    @XmlElement(name = "McuResource", nillable = true)
    protected List<McuResource> mcuResource;

    /**
     * Gets the value of the mcuResource property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mcuResource property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMcuResource().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link McuResource }
     * 
     * 
     */
    public List<McuResource> getMcuResource() {
        if (mcuResource == null) {
            mcuResource = new ArrayList<McuResource>();
        }
        return this.mcuResource;
    }

}
