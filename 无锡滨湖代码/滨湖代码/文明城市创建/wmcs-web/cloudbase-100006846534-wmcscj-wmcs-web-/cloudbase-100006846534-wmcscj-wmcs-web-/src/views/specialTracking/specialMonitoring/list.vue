<template>
    <baseKanbanTd title="专项监测">
        <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
            <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                    <selectEventType ref="eventTypeId" labelName="专项课题" keyName="eventTypeId" />
                    <searchCheckPointsType ref="typeId" labelName="专题类型" keyName="typeId" placName="请选择专题类型" />
                </div>
            </div>
            <div slot="table">
                <div class="show-flex-r j-e-2 margin-bottom-20" style="margin: 10px; font-size: 14px; color: red"> 总体得分： {{ scoreTotal }}</div>
                <div>
                    <el-table ref="listtable" v-loading="tableListLoding" stripe :data="tableData">
                        <el-table-column label="专项课题" prop="eventTypeName" width="200" />
                        <el-table-column label="专题类型" prop="typeName" width="220" />
                        <el-table-column label="指标得分" prop="score" width="100" />
                        <el-table-column label="存在问题" prop="content"width="300" />
                        <el-table-column label="点位名称" prop="pointName" min-width="220" />
                        <el-table-column label="所属区域" prop="regionName" min-width="200" />
                    </el-table>
                </div>
            </div>
        </baseSearchForm>
    </baseKanbanTd>
</template>
<script>
import { reportMixins } from '@/utils/mixins'
import { specialList, specialSroceTotal  } from '@/api/assess/analysis'
export default {
    mixins: [reportMixins],
    data() {
        return {
            scoreTotal: 0
        }
    },
    mounted() {
        this.getScoreTotal()
    },
    methods: {
        getScoreTotal() {
            let _this = this
            specialSroceTotal({}).then(res => {
                console.log(res.data)
                _this.scoreTotal = res.data
            })

        },
        getList(searchData) {
            if (searchData['eventTypeId']) searchData['eventTypeId'] = searchData['eventTypeId'][searchData['eventTypeId'].length - 1]
            this.getTableData(specialList, searchData)
        }
    }
}
</script>
