import {
  setStore,
  getStore,
  removeStore
} from '@/util/store'
import { getSysInfo } from "@/api/system/tenant";
import website from '@/config/website';
import { getSsoLogoutUri } from "@/api/common";


const common = {

  state: {
    language: getStore({ name: 'language' }) || 'zh',
    isCollapse: false,
    isFullScren: false,
    isMenu: true,
    isShade: false,
    screen: -1,
    isLock: getStore({ name: 'isLock' }) || false,
    showTag: true,
    showDebug: true,
    showCollapse: true,
    showSearch: true,
    showLock: true,
    showFullScren: true,
    showTheme: true,
    showMenu: true,
    showColor: true,
    colorName: getStore({ name: 'colorName' }) || '#0e54d6',
    themeName: getStore({ name: 'themeName' }) || 'theme-default',
    lockPasswd: getStore({ name: 'lockPasswd' }) || '',
    website: website,
    websitConfig: {},
  },
  mutations: {
    SET_WEBSITE_CONFIG: (state, config) => {
      state.websitConfig = config
    },
    SET_LANGUAGE: (state, language) => {
      state.language = language
      setStore({
        name: 'language',
        content: state.language
      })
    },
    SET_SHADE: (state, active) => {
      state.isShade = active;
    },
    SET_COLLAPSE: (state) => {
      state.isCollapse = !state.isCollapse;
    },
    SET_FULLSCREN: (state) => {
      state.isFullScren = !state.isFullScren;
    },
    SET_IS_MENU: (state, menu) => {
      state.isMenu = menu;
    },
    SET_LOCK: (state) => {
      state.isLock = true;
      setStore({
        name: 'isLock',
        content: state.isLock,
        type: 'session'
      })
    },
    SET_SCREEN: (state, screen) => {
      state.screen = screen;
    },
    SET_COLOR_NAME: (state, colorName) => {
      state.colorName = colorName;
      setStore({
        name: 'colorName',
        content: state.colorName,
      })
    },
    SET_THEME_NAME: (state, themeName) => {
      state.themeName = themeName;
      setStore({
        name: 'themeName',
        content: state.themeName,
      })
    },
    SET_LOCK_PASSWD: (state, lockPasswd) => {
      state.lockPasswd = lockPasswd;
      setStore({
        name: 'lockPasswd',
        content: state.lockPasswd,
        type: 'session'
      })
    },
    CLEAR_LOCK: (state) => {
      state.isLock = false;
      state.lockPasswd = '';
      removeStore({
        name: 'lockPasswd',
        type: 'session'
      });
      removeStore({
        name: 'isLock',
        type: 'session'
      });
    },
  },
  actions: {
    // 获取网站配置
    GetWebsiteConfig({ commit }) {
      return new Promise((resolve, reject) => {
        getSysInfo().then(res => {
          let content = res.data.data;
          if (content.logo) {
            window.sessionStorage.setItem("linkLogo", content.logo);
          }
          commit('SET_WEBSITE_CONFIG', content);
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      })
    },

    //设置app过期时间
    UpdateAppExpiryTime({ commit }) {
       window.sessionStorage.setItem("shbhExpiryTime", new Date().getTime() + 600000);
    },
    //监测app是否过期，如果过期自动退出系统
    CheckAppExpiryTime({ commit },app) {
       let shbhExpiryTime = window.sessionStorage.getItem("shbhExpiryTime");
       if (shbhExpiryTime) {
         if (new Date().getTime() > shbhExpiryTime) {
             app.$store.dispatch("LogOut").then(() => {
             window.sessionStorage.removeItem("sideBar");
             getSsoLogoutUri().then((res) => {
               window.location.href = res.data.data;
             });
           });
         }
       }
    },

  }
}
export default common
