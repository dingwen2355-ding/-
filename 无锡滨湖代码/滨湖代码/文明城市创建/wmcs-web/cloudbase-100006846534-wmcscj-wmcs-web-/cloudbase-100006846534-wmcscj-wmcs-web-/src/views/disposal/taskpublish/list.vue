<template>
    <baseKanbanTd title="巡查任务">
        <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
            <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                    <!-- <normalSearch ref="name" labelName="任务名称" keyName="name" placName="请输入任务名称" /> -->
                    <searchCheckPointsType ref="typeId" labelName="点位类型" keyName="pointsTypeId" placName="请选择点位类型" />
                    <asyncSearchPoint ref="pointsId" labelName="所属点位" keyName="pointsId" />
                </div>
            </div>
            <div slot="table">
                <div class="show-flex-r j-e-2 margin-bottom-10">
                    <t-button class="margin-right-10" @click="toAddNew()">+ 新增</t-button>
                </div>
                <div>
                    <el-table
                        ref="listtable"
                        v-loading="tableListLoding"
                        stripe
                        :data="tableData"
                    >
                        <el-table-column label="任务类型" width="100">
                            <template slot-scope="scope">
                                <div v-if="scope.row.type == 1">常规类型</div>
                                <div v-if="scope.row.type == 2">特殊类型</div>
                            </template>
                        </el-table-column>
                        <el-table-column label="点位类型" prop="pointsTypeName" width="200" />
                        <el-table-column label="所属点位" prop="pointsName" width="200" />
                        <el-table-column label="巡查项" prop="checkItem" min-width="200" />
                        <el-table-column label="巡查指标" prop="content" min-width="200" />
                        <el-table-column label="负责督查类型" width="180">
                            <template slot-scope="scope">
                                <dirtCheckTaskStatus
                                    noLabel
                                    useType="form"
                                    allWidth="100%"
                                    disabled
                                    onlyShow
                                    :defaultVal="scope.row.status"
                                />
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" width="150" fixed="right">
                            <template slot-scope="scope">
                                <div class="show-flex-r a-c">
                                    <div class="zr-link-p unline m-5" @click="toInfoItem(scope.row)">详情</div>
                                    <div class="zr-link-h unline m-5" @click="toEditItem(scope.row)">编辑</div>
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
import { listCheckTask, deleteCheckTask } from '@/api/data/taskPublish'
export default {
    mixins: [reportMixins],
    data() {
        return {}
    },
    methods: {
        getList(searchData) {
            this.getTableData(listCheckTask, searchData)
        },
        delItem(item) {
            let _this = this
            new Promise((res, rej) => {
                _this.useDia = this.$dialog({
                    header: '系统提示',
                    body: `是否确认删除名称为"${item.pointsTypeName}"的数据项？`,
                    style: 'color: rgba(0, 0, 0, 0.6)',
                    onConfirm: ({ e }) => {
                        res()
                    }
                })
            })
            .then(d => {
                _this.useDia.hide()
                return deleteCheckTask(item.id)
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        }
    }
}
</script>
