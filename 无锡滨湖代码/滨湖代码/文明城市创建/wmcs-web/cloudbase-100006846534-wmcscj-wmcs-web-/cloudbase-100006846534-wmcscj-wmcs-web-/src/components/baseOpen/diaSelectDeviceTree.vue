<template>
    <!-- 视频设备选择弹框 -->
    <el-dialog :visible.sync="show" append-to-body :width="rowWidth" :before-close="cancel">
        <div class="dia-bd-hei">
            <div class="show-flex-r" >
                <div class="flex-1 w-0 h-100" style="overflow: auto;">
                    <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
                        <div slot="customQuery" class="listSearch">
                            <div class="search-line">
                                <normalSearch ref="name" labelName="组织名称" keyName="name" placName="请输入组织名称" />
                                <normalSearch ref="sn" labelName="组织编码" keyName="sn" placName="请输入组织编码" />
                            </div>
                        </div>
                        <div slot="table">
                            <el-table v-loading="tableListLoding" stripe size="mini" :data="tableData">
                                <el-table-column label="上级组织名称" prop="parentName" min-width="300" />
                                <el-table-column label="组织名称" prop="name" min-width="200" />
                                <el-table-column label="组织编码" prop="sn" min-width="200" />
                                <el-table-column label="视频数" prop="channelCount" width="150" />
                                <el-table-column label="操作" width="100" fixed="right">
                                    <template slot-scope="scope">
                                        <div class="show-flex-r d-c">
                                            <div class="zr-link-p unline m-5" @click="toSetRegion(scope.row)">设置属地</div>
                                        </div>
                                    </template>
                                </el-table-column>
                            </el-table>
                            <!-- 设置区划 -->
                            <regionDialog
                                v-if="show_region"
                                :show="show_region"
                                @cancel="() => { show_region = false }"
                                @sure="saveRegionMethod"
                            ></regionDialog>
                        </div>
                    </baseSearchForm>
                </div>
            </div>
        </div>
    </el-dialog>
</template>
<script>
import { reportMixins } from '@/utils/mixins'
import { pageDeviceList, saveRegion } from '@/api/data/device'
import regionDialog from '@/views/data/deviceTree/regionDialog'

// './diaSelectDeviceTreeSetRegion.vue'

export default {
    mixins: [reportMixins],
    components: { regionDialog },
    props: {
        rowWidth: {
            // 整体宽度设置
            type: String,
            default: '70%'
        },
        show: {
            type: Boolean,
            default: false
        }
    },
    mounted() {
        // 这里看是否需要初始化一些条件:如果不想等组件,可以直接去调用getList方法,组件后台自己装显示
        // 看后续需要扩展,这里先这样
        this.$nextTick(() => {
            // dia里面的内容会在mounted之后,延迟一下
            this.startSearch()
        })
    },
    data() {
        return {
            noStart: true,
            selectItem: null,
            show_region: false,
            selectList: [] // 多选模式下选择的内容
        }
    },
    methods: {
        getList(searchData) {
            searchData.selectStatus = 0;
            this.getTableData(pageDeviceList, searchData)
        },
        cancel() {
            this.$emit('cancel')
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
