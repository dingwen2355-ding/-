<template>
    <baseKanbanTd title="区指数">
        <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
            <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                    <normalSearch ref="month" labelName="年月" keyName="month" placName="年月" />
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
                        <el-table-column label="月份" prop="month" width="200" />
                        <el-table-column label="分数" prop="score" min-width="120">
                            <template slot-scope="scope">
                                <div v-if="!scope.row.is_edit" class="show-flex-r">
                                    <span class="zr-link-w noc">{{ scope.row.score }}</span>
                                </div>
                                <div v-if="scope.row.is_edit">
                                    <el-input v-model="scope.row.edit_score" size="mini" />
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" width="150" fixed="right">
                            <template slot-scope="scope">
                                <div v-if="!scope.row.is_edit" class="show-flex-r a-c">
                                    <div class="zr-link-p unline m-5" @click="scope.row.is_edit = true">修改</div>
                                    <div class="zr-link-d unline m-5" @click="delItem(scope.row)">删除</div>   
                                </div>
                                <div v-if="scope.row.is_edit" class="show-flex-r a-c">
                                    <div class="zr-link-p unline m-5" @click="toEditItem(scope.row)">保存</div>
                                    <div class="zr-link-w unline m-5" @click="scope.row.is_edit = false">取消</div>
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
import { pageRegionIndex, deleteRegionIndex, editRegionIndex } from '@/api/assess/regionIndex'
export default {
    mixins: [reportMixins],
    data() {
        return {}
    },
    methods: {
        getList(searchData) {
            this.getTableData(pageRegionIndex, searchData, false, (i) => {
                i.is_edit = false
                i.edit_score = i.score
            })
        },
        delItem(item) {
            let _this = this
            new Promise((res, rej) => {
                _this.useDia = this.$dialog({
                    header: '系统提示',
                    body: `是否确认删除名称为"${item.month}"的数据项？`,
                    style: 'color: rgba(0, 0, 0, 0.6)',
                    onConfirm: ({ e }) => {
                        res()
                    }
                })
            })
            .then(d => {
                _this.useDia.hide()
                return deleteRegionIndex(item.id)
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        },
        toEditItem(item) {
            let _this = this
            let upData = {
                id: item.id,
                score: item.edit_score
            }
            editRegionIndex(upData)
            .then(d => {
                _this.refushPage()
            })
        }
    }
}
</script>
