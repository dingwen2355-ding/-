<template>
    <baseKanbanTd title="工单管理">
        <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
            <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                    <normalSearch ref="code" labelName="事件编号" keyName="code" placName="请输入事件编号" />
                    <searchCheckPointsType ref="typeId" labelName="点位类型" keyName="typeId" placName="请选择点位类型" />
                    <normalSearch ref="checkPoints"  labelName="点位名称" keyName="checkPoints" placName="请输入点位名称" />
                    <normalSearch ref="content" labelName="存在问题" keyName="content" placName="请输入关键字" />
                </div>
                <div class="search-line">
                    <normalCheckAll
                        ref="statusArray"
                        labelName="处置状态"
                        keyName="statusArray"
                        :selectOptions="statusOptions"
                    />
                </div>
            </div>
            <div slot="table">
                <div>
                    <el-table ref="listtable" v-loading="tableListLoding" stripe :data="tableData">
                        <el-table-column label="事件来源" width="100">
                            <template slot-scope="scope">
                                <div>
                                    <el-tag v-if="scope.row.eventSource == 1" type="primary">自主巡查</el-tag>
                                    <el-tag v-if="scope.row.eventSource == 2" type="danger">AI告警</el-tag>
                                    <el-tag v-if="scope.row.eventSource == 3" type="danger">专班巡查</el-tag>
                                    <el-tag v-if="scope.row.eventSource == 4" type="warning">市级下发</el-tag>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column label="事件编号" prop="code" width="140" />
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
                        <el-table-column label="问题类型" prop="eventTypeName" width="120" />
                        <el-table-column label="点位类型" prop="checkPointsType" min-width="180" />
                        <el-table-column label="点位名称" prop="checkPoints" min-width="200" />
                        <el-table-column label="存在问题" prop="content" min-width="180" />
                        <el-table-column label="监控设备" prop="channelName" min-width="200" />
                        <el-table-column label="上报时间" prop="reportTime" width="170" />
                        <el-table-column label="操作" width="150" fixed="right">
                            <template slot-scope="scope">
                                <div class="show-flex-r a-c">
                                    <div class="zr-link-p unline m-5" @click="toInfo(scope.row)">详情</div>
                                    <div class="zr-link-p unline m-5" v-if="scope.row.status == 20" @click="give(scope.row)">分派</div>
                                    <div class="zr-link-w unline m-5" @click="row = scope.row; showVideo = true">监控</div>
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
                <diarecordApprove
                    v-if="dia_show_pf"
                    :show="dia_show_pf"
                    :detail="nowItem"
                    @cancel="() => { dia_show_pf = false }"
                    @sure="giveRecord"
                ></diarecordApprove>
            </div>
        </baseSearchForm>
        <video-dialog v-if="showVideo" :show="showVideo" :row="row" @cancel="showVideo = false"></video-dialog>
    </baseKanbanTd>
</template>
<script>
import { reportMixins } from '@/utils/mixins'
import { all, examineGive } from '@/api/workflow/workflow'
import videoDialog from "@/components/util/videoDialog.vue"
import diarecordApprove from './diarecordApprove.vue'
export default {
    mixins: [reportMixins],
    components: { videoDialog, diarecordApprove },
    data() {
        return {
            statusOptions: [
                { label: '待分派', value: 20 },
                { label: '待受理', value: 30 },
                { label: '待处置', value: 40 },
                { label: '待核查', value: 50 },
                { label: '已办结', value: 60 }
            ],
            showVideo: false,
            dia_show_pf: false,
            row: {},
            nowItem: null
        }
   
    },
    methods: {
        getList(searchData) {
            // searchData.eventSource = 2;
            ///////////
            this.getTableData(all, searchData)
        },
        toInfo(item) {
            this.$router.push(
                `./_info?id=${item.id}&flowinstanceid=${item.flowinstanceid}&scope=all`
            );
        },
        give(item) {
            this.nowItem = item
            this.dia_show_pf = true
            // his.$router.push(
            //     `./_handle?id=${item.id}&flowinstanceid=${item.flowinstanceid}&scope=all`
            // );
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
        }
    }
}
</script>
