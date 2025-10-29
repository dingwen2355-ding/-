
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
 *         &lt;element name="GetTempURIResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="uri" type="{http://smc.huawei.com/}ArrayOfTempURI" minOccurs="0"/>
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
    "getTempURIResult",
    "uri"
})
@XmlRootElement(name = "GetTempURIResponse")
public class GetTempURIResponse {

    @XmlElement(name = "GetTempURIResult")
    protected SmcErr2 getTempURIResult;
    protected ArrayOfTempURI uri;

    /**
     * ��ȡgetTempURIResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getGetTempURIResult() {
        return getTempURIResult;
    }

    /**
     * ����getTempURIResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setGetTempURIResult(SmcErr2 value) {
        this.getTempURIResult = value;
    }

    /**
     * ��ȡuri���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfTempURI }
     *     
     */
    public ArrayOfTempURI getUri() {
        return uri;
    }

    /**
     * ����uri���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfTempURI }
     *     
     */
    public void setUri(ArrayOfTempURI value) {
        this.uri = value;
    }

}
