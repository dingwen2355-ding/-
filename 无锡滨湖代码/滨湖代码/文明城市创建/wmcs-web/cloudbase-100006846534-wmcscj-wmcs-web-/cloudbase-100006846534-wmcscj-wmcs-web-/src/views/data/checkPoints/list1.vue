<template>
    <baseKanbanTd title="督察点位">
        <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
            <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                    <normalSearch ref="name" labelName="点位名称" keyName="name" placName="请输入点位名称" />
                    <searchCheckPointsType ref="typeId" labelName="点位类型" keyName="typeId" placName="请选择点位类型" />
                </div>
            </div>
            <div slot="table">
                <div class="show-flex-r j-e-2 margin-bottom-10">
                    <t-button class="margin-right-10" @click="toAddNew()">+ 新增</t-button>
                    <!-- <t-button class="margin-right-10" @click="toSynchPoint()">同步数据</t-button> -->
                </div>
                <div>
                    <el-table
                        ref="listtable"
                        v-loading="tableListLoding"
                        stripe
                        :data="tableData"
                    >
                        <el-table-column label="点位名称" prop="name" min-width="300">
                            <template slot-scope="scope">
                                <div v-if="scope.row.checkUserName" class="zr-link-s unline">{{ scope.row.name }}</div>
                                <div v-if="!scope.row.checkUserName">{{ scope.row.name }}</div>
                            </template>
                        </el-table-column>
                        <el-table-column label="点位类型" prop="typeName" width="300" />
                        <el-table-column label="街道/社区" prop="regionName" width="220" />
                        <!-- <el-table-column label="关联设备" prop="deviceCount" width="150" /> -->
                        <!-- <el-table-column label="经度" prop="longitude" width="150" />
                        <el-table-column label="纬度" prop="latitude" width="150" /> -->
                        <el-table-column label="操作" width="200" fixed="right">
                            <template slot-scope="scope">
                                <div class="show-flex-r a-c">
                                    <div class="zr-link-p unline m-5" @click="toInfoItem(scope.row)">查看</div>
                                    <div class="zr-link-h unline m-5" @click="toEditItem(scope.row)">编辑</div>
                                    <div class="zr-link-d unline m-5" @click="delItem(scope.row)">删除</div>
                                    <div class="zr-link-w unline m-5" @click="showFZR(scope.row)">负责人</div>
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>
                    <!-- 负责人弹框 -->
                    <fuzeren
                        v-if="show_fuzeren"
                        :show="show_fuzeren"
                        :nowitem="nowItem"
                        @cancel="() => { show_fuzeren = false }"
                    />
                </div>
            </div>
        </baseSearchForm>
    </baseKanbanTd>
</template>
<script>
import { reportMixins } from '@/utils/mixins'
import { pageCheckPoints, deleteCheckPoints, synchPoint } from '@/api/data/checkPoints'
import fuzeren from './fuzeren.vue'
export default {
    components: { fuzeren },
    mixins: [reportMixins],
    data() {
        return {
            deviceCount: 0,
            show_fuzeren: false,
            nowItem: null
        }
    },
    methods: {
        getList(searchData) {
            this.getTableData(pageCheckPoints, searchData)
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
                return deleteCheckPoints(item.id)
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        },
        toSynchPoint() {
            let _this = this;
            synchPoint().then(data => {
                _this.getList(searchData);
            });
        },
        showFZR(item) {
            this.nowItem = item
            this.show_fuzeren = true
        }
    }
}
</script>
