<template>
    <div class="show-flex-r" style="height: 100%;">
        <div v-loading="tableListLoding" class="show-flex-c" style="width: 330px;margin-right:10px;height: 100%;border-right: 10px solid #eceef5;">
            <!-- 类型搜索 -->
            <div class="flex-1" style="background: #ffffff;overflow: auto;padding: 10px;">
                <div class="show-flex-r margin-bottom-10 margin-top-5" style="padding: 0 10px;">
                    <el-input v-model="filterText" size="mini" clearable placeholder="请输入类型名称" />
                </div>
                <el-tree
                    ref="typeTree"
                    :props="props"
                    :data="typeList"
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
            <baseKanbanTd title="指标类型">
                <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
                    <div slot="customQuery" class="listSearch padding-left-10">
                        <div class="search-line">
                            <normalSearch ref="name" labelName="检查内容" keyName="name" placName="请输入检查内容" />
                        </div>
                    </div>
                    <div slot="table">
                        <div class="show-flex-r j-e-2 margin-bottom-10">
                            <t-button class="margin-right-10" @click="toAddNew()">+ 新增</t-button>
                            <!-- <t-button class="margin-right-10" @click="toSynchCheckItem()">同步数据</t-button> -->
                        </div>
                        <el-table
                            v-loading="tableListLoding"
                            stripe
                            :data="tableData"
                        >
                            <el-table-column label="检查内容" prop="name" min-width="280" />
                            <!-- <el-table-column label="点位类型" prop="typeName" width="220" /> -->
                            <el-table-column label="分数" prop="score" width="80">
                                <template slot-scope="scope">
                                    <div class="show-flex-r">
                                        <span v-if="scope.row.score > 0" class="zr-link-s noc">{{ scope.row.score }}</span>
                                        <span v-if="scope.row.score < 0" class="zr-link-d noc">{{ scope.row.score }}</span>
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column label="责任单位" prop="deptNames" width="200" />
                            <el-table-column label="事件类型" prop="eventTypeName" width="200" />
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
import { listCheckPointsType, getPointsContentList, delPointsContent, synchCheckItem } from '@/api/data/checkPointsType'
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
            typeList: [],
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
            listCheckPointsType({
                pageNum: 1,
                pageSize: 1000
            })
            .then(data => {
                _this.typeList = data.data.records || []
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
                searchData['typeId'] = this.currentKey[0]
            }
            this.getTableData(getPointsContentList, searchData)
        },
        toSynchCheckItem() {
            let _this = this;
            synchCheckItem().then(data => {
                _this.getList(searchData);
            });
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
                return delPointsContent(item.id)
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        }
    }
}
</script>
