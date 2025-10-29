
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
 *         &lt;element name="SynchAdhocConfFreeBusyResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="freebusy" type="{http://smc.huawei.com/}ArrayOfAdhocConfFreeBusyStateEx2" minOccurs="0"/>
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
    "synchAdhocConfFreeBusyResult",
    "freebusy"
})
@XmlRootElement(name = "SynchAdhocConfFreeBusyResponse")
public class SynchAdhocConfFreeBusyResponse {

    @XmlElement(name = "SynchAdhocConfFreeBusyResult")
    protected SmcErr2 synchAdhocConfFreeBusyResult;
    protected ArrayOfAdhocConfFreeBusyStateEx2 freebusy;

    /**
     * ��ȡsynchAdhocConfFreeBusyResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getSynchAdhocConfFreeBusyResult() {
        return synchAdhocConfFreeBusyResult;
    }

    /**
     * ����synchAdhocConfFreeBusyResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setSynchAdhocConfFreeBusyResult(SmcErr2 value) {
        this.synchAdhocConfFreeBusyResult = value;
    }

    /**
     * ��ȡfreebusy���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAdhocConfFreeBusyStateEx2 }
     *     
     */
    public ArrayOfAdhocConfFreeBusyStateEx2 getFreebusy() {
        return freebusy;
    }

    /**
     * ����freebusy���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAdhocConfFreeBusyStateEx2 }
     *     
     */
    public void setFreebusy(ArrayOfAdhocConfFreeBusyStateEx2 value) {
        this.freebusy = value;
    }

}
