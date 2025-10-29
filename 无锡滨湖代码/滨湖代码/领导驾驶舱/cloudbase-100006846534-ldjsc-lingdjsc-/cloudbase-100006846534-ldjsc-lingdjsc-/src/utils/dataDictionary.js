// 字典转换工具
export const dictTransformTool = (data = {}, dictName = "", eliminateKey = ["geom", "gid", "wybs"]) => {
    if (!Dictionary[dictName]) return;
    let attrData = [];
    for (const key in data) {
        if (Object.hasOwnProperty.call(data, key)) {
            if (eliminateKey.includes(key)) continue;
            let item = Dictionary[dictName].find((o) => o.key == key);
            if (item) {
                let value = !item.enum ? data[key] : item.enum[data[key]];
                attrData.push({
                    name: item.name,
                    value: value,
                    index: item.index,
                });
            }
        }
    }
    attrData.sort(function (a, b) {
        return a.index - b.index;
    });
    return attrData;
}

const Dictionary = {
    // 房屋普查数据字典
    HousingCensus: [
        { key: "bh", name: "编号", index: 1 },
        { key: "province", name: "省行政编码", index: 2 },
        { key: "city", name: "市行政编码", index: 3 },
        { key: "district", name: "区县行政编码", index: 4 },
        { key: "town", name: "镇街行政编码", index: 5 },
        { key: "village", name: "村居行政编码", index: 6 },
        { key: "fwbh", name: "房屋编号", index: 7 },
        { key: "fwlb", name: "房屋类别", index: 8 },
        { key: "house_type", name: "房屋类型", index: 9 },
        { key: "xcdcqk", name: "现场调查情况", index: 10 },
        { key: "bxdcyy", name: "不需调查原因", index: 11 },
        { key: "xbcdcyy", name: "需补充调查原因", index: 12 },
        { key: "xbcdcqtyy", name: "需补充调查其它原因", index: 13 },
        { key: "address", name: "地址", index: 14 },
        {
            key: "fwlx",
            name: "住宅类型",
            index: 15,
            enum: { 1: "独立住宅", 2: "集合住宅", 4: "住宅辅助用房" },
        },
        { key: "hzxm", name: "户主姓名", index: 16 },
        { key: "sfz", name: "身份证", index: 17 },
        { key: "czrk", name: "常住人口数", index: 18 },
        { key: "cs", name: "层数", index: 19 },
        { key: "dcmj", name: "调查面积", index: 20 },
        { key: "gd", name: "建筑高度", index: 21 },
        { key: "build_time", name: "建成时间", index: 22 },
        { key: "jglx", name: "结构类型", index: 23 },
        { key: "qtjglx", name: "其他结构类型", index: 24 },
        { key: "jzfs", name: "建设方式", index: 25 },
        { key: "sfkzjg", name: "是否进行过抗震加固", index: 26 },
        { key: "jgsj", name: "加固时间", index: 27 },
        { key: "kzgzcs", name: "抗震构造措施", index: 28 },
        { key: "fwyt", name: "房屋用途", index: 29 },
        { key: "qtfwyt", name: "其他房屋用途", index: 30 },
        { key: "azhs", name: "安置户数", index: 31 },
        { key: "sfszcg", name: "是否进行过改造", index: 32 },
        { key: "gzsj", name: "改造时间", index: 33 },
        { key: "gznr", name: "改造内容", index: 34 },
        { key: "sub_jglx", name: "二级结构类型", index: 35 },
        {
            key: "fwsgfs",
            name: "是否由专业建筑工匠或有资质的施工队伍施工",
            index: 36,
        },
        { key: "fwsjfs", name: "是否采用专业设计或标准图集", index: 37 },
        { key: "ywlfbxqx", name: "有无肉眼可见明显裂缝、变形、倾斜", index: 38 },
        { key: "lfbxqxzp", name: "变形损伤照片", index: 39 },
        { key: "dscs", name: "地上层数（集合住宅用）", index: 40 },
        { key: "dxcs", name: "地下层数（集合住宅用）", index: 41 },
        { key: "jzmc", name: "建筑名称", index: 42 },
        { key: "sfjgaqjd", name: "是否经过安全鉴定", index: 43 },
        { key: "aqjdnf", name: "鉴定时间", index: 44 },
        { key: "aqjdjl", name: "鉴定结论等级", index: 45 },
        { key: "jdsfaq", name: "安全性鉴定（是否安全）", index: 46 },
        { key: "sfzxgkj", name: "是否自行改扩建", index: 47 },
        { key: "gkjsj", name: "改扩建时间", index: 48 },
        { key: "photo", name: "照片ID", index: 49 },
        { key: "bz", name: "备注", index: 50 },
        { key: "dcr", name: "调查人", index: 51 },
        { key: "lxdh", name: "联系电话", index: 52 },
        { key: "dcsj", name: "调查人组织", index: 53 },
        { key: "ywlfbxqx", name: "调查时间", index: 54 },
        {
            key: "status",
            name: "调查状态",
            index: 55,
            enum: { 1: "调查中", 2: "已调查" },
        },
    ],
    // 人口查询
    Population: [
        { key: "building", name: "楼栋", index: 0 },
        { key: "unitnum", name: "单元", index: 1 },
        { key: "floornum", name: "楼层", index: 2 },
        { key: "housenum", name: "房间号", index: 3 },
        { key: "username", name: "姓名", index: 5 },
        { key: "sex", name: "性别", index: 6 },
        { key: "age", name: "年龄", index: 7 },
        { key: "ethnic", name: "民族", index: 8 },
        { key: "education", name: "文化水平", index: 9 },
        { key: "coordinate", name: "坐标", index: 10 },
        { key: "objectid", name: " 图层id", index: 11 },
        { key: "bldstru", name: " 图元id", index: 12 },
    ]
}