package cn.wxgis.jc.system.model;

import cn.wxgis.jc.system.po.SysRole;
import cn.wxgis.jc.system.po.SysUser;
import lombok.Data;

import java.util.List;

@Data
public class SysUserVO extends SysUser {

    /**
     * 网格长：true：是网格长；false：不是网格长
     */
    private Boolean gridManager;

    /**
     * 角色列表
     */
    private List<SysRole> roleList;

}
