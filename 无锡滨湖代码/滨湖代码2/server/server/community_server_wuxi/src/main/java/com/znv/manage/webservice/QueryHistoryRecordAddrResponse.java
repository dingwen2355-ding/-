
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
 *         &lt;element name="QueryHistoryRecordAddrResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="recordAddrs" type="{http://smc.huawei.com/}ArrayOfConfRecordAddr" minOccurs="0"/>
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
    "queryHistoryRecordAddrResult",
    "recordAddrs"
})
@XmlRootElement(name = "QueryHistoryRecordAddrResponse")
public class QueryHistoryRecordAddrResponse {

    @XmlElement(name = "QueryHistoryRecordAddrResult")
    protected SmcErr2 queryHistoryRecordAddrResult;
    protected ArrayOfConfRecordAddr recordAddrs;

    /**
     * ��ȡqueryHistoryRecordAddrResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getQueryHistoryRecordAddrResult() {
        return queryHistoryRecordAddrResult;
    }

    /**
     * ����queryHistoryRecordAddrResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setQueryHistoryRecordAddrResult(SmcErr2 value) {
        this.queryHistoryRecordAddrResult = value;
    }

    /**
     * ��ȡrecordAddrs���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfConfRecordAddr }
     *     
     */
    public ArrayOfConfRecordAddr getRecordAddrs() {
        return recordAddrs;
    }

    /**
     * ����recordAddrs���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfConfRecordAddr }
     *     
     */
    public void setRecordAddrs(ArrayOfConfRecordAddr value) {
        this.recordAddrs = value;
    }

}
