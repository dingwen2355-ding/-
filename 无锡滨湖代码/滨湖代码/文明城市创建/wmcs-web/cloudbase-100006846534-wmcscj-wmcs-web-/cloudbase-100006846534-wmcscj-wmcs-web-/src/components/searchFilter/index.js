import changeButton from './changeButton' // 按钮组
import normalCheckAll from './normalCheckAll' // 通用多选带全选组件
import normalLabSearch from './normalLabSearch' // lab为下拉框的选择搜索
import normalSearch from './normalSearch' // 通用关键词搜索
import normalToF from './normalToF' // 通用单状态勾选是或否
import normalSelect from './normalSelect' // 通用下拉选择
import normalEnum from './normalEnum' // 通用枚举类选择
import dateMSelectM from './dateMSelectM' // 通用选择时间范围
import searchTimeTagA from './searchTimeTagA' // 时间标签A

// ==============业务
import searchDept from './searchDept' // 选择部门
import searchRegionDept from './searchRegionDept'   //选择街道部门
import searchEventType from './searchEventType' // 选择事件类型
import searchRegion from './searchRegion' // 选择区划
import searchRole from './searchRole' //选择角色
import searchRoleM from './searchRoleM' //选择角色(多选)
import searchCheckPointsType from './searchCheckPointsType' // 选择检查点位类型
import searchIndicatorType from './searchIndicatorType' //指标类型
import searchUnityDept from './searchUnityDept' // 选择同步组织
import searchAssessCycle from './searchAssessCycle' //选择考核重中之重走起
import pointEventType from './pointEventType' // 选择点位类型
import searchTimeconfig from './searchTimeconfig' // 选择时间配置

// 异步搜索组件
import asyncSearchPoint from './asyncSearchPoint' // 点位

// ==============字典
import dirtAll from './dirt/dirtAll'
import dirtCheckUserType from './dirt/dirtCheckUserType'
import dirtCivilizationSelectLevel from './dirt/dirtCivilizationSelectLevel'
import dirtCivilizationCompanyType from './dirt/dirtCivilizationCompanyType'
import dirtCivilizationTownType from './dirt/dirtCivilizationTownType'
import dirtCivilizationSchoolAttribute from './dirt/dirtCivilizationSchoolAttribute'
import dirtCivilizationSchoolType from './dirt/dirtCivilizationSchoolType'
import dirtCivilizationAdvancedType from './dirt/dirtCivilizationAdvancedType'
import dirtRecommendType from './dirt/dirtRecommendType'
import dirtVoluTeamType from './dirtVoluTeamType'
import dirtVoluActiveType from './dirtVoluActiveType'
import dirtEventSource from './dirt/dirtEventSource'
import dirtWorkAlarmType from './dirt/dirtWorkAlarmType'
import dirtCheckTaskStatus from './dirt/dirtCheckTaskStatus'

const biBaseSearchFilter = {
    install(Vue) {
        Vue.component('changeButton', changeButton)
        Vue.component('normalCheckAll', normalCheckAll)
        Vue.component('normalLabSearch', normalLabSearch)
        Vue.component('normalSearch', normalSearch)
        Vue.component('normalToF', normalToF)
        Vue.component('normalSelect', normalSelect)
        Vue.component('normalEnum', normalEnum)
        Vue.component('dateMSelectM', dateMSelectM)
        Vue.component('searchTimeTagA', searchTimeTagA)
        // 业务组件
        Vue.component('searchDept', searchDept)
        Vue.component('searchRegionDept', searchRegionDept)
        Vue.component('searchEventType', searchEventType)
        Vue.component('searchRegion', searchRegion)
        Vue.component('searchRole', searchRole)
        Vue.component('searchRoleM', searchRoleM)
        Vue.component('searchCheckPointsType', searchCheckPointsType)
        Vue.component('searchIndicatorType', searchIndicatorType)
        Vue.component('searchUnityDept', searchUnityDept)
        Vue.component('searchAssessCycle', searchAssessCycle)
        Vue.component('pointEventType', pointEventType)
        Vue.component('searchTimeconfig', searchTimeconfig)
        // 异步搜索组件
        Vue.component('asyncSearchPoint', asyncSearchPoint)
        // 字典组件
        Vue.component('dirtAll', dirtAll) // 通用字典
        Vue.component('dirtCheckUserType', dirtCheckUserType) // 人员类型
        Vue.component('dirtCivilizationSelectLevel', dirtCivilizationSelectLevel)   //评选层级
        Vue.component('dirtCivilizationCompanyType', dirtCivilizationCompanyType) // 文明单位类型
        Vue.component('dirtCivilizationTownType', dirtCivilizationTownType) //文明村镇类型
        Vue.component('dirtCivilizationSchoolAttribute', dirtCivilizationSchoolAttribute)   //文明校园属性
        Vue.component('dirtCivilizationSchoolType', dirtCivilizationSchoolType) //文明校园类型
        Vue.component('dirtCivilizationAdvancedType', dirtCivilizationAdvancedType) //文明先进人物类型
        Vue.component('dirtRecommendType', dirtRecommendType) //典型先进人物推荐类型
        Vue.component('dirtVoluTeamType', dirtVoluTeamType) // 志愿团队类型
        Vue.component('dirtVoluActiveType', dirtVoluActiveType) // 志愿活动类型
        Vue.component('dirtEventSource', dirtEventSource) //事件来源
        Vue.component('dirtWorkAlarmType', dirtWorkAlarmType)  //AI告警类型
        Vue.component('dirtCheckTaskStatus', dirtCheckTaskStatus)   //巡查任务状态
    }
}
export default biBaseSearchFilter