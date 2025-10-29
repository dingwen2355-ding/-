<template>
    <div class="show-flex-r" style="height: 100%;">
        <div v-loading="tableListLoding" class="show-flex-c" style="width: 330px;margin-right:10px;height: 100%;border-right: 10px solid #eceef5;">
            <div class="flex-1" style="background: #ffffff;overflow: auto;padding: 10px;">
                <div class="show-flex-r margin-bottom-10 margin-top-5" style="padding: 0 10px;">
                    <el-input v-model="filterText" size="mini" clearable nosearch placeholder="请输入年月" />
                </div>
                <el-tree
                    ref="typeTree"
                    :props="props"
                    :data="monthList"
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
            <baseKanbanTd title="文明程度指数">
                <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
                    <div slot="customQuery" class="listSearch padding-left-10">
                        <div class="search-line">
                            <normalSelect 
                                ref="type"
                                labelName="类型"
                                keyName="type"
                                :options="[{label: '职能部门',value:1},{label: '区划部门', value: 2}]"
                            />
                            <normalSearch ref="name" labelName="搜索项名称" keyName="name" placName="请输入搜索项名称" />
                        </div>
                    </div>
                    <div slot="table">
                        <div class="show-flex-r j-e-2 margin-bottom-10">
                            <t-button class="margin-right-20" @click="toAddNew()">+ 新增</t-button>
                            <t-button theme="primary" @click="toAllDelete()"> - 删除</t-button>
                        </div>
                        <el-table
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
                            <el-table-column label="类型" prop="type" width="150">
                                <template slot-scope="scope">
                                    <div>
                                        <span v-if="scope.row.type == 1" class="zr-link-s noc">职能部门</span>
                                        <span v-if="scope.row.type == 2" class="zr-link-p noc">区划部门</span>
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column label="名称" prop="regionName" width="220" />
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
                </baseSearchForm>
            </baseKanbanTd>
        </div>
    </div>
</template>
<script>
import { reportMixins } from '@/utils/mixins'
import { getMonthList, pageCivilizationLevel, editCivilizationLevel, deleteCivilizationLevel, deleteAllCivilizationLevel } from '@/api/assess/civilizationLevel'
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
            monthList: [],
            currentKey: [],
            dataList: null,
            regionNames: ''
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
            _this.monthList = []
            getMonthList({})
            .then(data => {
                data.data.forEach(d => {
                    let t = {id: d, name: d}
                    _this.monthList.push(t)
                });

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
                searchData['month'] = this.currentKey[0]
            }
            this.getTableData(pageCivilizationLevel, searchData, false, (i) => {
                i.is_edit = false
                i.edit_score = i.score
            })
        },
        toEditItem(item) {
            let _this = this
            let upData = {
                id: item.id,
                score: item.edit_score
            }
            editCivilizationLevel(upData)
            .then(d => {
                _this.refushPage()
            })
        },
        delItem(item) {
            let _this = this
            new Promise((res, rej) => {
                _this.useDia = this.$dialog({
                    header: '系统提示',
                    body: `是否确认删除名称为"${item.regionName}"的数据项？`,
                    style: 'color: rgba(0, 0, 0, 0.6)',
                    onConfirm: ({ e }) => {
                        res()
                    }
                })
            })
            .then(d => {
                _this.useDia.hide()
                return deleteCivilizationLevel(item.id)
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        },
        toAllDelete() {
            let _this = this
            let list = this.tableData.filter(i => { return i.zr_select })
            if (list.length == 0) return _this.$errorMessage('请选择需要删除的数据')
            this.dataList = list
            this.dia_show_pf_batch = false
            let ids = this.dataList.map(i => {return i.id })
            this.regionNames = this.dataList.map(i => { return i.regionName }).join(",")
            new Promise((res, rej) => {
                _this.useDia = this.$dialog({
                    header: '系统提示',
                    body: `是否确认删除 [ ` + this.regionNames + ` ] 的数据`,
                    style: 'color: rgba(0, 0, 0, 0.6)',
                    onConfirm: ({ e }) => {
                        res()
                    }
                })
            })
            .then(d => {
                _this.useDia.hide()
                return deleteAllCivilizationLevel({ ids: ids})
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        }
    }
}
</script>
