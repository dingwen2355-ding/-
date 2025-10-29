<template>
    <baseKanbanTd title="事项类别">
        <baseSearchForm ref="searchTable" noPage @getList="getList" @search="startSearch" @reset="resetQuery">
            <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                    <normalSearch ref="name" labelName="类别名称" keyName="name" placName="请输入类别名称" />
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
                        row-key="id"
                        :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
                        :cell-class-name="treeCellClassName"
                        :expand-row-keys="expandRowKeys"
                        @expand-change="changeExpand"
                    >
                        <el-table-column label="类别名称" prop="name" min-width="500">
                            <template slot-scope="scope">
                                <div
                                    class="show-flex-r a-c"
                                    :class="{'first-indent': scope.row.zr_first_level && (!scope.row.children || scope.row.children.length == 0)}"
                                    style="display: inline-flex;"
                                >{{ scope.row.name }}</div>
                            </template>
                        </el-table-column>
                        <el-table-column label="排序" prop="orderNum" width="100" />
                        <el-table-column label="操作" width="180" fixed="right">
                            <template slot-scope="scope">
                                <div class="show-flex-r a-c">
                                    <div class="zr-link-p unline m-5" @click="toInfoItem(scope.row)">详情</div>
                                    <div class="zr-link-h unline m-5" @click="toEditItem(scope.row)">修改</div>
                                    <div class="zr-link-s unline m-5" @click="toAddNewNext(scope.row)">新增</div>
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
import { pageEventType, deleteEventType } from '@/api/workflow/eventType'
export default {
    mixins: [reportMixins],
    data() {
        return {
            expandRowKeys: []
        }
    },
    methods: {
        changeExpand(row, expandRows) {
            // 展开行回调
            if (expandRows) {
                // 必须是字符串
                this.expandRowKeys.push(row.id + '')
            } else {
                this.expandRowKeys = this.expandRowKeys.filter(i => { return i != row.id })
            }
        },
        getList(searchData) {
            this.getTableData(pageEventType, searchData)
        },
        toAddNewNext(item) {
            this.$router.push(`./_add?parentId=${item.id}`)
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
                return deleteEventType(item.id)
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        }
    }
}
</script>
