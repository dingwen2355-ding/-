const state = {
  mapClickGps: []
}

const mutations = {
  SET_MAP_CLICK_GPS: (state, gps) => {
    state.mapClickGps = gps
  }
}

const actions = {
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
