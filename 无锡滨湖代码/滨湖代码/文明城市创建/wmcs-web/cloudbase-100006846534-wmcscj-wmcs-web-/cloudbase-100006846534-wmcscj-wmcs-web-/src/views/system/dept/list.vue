<template>
    <baseKanbanTd title="单位管理">
        <baseSearchForm ref="searchTable" noPage @getList="getList" @search="startSearch" @reset="resetQuery">
            <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                    <normalSearch ref="deptName" labelName="单位名称" keyName="name" placName="请输入单位名称" />
                </div>
            </div>
            <div slot="table">
                <div class="show-flex-r j-e-2 margin-bottom-10">
                    <t-button class="margin-right-20" @click="toAddNew()">+ 新增</t-button>
                    <t-button class="margin-right-20" @click="syncDept()">更新政务组织信息</t-button>
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
                        <el-table-column label="单位名称" prop="name" min-width="300">
                            <template slot-scope="scope">
                                <div
                                    class="show-flex-r a-c"
                                    :class="{'first-indent': scope.row.zr_first_level && (!scope.row.children || scope.row.children.length == 0)}"
                                    style="display: inline-flex;"
                                >{{ scope.row.name }}</div>
                            </template>
                        </el-table-column>
                        <el-table-column label="上级单位" prop="parentName" min-width="200" />
                        <el-table-column label="类型" prop="type" min-width="120">
                            <template slot-scope="scope">
                                <div>
                                    <span v-if="scope.row.type == 1" class="zr-link-s noc">职能部门</span>
                                    <span v-if="scope.row.type == 2" class="zr-link-p noc">区划部门</span>
                                </div>
                            </template>
                        </el-table-column>
                        <!-- <el-table-column label="联系人" prop="leader" width="150" /> -->
                        <!-- <el-table-column label="联系电话" prop="phone" width="180" /> -->
                        <el-table-column label="政务微信所属组织" prop="wechatDeptName" min-width="220" />
                        <el-table-column label="负责人" width="120">
                            <template slot-scope="scope">
                                <div class="zr-link-p" @click="getDCYList(scope.row)">
                                    {{ scope.row.volunteerCount }}
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column label="排序" prop="orderNum" min-width="100" />
                        <el-table-column label="创建时间" prop="createTime" min-width="170" />
                        <el-table-column label="操作" width="150" fixed="right">
                            <template slot-scope="scope">
                                <div class="show-flex-r a-c">
                                    <div class="zr-link-p unline m-5" @click="toEditItem(scope.row)">修改</div>
                                    <div class="zr-link-d unline m-5" @click="delItem(scope.row)">删除</div>
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>
                    <!-- 督察员列表 -->
                    <dcylist
                        v-if="dia_show_dcylist"
                        :show="dia_show_dcylist"
                        :task="nowItem"
                        @cancel="() => { dia_show_dcylist = false }"
                    />
                </div>
            </div>
        </baseSearchForm>
    </baseKanbanTd>
</template>
<script>
import { reportMixins } from '@/utils/mixins'
import { deleteDept, pageDept, syncDept } from '@/api/system/dept'
import dcylist from './dcylist.vue'
export default {
    components: { dcylist },
    mixins: [reportMixins],
    data() {
        return {
            dia_show_dcylist: false,
            nowItem: null
        }
    },
    methods: {
        getList(searchData) {
            this.getTableData(pageDept, searchData)
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
        },
        getDCYList(item) {
            this.nowItem = item
            this.dia_show_dcylist = true
        },
        syncDept() {
            let _this = this
            new Promise((res, rej) => {
                _this.useDia = this.$dialog({
                    header: '系统提示',
                    body: `是否更新政务微信数据？`,
                    style: 'color: rgba(0, 0, 0, 0.6)',
                    onConfirm: ({ e }) => {
                        res()
                    }
                })
            })
            .then(d => {
                _this.useDia.hide()
                return syncDept()
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        }
            
    }
}
</script>
