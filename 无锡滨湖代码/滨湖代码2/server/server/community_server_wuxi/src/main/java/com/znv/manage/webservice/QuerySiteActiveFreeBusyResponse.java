
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
 *         &lt;element name="QuerySiteActiveFreeBusyResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="freebusy" type="{http://smc.huawei.com/}ArrayOfSiteFreeBusyStateEx2" minOccurs="0"/>
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
    "querySiteActiveFreeBusyResult",
    "freebusy"
})
@XmlRootElement(name = "QuerySiteActiveFreeBusyResponse")
public class QuerySiteActiveFreeBusyResponse {

    @XmlElement(name = "QuerySiteActiveFreeBusyResult")
    protected SmcErr2 querySiteActiveFreeBusyResult;
    protected ArrayOfSiteFreeBusyStateEx2 freebusy;

    /**
     * ��ȡquerySiteActiveFreeBusyResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getQuerySiteActiveFreeBusyResult() {
        return querySiteActiveFreeBusyResult;
    }

    /**
     * ����querySiteActiveFreeBusyResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setQuerySiteActiveFreeBusyResult(SmcErr2 value) {
        this.querySiteActiveFreeBusyResult = value;
    }

    /**
     * ��ȡfreebusy���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSiteFreeBusyStateEx2 }
     *     
     */
    public ArrayOfSiteFreeBusyStateEx2 getFreebusy() {
        return freebusy;
    }

    /**
     * ����freebusy���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSiteFreeBusyStateEx2 }
     *     
     */
    public void setFreebusy(ArrayOfSiteFreeBusyStateEx2 value) {
        this.freebusy = value;
    }

}
