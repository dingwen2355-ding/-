
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>TVWallParam complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="TVWallParam">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TVWallSite" type="{http://smc.huawei.com/}TVWallSiteParam" minOccurs="0"/>
 *         &lt;element name="TVWallSource" type="{http://smc.huawei.com/}TVWallSourceParam" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TVWallParam", propOrder = {
    "tvWallSite",
    "tvWallSource"
})
public class TVWallParam {

    @XmlElement(name = "TVWallSite")
    protected TVWallSiteParam tvWallSite;
    @XmlElement(name = "TVWallSource")
    protected TVWallSourceParam tvWallSource;

    /**
     * ��ȡtvWallSite���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link TVWallSiteParam }
     *     
     */
    public TVWallSiteParam getTVWallSite() {
        return tvWallSite;
    }

    /**
     * ����tvWallSite���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link TVWallSiteParam }
     *     
     */
    public void setTVWallSite(TVWallSiteParam value) {
        this.tvWallSite = value;
    }

    /**
     * ��ȡtvWallSource���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link TVWallSourceParam }
     *     
     */
    public TVWallSourceParam getTVWallSource() {
        return tvWallSource;
    }

    /**
     * ����tvWallSource���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link TVWallSourceParam }
     *     
     */
    public void setTVWallSource(TVWallSourceParam value) {
        this.tvWallSource = value;
    }

}
