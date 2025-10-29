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
@Table(name = "sys_role_menu")
@TableComment("角色菜单表")
public class SysRoleMenu {

    /**
     * 主键ID
     */
    @Id
    @Column(length = 64)
    @ColumnComment("主键ID")
    @TableId(value = "id",type= IdType.ASSIGN_UUID)
    private String id;

    @Column(length = 64)
    @ColumnComment("角色主键ID")
    private String roleId;

    @Column(length = 64)
    @ColumnComment("菜单主键ID")
    private String menuId;
}
