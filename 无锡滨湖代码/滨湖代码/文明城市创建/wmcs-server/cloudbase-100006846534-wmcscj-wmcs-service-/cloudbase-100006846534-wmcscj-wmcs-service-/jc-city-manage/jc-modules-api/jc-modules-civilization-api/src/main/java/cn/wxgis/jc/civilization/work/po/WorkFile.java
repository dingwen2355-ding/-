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
@Table(name = "work_file")
@TableComment("工单附件")
public class WorkFile extends BasePO {

    /** 工单id */
    @Column(length = 64)
    @ColumnComment("工单id")
    private String workId;

    /** 附件名 */
    @Column(length = 100)
    @ColumnComment("附件名")
    private String name;

    /** 提交时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ColumnComment("提交时间")
    private Date uploadTime;

    /** 提交用户 */
    @Column(length = 100)
    @ColumnComment("提交用户")
    private String uploadUser;

    /** 附件地址 */
    @Column(length = 500)
    @ColumnComment("附件地址")
    private String url;

}
