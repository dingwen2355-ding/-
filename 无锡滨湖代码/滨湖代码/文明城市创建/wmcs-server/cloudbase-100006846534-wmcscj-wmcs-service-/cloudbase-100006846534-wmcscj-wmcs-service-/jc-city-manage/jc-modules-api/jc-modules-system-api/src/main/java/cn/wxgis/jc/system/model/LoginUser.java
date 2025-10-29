package cn.wxgis.jc.system.model;

import cn.wxgis.jc.system.po.SysDept;
import cn.wxgis.jc.system.po.SysRole;
import cn.wxgis.jc.system.po.SysUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class LoginUser implements Serializable {

    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 用户名id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 部门id
     */
    private String deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间（秒）
     */
    private Long expireTime;

    /**
     * 登录IP地址
     */
    private String ipAddr;


    private Boolean isRememberMe;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 权限列表
     */
    private Set<String> userroles;

    /**
     * 用户信息
     */
    private SysUser user;
    /**
     * 部门信息
     */
    private SysDept dept;
    private String[] roles;
    private List<SysRole> roleList;
    private String[] roleNames;
    private Object wechatTicket;

    public LoginUser(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

}
