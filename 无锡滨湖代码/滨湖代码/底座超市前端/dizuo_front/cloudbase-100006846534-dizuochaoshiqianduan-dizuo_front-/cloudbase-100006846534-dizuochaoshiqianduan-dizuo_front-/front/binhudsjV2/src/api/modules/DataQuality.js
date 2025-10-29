import IP from "../ipconfig";
const api = {
  //建筑物统计

  Spatialization:
    IP.server_statistics + "/statistics/data/build/bySpatializationRate", //建筑空间化
  Vacancy: IP.server_statistics + "/statistics/data/build/byVacantRate", //房屋空置
  RoomBinding:
    IP.server_statistics + "/statistics/data/build/byPopulationBindingRate", //人房绑定
  GridStatistics:
    IP.server_statistics + "/statistics/data/grid/quantity/byRegionType", //网格数量统计
  InformationPerfection: IP.server_statistics + "/statistics/data/integrity" //完善率
};
export default api;
