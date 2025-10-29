<template>
    <div class="w-100">
        <el-row :gutter="20" class="margin-top-20">
            <el-col :span="15">
                <t-card title="近期事件趋势" :bordered="false" hover-shadow>
                    <div style="height: 500px;">
                        <s3 />
                    </div>
                </t-card>
            </el-col>
            <el-col :span="9">
                <t-card title="各街镇整改占比" :bordered="false" hover-shadow>
                    <div style="height: 500px;">
                        <s4 />
                    </div>
                </t-card>
            </el-col>
        </el-row>
    </div>
</template>
<script>
import s3 from './s3.vue'
import s4 from './s4.vue'
export default {
    components: {
        s3, s4
    },
    data() {
        return {}
    }
}
</script>
