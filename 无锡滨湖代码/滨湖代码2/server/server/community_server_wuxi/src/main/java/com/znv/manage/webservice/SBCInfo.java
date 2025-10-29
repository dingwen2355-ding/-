
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>SBCInfo complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="SBCInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="SBCAddr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SBCPort" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HttpProxyAddr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="STGAddr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UserName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SBCInterAddr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SBCInterPort" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Prefix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StartCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EndCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SBCInfo", propOrder = {
    "id",
    "sbcAddr",
    "sbcPort",
    "httpProxyAddr",
    "stgAddr",
    "userName",
    "password",
    "sbcInterAddr",
    "sbcInterPort",
    "prefix",
    "startCode",
    "endCode"
})
public class SBCInfo {

    @XmlElement(name = "Id")
    @XmlSchemaType(name = "unsignedInt")
    protected long id;
    @XmlElement(name = "SBCAddr")
    protected String sbcAddr;
    @XmlElement(name = "SBCPort")
    protected String sbcPort;
    @XmlElement(name = "HttpProxyAddr")
    protected String httpProxyAddr;
    @XmlElement(name = "STGAddr")
    protected String stgAddr;
    @XmlElement(name = "UserName")
    protected String userName;
    @XmlElement(name = "Password")
    protected String password;
    @XmlElement(name = "SBCInterAddr")
    protected String sbcInterAddr;
    @XmlElement(name = "SBCInterPort")
    protected String sbcInterPort;
    @XmlElement(name = "Prefix")
    protected String prefix;
    @XmlElement(name = "StartCode")
    protected String startCode;
    @XmlElement(name = "EndCode")
    protected String endCode;

    /**
     * ��ȡid���Ե�ֵ��
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * ����id���Ե�ֵ��
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * ��ȡsbcAddr���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSBCAddr() {
        return sbcAddr;
    }

    /**
     * ����sbcAddr���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSBCAddr(String value) {
        this.sbcAddr = value;
    }

    /**
     * ��ȡsbcPort���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSBCPort() {
        return sbcPort;
    }

    /**
     * ����sbcPort���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSBCPort(String value) {
        this.sbcPort = value;
    }

    /**
     * ��ȡhttpProxyAddr���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHttpProxyAddr() {
        return httpProxyAddr;
    }

    /**
     * ����httpProxyAddr���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHttpProxyAddr(String value) {
        this.httpProxyAddr = value;
    }

    /**
     * ��ȡstgAddr���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTGAddr() {
        return stgAddr;
    }

    /**
     * ����stgAddr���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTGAddr(String value) {
        this.stgAddr = value;
    }

    /**
     * ��ȡuserName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserName() {
        return userName;
    }

    /**
     * ����userName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserName(String value) {
        this.userName = value;
    }

    /**
     * ��ȡpassword���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * ����password���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * ��ȡsbcInterAddr���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSBCInterAddr() {
        return sbcInterAddr;
    }

    /**
     * ����sbcInterAddr���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSBCInterAddr(String value) {
        this.sbcInterAddr = value;
    }

    /**
     * ��ȡsbcInterPort���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSBCInterPort() {
        return sbcInterPort;
    }

    /**
     * ����sbcInterPort���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSBCInterPort(String value) {
        this.sbcInterPort = value;
    }

    /**
     * ��ȡprefix���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * ����prefix���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrefix(String value) {
        this.prefix = value;
    }

    /**
     * ��ȡstartCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartCode() {
        return startCode;
    }

    /**
     * ����startCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartCode(String value) {
        this.startCode = value;
    }

    /**
     * ��ȡendCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndCode() {
        return endCode;
    }

    /**
     * ����endCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndCode(String value) {
        this.endCode = value;
    }

}
