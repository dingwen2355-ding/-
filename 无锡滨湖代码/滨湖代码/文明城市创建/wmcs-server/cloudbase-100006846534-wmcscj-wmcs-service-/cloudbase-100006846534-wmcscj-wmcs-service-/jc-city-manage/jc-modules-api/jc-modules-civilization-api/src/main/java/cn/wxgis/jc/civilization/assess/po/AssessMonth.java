package cn.wxgis.jc.civilization.assess.po;

import cn.wxgis.jc.jpa.domain.comment.TableComment;
import cn.wxgis.jc.jpa.domain.po.BasePO;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "assess_month")
@TableComment("月度评测")
public class AssessMonth extends BasePO {
}
