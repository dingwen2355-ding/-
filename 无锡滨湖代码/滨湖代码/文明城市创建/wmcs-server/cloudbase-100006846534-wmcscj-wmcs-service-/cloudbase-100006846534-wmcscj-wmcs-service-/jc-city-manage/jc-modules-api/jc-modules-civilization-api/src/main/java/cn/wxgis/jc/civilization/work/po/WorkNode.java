package cn.wxgis.jc.civilization.work.po;

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
@Table(name = "work_node")
@TableComment("工单处置节点")
public class WorkNode extends BasePO {

    /** 工单id */
    @Column(length = 64)
    @ColumnComment("工单id")
    private String workId;

    /** 受理人 */
    @Column(length = 64)
    @ColumnComment("受理人")
    private String handleUser;

    /** 受理单位 */
    @Column(length = 64)
    @ColumnComment("受理单位")
    private String handleDept;

    /** 受理时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ColumnComment("受理时间")
    private Date handletime;

    /** 受理意见 */
    @Column(length = 500)
    @ColumnComment("受理意见")
    private String handleResult;

    /** 问题描述 */
    @Column(length = 2000)
    @ColumnComment("问题描述")
    private String info;

}
