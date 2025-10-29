<template>
    <div class="search-item-td" :style="{ 'width': allWidth }">
        <div v-if="!noLabel" class="label-title" :style="{ 'width': diyWidth }">
            <label>{{ labelName }} :</label>
        </div>
        <div class="search-wapper">
            <el-input v-model="value" size="small" clearable :placeholder="placName" @keyup.enter.native="onSubmit">
                <!-- <i slot="suffix" class="el-input__icon el-icon-search"></i> -->
            </el-input>
        </div>
    </div>
</template>
<script>
export default {
    props: {
        noLabel: { type: Boolean, default: false },
        noQuery: { type: Boolean, default: true },
        placName: { type: String, default: '请输入关键词' },
        labelName: { type: String, default: '关键词' },
        keyName: { type: String, default: 'keyword' },
        diyWidth: {
            type: String,
            default: '85px'
        },
        allWidth: {
            type: String,
            default: '260px'
        }
    },
    data() {
        return {
            value: ''
        }
    },
    watch: {
        value(val) {
            console.log('查询组件选择值变化了', val)
            this.$emit('change', this.getSearchValue())
        }
    },
    methods: {
        onSubmit() {
            this.$emit('sure')
        },
        resetSearchValue() {
            this.value = ''
        },
        getSearchValue() {
            let returnVal = {}
            if (this.value) {
                returnVal[this.keyName] = this.value.trim()
            }
            return returnVal
        }
    }
}
</script>
<style scoped lang="scss">
@import './basestyle.scss';
</style>
