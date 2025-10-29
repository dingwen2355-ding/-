package cn.wxgis.jc.civilization.data.po;

import cn.wxgis.jc.jpa.domain.comment.ColumnComment;
import cn.wxgis.jc.jpa.domain.comment.TableComment;
import cn.wxgis.jc.jpa.domain.po.BasePO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "data_knowledge")
@TableComment("知识库")
public class DataKnowledge extends BasePO {

    /** 名称 */
    @Column(length = 100)
    @ColumnComment("名称")
    private String name;

    @Column(length = 2000)
    @ColumnComment("内容")
    private String content;

    /** 附件 */
    @Column(length = 4000)
    @ColumnComment("附件")
    private String attachment;
}
