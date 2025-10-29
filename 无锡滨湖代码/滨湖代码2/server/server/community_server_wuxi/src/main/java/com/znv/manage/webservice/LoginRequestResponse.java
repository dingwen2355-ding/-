
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
 *         &lt;element name="LoginRequestResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="randomSequence" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="hashType" type="{http://smc.huawei.com/}HashType"/>
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
    "loginRequestResult",
    "randomSequence",
    "hashType"
})
@XmlRootElement(name = "LoginRequestResponse")
public class LoginRequestResponse {

    @XmlElement(name = "LoginRequestResult")
    protected SmcErr2 loginRequestResult;
    protected byte[] randomSequence;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected HashType hashType;

    /**
     * ��ȡloginRequestResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getLoginRequestResult() {
        return loginRequestResult;
    }

    /**
     * ����loginRequestResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setLoginRequestResult(SmcErr2 value) {
        this.loginRequestResult = value;
    }

    /**
     * ��ȡrandomSequence���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getRandomSequence() {
        return randomSequence;
    }

    /**
     * ����randomSequence���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setRandomSequence(byte[] value) {
        this.randomSequence = value;
    }

    /**
     * ��ȡhashType���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link HashType }
     *     
     */
    public HashType getHashType() {
        return hashType;
    }

    /**
     * ����hashType���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link HashType }
     *     
     */
    public void setHashType(HashType value) {
        this.hashType = value;
    }

}
