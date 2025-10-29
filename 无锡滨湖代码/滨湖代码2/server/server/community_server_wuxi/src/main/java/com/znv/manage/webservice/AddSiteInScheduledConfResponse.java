
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
 *         &lt;element name="AddSiteInScheduledConfResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="confs" type="{http://smc.huawei.com/}ArrayOfScheduledConfInfo" minOccurs="0"/>
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
    "addSiteInScheduledConfResult",
    "confs"
})
@XmlRootElement(name = "AddSiteInScheduledConfResponse")
public class AddSiteInScheduledConfResponse {

    @XmlElement(name = "AddSiteInScheduledConfResult")
    protected SmcErr2 addSiteInScheduledConfResult;
    protected ArrayOfScheduledConfInfo confs;

    /**
     * ��ȡaddSiteInScheduledConfResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getAddSiteInScheduledConfResult() {
        return addSiteInScheduledConfResult;
    }

    /**
     * ����addSiteInScheduledConfResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setAddSiteInScheduledConfResult(SmcErr2 value) {
        this.addSiteInScheduledConfResult = value;
    }

    /**
     * ��ȡconfs���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfScheduledConfInfo }
     *     
     */
    public ArrayOfScheduledConfInfo getConfs() {
        return confs;
    }

    /**
     * ����confs���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfScheduledConfInfo }
     *     
     */
    public void setConfs(ArrayOfScheduledConfInfo value) {
        this.confs = value;
    }

}
