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
                { label: '荣巷街道', key1: 'rxjd1', key2: 'rxjd2' },
                { label: '蠡园街道', key1: 'lyjd1', key2: 'lyjd2' },
                { label: '蠡湖街道', key1: 'lhjd1', key2: 'lhjd2' },
                { label: '雪浪街道', key1: 'xljd1', key2: 'xljd2' },
                { label: '马山街道', key1: 'msjd1', key2: 'msjd2' },
                { label: '胡埭镇', key1: 'hlz1', key2: 'hlz2' }
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
                        // rxjd1: 20, lyjd1: 50, lhjd1: 70, xljd1: 80, msjd1: 52, hlz1: 85,
                        // rxjd2: 20, lyjd2: 50, lhjd2: 70, xljd2: 80, msjd2: 52, hlz2: 85
                        rxjd1: 0, lyjd1: 0, lhjd1: 0, xljd1: 0, msjd1: 0, hlz1: 0,
                        rxjd2: 0, lyjd2: 0, lhjd2: 0, xljd2: 0, msjd2: 0, hlz2: 0
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
                        // rxjd1: 200, lyjd1: 500, lhjd1: 700, xljd1: 800, msjd1: 520, hlz1: 850,
                        // rxjd2: 200, lyjd2: 500, lhjd2: 700, xljd2: 800, msjd2: 520, hlz2: 850
                        rxjd1: 0, lyjd1: 0, lhjd1: 0, xljd1: 0, msjd1: 0, hlz1: 0,
                        rxjd2: 0, lyjd2: 0, lhjd2: 0, xljd2: 0, msjd2: 0, hlz2: 0
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
                        rxjd1: 2000, lyjd1: 5000, lhjd1: 7000, xljd1: 8000, msjd1: 5200, hlz1: 8500,
                        rxjd2: 2000, lyjd2: 5000, lhjd2: 7000, xljd2: 8000, msjd2: 5200, hlz2: 8500
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
