
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
 *         &lt;element name="confId" type="{http://smc.huawei.com/}ConferenceId" minOccurs="0"/>
 *         &lt;element name="nodeInfos" type="{http://smc.huawei.com/}ArrayOfMergeNodeInfo" minOccurs="0"/>
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
    "confId",
    "nodeInfos"
})
@XmlRootElement(name = "MergeConf")
public class MergeConf {

    protected ConferenceId confId;
    protected ArrayOfMergeNodeInfo nodeInfos;

    /**
     * ��ȡconfId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ConferenceId }
     *     
     */
    public ConferenceId getConfId() {
        return confId;
    }

    /**
     * ����confId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ConferenceId }
     *     
     */
    public void setConfId(ConferenceId value) {
        this.confId = value;
    }

    /**
     * ��ȡnodeInfos���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfMergeNodeInfo }
     *     
     */
    public ArrayOfMergeNodeInfo getNodeInfos() {
        return nodeInfos;
    }

    /**
     * ����nodeInfos���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfMergeNodeInfo }
     *     
     */
    public void setNodeInfos(ArrayOfMergeNodeInfo value) {
        this.nodeInfos = value;
    }

}
