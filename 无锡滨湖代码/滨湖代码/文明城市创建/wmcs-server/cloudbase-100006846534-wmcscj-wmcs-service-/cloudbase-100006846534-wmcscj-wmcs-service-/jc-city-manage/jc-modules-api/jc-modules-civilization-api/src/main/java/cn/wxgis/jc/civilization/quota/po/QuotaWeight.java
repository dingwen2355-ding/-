package cn.wxgis.jc.civilization.quota.po;

import cn.wxgis.jc.jpa.domain.comment.ColumnComment;
import cn.wxgis.jc.jpa.domain.comment.TableComment;
import cn.wxgis.jc.jpa.domain.po.BasePO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "quota_weight")
@TableComment("权重管理")
public class QuotaWeight extends BasePO {

    /** 权重名称 */
    @Column(length = 100)
    @ColumnComment("权重名称")
    private String name;

    /** 权重值 **/
    @ColumnComment("权重值")
    private Integer val;

    /** 状态：0启用；1停用 */
    @Column(columnDefinition = "int DEFAULT 0")
    @ColumnComment("状态：0启用；1停用")
    private Integer status;

    /** 权重描述 **/
    @Column(length = 500)
    @ColumnComment("权重描述")
    private String description;
}
