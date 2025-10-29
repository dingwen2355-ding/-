<template>
    <div class="w-100 statis-total-page" style="overflow-x: hidden;">
        <el-row :gutter="20">
            <el-col :span="6">
                <t-card style="height: 190px;" :bordered="false" hover-shadow>
                    <div class="card-title">
                        <span class="zr-link-p link-url" @click="toPath('/home/data/civilizationTown/list')">文明村镇</span>
                    </div>
                    <div class="show-flex-r a-c margin-bottom-20">
                        <div class="flex-1">
                            <span class="num-wapper">{{ count.civilizationTownCount }}</span>个
                        </div>
                        <div class="icon-wap">
                            <i class="el-icon-s-data" style="color: rgb(0, 82, 217);" />
                        </div>
                    </div>
                    <div>总计</div>
                </t-card>
            </el-col>
            <el-col :span="6">
                <t-card style="height: 190px;" :bordered="false" hover-shadow>
                    <div class="card-title">
                        <span class="zr-link-p link-url" @click="toPath('/home/data/civilizationCompany/list')">文明单位</span>
                    </div>
                    <div class="show-flex-r a-c margin-bottom-20">
                        <div class="flex-1">
                            <span class="num-wapper">{{ count.civilizationCompanyCount }}</span>个
                        </div>
                        <div class="icon-wap">
                            <i class="el-icon-s-data" style="color: rgb(0, 82, 217);" />
                        </div>
                    </div>
                    <div>总计</div>
                </t-card>
            </el-col>
            <el-col :span="6">
                <t-card style="height: 190px;" :bordered="false" hover-shadow>
                    <div class="card-title">
                        <span class="zr-link-p link-url" @click="toPath('/home/data/civilizationFamily/list')">文明家庭</span>
                    </div>
                    <div class="show-flex-r a-c margin-bottom-20">
                        <div class="flex-1">
                            <span class="num-wapper">{{ count.civilizationFamilyCount }}</span>个
                        </div>
                        <div class="icon-wap">
                            <i class="el-icon-s-data" style="color: rgb(0, 82, 217);" />
                        </div>
                    </div>
                    <div>总计</div>
                </t-card>
            </el-col>
            <el-col :span="6">
                <t-card style="height: 190px;" :bordered="false" hover-shadow>
                    <div class="card-title">
                        <span class="zr-link-p link-url" @click="toPath('/home/data/civilizationSchool/list')">文明校园</span>
                    </div>
                    <div class="show-flex-r a-c margin-bottom-20">
                        <div class="flex-1">
                            <span class="num-wapper">{{ count.civilizationSchoolCount }}</span>个
                        </div>
                        <div class="icon-wap">
                            <i class="el-icon-s-data" style="color: rgb(0, 82, 217);" />
                        </div>
                    </div>
                    <div>总计</div>
                </t-card>
            </el-col>
        </el-row>
        <el-row :gutter="20" class="margin-top-20">
            <el-col :span="6">
                <t-card style="height: 190px;" :bordered="false" hover-shadow>
                    <div class="card-title">
                        <span class="zr-link-p link-url"
                            @click="toPath('/home/data/civilizationAdvanced/list')">先进典型人物</span>
                    </div>
                    <div class="show-flex-r a-c margin-bottom-20">
                        <div class="flex-1">
                            <span class="num-wapper">{{ count.civilizationAdvancedCount }}</span>个
                        </div>
                        <div class="icon-wap">
                            <i class="el-icon-set-up" style="color: #409EFF;" />
                        </div>
                    </div>
                    <div>总计</div>
                </t-card>
            </el-col>
            <el-col :span="6">
                <t-card style="height: 190px;" :bordered="false" hover-shadow>
                    <div class="card-title">
                        <span class="zr-link-p link-url" @click="toPath('/home/data/checkPoints/list')">督察点位</span>
                    </div>
                    <div class="show-flex-r a-c margin-bottom-20">
                        <div class="flex-1">
                            <span class="num-wapper">{{ count.checkPointsCount }}</span>个
                        </div>
                        <div class="icon-wap">
                            <i class="el-icon-set-up" style="color: #409EFF;" />
                        </div>
                    </div>
                    <div>督察资源建设</div>
                </t-card>
            </el-col>
            <el-col :span="6">
                <t-card style="height: 190px;" :bordered="false" hover-shadow sty>
                    <div class="card-title">
                        <span class="zr-link-p link-url" @click="toPath('/home/system/dept/list')">责任单位</span>
                    </div>
                    <div class="show-flex-r a-c margin-bottom-20">
                        <div class="flex-1">
                            <span class="num-wapper">{{ count.deptCount }}</span>个
                        </div>
                        <div class="icon-wap">
                            <i class="el-icon-set-up" style="color: #409EFF;" />
                        </div>
                    </div>
                    <div>督察资源建设</div>
                </t-card>
            </el-col>
            <el-col :span="6">
                <t-card style="height: 190px;" :bordered="false" hover-shadow>
                    <div class="card-title">
                        <span class="zr-link-p link-url" @click="toPath('/home/data/device/list')">视频监控点位</span>
                    </div>
                    <div class="show-flex-r a-c margin-bottom-20">
                        <div class="flex-1">
                            <span class="num-wapper">{{ count.deviceChannelCount }}</span>个
                        </div>
                        <div class="icon-wap">
                            <i class="el-icon-location-information" style="color: #E6A23C;" />
                        </div>
                    </div>
                    <div>
                        应用智能分析设备建设
                    </div>
                </t-card>
            </el-col>
        </el-row>
        <el-row :gutter="20" class="margin-top-20">
            <el-col :span="12">
                <t-card title="告警信息" style="height: 700px;" :bordered="false" hover-shadow>
                    <el-table stripe :data="warnData" height="600">
                        <el-table-column label="告警设备ID" prop="channelId" min-width="100" />
                        <el-table-column label="告警类型" prop="type" min-width="100">
                            <template slot-scope="scope">
                                <div class="zr-link-p" style="cursor: pointer;" @click="toPath('/home/event/warning/list')">
                                    <dirtWorkAlarmType
                                        noLabel
                                        useType="form"
                                        allWidth="100%"
                                        disabled
                                        onlyShow
                                        :defaultVal="scope.row.type"
                                    />
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column label="告警描述" prop="content" min-width="100" />
                        <el-table-column label="告警时间" prop="createTime" width="170" />
                    </el-table>
                </t-card>
            </el-col>
            <el-col :span="12">
                <t-card title="监管数据" style="height: 700px;overflow: auto;" :bordered="false" hover-shadow>
                    <div id="chartJianGuan"></div>
                    <div id="chartJianGuan2"></div>
                </t-card>
            </el-col>
        </el-row>
        <!--- 标书文件中的指标总览
        -->
        <el-row :gutter="20" class="margin-top-20">
            <el-col :span="12">
                <t-card title="排摸列表" style="height: 700px;" :bordered="false" hover-shadow>
                    <el-table stripe :data="zyzData" height="600">
                        <el-table-column label="责任单位" prop="deptName" min-width="100">
                        </el-table-column>
                        <el-table-column label="点位类型" show-overflow-tooltip prop="typeName" min-width="200">
                        </el-table-column>
                        <el-table-column label="点位名称" prop="pointName" min-width="200" />
                        <el-table-column label="存在问题" prop="content" min-width="300">
                            <template slot-scope="scope">
                                <div v-if="scope.row.eventsource != 5" class="zr-link-p" style="cursor: pointer;"
                                    @click="toPath(`/home/analysis/all/_info?id=${scope.row.id}&flowinstanceid=${scope.row.flowinstanceid}&scope=all`)">
                                    {{ scope.row.content }}</div>
                                <div v-else> {{ scope.row.content }}</div>
                            </template>
                        </el-table-column>
                    </el-table>
                </t-card>
            </el-col>
            <el-col :span="12">
                <t-card title="指标总览" style="height: 700px;" :bordered="false" hover-shadow>
                    <el-row :gutter="20">
                        <el-col :span="6">
                            <div style="font-size: 16px;">总分</div>
                            <div style="font-size: 24px;margin-top: 15px;color: #409EFF;">{{ dtData.scoreTotal }}</div>
                        </el-col>
                        <el-col :span="6">
                            <div style="font-size: 16px;">点位总量</div>
                            <div style="font-size: 24px;margin-top: 15px;color: #409EFF;">{{ dtData.pointsCountTotal }}
                            </div>
                        </el-col>
                        <el-col :span="6">
                            <div style="font-size: 16px;">当期监测量</div>
                            <div style="font-size: 24px;margin-top: 15px;color: #409EFF;">{{ dtData.pointsCount }}</div>
                        </el-col>
                        <el-col :span="6">
                            <div style="font-size: 16px;">满意度</div>
                            <div style="font-size: 24px;margin-top: 15px;color: #409EFF;">99%</div>
                        </el-col>
                    </el-row>
                    <div id="chartJianGuan3"></div>
                </t-card>
            </el-col>
        </el-row>
    </div>
