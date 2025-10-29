<template>
    <!--
        通用:字典类
    -->
    <div :class="{'search-item-td': !onlyShow,'search-form': useType == 'form'}" :style="{ 'width': allWidth }">
        <div v-if="!noLabel" class="label-title" :style="{ 'width': diyWidth }">
            <label>{{ labelName }} :</label>
        </div>
        <div class="search-wapper">
            <!-- 下拉框类型 -->
            <el-select
                v-if="searchType == 'select'"
                v-show="!disabled"
                v-model="value"
                size="small"
                :placeholder="placName"
                :disabled="disabled"
                :clearable="clearable"
                :filterable="filterable"
            >
                <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
            <!-- 单选类型 -->
            <el-radio-group v-if="searchType == 'radio'" v-show="!disabled" v-model="value">
                <el-radio v-for="item in options" :key="item.value" :label="item.value">{{ item.label }}</el-radio>
            </el-radio-group>
            <div v-show="disabled" :class="{'td-form-val': !onlyShow,'only-show': onlyShow}">
                {{ disableShowName }}
            </div>
        </div>
    </div>
</template>
<script>
export default {
    props: {
        labelName: { type: String, default: '字典类' },
        noLabel: { type: Boolean, default: false },
        keyName: { type: String, default: 'dictType' },
        placName: { type: String, default: '请选择' },
        disabled: { type: Boolean, default: false },
        // search, form, list
        useType: { type: String, default: 'search' },
        // 展现形式: select: 下拉选择, radio: 单选
        searchType: { type: String, default: 'select' },
        // 除了search form 增加一种覆盖场景:列表,只给枚举值, 避免使用filters, 可以用这个
        onlyShow: { type: Boolean, default: false },
        // 默认是按照下拉搜索的长度
        diyWidth: { type: String, default: '85px' },
        allWidth: { type: String, default: '260px' },
        // 默认值
        defaultVal: { type: [String, Number], default: '' },
        // 是否需要实现类似v-model的变更defaultVal自动变更值,不用再表单传值
        useDefaultChange: { type: Boolean, default: true },
        // 从所有枚举类里过滤出当前需要的关键词, 必填
        dictName: { type: String, default: '' },
        clearable: { type: Boolean, default: true },
        filterable: { type: Boolean, default: true }
    },
    data() {
        return {
            api: 'getAllDictType',
            options: [],
            value: this.defaultVal,
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
        '$store.state.searchFilter.getAllDictType'(newVal, oldVal) {
            this.init(JSON.parse(JSON.stringify(newVal)))
        },
        value(val) {
            console.log('查询组件选择值变化了', val)
            if (!this.stopEmit) {
                this.$emit('change', this.getSearchValue())
            }
        },
        defaultVal(v) {
            if (this.useDefaultChange) {
                // 防止死循环
                if (v !== this.value) {
                    this.setValWithoutChange(v)
                }
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
            let originList = JSON.parse(JSON.stringify(val.records))
            // 过滤出本次需要的
            originList = originList.find(i => { return i.dictType == this.dictName })
            originList = originList || {}
            originList = originList['dict'] || []
            this.options = originList.map(i => {
                return {
                    label: i.dictLabel,
                    value: i.dictValue + '',
                    status: i.status
                }
            })
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
            if (this.value !== v) this.value = v
            this.$nextTick(() => {
                this.stopEmit = false
            })
        },
        resetSearchValue() {
            this.value = this.defaultVal
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
@import '../basestyle.scss';
</style>
