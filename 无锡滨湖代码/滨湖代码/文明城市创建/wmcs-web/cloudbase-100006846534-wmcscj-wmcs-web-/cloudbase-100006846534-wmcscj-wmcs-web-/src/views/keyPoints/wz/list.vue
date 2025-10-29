<template>
    <baseKanbanTd title="顽症">
        <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
            <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                    <searchCheckPointsType ref="typeId" labelName="点位类型" keyName="typeId" placName="请选择点位类型" />
                    <normalSearch ref="checkPoints" labelName="点位名称" keyName="checkPoints" placName="请输入点位名称" />
                    <selectEventType ref="eventTypeId" labelName="问题类型" keyName="eventTypeId" />
                </div>
            </div>
            <div slot="table">
                <div>
                    <el-table ref="listtable" v-loading="tableListLoding" stripe :data="tableData">
                        <!-- <el-table-column label="所属期数" min-width="200" /> -->
                        <el-table-column label="点位类型" prop="checkPointsType" min-width="100" />
                        <el-table-column label="点位名称" prop="checkPoints" min-width="100" />
                        <el-table-column label="主要问题" prop="eventTypeName" min-width="100" />
                        <el-table-column label="数量" prop="content" min-width="100" />
                    </el-table>
                </div>
            </div>
        </baseSearchForm>
    </baseKanbanTd>
</template>
<script>
import { reportMixins } from '@/utils/mixins'
import { keyPointsByWz } from '@/api/assess/analysis'
export default {
    mixins: [reportMixins],
    data() {
        return {}
    },
    methods: {
        getList(searchData) {
            // if (searchData['eventTypeId']) searchData['eventTypeId'] = searchData['eventTypeId'][searchData['eventTypeId'].length - 1]
            // this.getTableData(keyPointsByWz, searchData)
        }
    }
}
</script>
