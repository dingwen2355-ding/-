<template>
    <div class="ana-trend-page-table">
        <!-- <div class="margin-bottom-10" style="text-align: right;">
            <el-date-picker
                v-model="useMonth"
                size="small"
                type="monthrange"
                placeholder="选择月"
                value-format="yyyy-MM"
                :clearable="false"
                @change="getTableList"
            />
        </div> -->
        <div class="w-100 flex-1 h-0">
            <el-table
                ref="listtable"
                v-loading="tableListLoding"
                stripe
                :data="tableData"
                height="100%"
            >
                <el-table-column label="日期" prop="statDate" width="150" />
                <el-table-column
                    v-for="(title, index) in titleArr"
                    :key="index"
                    :label="title.label"
                >
                    <el-table-column label="巡查率" width="80">
                        <template>
                            100%
                        </template>
                    </el-table-column>
                    <el-table-column label="问题数" :prop="title.key1" min-width="80" />
                    <el-table-column label="整改数" :prop="title.key2" min-width="80" />
                    <el-table-column label="整改率" width="80">
                        <template slot-scope="scope">
                            {{ (~~(10000 * (scope.row[title.key2] / (scope.row[title.key1] || 1)))) / 100 }}
                            %
                        </template>
                    </el-table-column>
                </el-table-column>
            </el-table>
        </div>
        <!-- <div class="w-100 margin-top-20">
            <t-pagination
                showFirstAndLastPageBtn
                :total="total"
                :default-page-size="limit"
                :pageSizeOptions="pageSizes"
                @current-change="handleCurrentChange"
                @page-size-change="handleSizeChange"
            />
        </div> -->
    </div>
