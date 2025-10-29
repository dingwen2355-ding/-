
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>RseInfo complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="RseInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Ip" type="{http://smc.huawei.com/}IPAddressEx" minOccurs="0"/>
 *         &lt;element name="Status" type="{http://smc.huawei.com/}RseStatus"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RseInfo", propOrder = {
    "id",
    "name",
    "ip",
    "status"
})
public class RseInfo {

    @XmlElement(name = "Id")
    @XmlSchemaType(name = "unsignedInt")
    protected long id;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Ip")
    protected IPAddressEx ip;
    @XmlElement(name = "Status", required = true)
    @XmlSchemaType(name = "string")
    protected RseStatus status;

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
     * ��ȡip���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link IPAddressEx }
     *     
     */
    public IPAddressEx getIp() {
        return ip;
    }

    /**
     * ����ip���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link IPAddressEx }
     *     
     */
    public void setIp(IPAddressEx value) {
        this.ip = value;
    }

    /**
     * ��ȡstatus���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link RseStatus }
     *     
     */
    public RseStatus getStatus() {
        return status;
    }

    /**
     * ����status���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link RseStatus }
     *     
     */
    public void setStatus(RseStatus value) {
        this.status = value;
    }

}
