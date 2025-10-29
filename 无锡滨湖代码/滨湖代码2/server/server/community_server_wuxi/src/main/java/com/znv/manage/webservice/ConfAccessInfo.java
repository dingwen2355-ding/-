
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ConfAccessInfo complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ConfAccessInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NeedPassword" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="AccessNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConfAccessInfo", propOrder = {
    "needPassword",
    "accessNumber"
})
public class ConfAccessInfo {

    @XmlElement(name = "NeedPassword")
    protected boolean needPassword;
    @XmlElement(name = "AccessNumber")
    protected String accessNumber;

    /**
     * ��ȡneedPassword���Ե�ֵ��
     * 
     */
    public boolean isNeedPassword() {
        return needPassword;
    }

    /**
     * ����needPassword���Ե�ֵ��
     * 
     */
    public void setNeedPassword(boolean value) {
        this.needPassword = value;
    }

    /**
     * ��ȡaccessNumber���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccessNumber() {
        return accessNumber;
    }

    /**
     * ����accessNumber���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccessNumber(String value) {
        this.accessNumber = value;
    }

}
