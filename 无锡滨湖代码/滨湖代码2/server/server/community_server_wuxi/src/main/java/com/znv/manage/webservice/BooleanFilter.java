
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>BooleanFilter complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="BooleanFilter">
 *   &lt;complexContent>
 *     &lt;extension base="{http://smc.huawei.com/}FilterBase">
 *       &lt;sequence>
 *         &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BooleanFilter", propOrder = {
    "value"
})
public class BooleanFilter
    extends FilterBase
{

    @XmlElement(name = "Value")
    protected boolean value;

    /**
     * ��ȡvalue���Ե�ֵ��
     * 
     */
    public boolean isValue() {
        return value;
    }

    /**
     * ����value���Ե�ֵ��
     * 
     */
    public void setValue(boolean value) {
        this.value = value;
    }

}
