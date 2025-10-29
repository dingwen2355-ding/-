
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>PageParam complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="PageParam">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NumberPerPage" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CurrentPage" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PageParam", propOrder = {
    "numberPerPage",
    "currentPage"
})
public class PageParam {

    @XmlElement(name = "NumberPerPage")
    protected int numberPerPage;
    @XmlElement(name = "CurrentPage")
    protected int currentPage;

    /**
     * ��ȡnumberPerPage���Ե�ֵ��
     * 
     */
    public int getNumberPerPage() {
        return numberPerPage;
    }

    /**
     * ����numberPerPage���Ե�ֵ��
     * 
     */
    public void setNumberPerPage(int value) {
        this.numberPerPage = value;
    }

    /**
     * ��ȡcurrentPage���Ե�ֵ��
     * 
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * ����currentPage���Ե�ֵ��
     * 
     */
    public void setCurrentPage(int value) {
        this.currentPage = value;
    }

}
