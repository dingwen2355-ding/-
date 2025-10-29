import Vue from 'vue'
import VueRouter from 'vue-router'
import ShCityManagementStaff from '../views/shCityManagementStaff/ShCityManagementStaff'
import EventList from '../views/shCityManagementStaff/EventList'
Vue.use(VueRouter)

const routes = [
  { path: '/DaZhouCityManagementStaff', name: 'ShCityManagementStaff', component: ShCityManagementStaff },
  { path: '/EventList', name: 'EventList', component: EventList },
  {
    path: '/Login',
    name: 'Login',
    component: () => import('../views/Login/Login.vue')
  }
]

const router = new VueRouter({ routes })

router.beforeEach((to, from, next) => {
  if (to.path === '/') {
    next('/DaZhouCityManagementStaff')
  } else {
    next()
  }
})

export default router
