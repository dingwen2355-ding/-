<template>
    <div class="show-flex-r" style="height: 100%;">
        <div v-loading="tableListLoding" class="show-flex-c" style="width: 300px;margin-right:10px;height: 100%;border-right: 10px solid #eceef5;">
            <!-- 部门搜索 -->
            <div class="flex-1" style="background: #ffffff;overflow: auto;padding: 10px;">
                <div class="show-flex-r margin-bottom-10 margin-top-5" style="padding: 0 10px;">
                    <el-input v-model="filterText" size="mini" clearable placeholder="请输入部门名称" />
                </div>
                <el-tree
                    ref="typeTree"
                    :props="props"
                    :data="deptList"
                    show-checkbox
                    check-strictly
                    node-key="id"
                    :filter-node-method="filterNode"
                    default-expand-all
                    :default-checked-keys="currentKey"
                    @check="changeCheckType"
                />
            </div>
        </div>
        <div class="flex-1 w-0 h-100" style="overflow: auto;">
            <baseKanbanTd title="员工管理">
                <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
                    <div slot="customQuery" class="listSearch padding-left-10">
                        <div class="search-line">
                            <normalSearch ref="name" labelName="用户名称" keyName="nickName" placName="请输入用户名称" />
                            <normalSearch ref="userName" labelName="登录账号" keyName="userName" placName="请输入登录账号" />
                            <normalSearch ref="phonenumber" labelName="手机号码" keyName="phonenumber" placName="手机号码" />
                        </div>
                    </div>
                    <div slot="table">
                        <div class="show-flex-r j-e-2 margin-bottom-10">
                            <t-button @click="toAddNew()">+ 新增</t-button>
                        </div>
                        <el-table
                            v-loading="tableListLoding"
                            stripe
                            :data="tableData"
                        >
                            <el-table-column label="登录账号" prop="userName" min-width="200" />
                            <el-table-column label="姓名" prop="nickName" min-width="200" />
                            <el-table-column label="联系电话" prop="phonenumber" min-width="150" />
                            <el-table-column label="所属机构" prop="deptName" min-width="220" />
                            <el-table-column label="角色" prop="roleNames" min-width="220">
                                <template slot-scope="scope">
                                    <div v-if="scope.row.roleNames != null">
                                        <el-tag 
                                            v-for="(t, index) in scope.row.roleNames.split(',')"
                                            :key="index"
                                            size="small"
                                            class="margin-right-10 margin-bottom-10"
                                        >
                                            {{ t }}
                                        </el-tag>
                                    </div>
                                </template>
                            </el-table-column>
                            <!-- <el-table-column label="邮箱" prop="email" width="150" /> -->
                            <el-table-column label="操作" width="150" fixed="right">
                                <template slot-scope="scope">
                                    <div class="show-flex-r a-c">
                                        <div class="zr-link-p unline m-5" @click="toInfoItem(scope.row)">详情</div>
                                        <div class="zr-link-p unline m-5" v-if="scope.row.id != 1" @click="toEditItem(scope.row)">修改</div>
                                        <div class="zr-link-d unline m-5" v-if="scope.row.id != 1" @click="delItem(scope.row)">删除</div>
                                    </div>
                                </template>
                            </el-table-column>
                        </el-table>
                    </div>
                </baseSearchForm>
            </baseKanbanTd>
        </div>
    </div>
</template>
<script>
import { reportMixins } from '@/utils/mixins'
import { pageDept } from '@/api/system/dept'
import { pageEmployee, deleteEmployee } from '@/api/system/employee'
export default {
    mixins: [reportMixins],
    data() {
        return {
            filterText: '',
            props: {
                label: 'name',
                children: 'children'
            },
            includeChildren: false,
            deptList: [],
            currentKey: []
        }
    },
    watch: {
        filterText(val) {
            this.$refs.typeTree.filter(val)
        }
    },
    mounted() {
        this.getAllType()
    },
    methods: {
        getAllType() {
            let _this = this
            pageDept({}).then(data => {
                _this.deptList = data.data.records || []
            })
        },
        filterNode(value, data) {
            if (!value) return true
            return data.name.indexOf(value) !== -1
        },
        changeCheckType(node, list) {
            let arr = []
            if (list.checkedKeys.length > 0) { arr = [node.id] }
            this.$nextTick(() => {
                this.currentKey = arr
                this.$refs.typeTree.setCheckedKeys(arr)
                this.startSearch()
            })
        },
        getList(searchData) {
            if (this.currentKey.length > 0) {
                searchData['deptId'] = this.currentKey[0]
            }
            this.getTableData(pageEmployee, searchData)
        },
        delItem(item) {
            let _this = this
            new Promise((res, rej) => {
                _this.useDia = this.$dialog({
                    header: '系统提示',
                    body: `是否确认删除名称为"${item.nickName}"的数据项？`,
                    style: 'color: rgba(0, 0, 0, 0.6)',
                    onConfirm: ({ e }) => {
                        res()
                    }
                })
            })
            .then(d => {
                _this.useDia.hide()
                return deleteEmployee(item.id)
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        }
    }
}
</script>
