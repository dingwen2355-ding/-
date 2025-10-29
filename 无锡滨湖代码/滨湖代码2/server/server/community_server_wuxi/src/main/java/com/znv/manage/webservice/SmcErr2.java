
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>SmcErr2 complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="SmcErr2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ErrCode" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="SubSystem" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SmcErr2", propOrder = {
    "errCode",
    "subSystem",
    "description"
})
public class SmcErr2 {

    @XmlElement(name = "ErrCode")
    @XmlSchemaType(name = "unsignedInt")
    protected long errCode;
    @XmlElement(name = "SubSystem")
    protected int subSystem;
    @XmlElement(name = "Description")
    protected String description;

    /**
     * ��ȡerrCode���Ե�ֵ��
     * 
     */
    public long getErrCode() {
        return errCode;
    }

    /**
     * ����errCode���Ե�ֵ��
     * 
     */
    public void setErrCode(long value) {
        this.errCode = value;
    }

    /**
     * ��ȡsubSystem���Ե�ֵ��
     * 
     */
    public int getSubSystem() {
        return subSystem;
    }

    /**
     * ����subSystem���Ե�ֵ��
     * 
     */
    public void setSubSystem(int value) {
        this.subSystem = value;
    }

    /**
     * ��ȡdescription���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * ����description���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

}
