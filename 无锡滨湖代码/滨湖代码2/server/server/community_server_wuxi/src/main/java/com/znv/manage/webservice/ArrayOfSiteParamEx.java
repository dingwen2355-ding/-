
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfSiteParamEx complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfSiteParamEx">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SiteParamEx" type="{http://smc.huawei.com/}SiteParamEx" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSiteParamEx", propOrder = {
    "siteParamEx"
})
public class ArrayOfSiteParamEx {

    @XmlElement(name = "SiteParamEx", nillable = true)
    protected List<SiteParamEx> siteParamEx;

    /**
     * Gets the value of the siteParamEx property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the siteParamEx property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSiteParamEx().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SiteParamEx }
     * 
     * 
     */
    public List<SiteParamEx> getSiteParamEx() {
        if (siteParamEx == null) {
            siteParamEx = new ArrayList<SiteParamEx>();
        }
        return this.siteParamEx;
    }

}
