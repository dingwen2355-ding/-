
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
 *         &lt;element name="SetConfSiteVolumeResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
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
    "setConfSiteVolumeResult"
})
@XmlRootElement(name = "SetConfSiteVolumeResponse")
public class SetConfSiteVolumeResponse {

    @XmlElement(name = "SetConfSiteVolumeResult")
    protected SmcErr2 setConfSiteVolumeResult;

    /**
     * ��ȡsetConfSiteVolumeResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getSetConfSiteVolumeResult() {
        return setConfSiteVolumeResult;
    }

    /**
     * ����setConfSiteVolumeResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setSetConfSiteVolumeResult(SmcErr2 value) {
        this.setConfSiteVolumeResult = value;
    }

}
