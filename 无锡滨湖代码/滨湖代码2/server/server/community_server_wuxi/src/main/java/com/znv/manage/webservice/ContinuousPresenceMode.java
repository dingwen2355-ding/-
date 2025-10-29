
package com.znv.manage.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ContinuousPresenceMode�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * <p>
 * <pre>
 * &lt;simpleType name="ContinuousPresenceMode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CP_None"/>
 *     &lt;enumeration value="CP_1_1"/>
 *     &lt;enumeration value="CP_2_1"/>
 *     &lt;enumeration value="CP_2_2"/>
 *     &lt;enumeration value="CP_2_3"/>
 *     &lt;enumeration value="CP_2_5"/>
 *     &lt;enumeration value="CP_2_6"/>
 *     &lt;enumeration value="CP_2_7"/>
 *     &lt;enumeration value="CP_3_1"/>
 *     &lt;enumeration value="CP_3_2"/>
 *     &lt;enumeration value="CP_3_3"/>
 *     &lt;enumeration value="CP_3_4"/>
 *     &lt;enumeration value="CP_3_5"/>
 *     &lt;enumeration value="CP_3_6"/>
 *     &lt;enumeration value="CP_3_8"/>
 *     &lt;enumeration value="CP_4_1"/>
 *     &lt;enumeration value="CP_4_2"/>
 *     &lt;enumeration value="CP_4_3"/>
 *     &lt;enumeration value="CP_4_4"/>
 *     &lt;enumeration value="CP_4_5"/>
 *     &lt;enumeration value="CP_4_6"/>
 *     &lt;enumeration value="CP_4_7"/>
 *     &lt;enumeration value="CP_4_8"/>
 *     &lt;enumeration value="CP_5_1"/>
 *     &lt;enumeration value="CP_5_2"/>
 *     &lt;enumeration value="CP_5_3"/>
 *     &lt;enumeration value="CP_5_4"/>
 *     &lt;enumeration value="CP_5_6"/>
 *     &lt;enumeration value="CP_5_7"/>
 *     &lt;enumeration value="CP_6_1"/>
 *     &lt;enumeration value="CP_6_2"/>
 *     &lt;enumeration value="CP_6_3"/>
 *     &lt;enumeration value="CP_6_4"/>
 *     &lt;enumeration value="CP_6_5"/>
 *     &lt;enumeration value="CP_6_6"/>
 *     &lt;enumeration value="CP_6_7"/>
 *     &lt;enumeration value="CP_6_8"/>
 *     &lt;enumeration value="CP_7_1"/>
 *     &lt;enumeration value="CP_7_2"/>
 *     &lt;enumeration value="CP_7_3"/>
 *     &lt;enumeration value="CP_7_4"/>
 *     &lt;enumeration value="CP_7_5"/>
 *     &lt;enumeration value="CP_7_6"/>
 *     &lt;enumeration value="CP_8_1"/>
 *     &lt;enumeration value="CP_8_2"/>
 *     &lt;enumeration value="CP_8_3"/>
 *     &lt;enumeration value="CP_8_4"/>
 *     &lt;enumeration value="CP_8_5"/>
 *     &lt;enumeration value="CP_8_6"/>
 *     &lt;enumeration value="CP_9_1"/>
 *     &lt;enumeration value="CP_9_2"/>
 *     &lt;enumeration value="CP_10_1"/>
 *     &lt;enumeration value="CP_10_2"/>
 *     &lt;enumeration value="CP_10_3"/>
 *     &lt;enumeration value="CP_10_4"/>
 *     &lt;enumeration value="CP_10_5"/>
 *     &lt;enumeration value="CP_10_6"/>
 *     &lt;enumeration value="CP_10_7"/>
 *     &lt;enumeration value="CP_11_1"/>
 *     &lt;enumeration value="CP_12_1"/>
 *     &lt;enumeration value="CP_13_1"/>
 *     &lt;enumeration value="CP_13_2"/>
 *     &lt;enumeration value="CP_13_3"/>
 *     &lt;enumeration value="CP_13_4"/>
 *     &lt;enumeration value="CP_13_5"/>
 *     &lt;enumeration value="CP_13_6"/>
 *     &lt;enumeration value="CP_14_1"/>
 *     &lt;enumeration value="CP_15_1"/>
 *     &lt;enumeration value="CP_15_2"/>
 *     &lt;enumeration value="CP_16_1"/>
 *     &lt;enumeration value="CP_16_2"/>
 *     &lt;enumeration value="CP_17_1"/>
 *     &lt;enumeration value="CP_18_1"/>
 *     &lt;enumeration value="CP_18_2"/>
 *     &lt;enumeration value="CP_19_1"/>
 *     &lt;enumeration value="CP_20_1"/>
 *     &lt;enumeration value="CP_20_2"/>
 *     &lt;enumeration value="CP_21_1"/>
 *     &lt;enumeration value="CP_22_1"/>
 *     &lt;enumeration value="CP_23_1"/>
 *     &lt;enumeration value="CP_24_1"/>
 *     &lt;enumeration value="CP_24_2"/>
 *     &lt;enumeration value="CP_25_1"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ContinuousPresenceMode")
