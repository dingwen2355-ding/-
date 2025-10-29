
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>TempURI complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="TempURI">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Uri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RegUser" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RegPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SBCIP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SBCPort" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="SCIP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TempURI", propOrder = {
    "uri",
    "regUser",
    "regPassword",
    "sbcip",
    "sbcPort",
    "scip"
})
public class TempURI {

    @XmlElement(name = "Uri")
    protected String uri;
    @XmlElement(name = "RegUser")
    protected String regUser;
    @XmlElement(name = "RegPassword")
    protected String regPassword;
    @XmlElement(name = "SBCIP")
    protected String sbcip;
    @XmlElement(name = "SBCPort", required = true, type = Integer.class, nillable = true)
    protected Integer sbcPort;
    @XmlElement(name = "SCIP")
    protected String scip;

    /**
     * ��ȡuri���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUri() {
        return uri;
    }

    /**
     * ����uri���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUri(String value) {
        this.uri = value;
    }

    /**
     * ��ȡregUser���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegUser() {
        return regUser;
    }

    /**
     * ����regUser���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegUser(String value) {
        this.regUser = value;
    }

    /**
     * ��ȡregPassword���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegPassword() {
        return regPassword;
    }

    /**
     * ����regPassword���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegPassword(String value) {
        this.regPassword = value;
    }

    /**
     * ��ȡsbcip���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSBCIP() {
        return sbcip;
    }

    /**
     * ����sbcip���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSBCIP(String value) {
        this.sbcip = value;
    }

    /**
     * ��ȡsbcPort���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSBCPort() {
        return sbcPort;
    }

    /**
     * ����sbcPort���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSBCPort(Integer value) {
        this.sbcPort = value;
    }

    /**
     * ��ȡscip���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSCIP() {
        return scip;
    }

    /**
     * ����scip���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSCIP(String value) {
        this.scip = value;
    }

}
