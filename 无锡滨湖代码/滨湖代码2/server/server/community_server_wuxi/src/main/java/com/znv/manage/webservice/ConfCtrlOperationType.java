
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ConfCtrlOperationType�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="ConfCtrlOperationType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ConfCtrlOpType_Set"/>
 *     &lt;enumeration value="ConfCtrlOpType_Cancel"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ConfCtrlOperationType")
@XmlEnum
public enum ConfCtrlOperationType {

    @XmlEnumValue("ConfCtrlOpType_Set")
    CONF_CTRL_OP_TYPE_SET("ConfCtrlOpType_Set"),
    @XmlEnumValue("ConfCtrlOpType_Cancel")
    CONF_CTRL_OP_TYPE_CANCEL("ConfCtrlOpType_Cancel");
    private final String value;

    ConfCtrlOperationType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ConfCtrlOperationType fromValue(String v) {
        for (ConfCtrlOperationType c: ConfCtrlOperationType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
