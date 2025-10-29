package cn.wxgis.jc.system.po;

import cn.wxgis.jc.jpa.domain.comment.TableComment;
import cn.wxgis.jc.jpa.domain.po.BasePO;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "sys_notice")
@TableComment("通知表")
public class SysNotice extends BasePO {
}
