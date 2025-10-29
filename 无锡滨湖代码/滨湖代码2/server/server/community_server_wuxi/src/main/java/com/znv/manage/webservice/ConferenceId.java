
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>ConferenceId complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ConferenceId">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ConfInternalId" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="ConfExternalId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConferenceId", propOrder = {
    "confInternalId",
    "confExternalId"
})
public class ConferenceId {

    @XmlElement(name = "ConfInternalId", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long confInternalId;
    @XmlElement(name = "ConfExternalId")
    protected String confExternalId;

    /**
     * ��ȡconfInternalId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getConfInternalId() {
        return confInternalId;
    }

    /**
     * ����confInternalId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setConfInternalId(Long value) {
        this.confInternalId = value;
    }

    /**
     * ��ȡconfExternalId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfExternalId() {
        return confExternalId;
    }

    /**
     * ����confExternalId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfExternalId(String value) {
        this.confExternalId = value;
    }

}
