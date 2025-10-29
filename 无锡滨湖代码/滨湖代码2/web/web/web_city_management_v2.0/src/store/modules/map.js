const state = {
  pointInfoList: [],
  currentShowPoint: [],
  windowInfoDetail: {},
  mapClickGps: '',
  circleContainPoints: [],
  eventCircleContainPoints: [],
  filterIconList: [],
  mutiPoints: [],
  currentCircle: {
    geoType: '',
    center: {
      gpsX: '',
      gpsY: '',
      precinctName: ''
    },
    radius: 0
  }
}

const mutations = {
  SET_MUTI_POINTS: (state, status) => {
    state.mutiPoints = status
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
  SET_EVENT_CIRCLE_CONTAIN_POINTS: (state, status) => {
    state.eventCircleContainPoints = status
  },
  SET_CURRENT_CIRCLE: (state, status) => {
    state.currentCircle = status
  },
  SET_FIRE_POINT: (state, status) => {
    state.firePoint = status
  },
  SET_FILTER_ICON_LIST: (state, status) => {
    state.filterIconList = status
  }
}

const actions = {
  setMutiPoints({ commit }, val) {
    commit('SET_MUTI_POINTS', val)
  },
  setPointInfoList({ commit }, val) {
    commit('SET_POINT_INFO_LIST', val)
  },
  setCurrentShowPoint({ commit }, val) {
    commit('SET_CURRENT_SHOW_POINT', val)
  },
  setWindowInfoDetail({ commit }, val) {
    commit('SET_WINDOW_INFO_DETAIL', val)
  },
  setMapClickGps({ commit }, val) {
    commit('SET_MAP_CLICK_GPS', val)
  },
  setCircleContainPoints({ commit }, val) {
    commit('SET_CIRCLE_CONTAIN_POINTS', val)
  },
  setEventCircleContainPoints({ commit }, val) {
    commit('SET_EVENT_CIRCLE_CONTAIN_POINTS', val)
  },
  setCurrentCircle({ commit }, val) {
    commit('SET_CURRENT_CIRCLE', val)
  },
  setFilterIconList({ commit }, val) {
    commit('SET_FILTER_ICON_LIST', val)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
