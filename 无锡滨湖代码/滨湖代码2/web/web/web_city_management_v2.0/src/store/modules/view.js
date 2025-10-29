const state = {
  resizeValue: null, // 界面大小变化
  intervalRefresh: '', // 定时刷新
  integration: {},
  right_btnVisible: false,
  left_btnVisible: false,
  eventInfo: '',
  eventPlanId: 0,
  eventTags: [],
  eventMessage: '',
  fusionVideo: false
}

const mutations = {
  SET_RESIZE: (state, status) => {
    state.resizeValue = status
  },
  SET_INTERVAL_REFRESH: (state, status) => {
    state.intervalRefresh = status
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
  SET_EVENTINFO: (state, status) => {
    state.eventInfo = status
  },
  SET_EVENTPLANID: (state, status) => {
    state.eventPlanId = status
  },
  SET_EVENTTAGS: (state, status) => {
    state.eventTags = status
  },
  SET_EVENTMESSAGE: (state, status) => {
    state.eventMessage = status
  },
  SET_FUSIONVIDEO: (state, status) => {
    state.fusionVideo = status
  }
}

const actions = {
  setResize({ commit }, val) {
    commit('SET_RESIZE', val)
  },
  setIntervalRefresh({ commit }, val) {
    commit('SET_INTERVAL_REFRESH', val)
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
  setEventInfo({ commit }, val) {
    commit('SET_EVENTINFO', val)
  },
  setEventPlanId({ commit }, val) {
    commit('SET_EVENTPLANID', val)
  },
  setEventTags({ commit }, val) {
    commit('SET_EVENTTAGS', val)
  },
  setEventMessage({ commit }, val) {
    commit('SET_EVENTMESSAGE', val)
  },
  setFusionVideo({ commit }, val) {
    commit('SET_FUSIONVIDEO', val)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
