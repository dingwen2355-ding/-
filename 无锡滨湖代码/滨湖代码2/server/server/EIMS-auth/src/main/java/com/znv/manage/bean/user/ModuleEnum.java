package com.znv.manage.bean.user;

public enum ModuleEnum {

    APPROVE("1001", "电子证审批"),
    ECARD("1002", "查看电子证"),
    HOMEPAGE("1", "首页"),
    POPULATIONDATAQUERY("2", "人口数据管理查询"),
    POPULATIONENTRY("3", "人口数据管理新增"),
    POPULATIONDATAMODIFY("4", "人口数据管理修改"),
    POPULATIONDATADEL("5", "人口数据管理删除"),
    USERADD("6", "用户新增"),
    USERUPDATE("7", "用户修改"),
    USERDELETE("8", "用户删除"),
    USERQUERY("9", "用户查询"),
    DEPARTMENTADD("10", "新增组织"),
    DEPARTMENTUPDATE("11", "修改组织"),
    DEPARTMENTDELETE("12", "删除组织"),
    DEPARTMENTQUERY("13", "组织查询"),
    ROLEADD("14", "新增角色"),
    ROLEUPDATE("15", "修改角色"),
    ROLEDELETE("16", "删除角色"),
    ROLEQUERY("17", "角色查看"),
    HOUSEQUERY("18", "房屋数据管理查询"),
    HOUSEADD("19", "房屋数据管理新增"),
    HOUSEDELETE("20", "房屋数据管理删除"),
    HOUSEUPDATE("21", "房屋数据管理修改"),
    DEVICESTATISTICS("22", "设备统计"),
    DEVICEADD("23", "设备新增"),
    DEVICEUPDATE("24", "设备修改"),
    DEVICEDELETE("25", "设备删除"),
    DEVICEQUERY("26", "设备查询"),
    DEVICEDOWNLOAD("27", "设备导出"),
    PRECINCTADD("28", "区域管理新增"),
    PRECINCTDELETE("29", "区域管理删除"),
    PRECINCTUPDATE("30", "区域管理修改"),
    PRECINCTQUERY("31", "区域管理查询"),
    GRIDADD("32", "网格管理新增"),
    GRIDDELETE("33", "网格管理删除"),
    GRIDUPDATE("34", "网格管理修改"),
    GRIDQUERY("35", "网格管理查询"),
    IMPORTANTCOMPANYADD("36", "重点单位新增"),
    IMPORTANTCOMPANYUPDATE("37", "重点单位修改"),
    IMPORTANTCOMPANYDELETE("38", "重点单位删除"),
    IMPORTANTCOMPANYQUERY("39", "重点单位查询"),
    VILLAGECOMMITTEETELEPHONEADD("40", "村居通讯录新增"),
    VILLAGECOMMITTEETELEPHONEDELETE("41", "村居通讯录删除"),
    VILLAGECOMMITTEETELEPHONEUPDATE("42", "村居通讯录修改"),
    VILLAGECOMMITTEETELEPHONEQUERY("43", "村居通讯录查询"),
    NURSINGHOMEADD("44", "敬老机构新增"),
    NURSINGHOMEDELETE("45", "敬老机构删除"),
    NURSINGHOMEUPDATE("46", "敬老机构修改"),
    NURSINGHOMEQUERY("47", "敬老机构查询"),
    COOPERATIVEHOSPITALADD("48", "合作医院新增"),
    COOPERATIVEHOSPITALDELETE("49", "合作医院删除"),
    COOPERATIVEHOSPITALUPDATE("50", "合作医院修改"),
    COOPERATIVEHOSPITALQUERY("51", "合作医院查询"),
    COMMUNITYDOCTORADD("52", "社区医生新增"),
    COMMUNITYDOCTORDELETE("53", "社区医生删除"),
    COMMUNITYDOCTORUPDATE("54", "社区医生修改"),
    COMMUNITYDOCTORQUERY("55", "社区医生查询"),
    GARBAGESORTINGPOINTADD("56", "垃圾分类点新增"),
    GARBAGESORTINGPOINTDELETE("57", "垃圾分类点删除"),
    GARBAGESORTINGPOINTUPDATE("58", "垃圾分类点修改"),
    GARBAGESORTINGPOINTQUERY("59", "垃圾分类点查询"),
    RIVERADD("60", "河道新增"),
    RIVERDELETE("61", "河道删除"),
    RIVERUPDATE("62", "河道修改"),
    RIVERQUERY("63", "河道查询"),
    POPULATIONADD("64", "实有人口新增"),
    POPULATIONDELETE("65", "实有人口删除"),
    POPULATIONUPDATE("66", "实有人口修改"),
    POPULATIONQUERY("67", "实有人口查询"),
    CONFIGURATIONMANAGEMENTADD("68", "配置管理新增"),
    CONFIGURATIONMANAGEMENTDELETE("69", "配置管理删除"),
    CONFIGURATIONMANAGEMENTUPDATE("70", "配置管理修改"),
    CONFIGURATIONMANAGEMENTQUERY("71", "配置管理查询"),
    LOGINLOGQUERY("72", "登录日志查询"),
    OPERATELOGQUERY("73", "操作日志查询"),
    MODULEADD("74", "模块新增"),
    MODULEDELETE("75", "模块删除"),
    MODULEUPDATE("76", "模块修改"),
    MODULEQUERY("77", "模块查询"),
    THIRDDEVICEADD("78", "第三方设备新增"),
    THIRDDEVICEDELETE("79", "第三方设备删除"),
    THIRDDEVICEUPDATE("80", "第三方设备修改"),
    THIRDDEVICEQUERY("81", "第三方设备查询");

    private String id;
    private String name;

    private ModuleEnum(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
