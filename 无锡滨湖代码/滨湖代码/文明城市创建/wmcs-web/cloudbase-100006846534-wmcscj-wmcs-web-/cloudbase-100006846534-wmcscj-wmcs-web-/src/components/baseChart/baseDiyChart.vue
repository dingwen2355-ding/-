<template>
    <!-- 为了更自定义式, 把updata放在父组件自行维护, 适合非一般范式图表 -->
    <div class="base-diy-charts-wapper" :class="{'nopadding': noPadding}">
        <div :id="id" :style="{width: '100%', height: '100%'}" />
    </div>
</template>
<script>
export default {
    props: {
        noPadding: {
            type: Boolean,
            default: false
        },
        chartData: {
            type: [Array, Object],
            default: () => {
                // 示例数据格式
                // let demoData = [ { name: 'label', value: 13 } ]
                return []
            }
        },
        // 定制化的参数
        // options: { type: Object, default: () => { return {} } },
        // upDataFuc
        upDataFuc: {
            type: Function,
            default: () => {
                return new Promise((res, rej) => {
                    res()
                })
            }
        }
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
    },
    methods: {
        initChart() {
            this.chart = this.$echarts.init(document.getElementById(this.id))
        },
        updataPie() {
            let option = this.upDataFuc()
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
.base-diy-charts-wapper{
    width: 100%;
    height: 100%;
    padding: 30px;
    &.nopadding{
        padding: 0;
    }
}
</style>
