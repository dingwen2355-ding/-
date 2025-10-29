
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
 *         &lt;element name="SynchSiteFreeBusyResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
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
    "synchSiteFreeBusyResult",
    "freebusy"
})
@XmlRootElement(name = "SynchSiteFreeBusyResponse")
public class SynchSiteFreeBusyResponse {

    @XmlElement(name = "SynchSiteFreeBusyResult")
    protected SmcErr2 synchSiteFreeBusyResult;
    protected ArrayOfSiteFreeBusyStateEx2 freebusy;

    /**
     * ��ȡsynchSiteFreeBusyResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getSynchSiteFreeBusyResult() {
        return synchSiteFreeBusyResult;
    }

    /**
     * ����synchSiteFreeBusyResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setSynchSiteFreeBusyResult(SmcErr2 value) {
        this.synchSiteFreeBusyResult = value;
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
