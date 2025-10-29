<template>
    <!-- 点位选择弹框 -->
    <el-dialog :title="title" :visible.sync="show" append-to-body :width="rowWidth" :before-close="cancel">
        <div class="dia-bd-hei">
            <baseSearchForm ref="searchTable" noPage @getList="getList" @search="startSearch" @reset="resetQuery">
                <div slot="table">
                    <el-table v-loading="tableListLoding" stripe size="mini" :data="tableData">
                        <el-table-column label="所属区划" prop="regionName" min-width="150" />
                        <el-table-column label="点位名称" prop="name" min-width="200" />
                        <el-table-column label="点位类型" prop="typeName" min-width="200" />
                        <el-table-column label="经度" prop="longitude" width="120" />
                        <el-table-column label="纬度" prop="latitude" width="120" />
                    </el-table>
                </div>
            </baseSearchForm>
        </div>
        <div slot="footer" class="dialog-footer">
            <t-button ghost class="margin-right-20" @click="cancel">取消</t-button>
        </div>
    </el-dialog>
</template>
<script>
import { reportMixins } from '@/utils/mixins'
import { pageCheckPoints } from '@/api/data/checkPoints.js'
export default {
    mixins: [reportMixins],
    props: {
        title: {
            type: String,
            default: '巡检报告'
        },
        rowWidth: {
            // 整体宽度设置
            type: String,
            default: '80%'
        },
        show: {
            type: Boolean,
            default: false
        },
        getMoreAttr: {
            type: Function,
            // 使调用方可以添加额外搜索参数的能力
            default: () => { return {} }
        },
        nowitem: {
            type: Object,
            default: () => { return {} }
        }
    },
    data() {
        return {
            noStart: true
        }
    },
    mounted() {
        this.$nextTick(() => {
            this.startSearch()
        })
    },
    methods: {
        getList(searchData) {
            searchData['fzrId'] = this.nowitem.id // 点位ID
            // 合并外部使用参数
            let moreAttr = this.getMoreAttr()
            Object.assign(searchData, moreAttr)
            this.getTableData(pageCheckPoints, searchData)
        },
        cancel() {
            this.$emit('cancel')
        }
    }
}
</script>
