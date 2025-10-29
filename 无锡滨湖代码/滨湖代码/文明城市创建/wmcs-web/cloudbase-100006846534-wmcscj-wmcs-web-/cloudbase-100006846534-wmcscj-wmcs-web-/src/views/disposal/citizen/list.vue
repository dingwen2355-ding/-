<template>
    <baseKanbanTd title="已处置">
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
                </div>
                <div>
                    <el-table ref="listtable" v-loading="tableListLoding" stripe :data="tableData">
                        <el-table-column label="事件编号" prop="code" width="140" />
                        <el-table-column label="周期" prop="cycle" width="80" />
                        <el-table-column label="街道" prop="regionName" width="100" />
                        <el-table-column label="点位类型" prop="checkPointsType" width="200" />
                        <el-table-column label="点位名称" prop="checkPoints" width="200" />
                        <el-table-column label="存在问题" prop="content" width="250" />
                        <el-table-column label="责任单位" prop="deptNames" width="120" />
                        <el-table-column label="扣分" prop="score" width="60" />
                        <el-table-column label="当前节点" prop="nodename" width="80" />
                        <el-table-column label="状态" width="80">
                            <template slot-scope="scope">
                                <div>
                                    <el-tag v-if="scope.row.status == 20" type="primary" size="small">待分派</el-tag>
                                    <el-tag v-if="scope.row.status == 30" type="primary" size="small">待受理</el-tag>
                                    <el-tag v-if="scope.row.status == 40" type="success" size="small">待处置</el-tag>
                                    <el-tag v-if="scope.row.status == 50" type="warning" size="small">待核查</el-tag>
                                    <el-tag v-if="scope.row.status == 60" type="danger" size="small">待办结</el-tag>
                                    <el-tag v-if="scope.row.status == 70" type="info" size="small">已办结</el-tag>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column label="上报时间" prop="reportTime" width="170" />
                        <el-table-column label="操作" width="70" fixed="right">
                            <template slot-scope="scope">
                                <div class="show-flex-r a-c">
                                    <div class="zr-link-p unline m-5" @click="toInfo(scope.row)">详情</div>
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
import { citizen } from "@/api/workflow/workflow";
export default {
    mixins: [reportMixins],
    data() {
        return {}
    },
    methods: {
        getList(searchData) {
            this.getTableData(citizen, searchData)
        },
        toInfo(item) {
            this.$router.push(
                `./_info?id=${item.id}&flowinstanceid=${item.flowinstanceid}`
            );
        }
    }
}
</script>
