
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
 *         &lt;element name="QueryServerInfoResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="info" type="{http://smc.huawei.com/}SMCServerInfo" minOccurs="0"/>
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
    "queryServerInfoResult",
    "info"
})
@XmlRootElement(name = "QueryServerInfoResponse")
public class QueryServerInfoResponse {

    @XmlElement(name = "QueryServerInfoResult")
    protected SmcErr2 queryServerInfoResult;
    protected SMCServerInfo info;

    /**
     * ��ȡqueryServerInfoResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getQueryServerInfoResult() {
        return queryServerInfoResult;
    }

    /**
     * ����queryServerInfoResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setQueryServerInfoResult(SmcErr2 value) {
        this.queryServerInfoResult = value;
    }

    /**
     * ��ȡinfo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SMCServerInfo }
     *     
     */
    public SMCServerInfo getInfo() {
        return info;
    }

    /**
     * ����info���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SMCServerInfo }
     *     
     */
    public void setInfo(SMCServerInfo value) {
        this.info = value;
    }

}
