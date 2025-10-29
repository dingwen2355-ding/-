
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>ContinuousPresenceParam complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ContinuousPresenceParam">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Mode" type="{http://smc.huawei.com/}ContinuousPresenceMode"/>
 *         &lt;element name="SubPics" type="{http://smc.huawei.com/}ArrayOfString" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContinuousPresenceParam", propOrder = {
    "mode",
    "subPics"
})
public class ContinuousPresenceParam {

    @XmlElement(name = "Mode", required = true)
    @XmlSchemaType(name = "string")
    protected ContinuousPresenceMode mode;
    @XmlElement(name = "SubPics")
    protected ArrayOfString subPics;

    /**
     * ��ȡmode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ContinuousPresenceMode }
     *     
     */
    public ContinuousPresenceMode getMode() {
        return mode;
    }

    /**
     * ����mode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ContinuousPresenceMode }
     *     
     */
    public void setMode(ContinuousPresenceMode value) {
        this.mode = value;
    }

    /**
     * ��ȡsubPics���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getSubPics() {
        return subPics;
    }

    /**
     * ����subPics���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setSubPics(ArrayOfString value) {
        this.subPics = value;
    }

}
