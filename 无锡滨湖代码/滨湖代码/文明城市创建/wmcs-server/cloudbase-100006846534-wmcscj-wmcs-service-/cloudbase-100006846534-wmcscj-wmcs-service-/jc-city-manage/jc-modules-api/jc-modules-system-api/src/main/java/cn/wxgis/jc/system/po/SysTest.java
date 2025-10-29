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
@Table(name = "sys_test")
@TableComment("测试版")
public class SysTest extends BasePO {

    @Column(length = 50)
    @ColumnComment("名称")
    private String name;

}
