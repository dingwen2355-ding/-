
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfPointToPointCDR complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfPointToPointCDR">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PointToPointCDR" type="{http://smc.huawei.com/}PointToPointCDR" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfPointToPointCDR", propOrder = {
    "pointToPointCDR"
})
public class ArrayOfPointToPointCDR {

    @XmlElement(name = "PointToPointCDR", nillable = true)
    protected List<PointToPointCDR> pointToPointCDR;

    /**
     * Gets the value of the pointToPointCDR property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pointToPointCDR property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPointToPointCDR().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PointToPointCDR }
     * 
     * 
     */
    public List<PointToPointCDR> getPointToPointCDR() {
        if (pointToPointCDR == null) {
            pointToPointCDR = new ArrayList<PointToPointCDR>();
        }
        return this.pointToPointCDR;
    }

}
