<template>
    <baseKanbanTd title="阈值管理">
        <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
            <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                    <dirtWorkAlarmType ref="type" labelName="告警类型" keyName="type" placName="请选择告警类型" />
                </div>
            </div>
            <div slot="table">
                <div class="show-flex-r j-e-2 margin-bottom-10">
                    <t-button @click="toAddNew()">+ 新增</t-button>
                </div>
                <div>
                    <el-table
                        ref="listtable"
                        v-loading="tableListLoding"
                        stripe
                        :data="tableData"
                    >
                        <el-table-column label="告警类型" prop="alarmType" min-width="180">
                            <template slot-scope="scope">
                                <dirtWorkAlarmType
                                    noLabel
                                    useType="form"
                                    allWidth="100%"
                                    disabled
                                    onlyShow
                                    :defaultVal="scope.row.alarmType"
                                />
                            </template>
                        </el-table-column>
                        <el-table-column label="工单类别" prop="eventTypeName" min-width="200" />
                        <el-table-column label="平时模式告警次数" prop="normalOverCount" width="150" />
                        <el-table-column label="检测类型" prop="type" width="150">
                            <template slot-scope="scope">
                                <div>
                                    <el-tag v-if="scope.row.type == 1" type="primary" size="small">每分钟</el-tag>
                                    <el-tag v-if="scope.row.type == 2" type="danger" size="small">每小时</el-tag>
                                    <el-tag v-if="scope.row.type == 3" type="info" size="small">每天</el-tag>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column label="是否开始战时模式" prop="specialFlag" width="150">
                            <template slot-scope="scope">
                                <div>
                                    <el-tag v-if="scope.row.specialFlag == 1">是</el-tag>
                                    <el-tag v-if="scope.row.specialFlag == 0">否</el-tag>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column label="战时模式告警次数" prop="overCount" width="150" />
                        <el-table-column label="配置时间段" prop="timeConfigName" width="200" />
                        <el-table-column label="开始时间" prop="startTime" width="170" />
                        <el-table-column label="结束时间" prop="finishTime" width="170" />
                        <el-table-column label="操作" width="150" fixed="right">
                            <template slot-scope="scope">
                                <div class="show-flex-r a-c">
                                    <div class="zr-link-p unline m-5" @click="toInfoItem(scope.row)">详情</div>
                                    <div class="zr-link-h unline m-5" @click="toEditItem(scope.row)">修改</div>
                                    <div class="zr-link-d unline m-5" @click="delItem(scope.row)">删除</div>
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
import { listThreshold, deleteThreshold } from '@/api/work/threshold'
export default {
    mixins: [reportMixins],
    data() {
        return {}
    },
    methods: {
        getList(searchData) {
            this.getTableData(listThreshold, searchData)
        },
        delItem(item) {
            let _this = this
            new Promise((res, rej) => {
                _this.useDia = this.$dialog({
                    header: '系统提示',
                    body: `是否确认删除ID为"${item.id}"的数据项？`,
                    style: 'color: rgba(0, 0, 0, 0.6)',
                    onConfirm: ({ e }) => {
                        res()
                    }
                })
            })
            .then(d => {
                _this.useDia.hide()
                return deleteThreshold(item.id)
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        }
    }
}
</script>
