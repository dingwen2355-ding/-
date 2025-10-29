
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
 *         &lt;element name="QueryConfSiteMcuResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="mcus" type="{http://smc.huawei.com/}ArrayOfSiteMcu" minOccurs="0"/>
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
    "queryConfSiteMcuResult",
    "mcus"
})
@XmlRootElement(name = "QueryConfSiteMcuResponse")
public class QueryConfSiteMcuResponse {

    @XmlElement(name = "QueryConfSiteMcuResult")
    protected SmcErr2 queryConfSiteMcuResult;
    protected ArrayOfSiteMcu mcus;

    /**
     * ��ȡqueryConfSiteMcuResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getQueryConfSiteMcuResult() {
        return queryConfSiteMcuResult;
    }

    /**
     * ����queryConfSiteMcuResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setQueryConfSiteMcuResult(SmcErr2 value) {
        this.queryConfSiteMcuResult = value;
    }

    /**
     * ��ȡmcus���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSiteMcu }
     *     
     */
    public ArrayOfSiteMcu getMcus() {
        return mcus;
    }

    /**
     * ����mcus���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSiteMcu }
     *     
     */
    public void setMcus(ArrayOfSiteMcu value) {
        this.mcus = value;
    }

}
