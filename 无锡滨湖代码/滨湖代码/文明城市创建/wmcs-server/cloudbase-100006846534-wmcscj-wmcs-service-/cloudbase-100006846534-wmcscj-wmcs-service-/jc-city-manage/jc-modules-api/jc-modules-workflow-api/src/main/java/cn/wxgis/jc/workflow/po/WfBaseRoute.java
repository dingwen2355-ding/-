package cn.wxgis.jc.workflow.po;

import cn.wxgis.jc.jpa.domain.comment.ColumnComment;
import cn.wxgis.jc.jpa.domain.comment.TableComment;
import cn.wxgis.jc.jpa.domain.po.BasePO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "wf_base_route")
@TableComment("流程节点表")
public class WfBaseRoute extends BasePO {

    /** 路由id */
    @Column(length = 64)
    @ColumnComment("路由id")
    private String routeid;

    /** 路由名称 */
    @Column(length = 100)
    @ColumnComment("路由名称")
    private String routename;

    /** 流程id */
    @Column(length = 64)
    @ColumnComment("流程id")
    private String flowid;

    /** 流程名称 */
    @Column(length = 100)
    @ColumnComment("流程名称")
    private String flowname;

    /** 开始节点ID */
    @Column(length = 100)
    @ColumnComment("开始节点ID")
    private String startnodeid;

    /** 开始节点名称 */
    @Column(length = 100)
    @ColumnComment("开始节点名称")
    private String startnodename;

    /** 开始节点自定义名称 */
    @Column(length = 200)
    @ColumnComment("开始节点自定义名称")
    private String startnodecustom;

    /** 结束节点ID */
    @Column(length = 100)
    @ColumnComment("结束节点ID")
    private String endnodeid;

    /** 结束节点名称 */
    @Column(length = 100)
    @ColumnComment("结束节点名称")
    private String endnodename;

    /** 结束节点自定义名称 */
    @ColumnComment("结束节点自定义名称")
    private String endnodecustom;

    /** 路由类型
     * 1：提交
     * 2：退回
     */
    @ColumnComment("路由类型 1：提交 2：退回")
    private Integer routetype;
}
