
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="queryConfig" type="{http://smc.huawei.com/}MultiPointCDRQueryConfig" minOccurs="0"/>
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
    "queryConfig"
})
@XmlRootElement(name = "QueryMultiPointCDR")
public class QueryMultiPointCDR {

    protected MultiPointCDRQueryConfig queryConfig;

    /**
     * ��ȡqueryConfig���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link MultiPointCDRQueryConfig }
     *     
     */
    public MultiPointCDRQueryConfig getQueryConfig() {
        return queryConfig;
    }

    /**
     * ����queryConfig���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link MultiPointCDRQueryConfig }
     *     
     */
    public void setQueryConfig(MultiPointCDRQueryConfig value) {
        this.queryConfig = value;
    }

}
