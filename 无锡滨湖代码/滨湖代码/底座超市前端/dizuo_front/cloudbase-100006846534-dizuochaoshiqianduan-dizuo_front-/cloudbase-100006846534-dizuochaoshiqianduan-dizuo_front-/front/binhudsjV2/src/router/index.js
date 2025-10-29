/*
 * @Descripttion: 路由配置
 * @version:
 * @Author: GM
 * @Date: 2021-03-04 10:07:56
 * @LastEditors: GM
 * @LastEditTime: 2021-09-29 17:02:56
 */
import Vue from "vue";
import VueRouter from "vue-router";
// import Home from "@/views/page/Home.vue";
// import routerv from "@/views/layouts/routerv";
import store from "@/store/index";
import "@/components/utils/ofront-sdk.js";
import { baseInteractive } from "@/api/api";
import IP from "@/api/ipconfig";
import { get } from "@/api/ajax";
Vue.use(VueRouter);

const originalPush = VueRouter.prototype.push;
//修改原型对象中的push方法
// const isProduction = process.env.NODE_ENV === "prod";
// VueRouter.prototype.push = function push(location) {
//   // 打包后路由出问题
//   if (!isProduction) {
//     return originalPush.call(this, location).catch(err => err);
//   } else {
//     return originalPush.call(this, location);
//   }
// };
const localroutes = [
  // 数字滨湖
  {
    name: '数字滨湖',
    path: "/",
    meta: {
      key: 'digitalBinhu',
      keepAlive: false // 指定当前页面不缓存
    },
    component: () => import('../views/digitalBinhu/digitalBinhu')
  },
  // 检索页面
  {
    name: '检索',
    path: "/digitalBinhu/binhuSearch",
    meta: {
      key: 'digitalBinhu',
      keepAlive: false // 指定当前页面不缓存
    },
    component: () => import('../views/digitalBinhu/binhuSearch')
  },
  // 列表搜索
  {
    name: '搜索',
    path: "/digitalBinhu/searchList",
    meta: {
      key: 'digitalBinhu',
      keepAlive: false // 指定当前页面不缓存
    },
    component: () => import('../views/digitalBinhu/searchList')
  },
   // 应用推荐
   {
    name: '应用推荐',
    path: "/digitalBinhu/appRecommend",
    meta: {
      key: 'digitalBinhu',
      keepAlive: false // 指定当前页面不缓存
    },
    component: () => import('../views/digitalBinhu/appRecommend')
  },
  // 最新推荐
  {
    name: '最新推荐',
    path: "/digitalBinhu/newRecommend",
    meta: {
      key: 'digitalBinhu',
      keepAlive: false // 指定当前页面不缓存
    },
    component: () => import('../views/digitalBinhu/newRecommend')
  },
  // 申请上架
  {
    name: '申请上架',
    path: "/digitalBinhu/appGronding",
    meta: {
      key: 'digitalBinhu',
      keepAlive: false // 指定当前页面不缓存
    },
    component: () => import('../views/digitalBinhu/appGronding')
  },
  // 底座详情
  {
    name: '底座详情',
    path: "/digitalBinhu/baseDetail",
    meta: {
      key: 'digitalBinhu',
      keepAlive: false // 指定当前页面不缓存
    },
    component: () => import('../views/digitalBinhu/baseDetail')
  },
  // 数字底座
  {
    name: '数字底座',
    path: "/digitalBase",
    meta: {
      key: 'digitalBase',
      keepAlive: false // 指定当前页面不缓存
    },
    component: () => import('../views/digitalBase/digitalBase')
  },
  // 搜索页面
  {
    name: '资源搜索',
    path: "/digitalBase/search",
    meta: {
      key: 'digitalBase',
      keepAlive: false // 指定当前页面不缓存
    },
    component: () => import('../views/digitalBase/search')
  },
  // 工具组件搜索
  {
    name: '工具组件搜索',
    path: "/digitalBase/searchv2",
    meta: {
      key: 'digitalBase',
      keepAlive: false // 指定当前页面不缓存
    },
    component: () => import('../views/digitalBase/searchv2')
  },
  // 应用市场
  {
    name: '应用市场',
    path: "/appMarket",
    meta: {
      key: 'appMarket',
      keepAlive: false // 指定当前页面不缓存
    },
    component: () => import('../views/appMarket/appMarket'),
  },
  // 全部应用
  {
    name: '全部应用',
    path: "/appMarket/allapp",
    meta: {
      key: 'appMarket',
      keepAlive: false // 指定当前页面不缓存
    },
    component: () => import('../views/appMarket/allapp')
  },
   // 全部应用
   {
    name: '全部应用',
    path: "/allapp",
    meta: {
      key: 'appMarket',
      keepAlive: false // 指定当前页面不缓存
    },
    component: () => import('../views/appMarket/allapp')
  },
  // 应用详情
  {
    name: '应用详情',
    path: "/appMarket/appDetails",
    meta: {
      key: 'appMarket',
      keepAlive: false // 指定当前页面不缓存
    },
    component: () => import('../views/appMarket/appDetails')
  },

  // 个人中心
  {
    name: '个人中心',
    path: "/userCenter",
    meta: {
      key: 'userCenter',
      keepAlive: false // 指定当前页面不缓存
    },
    component: () => import('../views/userCenter/userCenter')
  },
  {
    name: '应用详情',
    path: "/userCenter/appDetails",
    meta: {
      key: 'userCenter',
      keepAlive: false // 指定当前页面不缓存
    },
    component: () => import('../views/userCenter/appDetails')
  },
  // 应用申请
  {
    name: '应用申请',
    path: "/appApply",
    meta: {
      key: 'appApply',
      keepAlive: false // 指定当前页面不缓存
    },
    component: () => import('../views/appApply/appApply')
  },
  // 能力申请
  {
    name: '能力申请',
    path: "/appNengLiApply",
    meta: {
      key: 'appNengLiApply',
      keepAlive: false // 指定当前页面不缓存
    },
    component: () => import('../views/appApply/appNengLiApply')
  },
];

const router = new VueRouter({
  routes: localroutes
});

// 懒加载 vue 组件
const loadView = view => {
  // 路由懒加载
  return () =>
    import(/* webpackChunkName: "[request]" */ `@/views/${view}.vue`);
};

router.beforeEach((to, from, next) => {
  if (to.meta.title) {
    if (
      to.meta.title.indexOf("户况图") == -1 &&
      to.meta.title.indexOf("403") == -1 &&
      !to.meta.iframe
    ) {
      store.commit("statechange", { ways: "showAreaPicker", data: true });
    } else {
      store.commit("statechange", { ways: "showAreaPicker", data: false });
    }
  }
  next();
});
export default router;
