const state = {
  pointInfoList: [],
  currentShowPoint: [],
  windowInfoDetail: {},
  mapClickGps: ''
}

const mutations = {
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
  }
}

const actions = {
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
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
