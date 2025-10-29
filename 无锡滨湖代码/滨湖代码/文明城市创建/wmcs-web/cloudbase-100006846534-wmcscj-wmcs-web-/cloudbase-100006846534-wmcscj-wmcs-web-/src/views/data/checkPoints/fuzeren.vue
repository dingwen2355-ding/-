<template>
    <!-- 点位选择弹框 -->
    <el-dialog :title="title" :visible.sync="show" append-to-body :width="rowWidth" :before-close="cancel">
        <div class="dia-bd-hei">
            <baseSearchForm ref="searchTable" noPage @getList="getList" @search="startSearch" @reset="resetQuery">
                <div slot="table">
                    <el-table v-loading="tableListLoding" stripe size="mini" :data="tableData">
                        <el-table-column label="姓名" prop="name" min-width="150" />
                        <el-table-column label="人员类型" prop="typeName" min-width="200" />
                        <el-table-column label="负责区域" prop="regionName" min-width="150" />
                        <el-table-column label="负责点位" prop="checkPointsNames" min-width="200" />
                        <el-table-column label="操作" width="100" fixed="right">
                            <template slot-scope="scope">
                                <div class="show-flex-r d-c">
                                    <div class="zr-link-p unline m-5" @click="showXJBG(scope.row)">巡检报告</div>
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>
                    <!-- 巡检报告弹框 -->
                    <xjbg
                        v-if="show_xjbg"
                        :show="show_xjbg"
                        :nowitem="fzr"
                        @cancel="() => { show_xjbg = false }"
                    />
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
import { listByPointsId } from '@/api/data/checkPoints.js'
import xjbg from './xjbg.vue'
export default {
    components: { xjbg },
    mixins: [reportMixins],
    props: {
        title: {
            type: String,
            default: '负责人'
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
            noStart: true,
            show_xjbg: false,
            fzr: null
        }
    },
    mounted() {
        this.$nextTick(() => {
            this.startSearch()
        })
    },
    methods: {
        getList(searchData) {
            let _this = this
            this.getTableData(listByPointsId, this.nowitem.id).then(data => {
                _this.tableData = data.data
            });
        },
        cancel() {
            this.$emit('cancel')
        },
        showXJBG(item) {
            this.fzr = item
            this.show_xjbg = true
        }
    }
}
</script>
