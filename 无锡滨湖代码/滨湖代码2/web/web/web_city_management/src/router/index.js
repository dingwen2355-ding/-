import Vue from 'vue'
import VueRouter from 'vue-router'
import axios from 'axios'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'StartMenu',
    component: () => import('../views/startMenu/StartMenu')
  },
  {
    path: '/Login',
    name: 'Login',
    component: () => import('../views/Login/Login.vue')
  },
  {
    path: '/SoloDialog',
    name: 'SoloDialog',
    component: () => import('../components/map/mapDialog/SoloDialog')
  },
  {
    path: '/MapInfoWindow',
    name: 'MapInfoWindow',
    component: () => import('../components/map/mapDialog/MapInfoWindow')
  },
  {
    path: '/videoManagePanel',
    name: 'videoManagePanel',
    component: () => import('../components/VideoManage/videoManagePanel')
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
  return originalPush.call(this, location).catch(err => err)
}

router.beforeEach((to, from, next) => {
  if (!sysConfig.superControl) {
    if (to.path === '/' && to.query.code) {
      if (localStorage.getItem('cauthorization')) {
        next()
      } else {
        let code = to.query.code
        let requestAppId = 'CdZLrF07'
        let requestAppSecret = '1914d35bc31ae09ae924a330a05e726888602004'
        let requestRedirectUrl = 'http://2.21.137.156:8080'
        axios.get(`/spi/UserData/getAuthToken?code=${code}&requestAppId=${requestAppId}&requestAppSecret=${requestAppSecret}&requestRedirectUrl=${requestRedirectUrl}`).then(res=>{
          if(res.data.code === 200){
            localStorage.setItem('cauthorization',res.data.data.accessToken)
            localStorage.setItem('userInfo',JSON.stringify(res.data.data.userInfo))
            next('/')
          }else{
            window.location.href = 'http://2.22.51.26:30002/oauth/authorize?client_id=CdZLrF07&client_secret=1914d35bc31ae09ae924a330a05e726888602004&redirect_uri=http://2.21.137.156:8080&response_type=code&scope=all'
          }
        }).catch(err=>{

        })
      }
    } else {
      next()
    }
  } else {
    next()
  }
})

export default router
