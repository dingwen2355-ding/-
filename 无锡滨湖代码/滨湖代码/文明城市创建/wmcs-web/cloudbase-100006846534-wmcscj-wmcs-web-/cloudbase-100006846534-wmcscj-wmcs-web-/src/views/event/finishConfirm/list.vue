<template>
    <baseKanbanTd title="事件总览">
        <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
            <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                    <normalSearch ref="code" labelName="事件编号" keyName="code" placName="请输入事件编号" />
                    <searchAssessCycle ref="assessCycleId" labelName="考核周期" keyName="cycleId" placName="请选择周期" />
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
                        <el-table-column label="事件编号" prop="code" width="130" />
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
                        <el-table-column label="点位类型" prop="checkPointsType" min-width="200" />
                        <el-table-column label="点位名称" prop="checkPoints" min-width="200" />
                        <el-table-column label="监控设备" prop="channelName" min-width="200" />
                        <el-table-column label="事件类型" prop="eventTypeName" min-width="150" />
                        <el-table-column label="存在问题" prop="content" min-width="250" />
                        <el-table-column label="上报时间" prop="reportTime" width="170" />
                        <el-table-column label="办结时间" prop="endtime" width="170" />
                        <el-table-column label="审核状态" width="80">
                            <template slot-scope="scope">
                                <div>
                                    <el-tag v-if="scope.row.endExamine == 0" type="primary" size="small">待审核</el-tag>
                                    <el-tag v-if="scope.row.endExamine == 1" type="success" size="small">已完成</el-tag>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" width="150" fixed="right">
                            <template slot-scope="scope">
                                <div class="show-flex-r a-c">
                                    <div class="zr-link-p unline m-5" v-if="scope.row.endExamine != 1"  @click="toExamine(scope.row)">审核</div>
                                    <div class="zr-link-w unline m-5" @click="row = scope.row; showVideo = true">查看监控</div>
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
            </div>
        </baseSearchForm>
        <video-dialog v-if="showVideo" :show="showVideo" :row="row" @cancel="showVideo = false"></video-dialog>
    </baseKanbanTd>
</template>
<script>
import { reportMixins } from '@/utils/mixins'
import { all, examine } from '@/api/workflow/workflow'
import videoDialog from "@/components/util/videoDialog.vue"
export default {
    mixins: [reportMixins],
    data() {
        return {
            showVideo: false,
            row: {}
        }
    },
    components: { videoDialog },
    methods: {
        getList(searchData) {
            searchData.eventSource = 2;
            searchData.status = 60;
            this.getTableData(all, searchData)
        },
        toExamine(item) {
            let _this = this
            new Promise((res, rej) => {
                _this.useDia = this.$dialog({
                    header: '系统提示',
                    body: `是否确认审核选中的数据项？`,
                    style: 'color: rgba(0, 0, 0, 0.6)',
                    onConfirm: ({ e }) => {
                        res()
                    }
                })
            })
            .then(d => {
                _this.useDia.hide()
                return examine(item.id)
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        }
    }
}
</script>
