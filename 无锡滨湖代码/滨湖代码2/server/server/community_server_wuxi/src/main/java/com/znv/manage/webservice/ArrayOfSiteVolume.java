
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfSiteVolume complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfSiteVolume">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SiteVolume" type="{http://smc.huawei.com/}SiteVolume" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSiteVolume", propOrder = {
    "siteVolume"
})
public class ArrayOfSiteVolume {

    @XmlElement(name = "SiteVolume", nillable = true)
    protected List<SiteVolume> siteVolume;

    /**
     * Gets the value of the siteVolume property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the siteVolume property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSiteVolume().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SiteVolume }
     * 
     * 
     */
    public List<SiteVolume> getSiteVolume() {
        if (siteVolume == null) {
            siteVolume = new ArrayList<SiteVolume>();
        }
        return this.siteVolume;
    }

}
