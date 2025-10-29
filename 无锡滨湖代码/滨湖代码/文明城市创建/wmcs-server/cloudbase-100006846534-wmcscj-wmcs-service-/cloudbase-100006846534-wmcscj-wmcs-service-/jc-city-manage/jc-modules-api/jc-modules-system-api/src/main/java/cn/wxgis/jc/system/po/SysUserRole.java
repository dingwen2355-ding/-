package cn.wxgis.jc.system.po;

import cn.wxgis.jc.jpa.domain.comment.ColumnComment;
import cn.wxgis.jc.jpa.domain.comment.TableComment;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "sys_user_role")
@TableComment("用户角色表")
public class SysUserRole {

    /**
     * 主键ID
     */
    @Id
    @Column(length = 64)
    @TableId(value = "id",type= IdType.ASSIGN_UUID)
    private String id;

    @Column(length = 64)
    @ColumnComment("用户")
    private String userId;

    @Column(length = 64)
    @ColumnComment("角色")
    private String roleId;
}
