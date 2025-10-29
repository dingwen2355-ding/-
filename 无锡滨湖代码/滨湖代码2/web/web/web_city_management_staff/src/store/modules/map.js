const state = {
  infoWindowType: null // 地图资源弹框
}

const mutations = {
  SET_INFO_WINDOW: (state, status) => {
    state.infoWindowType = status
  }
}

const actions = {
  setInfoWindow({ commit }, val) {
    commit('SET_INFO_WINDOW', val)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
