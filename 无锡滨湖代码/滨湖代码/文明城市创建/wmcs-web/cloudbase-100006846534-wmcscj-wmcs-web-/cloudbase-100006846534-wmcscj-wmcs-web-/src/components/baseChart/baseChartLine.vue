<template>
    <div class="base-pie-wapper">
        <div :id="id" :style="{width: '100%', height: '100%'}" />
    </div>
</template>
<script>
export default {
    props: {
        title: { type: String, default: '' },
        subtitle: { type: String, default: '' },
        chartData: {
            type: Array,
            default: () => {
                // 示例数据格式
                // let demoData = [ { name: 'label', value: 13 } ]
                return []
            }
        },
        // 定制化的参数
        options: { type: Object, default: () => { return {} } },
        // 是否为横向
        useY: { type: Boolean, default: false }
    },
    data() {
        let uuid = this.$get_uuid()
        return {
            id: uuid,
            chart: {}
        }
    },
    computed: {
        windowSizeChangeNum() {
            return this.$store.state.windowSizeChangeNum
        }
    },
    watch: {
        chartData() {
            this.updataPie()
        },
        windowSizeChangeNum() {
            this.reSize()
        }
    },
    mounted() {
        this.initChart()
        // this.updataPie()
    },
    methods: {
        initChart() {
            this.chart = this.$echarts.init(document.getElementById(this.id))
        },
        updataPie() {
            // 改为option必填:可以填option的key
            let option = this.options
            if (this.useY) {
                // 横向
                if (!option.yNumType || option.yNumType == 1) {
                    // 单线
                    option.yAxis.data = this.chartData.map(i => {
                        return i.name
                    })
                    option.series[0].data = this.chartData.map(i => {
                        return i.value
                    })
                } else {
                    // 多线
                    option.yAxis.data = this.chartData.name
                    option.series = this.chartData.value
                }
            } else {
                // 折线图的数据需要X,Y分离
                if (!option.yNumType || option.yNumType == 1) {
                    option.xAxis.data = this.chartData.map(i => {
                        return i.name
                    })
                    // 一条线
                    option.series[0].data = this.chartData.map(i => {
                        return i.value
                    })
                } else {
                    // 多条线: 等数据格式看看
                    // 多线
                    option.xAxis.data = this.chartData[0].name
                    option.series = this.chartData[0].value
                }
            }
            // 以传入的title覆盖
            if (this.title) option.title.text = this.title
            if (this.subtitle) option.title.subtext = this.subtitle
            // 定制化结束
            this.chart.setOption(option)
            this.$nextTick(() => {
                this.reSize()
            })
        },
        reSize() {
            this.chart.resize()
        }
    }
}
</script>
<style lang='scss' scoped>
.base-pie-wapper{
    width: 100%;
    height: 100%;
}
</style>
