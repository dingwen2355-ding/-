<template>
    <el-dialog title="巡检报告" :visible.sync="show" append-to-body :width="rowWidth" :before-close="cancel">
        <div class="dia-bd-hei">
            <el-table
                v-loading="tableListLoding"
                stripe
                size="mini"
                :data="tableData"
            >
                <el-table-column label="序" type="index" width="80" />
                <el-table-column label="周期" prop="orderNum" />
                <el-table-column label="街道/社区" prop="orderNum" />
                <el-table-column label="点位类型" prop="orderNum" />
                <el-table-column label="点位" prop="orderNum" />
                <el-table-column label="存在问题" prop="orderNum" />
                <el-table-column label="上报时间" prop="orderNum" width="170" />
            </el-table>
        </div>
        <div slot="footer" class="dialog-footer">
            <t-button ghost class="margin-right-20" @click="cancel">取消</t-button>
        </div>
    </el-dialog>
</template>
<script>
// import { pageEventType } from '@/api/workflow/eventType'
export default {
    // mixins: [reportMixins],
    props: {
        usenotice: {
            // 是否通知
            type: Boolean,
            default: false
        },
        rowWidth: {
            // 整体宽度设置
            type: String,
            default: '80%'
        },
        show: {
            type: Boolean,
            default: false
        }
    },
    data() {
        return {
            tableData: [],
            tableListLoding: false
        }
    },
    mounted() {},
    methods: {
        cancel() {
            this.$emit('cancel')
        }
    }
}
</script>
