
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>SiteParamEx complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="SiteParamEx">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Uri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Type" type="{http://smc.huawei.com/}SiteType"/>
 *         &lt;element name="Rate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RegUser" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RegPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VideoCap" type="{http://smc.huawei.com/}ArrayOfVdieoCapbilityItem" minOccurs="0"/>
 *         &lt;element name="DeviceStatus" type="{http://smc.huawei.com/}DeviceStatusEx" minOccurs="0"/>
 *         &lt;element name="DeviceSn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Model" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrgID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IpAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SiteParamEx", propOrder = {
    "name",
    "uri",
    "type",
    "rate",
    "regUser",
    "regPassword",
    "videoCap",
    "deviceStatus",
    "deviceSn",
    "model",
    "orgID",
    "ipAddress"
})
public class SiteParamEx {

    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Uri")
    protected String uri;
    @XmlElement(name = "Type", required = true)
    @XmlSchemaType(name = "string")
    protected SiteType type;
    @XmlElement(name = "Rate")
    protected String rate;
    @XmlElement(name = "RegUser")
    protected String regUser;
    @XmlElement(name = "RegPassword")
    protected String regPassword;
    @XmlElement(name = "VideoCap")
    protected ArrayOfVdieoCapbilityItem videoCap;
    @XmlElement(name = "DeviceStatus")
    protected DeviceStatusEx deviceStatus;
    @XmlElement(name = "DeviceSn")
    protected String deviceSn;
    @XmlElement(name = "Model")
    protected String model;
    @XmlElement(name = "OrgID")
    protected String orgID;
    @XmlElement(name = "IpAddress")
    protected String ipAddress;

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
     * ��ȡtype���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SiteType }
     *     
     */
    public SiteType getType() {
        return type;
    }

    /**
     * ����type���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SiteType }
     *     
     */
    public void setType(SiteType value) {
        this.type = value;
    }

    /**
     * ��ȡrate���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRate() {
        return rate;
    }

    /**
     * ����rate���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRate(String value) {
        this.rate = value;
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
     * ��ȡvideoCap���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfVdieoCapbilityItem }
     *     
     */
    public ArrayOfVdieoCapbilityItem getVideoCap() {
        return videoCap;
    }

    /**
     * ����videoCap���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfVdieoCapbilityItem }
     *     
     */
    public void setVideoCap(ArrayOfVdieoCapbilityItem value) {
        this.videoCap = value;
    }

    /**
     * ��ȡdeviceStatus���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link DeviceStatusEx }
     *     
     */
    public DeviceStatusEx getDeviceStatus() {
        return deviceStatus;
    }

    /**
     * ����deviceStatus���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link DeviceStatusEx }
     *     
     */
    public void setDeviceStatus(DeviceStatusEx value) {
        this.deviceStatus = value;
    }

    /**
     * ��ȡdeviceSn���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceSn() {
        return deviceSn;
    }

    /**
     * ����deviceSn���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceSn(String value) {
        this.deviceSn = value;
    }

    /**
     * ��ȡmodel���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModel() {
        return model;
    }

    /**
     * ����model���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModel(String value) {
        this.model = value;
    }

    /**
     * ��ȡorgID���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgID() {
        return orgID;
    }

    /**
     * ����orgID���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgID(String value) {
        this.orgID = value;
    }

    /**
     * ��ȡipAddress���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * ����ipAddress���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIpAddress(String value) {
        this.ipAddress = value;
    }

}
