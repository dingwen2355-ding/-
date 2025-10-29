
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>PointToPointCDR complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="PointToPointCDR">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CallingUri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CallingProtocol" type="{http://smc.huawei.com/}CallProtocol"/>
 *         &lt;element name="CalledUri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CalledProtocol" type="{http://smc.huawei.com/}CallProtocol"/>
 *         &lt;element name="Rate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StartTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="EndTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="InTerminalIsSoftTerminal" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="OutTerminalIsSoftTerminal" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PointToPointCDR", propOrder = {
    "callingUri",
    "callingProtocol",
    "calledUri",
    "calledProtocol",
    "rate",
    "startTime",
    "endTime",
    "inTerminalIsSoftTerminal",
    "outTerminalIsSoftTerminal"
})
public class PointToPointCDR {

    @XmlElement(name = "CallingUri")
    protected String callingUri;
    @XmlElement(name = "CallingProtocol", required = true)
    @XmlSchemaType(name = "string")
    protected CallProtocol callingProtocol;
    @XmlElement(name = "CalledUri")
    protected String calledUri;
    @XmlElement(name = "CalledProtocol", required = true)
    @XmlSchemaType(name = "string")
    protected CallProtocol calledProtocol;
    @XmlElement(name = "Rate")
    protected String rate;
    @XmlElement(name = "StartTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startTime;
    @XmlElement(name = "EndTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endTime;
    @XmlElement(name = "InTerminalIsSoftTerminal", required = true, type = Boolean.class, nillable = true)
    protected Boolean inTerminalIsSoftTerminal;
    @XmlElement(name = "OutTerminalIsSoftTerminal", required = true, type = Boolean.class, nillable = true)
    protected Boolean outTerminalIsSoftTerminal;

    /**
     * ��ȡcallingUri���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCallingUri() {
        return callingUri;
    }

    /**
     * ����callingUri���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCallingUri(String value) {
        this.callingUri = value;
    }

    /**
     * ��ȡcallingProtocol���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link CallProtocol }
     *     
     */
    public CallProtocol getCallingProtocol() {
        return callingProtocol;
    }

    /**
     * ����callingProtocol���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link CallProtocol }
     *     
     */
    public void setCallingProtocol(CallProtocol value) {
        this.callingProtocol = value;
    }

    /**
     * ��ȡcalledUri���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCalledUri() {
        return calledUri;
    }

    /**
     * ����calledUri���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCalledUri(String value) {
        this.calledUri = value;
    }

    /**
     * ��ȡcalledProtocol���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link CallProtocol }
     *     
     */
    public CallProtocol getCalledProtocol() {
        return calledProtocol;
    }

    /**
     * ����calledProtocol���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link CallProtocol }
     *     
     */
    public void setCalledProtocol(CallProtocol value) {
        this.calledProtocol = value;
    }

    /**
     * ��ȡrate���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRate() {
        return rate;
    }

    /**
     * ����rate���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRate(String value) {
        this.rate = value;
    }

    /**
     * ��ȡstartTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartTime() {
        return startTime;
    }

    /**
     * ����startTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartTime(XMLGregorianCalendar value) {
        this.startTime = value;
    }

    /**
     * ��ȡendTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndTime() {
        return endTime;
    }

    /**
     * ����endTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndTime(XMLGregorianCalendar value) {
        this.endTime = value;
    }

    /**
     * ��ȡinTerminalIsSoftTerminal���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isInTerminalIsSoftTerminal() {
        return inTerminalIsSoftTerminal;
    }

    /**
     * ����inTerminalIsSoftTerminal���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setInTerminalIsSoftTerminal(Boolean value) {
        this.inTerminalIsSoftTerminal = value;
    }

    /**
     * ��ȡoutTerminalIsSoftTerminal���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOutTerminalIsSoftTerminal() {
        return outTerminalIsSoftTerminal;
    }

    /**
     * ����outTerminalIsSoftTerminal���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOutTerminalIsSoftTerminal(Boolean value) {
        this.outTerminalIsSoftTerminal = value;
    }

}
