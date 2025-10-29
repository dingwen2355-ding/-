<template>
    <baseKanbanTd title="考核周期">
        <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery" noSearch>
            <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                    <normalSearch ref="checkPoints" labelName="点位名称" keyName="checkPoints" placName="请输入点位名称" />
                    <normalSearch ref="content" labelName="存在问题" keyName="content" placName="请输入关键字" />
                </div>
            </div>
            <div slot="table">
                <div class="show-flex-r j-e-2 margin-bottom-10">
                    <t-button @click="toAddNew()" class="margin-right-20" >+ 新增 </t-button>
                    <!-- <t-button @click="toSynchCycle()" class="margin-right-20" > 同步数据</t-button> -->
                </div>
                <div>
                    <el-table ref="listtable" v-loading="tableListLoding" stripe :data="tableData">
                        <el-table-column label="考核周期" prop="name" min-width="200" />
                        <el-table-column label="开始时间" prop="beginTime" width="250" />
                        <el-table-column label="结束时间" prop="finishTime" width="250" />
                        <el-table-column label="创建时间" prop="createTime" width="250" />
                        <el-table-column label="操作" width="150" fixed="right">
                            <template slot-scope="scope">
                                <div class="show-flex-r a-c">
                                    <div class="zr-link-p unline m-5" @click="toInfo(scope.row)">详情</div>
                                    <div class="zr-link-h unline m-5" @click="toEditItem(scope.row)">修改</div>
                                    <div class="zr-link-d unline m-5" @click="delItem(scope.row)">删除</div>
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
            </div>
        </baseSearchForm>
    </baseKanbanTd>
</template>
<script>
import { reportMixins } from '@/utils/mixins'
import { pageAssessCycle, deleteAssessCycle, synchCycle } from '@/api/workflow/assessCycle'
export default {
    mixins: [reportMixins],
    data() {
        return {}
    },
    methods: {
        getList(searchData) {
            this.getTableData(pageAssessCycle, searchData)
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
                return deleteAssessCycle(item.id)
            })
            .then(d => {
                    _this.useDia.hide()
                })
                .then(d => {
                    _this.$successMessage('操作成功!')
                    _this.refushPage()
                })
        },
        toInfo(item) {
            this.$router.push(
                `./_info?id=${item.id}`
            );
        },
        toSynchCycle() {
            let _this = this;
            synchCycle().then(data => {
                _this.getList(searchData);
            });
        },
    }
}
</script>
