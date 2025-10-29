import qs from 'qs'
let urlParams = qs.parse(location.href.split('ChengDuCityManagementStaff')[1] ? location.href.split('ChengDuCityManagementStaff')[1].replace('?', '') : '')
let precinctId = urlParams.precinctId || sysConfig.defaultPrecinct.precinctId
let precinctName = urlParams.precinctName || sysConfig.defaultPrecinct.precinctName

const state = {
  ratio: 1920,
  precinctName: precinctName,
  precinctId: precinctId,
  moduleVisible: false,
  eventId: {
    id: '',
    timer: ''
  }
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
  SET_MODULE_VISIBLE: (state, status) => {
    state.moduleVisible = status
  },
  SET_EVENT_ID: (state, status) => {
    state.eventId = status
  }
}

const actions = {
  setRatio ({ commit }, val) {
    commit('SET_RATIO', val)
  },
  setPrecinctName({ commit }, val) {
    commit('SET_PRECINCT_NAME', val)
  },
  setPrecinctId({ commit }, val) {
    commit('SET_PRECINCT_ID', val)
  },
  setModuleVisible({ commit }, val) {
    commit('SET_MODULE_VISIBLE', val)
  },
  setEventId ({ commit }, val) {
    commit('SET_EVENT_ID', val)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
