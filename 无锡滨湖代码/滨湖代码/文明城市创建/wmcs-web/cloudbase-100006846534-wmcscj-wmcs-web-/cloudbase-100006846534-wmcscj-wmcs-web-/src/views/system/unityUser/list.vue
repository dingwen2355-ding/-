<template>
    <div class="show-flex-r" style="height: 100%;">
        <div v-loading="tableListLoding" class="show-flex-c" style="width: 300px;margin-right:10px;height: 100%;border-right: 10px solid #eceef5;">
            <!-- 部门搜索 -->
            <div class="flex-1" style="background: #ffffff;overflow: auto;padding: 10px;">
                <div class="show-flex-r margin-bottom-10 margin-top-5" style="padding: 0 10px;">
                    <el-input v-model="filterText" size="mini" clearable placeholder="请输入部门名称" />
                </div>
                <el-tree
                    ref="unityDeptId"
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
            <baseKanbanTd title="统一账号">
                <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
                    <div slot="customQuery" class="listSearch padding-left-10">
                        <div class="search-line">
                            <normalSearch ref="name" labelName="用户名称" keyName="name" placName="请输入用户名称" />
                            <normalSearch ref="account" labelName="用户账号" keyName="account" placName="请输入用户账号" />
                            <normalSearch ref="majorMobile" labelName="手机号码" keyName="majorMobile" placName="手机号码" />
                        </div>
                    </div>
                    <div slot="table">
                        <div class="show-flex-r j-e-2 margin-bottom-10">
                            <t-button class="margin-right-20" @click="toSynchDept()">同步组织</t-button>
                            <t-button @click="toSynchUser()">同步用户</t-button>
                        </div>
                        <el-table
                            v-loading="tableListLoding"
                            stripe
                            :data="tableData"
                        >
                            <el-table-column label="用户账号" prop="account" min-width="200" />
                            <el-table-column label="用户名称" prop="name" min-width="150" />
                            <el-table-column label="归属部门" prop="majorDeptName" min-width="200" />
                            <el-table-column label="手机号" prop="majorMobile" min-width="150" />
                            <el-table-column label="职位" prop="majorPosition" min-width="200" />
                        </el-table>
                    </div>
                </baseSearchForm>
            </baseKanbanTd>
        </div>
    </div>
</template>
<script>
import { reportMixins } from '@/utils/mixins'
import { unifyDept, unifyUser, synchDept, synchUser } from '@/api/system/unityUser'
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
            this.$refs.unityDeptId.filter(val)
        }
    },
    mounted() {
        this.getAllType()
    },
    methods: {
        getAllType() {
            let _this = this
            unifyDept({})
            .then(data => {
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
                this.$refs.unityDeptId.setCheckedKeys(arr)
                this.startSearch()
            })
        },
        getList(searchData) {
            if (this.currentKey.length > 0) {
                searchData['majorDept'] = this.currentKey[0]
            }
            this.getTableData(unifyUser, searchData)
        },
        toSynchDept() {
            synchDept().then(data => {
                this.startSearch();
            });
        },
        toSynchUser() {
            synchUser().then(data => {
                this.startSearch();
                this.getAllType();
            });
        }
    }
}
</script>
