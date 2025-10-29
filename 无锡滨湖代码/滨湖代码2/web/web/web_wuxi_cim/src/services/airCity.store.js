'use strict'
const airCityStore = {}

/* @usage : store state data shcema
            this.$store.state.airCity.eventSealAPI  */
airCityStore.state = {
    // 封装API的JS回调数据
    eventSealAPI: {},
    //场景信息
    sceneInfoData: {},
}

airCityStore.mutations = {
    'SET_EVENTSEALAPI'(state, obj) {
        state.eventSealAPI = obj
    },
    'SET_SCENEINFO'(state, obj) {
        state.sceneInfoData = obj
    },
}

/** this.$store.getters.eventSealAPI */
airCityStore.getters = {
    eventSealAPI: state => state.eventSealAPI,
    sceneInfoData: state => state.sceneInfoData,
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
}

export default airCityStore