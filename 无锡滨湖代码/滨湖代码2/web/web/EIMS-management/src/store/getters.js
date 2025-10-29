const getters = {
  ratio: state => state.app.ratio,
  precinctName: state => state.app.precinctName,
  precinctId: state => state.app.precinctId,
  precinctKind: state => state.app.precinctKind,
  intervalRefresh: state => state.view.intervalRefresh,
  resizeValue: state => state.view.resizeValue,
  dataList: state => state.view.dataList,
  integration: state => state.view.integration,
  right_btnVisible: state => state.view.right_btnVisible,
  left_btnVisible: state => state.view.left_btnVisible,
  isCollapse: state => state.view.isCollapse,
  pointInfoList: state => state.map.pointInfoList,
  currentShowPoint: state => state.map.currentShowPoint,
  windowInfoDetail: state => state.map.windowInfoDetail,
  mapClickGps: state => state.map.mapClickGps
}
export default getters
