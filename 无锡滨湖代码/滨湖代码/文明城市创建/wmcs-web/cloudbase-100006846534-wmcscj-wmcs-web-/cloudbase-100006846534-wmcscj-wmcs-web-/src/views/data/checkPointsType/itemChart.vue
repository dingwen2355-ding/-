<template>
    <!-- 视频设备选择弹框 -->
    <el-dialog :title="(task.name || '') + '-类型指数'" :visible.sync="show" append-to-body :width="rowWidth" :before-close="cancel">
        <div class="dia-bd-hei">
            <div class="show-flex-r j-e">
                <!-- <searchTimeTagA ref="timetag" @change="changeDateType" /> -->
                <el-radio-group v-model="dateType" size="mini" @change="changeDateType">
                    <el-radio-button label="day">每日</el-radio-button>
                    <el-radio-button label="week">每周</el-radio-button>
                    <el-radio-button label="month">每月</el-radio-button>
                </el-radio-group>
            </div>
            <div style="width: 100%;height: 50vh;">
                <baseDiyChart noPadding :chartData="data1" :upDataFuc="upDataFucLeft" />
            </div>
        </div>
        <div slot="footer" class="dialog-footer">
            <t-button ghost class="margin-right-20" @click="cancel">取消</t-button>
        </div>
    </el-dialog>
