<template>
    <!-- 单状态勾选 -->
    <div class="search-item">
        <div
            v-if="!noLabel"
            class="label-title"
            :style="{ 'width': diyWidth }"
        >
            <label>{{ labelName }}</label>
        </div>
        <div class="search-wapper" style="width: fit-content;">
            <el-checkbox v-model="value">{{ showName }}</el-checkbox>
        </div>
    </div>
</template>
<script>
export default {
    props: {
        noLabel: { type: Boolean, default: false },
        labelName: { type: String, default: '是否显示' },
        showName: { type: String, default: '是否显示' },
        keyName: {
            type: String,
            default: 'keyName'
        },
        defalutSelect: {
            type: Boolean,
            default: false
        },
        diyWidth: {
            type: String,
            default: '80px'
        }
    },
    data() {
        return {
            value: this.defalutSelect
        }
    },
    methods: {
        resetSearchValue() {
            this.value = this.defalutSelect
        },
        getSearchValue() {
            let returnVal = {}
            returnVal[this.keyName] = this.value
            return returnVal
        }
    }
}
</script>
