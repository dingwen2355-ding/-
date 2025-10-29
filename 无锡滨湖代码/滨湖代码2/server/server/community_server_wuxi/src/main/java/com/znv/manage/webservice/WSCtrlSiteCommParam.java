
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>WSCtrlSiteCommParam complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="WSCtrlSiteCommParam">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SiteCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OperaTypeParam" type="{http://smc.huawei.com/}ConfCtrlOperationType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WSCtrlSiteCommParam", propOrder = {
    "siteCode",
    "operaTypeParam"
})
public class WSCtrlSiteCommParam {

    @XmlElement(name = "SiteCode")
    protected String siteCode;
    @XmlElement(name = "OperaTypeParam", required = true)
    @XmlSchemaType(name = "string")
    protected ConfCtrlOperationType operaTypeParam;

    /**
     * ��ȡsiteCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiteCode() {
        return siteCode;
    }

    /**
     * ����siteCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiteCode(String value) {
        this.siteCode = value;
    }

    /**
     * ��ȡoperaTypeParam���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ConfCtrlOperationType }
     *     
     */
    public ConfCtrlOperationType getOperaTypeParam() {
        return operaTypeParam;
    }

    /**
     * ����operaTypeParam���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ConfCtrlOperationType }
     *     
     */
    public void setOperaTypeParam(ConfCtrlOperationType value) {
        this.operaTypeParam = value;
    }

}
