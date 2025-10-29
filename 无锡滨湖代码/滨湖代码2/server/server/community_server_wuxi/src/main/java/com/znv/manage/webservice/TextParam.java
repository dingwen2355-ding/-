
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>TextParam complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="TextParam">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Content" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SiteCodeList" type="{http://smc.huawei.com/}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="opType" type="{http://smc.huawei.com/}ConfCtrlOperationType"/>
 *         &lt;element name="TextType" type="{http://smc.huawei.com/}TextType"/>
 *         &lt;element name="DisPos" type="{http://smc.huawei.com/}TextDisplayPosition"/>
 *         &lt;element name="DisType" type="{http://smc.huawei.com/}TextDisplayType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TextParam", propOrder = {
    "content",
    "siteCodeList",
    "opType",
    "textType",
    "disPos",
    "disType"
})
public class TextParam {

    @XmlElement(name = "Content")
    protected String content;
    @XmlElement(name = "SiteCodeList")
    protected ArrayOfString siteCodeList;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected ConfCtrlOperationType opType;
    @XmlElement(name = "TextType", required = true)
    @XmlSchemaType(name = "string")
    protected TextType textType;
    @XmlElement(name = "DisPos", required = true)
    @XmlSchemaType(name = "string")
    protected TextDisplayPosition disPos;
    @XmlElement(name = "DisType", required = true)
    @XmlSchemaType(name = "string")
    protected TextDisplayType disType;

    /**
     * ��ȡcontent���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContent() {
        return content;
    }

    /**
     * ����content���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContent(String value) {
        this.content = value;
    }

    /**
     * ��ȡsiteCodeList���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getSiteCodeList() {
        return siteCodeList;
    }

    /**
     * ����siteCodeList���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setSiteCodeList(ArrayOfString value) {
        this.siteCodeList = value;
    }

    /**
     * ��ȡopType���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ConfCtrlOperationType }
     *     
     */
    public ConfCtrlOperationType getOpType() {
        return opType;
    }

    /**
     * ����opType���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ConfCtrlOperationType }
     *     
     */
    public void setOpType(ConfCtrlOperationType value) {
        this.opType = value;
    }

    /**
     * ��ȡtextType���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getTextType() {
        return textType;
    }

    /**
     * ����textType���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setTextType(TextType value) {
        this.textType = value;
    }

    /**
     * ��ȡdisPos���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link TextDisplayPosition }
     *     
     */
    public TextDisplayPosition getDisPos() {
        return disPos;
    }

    /**
     * ����disPos���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link TextDisplayPosition }
     *     
     */
    public void setDisPos(TextDisplayPosition value) {
        this.disPos = value;
    }

    /**
     * ��ȡdisType���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link TextDisplayType }
     *     
     */
    public TextDisplayType getDisType() {
        return disType;
    }

    /**
     * ����disType���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link TextDisplayType }
     *     
     */
    public void setDisType(TextDisplayType value) {
        this.disType = value;
    }

}
