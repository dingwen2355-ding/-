
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ScheduleConfSubscribe complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ScheduleConfSubscribe">
 *   &lt;complexContent>
 *     &lt;extension base="{http://smc.huawei.com/}SubscribeInfo">
 *       &lt;sequence>
 *         &lt;element name="ConfId" type="{http://smc.huawei.com/}ArrayOfUnsignedInt" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduleConfSubscribe", propOrder = {
    "confId"
})
public class ScheduleConfSubscribe
    extends SubscribeInfo
{

    @XmlElement(name = "ConfId")
    protected ArrayOfUnsignedInt confId;

    /**
     * ��ȡconfId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfUnsignedInt }
     *     
     */
    public ArrayOfUnsignedInt getConfId() {
        return confId;
    }

    /**
     * ����confId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfUnsignedInt }
     *     
     */
    public void setConfId(ArrayOfUnsignedInt value) {
        this.confId = value;
    }

}
