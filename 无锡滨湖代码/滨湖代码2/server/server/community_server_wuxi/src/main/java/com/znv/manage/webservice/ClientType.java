
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ClientType�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="ClientType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="GUI"/>
 *     &lt;enumeration value="WEB"/>
 *     &lt;enumeration value="API"/>
 *     &lt;enumeration value="OA"/>
 *     &lt;enumeration value="INVALID"/>
 *     &lt;enumeration value="VSD"/>
 *     &lt;enumeration value="VDM"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ClientType")
@XmlEnum
public enum ClientType {

    GUI,
    WEB,
    API,
    OA,
    INVALID,
    VSD,
    VDM;

    public String value() {
        return name();
    }

    public static ClientType fromValue(String v) {
        return valueOf(v);
    }

}
