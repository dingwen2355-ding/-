
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


/**
 * <p>CameraParam complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="CameraParam">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CameraOpType" type="{http://smc.huawei.com/}CameraOperationType"/>
 *         &lt;element name="CameraAction" type="{http://smc.huawei.com/}CameraActionType"/>
 *         &lt;element name="CameraSeq" type="{http://smc.huawei.com/}CameraSequence"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CameraParam", propOrder = {
    "cameraOpType",
    "cameraAction",
    "cameraSeq"
})
public class CameraParam {

    @XmlElement(name = "CameraOpType", required = true)
    @XmlSchemaType(name = "string")
    protected CameraOperationType cameraOpType;
    @XmlElement(name = "CameraAction", required = true)
    @XmlSchemaType(name = "string")
    protected CameraActionType cameraAction;
    @XmlElement(name = "CameraSeq", required = true)
    @XmlSchemaType(name = "string")
    protected CameraSequence cameraSeq;

    /**
     * ��ȡcameraOpType���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link CameraOperationType }
     *     
     */
    public CameraOperationType getCameraOpType() {
        return cameraOpType;
    }

    /**
     * ����cameraOpType���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link CameraOperationType }
     *     
     */
    public void setCameraOpType(CameraOperationType value) {
        this.cameraOpType = value;
    }

    /**
     * ��ȡcameraAction���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link CameraActionType }
     *     
     */
    public CameraActionType getCameraAction() {
        return cameraAction;
    }

    /**
     * ����cameraAction���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link CameraActionType }
     *     
     */
    public void setCameraAction(CameraActionType value) {
        this.cameraAction = value;
    }

    /**
     * ��ȡcameraSeq���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link CameraSequence }
     *     
     */
    public CameraSequence getCameraSeq() {
        return cameraSeq;
    }

    /**
     * ����cameraSeq���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link CameraSequence }
     *     
     */
    public void setCameraSeq(CameraSequence value) {
        this.cameraSeq = value;
    }

}
