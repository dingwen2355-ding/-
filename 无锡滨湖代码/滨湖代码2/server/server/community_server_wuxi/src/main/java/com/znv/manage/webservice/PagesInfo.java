
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>PagesInfo complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="PagesInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TotalPages" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CurrentPage" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TotalRecords" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PagesInfo", propOrder = {
    "totalPages",
    "currentPage",
    "totalRecords"
})
public class PagesInfo {

    @XmlElement(name = "TotalPages")
    protected int totalPages;
    @XmlElement(name = "CurrentPage")
    protected int currentPage;
    @XmlElement(name = "TotalRecords")
    protected int totalRecords;

    /**
     * ��ȡtotalPages���Ե�ֵ��
     * 
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * ����totalPages���Ե�ֵ��
     * 
     */
    public void setTotalPages(int value) {
        this.totalPages = value;
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

    /**
     * ��ȡtotalRecords���Ե�ֵ��
     * 
     */
    public int getTotalRecords() {
        return totalRecords;
    }

    /**
     * ����totalRecords���Ե�ֵ��
     * 
     */
    public void setTotalRecords(int value) {
        this.totalRecords = value;
    }

}
