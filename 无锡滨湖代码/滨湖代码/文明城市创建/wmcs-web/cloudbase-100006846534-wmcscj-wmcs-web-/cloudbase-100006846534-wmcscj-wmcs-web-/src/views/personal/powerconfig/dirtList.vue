<template>
    <baseSearchForm ref="searchTable" v-loading="v_page_loading" class="base-account-list-page" @getList="getList" @search="startSearch" @reset="resetQuery">
        <div slot="customQuery" class="listSearch per-account-list">
            <div class="search-line">
                <normalSearch
                    ref="acc"
                    keyName="name"
                    placName="请输入字典名称"
                    labelName="字典名称"
                />
                <normalSearch
                    ref="acc"
                    keyName="code"
                    placName="请输入字典类型"
                    labelName="字典类型"
                />
            </div>
        </div>
        <div slot="table">
            <div class="show-flex-r j-e-2 margin-bottom-10">
                <t-button @click="showAddNewDirtType">+ 新增字典类型</t-button>
            </div>
            <el-table v-loading="tableListLoding" :data="tableData" style="width: 100%" stripe>
                <el-table-column label="字典名称" prop="dictName" min-width="200" />
                <el-table-column label="字典类型" prop="dictType" min-width="200" />
                <el-table-column label="状态" prop="status" width="100">
                    <template slot-scope="scope">
                        <div class="show-flex-r">
                            <el-tag v-if="scope.row.status == 0" type="primary" size="small">启用</el-tag>
                            <el-tag v-if="scope.row.status == 1" type="danger" size="small">停用</el-tag>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column label="创建时间" prop="createTime" width="170" />
                <el-table-column label="操作" width="300" fixed="right">
                    <template slot-scope="scope">
                        <div class="show-flex-r">
                            <div class="zr-link-p unline m-5" @click="showEdit(scope.row)">维护内容</div>
                            <div class="zr-link-p unline m-5" @click="showEditName(scope.row)">修改名称</div>
                            <div class="zr-link-d unline m-5" @click="delItem(scope.row)">删除</div>
                        </div>
                    </template>
                </el-table-column>
            </el-table>
            <!-- 新增字典类型 -->
            <addDirtType v-if="show_add_new" :show="show_add_new" @cancel="show_add_new = false" @submitForm="addDirtType" />
            <!-- 修改字典类型 -->
            <addDirtType v-if="show_edit_new" ref="edittype" :show="show_edit_new" useType="edit" @cancel="show_edit_new = false" @submitForm="editDirtType" />
            <!-- 编辑字典内容 -->
            <editDirt v-if="show_edit" :show="show_edit" :item="nowItem" @cancel="show_edit = false" @submitForm="editDirt" />
        </div>
    </baseSearchForm>
</template>
<script>
import addDirtType from './addDirtType.vue'
import editDirt from './editDirt.vue'
import { getDirtList, apiAddDirtType, apiDelDirtType, apiChangeDirtList, apiChangeDirtType } from '@/api/personal'
import { reportMixins } from '@/utils/mixins'
export default {
    components: { addDirtType, editDirt },
    mixins: [reportMixins],
    data() {
        return {
            v_page_loading: false,
            show_add_new: false,
            show_edit_new: false,
            show_edit: false,
            nowItem: {}
        }
    },
    methods: {
        getList(search) {
            this.getTableData(getDirtList, search)
            // this.tableData = [{}]
        },
        showAddNewDirtType() { this.show_add_new = true },
        showEdit(item) {
            this.nowItem = item
            this.show_edit = true
        },
        showEditName(item) {
            this.show_edit_new = true
            this.$nextTick(() => {
                this.$refs.edittype.initItem(JSON.parse(JSON.stringify(item)))
            })
        },
        editDirtType(item) {
            // 修改字典大类(基础内容)
            let _this = this
            if (this.v_page_loading) return
            this.v_page_loading = true
            apiChangeDirtType(item)
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.v_page_loading = false
                _this.show_edit_new = false
                _this.refushPage()
            })
            .finally(e => {
                _this.v_page_loading = false
            })
        },
        addDirtType(item) {
            // 添加字典大类
            let _this = this
            if (this.v_page_loading) return
            this.v_page_loading = true
            apiAddDirtType(item)
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.v_page_loading = false
                _this.show_add_new = false
                _this.startSearch()
            })
            .finally(e => {
                _this.v_page_loading = false
            })
        },
        editDirt(list) {
            console.log(list)
            if (list.length == 0) {
                this.show_edit = false
                return
            }
            let _this = this
            if (this.v_page_loading) return
            this.v_page_loading = true
            apiChangeDirtList(list)
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.v_page_loading = false
                _this.show_edit = false
                _this.refushPage()
            })
            .finally(e => {
                _this.v_page_loading = false
            })
        },
        delItem(item) {
            let _this = this
            new Promise((res, rej) => {
                _this.useDia = this.$dialog({
                    header: '系统提示',
                    body: `是否确认删除名称为"${item.dictName}"的数据项？`,
                    style: 'color: rgba(0, 0, 0, 0.6)',
                    onConfirm: ({ e }) => {
                        res()
                    }
                })
            })
            .then(d => {
                _this.useDia.hide()
                return apiDelDirtType(item.id)
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        }
    }
}
</script>
