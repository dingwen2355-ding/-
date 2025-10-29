
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
 *         &lt;element name="QueryCallInfoResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="confInfo" type="{http://smc.huawei.com/}ConfSimpleInfo" minOccurs="0"/>
 *         &lt;element name="RemoteSiteUri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "queryCallInfoResult",
    "confInfo",
    "remoteSiteUri"
})
@XmlRootElement(name = "QueryCallInfoResponse")
public class QueryCallInfoResponse {

    @XmlElement(name = "QueryCallInfoResult")
    protected SmcErr2 queryCallInfoResult;
    protected ConfSimpleInfo confInfo;
    @XmlElement(name = "RemoteSiteUri")
    protected String remoteSiteUri;

    /**
     * ��ȡqueryCallInfoResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getQueryCallInfoResult() {
        return queryCallInfoResult;
    }

    /**
     * ����queryCallInfoResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setQueryCallInfoResult(SmcErr2 value) {
        this.queryCallInfoResult = value;
    }

    /**
     * ��ȡconfInfo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ConfSimpleInfo }
     *     
     */
    public ConfSimpleInfo getConfInfo() {
        return confInfo;
    }

    /**
     * ����confInfo���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ConfSimpleInfo }
     *     
     */
    public void setConfInfo(ConfSimpleInfo value) {
        this.confInfo = value;
    }

    /**
     * ��ȡremoteSiteUri���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemoteSiteUri() {
        return remoteSiteUri;
    }

    /**
     * ����remoteSiteUri���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemoteSiteUri(String value) {
        this.remoteSiteUri = value;
    }

}
