
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
 *         &lt;element name="QueryConfListBySiteUriResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="confList" type="{http://smc.huawei.com/}ArrayOfConferenceInfo" minOccurs="0"/>
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
    "queryConfListBySiteUriResult",
    "confList",
    "pageInfo"
})
@XmlRootElement(name = "QueryConfListBySiteUriResponse")
public class QueryConfListBySiteUriResponse {

    @XmlElement(name = "QueryConfListBySiteUriResult")
    protected SmcErr2 queryConfListBySiteUriResult;
    protected ArrayOfConferenceInfo confList;
    protected PagesInfo pageInfo;

    /**
     * ��ȡqueryConfListBySiteUriResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getQueryConfListBySiteUriResult() {
        return queryConfListBySiteUriResult;
    }

    /**
     * ����queryConfListBySiteUriResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setQueryConfListBySiteUriResult(SmcErr2 value) {
        this.queryConfListBySiteUriResult = value;
    }

    /**
     * ��ȡconfList���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfConferenceInfo }
     *     
     */
    public ArrayOfConferenceInfo getConfList() {
        return confList;
    }

    /**
     * ����confList���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfConferenceInfo }
     *     
     */
    public void setConfList(ArrayOfConferenceInfo value) {
        this.confList = value;
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
