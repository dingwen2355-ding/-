<template>
    <baseKanbanTd title="知识库管理">
        <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
            <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                    <normalSearch ref="name" labelName="名称" keyName="name" placName="请输入名称" />
                    <normalSearch ref="content" labelName="内容" keyName="content" placName="请输入内容" />
                </div>
            </div>
            <div slot="table">
                <div class="show-flex-r j-e-2 margin-bottom-10">
                    <t-button class="margin-right-10" @click="toAddNew()">+ 新增</t-button>
                    <baseImportExcel
                        :outImport="excel2Arr"
                        :getKeyArray="excelKeyArr"
                        mubanTitle="知识库"
                        @success="startSearch"
                    />
                    <t-button theme="success" @click="toExport()">导出</t-button>
                </div>
                <div>
                    <el-table
                        ref="listtable"
                        v-loading="tableListLoding"
                        stripe
                        :data="tableData"
                    >
                        <el-table-column label="选择" width="50">
                            <template slot="header" slot-scope="scope">
                                <el-checkbox v-model="zrAllSelect" :indeterminate="zrisIndeterminate" :error="scope.row" @change="handleCheckAllChange" />
                            </template>
                            <template slot-scope="scope">
                                <el-checkbox v-model="scope.row.zr_select" @change="changeZrSelect" />
                            </template>
                        </el-table-column>
                        <el-table-column label="名称" prop="name" min-width="200" />
                        <el-table-column label="内容" prop="content" min-width="300" />
                        <el-table-column label="创建日期" prop="createTime" width="200" />
                        <el-table-column label="操作" width="200" fixed="right">
                            <template slot-scope="scope">
                                <div class="show-flex-r a-c">
                                    <div class="zr-link-p unline m-5" @click="toInfoItem(scope.row)">详情</div>
                                    <div class="zr-link-h unline m-5" @click="toEditItem(scope.row)">编辑</div>
                                    <div class="zr-link-d unline m-5" @click="delItem(scope.row)">删除</div>
                                    <!-- <div class="zr-link-w unline m-5" @click="downLoad(scope.row)">下载附件</div> -->
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
import { listKnowledge, deleteKnowledge, batchSaveKnowledge, downloadZip } from '@/api/data/knowledge'
export default {
    mixins: [reportMixins],
    data() {
        return {}
    },
    methods: {
        getList(searchData) {
            this.getTableData(listKnowledge, searchData)
        },
        delItem(item) {
            let _this = this
            new Promise((res, rej) => {
                _this.useDia = this.$dialog({
                    header: '系统提示',
                    body: `是否确认删除名称为"${item.name}"的数据项？`,
                    style: 'color: rgba(0, 0, 0, 0.6)',
                    onConfirm: ({ e }) => {
                        res()
                    }
                })
            })
            .then(d => {
                _this.useDia.hide()
                return deleteKnowledge(item.id)
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        },
        excelKeyArr() {
            return [
                { key: 'name', name: '名称' },
                { key: 'content', name: '内容' }
            ]
        },
        excel2Arr(list) {
            let _this = this
            let arr = list.map(i => {
                let obj = {}
                _this.excelKeyArr().forEach(k => {
                    obj[k.key] = ((i[k.name] || '') + '').trim()
                })
                obj['name'] = i['名称']
                obj['content'] = i['内容']
                return obj
            })
            return batchSaveKnowledge({
                itemList: arr
            })
        },
        toExport() {
            let _this = this
            let list = this.tableData.filter(i => { return i.zr_select })
            if (list.length == 0) {
                this.$errorMessage('请选择知识库')
                return
            }
            new Promise((res, rej) => {
                _this.useDia = this.$dialog({
                    header: '系统提示',
                    body: `是否确认批量导出选中项？`,
                    style: 'color: rgba(0, 0, 0, 0.6)',
                    onConfirm: ({ e }) => {
                        res()
                    }
                })
            })
            .then(d => {
                _this.useDia.hide()
            })
            .then(d => {
                let arr = list.map((i, index) => {
                    return {
                        '序号': index + 1,
                        '名称': i.name,
                        '内容': i.content
                    }
                })
                let style = {
                    col: [10, 20, 40]
                }
                _this.export_downExcel('知识库', arr, style)
            })
        }
    }
}
</script>
