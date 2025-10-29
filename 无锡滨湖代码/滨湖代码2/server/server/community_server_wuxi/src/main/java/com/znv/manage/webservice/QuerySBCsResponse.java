
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
 *         &lt;element name="QuerySBCsResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="sbcs" type="{http://smc.huawei.com/}ArrayOfSBCInfo" minOccurs="0"/>
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
    "querySBCsResult",
    "sbcs"
})
@XmlRootElement(name = "QuerySBCsResponse")
public class QuerySBCsResponse {

    @XmlElement(name = "QuerySBCsResult")
    protected SmcErr2 querySBCsResult;
    protected ArrayOfSBCInfo sbcs;

    /**
     * ��ȡquerySBCsResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getQuerySBCsResult() {
        return querySBCsResult;
    }

    /**
     * ����querySBCsResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setQuerySBCsResult(SmcErr2 value) {
        this.querySBCsResult = value;
    }

    /**
     * ��ȡsbcs���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSBCInfo }
     *     
     */
    public ArrayOfSBCInfo getSbcs() {
        return sbcs;
    }

    /**
     * ����sbcs���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSBCInfo }
     *     
     */
    public void setSbcs(ArrayOfSBCInfo value) {
        this.sbcs = value;
    }

}
