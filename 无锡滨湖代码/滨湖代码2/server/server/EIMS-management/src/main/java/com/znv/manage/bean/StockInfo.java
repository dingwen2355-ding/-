package com.znv.manage.bean;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class StockInfo {

    private String id;

    @NotBlank(message="不能为空")
    //仓库名称
    private String stockName;

    //所属地市
    private Integer city;

    @NotNull(message="不能为空")
    private Integer regionId;

    //所在辖区
    private Integer region;

    private String helpTypeId;
    //救援类型
    private String helpType;

    @NotNull(message="不能为空")
    private Integer beManagedTypeId;

    //管理类别
    private Integer beManagedType;

    private Integer storageLevelId;
    //管理级别
    private Integer storageLevel;

    private Integer storageTypeId;
    //存储类别
    private Integer storageType;

    private Integer belongUnitId;
    //管理单位
    private Integer belongUnit;

    //地址
    private String address;

    //经度
    private String longitude;

    //纬度
    private String latitude;

    //负责人
    private String manager;

    //负责人联系方式
    private String managerPhone;

    //是否在用（Y/N）
    private String isUseAble;

    //创建人用户等级
    private Integer createUserLevel;

    //创建人(登录账户)
    private String createUserAccount;

    private String industryId;
    //所属行业
    private String industry;

    //主要负责人
    private String mainPeople;

    //值班电话
    private String dutyPhone;

    //单位信息
    private String unitInfo;

    //职工人数
    private String memberNumber;
    //职工人数
    private String stockType;

    private String introduction;

    private String stockOrder;

}
