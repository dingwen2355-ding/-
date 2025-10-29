
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfSiteMcu complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfSiteMcu">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SiteMcu" type="{http://smc.huawei.com/}SiteMcu" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSiteMcu", propOrder = {
    "siteMcu"
})
public class ArrayOfSiteMcu {

    @XmlElement(name = "SiteMcu", nillable = true)
    protected List<SiteMcu> siteMcu;

    /**
     * Gets the value of the siteMcu property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the siteMcu property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSiteMcu().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SiteMcu }
     * 
     * 
     */
    public List<SiteMcu> getSiteMcu() {
        if (siteMcu == null) {
            siteMcu = new ArrayList<SiteMcu>();
        }
        return this.siteMcu;
    }

}
