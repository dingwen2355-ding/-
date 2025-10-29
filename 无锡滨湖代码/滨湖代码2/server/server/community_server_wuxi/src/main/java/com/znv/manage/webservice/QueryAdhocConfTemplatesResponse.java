
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
 *         &lt;element name="QueryAdhocConfTemplatesResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="adhocConfTemplates" type="{http://smc.huawei.com/}ArrayOfAdhocConfTempplateParam" minOccurs="0"/>
 *         &lt;element name="pageInfo" type="{http://smc.huawei.com/}PagesInfo" minOccurs="0"/>
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
    "queryAdhocConfTemplatesResult",
    "adhocConfTemplates",
    "pageInfo"
})
@XmlRootElement(name = "QueryAdhocConfTemplatesResponse")
public class QueryAdhocConfTemplatesResponse {

    @XmlElement(name = "QueryAdhocConfTemplatesResult")
    protected SmcErr2 queryAdhocConfTemplatesResult;
    protected ArrayOfAdhocConfTempplateParam adhocConfTemplates;
    protected PagesInfo pageInfo;

    /**
     * ��ȡqueryAdhocConfTemplatesResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getQueryAdhocConfTemplatesResult() {
        return queryAdhocConfTemplatesResult;
    }

    /**
     * ����queryAdhocConfTemplatesResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setQueryAdhocConfTemplatesResult(SmcErr2 value) {
        this.queryAdhocConfTemplatesResult = value;
    }

    /**
     * ��ȡadhocConfTemplates���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAdhocConfTempplateParam }
     *     
     */
    public ArrayOfAdhocConfTempplateParam getAdhocConfTemplates() {
        return adhocConfTemplates;
    }

    /**
     * ����adhocConfTemplates���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAdhocConfTempplateParam }
     *     
     */
    public void setAdhocConfTemplates(ArrayOfAdhocConfTempplateParam value) {
        this.adhocConfTemplates = value;
    }

    /**
     * ��ȡpageInfo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link PagesInfo }
     *     
     */
    public PagesInfo getPageInfo() {
        return pageInfo;
    }

    /**
     * ����pageInfo���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link PagesInfo }
     *     
     */
    public void setPageInfo(PagesInfo value) {
        this.pageInfo = value;
    }

}
