package cn.wxgis.jc.workflow.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 事件状态
 */
@Getter
@AllArgsConstructor
public enum WfStatusEnum {

    /*** Start -> Node1 (上报)  ***/
    REPORT(10,"Start"),
    /*** Node1 -> Node2 (区文明办分派)  ***/
    GIVE(20,"Node1"),
    /*** Node2 -> Node3 (街镇分拨)  ***/
    ALLOCATE(30,"Node2"),
    /*** Node3 -> Node4 (社区整改)  ***/
    /*** Node2 -> Node4 (职能部门受理)  ***/
    HANDLE(40,"Node3"),
    /*** Node4 -> End (区文明办核查办结)  ***/
    FINISH(50,"Node4"),
    /*** End (区文明办办结)  ***/
    END(60,"End");

    private Integer code;
    private String value;
}
