<template>
    <baseKanbanTd title="重点问题">
        <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
            <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                    <dateMSelectM ref="checkDate" labelName="发生时间" key1="beginTime" key2="endTime" datetype="second" allWidth="450px" />
                    <searchRegion ref="regionCode" labelName="所属街镇" keyName="regionCode"/>
                    <selectEventType ref="eventTypeId" labelName="问题类型" keyName="eventTypeId" />
                </div>
            </div>
            <div slot="table">
                <div>
                    <el-table ref="listtable" v-loading="tableListLoding" stripe :data="tableData">
                        <el-table-column label="重点问题" prop="eventTypeName" min-width="200" />
                        <el-table-column label="所属区域" prop="regionName" min-width="200" />
                        <el-table-column label="发生次数" prop="count" width="150" />
                        <el-table-column label="总扣分数" prop="scoreTotal" width="150" />
                        <el-table-column label="整改个数" prop="handleCount"  width="150" />
                        <el-table-column label="整改完成率" min-width="150" >
                            <template slot-scope="scope">
                                {{ scope.row.handleAvg }} %
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
import { pageMainEvent } from '@/api/assess/analysis'
export default {
    mixins: [reportMixins],
    data() {
        return {}
    },
    methods: {
        getList(searchData) {
            if (searchData['eventTypeId']) searchData['eventTypeId'] = searchData['eventTypeId'][searchData['eventTypeId'].length - 1]
            this.getTableData(pageMainEvent, searchData)
        }
    }
}
</script>
