
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
 *         &lt;element name="SiteCameraCtrlResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
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
    "siteCameraCtrlResult"
})
@XmlRootElement(name = "SiteCameraCtrlResponse")
public class SiteCameraCtrlResponse {

    @XmlElement(name = "SiteCameraCtrlResult")
    protected SmcErr2 siteCameraCtrlResult;

    /**
     * ��ȡsiteCameraCtrlResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getSiteCameraCtrlResult() {
        return siteCameraCtrlResult;
    }

    /**
     * ����siteCameraCtrlResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setSiteCameraCtrlResult(SmcErr2 value) {
        this.siteCameraCtrlResult = value;
    }

}
