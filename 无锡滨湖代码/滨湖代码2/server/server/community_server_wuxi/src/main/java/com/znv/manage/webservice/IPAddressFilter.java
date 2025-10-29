
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>IPAddressFilter complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="IPAddressFilter">
 *   &lt;complexContent>
 *     &lt;extension base="{http://smc.huawei.com/}FilterBase">
 *       &lt;sequence>
 *         &lt;element name="Value" type="{http://smc.huawei.com/}IPAddressEx" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IPAddressFilter", propOrder = {
    "value"
})
public class IPAddressFilter
    extends FilterBase
{

    @XmlElement(name = "Value")
    protected IPAddressEx value;

    /**
     * ��ȡvalue���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link IPAddressEx }
     *     
     */
    public IPAddressEx getValue() {
        return value;
    }

    /**
     * ����value���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link IPAddressEx }
     *     
     */
    public void setValue(IPAddressEx value) {
        this.value = value;
    }

}
