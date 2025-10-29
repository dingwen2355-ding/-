const state = {
  resizeValue: null, // 界面大小变化
  intervalRefresh: '', // 定时刷新
  centerViewModule: {
    moduleName: 'CenterView',
    moduleData: null
  } // CenterView MapView
}

const mutations = {
  SET_RESIZE: (state, status) => {
    state.resizeValue = status
  },
  SET_INTERVAL_REFRESH: (state, status) => {
    state.intervalRefresh = status
  },
  SET_CENTERVIEWMODULE: (state, status) => {
    state.centerViewModule = status
  }
}

const actions = {
  setResize ({ commit }, val) {
    commit('SET_RESIZE', val)
  },
  setIntervalRefresh ({ commit }, val) {
    commit('SET_INTERVAL_REFRESH', val)
  },
  setCenterViewModule ({ commit }, val) {
    commit('SET_CENTERVIEWMODULE', val)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
