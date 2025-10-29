// 一些公共的弹框组件,用于业务场景,非选择场景
import diaSelectDevice from './diaSelectDevice' // 选择视频设备
import diaSelectPoint from './diaSelectPoint' // 选择点位
import diaSelectDept from './diaSelectDept' // 选择责任部门
import diaSelectEventT from './diaSelectEventT' // 选择事件类型
import diaApprove from './diaApprove' // 通用审核弹框
import diaSelectVolunteerUser from './diaSelectVolunteerUser' // 志愿者选择弹框
import diaSelectUserCard from './diaSelectUserCard' // 选择用户:电子工牌
import diaSelectUser from './diaSelectUser' //选择政务微信用户
import diaSelectDeviceTree from './diaSelectDeviceTree' //选择设备组织

const baseDia = {
    install: function(Vue) {
        Vue.component('diaSelectDevice', diaSelectDevice)
        Vue.component('diaSelectPoint', diaSelectPoint)
        Vue.component('diaSelectDept', diaSelectDept)
        Vue.component('diaSelectEventT', diaSelectEventT)
        Vue.component('diaApprove', diaApprove)
        Vue.component('diaSelectVolunteerUser', diaSelectVolunteerUser)
        Vue.component('diaSelectUserCard', diaSelectUserCard)
        Vue.component('diaSelectUser', diaSelectUser)
        Vue.component('diaSelectDeviceTree', diaSelectDeviceTree)
    }
}
export default baseDia