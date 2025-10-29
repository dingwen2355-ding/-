
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
 *         &lt;element name="confId" type="{http://smc.huawei.com/}ConferenceId" minOccurs="0"/>
 *         &lt;element name="vSAttrParamList" type="{http://smc.huawei.com/}ArrayOfWSCtrlSiteCommParam" minOccurs="0"/>
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
    "confId",
    "vsAttrParamList"
})
@XmlRootElement(name = "SetVSAttrCtrl")
public class SetVSAttrCtrl {

    protected ConferenceId confId;
    @XmlElement(name = "vSAttrParamList")
    protected ArrayOfWSCtrlSiteCommParam vsAttrParamList;

    /**
     * ��ȡconfId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ConferenceId }
     *     
     */
    public ConferenceId getConfId() {
        return confId;
    }

    /**
     * ����confId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ConferenceId }
     *     
     */
    public void setConfId(ConferenceId value) {
        this.confId = value;
    }

    /**
     * ��ȡvsAttrParamList���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfWSCtrlSiteCommParam }
     *     
     */
    public ArrayOfWSCtrlSiteCommParam getVSAttrParamList() {
        return vsAttrParamList;
    }

    /**
     * ����vsAttrParamList���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfWSCtrlSiteCommParam }
     *     
     */
    public void setVSAttrParamList(ArrayOfWSCtrlSiteCommParam value) {
        this.vsAttrParamList = value;
    }

}
