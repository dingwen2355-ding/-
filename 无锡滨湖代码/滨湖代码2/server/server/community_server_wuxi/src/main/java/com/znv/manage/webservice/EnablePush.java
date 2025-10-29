
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
 *         &lt;element name="pushMode" type="{http://smc.huawei.com/}PushMode"/>
 *         &lt;element name="extendParameter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "pushMode",
    "extendParameter"
})
@XmlRootElement(name = "EnablePush")
public class EnablePush {

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected PushMode pushMode;
    protected String extendParameter;

    /**
     * ��ȡpushMode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link PushMode }
     *     
     */
    public PushMode getPushMode() {
        return pushMode;
    }

    /**
     * ����pushMode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link PushMode }
     *     
     */
    public void setPushMode(PushMode value) {
        this.pushMode = value;
    }

    /**
     * ��ȡextendParameter���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtendParameter() {
        return extendParameter;
    }

    /**
     * ����extendParameter���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtendParameter(String value) {
        this.extendParameter = value;
    }

}
