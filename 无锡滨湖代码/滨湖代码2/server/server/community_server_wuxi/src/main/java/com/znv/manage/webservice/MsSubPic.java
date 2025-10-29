
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>MsSubPic complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="MsSubPic">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SiteUri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StreamID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="StreamHight" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="StreamWidth" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MsSubPic", propOrder = {
    "siteUri",
    "streamID",
    "streamHight",
    "streamWidth"
})
public class MsSubPic {

    @XmlElement(name = "SiteUri")
    protected String siteUri;
    @XmlElement(name = "StreamID")
    protected int streamID;
    @XmlElement(name = "StreamHight", required = true, type = Integer.class, nillable = true)
    protected Integer streamHight;
    @XmlElement(name = "StreamWidth", required = true, type = Integer.class, nillable = true)
    protected Integer streamWidth;

    /**
     * ��ȡsiteUri���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiteUri() {
        return siteUri;
    }

    /**
     * ����siteUri���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiteUri(String value) {
        this.siteUri = value;
    }

    /**
     * ��ȡstreamID���Ե�ֵ��
     * 
     */
    public int getStreamID() {
        return streamID;
    }

    /**
     * ����streamID���Ե�ֵ��
     * 
     */
    public void setStreamID(int value) {
        this.streamID = value;
    }

    /**
     * ��ȡstreamHight���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStreamHight() {
        return streamHight;
    }

    /**
     * ����streamHight���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStreamHight(Integer value) {
        this.streamHight = value;
    }

    /**
     * ��ȡstreamWidth���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStreamWidth() {
        return streamWidth;
    }

    /**
     * ����streamWidth���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStreamWidth(Integer value) {
        this.streamWidth = value;
    }

}
