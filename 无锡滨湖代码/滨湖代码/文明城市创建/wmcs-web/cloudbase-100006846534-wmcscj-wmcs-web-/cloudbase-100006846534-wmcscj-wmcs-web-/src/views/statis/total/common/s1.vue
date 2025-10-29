<template>
    <baseDiyChart :chartData="data1" :upDataFuc="upDataFucLeft" />
</template>
<script>
export default {
    data() {
        return {
            v_loading: false,
            dataOrigin: {},
            data1: [],
            options: {
                legend: {
                    data: ['事件工单', '整改情况'],
                    left: 'center',
                    top: 'top'
                },
                color: ['#3AA0FF', '#36CBCB', '#4DCB73', '#FAD337', '#D38DEB', '#F2637B', '#975FE4', '#88D1EA', '#EAA674', '#ACDF82', '#8DACEB', '#3FA674'],
                tooltip: {
                    trigger: 'axis',
                    axisPointer: { type: 'shadow' }
                },
                xAxis: {
                    type: 'category',
                    data: []
                },
                yAxis: [
                    { type: 'value', name: '事件工单', splitNumber: 5 },
                    { type: 'value', name: '整改情况', splitNumber: 5 }
                ],
                series: [
                    {
                        name: '事件工单',
                        type: 'bar',
                        smooth: true,
                        barWidth: 15,
                        data: []
                    },
                    {
                        name: '整改情况',
                        yAxisIndex: 1,
                        type: 'line',
                        smooth: true,
                        barWidth: 15,
                        data: []
                    }
                ],
                grid: {
                    left: 10,
                    right: 40,
                    top: 30,
                    bottom: 40,
                    containLabel: true
                },
                dataZoom: {
                    type: 'slider',
                    realtime: true,
                    startValue: 0,
                    endValue: 10
                }
            }
        }
    },
    mounted() {
        this.reSearch()
    },
    methods: {
        reSearch(data) {
            data = {
                outAmountSource: [],
                profitPercentSource: [],
                outAmountDate: [
                    { 'name': '2023-01-01', 'value': Math.round(Math.random() * 100) + 10 },
                    { 'name': '2023-01-02', 'value': Math.round(Math.random() * 100) + 10 },
                    { 'name': '2023-01-03', 'value': Math.round(Math.random() * 100) + 10 },
                    { 'name': '2023-01-04', 'value': Math.round(Math.random() * 100) + 10 },
                    { 'name': '2023-01-05', 'value': Math.round(Math.random() * 100) + 10 },
                    { 'name': '2023-01-06', 'value': Math.round(Math.random() * 100) + 10 },
                    { 'name': '2023-01-07', 'value': Math.round(Math.random() * 100) + 10 },
                    { 'name': '2023-01-08', 'value': Math.round(Math.random() * 100) + 10 },
                    { 'name': '2023-01-09', 'value': Math.round(Math.random() * 100) + 10 },
                    { 'name': '2023-01-10', 'value': Math.round(Math.random() * 100) + 10 },
                    { 'name': '2023-01-11', 'value': Math.round(Math.random() * 100) + 10 },
                    { 'name': '2023-01-12', 'value': Math.round(Math.random() * 100) + 10 },
                    { 'name': '2023-01-13', 'value': Math.round(Math.random() * 100) + 10 },
                    { 'name': '2023-01-14', 'value': Math.round(Math.random() * 100) + 10 },
                    { 'name': '2023-01-15', 'value': Math.round(Math.random() * 100) + 10 },
                    { 'name': '2023-01-16', 'value': Math.round(Math.random() * 100) + 10 },
                    { 'name': '2023-01-17', 'value': Math.round(Math.random() * 100) + 10 },
                    { 'name': '2023-01-18', 'value': Math.round(Math.random() * 100) + 10 },
                    { 'name': '2023-01-19', 'value': Math.round(Math.random() * 100) + 10 },
                    { 'name': '2023-01-20', 'value': Math.round(Math.random() * 100) + 10 },
                    { 'name': '2023-01-21', 'value': Math.round(Math.random() * 100) + 10 },
                    { 'name': '2023-01-22', 'value': Math.round(Math.random() * 100) + 10 },
                    { 'name': '2023-01-23', 'value': Math.round(Math.random() * 100) + 10 },
                    { 'name': '2023-01-24', 'value': Math.round(Math.random() * 100) + 10 },
                    { 'name': '2023-01-25', 'value': Math.round(Math.random() * 100) + 10 },
                    { 'name': '2023-01-26', 'value': Math.round(Math.random() * 100) + 10 },
                    { 'name': '2023-01-27', 'value': Math.round(Math.random() * 100) + 10 },
                    { 'name': '2023-01-28', 'value': Math.round(Math.random() * 100) + 10 },
                    { 'name': '2023-01-29', 'value': Math.round(Math.random() * 100) + 10 }
                ],
                profitPercentDate: [
                    { 'name': '2023-01-01', 'value': Math.round(Math.random() * 50) + 10 },
                    { 'name': '2023-01-02', 'value': Math.round(Math.random() * 50) + 10 },
                    { 'name': '2023-01-03', 'value': Math.round(Math.random() * 50) + 10 },
                    { 'name': '2023-01-04', 'value': Math.round(Math.random() * 50) + 10 },
                    { 'name': '2023-01-05', 'value': Math.round(Math.random() * 50) + 10 },
                    { 'name': '2023-01-06', 'value': Math.round(Math.random() * 50) + 10 },
                    { 'name': '2023-01-07', 'value': Math.round(Math.random() * 50) + 10 },
                    { 'name': '2023-01-08', 'value': Math.round(Math.random() * 50) + 10 },
                    { 'name': '2023-01-09', 'value': Math.round(Math.random() * 50) + 10 },
                    { 'name': '2023-01-10', 'value': Math.round(Math.random() * 50) + 10 },
                    { 'name': '2023-01-11', 'value': Math.round(Math.random() * 50) + 10 },
                    { 'name': '2023-01-12', 'value': Math.round(Math.random() * 50) + 10 },
                    { 'name': '2023-01-13', 'value': Math.round(Math.random() * 50) + 10 },
                    { 'name': '2023-01-14', 'value': Math.round(Math.random() * 50) + 10 },
                    { 'name': '2023-01-15', 'value': Math.round(Math.random() * 50) + 10 },
                    { 'name': '2023-01-16', 'value': Math.round(Math.random() * 50) + 10 },
                    { 'name': '2023-01-17', 'value': Math.round(Math.random() * 50) + 10 },
                    { 'name': '2023-01-18', 'value': Math.round(Math.random() * 50) + 10 },
                    { 'name': '2023-01-19', 'value': Math.round(Math.random() * 50) + 10 },
                    { 'name': '2023-01-20', 'value': Math.round(Math.random() * 50) + 10 },
                    { 'name': '2023-01-21', 'value': Math.round(Math.random() * 50) + 10 },
                    { 'name': '2023-01-22', 'value': Math.round(Math.random() * 50) + 10 },
                    { 'name': '2023-01-23', 'value': Math.round(Math.random() * 50) + 10 },
                    { 'name': '2023-01-24', 'value': Math.round(Math.random() * 50) + 10 },
                    { 'name': '2023-01-25', 'value': Math.round(Math.random() * 50) + 10 },
                    { 'name': '2023-01-26', 'value': Math.round(Math.random() * 50) + 10 },
                    { 'name': '2023-01-27', 'value': Math.round(Math.random() * 50) + 10 },
                    { 'name': '2023-01-28', 'value': Math.round(Math.random() * 50) + 10 },
                    { 'name': '2023-01-29', 'value': Math.round(Math.random() * 50) + 10 }
                ]
            }
            this.dataOrigin = data
            // 经营利润分析
            let arr1 = []
            let arr2 = []
            arr1 = this.dataOrigin.outAmountDate // 出库金额-日期
            arr2 = this.dataOrigin.profitPercentDate // 利润率-日期
            let y_config = this.$chartDY_number(arr1.map(i => { return i.value }), arr2.map(i => { return i.value }))
            this.options.yAxis[0].min = y_config[0]
            this.options.yAxis[0].max = y_config[1]
            this.options.yAxis[0].interval = (y_config[1] - y_config[0]) / 5
            this.options.yAxis[1].min = y_config[2]
            this.options.yAxis[1].max = y_config[3]
            this.options.yAxis[1].interval = (y_config[3] - y_config[2]) / 5
            this.data1 = [arr1, arr2]
        },
        upDataFucLeft() {
            this.options.xAxis.data = this.data1[0].map(i => { return i.name })
            this.options.series[0].data = this.data1[0].map(i => { return i.value })
            this.options.series[1].data = this.data1[1].map(i => { return i.value })
            return this.options
        }
    }
}
</script>
