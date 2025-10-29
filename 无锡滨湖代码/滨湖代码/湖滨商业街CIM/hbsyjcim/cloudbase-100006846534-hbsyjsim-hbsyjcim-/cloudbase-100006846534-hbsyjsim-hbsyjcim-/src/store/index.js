import Vue from 'vue'
import Vuex from 'vuex'

// 导入 modules 文件夹下的模块

// 导入 getters文件
import getters from './getters'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {

  },
  getters
})

export default store
