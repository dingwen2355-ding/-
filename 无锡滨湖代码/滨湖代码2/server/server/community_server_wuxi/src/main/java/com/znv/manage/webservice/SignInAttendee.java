
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>SignInAttendee complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="SignInAttendee">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SiteUri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UportalId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Number" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SignInTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="signInType" type="{http://smc.huawei.com/}SignInType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignInAttendee", propOrder = {
    "siteUri",
    "uportalId",
    "name",
    "number",
    "signInTime",
    "signInType"
})
public class SignInAttendee {

    @XmlElement(name = "SiteUri")
    protected String siteUri;
    @XmlElement(name = "UportalId")
    protected String uportalId;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Number")
    protected String number;
    @XmlElement(name = "SignInTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar signInTime;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected SignInType signInType;

    /**
     * ��ȡsiteUri���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiteUri() {
        return siteUri;
    }

    /**
     * ����siteUri���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiteUri(String value) {
        this.siteUri = value;
    }

    /**
     * ��ȡuportalId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUportalId() {
        return uportalId;
    }

    /**
     * ����uportalId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUportalId(String value) {
        this.uportalId = value;
    }

    /**
     * ��ȡname���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * ����name���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * ��ȡnumber���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumber() {
        return number;
    }

    /**
     * ����number���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumber(String value) {
        this.number = value;
    }

    /**
     * ��ȡsignInTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSignInTime() {
        return signInTime;
    }

    /**
     * ����signInTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSignInTime(XMLGregorianCalendar value) {
        this.signInTime = value;
    }

    /**
     * ��ȡsignInType���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SignInType }
     *     
     */
    public SignInType getSignInType() {
        return signInType;
    }

    /**
     * ����signInType���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SignInType }
     *     
     */
    public void setSignInType(SignInType value) {
        this.signInType = value;
    }

}
