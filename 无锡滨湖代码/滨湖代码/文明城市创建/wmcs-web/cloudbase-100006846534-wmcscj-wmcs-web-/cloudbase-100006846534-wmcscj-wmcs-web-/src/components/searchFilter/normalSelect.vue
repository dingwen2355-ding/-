<template>
    <!-- 通用选择 -->
    <div class="search-item-td" :class="{'search-form': useType == 'form'}" :style="{ 'width': allWidth }">
        <div v-if="!noLabel" class="label-title" :style="{ 'width': diyWidth }">
            <label>{{ labelName }} :</label>
        </div>
        <div class="search-wapper">
            <el-select v-show="!disabled" v-model="value" size="small" :placeholder="placName" :disabled="disabled" clearable filterable>
                <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
            <div v-show="disabled" class="search-form-disable-vtxt">
                {{ disableShowName }}
            </div>
        </div>
    </div>
</template>
<script>
export default {
    props: {
        labelName: { type: String, default: '通用选择' },
        noLabel: { type: Boolean, default: false },
        keyName: {
            type: String,
            default: 'onPut'
        },
        placName: { type: String, default: '请选择' },
        disabled: {
            type: Boolean,
            default: false
        },
        useType: {
            type: String,
            default: 'search' // search, form
        },
        options: {
            type: Array,
            default: () => {
                return []
            }
        },
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
            // options: [],
            value: '',
            stopEmit: false // 阻止事件传递
        }
    },
    computed: {
        disableShowName() {
            if (this.value === '') return ''
            if (this.options.length == 0) return ''
            let item = this.options.find(i => {
                return i.value == this.value
            })
            return item && item.label || ''
        }
    },
    watch: {
        value(val) {
            console.log('查询组件选择值变化了', val)
            if (!this.stopEmit) {
                this.$emit('change', this.getSearchValue())
            }
        }
    },
    methods: {
        resetSearchValue() {
            this.value = ''
        },
        getSearchValue() {
            let returnVal = {}
            if (this.value !== '') returnVal[this.keyName] = this.value
            return returnVal
        }
    }
}
</script>
<style scoped lang="scss">
@import './basestyle.scss';
</style>
