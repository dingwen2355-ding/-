
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>VMRInfo complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="VMRInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VMRNumber" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="VMRName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VMRID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ChairmanPwd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GuestPwd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VMROwner" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AllowGuestActivation" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VMRInfo", propOrder = {
    "vmrNumber",
    "vmrName",
    "vmrid",
    "chairmanPwd",
    "guestPwd",
    "vmrOwner",
    "allowGuestActivation"
})
public class VMRInfo {

    @XmlElement(name = "VMRNumber", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long vmrNumber;
    @XmlElement(name = "VMRName")
    protected String vmrName;
    @XmlElement(name = "VMRID")
    protected String vmrid;
    @XmlElement(name = "ChairmanPwd")
    protected String chairmanPwd;
    @XmlElement(name = "GuestPwd")
    protected String guestPwd;
    @XmlElement(name = "VMROwner")
    protected String vmrOwner;
    @XmlElement(name = "AllowGuestActivation")
    protected boolean allowGuestActivation;

    /**
     * ��ȡvmrNumber���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getVMRNumber() {
        return vmrNumber;
    }

    /**
     * ����vmrNumber���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setVMRNumber(Long value) {
        this.vmrNumber = value;
    }

    /**
     * ��ȡvmrName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVMRName() {
        return vmrName;
    }

    /**
     * ����vmrName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVMRName(String value) {
        this.vmrName = value;
    }

    /**
     * ��ȡvmrid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVMRID() {
        return vmrid;
    }

    /**
     * ����vmrid���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVMRID(String value) {
        this.vmrid = value;
    }

    /**
     * ��ȡchairmanPwd���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChairmanPwd() {
        return chairmanPwd;
    }

    /**
     * ����chairmanPwd���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChairmanPwd(String value) {
        this.chairmanPwd = value;
    }

    /**
     * ��ȡguestPwd���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGuestPwd() {
        return guestPwd;
    }

    /**
     * ����guestPwd���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGuestPwd(String value) {
        this.guestPwd = value;
    }

    /**
     * ��ȡvmrOwner���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVMROwner() {
        return vmrOwner;
    }

    /**
     * ����vmrOwner���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVMROwner(String value) {
        this.vmrOwner = value;
    }

    /**
     * ��ȡallowGuestActivation���Ե�ֵ��
     * 
     */
    public boolean isAllowGuestActivation() {
        return allowGuestActivation;
    }

    /**
     * ����allowGuestActivation���Ե�ֵ��
     * 
     */
    public void setAllowGuestActivation(boolean value) {
        this.allowGuestActivation = value;
    }

}
