<template>
    <baseKanbanTd title="历史事件">
        <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
            <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                    <!-- <normalSearch ref="checkName" labelName="周期" keyName="checkName" placName="请输入周期" /> -->
                    <!-- <normalSearch ref="typeName" labelName="类别" keyName="typeName" placName="请输入类别" /> -->
                    <searchAssessCycle ref="assessCycleId" labelName="考核周期" keyName="cycleId" placName="请选择周期" />
                    <searchCheckPointsType ref="typeId" labelName="点位类型" keyName="typeId" placName="请选择点位类型" />
                    <normalSearch ref="checkPoints"  labelName="点位名称" keyName="pointName" placName="请输入点位名称" />
                    <normalSearch ref="checkInfo" labelName="存在问题" keyName="checkInfo" placName="请输入检查内容" />
                    <normalSearch ref="regionName" labelName="区域" keyName="regionName" placName="请输入区域" />
                    <normalSearch ref="departName" labelName="责任单位" keyName="departName" placName="请输入责任单位" />
                </div>
            </div>
            <div slot="table">
                <div>
                    <el-table ref="listtable" v-loading="tableListLoding" stripe :data="tableData">
                        <el-table-column label="区域" prop="regionName" width="100" />
                        <el-table-column label="周期" prop="checkName" width="100" />
                        <el-table-column label="责任单位" prop="departName" min-width="200" />
                        <el-table-column label="类别" prop="typeName" min-width="200" />
                        <el-table-column label="点位" prop="pointName" min-width="200" />
                        <el-table-column label="存在问题" prop="checkInfo" min-width="250" />
                        <el-table-column label="扣分" prop="cscore" width="80" />
                        <el-table-column label="状态" width="80">
                            <template slot-scope="scope">
                                <div>
                                    <el-tag v-if="scope.row.state == '0'" type="danger">未整改</el-tag>
                                    <el-tag v-if="scope.row.state == '1'" type="primary">短期整改</el-tag>
                                    <el-tag v-if="scope.row.state == '2'" type="warning">长期整改</el-tag>
                                    <el-tag v-if="scope.row.state == '3'" type="success">已整改</el-tag>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column label="巡查时间" prop="checkDate" width="170" />
                        <el-table-column label="预计整改时间" prop="planDate" width="170" />
                        <!-- <el-table-column label="操作" width="100" fixed="right">
                            <template slot-scope="scope">
                                <div class="show-flex-r a-c">
                                    <div class="zr-link-p unline m-5" @click="toInfo(scope.row)">详情</div>
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
import { listWorkEvent, synchEvent } from '@/api/work/workevent'
export default {
    mixins: [reportMixins],
    data() {
        return {}
    },
    methods: {
        getList(searchData) {
            this.getTableData(listWorkEvent, searchData)
        },
        toInfo(item) {
            this.$router.push(
                `./_info?id=${item.id}`
            );
        }
    }
}
</script>
