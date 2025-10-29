
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfMsSubPic complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfMsSubPic">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MsSubPic" type="{http://smc.huawei.com/}MsSubPic" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfMsSubPic", propOrder = {
    "msSubPic"
})
public class ArrayOfMsSubPic {

    @XmlElement(name = "MsSubPic", nillable = true)
    protected List<MsSubPic> msSubPic;

    /**
     * Gets the value of the msSubPic property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the msSubPic property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMsSubPic().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MsSubPic }
     * 
     * 
     */
    public List<MsSubPic> getMsSubPic() {
        if (msSubPic == null) {
            msSubPic = new ArrayList<MsSubPic>();
        }
        return this.msSubPic;
    }

}
