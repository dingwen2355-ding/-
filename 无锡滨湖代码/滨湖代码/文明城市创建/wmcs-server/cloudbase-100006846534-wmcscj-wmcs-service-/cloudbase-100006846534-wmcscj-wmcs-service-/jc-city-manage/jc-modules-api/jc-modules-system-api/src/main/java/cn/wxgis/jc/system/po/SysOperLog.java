package cn.wxgis.jc.system.po;

import cn.wxgis.jc.jpa.domain.comment.ColumnComment;
import cn.wxgis.jc.jpa.domain.comment.TableComment;
import cn.wxgis.jc.jpa.domain.po.BasePO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "sys_oper_log")
@TableComment("日志表")
public class SysOperLog extends BasePO {

    @Column(length = 50)
    @ColumnComment("模块标题")
    private String title;

    @Column(length = 50)
    @ColumnComment("操作人员")
    private String operName;

    @Column(length = 50)
    @ColumnComment("部门名称")
    private String deptName;

    @Column(length = 10)
    @ColumnComment("请求方法")
    private String requestMethod;

    @Column(length = 200)
    @ColumnComment("请求地址")
    private String operUrl;

    @Column(length = 100)
    @ColumnComment("方法名称")
    private String method;

    @Column(length = 2000)
    @ColumnComment("请求参数")
    private String operParam;

    @Column(length = 50)
    @ColumnComment("请求Ip")
    private String operIp;

    @Column(length = 200)
    @ColumnComment("请求地点")
    private String operLocation;

    @Column(length = 2000)
    @ColumnComment("返回结果")
    private String jsonResult;

    @Column(length = 2000)
    @ColumnComment("错误消息")
    private String errorMsg;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ColumnComment("操作时间")
    private Date operTime;

    @ColumnComment("消耗时间")
    private Long costTime;

    @Column(columnDefinition = "int DEFAULT 0")
    @ColumnComment("业务类型（0其它 1新增 2修改 3删除）")
    private Integer businessType;

    @Column(columnDefinition = "int DEFAULT 1")
    @ColumnComment("操作类别（0其它 1后台用户 2手机端用户）")
    private Integer operatorType;

    @Column(columnDefinition = "int DEFAULT 0")
    @ColumnComment("操作状态（0正常 1异常）")
    private Integer status;
}
