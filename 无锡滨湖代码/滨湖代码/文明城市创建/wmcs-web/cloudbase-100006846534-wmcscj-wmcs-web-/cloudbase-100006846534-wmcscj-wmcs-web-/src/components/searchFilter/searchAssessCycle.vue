<template>
    <!-- 字典: 考核周期 -->
    <div :class="{'search-item-td': !onlyShow,'search-form': useType == 'form'}" :style="{ 'width': allWidth }">
        <div v-if="!noLabel" class="label-title" :style="{ 'width': diyWidth }">
            <label>{{ labelName }} :</label>
        </div>
        <div class="search-wapper">
            <el-select v-show="!disabled" v-model="value" size="small" :placeholder="placName" :disabled="disabled" clearable filterable>
                <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
            <div v-show="disabled" :class="{'td-form-val': !onlyShow,'only-show': onlyShow}">
                {{ disableShowName }}
            </div>
        </div>
    </div>
</template>
<script>
export default {
    props: {
        labelName: { type: String, default: '考核周期' },
        noLabel: { type: Boolean, default: false },
        keyName: { type: String, default: 'assessCycleId' },
        keyLabelName: { type: String, default: "assessCycleName" },
        placName: { type: String, default: '请选择考核周期' },
        disabled: {
            type: Boolean,
            default: false
        },
        useType: {
            type: String,
            default: 'search' // search, form
        },
        diyWidth: {
            type: String,
            default: '85px'
        },
        allWidth: {
            type: String,
            default: '260px'
        },
        defaultVal: {
            type: [String, Number],
            default: ''
        },
        onlyShow: {
            type: Boolean,
            default: false
        },
        firstFlag: {
            type: Boolean,
            default: false
        }
    },
    data() {
        return {
            api: 'getAssessCycleList',
            originData: [],
            options: [],
            value: this.defaultVal,
            stopEmit: false // 阻止事件传递
        }
    },
    computed: {
        disableShowName() {
            if (this.value === '') return '暂未设置'
            if (this.options.length == 0) return ''
            let item = this.options.find(i => {
                return i.value == this.value
            })
            return item && item.label || '暂未设置'
        }
    },
    watch: {
        '$store.state.searchFilter.getAssessCycleList'(newVal, oldVal) {
            this.init(JSON.parse(JSON.stringify(newVal)))
        },
        value(val) {
            console.log('查询组件选择值变化了', val)
            if (!this.stopEmit) {
                this.$emit('change', this.getSearchValue())
                this.$emit('changeNext', this.getSearchItem())
            }
        }
    },
    mounted() {
        let dataData = this.$store.state.searchFilter[this.api]
        if (dataData) {
            // 已经有缓存
            this.init(JSON.parse(JSON.stringify(dataData)))
        } else {
            // 没有缓存则注册缓存请求
            this.$store.commit('addSearchFilter', this.api)
        }
    },
    methods: {
        init(val) {
            this.originData = JSON.parse(JSON.stringify(val.records))
            this.options = val.records.map(i => {
                return {
                    value: i.id,
                    label: i.name
                }
            })
            if(this.firstFlag) {
                this.value = this.options[0].value  
            }
        },
        setValWithChange(v) {
            // 这种结构不需要等待后台接口返回options
            // 用这种方式调用初始化, 传递事件出去,改变外面的值
            this.value = v
        },
        setValWithoutChange(v) {
            // 这种结构不需要等待后台接口返回options
            // 用这种方式调用初始化, 不传递事件出去
            this.stopEmit = true
            this.value = v
            this.$nextTick(() => {
                this.stopEmit = false
            })
        },
        resetSearchValue() {
            this.value = ''
        },
        getSearchValue() {
            let returnVal = {}
            if (this.value !== '') returnVal[this.keyName] = this.value
            let item = this.options.find(j => {
                return j.value == this.value
            })
            if (item) {
                returnVal[this.keyLabelName] = item.label
            }
            return returnVal
        },
        getSearchItem() {
            // 部分场景下需要把完整的对象传递出去做联动
            if (this.value) {
                let item = this.originData.find(i => {
                    return i.id == this.value
                })
                if (item) {
                    return item
                } else {
                    return null
                }
            } else {
                return null
            }
        }
    }
}
</script>
<style scoped lang="scss">
@import './basestyle.scss';
</style>
