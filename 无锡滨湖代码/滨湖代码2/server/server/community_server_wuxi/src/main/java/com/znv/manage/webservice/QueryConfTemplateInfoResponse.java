
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
 *         &lt;element name="QueryConfTemplateInfoResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="confTemplate" type="{http://smc.huawei.com/}ConfTempplateParam" minOccurs="0"/>
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
    "queryConfTemplateInfoResult",
    "confTemplate"
})
@XmlRootElement(name = "QueryConfTemplateInfoResponse")
public class QueryConfTemplateInfoResponse {

    @XmlElement(name = "QueryConfTemplateInfoResult")
    protected SmcErr2 queryConfTemplateInfoResult;
    protected ConfTempplateParam confTemplate;

    /**
     * ��ȡqueryConfTemplateInfoResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getQueryConfTemplateInfoResult() {
        return queryConfTemplateInfoResult;
    }

    /**
     * ����queryConfTemplateInfoResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setQueryConfTemplateInfoResult(SmcErr2 value) {
        this.queryConfTemplateInfoResult = value;
    }

    /**
     * ��ȡconfTemplate���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ConfTempplateParam }
     *     
     */
    public ConfTempplateParam getConfTemplate() {
        return confTemplate;
    }

    /**
     * ����confTemplate���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ConfTempplateParam }
     *     
     */
    public void setConfTemplate(ConfTempplateParam value) {
        this.confTemplate = value;
    }

}
