
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>MsMultiPic complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="MsMultiPic">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SubPic" type="{http://smc.huawei.com/}ArrayOfMsSubPic" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MsMultiPic", propOrder = {
    "subPic"
})
public class MsMultiPic {

    @XmlElement(name = "SubPic")
    protected ArrayOfMsSubPic subPic;

    /**
     * ��ȡsubPic���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfMsSubPic }
     *     
     */
    public ArrayOfMsSubPic getSubPic() {
        return subPic;
    }

    /**
     * ����subPic���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfMsSubPic }
     *     
     */
    public void setSubPic(ArrayOfMsSubPic value) {
        this.subPic = value;
    }

}
