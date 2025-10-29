
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>SMCServerInfo complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="SMCServerInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MACAddress" type="{http://smc.huawei.com/}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="OSVersion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SMCVersion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SMCServerInfo", propOrder = {
    "macAddress",
    "osVersion",
    "smcVersion"
})
public class SMCServerInfo {

    @XmlElement(name = "MACAddress")
    protected ArrayOfString macAddress;
    @XmlElement(name = "OSVersion")
    protected String osVersion;
    @XmlElement(name = "SMCVersion")
    protected String smcVersion;

    /**
     * ��ȡmacAddress���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getMACAddress() {
        return macAddress;
    }

    /**
     * ����macAddress���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setMACAddress(ArrayOfString value) {
        this.macAddress = value;
    }

    /**
     * ��ȡosVersion���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOSVersion() {
        return osVersion;
    }

    /**
     * ����osVersion���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOSVersion(String value) {
        this.osVersion = value;
    }

    /**
     * ��ȡsmcVersion���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSMCVersion() {
        return smcVersion;
    }

    /**
     * ����smcVersion���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSMCVersion(String value) {
        this.smcVersion = value;
    }

}
