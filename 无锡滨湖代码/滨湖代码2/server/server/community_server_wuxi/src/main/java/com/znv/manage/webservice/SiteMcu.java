
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>SiteMcu complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="SiteMcu">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Uri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="McuId" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SiteMcu", propOrder = {
    "uri",
    "mcuId"
})
public class SiteMcu {

    @XmlElement(name = "Uri")
    protected String uri;
    @XmlElement(name = "McuId")
    @XmlSchemaType(name = "unsignedInt")
    protected long mcuId;

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
     * ��ȡmcuId���Ե�ֵ��
     * 
     */
    public long getMcuId() {
        return mcuId;
    }

    /**
     * ����mcuId���Ե�ֵ��
     * 
     */
    public void setMcuId(long value) {
        this.mcuId = value;
    }

}
