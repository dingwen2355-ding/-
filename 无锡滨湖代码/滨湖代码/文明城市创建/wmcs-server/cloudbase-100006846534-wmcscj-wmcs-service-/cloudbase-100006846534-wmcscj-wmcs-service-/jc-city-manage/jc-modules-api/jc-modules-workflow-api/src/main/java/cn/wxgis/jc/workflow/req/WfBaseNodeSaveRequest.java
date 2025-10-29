package cn.wxgis.jc.workflow.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "WfBaseNodeSaveRequest", description = "工作流程节点保存 [传参]")
@Data
public class WfBaseNodeSaveRequest {

    /** id */
    @ApiModelProperty(value = "id")
    private String id;

    /** 流程id */
    @ApiModelProperty(value = "流程id")
    private String flowid;

    /** 流程名称 */
    @ApiModelProperty(value = "流程名称")
    private String flowname;

    /** 节点名称 */
    @ApiModelProperty(value = "节点名称")
    private String nodeid;

    /** 节点名称 */
    @ApiModelProperty(value = "节点名称")
    private String nodename;

    /** 节点前置拦截方法 */
    @ApiModelProperty(value = "节点前置拦截方法")
    private String beforefunc;

    /** 节点后置拦截方法 */
    @ApiModelProperty(value = "节点后置拦截方法")
    private String afterfunc;

    /** 节点处理类型
     * 1：单一签核、
     * 2：多人单一签核、
     * 3：多人处理、
     * 4：多人顺序 */
    @ApiModelProperty(value = "节点处理类型 1：单一签核、2：多人单一签核、3：多人处理、4：多人顺序")
    private Integer nodehandletype;

    /** 处理人ID */
    @ApiModelProperty(value = "处理人ID")
    private String handlerid;

    /** 自定义处理人方法 */
    @ApiModelProperty(value = "自定义处理人方法")
    private String customfunc;

    /** 操作权限
     * 1：提交
     * 2：退回
     * 3：拿回
     **/
    @ApiModelProperty(value = "操作权限 1：提交 2：退回 3：拿回")
    private Integer operateauthor;

    /** 节点处置时限类型
     * 1：按日
     * 2：按时
     **/
    @ApiModelProperty(value = "节点处置时限类型 1：按日 2：按时")
    private Integer timelimittype;

    /** 节点处置时限 */
    @ApiModelProperty(value = "节点处置时限")
    private String timelimit;

    /** 节点类型
     * 1：开始
     * 2：过程
     * 3：办结
     */
    @ApiModelProperty(value = "节点类型  1：开始 2：过程 3：办结")
    private Integer nodetype;

    /** 可退回节点 多个字段用逗号分隔 */
    @ApiModelProperty(value = "可退回节点 多个字段用逗号分隔")
    private String returnnode;

    /** 编辑字段 多个字段用逗号分隔 */
    @ApiModelProperty(value = "编辑字段 多个字段用逗号分隔")
    private String editfield;

    /** 只读字段 多个字段用逗号分隔 */
    @ApiModelProperty(value = "只读字段 多个字段用逗号分隔")
    private String readfield;

    /** 隐藏字段 多个字段用逗号分隔 */
    @ApiModelProperty(value = "隐藏字段 多个字段用逗号分隔")
    private String hidefield;
}
