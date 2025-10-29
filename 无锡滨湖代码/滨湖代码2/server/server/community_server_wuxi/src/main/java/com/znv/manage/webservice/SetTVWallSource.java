
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
 *         &lt;element name="tvWallParam" type="{http://smc.huawei.com/}TVWallParam" minOccurs="0"/>
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
    "tvWallParam"
})
@XmlRootElement(name = "SetTVWallSource")
public class SetTVWallSource {

    protected TVWallParam tvWallParam;

    /**
     * ��ȡtvWallParam���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link TVWallParam }
     *     
     */
    public TVWallParam getTvWallParam() {
        return tvWallParam;
    }

    /**
     * ����tvWallParam���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link TVWallParam }
     *     
     */
    public void setTvWallParam(TVWallParam value) {
        this.tvWallParam = value;
    }

}
