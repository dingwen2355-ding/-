
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>VideoParam complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="VideoParam">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Protocol" type="{http://smc.huawei.com/}VideoProtocol"/>
 *         &lt;element name="Format" type="{http://smc.huawei.com/}VideoFormat"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VideoParam", propOrder = {
    "protocol",
    "format"
})
public class VideoParam {

    @XmlElement(name = "Protocol", required = true)
    @XmlSchemaType(name = "string")
    protected VideoProtocol protocol;
    @XmlElement(name = "Format", required = true)
    @XmlSchemaType(name = "string")
    protected VideoFormat format;

    /**
     * ��ȡprotocol���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link VideoProtocol }
     *     
     */
    public VideoProtocol getProtocol() {
        return protocol;
    }

    /**
     * ����protocol���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link VideoProtocol }
     *     
     */
    public void setProtocol(VideoProtocol value) {
        this.protocol = value;
    }

    /**
     * ��ȡformat���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link VideoFormat }
     *     
     */
    public VideoFormat getFormat() {
        return format;
    }

    /**
     * ����format���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link VideoFormat }
     *     
     */
    public void setFormat(VideoFormat value) {
        this.format = value;
    }

}
