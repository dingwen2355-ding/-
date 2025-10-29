const state = {
  pointInfoList: [],
  currentShowPoint: [],
  windowInfoDetail: {},
  mapClickGps: '',
  circleContainPoints: [],
  currentCircle: {
    geoType: '',
    center: {
      gpsX: '',
      gpsY: '',
      precinctName: ''
    },
    radius: 0
  },
  areaDataList: {
    data: [],
    type: ''
  },
  deviceWater: [],
  disasterInfo: [],
  importPoint: [],
  forestFarm: [],
  importFireDevice: [],
  industrialPark: [],
  circleContainCamera: []
}

const mutations = {
  SET_INDUSTRIAL_PARK: (state, status) => {
    state.industrialPark = status
  },
  SET_IMPORT_FIRE_DEVICE: (state, status) => {
    state.importFireDevice = status
  },
  SET_FOREST_FARM: (state, status) => {
    state.forestFarm = status
  },
  SET_IMPORT_POINT: (state, status) => {
    state.importPoint = status
  },
  SET_DISASTER_INFO: (state, status) => {
    state.disasterInfo = status
  },
  SET_DEVICE_WATER: (state, status) => {
    state.deviceWater = status
  },
  SET_POINT_INFO_LIST: (state, status) => {
    state.pointInfoList = status
  },
  SET_CURRENT_SHOW_POINT: (state, status) => {
    state.currentShowPoint = status
  },
  SET_WINDOW_INFO_DETAIL: (state, status) => {
    state.windowInfoDetail = status
  },
  SET_MAP_CLICK_GPS: (state, status) => {
    state.mapClickGps = status
  },
  SET_CIRCLE_CONTAIN_POINTS: (state, status) => {
    state.circleContainPoints = status
  },
  SET_CURRENT_CIRCLE: (state, status) => {
    state.currentCircle = status
  },
  SET_FIRE_POINT: (state, status) => {
    state.firePoint = status
  },
  SET_AREA_DATA_LIST: (state, status) => {
    state.areaDataList = status
  },
  SET_CIRCLE_CONTAIN_CAMERA: (state, status) => {
    state.circleContainCamera = status
  }
}

const actions = {
  setIndustrialPark ({ commit }, val) {
    commit('SET_INDUSTRIAL_PARK', val)
  },
  setImportFireDevice ({ commit }, val) {
    commit('SET_IMPORT_FIRE_DEVICE', val)
  },
  setForestFarm ({ commit }, val) {
    commit('SET_FOREST_FARM', val)
  },
  setImportPoint ({ commit }, val) {
    commit('SET_IMPORT_POINT', val)
  },
  setDisasterInfo ({ commit }, val) {
    commit('SET_DISASTER_INFO', val)
  },
  setDeviceWater ({ commit }, val) {
    commit('SET_DEVICE_WATER', val)
  },
  setPointInfoList ({ commit }, val) {
    commit('SET_POINT_INFO_LIST', val)
  },
  setCurrentShowPoint ({ commit }, val) {
    commit('SET_CURRENT_SHOW_POINT', val)
  },
  setWindowInfoDetail ({ commit }, val) {
    commit('SET_WINDOW_INFO_DETAIL', val)
  },
  setMapClickGps ({ commit }, val) {
    commit('SET_MAP_CLICK_GPS', val)
  },
  setCircleContainPoints ({ commit }, val) {
    commit('SET_CIRCLE_CONTAIN_POINTS', val)
  },
  setCurrentCircle ({ commit }, val) {
    commit('SET_CURRENT_CIRCLE', val)
  },
  setAreaDataList ({ commit }, val) {
    commit('SET_AREA_DATA_LIST', val)
  },
  setCircleContainCamera({ commit }, val) {
    commit('SET_CIRCLE_CONTAIN_CAMERA', val)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
