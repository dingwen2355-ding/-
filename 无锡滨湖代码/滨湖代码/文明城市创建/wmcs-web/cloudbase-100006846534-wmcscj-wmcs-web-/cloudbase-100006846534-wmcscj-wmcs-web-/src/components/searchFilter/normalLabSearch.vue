<template>
    <div class="search-item">
        <div v-if="!noLabel" class="label-title" :style="{ 'width': diyWidth }">
            <label>{{ labelName }}</label>
        </div>
        <div class="label-title lab-select-small">
            <el-select v-model="labValue" size="mini" placeholder="请选择" @change="changeLab">
                <el-option
                    v-for="item in labelList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                />
            </el-select>
        </div>
        <div class="search-wapper">
            <el-input v-model="value" size="mini" clearable :placeholder="placName" @keyup.enter.native="onSubmit" />
        </div>
    </div>
</template>
<script>
export default {
    props: {
        noLabel: { type: Boolean, default: false },
        noQuery: { type: Boolean, default: true },
        placName: { type: String, default: '请输入关键字' },
        labelName: { type: String, default: '关键词' },
        keyList: {
            type: Array,
            default: () => {
                return []
            }
        },
        // 默认的选择key
        defaultLab: { type: String, default: '0' },
        // 是否需要请选择选项
        needdefault: { type: Boolean, default: true },
        keyName: { type: String, default: 'keyword' },
        diyWidth: {
            type: String,
            default: '80px'
        }
    },
    data() {
        return {
            labValue: this.defaultLab,
            value: ''
        }
    },
    computed: {
        labelList() {
            let arr = JSON.parse(JSON.stringify(this.keyList))
            // 是否需要添加请选择选项
            if (this.needdefault) {
                arr.unshift({
                    label: '请选择',
                    value: '0'
                })
            }
            return arr
        }
    },
    watch: {
        value(val) {
            console.log('查询组件选择值变化了', val)
            this.$emit('change', this.getSearchValue())
        }
    },
    methods: {
        changeLab() {
            this.value = ''
        },
        onSubmit() {
            this.$emit('sure')
        },
        resetSearchValue() {
            this.labValue = this.defaultLab
            this.value = ''
        },
        getSearchValue() {
            let returnVal = {}
            if (this.labValue != '0') {
                this.value && (returnVal[this.labValue] = this.value.trim())
            }
            return returnVal
        }
    }
}
</script>

<style lang="scss" scoped>
.lab-select-small{
    width: 110px;
}
</style>
