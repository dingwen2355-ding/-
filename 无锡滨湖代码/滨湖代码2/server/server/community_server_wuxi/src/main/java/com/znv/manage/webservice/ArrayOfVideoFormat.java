
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfVideoFormat complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfVideoFormat">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VideoFormat" type="{http://smc.huawei.com/}VideoFormat" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfVideoFormat", propOrder = {
    "videoFormat"
})
public class ArrayOfVideoFormat {

    @XmlElement(name = "VideoFormat")
    @XmlSchemaType(name = "string")
    protected List<VideoFormat> videoFormat;

    /**
     * Gets the value of the videoFormat property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the videoFormat property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVideoFormat().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VideoFormat }
     * 
     * 
     */
    public List<VideoFormat> getVideoFormat() {
        if (videoFormat == null) {
            videoFormat = new ArrayList<VideoFormat>();
        }
        return this.videoFormat;
    }

}
