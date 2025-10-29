<template>
    <div class="show-flex-r" style="height: 100%;">
        <div v-loading="tableListLoding" class="show-flex-c" style="width: 400px;margin-right:10px;height: 100%;border-right: 10px solid #eceef5;">
            <!-- 类型搜索 -->
            <div class="flex-1" style="background: #ffffff;overflow: auto;padding: 10px;">
                <div class="show-flex-r margin-bottom-10 margin-top-5" style="padding: 0 10px;">
                    <el-input v-model="filterText" size="mini" clearable placeholder="请输入类型名称" />
                </div>
                <el-tree
                    ref="indicatorList"
                    :props="props"
                    :data="indicatorList"
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
            <baseKanbanTd title="指标管理">
                <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
                    <div slot="customQuery" class="listSearch padding-left-10">
                        <div class="search-line">
                            <normalSearch ref="name" labelName="指标名称" keyName="name" placName="请输入指标名称" />
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
                            <el-table-column label="指标类型" prop="indicatorTypeName" min-width="300" />
                            <el-table-column label="指标名称" prop="name" min-width="200" />
                            <el-table-column label="最小范围值" prop="minVal" width="200" />
                            <el-table-column label="最大范围值" prop="maxVal" width="200" />
                            <el-table-column label="分数" prop="score" width="200" />
                            <el-table-column label="创建时间" prop="createTime" min-width="200" />
                            <el-table-column label="操作" width="150" fixed="right">
                                <template slot-scope="scope">
                                    <div class="show-flex-r a-c">
                                        <div class="zr-link-p unline m-5" @click="toInfoItem(scope.row)">详情</div>
                                        <div class="zr-link-p unline m-5" @click="toEditItem(scope.row)">修改</div>
                                        <div class="zr-link-d unline m-5" @click="delItem(scope.row)">删除</div>
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
import { pageIndicatorType } from '@/api/quota/indicatorType'
import { pageIndicator, deleteIndicator } from '@/api/quota/indicator'
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
            indicatorList: [],
            currentKey: []
        }
    },
    watch: {
        filterText(val) {
            this.$refs.indicatorList.filter(val)
        }
    },
    mounted() {
        this.getAllType()
    },
    methods: {
        getAllType() {
            let _this = this
            pageIndicatorType({
                pageNum: 1,
                pageSize: 1000
            })
            .then(data => {
                _this.indicatorList = data.data.records || []
            })
        },
        filterNode(value, data) {
            if (!value) return true
            return data.name.indexOf(value) !== -1
        },
        changeCheckType(node, list) {
            let arr = node.id
            if (list.checkedKeys.length > 0) { arr = [node.id] }
            this.$nextTick(() => {
                this.currentKey = arr
                this.$refs.indicatorList.setCheckedKeys(arr)
                this.startSearch()
            })
        },
        getList(searchData) {
            if (this.currentKey.length > 0) {
                searchData['indicatorTypeId'] = this.currentKey[this.currentKey.length-1]
            }
            this.getTableData(pageIndicator, searchData)
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
                return deleteIndicator(item.id)
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        }
    }
}
</script>
