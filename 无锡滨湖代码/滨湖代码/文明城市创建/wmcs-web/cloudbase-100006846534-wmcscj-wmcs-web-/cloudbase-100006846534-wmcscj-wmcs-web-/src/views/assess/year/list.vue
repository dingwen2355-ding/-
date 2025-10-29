<template>
    <baseKanbanTd title="年度考核">
        <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
            <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                    <normalSearch ref="pointsName" labelName="点位名称" keyName="pointsName" placName="请输入点位名称" />
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
                        <el-table-column label="责任单位" prop="deptName" min-width="150" />
                        <el-table-column label="街道/社区" prop="regionName" min-width="220" />
                        <el-table-column label="考核时间" prop="checkDate" width="170" />
                        <el-table-column label="点位类别" prop="typeName" min-width="200" />
                        <el-table-column label="点位" prop="pointName" min-width="200" />
                        <el-table-column label="发生次数" prop="count" min-width="100" />
                        <el-table-column label="总扣分数" prop="scoreTotal" min-width="100" />
                        <el-table-column label="整改个数" prop="handleCount" min-width="100" />
                        <el-table-column label="整改完成率" min-width="100" >
                            <template slot-scope="scope">
                                {{ scope.row.handleAvg }} %
                            </template>
                        </el-table-column>
                        <!-- <el-table-column label="操作" width="150" fixed="right">
                            <template slot-scope="scope">
                                <div class="show-flex-r a-c">
                                    <div class="zr-link-p unline m-5" @click="toInfoItem(scope.row)">详情</div>
                                    <div class="zr-link-p unline m-5" @click="toEditItem(scope.row)">修改</div>
                                    <div class="zr-link-p unline m-5" @click="toAddNewNext(scope.row)">新增</div>
                                    <div class="zr-link-d unline m-5" @click="delItem(scope.row)">删除</div>
                                </div>
                            </template>
                        </el-table-column> -->
                    </el-table>
                </div>
            </div>
        </baseSearchForm>
    </baseKanbanTd>
</template>
<script>
import { reportMixins } from '@/utils/mixins'
import { getYearAssess } from '@/api/assess/assess'
export default {
    mixins: [reportMixins],
    data() {
        return {}
    },
    methods: {
        getList(searchData) {
            this.getTableData(getYearAssess, searchData)
        }
    }
}
</script>
