
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="editConf" type="{http://smc.huawei.com/}ScheduledConfParamEx2" minOccurs="0"/>
 *         &lt;element name="sites" type="{http://smc.huawei.com/}ArrayOfScheduleSiteParam2" minOccurs="0"/>
 *         &lt;element name="beginTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
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
    "editConf",
    "sites",
    "beginTime"
})
@XmlRootElement(name = "EditFutureConf")
public class EditFutureConf {

    protected ScheduledConfParamEx2 editConf;
    protected ArrayOfScheduleSiteParam2 sites;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar beginTime;

    /**
     * ��ȡeditConf���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ScheduledConfParamEx2 }
     *     
     */
    public ScheduledConfParamEx2 getEditConf() {
        return editConf;
    }

    /**
     * ����editConf���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ScheduledConfParamEx2 }
     *     
     */
    public void setEditConf(ScheduledConfParamEx2 value) {
        this.editConf = value;
    }

    /**
     * ��ȡsites���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfScheduleSiteParam2 }
     *     
     */
    public ArrayOfScheduleSiteParam2 getSites() {
        return sites;
    }

    /**
     * ����sites���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfScheduleSiteParam2 }
     *     
     */
    public void setSites(ArrayOfScheduleSiteParam2 value) {
        this.sites = value;
    }

    /**
     * ��ȡbeginTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBeginTime() {
        return beginTime;
    }

    /**
     * ����beginTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBeginTime(XMLGregorianCalendar value) {
        this.beginTime = value;
    }

}
