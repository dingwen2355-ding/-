// 搜索条件api: 本API是约定好所有需要后端请求的搜索条件API
import { http } from '../network/request'
import { api } from '../config'
import { pageDevice } from './data/device'

// 部门下拉
export function getDeptList() {
    return http({
        method: 'post',
        url: api + `/system/dept/list`,
        data: {}
    })
}

// 部门下拉
export function getRegionDeptList() {
    return http({
        method: 'post',
        url: api + `/system/dept/region`,
        data: {}
    })
}

// 区划下拉
export function getRegionList() {
    return http({
        method: 'post',
        url: api + `/system/region/list`,
        data: {}
    })
}

// 角色下拉
// 区划下拉
export function getRoleList() {
    return http({
        method: 'post',
        url: api + `/system/role/page`,
        data: {
            pageNum: 1,
            pageSize: 1000
        }
    })
}

// 设备组织下拉
export function getDeviceList() {
    return pageDevice()
    // return http({
    //     method: 'post',
    //     url: api + `/civilization/device/list`,
    //     data: {}
    // })
}

// 点位类型下拉
export function getCheckPointsTypeList() {
    return http({
        method: 'post',
        url: api + `/civilization/checkPointsType/page`,
        data: {
            pageNum: 1,
            pageSize: 1000
        }
    })
}

// 指标类型下拉
export function getIndicatorTypeList() {
    return http({
        method: 'post',
        url: api + `/civilization/indicatorType/page`,
        data: {
            pageNum: 1,
            pageSize: 1000
        }
    })   
}

// 事件类型下拉
export function getEventTypeList() {
    return http({
        method: 'post',
        url: api + `/civilization/eventType/list`,
        data: {}
    })
}

// 根据key查询字典值
export function getDictData(key) {
    return http({
        method: 'get',
        url: api + `/system/dictData/type/${key}`
    })
}

// 所有字典
export function getAllDictType() {
    // 数据格式参考
    // 后续所有单据状态会以字典形式返回
    // d.data['customer_type'] = [{ code: '1', value: '类型1' }]
    // 此处的列表结构比较特殊
    return http({
        method: 'get',
        url: api + '/system/dictData/all',
    })
}

// 查询一级事项类别
export function getEvenTypeOneList(key) {
    return http({
        method: 'post',
        url: api + `/civilization/eventType/getOneList`,
        key
    })
}

// 查询统一组织列表
export function getUnityDeptList() {
    return http({
        method: 'post',
        url: api + `/civilization/unify/dept`,
        data: {}
    })
}

// 查询时间配置列表
export function getTimeConfigList() {
    return http({
        method: 'post',
        url: api + `/civilization/timeConfig/page`,
        data: {
            pageNum: 1,
            pageSize: 1000
        }
    })
}

// 查询考核周期列表
export function getAssessCycleList() {
    return http({
        method: 'post',
        url: api + `/civilization/assessCycle/page`,
        data: {
            pageNum: 1,
            pageSize: 50
        }
    })
}

// 字典:督察人员
export function dirtCheckUserType() { return getDictData('data_inspector_type') }

// 字典:文明单位类型
export function dirtCivilizationCompanyType() { return getDictData('data_civilization_company_type') }

// 字典:文明村镇类型
export function dirtCivilizationTownType() { return getDictData('data_civilization_town_type') }

// 字典:文明校园性质
export function dirtCivilizationSchoolAttribute() { return getDictData('data_civilization_school_attribute') }

// 字典:文明校园类型
export function dirtCivilizationSchoolType() { return getDictData('data_civilization_school_type') }

// 字典：评选层级
export function dirtCivilizationSelectLevel() { return getDictData('data_civilization_select_level')}

// 字典：典型先进人物类型
export function dirtCivilizationAdvancedType() { return getDictData('data_civilization_advanced_type')}

// 字典:典型先进人物推荐类型
export function dirtRecommendType() { return getDictData('data_recommend_type') }

// 字典: 人员类型
export function dirtInspectorType() { return getDictData('data_inspector_type') }

// 字典: 志愿团队类型
export function dirtVolunteerTeamType() { return getDictData('data_volunteer_team_type') }

// 字典: 志愿活动类型
export function dirtVolunteerActiveType() { return getDictData('data_volunteer_activity_type') }

// 字典：事件来源类型
export function dirtEventSource() { return getDictData('event_source') }

// 字典：告警类型
export function dirtAlarmType() { return getDictData('work_alarm_type') }

// 字典：巡查任务状态
export function dirtCheckTask() { return getDictData('work_check_task_status') }

// 枚举: 获取所有枚举类列表
export function getAllEnumType() {
    return http({
        method: 'get',
        url: api + `/system/common/menus`
    })
}
