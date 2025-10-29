
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>VdieoCapbilityItem complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="VdieoCapbilityItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Video" type="{http://smc.huawei.com/}VideoProtocol"/>
 *         &lt;element name="Formats" type="{http://smc.huawei.com/}ArrayOfVideoFormat" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VdieoCapbilityItem", propOrder = {
    "video",
    "formats"
})
public class VdieoCapbilityItem {

    @XmlElement(name = "Video", required = true)
    @XmlSchemaType(name = "string")
    protected VideoProtocol video;
    @XmlElement(name = "Formats")
    protected ArrayOfVideoFormat formats;

    /**
     * ��ȡvideo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link VideoProtocol }
     *     
     */
    public VideoProtocol getVideo() {
        return video;
    }

    /**
     * ����video���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link VideoProtocol }
     *     
     */
    public void setVideo(VideoProtocol value) {
        this.video = value;
    }

    /**
     * ��ȡformats���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfVideoFormat }
     *     
     */
    public ArrayOfVideoFormat getFormats() {
        return formats;
    }

    /**
     * ����formats���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfVideoFormat }
     *     
     */
    public void setFormats(ArrayOfVideoFormat value) {
        this.formats = value;
    }

}
