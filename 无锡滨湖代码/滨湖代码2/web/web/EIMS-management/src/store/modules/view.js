const state = {
  intervalRefresh: '', // 定时刷新
  dataList: {},
  resizeValue: null, // 界面大小变化
  integration: {},
  right_btnVisible: true,
  left_btnVisible: true,
  isCollapse: false // 左边侧边栏默认关闭
}

const mutations = {
  SET_INTERVAL_REFRESH: (state, status) => {
    state.intervalRefresh = status
  },
  SET_RESIZE: (state, status) => {
    state.resizeValue = status
  },
  SET_DATALIST: (state, status) => {
    state.dataList = status
  },
  SET_INTEGRATION: (state, status) => {
    state.integration = status
  },
  SET_RIGHT_BTN_VISIBLE: (state, status) => {
    state.right_btnVisible = status
  },
  SET_LEFT_BTN_VISIBLE: (state, status) => {
    state.left_btnVisible = status
  },
  SET_IS_COLLAPSE: (state, status) => {
    state.isCollapse = status
  }
}

const actions = {
  setIntervalRefresh({ commit }, val) {
    commit('SET_INTERVAL_REFRESH', val)
  },
  setResize({ commit }, val) {
    commit('SET_RESIZE', val)
  },
  setDatalist({ commit }, val) {
    commit('SET_DATALIST', val)
  },
  setIntegration({ commit }, val) {
    commit('SET_INTEGRATION', val)
  },
  setRightBtnVisible({ commit }, val) {
    commit('SET_RIGHT_BTN_VISIBLE', val)
  },
  setLeftBtnVisible({ commit }, val) {
    commit('SET_LEFT_BTN_VISIBLE', val)
  },
  setIsCollapse({ commit }, val) {
    commit('SET_IS_COLLAPSE', val)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
