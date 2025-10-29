<template>
    <!-- 角色选择(多选) -->
    <div :class="{'search-item-td': !onlyShow, 'search-form': useType == 'form'}" :style="{ 'width': allWidth }">
        <div v-if="!noLabel" class="label-title" :style="{ 'width': diyWidth }">
            <label>{{ labelName }} :</label>
        </div>
        <div class="search-wapper">
            <el-select
                v-show="!disabled"
                ref="selectlabel"
                v-model="value"
                multiple
                :placeholder="placName"
                size="small"
                clearable
                filterable
            >
                <el-option
                    v-for="item in options"
                    v-show="!item.disabled"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                    :disabled="item.disabled"
                />
            </el-select>
            <div v-show="disabled" :class="{'td-form-val': !onlyShow,'only-show': onlyShow}">
                <el-tag
                    v-for="(r, index) in disableShowName"
                    :key="index"
                    :type="r.disabled ? 'info' : 'primary'"
                    class="margin-right-10 margin-bottom-10"
                    size="small"
                >{{ r.label }}</el-tag>
                <span v-if="disableShowName.length == 0">暂未设置</span>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    props: {
        labelName: { type: String, default: '所属角色' },
        noLabel: { type: Boolean, default: false },
        keyName: { type: String, default: 'roleId' },
        placName: { type: String, default: '请选择角色' },
        disabled: {
            type: Boolean,
            default: false
        },
        useType: {
            type: String,
            default: 'search' // search, form
        },
        // 除了search form 增加一种覆盖场景:列表,只给枚举值, 避免使用filters, 可以用这个
        onlyShow: { type: Boolean, default: false },
        diyWidth: {
            type: String,
            default: '85px'
        },
        allWidth: {
            type: String,
            default: '260px'
        },
        defaultVal: {
            type: Array,
            default: () => { return [] }
        }
    },
    data() {
        return {
            api: 'getRoleList',
            options: [],
            value: this.defaultVal,
            stopEmit: false // 阻止事件传递
        }
    },
    computed: {
        disableShowName() {
            let selectIds = this.value
            if (this.options.length == 0) return []
            if (selectIds.length == 0) return []
            let labelNames = selectIds.map(j => {
                return this.options.find(i => { return i.value == j })
            })
            .filter(k => { return k })
            if (labelNames.length == 0) return []
            return labelNames
        }
    },
    watch: {
        '$store.state.searchFilter.getRoleList'(newVal, oldVal) {
            this.init(JSON.parse(JSON.stringify(newVal)))
        },
        value(val) {
            console.log('查询组件选择值变化了', val)
            if (!this.stopEmit) {
                this.$emit('change', this.getSearchValue())
            } else {
                console.log('这次改变不传递')
            }
        }
    },
    mounted() {
        let options = this.$store.state.searchFilter[this.api]
        if (options) {
            // 已经有缓存
            this.init(JSON.parse(JSON.stringify(options)))
        } else {
            // 没有缓存则注册缓存请求
            this.$store.commit('addSearchFilter', this.api)
        }
    },
    methods: {
        init(val) {
            // 按照过滤模式先做一次过滤
            let oV = JSON.parse(JSON.stringify(val.records))
            this.options = oV.map(i => {
                return {
                    label: i.name,
                    value: i.id,
                    disabled: i.disabled
                }
            })
        },
        setValWithChange(arr) {
            arr = arr || []
            let _this = this
            // 用这种方式调用初始化, 传递事件出去,改变外面的值
            if (this.options.length == 0) {
                setTimeout(() => {
                    _this.setValWithChange(arr)
                }, 100)
            } else {
                console.log('初始化成功')
                this.value = JSON.parse(JSON.stringify(arr))
            }
        },
        setValWithoutChange(arr) {
            arr = arr || []
            let _this = this
            // 用这种方式调用初始化, 不传递事件出去
            this.stopEmit = true
            if (this.options.length == 0) {
                setTimeout(() => {
                    _this.setValWithoutChange(arr)
                }, 100)
            } else {
                console.log('初始化成功')
                this.value = JSON.parse(JSON.stringify(arr))
                this.$nextTick(() => { this.stopEmit = false })
            }
        },
        resetSearchValue() {
            this.value = this.defaultVal
        },
        getSearchValue() {
            let returnVal = {}
            if (!this.value) return returnVal
            if (this.value.length == 0) return returnVal
            returnVal[this.keyName] = this.value || []
            return returnVal
        }
    }
}
</script>
<style scoped lang="scss">
@import './basestyle.scss';
</style>
