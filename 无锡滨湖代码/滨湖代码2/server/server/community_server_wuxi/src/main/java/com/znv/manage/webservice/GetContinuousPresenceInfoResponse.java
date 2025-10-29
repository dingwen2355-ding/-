
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
 *         &lt;element name="GetContinuousPresenceInfoResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="targets" type="{http://smc.huawei.com/}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="cpResource" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="supportCPModes" type="{http://smc.huawei.com/}ArrayOfContinuousPresenceMode" minOccurs="0"/>
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
    "getContinuousPresenceInfoResult",
    "targets",
    "cpResource",
    "supportCPModes"
})
@XmlRootElement(name = "GetContinuousPresenceInfoResponse")
public class GetContinuousPresenceInfoResponse {

    @XmlElement(name = "GetContinuousPresenceInfoResult")
    protected SmcErr2 getContinuousPresenceInfoResult;
    protected ArrayOfString targets;
    protected int cpResource;
    protected ArrayOfContinuousPresenceMode supportCPModes;

    /**
     * ��ȡgetContinuousPresenceInfoResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getGetContinuousPresenceInfoResult() {
        return getContinuousPresenceInfoResult;
    }

    /**
     * ����getContinuousPresenceInfoResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setGetContinuousPresenceInfoResult(SmcErr2 value) {
        this.getContinuousPresenceInfoResult = value;
    }

    /**
     * ��ȡtargets���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getTargets() {
        return targets;
    }

    /**
     * ����targets���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setTargets(ArrayOfString value) {
        this.targets = value;
    }

    /**
     * ��ȡcpResource���Ե�ֵ��
     * 
     */
    public int getCpResource() {
        return cpResource;
    }

    /**
     * ����cpResource���Ե�ֵ��
     * 
     */
    public void setCpResource(int value) {
        this.cpResource = value;
    }

    /**
     * ��ȡsupportCPModes���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfContinuousPresenceMode }
     *     
     */
    public ArrayOfContinuousPresenceMode getSupportCPModes() {
        return supportCPModes;
    }

    /**
     * ����supportCPModes���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfContinuousPresenceMode }
     *     
     */
    public void setSupportCPModes(ArrayOfContinuousPresenceMode value) {
        this.supportCPModes = value;
    }

}
