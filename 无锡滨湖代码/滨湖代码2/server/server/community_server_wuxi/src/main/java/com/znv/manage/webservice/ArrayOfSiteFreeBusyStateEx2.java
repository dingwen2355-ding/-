
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfSiteFreeBusyStateEx2 complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfSiteFreeBusyStateEx2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SiteFreeBusyStateEx2" type="{http://smc.huawei.com/}SiteFreeBusyStateEx2" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSiteFreeBusyStateEx2", propOrder = {
    "siteFreeBusyStateEx2"
})
public class ArrayOfSiteFreeBusyStateEx2 {

    @XmlElement(name = "SiteFreeBusyStateEx2", nillable = true)
    protected List<SiteFreeBusyStateEx2> siteFreeBusyStateEx2;

    /**
     * Gets the value of the siteFreeBusyStateEx2 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the siteFreeBusyStateEx2 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSiteFreeBusyStateEx2().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SiteFreeBusyStateEx2 }
     * 
     * 
     */
    public List<SiteFreeBusyStateEx2> getSiteFreeBusyStateEx2() {
        if (siteFreeBusyStateEx2 == null) {
            siteFreeBusyStateEx2 = new ArrayList<SiteFreeBusyStateEx2>();
        }
        return this.siteFreeBusyStateEx2;
    }

}
