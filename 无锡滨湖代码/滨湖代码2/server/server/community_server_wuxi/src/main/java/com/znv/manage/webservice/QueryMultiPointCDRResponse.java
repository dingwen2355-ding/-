
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
 *         &lt;element name="QueryMultiPointCDRResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="cdr" type="{http://smc.huawei.com/}ArrayOfMultiPointCDR" minOccurs="0"/>
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
    "queryMultiPointCDRResult",
    "cdr",
    "pageInfo"
})
@XmlRootElement(name = "QueryMultiPointCDRResponse")
public class QueryMultiPointCDRResponse {

    @XmlElement(name = "QueryMultiPointCDRResult")
    protected SmcErr2 queryMultiPointCDRResult;
    protected ArrayOfMultiPointCDR cdr;
    protected PagesInfo pageInfo;

    /**
     * ��ȡqueryMultiPointCDRResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getQueryMultiPointCDRResult() {
        return queryMultiPointCDRResult;
    }

    /**
     * ����queryMultiPointCDRResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setQueryMultiPointCDRResult(SmcErr2 value) {
        this.queryMultiPointCDRResult = value;
    }

    /**
     * ��ȡcdr���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfMultiPointCDR }
     *     
     */
    public ArrayOfMultiPointCDR getCdr() {
        return cdr;
    }

    /**
     * ����cdr���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfMultiPointCDR }
     *     
     */
    public void setCdr(ArrayOfMultiPointCDR value) {
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
