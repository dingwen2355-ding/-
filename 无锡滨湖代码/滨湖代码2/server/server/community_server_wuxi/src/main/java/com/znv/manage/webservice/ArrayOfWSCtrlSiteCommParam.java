
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfWSCtrlSiteCommParam complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfWSCtrlSiteCommParam">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="WSCtrlSiteCommParam" type="{http://smc.huawei.com/}WSCtrlSiteCommParam" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfWSCtrlSiteCommParam", propOrder = {
    "wsCtrlSiteCommParam"
})
public class ArrayOfWSCtrlSiteCommParam {

    @XmlElement(name = "WSCtrlSiteCommParam", nillable = true)
    protected List<WSCtrlSiteCommParam> wsCtrlSiteCommParam;

    /**
     * Gets the value of the wsCtrlSiteCommParam property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the wsCtrlSiteCommParam property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWSCtrlSiteCommParam().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WSCtrlSiteCommParam }
     * 
     * 
     */
    public List<WSCtrlSiteCommParam> getWSCtrlSiteCommParam() {
        if (wsCtrlSiteCommParam == null) {
            wsCtrlSiteCommParam = new ArrayList<WSCtrlSiteCommParam>();
        }
        return this.wsCtrlSiteCommParam;
    }

}
