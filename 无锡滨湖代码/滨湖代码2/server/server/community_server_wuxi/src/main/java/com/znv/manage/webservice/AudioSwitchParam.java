
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>AudioSwitchParam complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="AudioSwitchParam">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SwitchGate" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AudioSwitchParam", propOrder = {
    "switchGate"
})
public class AudioSwitchParam {

    @XmlElement(name = "SwitchGate")
    @XmlSchemaType(name = "unsignedInt")
    protected long switchGate;

    /**
     * ��ȡswitchGate���Ե�ֵ��
     * 
     */
    public long getSwitchGate() {
        return switchGate;
    }

    /**
     * ����switchGate���Ե�ֵ��
     * 
     */
    public void setSwitchGate(long value) {
        this.switchGate = value;
    }

}
