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
@Table(name = "wf_base_node")
@TableComment("流程节点表")
public class WfBaseNode extends BasePO {

    /** 流程id */
    @Column(length = 64)
    @ColumnComment("流程id")
    private String flowid;

    /** 流程名称 */
    @Column(length = 100)
    @ColumnComment("流程名称")
    private String flowname;

    /** 节点id */
    @Column(length = 64)
    @ColumnComment("节点id")
    private String nodeid;

    /** 节点名称 */
    @Column(length = 100)
    @ColumnComment("节点名称")
    private String nodename;

    /** 节点前置拦截方法 */
    @Column(length = 200)
    @ColumnComment("节点前置拦截方法")
    private String beforefunc;

    /** 节点后置拦截方法 */
    @Column(length = 100)
    @ColumnComment("节点后置拦截方法")
    private String afterfunc;

    /** 节点处理类型
     * 1：单一签核、
     * 2：多人单一签核、
     * 3：多人处理、
     * 4：多人顺序 */
    @ColumnComment("节点处理类型 1：单一签核、2：多人单一签核、3：多人处理、4：多人顺序")
    private Integer nodehandletype;

    /** 处理人ID */
    @Column(length = 64)
    @ColumnComment("处理人ID")
    private String handlerid;

    /** 自定义处理人方法 */
    @Column(length = 200)
    @ColumnComment("自定义处理人方法")
    private String customfunc;

    /** 操作权限
     * 1：提交
     * 2：退回
     * 3：拿回
     **/
    @ColumnComment("操作权限 1：提交 2：退回 3：拿回")
    private Integer operateauthor;

    /** 节点处置时限类型
     * 1：按日
     * 2：按时
     **/
    @ColumnComment("节点处置时限类型 1：按日 2：按时")
    private Integer timelimittype;

    /** 节点处置时限 */
    @Column(length = 50)
    @ColumnComment("节点处置时限")
    private String timelimit;

    /** 节点类型
     * 1：开始
     * 2：过程
     * 3：办结
     */
    @ColumnComment("节点类型  1：开始 2：过程 3：办结")
    private Integer nodetype;

    /** 可退回节点 多个字段用逗号分隔 */
    @Column(length = 200)
    @ColumnComment("可退回节点 多个字段用逗号分隔")
    private String returnnode;

    /** 编辑字段 多个字段用逗号分隔 */
    @Column(length = 2000)
    @ColumnComment("编辑字段 多个字段用逗号分隔")
    private String editfield;

    /** 只读字段 多个字段用逗号分隔 */
    @Column(length = 2000)
    @ColumnComment("只读字段 多个字段用逗号分隔")
    private String readfield;

    /** 隐藏字段 多个字段用逗号分隔 */
    @Column(length = 2000)
    @ColumnComment("隐藏字段 多个字段用逗号分隔")
    private String hidefield;

}
