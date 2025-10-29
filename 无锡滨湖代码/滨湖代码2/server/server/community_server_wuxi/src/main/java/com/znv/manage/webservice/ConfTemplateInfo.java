
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>ConfTemplateInfo complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ConfTemplateInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ConfTemplateId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AccessCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConfTemptype" type="{http://smc.huawei.com/}ConfType"/>
 *         &lt;element name="State" type="{http://smc.huawei.com/}ConfTemplateRegState"/>
 *         &lt;element name="CreatUser" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConfTemplateInfo", propOrder = {
    "confTemplateId",
    "name",
    "accessCode",
    "rate",
    "confTemptype",
    "state",
    "creatUser"
})
public class ConfTemplateInfo {

    @XmlElement(name = "ConfTemplateId", required = true, type = Long.class, nillable = true)
    protected Long confTemplateId;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "AccessCode")
    protected String accessCode;
    @XmlElement(name = "Rate")
    protected String rate;
    @XmlElement(name = "ConfTemptype", required = true)
    @XmlSchemaType(name = "string")
    protected ConfType confTemptype;
    @XmlElement(name = "State", required = true)
    @XmlSchemaType(name = "string")
    protected ConfTemplateRegState state;
    @XmlElement(name = "CreatUser")
    protected String creatUser;

    /**
     * ��ȡconfTemplateId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getConfTemplateId() {
        return confTemplateId;
    }

    /**
     * ����confTemplateId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setConfTemplateId(Long value) {
        this.confTemplateId = value;
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
     * ��ȡaccessCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccessCode() {
        return accessCode;
    }

    /**
     * ����accessCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccessCode(String value) {
        this.accessCode = value;
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
     * ��ȡconfTemptype���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ConfType }
     *     
     */
    public ConfType getConfTemptype() {
        return confTemptype;
    }

    /**
     * ����confTemptype���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ConfType }
     *     
     */
    public void setConfTemptype(ConfType value) {
        this.confTemptype = value;
    }

    /**
     * ��ȡstate���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ConfTemplateRegState }
     *     
     */
    public ConfTemplateRegState getState() {
        return state;
    }

    /**
     * ����state���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ConfTemplateRegState }
     *     
     */
    public void setState(ConfTemplateRegState value) {
        this.state = value;
    }

    /**
     * ��ȡcreatUser���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreatUser() {
        return creatUser;
    }

    /**
     * ����creatUser���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreatUser(String value) {
        this.creatUser = value;
    }

}
