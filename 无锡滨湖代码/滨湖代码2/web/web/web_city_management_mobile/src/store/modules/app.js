const state = {
  documentVisibility: true // false 应用被挂起 true 应用被唤醒
}

const mutations = {
  SET_DOCUMENTVISIBILITY: (state, status) => {
    state.documentVisibility = status
  }
}

const actions = {
  setDocumentVisibility({ commit }, val) {
    commit('SET_DOCUMENTVISIBILITY', val)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
