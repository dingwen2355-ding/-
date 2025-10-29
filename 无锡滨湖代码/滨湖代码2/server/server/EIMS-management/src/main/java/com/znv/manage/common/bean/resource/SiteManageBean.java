package com.znv.manage.common.bean.resource;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author yangbo
 * @email
 * @date 2022/11/9
 */
@Data
public class SiteManageBean {

    private Integer id;
    @JSONField(name = "避难场所名称")
    private String name;
    @JSONField(name = "类型")
    private String type;
    @JSONField(name = "类别")
    private String category;

    private Integer roomCount;
    @JSONField(name = "应急职责")
    private String responsibility;
    @JSONField(name = "管理人员")
    private String person;
    @JSONField(name = "管理员手机")
    private String phone;
    @JSONField(name = "所属社区")
    private String community;
    @JSONField(name = "地址")
    private String address;
    @JSONField(name = "面积（m2）")
    private String acreage;
    @JSONField(name = "状态")
    private String state;
    @JSONField(name = "最大容纳人数")
    private String maxGalleryful;
    private String streetName;
    private String photo;
    @JSONField(name = "经度")
    private String gpsx;
    @JSONField(name = "纬度")
    private String gpsy;
    private String telephone;
    private String alreadyGalleryful;
    private String precinctTreeId;
    private String deviceId;
    private String deviceStatus;
}
