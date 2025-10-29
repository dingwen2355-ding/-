
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfSiteVideoSourceParam complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfSiteVideoSourceParam">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SiteVideoSourceParam" type="{http://smc.huawei.com/}SiteVideoSourceParam" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSiteVideoSourceParam", propOrder = {
    "siteVideoSourceParam"
})
public class ArrayOfSiteVideoSourceParam {

    @XmlElement(name = "SiteVideoSourceParam", nillable = true)
    protected List<SiteVideoSourceParam> siteVideoSourceParam;

    /**
     * Gets the value of the siteVideoSourceParam property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the siteVideoSourceParam property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSiteVideoSourceParam().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SiteVideoSourceParam }
     * 
     * 
     */
    public List<SiteVideoSourceParam> getSiteVideoSourceParam() {
        if (siteVideoSourceParam == null) {
            siteVideoSourceParam = new ArrayList<SiteVideoSourceParam>();
        }
        return this.siteVideoSourceParam;
    }

}
