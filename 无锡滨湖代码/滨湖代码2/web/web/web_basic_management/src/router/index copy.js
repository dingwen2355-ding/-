import Vue from 'vue'
import VueRouter from 'vue-router'
// import { getToken } from '@/utils/auth'

Vue.use(VueRouter)

const routes = [
  { path: '/', name: 'login', component: () => import('../Login') },
  {
    path: '/main',
    name: 'main',
    component: () => import('../Main.vue'),
    children: [
      {
        path: '/populationTable',
        name: 'populationTable',
        component: () => import('../views/populationTable')
      },
      // 事件接入
      {
        path: '/eventSink',
        name: 'eventSink',
        component: () => import('../views/eventSink/eventSink')
      },
      // 事件派遣
      {
        path: '/eventToBeSent',
        name: 'eventToBeSent',
        component: () => import('../views/eventToBeSent/eventToBeSent')
      },
      {
        path: '/eventHasSent',
        name: 'eventHasSent',
        component: () => import('../views/eventHasSent/eventHasSent')
      },
      // 事件处置
      {
        path: '/myAgent',
        name: 'myAgent',
        component: () => import('../views/myAgent/myAgent')
      },
      {
        path: '/beyondEvent',
        name: 'beyondEvent',
        component: () => import('../views/beyondEvent/beyondEvent')
      },
      {
        path: '/finishEvent',
        name: 'finishEvent',
        component: () => import('../views/finishEvent/finishEvent')
      },
      {
        path: '/gridDisposal',
        name: 'gridDisposal',
        component: () => import('../views/gridDisposal/gridDisposal')
      },
      {
        path: '/grabSingle',
        name: 'grabSingle',
        component: () => import('../views/grabSingle/grabSingle')
      },
      // 事件监管
      {
        path: '/reminded',
        name: 'reminded',
        component: () => import('../views/reminded/reminded')
      },
      {
        path: '/ruleManagement',
        name: 'ruleManagement',
        component: () => import('../views/ruleManagement/ruleManagement')
      },
      {
        path: '/statisticalAnalysis',
        name: 'statisticalAnalysis',
        component: () => import('../views/statisticalAnalysis/statisticalAnalysis')
      },
      // 系统管理
      {
        path: '/area',
        name: 'area',
        component: () => import('../views/area/Area')
      },
      {
        path: '/user',
        name: 'user',
        component: () => import('../views/user/UserManager')
      },
      {
        path: '/role',
        name: 'role',
        component: () => import('../views/role/RoleManager')
      },
      {
        path: '/module',
        name: 'module',
        component: () => import('../views/module/ModuleManager')
      },

      {
        path: '/dictionaries',
        name: 'dictionaries',
        component: () => import('../views/dictionaries/AllCheck')
      },
      // {
      //   path: '/dictionaryList',
      //   name: 'dictionaryList',
      //   component: () => import('../views/dictionaries/DictionaryList')
      // },
      {
        path: '/parameterSetting',
        name: 'parameterSetting',
        component: () => import('../views/parameterSetting/ParameterSetting')
      },
      {
        path: '/noticeAnnouncement',
        name: 'noticeAnnouncement',
        component: () => import('../views/noticeAnnouncement/NoticeAnnouncement')
      },
      {
        path: '/operationLog',
        name: 'operationLog',
        component: () => import('../views/operationLog/OperationLog')
      },
      {
        path: '/loginLog',
        name: 'loginLog',
        component: () => import('../views/loginLog/LoginLog')
      },
      {
        path: '/formSettings',
        name: 'formSettings',
        component: () => import('../views/formSettings/FormSettings')
      },
      {
        path: '/takeBack',
        name: 'takeBack',
        component: () => import('../views/takeBack/takeBack')
      }
    ]
  },
  {
    path: '/eventReport',
    name: 'eventReport',
    component: () => import('../views/qrCode/qrCode')
  }
]

// 点击过快的路由冗余修改
// 获取原型对象上的push函数
const originalPush = VueRouter.prototype.push
// 修改原型对象中的push方法
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  if (to.path !== '/' && to.path !== '/eventReport') {
    // 未登录，跳转到登录页面
    if (sessionStorage.getItem('token')) {
      next()
    } else {
      next('/')
    }
  } else {
    next()
  }
})
export default router
