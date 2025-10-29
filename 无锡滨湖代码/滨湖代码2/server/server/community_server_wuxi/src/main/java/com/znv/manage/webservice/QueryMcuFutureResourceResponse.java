
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
 *         &lt;element name="QueryMcuFutureResourceResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="mcuResList" type="{http://smc.huawei.com/}ArrayOfMcuResource" minOccurs="0"/>
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
    "queryMcuFutureResourceResult",
    "mcuResList"
})
@XmlRootElement(name = "QueryMcuFutureResourceResponse")
public class QueryMcuFutureResourceResponse {

    @XmlElement(name = "QueryMcuFutureResourceResult")
    protected SmcErr2 queryMcuFutureResourceResult;
    protected ArrayOfMcuResource mcuResList;

    /**
     * ��ȡqueryMcuFutureResourceResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getQueryMcuFutureResourceResult() {
        return queryMcuFutureResourceResult;
    }

    /**
     * ����queryMcuFutureResourceResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setQueryMcuFutureResourceResult(SmcErr2 value) {
        this.queryMcuFutureResourceResult = value;
    }

    /**
     * ��ȡmcuResList���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfMcuResource }
     *     
     */
    public ArrayOfMcuResource getMcuResList() {
        return mcuResList;
    }

    /**
     * ����mcuResList���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfMcuResource }
     *     
     */
    public void setMcuResList(ArrayOfMcuResource value) {
        this.mcuResList = value;
    }

}
