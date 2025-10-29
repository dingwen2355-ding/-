'use strict'
const airCityStore = {}

/* @usage : store state data shcema
            this.$store.state.airCity.eventSealAPI  */
airCityStore.state = {
    // 封装API的JS回调数据
    eventSealAPI: {},
    //场景信息
    sceneInfoData: {},
    //建筑信息
    buildCoord: [],
    //视频监控标注
    vMkr: [],
    //视频token
    vTk: null,
}

airCityStore.mutations = {
    'SET_EVENTSEALAPI'(state, obj) {
        state.eventSealAPI = obj
    },
    'SET_SCENEINFO'(state, obj) {
        state.sceneInfoData = obj
    },
    'SET_BUILDCOORD'(state, obj) {
        state.buildCoord = obj
    },
    'SET_VMKR'(state, obj) {
      state.vMkr = obj
    },
    'SET_VTK'(state, obj) {
      state.vTk = obj
    },
}

/** this.$store.getters.eventSealAPI */
airCityStore.getters = {
    eventSealAPI: state => state.eventSealAPI,
    sceneInfoData: state => state.sceneInfoData,
    buildCoord: state => state.buildCoord,
    vMkr: state => state.vMkr,
    vTk: state => state.vTk,
}

airCityStore.actions = {
    'SET_EVENTSEALAPI_ACTION'({ commit }, obj) {
        return new Promise((resolve, reject) => {
            commit('SET_EVENTSEALAPI', obj)
            resolve()
        })
    },
    'SET_SCENEINFO_ACTION'({ commit }, obj) {
        return new Promise((resolve, reject) => {
            commit('SET_SCENEINFO', obj)
            resolve()
        })
    },
    'SET_BUILDCOORD_ACTION'({ commit }, obj) {
        return new Promise((resolve, reject) => {
            commit('SET_BUILDCOORD', obj)
            resolve()
        })
    },
    'SET_VMKR_ACTION'({ commit }, obj) {
      return new Promise((resolve, reject) => {
          commit('SET_VMKR', obj)
          resolve()
      })
    },
    'SET_VTK_ACTION'({ commit }, obj) {
      return new Promise((resolve, reject) => {
          commit('SET_VTK', obj)
          resolve()
      })
    },
}

export default airCityStore