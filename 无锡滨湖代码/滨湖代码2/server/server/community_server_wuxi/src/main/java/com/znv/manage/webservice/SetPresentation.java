
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>anonymous complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="confId" type="{http://smc.huawei.com/}ConferenceId" minOccurs="0"/>
 *         &lt;element name="siteUri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="operationType" type="{http://smc.huawei.com/}ConfCtrlOperationType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "confId",
    "siteUri",
    "operationType"
})
@XmlRootElement(name = "SetPresentation")
public class SetPresentation {

    protected ConferenceId confId;
    protected String siteUri;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected ConfCtrlOperationType operationType;

    /**
     * ��ȡconfId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ConferenceId }
     *     
     */
    public ConferenceId getConfId() {
        return confId;
    }

    /**
     * ����confId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ConferenceId }
     *     
     */
    public void setConfId(ConferenceId value) {
        this.confId = value;
    }

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
     * ��ȡoperationType���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ConfCtrlOperationType }
     *     
     */
    public ConfCtrlOperationType getOperationType() {
        return operationType;
    }

    /**
     * ����operationType���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ConfCtrlOperationType }
     *     
     */
    public void setOperationType(ConfCtrlOperationType value) {
        this.operationType = value;
    }

}
