<template>
    <baseKanbanTd title="视频组织" classType="t100">
        <baseSearchForm ref="searchTable" noPage classType="limit-h" @getList="getList" @search="startSearch" @reset="resetQuery">
            <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                    <normalSearch ref="name" labelName="组织名称" keyName="name" placName="请输入组织名称" />
                    <searchRegionDept ref="regionCode" labelName="所属街镇" keyName="regionCode"/>
                    <normalToF ref="configFlag" keyName="configFlag" noLabel showName="未配置属地"/>
                </div>
            </div>
            <div slot="table" class="h-100">
                <div class="h-100">
                    <el-table
                        ref="listtable"
                        v-loading="tableListLoding"
                        height="100%"
                        stripe
                        :data="tableFilterData"
                        row-key="id"
                        :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
                        :cell-class-name="treeCellClassName"
                        default-expand-all
                    >
                        <el-table-column label="组织名称" prop="name" min-width="500">
                            <template slot-scope="scope">
                                <div
                                    class="show-flex-r a-c"
                                    :class="{'first-indent': scope.row.zr_first_level && (!scope.row.children || scope.row.children.length == 0)}"
                                    style="display: inline-flex;"
                                >{{ scope.row.name }}</div>
                            </template>
                        </el-table-column>
                        <el-table-column label="街道/社区" prop="regionAllName" min-width="300" />
                        <el-table-column label="操作" width="200" fixed="right">
                            <template slot-scope="scope">
                                <div class="show-flex-r a-c">
                                    <div v-if="!scope.row.parent" class="zr-link-p unline m-5" @click="toSetRegion(scope.row)">设置属地</div>
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
                <!-- 设置区划 -->
                <regionDialog
                    v-if="show_region"
                    :show="show_region"
                    @cancel="() => { show_region = false }"
                    @sure="saveRegionMethod"
                ></regionDialog>
            </div>
        </baseSearchForm>
    </baseKanbanTd>
</template>
<script>
import { reportMixins } from '@/utils/mixins'
import { pageDevice, saveRegion } from '@/api/data/device'
import regionDialog from './regionDialog.vue'
export default {
    mixins: [reportMixins],
    components: { regionDialog },
    data() {
        return {
            tableFilterData: [], // 过滤后的数据
            expandRowKeys: [],
            selectItem: {},
            show_region: false
        }
    },
    methods: {
        filterList(searchData) {
            let _this = this
            this.tableFilterData = []
            _this.$nextTick(() => {
                let filterName = searchData.name || ''
                let regionCode = searchData.regionCode || ''
                // if (regionCode) {
                //     regionCode = _this.$refs.regionCode.getNameById(regionCode)
                // }
                let configFlag = searchData.configFlag
                let originList = JSON.parse(JSON.stringify(_this.tableData))
                function addFlag(arr) {
                    arr.forEach(i => {
                        let showFlag = true // 默认显示:不满足条件就隐藏
                        if (filterName && (i.name || '').indexOf(filterName) < 0) showFlag = false
                        // if (regionCode && (i.regionAllName || '').indexOf(regionCode) < 0) showFlag = false
                        if (regionCode && i.regionCode != regionCode) showFlag = false
                        if (configFlag && i.regionCode) showFlag = false
                        i.showFlag = showFlag
                        if (i.children && i.children.length > 0) addFlag(i.children)
                    })
                }
                function depFlag(arr) {
                    let arrFlag = false
                    arr.forEach(i => {
                        let self_flag = i.showFlag
                        let next_flag = false
                        if (i.children && i.children.length > 0) {
                            next_flag = depFlag(i.children)
                        } else {
                            next_flag = self_flag
                        }
                        i.showFlag = self_flag || next_flag
                        if (i.showFlag) arrFlag = true
                    })
                    return arrFlag
                }
                function filterFlag(arr) {
                    arr.forEach(i => {
                        if (i.children && i.children.length > 0) {
                            i.children = filterFlag(i.children)
                        }
                    })
                    let b_arr = arr.filter(i => { return i.showFlag })
                    return b_arr
                }
                // 先打自己的标签
                addFlag(originList)
                // 从子往上递归，下级有则上级有
                depFlag(originList)
                // 过滤数组
                let v_arr = filterFlag(originList)
                _this.tableFilterData = v_arr
            })
        },
        getList(searchData) {
            let _this = this
            if (_this.tableData.length == 0) {
                _this.getTableData(pageDevice, searchData)
                .then(list => {
                    _this.filterList(searchData)
                })
            } else {
                _this.tableListLoding = false
                _this.filterList(searchData)
            }
        },
        toSetRegion(item) {
            this.selectItem = item
            this.show_region = true
        },
        saveRegionMethod(result) {
            let _this = this
            this.show_region = false
            saveRegion({
                "id": _this.selectItem.id,
                "regionCode": result.regionCode,
            })
            .then(d => {
                _this.$successMessage("操作成功!")
                _this.tableData = []
                _this.refushPage()
            })
        }
    }
}
</script>
