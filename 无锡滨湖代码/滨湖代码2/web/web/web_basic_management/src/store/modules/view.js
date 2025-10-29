const state = {
  informationVisible: false,
  dictionCheck: true,
  resizeValue: [1, 1],
  integration: {}
}

const mutations = {
  SET_INFORMATIONVISIBLE: (state, status) => {
    state.informationVisible = status
  },
  SET_DICTIONCHECK: (state, status) => {
    state.dictionCheck = status
  },
  SET_RESIZE: (state, status) => {
    state.resizeValue = status
  },
  SET_INTEGRATION: (state, status) => {
    state.integration = status
  },
}

const actions = {
  setInformationvisible({ commit }, val) {
    commit('SET_INFORMATIONVISIBLE', val)
  },
  setDictionCheck({ commit }, val) {
    commit('SET_DICTIONCHECK', val)
  },
  setResize({ commit }, val) {
    commit('SET_RESIZE', val)
  },
  setIntegration({ commit }, val) {
    commit('SET_INTEGRATION', val)
  },
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
