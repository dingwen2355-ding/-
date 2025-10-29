
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>McuInfo complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="McuInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Type" type="{http://smc.huawei.com/}McuType"/>
 *         &lt;element name="Ip1" type="{http://smc.huawei.com/}IPAddressEx" minOccurs="0"/>
 *         &lt;element name="Port1" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="Ip2" type="{http://smc.huawei.com/}IPAddressEx" minOccurs="0"/>
 *         &lt;element name="Port2" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="DomainName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Status" type="{http://smc.huawei.com/}McuStatus"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "McuInfo", propOrder = {
    "id",
    "name",
    "type",
    "ip1",
    "port1",
    "ip2",
    "port2",
    "domainName",
    "status"
})
public class McuInfo {

    @XmlElement(name = "Id")
    @XmlSchemaType(name = "unsignedInt")
    protected long id;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Type", required = true)
    @XmlSchemaType(name = "string")
    protected McuType type;
    @XmlElement(name = "Ip1")
    protected IPAddressEx ip1;
    @XmlElement(name = "Port1", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long port1;
    @XmlElement(name = "Ip2")
    protected IPAddressEx ip2;
    @XmlElement(name = "Port2", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long port2;
    @XmlElement(name = "DomainName")
    protected String domainName;
    @XmlElement(name = "Status", required = true)
    @XmlSchemaType(name = "string")
    protected McuStatus status;

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
     * ��ȡtype���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link McuType }
     *     
     */
    public McuType getType() {
        return type;
    }

    /**
     * ����type���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link McuType }
     *     
     */
    public void setType(McuType value) {
        this.type = value;
    }

    /**
     * ��ȡip1���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link IPAddressEx }
     *     
     */
    public IPAddressEx getIp1() {
        return ip1;
    }

    /**
     * ����ip1���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link IPAddressEx }
     *     
     */
    public void setIp1(IPAddressEx value) {
        this.ip1 = value;
    }

    /**
     * ��ȡport1���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPort1() {
        return port1;
    }

    /**
     * ����port1���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPort1(Long value) {
        this.port1 = value;
    }

    /**
     * ��ȡip2���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link IPAddressEx }
     *     
     */
    public IPAddressEx getIp2() {
        return ip2;
    }

    /**
     * ����ip2���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link IPAddressEx }
     *     
     */
    public void setIp2(IPAddressEx value) {
        this.ip2 = value;
    }

    /**
     * ��ȡport2���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPort2() {
        return port2;
    }

    /**
     * ����port2���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPort2(Long value) {
        this.port2 = value;
    }

    /**
     * ��ȡdomainName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomainName() {
        return domainName;
    }

    /**
     * ����domainName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomainName(String value) {
        this.domainName = value;
    }

    /**
     * ��ȡstatus���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link McuStatus }
     *     
     */
    public McuStatus getStatus() {
        return status;
    }

    /**
     * ����status���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link McuStatus }
     *     
     */
    public void setStatus(McuStatus value) {
        this.status = value;
    }

}
