/*
 * @Descripttion: vuex封装
   使用this.$store.commit("statechange", { ways: "state中的key", data: 数值 });
 * @version:
 * @Author: GM
 * @Date: 2021-03-02 09:54:45
 * @LastEditors: GM
 * @LastEditTime: 2021-09-18 14:23:20
 */
import Vue from "vue";
import Vuex from "vuex";
import sys from "./modules/sys.js";

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
  oUserInfo: {
    "account": "",
    "name": "",
    "email": "",
    "phone": "",
    "birthday": "",
    "sex": 1
  },//用户信息
  aUserAppData: [],// 用户的应用
  aAllApp: [],// 平台所有的应用-无分类
  bRequestState: true,// 请求状态(是否正在请求中)
  sCode: '',// 地址栏传过来的code
  aAllAppComUtil: [],
};

//实时监听state值的变化(最新状态)
const getters = {

};
const outState = [];

// 改变state的值
const mutations = {
  // 用户信息
  fSetUserInfo(state, data) {
    state.oUserInfo = { ...data };
    // console.log(state.oUserInfo)
  },
  // 存储用户的应用
  fSetUserAppData(state, data) {
    state.aUserAppData = data;
  },
  // 设置全部应用
  fSetAllApp(state, data) {
    // console.log(data)
    state.aAllApp = data;
  },
  // 设置全部应用
  fSetAllAppData(state, data) {
    state.aAllAppData = data;
  },
  // 设置组件和工具 分类的
  fSetComUtilsData(state, data) {
    state.aComUtilsData = data;
  },
  // 设置全部应用 组件 工具 （分类的）
  fSetAllAppDataRes(state, data) {
    state.aAllAppDataRes = data;
  },
  // 设置请求状态
  fSetRequestState(state, flag) {
    state.bRequestState = flag;
  },
  // 第三方跳转传过来的code
  fSetCode(state, code) {
    state.sCode = code;
  },
  // 
  fSetAllAppComUtil(state, data) {
    state.aAllAppComUtil = data;
  },
};

const actions = {}; //自定义触发mutations里函数的方法，context与store 实例具有相同方法和属性(操作是异步的，即如果你在同一个组件连续调用多次,获取的值就不是一样的)

export default new Vuex.Store({
  state,
  getters,
  mutations,
  actions,
  modules: {
    // 挂载子仓库
    sys
  }
});
