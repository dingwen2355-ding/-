// 统计报表组件
import baseDiyChart from './baseDiyChart' // 通用自定义报表
import baseChartLine from './baseChartLine'

const baseChart = {
    install: function(Vue) {
        Vue.component('baseDiyChart', baseDiyChart)
        Vue.component('baseChartLine', baseChartLine)
    }
}
export default baseChart
