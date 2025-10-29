<template>
    <baseKanbanTd title="文明单位">
        <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
            <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                    <normalSearch ref="name" labelName="组织名称" keyName="name" placName="请输入组织名称" />
                    <searchRegionDept ref="regionCode" labelName="所属街镇" keyName="regionCode"/>
                </div>
            </div>
            <div slot="table">
                <div class="show-flex-r j-e-2 margin-bottom-10">
                    <t-button @click="toSelectTree()">+ 组织归属分配</t-button>
                </div>
                <div>
                    <el-table
                        ref="listtable"
                        v-loading="tableListLoding"
                        stripe
                        :data="tableData"
                    >

                        <el-table-column label="上级组织名称" prop="parentName" min-width="300" />
                        <el-table-column label="组织名称" prop="name" min-width="200" />
                        <el-table-column label="组织编码" prop="sn" min-width="200" />
                        <el-table-column label="街道/社区" prop="regionAllName" min-width="250" />
                        <el-table-column label="视频数" prop="channelCount" width="150" />
                        <el-table-column label="操作" width="150" fixed="right">
                            <template slot-scope="scope">
                                <div class="show-flex-r a-c">
                                    <div v-if="!scope.row.parent" class="zr-link-p unline m-5" @click="toSetRegion(scope.row)">重置</div>
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>
                    
                    <!-- 选择组织 -->
                    <diaSelectDeviceTree
                        v-if="dia_selecttree"
                        :show="dia_selecttree"
                        @cancel="() => { dia_selecttree = false }"
                    />
                </div>
            </div>
        </baseSearchForm>
    </baseKanbanTd>
</template>
<script>
import { reportMixins } from '@/utils/mixins'
import { pageDeviceList, removeRegionCode } from '@/api/data/device'
export default {
    mixins: [reportMixins],
    data() {
        return {
            dia_selecttree: false
        }
    },
    methods: {
        getList(searchData) {
            searchData.selectStatus = 1;
            this.getTableData(pageDeviceList, searchData)
        },
        toSetRegion(item) {
            let _this = this
            new Promise((res, rej) => {
                _this.useDia = this.$dialog({
                    header: '系统提示',
                    body: `是否确认重置组织${item.name}的归属区划？`,
                    style: 'color: rgba(0, 0, 0, 0.6)',
                    onConfirm: ({ e }) => {
                        res()
                    }
                })
            })
            .then(d => {
                _this.useDia.hide()
                return removeRegionCode(item.id)
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        },
        toSelectTree() {
            this.dia_selecttree = true
        }
    }
}
</script>
