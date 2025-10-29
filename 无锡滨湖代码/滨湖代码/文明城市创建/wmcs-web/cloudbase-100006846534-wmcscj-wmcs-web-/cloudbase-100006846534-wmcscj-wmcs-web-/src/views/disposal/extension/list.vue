<template>
    <baseKanbanTd title="延期管理">
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
                <div>
                    <el-table
                        ref="listtable"
                        v-loading="tableListLoding"
                        stripe
                        :data="tableData"
                    >
                        <!-- <el-table-column label="事件来源" width="80">
                            <template slot-scope="scope">
                                <div>
                                    <el-tag v-if="scope.row.eventSource == 1" type="primary">自主巡查</el-tag>
                                    <el-tag v-if="scope.row.eventSource == 2" type="danger">AI告警</el-tag>
                                    <el-tag v-if="scope.row.eventSource == 3" type="success">专班巡查</el-tag>
                                    <el-tag v-if="scope.row.eventSource == 4" type="warning">市级下发</el-tag>
                                </div>
                            </template>
                        </el-table-column> -->
                        <el-table-column label="事件编号" prop="code" width="140" />
                        <!-- <el-table-column label="周期" prop="cycle" width="80" /> -->
                        <el-table-column label="状态" width="80">
                            <template slot-scope="scope">
                                <div>
                                    <el-tag v-if="scope.row.eventStatus == 20" type="primary" size="small">待分派</el-tag>
                                    <el-tag v-if="scope.row.eventStatus == 30" type="success" size="small">待受理</el-tag>
                                    <el-tag v-if="scope.row.eventStatus == 40" type="warning" size="small">待处置</el-tag>
                                    <el-tag v-if="scope.row.eventStatus == 50" type="danger" size="small">待核查</el-tag>
                                    <el-tag v-if="scope.row.eventStatus == 60" type="info" size="small">已办结</el-tag>
                                </div>
                            </template>
                        </el-table-column>
                        <!-- <el-table-column label="街道" prop="regionName" width="100" /> -->
                        <!-- <el-table-column label="问题类型" prop="eventTypeName" width="120" />
                        <el-table-column label="点位类型" prop="checkPointsType" min-width="180" />
                        <el-table-column label="点位名称" prop="checkPoints" min-width="200" /> -->
                        <el-table-column label="存在问题" prop="content" min-width="180" />
                        <el-table-column label="责任单位" prop="handleDept" min-width="120" />
                        <!-- <el-table-column label="当前节点" prop="handleNodename" width="80" /> -->
                        <el-table-column label="上报时间" prop="reportTime" width="170" />
                        <el-table-column label="处置时限" prop="handleTimelimit" width="170" />
                        <el-table-column label="申请延期时间" prop="extensionDate" width="170" />
                        <el-table-column label="申请原因" prop="reason" min-width="120" />
                        <el-table-column label="申请部门" prop="applyDeptName" width="100" />
                        <el-table-column label="申请时间" prop="applyDate" width="170" />
                        <el-table-column label="状态" width="80">
                            <template slot-scope="scope">
                                <div>
                                    <el-tag v-if="scope.row.status == 0" type="primary" size="small">待审批</el-tag>
                                    <el-tag v-if="scope.row.status == 1" type="success" size="small">已同意</el-tag>
                                    <el-tag v-if="scope.row.status == 2" type="warning" size="small">已拒绝</el-tag>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" width="100" fixed="right">
                            <template slot-scope="scope">
                                <div class="show-flex-r a-c">
                                    <div class="zr-link-p unline m-5" @click="toInfo(scope.row)">详情</div>
                                    <!-- <div v-if="scope.row.status == 0 && pow('ExtensionExamine')" class="zr-link-p unline m-5" @click="toExamine(scope.row)">审核</div> -->
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
                <!-- 审核 -->
                <diaApprove
                    v-if="diaShowApprove"
                    :show="diaShowApprove"
                    @cancel="diaShowApprove = false"
                    @sure="approveItem"
                />
            </div>
        </baseSearchForm>
    </baseKanbanTd>
</template>
<script>
import { reportMixins } from '@/utils/mixins'
import { extension, extensionApprove } from "@/api/workflow/workflow";
export default {
    mixins: [reportMixins],
    data() {
        return {
            diaShowApprove: false,
            nowItem: null
        }
    },
    methods: {
        getList(searchData) {
            this.getTableData(extension, searchData)
        },
        toInfo(item) {
            this.$router.push(
                `./_info?id=${item.eventId}&flowinstanceid=${item.flowinstanceid}`
            );
        }, 
        toExamine(item) {
            this.nowItem = item
            this.diaShowApprove = true
        },
        approveItem(result) {
            let _this = this
            this.diaShowApprove = false
            extensionApprove({
                "id": _this.nowItem.id,
                "status": result.status,
                "opinion": result.reason
            }).then(d => {
                _this.$successMessage("操作成功!")
                _this.refushPage()
            })
        }
    }
}
</script>
