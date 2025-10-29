
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfConfRecordAddr complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfConfRecordAddr">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ConfRecordAddr" type="{http://smc.huawei.com/}ConfRecordAddr" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfConfRecordAddr", propOrder = {
    "confRecordAddr"
})
public class ArrayOfConfRecordAddr {

    @XmlElement(name = "ConfRecordAddr", nillable = true)
    protected List<ConfRecordAddr> confRecordAddr;

    /**
     * Gets the value of the confRecordAddr property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the confRecordAddr property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConfRecordAddr().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConfRecordAddr }
     * 
     * 
     */
    public List<ConfRecordAddr> getConfRecordAddr() {
        if (confRecordAddr == null) {
            confRecordAddr = new ArrayList<ConfRecordAddr>();
        }
        return this.confRecordAddr;
    }

}
