
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
 *         &lt;element name="QueryOraganizationResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="orgs" type="{http://smc.huawei.com/}ArrayOfOrganizationItem" minOccurs="0"/>
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
    "queryOraganizationResult",
    "orgs"
})
@XmlRootElement(name = "QueryOraganizationResponse")
public class QueryOraganizationResponse {

    @XmlElement(name = "QueryOraganizationResult")
    protected SmcErr2 queryOraganizationResult;
    protected ArrayOfOrganizationItem orgs;

    /**
     * ��ȡqueryOraganizationResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getQueryOraganizationResult() {
        return queryOraganizationResult;
    }

    /**
     * ����queryOraganizationResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setQueryOraganizationResult(SmcErr2 value) {
        this.queryOraganizationResult = value;
    }

    /**
     * ��ȡorgs���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrganizationItem }
     *     
     */
    public ArrayOfOrganizationItem getOrgs() {
        return orgs;
    }

    /**
     * ����orgs���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrganizationItem }
     *     
     */
    public void setOrgs(ArrayOfOrganizationItem value) {
        this.orgs = value;
    }

}
