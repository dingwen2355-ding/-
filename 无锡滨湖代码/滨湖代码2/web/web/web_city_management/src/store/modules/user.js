import { login, logout } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'

const state = {
  userName: '',
  token: getToken(),
  userRight: null
}

const mutations = {
  SET_TOKEN: (state, status) => {
    state.token = status
  },
  SET_NAME: (state, status) => {
    state.userName = status
  },
  SET_USER_RIGHT: (state, status) => {
    state.userRight = status
  }
}

const actions = {
  // user login
  login ({ commit }, userInfo) {
    const { username, password } = userInfo
    return new Promise((resolve, reject) => {
      login({ username: username.trim(), password: password }).then(response => {
        const { data } = response
        commit('SET_TOKEN', data.token)
        setToken(data.token)
        commit('SET_NAME', data.token)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },
  // user logout
  logout ({ commit, state, dispatch }) {
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        commit('SET_TOKEN', '')
        removeToken()
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },
  // remove token
  resetToken ({ commit }) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '')
      removeToken()
      resolve()
    })
  },
  setToken ({ commit }, token) {
    commit('SET_TOKEN', token)
    setToken(token)
  },
  setName ({ commit }, userName) {
    return new Promise(resolve => {
      commit('SET_NAME', userName)
      resolve()
    })
  },
  setUserId ({ commit }, userId) {
    return new Promise(resolve => {
      commit('SET_USERID', userId)
      resolve()
    })
  },
  setRoleName ({ commit }, roleName) {
    return new Promise(resolve => {
      commit('SET_ROLENAME', roleName)
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
