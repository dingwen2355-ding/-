package cn.wxgis.jc.jpa.domain.po;

import cn.wxgis.jc.jpa.domain.comment.ColumnComment;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Data
@MappedSuperclass
public class BasePO {

    /**
     * 主键ID
     */
    @Id
    @Column(length = 64)
    @ColumnComment("主键ID")
    @TableId(value = "id",type= IdType.ASSIGN_UUID)
    private String id;

    /**
     * 创建时间
     */
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'")
    private Date createTime;

    /**
     * 创建人员id
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @Column(length = 64)
    @ColumnComment("创建人员id")
    private String createBy;

    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ColumnComment("修改时间")
    private Date updateTime;

    /**
     * 修改人员id
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    @Column(length = 64)
    @ColumnComment("修改人员id")
    private String updateBy;

    /**
     * 逻辑删除；0-正常，1-已删除
     */
    @Column(columnDefinition = "int DEFAULT 0")
    @ColumnComment("逻辑删除；0-正常，1-已删除")
    private Integer delFlag;

    /**
     * 备注
     */
    @Column(length = 200)
    @ColumnComment("备注")
    private String remark;
}
