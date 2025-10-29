
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>CallEndPoint complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="CallEndPoint">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Type" type="{http://smc.huawei.com/}CallType"/>
 *         &lt;element name="SiteUri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CallEndPoint", propOrder = {
    "type",
    "siteUri"
})
public class CallEndPoint {

    @XmlElement(name = "Type", required = true)
    @XmlSchemaType(name = "string")
    protected CallType type;
    @XmlElement(name = "SiteUri")
    protected String siteUri;

    /**
     * ��ȡtype���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link CallType }
     *     
     */
    public CallType getType() {
        return type;
    }

    /**
     * ����type���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link CallType }
     *     
     */
    public void setType(CallType value) {
        this.type = value;
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

}
