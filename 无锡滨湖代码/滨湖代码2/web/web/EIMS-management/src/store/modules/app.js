const state = {
  ratio: 1920,
  precinctName: '',
  precinctId: '',
  precinctKind: ''
}

const mutations = {
  SET_RATIO: (state, status) => {
    state.ratio = status
  },
  SET_PRECINCT_NAME: (state, status) => {
    state.precinctName = status
  },
  SET_PRECINCT_ID: (state, status) => {
    state.precinctId = status
  },
  SET_PRECINCT_KIND: (state, status) => {
    state.precinctKind = status
  }
}

const actions = {
  setRatio({ commit }, val) {
    commit('SET_RATIO', val)
  },
  setPrecinctName({ commit }, val) {
    commit('SET_PRECINCT_NAME', val)
  },
  setPrecinctId({ commit }, val) {
    commit('SET_PRECINCT_ID', val)
  },
  setPrecinctKind({ commit }, val) {
    commit('SET_PRECINCT_KIND', val)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
