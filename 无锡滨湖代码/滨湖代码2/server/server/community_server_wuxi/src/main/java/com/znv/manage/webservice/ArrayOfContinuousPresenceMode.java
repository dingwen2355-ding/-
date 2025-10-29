
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfContinuousPresenceMode complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfContinuousPresenceMode">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ContinuousPresenceMode" type="{http://smc.huawei.com/}ContinuousPresenceMode" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfContinuousPresenceMode", propOrder = {
    "continuousPresenceMode"
})
public class ArrayOfContinuousPresenceMode {

    @XmlElement(name = "ContinuousPresenceMode")
    @XmlSchemaType(name = "string")
    protected List<ContinuousPresenceMode> continuousPresenceMode;

    /**
     * Gets the value of the continuousPresenceMode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the continuousPresenceMode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContinuousPresenceMode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContinuousPresenceMode }
     * 
     * 
     */
    public List<ContinuousPresenceMode> getContinuousPresenceMode() {
        if (continuousPresenceMode == null) {
            continuousPresenceMode = new ArrayList<ContinuousPresenceMode>();
        }
        return this.continuousPresenceMode;
    }

}
