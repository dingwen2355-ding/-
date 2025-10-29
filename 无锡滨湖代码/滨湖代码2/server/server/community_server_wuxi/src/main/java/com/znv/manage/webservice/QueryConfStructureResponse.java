
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
 *         &lt;element name="QueryConfStructureResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="confStructure" type="{http://smc.huawei.com/}ConfStructure" minOccurs="0"/>
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
    "queryConfStructureResult",
    "confStructure"
})
@XmlRootElement(name = "QueryConfStructureResponse")
public class QueryConfStructureResponse {

    @XmlElement(name = "QueryConfStructureResult")
    protected SmcErr2 queryConfStructureResult;
    protected ConfStructure confStructure;

    /**
     * ��ȡqueryConfStructureResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getQueryConfStructureResult() {
        return queryConfStructureResult;
    }

    /**
     * ����queryConfStructureResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setQueryConfStructureResult(SmcErr2 value) {
        this.queryConfStructureResult = value;
    }

    /**
     * ��ȡconfStructure���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ConfStructure }
     *     
     */
    public ConfStructure getConfStructure() {
        return confStructure;
    }

    /**
     * ����confStructure���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ConfStructure }
     *     
     */
    public void setConfStructure(ConfStructure value) {
        this.confStructure = value;
    }

}
