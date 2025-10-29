/*
 * @Descripttion: vuex子仓库
   使用this.$store.commit("statechange", { ways: "state中的key", data: 数值 });
 * @version:
 * @Author: GM
 * @Date: 2021-03-02 09:54:45
 * @LastEditors: GM
 * @LastEditTime: 2021-08-04 16:13:27
 */
import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

// 本地储存
const setStorage = (key, value) => {
  if (typeof value === "object") {
    value = JSON.stringify(value);
  }
  sessionStorage.setItem(key, value);
};

// 本地获取
const getStorage = item => {
  let str = sessionStorage.getItem(item);
  if (typeof str === "object") return JSON.parse(str);
  return str;
};

//要设置的全局访问的state对象,赋予初始属性值
const state = {
};

//实时监听state值的变化(最新状态)
const getters = {

};

const outState = [];

const mutations = {

};

const actions = {}; //自定义触发mutations里函数的方法，context与store 实例具有相同方法和属性(操作是异步的，即如果你在同一个组件连续调用多次,获取的值就不是一样的)

const store = {
  // namespaced用于在全局引用此文件里的方法时标识这一个的文件名，使得让人明白这些数据来自哪个仓库
  // 即当你需要在别的文件里面使用子仓库(mapStates、mapGetters、mapActions)时，里面的方法需要注明来自哪一个模块的方法
  namespaced: true,
  state,
  getters,
  mutations,
  actions
};

export default store;
