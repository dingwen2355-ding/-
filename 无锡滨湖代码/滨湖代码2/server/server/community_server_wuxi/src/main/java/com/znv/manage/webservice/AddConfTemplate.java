
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="orgId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="confTemplate" type="{http://smc.huawei.com/}ConfTempplateParam" minOccurs="0"/>
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
    "orgId",
    "confTemplate"
})
@XmlRootElement(name = "AddConfTemplate")
public class AddConfTemplate {

    protected String orgId;
    protected ConfTempplateParam confTemplate;

    /**
     * ��ȡorgId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * ����orgId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgId(String value) {
        this.orgId = value;
    }

    /**
     * ��ȡconfTemplate���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ConfTempplateParam }
     *     
     */
    public ConfTempplateParam getConfTemplate() {
        return confTemplate;
    }

    /**
     * ����confTemplate���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ConfTempplateParam }
     *     
     */
    public void setConfTemplate(ConfTempplateParam value) {
        this.confTemplate = value;
    }

}