</template>
<script>
const moment = require('moment')
export default {
    data() {
        return {
            dateType: '',
            useMonth: [
                moment().format('YYYY-MM'),
                moment().format('YYYY-MM')
            ],
            tableListLoding: false,
            tableData: [],
            titleArr: [
                { key1: 'key_01_1', key2: 'key_01_2', label: '区教育局' },
                { key1: 'key_02_1', key2: 'key_02_2', label: '区工业和信息化局' },
                { key1: 'key_03_1', key2: 'key_03_2', label: '区住房城乡建设局' },
                { key1: 'key_04_1', key2: 'key_04_2', label: '区城管局' },
                { key1: 'key_05_1', key2: 'key_05_2', label: '区交通运输局' },
                { key1: 'key_06_1', key2: 'key_06_2', label: '区水利局' },
                { key1: 'key_07_1', key2: 'key_07_2', label: '区农业农村局' },
                { key1: 'key_08_1', key2: 'key_08_2', label: '区商务局' },
                { key1: 'key_09_1', key2: 'key_09_2', label: '区文体旅游局' },
                { key1: 'key_10_1', key2: 'key_10_2', label: '区卫生健康局' },
                { key1: 'key_11_1', key2: 'key_11_2', label: '区行政审批局' },
                { key1: 'key_12_1', key2: 'key_12_2', label: '区市场监管局' },
                { key1: 'key_13_1', key2: 'key_13_2', label: '区地方金融监管局' },
                { key1: 'key_14_1', key2: 'key_14_2', label: '区公安分局' },
                { key1: 'key_15_1', key2: 'key_15_2', label: '区消防救援大队' },
                { key1: 'key_16_1', key2: 'key_16_2', label: '国投公司' },
                { key1: 'key_17_1', key2: 'key_17_2', label: '滨湖交警大队' },
                { key1: 'key_18_1', key2: 'key_18_2', label: '去宣传部' },

            ],
            titleCol: [],
            // 分页
            limit: 20,
            total: 18,
            page: 1,
            pageSizes: [10, 20, 40, 80]
        }
    },
    mounted() {
        // this.getTableList()
    },
    methods: {
        changeDateType(v) {
            this.dateType = v
            this.getTableList()
        },
        handleSizeChange(val) {
            // 页数改变
            this.limit = val
            this.page = 1
            this.getTableList(false, true)
        },
        handleCurrentChange(val) {
            // 当前页改变了
            this.page = val
            this.getTableList(false, true)
        },
        getTableList() {
            if (this.dateType == 'day') {
                let nowHour = moment().format('DD日 HH时')
                let nowDay = moment().format('DD日 ')
                let dayArr = [
                    nowDay + '00',
                    nowDay + '01', nowDay + '02', nowDay + '03', nowDay + '04', nowDay + '05', nowDay + '06', nowDay + '07', nowDay + '08',
                    nowDay + '09', nowDay + '10', nowDay + '11', nowDay + '12', nowDay + '13', nowDay + '14', nowDay + '15', nowDay + '16',
                    nowDay + '17', nowDay + '18', nowDay + '19', nowDay + '20', nowDay + '21', nowDay + '22', nowDay + '23'
                ]
                let dayX = dayArr.filter(i => { return i <= nowHour }).map(k => { return k + '时' })
                this.tableData = dayX.map(i => {
                    return {
                        statDate: i,
                        key_01_1: 0, key_01_2: 0,
                        key_02_1: 0, key_02_2: 0,
                        key_03_1: 0, key_03_2: 0,
                        key_04_1: 0, key_04_2: 0,
                        key_05_1: 0, key_05_2: 0,
                        key_06_1: 0, key_06_2: 0,
                        key_07_1: 0, key_07_2: 0,
                        key_08_1: 0, key_08_2: 0,
                        key_09_1: 0, key_09_2: 0,
                        key_10_1: 0, key_10_2: 0,
                        key_11_1: 0, key_11_2: 0,
                        key_12_1: 0, key_12_2: 0,
                        key_13_1: 0, key_13_2: 0,
                        key_14_1: 0, key_14_2: 0,
                        key_15_1: 0, key_15_2: 0,
                        key_16_1: 0, key_16_2: 0,
                        key_17_1: 0, key_17_2: 0,
                        key_18_1: 0, key_18_2: 0

                    }
                })
            }
            if (this.dateType == 'week') {
                let weekX = [
                    // moment().add(-6, 'day').format('YYYY-MM-DD'),
                    moment().add(-5, 'day').format('YYYY-MM-DD'),
                    moment().add(-4, 'day').format('YYYY-MM-DD'),
                    moment().add(-3, 'day').format('YYYY-MM-DD'),
                    moment().add(-2, 'day').format('YYYY-MM-DD'),
                    moment().add(-1, 'day').format('YYYY-MM-DD'),
                    moment().format('YYYY-MM-DD')
                ]
                this.tableData = weekX.map(i => {
                    return {
                        statDate: i,
                        key_01_1: 0, key_01_2: 0,
                        key_02_1: 0, key_02_2: 0,
                        key_03_1: 0, key_03_2: 0,
                        key_04_1: 0, key_04_2: 0,
                        key_05_1: 0, key_05_2: 0,
                        key_06_1: 0, key_06_2: 0,
                        key_07_1: 0, key_07_2: 0,
                        key_08_1: 0, key_08_2: 0,
                        key_09_1: 0, key_09_2: 0,
                        key_10_1: 0, key_10_2: 0,
                        key_11_1: 0, key_11_2: 0,
                        key_12_1: 0, key_12_2: 0,
                        key_13_1: 0, key_13_2: 0,
                        key_14_1: 0, key_14_2: 0,
                        key_15_1: 0, key_15_2: 0,
                        key_16_1: 0, key_16_2: 0,
                        key_17_1: 0, key_17_2: 0,
                        key_18_1: 0, key_18_2: 0
                    }
                })
            }
            if (this.dateType == 'month') {
                let monthX = [
                    moment().add(-5, 'month').format('YYYY-MM月'),
                    moment().add(-4, 'month').format('YYYY-MM月'),
                    moment().add(-3, 'month').format('YYYY-MM月'),
                    moment().add(-2, 'month').format('YYYY-MM月'),
                    moment().add(-1, 'month').format('YYYY-MM月'),
                    moment().format('YYYY-MM月')
                ]
                this.tableData = monthX.map(i => {
                    return {
                        statDate: i,
                        key_01_1: 201, key_01_2: 198,
                        key_02_1: 150, key_02_2: 150,
                        key_03_1: 120, key_03_2: 120,
                        key_04_1: 120, key_04_2: 120,
                        key_05_1: 120, key_05_2: 120,
                        key_06_1: 120, key_06_2: 120,
                        key_07_1: 120, key_07_2: 120,
                        key_08_1: 120, key_08_2: 120,
                        key_09_1: 120, key_09_2: 120,
                        key_10_1: 120, key_10_2: 120,
                        key_11_1: 120, key_11_2: 120,
                        key_12_1: 120, key_12_2: 120,
                        key_13_1: 120, key_13_2: 120,
                        key_14_1: 120, key_14_2: 120,
                        key_15_1: 120, key_15_2: 120,
                        key_16_1: 120, key_16_2: 120,
                        key_17_1: 120, key_17_2: 120,
                        key_18_1: 120, key_18_2: 120
                    }
                })
            }
            return
        }
    }
}
</script>
<style scoped lang="scss">
.ana-trend-page-table{
    background: #ffffff;
    padding: 20px;
    border-radius: 5px;
    height: 100%;
    display: flex;
    flex-direction: column;
}
</style>
