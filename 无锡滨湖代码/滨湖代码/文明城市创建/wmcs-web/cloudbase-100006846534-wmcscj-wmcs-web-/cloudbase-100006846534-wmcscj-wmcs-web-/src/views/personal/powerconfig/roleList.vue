<template>
    <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
        <div slot="customQuery" class="listSearch">
            <div class="search-line">
                <normalSearch
                    ref="acc"
                    keyName="name"
                    placName="请输入角色名称"
                    labelName="角色名称"
                />
            </div>
        </div>
        <div slot="table">
            <div class="show-flex-r j-e-2 margin-bottom-10">
                <t-button @click="addNewRole()">+ 新增角色</t-button>
            </div>
            <el-table v-loading="tableListLoding" :data="tableData" style="width: 100%" stripe>
                <el-table-column label="角色名称" prop="name" min-width="200" />
                <el-table-column label="权限字符" prop="code" min-width="100" />
                <el-table-column label="排序" prop="orderNum" width="100" />
                <el-table-column label="数据权限" prop="dataScope" width="200">
                    <template slot-scope="scope">
                        <div>
                            <el-tag v-if="scope.row.dataScope == 1">所有数据权限</el-tag>
                            <el-tag v-if="scope.row.dataScope == 2">自定义数据权限</el-tag>
                            <el-tag v-if="scope.row.dataScope == 3">本部门数据权限</el-tag>
                            <el-tag v-if="scope.row.dataScope == 4">本部门及以下数据权限</el-tag>
                            <el-tag v-if="scope.row.dataScope == 5">仅本人数据权限</el-tag>
                        </div>
                    </template>    
                </el-table-column>
                <el-table-column label="创建时间" prop="createTime" width="180" />
                <el-table-column label="操作" width="200" fixed="right">
                    <template slot-scope="scope">
                        <div class="show-flex-r">
                            <div class="zr-link-p unline m-5" @click="edit(scope.row)">编辑</div>
                            <div class="zr-link-d unline m-5" @click="delItem(scope.row)">删除</div>
                        </div>
                    </template>
                </el-table-column>
            </el-table>
            <!-- 添加新角色 -->
            <addNewRole
                v-if="addNewRole_show"
                :show="addNewRole_show"
                :account="nowAccount"
                :confirmType="confirmType"
                @submitForm="finishUp"
                @cancel="addNewRole_show = false"
            />
        </div>
    </baseSearchForm>
</template>
<script>
import { getRoleList, delRole } from '@/api/personal'
import { reportMixins } from '@/utils/mixins'
import addNewRole from './addNewRole.vue'
export default {
    components: { addNewRole },
    mixins: [reportMixins],
    data() {
        return {
            addNewRole_show: false,
            confirmType: 'Create',
            nowAccount: {}
        }
    },
    methods: {
        getList(search) {
            search['platform'] = 1
            this.getTableData(getRoleList, search)
        },
        addNewRole() {
            this.confirmType = 'Create'
            this.addNewRole_show = true
        },
        enableAcc(item) {
            let message = `是否确认${item.disabled ? '启用' : '禁用'}该角色?`
            this.$confirm(message, '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            })
            .then(() => {
                // patchRole(item.id).then(() => {
                //   this.startSearch()
                // })
            })
        },
        edit(row) {
            this.confirmType = 'Update'
            this.nowAccount = row
            this.addNewRole_show = true
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
                return delRole(item.id)
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        },
        finishUp() {
            this.$successMessage('操作成功!')
            this.addNewRole_show = false
            this.startSearch()
        }
    }
}
</script>
