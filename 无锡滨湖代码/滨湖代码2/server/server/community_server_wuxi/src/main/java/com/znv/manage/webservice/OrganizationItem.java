
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>OrganizationItem complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="OrganizationItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RelationType" type="{http://smc.huawei.com/}RelationType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrganizationItem", propOrder = {
    "id",
    "name",
    "relationType"
})
public class OrganizationItem {

    @XmlElement(name = "Id")
    protected String id;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "RelationType", required = true)
    @XmlSchemaType(name = "string")
    protected RelationType relationType;

    /**
     * ��ȡid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * ����id���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
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
     * ��ȡrelationType���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link RelationType }
     *     
     */
    public RelationType getRelationType() {
        return relationType;
    }

    /**
     * ����relationType���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link RelationType }
     *     
     */
    public void setRelationType(RelationType value) {
        this.relationType = value;
    }

}
