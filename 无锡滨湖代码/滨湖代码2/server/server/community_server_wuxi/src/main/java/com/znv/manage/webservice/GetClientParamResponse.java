
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
 *         &lt;element name="GetClientParamResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="param" type="{http://smc.huawei.com/}ClientParam" minOccurs="0"/>
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
    "getClientParamResult",
    "param"
})
@XmlRootElement(name = "GetClientParamResponse")
public class GetClientParamResponse {

    @XmlElement(name = "GetClientParamResult")
    protected SmcErr2 getClientParamResult;
    protected ClientParam param;

    /**
     * ��ȡgetClientParamResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getGetClientParamResult() {
        return getClientParamResult;
    }

    /**
     * ����getClientParamResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setGetClientParamResult(SmcErr2 value) {
        this.getClientParamResult = value;
    }

    /**
     * ��ȡparam���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ClientParam }
     *     
     */
    public ClientParam getParam() {
        return param;
    }

    /**
     * ����param���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ClientParam }
     *     
     */
    public void setParam(ClientParam value) {
        this.param = value;
    }

}
