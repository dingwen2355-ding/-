<template>
    <baseKanbanTd title="工单总览">
        <baseSearchForm ref="searchTable"  noSearch @search="startSearch" @reset="resetQuery">
            <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                </div>
            </div>
            <div slot="table">
                <!-- <div class="show-flex-r j-e-2 margin-bottom-10">
                    <t-button @click="toAddNew()">+ 新增</t-button>
                </div> -->
                <div>
                    <el-table
                        ref="listtable"
                        v-loading="tableListLoding"
                        stripe
                        :data="tableData"
                    >
                        <el-table-column label="问题类型" prop="typeName" min-width="200" />
                        <el-table-column label="所属设备" prop="channelName" min-width="300" />
                        <el-table-column label="关联点位" prop="pointName" min-width="300" />
                        <el-table-column label="产生告警条数" prop="count" width="150" />
                        <el-table-column label="操作" width="120" fixed="right">
                            <template slot-scope="scope">
                                <div class="show-flex-r a-c">
                                    <div class="zr-link-p unline m-5" @click="addEvent(scope.row)">生成工单</div>
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
            </div>
        </baseSearchForm>
    </baseKanbanTd>
</template>
<script>
import { reportMixins } from '@/utils/mixins'
import { listAppearance } from '@/api/event'
export default {
    mixins: [reportMixins],
    data() {
        return {}
    },
    mounted(){
        this.tableList()
    },
    methods: {
        tableList() {
            let _this = this
            listAppearance(_this.searchData).then(data => {
                let list = data.data
                _this.tableData = list
            })
            .finally(e => {
                _this.tableListLoding = false
            })
            this.tableData = []
        }, 
        addEvent(row) {
            this.$router.push('./_add?pointId=' + row.pointId + "&pointTypeId=" + row.pointTypeId)
        }

    }
}
</script>
