import IP from "../ipconfig";
const api = {
  dict: {
    list: IP.server_system + "/system/dict/data/listDealDict",
    all: IP.server_system + "/system/dict/data/getAllDict",
    getAreaDictByIds: IP.server_system + "/system/city/getAreaDictByIds?ids="
  },
  cityLevel: {
    tree: IP.server_system + "/system/city/getAreaByPid"
  },
  file: {
    upload: IP.server_fileServe + "/sys/file/upload/url",
    download: IP.server_filePreview,
    preview: IP.server_filePreview + "/preview/onlinePreview?url="
  },
  region: {
    tree: IP.server_system + "/sys/region/tree",
    activation: IP.server_statistics + '/activation/statistics/byRegionTime',
    noHuoYueGridPeopleList: IP.server_statistics + '/activation/statistics/noHuoYueGridPeopleList',
  },
  index: {
    u_getEventCount: IP.server_statistics + 'index/statistics/getEventCount',// 顶部的四个数量
    u_getRegionXunCount: IP.server_statistics + 'index/statistics/getRegionXunCount',// 首页巡查走访统计
    u_getRegionEventTypeCount: IP.server_statistics + 'index/statistics/getRegionEventTypeCount',// 首页事件类别统计
    u_getRegionBaseDataCount: IP.server_statistics + 'index/statistics/getRegionBaseDataCount',// 首页基础数据统计
    u_getRegionEventLiuCount: IP.server_statistics + 'index/statistics/getRegionEventLiuCount',// 首页事件流转统计
    // echarts图表
    u_totalForPeopleType: IP.server_statistics + 'firstPage/totalForPeopleType',// 实有人口
    u_totalForHouseType: IP.server_statistics + 'firstPage/totalForHouseType',// 实有房屋
    u_totalForSmallPlaceType: IP.server_statistics + 'firstPage/totalForSmallPlaceType',// 实有房屋
    u_totalForOrangeType: IP.server_statistics + 'firstPage/totalForOrangeType',// 单位
    u_getRegionGridTypeCount: IP.server_statistics + 'index/statistics/getRegionGridTypeCount',// 基础数据看板
    u_getKeyPeopleTypeCount: IP.server_statistics + 'index/statistics/getKeyPeopleTypeCount',// 重点人群
    u_getRegionXunTypeCount: IP.server_statistics + 'index/statistics/getRegionXunTypeCount',// 巡查走访统计

    
  }
};
export default api;
