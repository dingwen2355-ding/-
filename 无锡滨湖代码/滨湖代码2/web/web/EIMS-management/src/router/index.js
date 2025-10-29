import Vue from 'vue'
import VueRouter from 'vue-router'
import UserManager from '../views/SystemManagement/user/UserManager'
import RoleManager from '../views/SystemManagement/role/RoleManager'
import ModuleManager from '../views/SystemManagement/module/ModuleManager'

Vue.use(VueRouter)

const routes = [
  { path: '/', name: 'login', component: () => import('../Login') },
  { path: '/fileView', name: 'fileView', component: () => import('../views/fileView/FileView') },
  {
    path: '/main',
    name: 'main',
    component: () => import('../Main.vue'),
    children: [
      // 系统管理 SystemManagement
      { path: '/user', name: 'user', component: UserManager },
      { path: '/role', name: 'role', component: RoleManager },
      { path: '/module', name: 'module', component: ModuleManager },
      {
        path: '/organizationManagement',
        name: 'organizationManagement',
        component: () => import('../views/SystemManagement/organizationManagement/organizationNew')
      },

      // 预案管理 BudgetManagement
      {
        path: '/myPlan',
        name: 'myPlan',
        component: () => import('../views/BudgetManagement/myPlan/myPlan')
      },
      {
        path: '/emergencyPlan',
        name: 'emergencyPlan',
        component: () => import('../views/BudgetManagement/EmergencyPlan/emergencyPlan')
      },
      {
        path: '/planDigitalProcess',
        name: 'planDigitalProcess',
        component: () => import('../views/BudgetManagement/planDigitalProcess/planDigitalProcess')
      },

      // 物资管理 MaterialManagement
      {
        path: '/storage',
        name: 'storage',
        component: () => import('../views/MaterialManagement/storage/storageManage/StorageManage')
      },
      {
        path: '/storageOutInDetail',
        name: 'storageOutInDetail',
        component: () => import('../views/MaterialManagement/storage/storageManage/components/StorageOutInDetail')
      },
      {
        path: '/storageOutIn',
        name: 'storageOutIn',
        component: () => import('../views/MaterialManagement/storage/storageManage/components/StorageOutInManage')
      },
      {
        path: '/storageInventory',
        name: 'storageInventory',
        component: () => import('../views/MaterialManagement/storage/storageManage/components/StorageInventory')
      },
      {
        path: '/materialList',
        name: 'materialList',
        component: () => import('../views/MaterialManagement/storage/materialList/MaterialList')
      },
      {
        path: '/MaterialAgreementReserve',
        name: 'MaterialAgreementReserve',
        component: () => import('../views/MaterialManagement/materialAgreementReserve/MaterialAgreementReserve')
      },
      {
        path: '/materialReserveLimit',
        name: 'materialReserveLimit',
        component: () => import('../views/MaterialManagement/materialReserveLimit/MaterialReserveLimit')
      },
      {
        path: '/materialWarehouse',
        name: 'materialWarehouse',
        component: () => import('../views/MaterialManagement/materialWarehouses/materialWarehouse')
      },

      // 系统监控 SystemMonitoring
      {
        path: '/configNacos',
        name: 'configNacos',
        component: () => import('../views/SystemMonitoring/configNacos/configNacos')
      },
      {
        path: '/gatewayRouting',
        name: 'gatewayRouting',
        component: () => import('../views/SystemMonitoring/gatewayRouting/gatewayRouting')
      },
      {
        path: '/InterfaceOverview',
        name: 'InterfaceOverview',
        component: () => import('../views/SystemMonitoring/InterfaceOverview/InterfaceOverview')
      },
      // 信息发布 InfoPublish
      {
        path: '/infoTemplate',
        name: 'infoTemplate',
        component: () => import('../views/InfoPublish/infoTemplate/InfoTemplate')
      },
      {
        path: '/alarmNews',
        name: 'alarmNews',
        component: () => import('../views/InfoPublish/alarmNews/alarmNews')
      },
      {
        path: '/informationRelease',
        name: 'informationRelease',
        component: () => import('../views/InfoPublish/informationRelease/informationRelease')
      },
      {
        path: '/AuditInformation',
        name: 'AuditInformation',
        component: () => import('../views/InfoPublish/informationRelease/AuditInformation')
      },
      {
        path: '/ReleaseWay',
        name: 'ReleaseWay',
        component: () => import('../views/InfoPublish/informationRelease/ReleaseWay')
      },
      {
        path: '/ReleaseLog',
        name: 'ReleaseLog',
        component: () => import('../views/InfoPublish/informationRelease/ReleaseLog')
      },
      {
        path: '/StateInspection',
        name: 'StateInspection',
        component: () => import('../views/InfoPublish/informationRelease/StateInspection')
      },
      {
        path: '/DisasterInformationReporting',
        name: 'DisasterInformationReporting',
        component: () => import('../views/InfoPublish/informationRelease/DisasterInformationReporting')
      },
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  next()
})
export default router
