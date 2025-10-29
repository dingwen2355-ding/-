<template>
    <baseSearchForm ref="searchTable" v-loading="v_page_loading" class="base-account-list-page" noSearch noPage @getList="getList" @search="startSearch" @reset="resetQuery">
        <div slot="customQuery" class="listSearch per-account-list">
            <div class="search-line">
                <normalSearch
                    ref="acc"
                    keyName="name"
                    placName="请输入资源名称"
                    labelName="资源名称"
                />
            </div>
        </div>
        <div slot="table">
            <div class="show-flex-r j-e-2 margin-bottom-10 margin-top-20">
                <changeButton
                    ref="changetabbutton"
                    keyName="platform"
                    class="margin-bottom-20"
                    :lists="tablist"
                    size="big"
                    noradis
                    @BtnClick="changeTab"
                />
            </div>
            <div class="show-flex-r j-e-2 margin-bottom-10">
                <t-button @click="toAdd()">+ 新增资源</t-button>
            </div>
            <el-table
                v-loading="tableListLoding"
                :data="tableData"
                style="width: 100%"
                stripe
                row-key="id"
                :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
                :cell-class-name="cellClassName"
                :expand-row-keys="expandRowKeys"
                @expand-change="changeExpand"
            >
                <!-- default-expand-all -->
                <el-table-column label="资源名称" prop="name" min-width="150" fixed="left">
                    <template slot-scope="scope">
                        <div
                            class="show-flex-r a-c unline noc"
                            :class="{'zr-link-p': scope.row.type == 'C', 'zr-link-i': scope.row.type == 'M', 'zr-link-s': scope.row.type == 'F', 'first-indent': scope.row.zr_first_level && (!scope.row.children || scope.row.children.length == 0)}"
                            style="display: inline-flex;"
                        >{{ scope.row.name }}</div>
                    </template>
                </el-table-column>
                <el-table-column label="图标" width="80">
                    <template slot-scope="scope">
                        <div v-if="scope.row.icon" class="show-flex-r d-c">
                            <i :class="scope.row.icon" style="font-size: 18px;" />
                        </div>
                    </template>
                </el-table-column>
                <el-table-column label="资源类型" prop="type" width="100">
                    <template slot-scope="scope">
                        <div class="show-flex-r">
                            <el-tag v-if="scope.row.type == 'M'" type="info" size="small">目录</el-tag>
                            <el-tag v-if="scope.row.type == 'C'" type="primary" size="small">菜单</el-tag>
                            <el-tag v-if="scope.row.type == 'F'" type="success" size="small">功能</el-tag>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column label="排序" prop="orderNum" width="80" />
                <el-table-column label="资源链接" prop="link" min-width="300" />
                <el-table-column label="权限字符" prop="perms" min-width="200" />
                <el-table-column label="是否显示" prop="visible" width="100">
                    <template slot-scope="scope">
                        <div class="show-flex-r">
                            <el-tag v-if="scope.row.visible == 0" type="primary" size="small">是</el-tag>
                            <el-tag v-if="scope.row.visible == 1" type="danger" size="small">否</el-tag>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column v-if="!$store.state.mtablel" label="操作" width="300" fixed="right">
                    <template slot-scope="scope">
                        <div class="show-flex-r">
                            <div class="zr-link-p unline m-5" @click="toEdit(scope.row)">编辑</div>
                            <div class="zr-link-p unline m-5" @click="toNextAdd(scope.row)">添加子类</div>
                            <div class="zr-link-d unline m-5" @click="delItem(scope.row)">删除</div>
                        </div>
                    </template>
                </el-table-column>
            </el-table>
            <!-- 新增字典类型 -->
            <addResource
                v-if="show_add_new"
                ref="addresource"
                :show="show_add_new"
                useType="add"
                :platform=nowPlatform
                @cancel="show_add_new = false"
                @submitForm="finishUp"
            />
            <!-- 编辑字典内容 -->
            <addResource
                v-if="show_edit"
                ref="editresource"
                :show="show_edit"
                useType="edit"
                :platform=nowPlatform
                @cancel="show_edit = false"
                @submitForm="finishUp"
            />
        </div>
    </baseSearchForm>
</template>
<script>
import addResource from './addResource.vue'
import { getResourceList, delResource } from '@/api/personal'
import { reportMixins } from '@/utils/mixins'
export default {
    components: { addResource },
    mixins: [reportMixins],
    data() {
        return {
            nowPlatform: 1,
            tablist: [
                { label: 'PC平台', value: 1 },
                { label: '政务微信', value: 2 }
            ],
            v_page_loading: false,
            show_add_new: false,
            show_edit: false,
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
        changeTab(i, item) {
            this.nowPlatform = item.value
            this.startSearch()
        },
        cellClassName({ row, column, rowIndex, columnIndex }) {
            if (columnIndex == 0) return 'cell-left'
        },
        getList(search) {
            this.getTableData(getResourceList, search)
        },
        toAdd() {
            this.show_add_new = true
        },
        toNextAdd(item) {
            this.show_add_new = true
            this.$nextTick(() => {
                this.$refs.addresource.initParentTypeId(item.id, item.name)
            })
        },
        toEdit(item) {
            this.show_edit = true
            this.$nextTick(() => {
                this.$refs.editresource.initItem(item)
            })
        },
        delItem(item) {
            let _this = this
            new Promise((res, rej) => {
                _this.useDia = this.$dialog({
                    header: '系统提示',
                    body: `是否确认删除名称为"${item.name}"的数据项及其所有子项？`,
                    style: 'color: rgba(0, 0, 0, 0.6)',
                    onConfirm: ({ e }) => {
                        res()
                    }
                })
            })
            .then(d => {
                _this.useDia.hide()
                return delResource(item.id)
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        },
        showAddNewDirtType() { this.show_add_new = true },
        showEdit(item) {
            this.nowItem = item
            this.show_edit = true
        },
        finishUp() {
            this.$successMessage('操作成功!')
            this.show_add_new = false
            this.show_edit = false
            this.startSearch()
        }
    }
}
</script>
