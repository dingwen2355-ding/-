
package com.znv.manage.webservice;

import javax.xml.bind.annotation.*;


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
 *         &lt;element name="QuerySignInAttendeesResult" type="{http://smc.huawei.com/}SmcErr2" minOccurs="0"/>
 *         &lt;element name="signInAttendeeList" type="{http://smc.huawei.com/}ArrayOfSignInAttendee" minOccurs="0"/>
 *         &lt;element name="pageInfo" type="{http://smc.huawei.com/}PagesInfo" minOccurs="0"/>
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
    "querySignInAttendeesResult",
    "signInAttendeeList",
    "pageInfo"
})
@XmlRootElement(name = "QuerySignInAttendeesResponse")
public class QuerySignInAttendeesResponse {

    @XmlElement(name = "QuerySignInAttendeesResult")
    protected SmcErr2 querySignInAttendeesResult;
    protected ArrayOfSignInAttendee signInAttendeeList;
    protected PagesInfo pageInfo;

    /**
     * ��ȡquerySignInAttendeesResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link SmcErr2 }
     *     
     */
    public SmcErr2 getQuerySignInAttendeesResult() {
        return querySignInAttendeesResult;
    }

    /**
     * ����querySignInAttendeesResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link SmcErr2 }
     *     
     */
    public void setQuerySignInAttendeesResult(SmcErr2 value) {
        this.querySignInAttendeesResult = value;
    }

    /**
     * ��ȡsignInAttendeeList���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSignInAttendee }
     *     
     */
    public ArrayOfSignInAttendee getSignInAttendeeList() {
        return signInAttendeeList;
    }

    /**
     * ����signInAttendeeList���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSignInAttendee }
     *     
     */
    public void setSignInAttendeeList(ArrayOfSignInAttendee value) {
        this.signInAttendeeList = value;
    }

    /**
     * ��ȡpageInfo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link PagesInfo }
     *     
     */
    public PagesInfo getPageInfo() {
        return pageInfo;
    }

    /**
     * ����pageInfo���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link PagesInfo }
     *     
     */
    public void setPageInfo(PagesInfo value) {
        this.pageInfo = value;
    }

}
