package com.ruoyi.system.customer.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.system.customer.CmBaseEntity;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 *
 * @author ruoyi
 */
@Data
@TableName(value = "cm_app")
public class CmApp extends CmBaseEntity
{


    /**
     * 应用名称
     */
    private String appName;
    /**
     * 应用编号
     */
    private String appSerialNo;

    /**
     * 应用类型
     */
    private String appType;
    /**
     * 应用图标
     */
    private String appPic;

    /**
     * 所属模块
     */
    private String appModule;


    /**
     * 是否推荐: 0:是 1:否
     */
    private Integer recommendFlag;

    /**
     * 状态 --  0:上架 1:待审核 2:驳回 3:下架
     */
    private Integer status;

    /**
     *应用链接
     */
    private String appLink;

    /**
     * 应用截图
     */
    private String appScreenshot;

    /**
     * 显示顺序
     */
    private Integer sortBy;

    /**
     * 申请理由
     */
    private String applicationReason;

    /**
     * 部门
     */
    private String  dept;
    /**
     * 同意身份认证 id
     */
    private String casAppId;

    /**
     * 负责人姓名
     */
    private String principalName;

    /**
     * 负责人姓名
     */
    private String principalPhone;

    /**
     * 附件信息
     */
    @TableField(jdbcType = JdbcType.VARCHAR,  typeHandler = ListToStringHandler.class)
    private List<UploadInfo> upload;

    /**
     * 附件信息
     */
    @TableField(jdbcType = JdbcType.VARCHAR,  typeHandler = ListToStringHandler.class)
    private List<UploadInfo> moban;

    /**
     * 应用来源
     */
    private String  source;

    /**
     * 单位类型
     */
    private String unitType;

    /**
     * 这个字段没有实际作用，用来去多条数据中第一条
     */
    @TableField(exist = false)
    private String rowid;
    /**
     * 上架时间，关联表cm_app_examine中的update_time
     */
    @TableField(exist = false)
    private String shangjiatime;

    /**
     * 特色亮点
     */
    private String liangdian;

    /**
     * 亮点截图
     */
    private String liangdianpic;

    /**
     * 申请单位
     */
    private String danwei;
    /**
     * 单位类型
     */
    private String danweiType;

    /**
     * 申请量  数据库没这字段
     */
    @TableField(exist = false)
    private String shengqingliang;
    /**
     * 访问量，数据库没这字段
     */
    @TableField(exist = false)
    private String fangwenliang;

    /**
     * 是否置顶 1否 2是
     */
    private String zhiding;
    /**
     * 是否底座超市展示  1否 2是
     */
    private String show;
}
