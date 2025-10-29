const getters = {
  informationVisible: (state) => state.view.informationVisible,
  dictionCheck: (state) => state.view.dictionCheck,
  resizeValue: (state) => state.view.resizeValue,
  mapClickGps: (state) => state.map.mapClickGps,
  windowInfoDetail: (state) => state.map.windowInfoDetail,
  integration: state => state.view.integration,
}
export default getters
