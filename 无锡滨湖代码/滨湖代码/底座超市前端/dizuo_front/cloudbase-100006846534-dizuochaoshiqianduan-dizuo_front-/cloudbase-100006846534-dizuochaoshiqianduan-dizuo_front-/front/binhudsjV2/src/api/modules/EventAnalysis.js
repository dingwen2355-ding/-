import IP from "../ipconfig";
const api = {
  //事件分析
  ReportTimeAnalysis: IP.server_statistics + "/statistics/event/byCreateTime", //上报时间分析
  ProcessingTimeAnalysis: IP.server_statistics + "/statistics/event/byTimeout", //办理时限分析
  CategoryAnalysis: IP.server_statistics + "/statistics/event/byMatterType", //事项类别分析
  EventsCategoryAnalysis:
    IP.server_statistics + "/statistics/event/byMatterTypeLevelOne", //事项大类分析
  NonconformanceEventStatistics:
    IP.server_statistics + "/statistics/event/byUnqualifiedRate" //不合格事件统计
};
export default api;
