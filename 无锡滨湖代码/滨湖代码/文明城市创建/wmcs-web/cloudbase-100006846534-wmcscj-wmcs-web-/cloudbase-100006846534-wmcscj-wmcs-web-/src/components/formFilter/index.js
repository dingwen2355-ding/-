import formDept from './formDept' // 部门

import selectCheckItem from './selectCheckItem' // 检查项目
import selectCheckPoints from './selectCheckPoints' // 事发点位
import selectDepts from './selectDepts' // 责任单位
import selectEventType from './selectEventType' 
import selectNode from './selectNode' 
import selectHandler from './selectHandler' 

const zrFormFilter = {
    install(Vue) {
        Vue.component('formDept', formDept)
        Vue.component('selectCheckItem', selectCheckItem)

        Vue.component('selectCheckPoints', selectCheckPoints)
        Vue.component('selectDepts', selectDepts)
        Vue.component('selectEventType', selectEventType)
        Vue.component('selectNode', selectNode)
        Vue.component('selectHandler', selectHandler)
    }
}
export default zrFormFilter