</template>
<script>
import { homeindex, shijianleixing, eventList, quotaInfo, pointsTypeList } from '@/api/home'
import { listVolunteerActivity } from '@/api/data/volunteerActivity'
import { listWorkWarn } from '@/api/work/workWarn'
import { all } from '@/api/workflow/workflow'
export default {
    data() {
        return {
            count: {
                checkPointsCount: 806,
                checkUserCount: 1,
                civilizationCompanyCount: 1,
                civilizationFamilyCount: 1,
                civilizationSchoolCount: 1,
                civilizationTownCount: 1,
                deptCount: 29,
                deviceChannelCount: 951
            },
            zyzData: [
                // { name: '文明滨湖 你我同行——青年志愿者积极参加“文明城市创建”活动', address: '滨湖区', count: 5, t: '2023-04-01至2023-04-15' },
                // { name: '创建惠民丨滨湖区：奋力打造文明滨湖新高地', address: '滨湖区', count: 5, t: '2023-04-01至2023-04-15' },
                // { name: '滨湖区召开文明城市创建工作推进会', address: '滨湖区', count: 5, t: '2023-04-28' },
                // { name: '“文明滨湖你我同行”——“青春有为‘骑’心协力”', address: '滨湖区', count: 5, t: '2023-03-23' }
            ],
            dtData: [],
            warnData: [
                // { name: '灵山钟楼祥符禅寺大殿后门', address: '暴露垃圾', count: 5, t: '2023-06-07 13:44:01' },
                // { name: '融创文旅城13号公寓楼顶鹰眼球机-FJ', address: '非机动车逆行', count: 5, t: '2023-06-07 13:44:01' },
                // { name: '湖滨饭店楼顶东北角高空球机-5101', address: '暴露垃圾', count: 5, t: '2023-06-07 13:44:01' },
                // { name: '湖滨饭店北门入口朝北双仓全-GZ01', address: '暴露垃圾', count: 5, t: '2023-06-07 13:44:01' },
                // { name: '金城路湖滨饭店路口-5101', address: '井盖丢失破损', count: 5, t: '2023-06-07 13:44:01' },
                // { name: '动物园出入口双仓细-L1', address: '暴露垃圾', count: 5, t: '2023-06-07 13:44:01' },
                // { name: '灵山东南角山坡基站高空球机-四期', address: '井盖丢失破损', count: 5, t: '2023-06-07 13:44:01' }
            ]
        }
    },
    mounted() {
        homeindex().then(res => {
            this.count = res.data
        })
        this.getZYZ()
        this.getDT()
        this.getWarn()
    },
    methods: {
        toPath(url) {
            this.$router.push(url)
        },
        getZYZ() {
            eventList().then(res => {
                this.zyzData = res.data
            })
            let _this = this
            // 事件类型
            shijianleixing()
                .then(res => {
                    let eventType = [];
                    let xData = [];
                    let yData = {};
                    var data = res.data.eventType;
                    let cycleData = res.data.cycleMap;
                    data.forEach(e => {
                        e.name = e.parentName;
                        e.value = e.count;
                        eventType.push(e.parentName);
                        yData[e.parentName] = []
                    })

                    let sum = 0
                    data.forEach(e => {
                        sum += e.count
                    })
                    let option = {
                        // title: {
                        //     text: "总数",
                        //     subtext: sum,
                        //     textStyle: {
                        //         color: "#000",
                        //         fontSize: 20,
                        //     },
                        //     subtextStyle: {
                        //         fontSize: 15,
                        //         color: ["#000"],
                        //     },
                        //     x: "center",
                        //     y: "center",
                        // },
                        grid: {
                            bottom: 200,
                            left: 100,
                            right: "10%",
                        },
                        legend: {
                            orient: "horizontal",
                            top: "bottom",
                            right: "15%",
                            textStyle: {
                                color: "#000",
                                fontSize: 12,
                            },
                            icon: "roundRect",
                            data: data,
                        },
                        series: [
                            // 主要展示层的
                            {
                                radius: ["30%", "61%"],
                                center: ["50%", "50%"],
                                type: "pie",
                                label: {
                                    normal: {
                                        show: true,
                                        formatter: "{d}%",
                                        textStyle: {
                                            fontSize: 20,
                                        },
                                        position: "outside",
                                    },
                                    emphasis: {
                                        show: true,
                                    },
                                },
                                labelLine: {
                                    normal: {
                                        show: true,
                                        length: 30,
                                        length2: 55,
                                    },
                                    emphasis: {
                                        show: true,
                                    },
                                },
                                name: "事件类型",
                                data: data,
                            },
                            // 边框的设置
                            {
                                radius: ["30%", "34%"],
                                center: ["50%", "50%"],
                                type: "pie",
                                label: {
                                    normal: {
                                        show: false,
                                    },
                                    emphasis: {
                                        show: false,
                                    },
                                },
                                labelLine: {
                                    normal: {
                                        show: false,
                                    },
                                    emphasis: {
                                        show: false,
                                    },
                                },
                                animation: false,
                                tooltip: {
                                    show: false,
                                },
                                data: [
                                    {
                                        value: 1,
                                        itemStyle: {
                                            color: "rgba(250,250,250,0.3)",
                                        },
                                    },
                                ],
                            },
                            {
                                name: "外边框",
                                type: "pie",
                                clockWise: false, //顺时加载
                                hoverAnimation: false, //鼠标移入变大
                                center: ["50%", "50%"],
                                radius: ["65%", "65%"],
                                label: {
                                    normal: {
                                        show: false,
                                    },
                                },
                                data: [
                                    {
                                        value: 9,
                                        name: "",
                                        itemStyle: {
                                            normal: {
                                                borderWidth: 2,
                                                borderColor: "#0b5263",
                                            },
                                        },
                                    },
                                ],
                            },
                        ],
                    };
                    let chart = this.$echarts.init(document.getElementById("chartJianGuan"))
                    chart.setOption(option)



                    let option2 = {
                        tooltip: {
                            trigger: "axis",
                            axisPointer: {
                                lineStyle: {
                                    color: "#57617B",
                                },
                            },
                        },
                        legend: {
                            icon: "rect",
                            itemWidth: 14,
                            itemHeight: 5,
                            itemGap: 13,
                            data: ["移动", "电信", "联通"],
                            right: "4%",
                            textStyle: {
                                fontSize: 12,
                                color: "#F1F1F3",
                            },
                        },
                        grid: {
                            left: "3%",
                            right: "4%",
                            bottom: "3%",
                            containLabel: true,
                        },
                        xAxis: [
                            {
                                type: "category",
                                boundaryGap: false,
                                axisLine: {
                                    lineStyle: {
                                        color: "#57617B",
                                    },
                                },
                                data: xData
                            }
                        ],
                        yAxis: [
                            {
                                type: "value",
                                name: "单位（%）",
                                axisTick: {
                                    show: false,
                                },
                                axisLine: {
                                    lineStyle: {
                                        color: "#57617B",
                                    },
                                },
                                axisLabel: {
                                    margin: 10,
                                    textStyle: {
                                        fontSize: 14,
                                    },
                                },
                                splitLine: {
                                    lineStyle: {
                                        color: "#57617B",
                                    },
                                },
                            },
                        ],
                        series: [

                        ],
                    };

                    for (const key in cycleData) {
                        if (Object.hasOwnProperty.call(cycleData, key)) {
                            const element = cycleData[key];
                            xData.push(key)
                            eventType.forEach(ee => {
                                let item = element.filter(s => s.parentName == ee);
                                if (item.length > 0) {
                                    yData[ee].push(item[0].count)
                                } else {
                                    yData[ee].push(0)
                                }
                            })
                        }
                    }
                    eventType.forEach(er => {
                        option2.series.push({
                            name: er,
                            type: "line",
                            smooth: true,
                            symbol: "circle",
                            symbolSize: 5,
                            showSymbol: false,
                            lineStyle: {
                                normal: {
                                    width: 1,
                                },
                            },
                            data: yData[er],
                        })
                    })

                    let chart2 = this.$echarts.init(document.getElementById("chartJianGuan2"))
                    chart2.setOption(option2)
                })
        },
        getDT() {
            let _this = this
            quotaInfo().then(res => {
                this.dtData = res.data
            })
            pointsTypeList({
                "pageNum": 1,
                "pageSize": 10
            })
                .then(res => {
                    let xData = [];
                    let yData = [99.9, 99.9, 99.8, 99.7, 99.6, 99.5, 99.5, 99.4, 99.4, 99.4];
                    var data = res.data.records;
                    data.forEach(e => {
                        xData.push(e.name);
                    })

                    let option = {
                        grid: {
                            left: "5%",
                            containLabel: true
                        },
                        xAxis: {
                            show: false,
                            type: "value",
                        },
                        yAxis: [
                            {
                                type: "category",
                                inverse: true,
                                axisLabel: {
                                    show: true,
                                    textStyle: {
                                        color: "#000",
                                    },
                                },
                                splitLine: {
                                    show: false,
                                },
                                axisTick: {
                                    show: false,
                                },
                                axisLine: {
                                    show: false,
                                },
                                data: xData,
                            },
                            {
                                type: "category",
                                inverse: true,
                                axisTick: "none",
                                axisLine: "none",
                                show: true,
                                axisLabel: {
                                    textStyle: {
                                        color: "#000",
                                        fontSize: "12",
                                    },
                                    formatter: function (value) {
                                        if (value >= 10000) {
                                            return (value / 10000).toLocaleString();
                                        } else {
                                            return value.toLocaleString();
                                        }
                                    },
                                },
                                data: yData,
                            },
                        ],
                        series: [
                            {
                                name: "金额",
                                type: "bar",
                                zlevel: 1,
                                barWidth: 20,
                                data: yData,
                                itemStyle: {
                                    normal: {
                                        color: "#409EFF",
                                        barBorderRadius: 30,
                                    },
                                },
                            },
                            {
                                name: "背景",
                                type: "bar",
                                barWidth: 20,
                                barGap: "-100%",
                                data: [100, 100, 100, 100, 100, 100, 100, 100, 100, 100],
                                itemStyle: {
                                    normal: {
                                        color: "rgba(24,31,68,1)",
                                        barBorderRadius: 30,
                                    },
                                },
                            },
                        ],
                    };
                    let chart = this.$echarts.init(document.getElementById("chartJianGuan3"))
                    chart.setOption(option)
                })
        },
        getWarn() {
            let _this = this
            // 告警信息
            listWorkWarn({
                pageNum: 1,
                pageSize: 11
            }).then(data => {
                _this.warnData = data.data.records
            })
        }
    }
}
</script>
<style scoped lang="scss">
.statis-total-page {
    .card-title {
        color: #333333;
        margin-bottom: 30px;
    }

    .num-wapper {
        font-size: 40px;
        color: rgba(0, 0, 0, 0.9);
    }

    .icon-wap {
        font-size: 50px;
    }

    .link-url {
        &:hover {
            text-decoration: underline;
        }
    }
}

#chartJianGuan {
    width: 100%;
    height: 300px;
}

#chartJianGuan2 {
    width: 100%;
    height: 300px;
}

#chartJianGuan3 {
    width: 100%;
    height: 500px;
}
</style>
