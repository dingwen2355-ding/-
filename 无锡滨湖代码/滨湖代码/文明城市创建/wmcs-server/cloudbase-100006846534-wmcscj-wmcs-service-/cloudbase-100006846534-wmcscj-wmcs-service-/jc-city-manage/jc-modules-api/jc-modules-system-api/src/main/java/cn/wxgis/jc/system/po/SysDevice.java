package cn.wxgis.jc.system.po;

import cn.wxgis.jc.jpa.domain.comment.ColumnComment;
import cn.wxgis.jc.jpa.domain.comment.TableComment;
import cn.wxgis.jc.jpa.domain.po.BasePO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "sys_device")
@TableComment("设备管理")
public class SysDevice extends BasePO {

    @Column(length = 100)
    @ColumnComment("设备名称")
    private String name;

    @Column(length = 100)
    @ColumnComment("设备编号")
    private String code;

    @Column(length = 64)
    @ColumnComment("绑定用户")
    private String userId;

    @Column(columnDefinition = "int DEFAULT 1")
    @ColumnComment("状态")
    private Integer status;

}
