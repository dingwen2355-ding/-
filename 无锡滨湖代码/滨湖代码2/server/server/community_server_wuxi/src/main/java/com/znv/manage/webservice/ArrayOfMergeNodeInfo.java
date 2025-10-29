
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfMergeNodeInfo complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfMergeNodeInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MergeNodeInfo" type="{http://smc.huawei.com/}MergeNodeInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfMergeNodeInfo", propOrder = {
    "mergeNodeInfo"
})
public class ArrayOfMergeNodeInfo {

    @XmlElement(name = "MergeNodeInfo", nillable = true)
    protected List<MergeNodeInfo> mergeNodeInfo;

    /**
     * Gets the value of the mergeNodeInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mergeNodeInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMergeNodeInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MergeNodeInfo }
     * 
     * 
     */
    public List<MergeNodeInfo> getMergeNodeInfo() {
        if (mergeNodeInfo == null) {
            mergeNodeInfo = new ArrayList<MergeNodeInfo>();
        }
        return this.mergeNodeInfo;
    }

}
