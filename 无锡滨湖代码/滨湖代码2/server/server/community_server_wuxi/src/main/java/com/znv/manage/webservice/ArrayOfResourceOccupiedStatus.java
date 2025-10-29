
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfResourceOccupiedStatus complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfResourceOccupiedStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ResourceOccupiedStatus" type="{http://smc.huawei.com/}ResourceOccupiedStatus" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfResourceOccupiedStatus", propOrder = {
    "resourceOccupiedStatus"
})
public class ArrayOfResourceOccupiedStatus {

    @XmlElement(name = "ResourceOccupiedStatus", nillable = true)
    protected List<ResourceOccupiedStatus> resourceOccupiedStatus;

    /**
     * Gets the value of the resourceOccupiedStatus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the resourceOccupiedStatus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResourceOccupiedStatus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ResourceOccupiedStatus }
     * 
     * 
     */
    public List<ResourceOccupiedStatus> getResourceOccupiedStatus() {
        if (resourceOccupiedStatus == null) {
            resourceOccupiedStatus = new ArrayList<ResourceOccupiedStatus>();
        }
        return this.resourceOccupiedStatus;
    }

}
