<template>
    <baseKanbanTd title="待分派">
        <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
            <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                    <normalSearch ref="code" labelName="事件编号" keyName="code" placName="请输入事件编号" />
                    <searchAssessCycle ref="assessCycleId" labelName="考核周期" keyName="cycleId" placName="请选择周期" />
                    <searchCheckPointsType ref="typeId" labelName="点位类型" keyName="typeId" placName="请选择点位类型" />
                    <normalSearch ref="checkPoints" labelName="点位名称" keyName="checkPoints" placName="请输入点位名称" />
                    <normalSearch ref="content" labelName="存在问题" keyName="content" placName="请输入关键字" />
                </div>
            </div>
            <div slot="table">
                <div class="show-flex-r j-e-2 margin-bottom-10">
                    <t-button theme="primary" @click="toBatchHandle()">批量派发</t-button>
                </div>
                <div>
                    <el-table ref="listtable" v-loading="tableListLoding" stripe :data="tableData">
                        <el-table-column label="选择" width="50">
                            <template slot="header" slot-scope="scope">
                                <el-checkbox v-model="zrAllSelect" :indeterminate="zrisIndeterminate" :error="scope.row" @change="handleCheckAllChange" />
                            </template>
                            <template slot-scope="scope">
                                <el-checkbox v-model="scope.row.zr_select" @change="changeZrSelect" />
                            </template>
                        </el-table-column>
                        <el-table-column label="事件来源" width="80">
                            <template slot-scope="scope">
                                <div>
                                    <el-tag v-if="scope.row.eventSource == 1" type="primary">自主巡查</el-tag>
                                    <el-tag v-if="scope.row.eventSource == 2" type="danger">AI告警</el-tag>
                                    <el-tag v-if="scope.row.eventSource == 3" type="success">专班巡查</el-tag>
                                    <el-tag v-if="scope.row.eventSource == 4" type="warning">市级下发</el-tag>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column label="事件编号" prop="code" width="140" />
                        <el-table-column label="周期" prop="cycle" width="80" />
                        <el-table-column label="状态" width="80">
                            <template slot-scope="scope">
                                <div>
                                    <el-tag v-if="scope.row.status == 20" type="primary" size="small">待分派</el-tag>
                                    <el-tag v-if="scope.row.status == 30" type="success" size="small">待受理</el-tag>
                                    <el-tag v-if="scope.row.status == 40" type="warning" size="small">待处置</el-tag>
                                    <el-tag v-if="scope.row.status == 50" type="danger" size="small">待核查</el-tag>
                                    <el-tag v-if="scope.row.status == 60" type="info" size="small">已办结</el-tag>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column label="街道" prop="regionName" width="100" />
                        <el-table-column label="问题类型" prop="eventTypeName" width="120" />
                        <el-table-column label="点位类型" prop="checkPointsType" min-width="180" />
                        <el-table-column label="点位名称" prop="checkPoints" min-width="200" />
                        <el-table-column label="存在问题" prop="content" min-width="180" />
                        <el-table-column label="责任单位" prop="deptNames" width="120" />
                        <el-table-column label="扣分" prop="score" width="60" />
                        <el-table-column label="上报时间" prop="reportTime" width="170" />
                                
                        <el-table-column label="操作" width="100" fixed="right">
                            <template slot-scope="scope">
                                <div class="show-flex-r a-c">
                                    <div class="zr-link-p unline m-5" @click="toHandle(scope.row)">分派</div>
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
                <!-- 单个 -->
                <diarecordApprove
                    v-if="dia_show_pf"
                    :show="dia_show_pf"
                    @cancel="() => { dia_show_pf = false }"
                    @sure="giveRecord"
                ></diarecordApprove>
                <!-- 批量 -->
                <diarecordApprove
                    v-if="dia_show_pf_batch"
                    :show="dia_show_pf_batch"
                    @cancel="() => { dia_show_pf_batch = false }"
                    @sure="giveRecordBatch"
                ></diarecordApprove>
            </div>
        </baseSearchForm>
    </baseKanbanTd>
</template>
<script>
import { reportMixins } from '@/utils/mixins'
import { give, examineGive } from "@/api/workflow/workflow"
import diarecordApprove from './diarecordApprove.vue'
export default {
    mixins: [reportMixins],
    components: { diarecordApprove },
    data() {
        return {
            dia_show_pf: false,
            dia_show_pf_batch: false,
            row: {},
            nowItem: null,
            nowList: null
        }
    },
    methods: {
        getList(searchData) {
            this.getTableData(give, searchData)
        },
        toHandle(item) {
            this.nowItem = item
            this.dia_show_pf = true
        },
        toBatchHandle() {
            let list = this.tableData.filter(i => { return i.zr_select })
            if (list.length == 0) return this.$errorMessage('请选择工单')
            this.nowList = list
            this.dia_show_pf_batch = true
        },
        giveRecord(result) {
            let _this = this
            this.dia_show_pf = false
            examineGive({
                "id": _this.nowItem.id,
                "deptIds": result.newDeptIds,
            }).then(d => {
                _this.$successMessage("操作成功!")
                _this.refushPage()
            })
        },
        giveRecordBatch() {
            let _this = this
            this.dia_show_pf_batch = false
            examineGiveBatch({
                "ids": _this.nowList.map(i => { return i.id }),
                "deptIds": result.newDeptIds,
            }).then(d => {
                _this.$successMessage("操作成功!")
                _this.refushPage()
            })
        }
    }
}
</script>
