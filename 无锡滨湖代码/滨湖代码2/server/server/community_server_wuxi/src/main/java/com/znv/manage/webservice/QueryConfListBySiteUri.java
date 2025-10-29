
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
 *         &lt;element name="siteUriList" type="{http://smc.huawei.com/}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="confId" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="queryConfig" type="{http://smc.huawei.com/}QueryConfig" minOccurs="0"/>
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
    "siteUriList",
    "confId",
    "queryConfig"
})
@XmlRootElement(name = "QueryConfListBySiteUri")
public class QueryConfListBySiteUri {

    protected ArrayOfString siteUriList;
    @XmlSchemaType(name = "unsignedInt")
    protected long confId;
    protected QueryConfig queryConfig;

    /**
     * ��ȡsiteUriList���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getSiteUriList() {
        return siteUriList;
    }

    /**
     * ����siteUriList���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setSiteUriList(ArrayOfString value) {
        this.siteUriList = value;
    }

    /**
     * ��ȡconfId���Ե�ֵ��
     * 
     */
    public long getConfId() {
        return confId;
    }

    /**
     * ����confId���Ե�ֵ��
     * 
     */
    public void setConfId(long value) {
        this.confId = value;
    }

    /**
     * ��ȡqueryConfig���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link QueryConfig }
     *     
     */
    public QueryConfig getQueryConfig() {
        return queryConfig;
    }

    /**
     * ����queryConfig���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link QueryConfig }
     *     
     */
    public void setQueryConfig(QueryConfig value) {
        this.queryConfig = value;
    }

}
