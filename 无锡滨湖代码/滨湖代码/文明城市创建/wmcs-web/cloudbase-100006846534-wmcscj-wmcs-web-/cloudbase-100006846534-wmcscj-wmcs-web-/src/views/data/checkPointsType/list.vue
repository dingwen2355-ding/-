<template>
    <baseKanbanTd title="督察点位类型">
        <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
            <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                    <normalSearch ref="name" labelName="点位类型" keyName="name" placName="请输入点位类型名称" />
                </div>
            </div>
            <div slot="table">
                <div class="show-flex-r j-e-2 margin-bottom-10">
                    <t-button @click="toAddNew()">+ 新增</t-button>
                    <!-- <t-button @click="toSynchPointsType()">同步数据</t-button> -->
                </div>
                <div>
                    <el-table
                        ref="listtable"
                        v-loading="tableListLoding"
                        stripe
                        :data="tableData"
                    >
                        <!-- <el-table-column label="点位类型编号" prop="id" width="200" /> -->
                        <el-table-column label="检查类型名称" prop="name" show-overflow-tooltip min-width="400" />
                        <el-table-column label="排序" prop="orderNum" show-overflow-tooltip width="100" />
                        <el-table-column label="查看" prop="orderNum" show-overflow-tooltip width="120">
                            <template slot-scope="scope">
                                <div class="zr-link-p unline" @click="checkChart(scope.row)">类型指数</div>
                            </template>
                        </el-table-column>
                        <el-table-column label="创建时间" prop="createTime" show-overflow-tooltip width="170" />
                        <el-table-column label="操作" show-overflow-tooltip width="150" fixed="right">
                            <template slot-scope="scope">
                                <div class="show-flex-r a-c">
                                    <!-- <div class="zr-link-p unline m-5" @click="nextItem(scope.row)">检查内容</div> -->
                                    <div class="zr-link-p unline m-5" @click="toInfoItem(scope.row)">查看</div>
                                    <div class="zr-link-h unline m-5" @click="toEditItem(scope.row)">编辑</div>
                                    <div class="zr-link-d unline m-5" @click="delItem(scope.row)">删除</div>
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
            </div>
        </baseSearchForm>
        <!-- 添加 -->
        <addType
            v-if="show_add_new"
            useType="add"
            :show="show_add_new"
            @cancel="show_add_new = false"
            @finish="startSearch"
        />
        <!-- 编辑 -->
        <addType
            v-if="show_edit_new"
            ref="edittype"
            useType="edit"
            :show="show_edit_new"
            @cancel="show_edit_new = false"
            @finish="refushPage"
        />
        <!-- 详情 -->
        <addType
            v-if="show_info_new"
            ref="infotype"
            useType="info"
            :show="show_info_new"
            @cancel="show_info_new = false"
        />
        <!-- 报表 -->
        <itemChart
            v-if="dia_show_chart"
            :show="dia_show_chart"
            :task="chartItem"
            @cancel="() => { dia_show_chart = false }"
        />
    </baseKanbanTd>
</template>
<script>
import { reportMixins } from '@/utils/mixins'
import { listCheckPointsType, deleteCheckPointsType, synchPointsType } from '@/api/data/checkPointsType'
import itemChart from './itemChart.vue'
import addType from './addType.vue'
export default {
    components: { addType, itemChart },
    mixins: [reportMixins],
    data() {
        return {
            show_add_new: false,
            show_edit_new: false,
            show_info_new: false,
            dia_show_chart: false,
            chartItem: null
        }
    },
    methods: {
        getList(searchData) {
            this.getTableData(listCheckPointsType, searchData)
        },
        addItem() {
            this.show_add_new = true
        },
        infoItem(item) {
            this.show_info_new = true
            this.$nextTick(() => {
                this.$refs.infotype.initItem(item)
            })
        },
        editItem(item) {
            this.show_edit_new = true
            this.$nextTick(() => {
                this.$refs.edittype.initItem(item)
            })
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
                return deleteCheckPointsType(item.id)
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        },
        nextItem(item) {
            this.$router.push(`./_info?id=${item.id}`)
        },
        checkChart(item) {
            this.chartItem = item
            this.dia_show_chart = true
        },
        toSynchPointsType() {
            let _this = this;
            synchPointsType().then(data => {
                _this.getList(searchData);
            });
        }
    }
}
</script>
