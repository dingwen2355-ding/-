<template>
    <baseKanbanTd title="问题高发点">
        <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
            <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                    <searchAssessCycle ref="assessCycleId" labelName="期数" keyName="cycleId" placName="请选择期数" />
                    <searchCheckPointsType ref="typeId" labelName="点位类型" keyName="typeId" placName="请选择点位类型" />
                </div>
            </div>
            <div slot="table">
                <div>
                    <el-table ref="listtable" v-loading="tableListLoding" stripe :data="tableData">
                        <el-table-column label="所属期数" prop="checkName" min-width="200" />
                        <el-table-column label="所属区域" prop="regionName" min-width="180" />
                        <el-table-column label="点位类型" prop="typeName" min-width="200" />
                        <el-table-column label="点位名称" prop="pointName" min-width="200" />
                        <el-table-column label="主要问题" prop="eventTypeName" min-width="200" />
                        <el-table-column label="数量" prop="count" width="100" />
                    </el-table>
                </div>
            </div>
        </baseSearchForm>
    </baseKanbanTd>
</template>
<script>
import { reportMixins } from '@/utils/mixins'
import { keyPointsByGf } from '@/api/assess/analysis'
export default {
    mixins: [reportMixins],
    data() {
        return {}
    },
    methods: {
        getList(searchData) {
            this.getTableData(keyPointsByGf, searchData)
        }
    }
}
</script>
