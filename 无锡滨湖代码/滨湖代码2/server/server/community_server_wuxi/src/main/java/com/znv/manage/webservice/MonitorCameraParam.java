
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>MonitorCameraParam complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="MonitorCameraParam">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IsCamera" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="CameraType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PBStarttime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PBEndTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IsH265" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IsViewer" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ViewerType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MonitorCameraParam", propOrder = {
    "isCamera",
    "cameraType",
    "pbStarttime",
    "pbEndTime",
    "isH265",
    "isViewer",
    "viewerType"
})
public class MonitorCameraParam {

    @XmlElement(name = "IsCamera")
    protected boolean isCamera;
    @XmlElement(name = "CameraType")
    protected String cameraType;
    @XmlElement(name = "PBStarttime")
    protected String pbStarttime;
    @XmlElement(name = "PBEndTime")
    protected String pbEndTime;
    @XmlElement(name = "IsH265")
    protected boolean isH265;
    @XmlElement(name = "IsViewer")
    protected boolean isViewer;
    @XmlElement(name = "ViewerType")
    protected String viewerType;

    /**
     * ��ȡisCamera���Ե�ֵ��
     * 
     */
    public boolean isIsCamera() {
        return isCamera;
    }

    /**
     * ����isCamera���Ե�ֵ��
     * 
     */
    public void setIsCamera(boolean value) {
        this.isCamera = value;
    }

    /**
     * ��ȡcameraType���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCameraType() {
        return cameraType;
    }

    /**
     * ����cameraType���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCameraType(String value) {
        this.cameraType = value;
    }

    /**
     * ��ȡpbStarttime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPBStarttime() {
        return pbStarttime;
    }

    /**
     * ����pbStarttime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPBStarttime(String value) {
        this.pbStarttime = value;
    }

    /**
     * ��ȡpbEndTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPBEndTime() {
        return pbEndTime;
    }

    /**
     * ����pbEndTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPBEndTime(String value) {
        this.pbEndTime = value;
    }

    /**
     * ��ȡisH265���Ե�ֵ��
     * 
     */
    public boolean isIsH265() {
        return isH265;
    }

    /**
     * ����isH265���Ե�ֵ��
     * 
     */
    public void setIsH265(boolean value) {
        this.isH265 = value;
    }

    /**
     * ��ȡisViewer���Ե�ֵ��
     * 
     */
    public boolean isIsViewer() {
        return isViewer;
    }

    /**
     * ����isViewer���Ե�ֵ��
     * 
     */
    public void setIsViewer(boolean value) {
        this.isViewer = value;
    }

    /**
     * ��ȡviewerType���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getViewerType() {
        return viewerType;
    }

    /**
     * ����viewerType���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViewerType(String value) {
        this.viewerType = value;
    }

}
