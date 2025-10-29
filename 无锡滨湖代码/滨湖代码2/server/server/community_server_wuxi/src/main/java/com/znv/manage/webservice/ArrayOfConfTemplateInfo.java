
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfConfTemplateInfo complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfConfTemplateInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ConfTemplateInfo" type="{http://smc.huawei.com/}ConfTemplateInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfConfTemplateInfo", propOrder = {
    "confTemplateInfo"
})
public class ArrayOfConfTemplateInfo {

    @XmlElement(name = "ConfTemplateInfo", nillable = true)
    protected List<ConfTemplateInfo> confTemplateInfo;

    /**
     * Gets the value of the confTemplateInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the confTemplateInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConfTemplateInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConfTemplateInfo }
     * 
     * 
     */
    public List<ConfTemplateInfo> getConfTemplateInfo() {
        if (confTemplateInfo == null) {
            confTemplateInfo = new ArrayList<ConfTemplateInfo>();
        }
        return this.confTemplateInfo;
    }

}
