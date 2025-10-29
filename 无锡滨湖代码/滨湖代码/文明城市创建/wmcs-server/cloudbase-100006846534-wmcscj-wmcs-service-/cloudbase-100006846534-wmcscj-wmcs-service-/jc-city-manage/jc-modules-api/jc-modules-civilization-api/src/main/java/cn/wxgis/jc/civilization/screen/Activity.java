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
@Table(name = "screen_activity")
@TableComment("大屏活动数据")
public class Activity {

    @Id
    @Column(length = 64)
    @ColumnComment("主键ID")
    private String id;

    @Column(length = 100)
    @ColumnComment("区划名称")
    private String regionName;

    @Column(columnDefinition = "int DEFAULT 0")
    @ColumnComment("活动场次")
    private Integer count;

}
