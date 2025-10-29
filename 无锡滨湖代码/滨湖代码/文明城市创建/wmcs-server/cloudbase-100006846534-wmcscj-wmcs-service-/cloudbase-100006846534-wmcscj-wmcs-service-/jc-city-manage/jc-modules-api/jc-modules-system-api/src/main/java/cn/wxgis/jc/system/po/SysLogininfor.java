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
@Table(name = "sys_logininfor")
@TableComment("系统日志表")
public class SysLogininfor extends BasePO {

    @Column(length = 50)
    @ColumnComment("用户账号")
    private String userName;

    @Column(length = 50)
    @ColumnComment("登陆ip")
    private String ipaddr;

    @Column(length = 2000)
    @ColumnComment("错误消息")
    private String msg;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ColumnComment("访问时间")
    private Date accessTime;

    @Column(columnDefinition = "int DEFAULT 0")
    @ColumnComment("操作状态（0正常 1异常）")
    private String status;
}
