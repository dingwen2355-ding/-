import Vue from 'vue'
import App from './App.vue'
import store from './store'
import router from './router'
import ElementUI from 'element-ui'
import 'babel-polyfill'
import './assets/style/base.css'
// 安装eleemntUi
import 'element-ui/lib/theme-chalk/index.css'
import './assets/style/elementui_td.scss'
// element-dialog-可拖动自定义命令
import '@/utils/element_ext/dialogDrag'
// element-dialog-可缩放自定义命令
import '@/utils/element_ext/dialogScal'

// 安装TDesign
import TDesign from 'tdesign-vue'
import 'tdesign-vue/es/style/index.css'
Vue.use(TDesign)

// 安装自定义全局CSS
import './assets/style/main.scss'
// 引入fontawesome
import './assets/font-awesome-4.7.0/css/font-awesome.min.css'
// 请求
import { http } from '@/network/request'

// 自定义全局组件
import biBase from '@/components/biBase'
Vue.use(biBase)
import biBaseSearchFilter from '@/components/searchFilter'
Vue.use(biBaseSearchFilter)
import zrFormFilter from '@/components/formFilter'
Vue.use(zrFormFilter)
import baseDia from '@/components/baseOpen'
Vue.use(baseDia)
import baseChart from '@/components/baseChart'
Vue.use(baseChart)

// js原生的toFixed使用的是银行家算法,四舍六入五留双算法,和正常的四舍五入有区别,所以写了一个正常的四舍五入
/* eslint-disable */
Number.prototype.zrFixed = function(v) {
    // 本来想用Math.round方案,但是普遍精度到6位的话担心遇到大数据精度的坑,干脆自己改原型
    let b = this.toString()
    // 不存在小数点
    if (b.indexOf('.') == -1) { return this.toFixed(v) }
    let num1 = b.split('.')
    // 小数不需要取舍
    if (num1[1].length <= v) { return this.toFixed(v) }
    let str_num = '0'
    // 四舍五入
    if (num1[1].slice(v, v + 1) > '4') {
        // 进位
        str_num = num1[0] + '.' + num1[1].slice(0, v) + '9'
    } else {
        // 补位
        str_num = num1[0] + '.' + num1[1].slice(0, v) + '1'
    }
    return parseFloat(str_num).toFixed(v)
}
/* eslint-enable */

Vue.prototype.$http = http
// 导入echarts
import echarts from 'echarts'
Vue.prototype.$echarts = echarts

// 设置一个uuid获取方法
Vue.prototype.$get_uuid = function() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        let r = Math.random() * 16 | 0
        let v = c == 'x' ? r : (r & 0x3 | 0x8)
        return v.toString(16)
    })
}

// 为报表设置一个双Y轴强制统一刻度的全局方法, 综合考虑不放在子组件, 而是在父组件实现
Vue.prototype.$chartDY_number = function(arr1, arr2) {
    // 复制两份
    let a1 = JSON.parse(JSON.stringify(arr1))
    let a2 = JSON.parse(JSON.stringify(arr2))
    a1.sort((a, b) => { return a - b })
    a2.sort((a, b) => { return a - b })
    // let min_a1 = a1.length > 0 ? (Math.floor(a1[0] / 10) * 10) : 0
    let min_a1 = a1.length > 0 ? (Math.floor(a1[0] * 0.9 / 10) * 10) : 0
    let max_a1 = a1.length > 0 ? (Math.floor((a1[a1.length - 1]) * 10 / 9.5)) : 0
    let min_a2 = a2.length > 0 ? (Math.floor(a2[0] * 0.9 / 10) * 10) : 0
    let max_a2 = a2.length > 0 ? (Math.floor((a2[a2.length - 1]) * 10 / 9.5)) : 0
    return [min_a1, max_a1, min_a2, max_a2]
}

// 设置一个全局的针对树形结构, 如果child为数组,且为空, 则删除key
Vue.prototype.$del_key = function(arr, keyName) {
    keyName = keyName || 'children'
    function zr_del_key(ar) {
        ar.forEach(a => {
            if (a[keyName] && a[keyName].length > 0) {
                zr_del_key(a[keyName])
            } else {
                delete a[keyName]
            }
        })
    }
    zr_del_key(arr)
}

// 设置两个全局的消息通知
Vue.prototype.$successMessage = function(msg) {
    window.queue_messageArr.push('_type_success_' + msg)
}

Vue.prototype.$errorMessage = function(msg) {
    window.queue_messageArr.push(msg)
}

// 全局过滤器
import * as vFilter from './filter'
Object.keys(vFilter).forEach((key) => { Vue.filter(key, vFilter[key]) })

// 过滤器同时注册为表格formatter
Object.keys(vFilter).forEach((key) => {
    Vue.prototype[key] = (row, colum, cellData) => {
        return vFilter[key](cellData)
    }
})

// 自定义手动触发窗口变更事件
Vue.prototype.$diyWindowResize = () => {
    // event.initEvent 已经被标准废除了,使用新标准:不考虑兼容IE
    let event = new Event('resize', { 'bubbles': true, 'cancelable': false })
    window.dispatchEvent(event)
}

Vue.config.productionTip = false
// 配置elementUI默认配置
ElementUI.Dialog.props.closeOnClickModal.default = false // 修改dialog默认不能通过点击模态框框关闭
ElementUI.Dialog.props.closeOnPressEscape.default = false // 修改dialog默认不能通过按下esc关闭
Vue.use(ElementUI)

// 权限按钮全局mixins
import powerMixins from './utils/powerMixins'
Vue.mixin(powerMixins)

// 定义全局队列
// 用于在token快到期时刷新token用, 防止同时多个请求, 用队列过滤
// 当token需要被刷新时挂起的请求队列
window.queue_refushTokenArr = []
// 优化当一个页面大量接口请求时, 大量的重复错误, 在500ms内同样的错误信息不重复弹出的队列
window.queue_messageArr = []

// 引入富文本编辑器
import VueQuillEditor from 'vue-quill-editor'
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'
Vue.use(VueQuillEditor)

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
