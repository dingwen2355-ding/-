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
@Table(name = "work_time_config")
@TableComment("时间配置")
public class WorkTimeConfig extends BasePO {

    /** 配置名称 */
    @Column(length = 100)
    @ColumnComment("配置名称")
    private String name;

    /** 所属年份 */
    @Column(length = 50)
    @ColumnComment("所属年份：默认为当年")
    private String year;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ColumnComment("开始时间")
    private String startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ColumnComment("结束时间")
    private String finishTime;

}
