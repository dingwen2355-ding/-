
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
 *         &lt;element name="QueryConfTemplatesResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="confTemplates" type="{http://smc.huawei.com/}ArrayOfConfTemplateInfo" minOccurs="0"/>
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
    "queryConfTemplatesResult",
    "confTemplates",
    "pageInfo"
})
@XmlRootElement(name = "QueryConfTemplatesResponse")
public class QueryConfTemplatesResponse {

    @XmlElement(name = "QueryConfTemplatesResult")
    protected SmcErr2 queryConfTemplatesResult;
    protected ArrayOfConfTemplateInfo confTemplates;
    protected PagesInfo pageInfo;

    /**
     * ��ȡqueryConfTemplatesResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getQueryConfTemplatesResult() {
        return queryConfTemplatesResult;
    }

    /**
     * ����queryConfTemplatesResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setQueryConfTemplatesResult(SmcErr2 value) {
        this.queryConfTemplatesResult = value;
    }

    /**
     * ��ȡconfTemplates���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfConfTemplateInfo }
     *     
     */
    public ArrayOfConfTemplateInfo getConfTemplates() {
        return confTemplates;
    }

    /**
     * ����confTemplates���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfConfTemplateInfo }
     *     
     */
    public void setConfTemplates(ArrayOfConfTemplateInfo value) {
        this.confTemplates = value;
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
