
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="newConf" type="{http://smc.huawei.com/}ScheduledConfParamEx2" minOccurs="0"/>
 *         &lt;element name="sites" type="{http://smc.huawei.com/}ArrayOfScheduleSiteParam2" minOccurs="0"/>
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
    "newConf",
    "sites"
})
@XmlRootElement(name = "ScheduleConf")
public class ScheduleConf {

    protected ScheduledConfParamEx2 newConf;
    protected ArrayOfScheduleSiteParam2 sites;

    /**
     * ��ȡnewConf���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ScheduledConfParamEx2 }
     *     
     */
    public ScheduledConfParamEx2 getNewConf() {
        return newConf;
    }

    /**
     * ����newConf���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ScheduledConfParamEx2 }
     *     
     */
    public void setNewConf(ScheduledConfParamEx2 value) {
        this.newConf = value;
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

}
