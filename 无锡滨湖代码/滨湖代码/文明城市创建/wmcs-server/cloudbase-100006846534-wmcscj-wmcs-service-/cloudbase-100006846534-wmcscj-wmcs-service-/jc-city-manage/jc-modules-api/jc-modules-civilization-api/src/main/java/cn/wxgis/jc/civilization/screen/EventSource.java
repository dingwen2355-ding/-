package cn.wxgis.jc.civilization.screen;

import cn.wxgis.jc.jpa.domain.comment.ColumnComment;
import cn.wxgis.jc.jpa.domain.comment.TableComment;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "screen_event_source")
@TableComment("事件来源")
public class EventSource {

    @Id
    @Column(length = 64)
    @ColumnComment("主键ID")
    private String id;

    @Column(columnDefinition = "int DEFAULT 0")
    @ColumnComment("日常上报")
    private Integer report;

    @Column(columnDefinition = "int DEFAULT 0")
    @ColumnComment("AI智能生成")
    private Integer ai;

    @Column(columnDefinition = "int DEFAULT 0")
    @ColumnComment("专班巡查")
    private Integer special;

    @Column(columnDefinition = "int DEFAULT 0")
    @ColumnComment("市级下发")
    private Integer city;
}
