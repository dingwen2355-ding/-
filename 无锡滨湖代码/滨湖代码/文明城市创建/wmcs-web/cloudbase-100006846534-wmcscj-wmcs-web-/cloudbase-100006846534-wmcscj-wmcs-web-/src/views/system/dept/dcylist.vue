<template>
    <el-dialog title="负责人" :visible.sync="show" append-to-body :width="rowWidth" :before-close="cancel">
        <div class="dia-bd-hei">
            <el-table
                v-loading="tableListLoding"
                stripe
                size="mini"
                :data="tableData"
            >
                <el-table-column label="序" type="index" width="80" />
                <el-table-column label="用户名称" prop="name"  width="100"/>
                <el-table-column label="负责区域" prop="regionName" width="150" />
                <el-table-column label="负责点位" prop="checkPointsNames"  />
            </el-table>
        </div>
        <div slot="footer" class="dialog-footer">
            <t-button ghost class="margin-right-20" @click="cancel">取消</t-button>
        </div>
    </el-dialog>
</template>
<script>
// import { pageEventType } from '@/api/workflow/eventType'
import { pageUserByDept } from '@/api/system/dept'
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
        },
        task: {
            type: Object,
            default: () => {
                return {}
            }
        }
    },
    data() {
        return {
            tableData: [],
            tableListLoding: false
        }
    },
    mounted() {
        this.$nextTick(() => {
            this.getList()
        })
    },
    methods: {
        cancel() {
            this.$emit('cancel')
        },
        getList() {
            let _this = this
            _this.tableListLoding = true
            pageUserByDept(this.task.id)
            .then(data => {
                console.log(data)
                _this.tableData = data.data
            })
            .finally(e => {
                _this.tableListLoding = false
            })
        }
    }
}
</script>