@XmlEnum
public enum ContinuousPresenceMode {

    @XmlEnumValue("CP_None")
    CP_NONE("CP_None"),
    CP_1_1("CP_1_1"),
    CP_2_1("CP_2_1"),
    CP_2_2("CP_2_2"),
    CP_2_3("CP_2_3"),
    CP_2_5("CP_2_5"),
    CP_2_6("CP_2_6"),
    CP_2_7("CP_2_7"),
    CP_3_1("CP_3_1"),
    CP_3_2("CP_3_2"),
    CP_3_3("CP_3_3"),
    CP_3_4("CP_3_4"),
    CP_3_5("CP_3_5"),
    CP_3_6("CP_3_6"),
    CP_3_8("CP_3_8"),
    CP_4_1("CP_4_1"),
    CP_4_2("CP_4_2"),
    CP_4_3("CP_4_3"),
    CP_4_4("CP_4_4"),
    CP_4_5("CP_4_5"),
    CP_4_6("CP_4_6"),
    CP_4_7("CP_4_7"),
    CP_4_8("CP_4_8"),
    CP_5_1("CP_5_1"),
    CP_5_2("CP_5_2"),
    CP_5_3("CP_5_3"),
    CP_5_4("CP_5_4"),
    CP_5_6("CP_5_6"),
    CP_5_7("CP_5_7"),
    CP_6_1("CP_6_1"),
    CP_6_2("CP_6_2"),
    CP_6_3("CP_6_3"),
    CP_6_4("CP_6_4"),
    CP_6_5("CP_6_5"),
    CP_6_6("CP_6_6"),
    CP_6_7("CP_6_7"),
    CP_6_8("CP_6_8"),
    CP_7_1("CP_7_1"),
    CP_7_2("CP_7_2"),
    CP_7_3("CP_7_3"),
    CP_7_4("CP_7_4"),
    CP_7_5("CP_7_5"),
    CP_7_6("CP_7_6"),
    CP_8_1("CP_8_1"),
    CP_8_2("CP_8_2"),
    CP_8_3("CP_8_3"),
    CP_8_4("CP_8_4"),
    CP_8_5("CP_8_5"),
    CP_8_6("CP_8_6"),
    CP_9_1("CP_9_1"),
    CP_9_2("CP_9_2"),
    CP_10_1("CP_10_1"),
    CP_10_2("CP_10_2"),
    CP_10_3("CP_10_3"),
    CP_10_4("CP_10_4"),
    CP_10_5("CP_10_5"),
    CP_10_6("CP_10_6"),
    CP_10_7("CP_10_7"),
    CP_11_1("CP_11_1"),
    CP_12_1("CP_12_1"),
    CP_13_1("CP_13_1"),
    CP_13_2("CP_13_2"),
    CP_13_3("CP_13_3"),
    CP_13_4("CP_13_4"),
    CP_13_5("CP_13_5"),
    CP_13_6("CP_13_6"),
    CP_14_1("CP_14_1"),
    CP_15_1("CP_15_1"),
    CP_15_2("CP_15_2"),
    CP_16_1("CP_16_1"),
    CP_16_2("CP_16_2"),
    CP_17_1("CP_17_1"),
    CP_18_1("CP_18_1"),
    CP_18_2("CP_18_2"),
    CP_19_1("CP_19_1"),
    CP_20_1("CP_20_1"),
    CP_20_2("CP_20_2"),
    CP_21_1("CP_21_1"),
    CP_22_1("CP_22_1"),
    CP_23_1("CP_23_1"),
    CP_24_1("CP_24_1"),
    CP_24_2("CP_24_2"),
    CP_25_1("CP_25_1");
    private final String value;

    ContinuousPresenceMode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ContinuousPresenceMode fromValue(String v) {
        for (ContinuousPresenceMode c: ContinuousPresenceMode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
