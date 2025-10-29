import baseKanban from './baseKanban.vue'
import baseKanbanTd from './baseKanbanTd.vue'
import baseKanbanInfo from './baseKanbanInfo.vue'
import baseInfoWapper from './baseInfoWapper.vue'
import baseSearchForm from './baseSearchForm.vue'
import baseSelectWapper from './baseSelectWapper.vue'
import baseUpFile from './upfile/baseUpFile'
import baseTab from './baseTab'
import baseFilterLine from './baseFilterLine'
import baseGoBack from './baseGoBack'
import baseQuill from './baseQuill' // 富文本编辑器
import baseInfoWapperA from './baseInfoWapperA' // 展示基本详情的看板
import baseInfoWapperAItemA from './baseInfoWapperAItemA' // 展示基本详情的看板
import baseImportExcel from './baseImportExcel'

const biBase = {
    install: function(Vue) {
        Vue.component('baseKanban', baseKanban)
        Vue.component('baseKanbanTd', baseKanbanTd)
        Vue.component('baseKanbanInfo', baseKanbanInfo)
        Vue.component('baseInfoWapper', baseInfoWapper)
        Vue.component('baseSearchForm', baseSearchForm)
        Vue.component('baseSelectWapper', baseSelectWapper)
        Vue.component('baseUpFile', baseUpFile)
        Vue.component('baseTab', baseTab)
        Vue.component('baseFilterLine', baseFilterLine)
        Vue.component('baseGoBack', baseGoBack)
        Vue.component('baseQuill', baseQuill)
        Vue.component('baseInfoWapperA', baseInfoWapperA)
        Vue.component('baseInfoWapperAItemA', baseInfoWapperAItemA)
        Vue.component('baseImportExcel', baseImportExcel)
    }
}
export default biBase
