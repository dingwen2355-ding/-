<template>
    <t-card title="统计分析" :bordered="false">
        <div slot="actions">
            <div class="show-flex-r">
                <searchAssessCycle
                    ref="assessCycleId" noLabel labelName="考核周期" keyName="cycleId" placName="请选择周期"
                    style="min-height:0;"
                    @change="changeCycle"
                />
                <t-button @click="toExport()">导出</t-button>
            </div>
            <!-- <el-date-picker
                v-model="useMonth"
                size="small"
                type="monthrange"
                placeholder="选择月"
                value-format="yyyy-MM"
                :clearable="false"
                @change="researchList"
            /> -->
        </div>
        <div class="margin-bottom-20" style="padding-bottom:20px;">
            <el-table
                ref="listtable"
                v-loading="tableListLoding"
                stripe
                :data="tableData"
            >
                <el-table-column label="责任单位" prop="deptName" min-width="200" />
                <el-table-column label="工单总数" prop="count" min-width="150" />
                <el-table-column label="办结数" prop="endCount" min-width="150" />
                <el-table-column label="办结率" min-width="150">
                    <template slot-scope="scope">
                        {{ scope.row.bjl }} %
                    </template>
                </el-table-column>
                <el-table-column label="超时数" prop="timeoutCount" min-width="150" />
                <el-table-column label="黄牌警告数" prop="yellowWarn" min-width="150" />
                <el-table-column label="红牌警告数" prop="redWarn" min-width="150" />
                <el-table-column label="综合排名" min-width="150">
                    <template slot-scope="scope">
                        {{ scope.$index + 1 }}
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <!-- <div class="w-100 margin-bottom-20">
            <t-pagination
                showFirstAndLastPageBtn
                :total="total"
                :default-page-size="limit"
                :pageSizeOptions="pageSizes"
                @current-change="handleCurrentChange"
                @page-size-change="handleSizeChange"
            />
        </div> -->
    </t-card>
</template>
<script>
import { totalEventCount } from '@/api/work/workevent'
const moment = require('moment')
const XLSX = require('xlsx')
const FileSaver = require('file-saver')
export default {
    data() {
        return {
            tableListLoding: false,
            tableData: [],
            searchData: {
                cycle: ''
            },
            useMonth: [
                moment().format('YYYY-MM'),
                moment().format('YYYY-MM')
            ],
            // 分页
            limit: 20,
            total: 18,
            page: 1,
            pageSizes: [10, 20, 40, 80]
        }
    },
    computed: {
        nowUseMonth() {
            return moment(this.useMonth[0]).format('YYYY年MM月') + '-' + moment(this.useMonth[0]).format('YYYY年MM月')
        }
    },
    mounted() {
        this.researchList()
    },
    methods: {
        handleSizeChange(val) {
            // 页数改变
            this.limit = val
            this.page = 1
            this.researchList(false, true)
        },
        handleCurrentChange(val) {
            // 当前页改变了
            this.page = val
            this.researchList(false, true)
        },
        changeCycle(v) {
            // let cycleId = v.cycleId || ''
            this.searchData.cycle = v.assessCycleName || ''
            this.researchList()
        },
        researchList() {
            let _this = this
            if (!this.searchData.cycle) return
            this.tableListLoding = true
            this.tableData = []
            totalEventCount(this.searchData)
            .then(data => {
                let list = data.data
                list.forEach(i => {
                    i.deptName = i.deptName || '未知'
                    // 办结率： 办结数 / 工单总数(为0则为100%)
                    if (i.count == 0) {
                        i.bjl = 100
                        i.real_bjl = 100
                    } else {
                        // 显示办结率
                        i.bjl = (~~((i.endCount * 100 * 100) / i.count)) / 100
                        // 排名办结率： 显示的有差异，实际排名按照实际精度
                        i.real_bjl = (((i.endCount * 100 * 100) / i.count)) / 100
                    }
                })
                list.sort((a, b) => {
                    return a.real_bjl < b.real_bjl ? 1 : -1
                })
                _this.tableData = list
            })
            .finally(e => {
                _this.tableListLoding = false
            })
            this.tableData = []
        },
        toExport() {
            let _this = this
            // 导出数据
            let downData = this.tableData.map((i, iindex) => {
                return {
                    '责任单位': i.deptName,
                    '工单总数': i.count,
                    '办结数': i.endCount,
                    '办结率': i.bjl + '%',
                    '超时数': i.timeoutCount,
                    '黄牌警告数': i.yellowWarn,
                    '红牌警告数': i.redWarn,
                    '综合排名': iindex + 1,
                }
            })
            let workBook = {
                SheetNames: ['Sheet1'], Sheets: {}, Props: {}
            }
            workBook.Sheets['Sheet1'] = XLSX.utils.json_to_sheet(downData)
            workBook.Sheets['Sheet1']['!cols'] = new Array(8).fill(0).map(i => {
                return { wch: 30 }
            })
            var wbout = XLSX.write(workBook, {
                bookType: 'xlsx',
                bookSST: true,
                type: 'array'
            })
            FileSaver.saveAs(new Blob([wbout], { type: 'application/octet-stream' }), `统计数据${this.searchData.cycle}.xlsx`)
        }
    }
}
</script>
