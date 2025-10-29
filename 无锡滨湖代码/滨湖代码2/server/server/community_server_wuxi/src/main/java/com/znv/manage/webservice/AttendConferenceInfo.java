
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>AttendConferenceInfo complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="AttendConferenceInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HasDataResource" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="HasVideoResource" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="MSAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UserNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SiteMid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SiteTid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttendConferenceInfo", propOrder = {
    "hasDataResource",
    "hasVideoResource",
    "msAddress",
    "userNum",
    "siteMid",
    "siteTid"
})
public class AttendConferenceInfo {

    @XmlElement(name = "HasDataResource")
    protected boolean hasDataResource;
    @XmlElement(name = "HasVideoResource")
    protected boolean hasVideoResource;
    @XmlElement(name = "MSAddress")
    protected String msAddress;
    @XmlElement(name = "UserNum")
    protected String userNum;
    @XmlElement(name = "SiteMid")
    protected String siteMid;
    @XmlElement(name = "SiteTid")
    protected String siteTid;

    /**
     * ��ȡhasDataResource���Ե�ֵ��
     * 
     */
    public boolean isHasDataResource() {
        return hasDataResource;
    }

    /**
     * ����hasDataResource���Ե�ֵ��
     * 
     */
    public void setHasDataResource(boolean value) {
        this.hasDataResource = value;
    }

    /**
     * ��ȡhasVideoResource���Ե�ֵ��
     * 
     */
    public boolean isHasVideoResource() {
        return hasVideoResource;
    }

    /**
     * ����hasVideoResource���Ե�ֵ��
     * 
     */
    public void setHasVideoResource(boolean value) {
        this.hasVideoResource = value;
    }

    /**
     * ��ȡmsAddress���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMSAddress() {
        return msAddress;
    }

    /**
     * ����msAddress���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMSAddress(String value) {
        this.msAddress = value;
    }

    /**
     * ��ȡuserNum���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserNum() {
        return userNum;
    }

    /**
     * ����userNum���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserNum(String value) {
        this.userNum = value;
    }

    /**
     * ��ȡsiteMid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiteMid() {
        return siteMid;
    }

    /**
     * ����siteMid���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiteMid(String value) {
        this.siteMid = value;
    }

    /**
     * ��ȡsiteTid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiteTid() {
        return siteTid;
    }

    /**
     * ����siteTid���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiteTid(String value) {
        this.siteTid = value;
    }

}
