import Vue from 'vue'
import VueRouter from 'vue-router'
// import qs from 'qs'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/consultationNotice'
  },
  {
    path: '/mapView',
    name: 'mapView',
    component: () => import('../views/classicCaseQuery/mapView.vue'),
    meta: {
      showBottomMenu: true,
      menuName: '地图'
    }
  },
  {
    path: '/mainPage',
    name: 'mainPage',
    component: () => import('../views/mainPage/mainPage.vue'),
    children: [
      {
        path: '/consultationNotice',
        name: 'consultationNotice',
        component: () => import('../views/consultationNotice/consultationNotice.vue'),
        meta: {
          showBottomMenu: true,
          menuName: '资讯'
        }
      },
      {
        path: '/consultationNoticeDetail',
        name: 'consultationNoticeDetail',
        component: () => import('../views/consultationNoticeDetail/consultationNoticeDetail.vue')
      },
      {
        path: '/eventOverview',
        name: 'eventOverview',
        component: () => import('../views/eventOverview/eventOverview.vue'),
        meta: {
          showBottomMenu: true,
          menuName: '事件'
        }
      },
      {
        path: '/eventDetail',
        name: 'eventDetail',
        component: () => import('../views/eventDetail/eventDetail.vue')
      },
      {
        path: '/notificationOverview',
        name: 'notificationOverview',
        component: () => import('../views/notificationOverview/notificationOverview.vue'),
        meta: {
          showBottomMenu: true,
          menuName: '通知'
        }
      },
      {
        path: '/notificationDetail',
        name: 'notificationDetail',
        component: () => import('../views/notificationDetail/notificationDetail.vue')
      },
      {
        path: '/eventReport',
        name: 'eventReport',
        component: () => import('../views/eventReport/eventReport.vue')
      },
      {
        path: '/classicCaseQuery',
        name: 'classicCaseQuery',
        component: () => import('../views/classicCaseQuery/classicCaseQuery.vue'),
        meta: {
          showBottomMenu: true,
          menuName: '查询'
        }
      },
      {
        path: '/specificationDetail',
        name: 'specificationDetail',
        component: () => import('../views/specificationDetail/specificationDetail.vue')
      },
      {
        path: '/lawDetail',
        name: 'lawDetail',
        component: () => import('../views/lawDetail/lawDetail.vue')
      },
      {
        path: '/historyEventDetail',
        name: 'historyEventDetail',
        component: () => import('../views/historyEventDetail/historyEventDetail.vue')
      }
    ]
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/login/login.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 获取原型对象上的push函数
const originalPush = VueRouter.prototype.push
// 修改原型对象中的push方法
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch((err) => err)
}

router.beforeEach((to, from, next) => {
  if (to.name === 'login') {
    next()
  } else {
    let token = sessionStorage.getItem('token')
    if (token) {
      next()
    } else {
      const url = location.href
      const hasCode = url.split('code=').length > 1
      if (hasCode) {
        // 有code ，进去登陆
        next()

      } else {
        const path = encodeURIComponent('http://2.21.137.156:8085')
        let OAuthURL = `https://open.weixin.qq.com/connect/oauth2/authorize?appid=ww29a8dfb1be6173c1&redirect_uri=${path}&response_type=code&scope=SCOPE&agentid=1000421&state=STATE#wechat_redirect`
        window.location.replace(OAuthURL)
      }
    }
  }
})

export default router
