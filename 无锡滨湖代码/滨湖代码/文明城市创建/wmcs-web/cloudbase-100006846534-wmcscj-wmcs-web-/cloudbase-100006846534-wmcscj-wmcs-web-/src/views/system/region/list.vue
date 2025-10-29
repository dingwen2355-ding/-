<template>
    <baseKanbanTd title="街道/社区管理">
        <baseSearchForm ref="searchTable" noPage @getList="getList" @search="startSearch" @reset="resetQuery">
            <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                    <normalSearch ref="name" labelName="街道/社区名称" keyName="name" placName="请输入街道/社区名称" />
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
                        default-expand-all
                    >
                        <el-table-column label="街道" prop="name" min-width="200">
                            <template slot-scope="scope">
                                <div
                                    class="show-flex-r a-c"
                                    :class="{'first-indent': scope.row.zr_first_level && (!scope.row.children || scope.row.children.length == 0)}"
                                    style="display: inline-flex;"
                                >{{ scope.row.name }}</div>
                            </template>
                        </el-table-column>
                        <el-table-column label="街道全称" prop="allname" min-width="300" />
                        <el-table-column label="创建时间" prop="createTime" min-width="170" />
                        <el-table-column label="操作" width="180" fixed="right">
                            <template slot-scope="scope">
                                <div class="show-flex-r a-c">
                                    <div class="zr-link-p unline m-5" @click="toInfoItem(scope.row)">详情</div>
                                    <div class="zr-link-p unline m-5" @click="toEditItem(scope.row)">修改</div>
                                    <div class="zr-link-p unline m-5" @click="toAddNewNext(scope.row)">新增</div>
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
import { deleteDept, pageDeptRegion } from '@/api/system/dept'
export default {
    mixins: [reportMixins],
    data() {
        return {}
    },
    methods: {
        getList(searchData) {
            this.getTableData(pageDeptRegion, searchData)
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
                return deleteDept(item.id)
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        }
    }
}
</script>
