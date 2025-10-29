
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
 *         &lt;element name="queryConfig" type="{http://smc.huawei.com/}PointToPointCDRQueryConfig" minOccurs="0"/>
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
@XmlRootElement(name = "QueryPointToPointCDR")
public class QueryPointToPointCDR {

    protected PointToPointCDRQueryConfig queryConfig;

    /**
     * ��ȡqueryConfig���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link PointToPointCDRQueryConfig }
     *     
     */
    public PointToPointCDRQueryConfig getQueryConfig() {
        return queryConfig;
    }

    /**
     * ����queryConfig���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link PointToPointCDRQueryConfig }
     *     
     */
    public void setQueryConfig(PointToPointCDRQueryConfig value) {
        this.queryConfig = value;
    }

}
