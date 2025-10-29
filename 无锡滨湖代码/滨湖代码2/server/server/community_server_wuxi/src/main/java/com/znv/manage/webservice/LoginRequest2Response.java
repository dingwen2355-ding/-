
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
 *         &lt;element name="LoginRequest2Result" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="randomSequence" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="salt" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
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
    "loginRequest2Result",
    "randomSequence",
    "salt",
    "hashType"
})
@XmlRootElement(name = "LoginRequest2Response")
public class LoginRequest2Response {

    @XmlElement(name = "LoginRequest2Result")
    protected SmcErr2 loginRequest2Result;
    protected byte[] randomSequence;
    protected byte[] salt;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected HashType hashType;

    /**
     * ��ȡloginRequest2Result���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getLoginRequest2Result() {
        return loginRequest2Result;
    }

    /**
     * ����loginRequest2Result���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setLoginRequest2Result(SmcErr2 value) {
        this.loginRequest2Result = value;
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
     * ��ȡsalt���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getSalt() {
        return salt;
    }

    /**
     * ����salt���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setSalt(byte[] value) {
        this.salt = value;
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
