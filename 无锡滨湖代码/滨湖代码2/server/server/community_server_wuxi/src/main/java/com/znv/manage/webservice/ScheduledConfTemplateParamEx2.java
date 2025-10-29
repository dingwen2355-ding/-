
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ScheduledConfTemplateParamEx2 complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ScheduledConfTemplateParamEx2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ConfTemplateId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="ConfName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConfTime" type="{http://smc.huawei.com/}ScheduleTime" minOccurs="0"/>
 *         &lt;element name="Password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ChairmanPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduledConfTemplateParamEx2", propOrder = {
    "confTemplateId",
    "confName",
    "confTime",
    "password",
    "chairmanPassword"
})
public class ScheduledConfTemplateParamEx2 {

    @XmlElement(name = "ConfTemplateId")
    protected long confTemplateId;
    @XmlElement(name = "ConfName")
    protected String confName;
    @XmlElement(name = "ConfTime")
    protected ScheduleTime confTime;
    @XmlElement(name = "Password")
    protected String password;
    @XmlElement(name = "ChairmanPassword")
    protected String chairmanPassword;

    /**
     * ��ȡconfTemplateId���Ե�ֵ��
     * 
     */
    public long getConfTemplateId() {
        return confTemplateId;
    }

    /**
     * ����confTemplateId���Ե�ֵ��
     * 
     */
    public void setConfTemplateId(long value) {
        this.confTemplateId = value;
    }

    /**
     * ��ȡconfName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfName() {
        return confName;
    }

    /**
     * ����confName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfName(String value) {
        this.confName = value;
    }

    /**
     * ��ȡconfTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ScheduleTime }
     *     
     */
    public ScheduleTime getConfTime() {
        return confTime;
    }

    /**
     * ����confTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ScheduleTime }
     *     
     */
    public void setConfTime(ScheduleTime value) {
        this.confTime = value;
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
     * ��ȡchairmanPassword���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChairmanPassword() {
        return chairmanPassword;
    }

    /**
     * ����chairmanPassword���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChairmanPassword(String value) {
        this.chairmanPassword = value;
    }

}
