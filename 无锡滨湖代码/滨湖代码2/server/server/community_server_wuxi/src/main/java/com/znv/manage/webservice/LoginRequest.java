
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
 *         &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clientType" type="{http://smc.huawei.com/}ClientType"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "userName",
    "clientType",
    "version"
})
@XmlRootElement(name = "LoginRequest")
public class LoginRequest {

    protected String userName;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected ClientType clientType;
    protected int version;

    /**
     * ��ȡuserName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserName() {
        return userName;
    }

    /**
     * ����userName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserName(String value) {
        this.userName = value;
    }

    /**
     * ��ȡclientType���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ClientType }
     *     
     */
    public ClientType getClientType() {
        return clientType;
    }

    /**
     * ����clientType���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ClientType }
     *     
     */
    public void setClientType(ClientType value) {
        this.clientType = value;
    }

    /**
     * ��ȡversion���Ե�ֵ��
     * 
     */
    public int getVersion() {
        return version;
    }

    /**
     * ����version���Ե�ֵ��
     * 
     */
    public void setVersion(int value) {
        this.version = value;
    }

}
