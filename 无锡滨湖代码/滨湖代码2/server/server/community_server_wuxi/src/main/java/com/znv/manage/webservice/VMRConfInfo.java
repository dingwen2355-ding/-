
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>VMRConfInfo complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="VMRConfInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VMRNumber" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="GuestLink" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ChairmanLink" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VMRConfInfo", propOrder = {
    "vmrNumber",
    "guestLink",
    "chairmanLink"
})
public class VMRConfInfo {

    @XmlElement(name = "VMRNumber")
    @XmlSchemaType(name = "unsignedInt")
    protected long vmrNumber;
    @XmlElement(name = "GuestLink")
    protected String guestLink;
    @XmlElement(name = "ChairmanLink")
    protected String chairmanLink;

    /**
     * ��ȡvmrNumber���Ե�ֵ��
     * 
     */
    public long getVMRNumber() {
        return vmrNumber;
    }

    /**
     * ����vmrNumber���Ե�ֵ��
     * 
     */
    public void setVMRNumber(long value) {
        this.vmrNumber = value;
    }

    /**
     * ��ȡguestLink���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGuestLink() {
        return guestLink;
    }

    /**
     * ����guestLink���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGuestLink(String value) {
        this.guestLink = value;
    }

    /**
     * ��ȡchairmanLink���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChairmanLink() {
        return chairmanLink;
    }

    /**
     * ����chairmanLink���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChairmanLink(String value) {
        this.chairmanLink = value;
    }

}
