
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
 *         &lt;element name="QueryPointToPointCDRResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="cdr" type="{http://smc.huawei.com/}ArrayOfPointToPointCDR" minOccurs="0"/>
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
    "queryPointToPointCDRResult",
    "cdr",
    "pageInfo"
})
@XmlRootElement(name = "QueryPointToPointCDRResponse")
public class QueryPointToPointCDRResponse {

    @XmlElement(name = "QueryPointToPointCDRResult")
    protected SmcErr2 queryPointToPointCDRResult;
    protected ArrayOfPointToPointCDR cdr;
    protected PagesInfo pageInfo;

    /**
     * ��ȡqueryPointToPointCDRResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getQueryPointToPointCDRResult() {
        return queryPointToPointCDRResult;
    }

    /**
     * ����queryPointToPointCDRResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setQueryPointToPointCDRResult(SmcErr2 value) {
        this.queryPointToPointCDRResult = value;
    }

    /**
     * ��ȡcdr���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPointToPointCDR }
     *     
     */
    public ArrayOfPointToPointCDR getCdr() {
        return cdr;
    }

    /**
     * ����cdr���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPointToPointCDR }
     *     
     */
    public void setCdr(ArrayOfPointToPointCDR value) {
        this.cdr = value;
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