</template>
<script>
const moment = require('moment')
export default {
    props: {
        rowWidth: {
            // 整体宽度设置
            type: String,
            default: '95%'
        },
        show: {
            type: Boolean,
            default: false
        },
        task: {
            type: Object,
            default: () => {
                return {}
            }
        }
    },
    data() {
        return {
            dateType: 'month',
            data1: [],
            option1: {
                // color: ['#f39393', '#b2db9e', '#12908c'],
                legend: {
                    data: ['问题总数', '已整改']
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: { type: 'shadow' },
                    // formatter: (v) => {
                    //     let lv = (~~(10000 * (v[1].data / (v[0].data || 1)))) / 100
                    //     return `
                    //         <ul>
                    //             <li>问题总数: ${v[0].data}</li>
                    //             <li>已整改: ${v[1].data}</li>
                    //             <li>整改率: ${lv} %</li>
                    //         </ul>
                    //     `
                    // },
                    // formatter: '{a} <br /> {b} : {d}%'
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: ['2022-12', '2023-01', '2023-02', '2023-02', '2023-04', '2023-05', '2023-06']
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name: '问题总数',
                        data: [150, 230, 224, 218, 135, 147, 260],
                        type: 'line',
                        smooth: true
                        // areaStyle: {}
                    },
                    {
                        name: '已整改',
                        data: [130, 210, 224, 208, 125, 137, 250],
                        type: 'bar',
                        barWidth: 20,
                        smooth: true
                        // areaStyle: {}
                    }
                ],
                // dataZoom: {
                //     type: 'slider',
                //     realtime: true,
                //     startValue: 0,
                //     endValue: 10
                // }
            }
        }
    },
    mounted() {
        let _this = this
        // 这里看是否需要初始化一些条件:如果不想等组件,可以直接去调用getList方法,组件后台自己装显示
        // 看后续需要扩展,这里先这样
        this.$nextTick(() => {
            // dia里面的内容会在mounted之后,延迟一下
            // setTimeout(() => {
            //     _this.startSearch()
            // }, 500)
            _this.reSearch2();
        })
    },
    methods: {
        cancel() {
            this.$emit('cancel')
        },
        changeDateType(v) {
            this.reSearch2()
        },
        reSearch(data) {
            // 日: 按小时算今天从0点开始
            // 周： 过去7天按天算
            // 月： 过去
            let lineArr = ['类型指数']
            this.option1.legend.data = lineArr
            let timeTag = this.$refs.timetag.getSearchValue()
            if (timeTag.startTime == timeTag.endTime) {
                let nowHour = moment().format('DD日 HH时')
                let nowDay = moment().format('DD日 ')
                let dayArr = [
                    nowDay + '00',
                    nowDay + '01', nowDay + '02', nowDay + '03', nowDay + '04', nowDay + '05', nowDay + '06', nowDay + '07', nowDay + '08',
                    nowDay + '09', nowDay + '10', nowDay + '11', nowDay + '12', nowDay + '13', nowDay + '14', nowDay + '15', nowDay + '16',
                    nowDay + '17', nowDay + '18', nowDay + '19', nowDay + '20', nowDay + '21', nowDay + '22', nowDay + '23'
                ]
                let dayX = dayArr.filter(i => { return i <= nowHour }).map(k => { return k + '时' })
                this.option1.xAxis.data = dayX // 横轴
                this.option1.series = lineArr.map(i => {
                    return {
                        name: i,
                        data: dayX.map(i => {
                            return 0
                            // return Math.floor(Math.random() * (60)) + 20
                        }),
                        type: 'line',
                        smooth: true
                    }
                })
                this.data1 = dayX
            } else {
                let timeArr = []
                let s_1 = timeTag.startTime
                while (s_1 <= timeTag.endTime) {
                    timeArr.push(s_1)
                    s_1 = moment(s_1).add(1, 'days').format('YYYY-MM-DD')
                }
                timeArr = timeArr.filter(i => { return i<= moment().format('YYYY-MM-DD') })
                this.option1.xAxis.data = timeArr // 横轴
                this.option1.series = lineArr.map(i => {
                    return {
                        name: i,
                        data: timeArr.map(i => { return Math.floor(Math.random() * (10)) + 80 }),
                        type: 'line',
                        smooth: true
                    }
                })
                this.data1 = timeArr
            }
            this.$emit('change', this.dateType)
            return
        },
        reSearch2(){
            let lineArr = ['类型指数']
            if (this.dateType == "day") {
                let timeArr = []
                let s_1 = moment().subtract(6, 'days').format('YYYY-MM-DD')
                while (s_1 <= moment(new Date()).format('YYYY-MM-DD')) {
                    timeArr.push(s_1)
                    s_1 = moment(s_1).add(1, 'days').format('YYYY-MM-DD')
                }
                timeArr = timeArr.filter(i => { return i <= moment().format('YYYY-MM-DD') })
                this.option1.xAxis.data = timeArr // 横轴
                this.option1.series = lineArr.map(i => {
                    return {
                        name: i,
                        data: timeArr.map(i => { return Math.floor(Math.random() * (60)) + 20 }),
                        type: 'line',
                        smooth: true
                    }
                })
                this.data1 = timeArr;
            } else if (this.dateType == "week") {
                let timeArr = []
                const endTime = moment(moment().week(moment().week()).endOf('week').valueOf()).format('YYYY-MM-DD');
                for (let i = 3; i >= 0; i--) {
                    let end = moment(endTime).subtract(i * 7, 'days').format('YYYY-MM-DD')
                    let time = moment(end).subtract(6, 'days').format('YYYY-MM-DD') + "~" + end
                    timeArr.push(time)
                }
                this.option1.xAxis.data = timeArr // 横轴
                this.option1.series = lineArr.map(i => {
                    return {
                        name: i,
                        data: timeArr.map(i => { return Math.floor(Math.random() * (60)) + 20 }),
                        type: 'line',
                        smooth: true
                    }
                })
                this.data1 = timeArr;
            } else if (this.dateType == "month") {
                let timeArr = []
                for (let i = 5; i >= 0; i--) {
                    timeArr.push(moment(new Date()).subtract(i * 30, 'days').format('YYYY-MM'))
                }
                this.option1.xAxis.data = timeArr // 横轴
                this.option1.series = lineArr.map(i => {
                    return {
                        name: i,
                        data: timeArr.map(i => { return Math.floor(Math.random() * (60)) + 20 }),
                        type: 'line',
                        smooth: true
                    }
                })
                this.data1 = timeArr;
            }
        },
        upDataFucLeft() {
            return this.option1
        }
    }
}
</script>
