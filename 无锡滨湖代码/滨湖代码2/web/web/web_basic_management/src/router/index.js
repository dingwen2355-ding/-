import Vue from 'vue'
import VueRouter from 'vue-router'
import axios from 'axios'
import routers from '../router'
import { Encrypt } from '@/utils/crypto'
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
      // 事件汇聚
      {
        path: '/eventSink',
        name: 'eventSink',
        component: () => import('../views/eventSink/eventSink')
      },
      // 事件受理
      {
        path: '/handleEvent',
        name: 'handleEvent',
        component: () => import('../views/eventSink/handleEvent')
      },
      {
        path: '/perimeterAnalysis',
        name: 'perimeterAnalysis',
        component: () => import('../views/PerimeterAnalysis/perimeterAnalysis')
      },
      {
        path: '/taskSupplementaryEntry',
        name: '任务补录',
        component: () => import('../views/emergencyProcessReappearance/taskSupplementaryEntry/taskSupplementaryEntry.vue')
      },
      {
        path: '/processReproduction',
        name: '过程再现',
        component: () => import('../views/emergencyProcessReappearance/processReproduction/processReproduction.vue')
      },
      {
        path: '/trendAnalysis',
        name: '态势分析',
        component: () => import('../views/emergencySituationAnalysis/situationAnalysis/situationAnalysis.vue')
      },
      {
        path: '/rescueTeams',
        name: '救援队伍',
        component: () => import('../views/rescueTeams/rescueTeams')
      },
      {
        path: '/emergencyExpertManagement',
        name: '应急专家',
        component: () => import('../views/emergencyExpertManagement/EmergencyExpertManagement')
      },
      {
        path: '/siteManage',
        name: '场所管理',
        component: () => import('../views/baseDataManage/siteManage/siteManage')
      },
      {
        path: '/distributionPlace',
        name: '场所分布',
        component: () => import('../views/baseDataManage/distributionPlace/distributionPlace')
      },
      {
        path: '/VideoMonitoring',
        name: '视频监控',
        component: () => import('../views/VideoMonitoring/VideoMonitoring.vue')
      },
      {
        path: '/myPlan',
        name: '我的预案',
        component: () => import('../views/myPlan/myPlan')
      },
      {
        path: '/myPlanIn',
        name: '预案指挥体系',
        component: () => import('../views/myPlanIn/myPlan')
      },
      {
        path: '/planDisassembly',
        name: '预案拆解',
        component: () => import('../views/planDisassembly/myPlan')
      },
      {
        path: '/drillEventEntry',
        name: '信息接报',
        component: () => import('../views/emergencyDrill/drillEventEntry/drillEventEntry')
      },
      {
        path: '/eventManage',
        name: '事件管理',
        component: () => import('../views/emergencyDrill/drillEventEntry/eventManage')
      },
      // 事件处置
      {
        path: '/myAgent',
        name: 'myAgent',
        component: () => import('../views/myAgent/myAgent')
      },
      // 痛点难点
      {
        path: '/difficultPain',
        name: 'difficultPain',
        component: () => import('../views/difficultPain/difficultPain')
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
      },
      {
        path: '/applicationCapabilityEvaluation',
        name: 'applicationCapabilityEvaluation',
        component: () => import('../views/emergencyDrill/applicationCapabilityEvaluation/applicationCapabilityEvaluation')
      },
      {
        path: '/materialManage',
        name: 'materialManage',
        component: () => import('../views/materialManagement/materialManage/materialManage')
      },
      {
        path: '/materialUse',
        name: 'materialUse',
        component: () => import('../views/materialManagement/materialUse/materialUse')
      },
      {
        path: '/emergencyPlanNew',
        name: 'emergencyPlanNew',
        component: () => import('../views/EmergencyPlanNew/emergencyPlanNew')
      },
      {
        path: '/planResponse',
        name: 'planResponse',
        component: () => import('../views/PlanResponse/planResponse')
      },
      {
        path: '/dictionaryConfiguration',
        name: 'dictionaryConfiguration',
        component: () => import('../views/dictionaryConfiguration/DictionaryConfiguration')
      },
      {
        path: '/consultationNotice',
        name: 'consultationNotice',
        component: () => import('../views/consultationNotice/ConsultationNotice')
      },
      {
        path: '/notificationManager',
        name: 'notificationManager',
        component: () => import('../views/notificationManager/NotificationManager')
      },
      {
        path: '/myTasks',
        name: 'myTasks',
        component: () => import('../views/myTasks/MyTasks')
      },
      {
        path: '/riskDanger',
        name: 'riskDanger',
        component: () => import('../views/riskManagement/riskDanger/riskDanger')
      },
      {
        path: '/protectiveTarget',
        name: 'protectiveTarget',
        component: () => import('../views/riskManagement/protectiveTarget/protectiveTarget')
      },
      {
        path: '/standardSpecification',
        name: 'standardSpecification',
        component: () => import('../views/knowledgeBaseManagement/standardSpecification/standardSpecification')
      },
      {
        path: '/activityManagement',
        name: 'activityManagement',
        component: () => import('../views/activityManagement/activityManagement.vue')
      },
      {
        path: '/lawsAndRegulations',
        name: 'lawsAndRegulations',
        component: () => import('../views/knowledgeBaseManagement/lawsAndRegulations/lawsAndRegulations')
      },
      {
        path: '/historicalEvent',
        name: 'historicalEvent',
        component: () => import('../views/knowledgeBaseManagement/historicalEvent/historicalEvent')
      },
      {
        path: '/classicCaseQuery',
        name: 'classicCaseQuery',
        component: () => import('../views/knowledgeBaseManagement/historicalEvent/historicalEvent')
      },
      {
        path: '/lawsAndRegulationsDatabase',
        name: 'lawsAndRegulationsDatabase',
        component: () => import('../views/knowledgeBaseManagement/lawsAndRegulations/lawsAndRegulations')
      },
      {
        path: '/emergencyPlanLibrary',
        name: 'emergencyPlanLibrary',
        component: () => import('../views/EmergencyPlanNew/emergencyPlanNew')
      },
      {
        path: '/documentDatabase',
        name: 'documentDatabase',
        component: () => import('../views/knowledgeBaseManagement/standardSpecification/standardSpecification')
      },
      {
        path: '/dutyPeople',
        name: 'dutyPeople',
        component: () => import('../views/dutyManage/DutyPeople.vue')
      },
      {
        path: '/dutyTemplate',
        name: 'dutyTemplate',
        component: () => import('../views/dutyManage/DutyTemplate.vue')
      },
      {
        path: '/dutyRole',
        name: 'dutyRole',
        component: () => import('../views/dutyManage/DutyRole.vue')
      },
      {
        path: '/dutyManage',
        name: 'dutyManage',
        component: () => import('../views/dutyManage/DutyManage.vue')
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
  return originalPush.call(this, location).catch((err) => err)
}

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
function loginOut() {
  localStorage.removeItem('cauthorization')
  localStorage.removeItem('userInfo')
  // window.open('http://2.22.51.26:30002/logout?logoutUrl=', '_self')
  // window.close()
  window.open('http://2.22.51.26:30002/logout?logoutUrl=', '_self')
  window.close()
  window.open('http://2.22.51.26:30002/oauth/authorize?client_id=Hhjvbg2U&client_secret=1383a5ddf71b635c3610fe4b26889a10d738d79b&redirect_uri=http://2.21.137.156:8084&response_type=code&scope=all')
}
router.beforeEach((to, from, next) => {
  console.log('跳转记录:', to.path, from.path)
  if (!configs.superControl) {
    if (to.path === '/') {
      if (localStorage.getItem('cauthorization')) {
        next('/drillEventEntry?state=信息接报')
      } else if (to.query.code) {
        let code = to.query.code
        let requestAppId = 'Hhjvbg2U'
        let requestAppSecret = '1383a5ddf71b635c3610fe4b26889a10d738d79b'
        let requestRedirectUrl = 'http://2.21.137.156:8084'
        axios.get(`/auth/UserData/getAuthToken?code=${code}&requestAppId=${requestAppId}&requestAppSecret=${requestAppSecret}&requestRedirectUrl=${requestRedirectUrl}`).then(res => {
          if (res.data.code === 200) {
            localStorage.setItem('cauthorization', res.data.data.accessToken)
            localStorage.setItem('userInfo', JSON.stringify(res.data.data.userInfo))
            next('/drillEventEntry?state=信息接报')
          } else {
            // window.open('http://2.22.51.26:30002/oauth/authorize?client_id=Hhjvbg2U&client_secret=1383a5ddf71b635c3610fe4b26889a10d738d79b&redirect_uri=http://2.21.137.156:8084&response_type=code&scope=all')
            window.location.href = 'http://2.22.51.26:30002/oauth/authorize?client_id=Hhjvbg2U&client_secret=1383a5ddf71b635c3610fe4b26889a10d738d79b&redirect_uri=http://2.21.137.156:8084&response_type=code&scope=all'
            // location.reload(); // 这里会重新加载页面
          }
        }).catch(err => { })
      } else {
        // window.open('http://2.22.51.26:30002/oauth/authorize?client_id=Hhjvbg2U&client_secret=1383a5ddf71b635c3610fe4b26889a10d738d79b&redirect_uri=http://2.21.137.156:8084&response_type=code&scope=all')
        window.location.href = 'http://2.22.51.26:30002/oauth/authorize?client_id=Hhjvbg2U&client_secret=1383a5ddf71b635c3610fe4b26889a10d738d79b&redirect_uri=http://2.21.137.156:8084&response_type=code&scope=all'
        // location.reload(); // 这里会重新加载页面
      }
    } else {
      if (localStorage.getItem('cauthorization')) {
        if (!sessionStorage.getItem('token')) {
          let userInfo = JSON.parse(localStorage.getItem('userInfo'))
          let params = {
            userName: userInfo.account,
            password: Encrypt('888888')
          }
          axios.post('/auth/user/login', params).then(res => {
            let { result, data } = res.data
            if (result === 0) {
              data = data[0]
              let modules = data.userMessage.modules
              // sessionStorage.setItem('token', data.token)
              sessionStorage.setItem('token', data.token)
              sessionStorage.setItem('roleId', data.userMessage.roleId)
              sessionStorage.setItem('user', data.userMessage.trueName)
              sessionStorage.setItem('phone', data.userMessage.phone)
              sessionStorage.setItem('precinctTreeId', data.userMessage.precinctTreeId)
              sessionStorage.setItem('streetId', data.userMessage.streetId)
              sessionStorage.setItem('streetName', data.userMessage.streetName)
              sessionStorage.setItem('roleName', data.userMessage.roleName)
              // sessionStorage.setItem('newDepartmentId', data.userMessage.precinctTreeId)
              if (data.userMessage.departmentName) {
                sessionStorage.setItem('departmentName', data.userMessage.departmentName)
                sessionStorage.setItem('departmentId', data.userMessage.departmentId)
              } else {
                sessionStorage.setItem('departmentName', '')
                sessionStorage.setItem('departmentId', '')
              }
              sessionStorage.setItem('departmentId', data.userMessage.departmentId)
              sessionStorage.setItem('userId', data.userMessage.userId)
              sessionStorage.setItem('userName', data.userMessage.userName)
              sessionStorage.setItem('region', data.userMessage.region)
              sessionStorage.setItem('menu', JSON.stringify(modules))
              sessionStorage.setItem('level', data.userMessage.roles[0].level)
              sessionStorage.setItem('userLevel', data.userMessage.userLevel)
              sessionStorage.setItem('stockId', data.userMessage.stockId)
              let menuIds = []
              modules.forEach((v) => {
                menuIds.push(v.id)
              })
              sessionStorage.setItem('menuIds', JSON.stringify(menuIds))
              // 为菜单设置权限
              let firstMenu // 普通角色登陆后访问的第一个菜单

              configs.menuList.forEach((e) => {
                e.isShow = false
                if (e.menus) {
                  let isShow = false
                  e.menus.forEach((se) => {
                    se.isShow = menuIds.includes(se.id)
                    if (se.isShow) {
                      isShow = true
                      if (!firstMenu) {
                        firstMenu = se
                      }
                    }
                  })
                  if (isShow) {
                    e.isShow = true
                  }
                } else {
                  e.isShow = menuIds.includes(e.id)
                  if (e.isShow && !firstMenu) {
                    firstMenu = e
                  }
                }
              })
              sessionStorage.setItem('menuList', JSON.stringify(configs.menuList))
              // 根据菜单权限跳转
              if (firstMenu) {
                let path = ''
                let v = false
                configs.menuList.some((e, index) => {
                  if (e.isShow) {
                    e.menus.some((item) => {
                      if (item.isShow) {
                        path = item.path
                        v = true
                        return true
                      }
                    })
                    return v
                  }
                })
                routers.push({ path: path })
                // next(path)
              } else {
                // this.$message.error('您无此系统权限，请联系管理员')
              }
              next('/drillEventEntry?state=信息接报')
            }
            next()
          })
        } else {
          next()
        }
      } else {
        loginOut()
      }
    }
  } else {
    next()
  }
})
export default router
