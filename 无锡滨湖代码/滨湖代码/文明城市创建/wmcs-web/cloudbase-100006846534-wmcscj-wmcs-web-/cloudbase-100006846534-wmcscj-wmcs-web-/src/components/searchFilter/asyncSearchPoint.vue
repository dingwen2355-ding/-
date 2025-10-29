<template>
    <!-- 异步搜索点位: 当前版本只满足最普通搜索条件 -->
    <div :class="{'search-item-td': !onlyShow,'search-form': useType == 'form'}" :style="{ 'width': allWidth }">
        <div v-if="!noLabel" class="label-title" :style="{ 'width': diyWidth }">
            <label>{{ labelName }} :</label>
        </div>
        <div class="search-wapper">
            <!-- 下拉框类型 -->
            <el-select
                v-show="!disabled"
                v-model="value"
                size="small"
                :placeholder="placName"
                :disabled="disabled"
                :clearable="clearable"
                :filterable="filterable"
                remote
                :remote-method="searchList"
                :loading="loading"
            >
                <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" :disabled="!canSelectDisabled && item.status == 'disable'" />
            </el-select>
            <div v-show="disabled" :class="{'td-form-val': !onlyShow,'only-show': onlyShow}">
                {{ disableShowName }}
            </div>
        </div>
    </div>
</template>
<script>
import { listCheckPoints } from '@/api/data/checkPoints.js'
export default {
    props: {
        labelName: { type: String, default: '所属点位' },
        keyName: { type: String, default: 'pointsId' },
        noLabel: { type: Boolean, default: false },
        placName: { type: String, default: '请选择点位' },
        disabled: { type: Boolean, default: false },
        // search, form, list
        useType: { type: String, default: 'search' },
        // 除了search form 增加一种覆盖场景:列表,只给枚举值, 避免使用filters, 可以用这个
        onlyShow: { type: Boolean, default: false },
        // 默认是按照下拉搜索的长度
        diyWidth: { type: String, default: '80px' },
        allWidth: { type: String, default: '260px' },
        // 默认值
        defaultVal: { type: [String, Number], default: '' },
        clearable: { type: Boolean, default: true },
        filterable: { type: Boolean, default: true },
        canSelectDisabled: {
            // 是否允许选择禁用:默认搜索可以选择
            type: Boolean,
            default: true
        }
    },
    data() {
        return {
            loading: false,
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
        value(val) {
            console.log('查询组件选择值变化了', val)
            if (!this.stopEmit) {
                this.$emit('change', this.getSearchValue())
                let item = this.options.find(i => { return i.value == this.value })
                this.$emit('changeItem', item)
            }
        }
    },
    methods: {
        getPage(searchData) {
            let _this = this
            this.loading = true
            searchData['page'] = 1
            searchData['pageSize'] = 50
            return new Promise((res, rej) => {
                listCheckPoints(searchData)
                .then(d => {
                    let list = d.data.records
                    list.forEach(i => {
                        i.label = i.name
                        i.value = i.id
                    })
                    res(list)
                })
                .finally(e => {
                    _this.loading = false
                })
            })
        },
        searchList(query, keyName) {
            // 默认搜索下拉使用fullname搜索,赋值时会用到code
            keyName = keyName || 'name'
            let _this = this
            // 先保留当前的选择
            let item = null
            if (this.value) {
                item = this.options.find(i => { return i.id == this.value })
            }
            if (query !== '') {
                let searchData = {}
                searchData[keyName] = query
                this.getPage(searchData)
                .then(list => {
                    if (item) {
                        if (!list.find(i => { return i.value == item.value })) {
                            list.push(item)
                        }
                    }
                    _this.options = list
                })
            } else {
                _this.options = item ? [item] : []
            }
        },
        setValWithChange(code) {
            let _this = this
            if (!code) return
            // 用这种方式调用初始化, 传递事件出去,改变外面的值
            this.getPage({ code: code })
            .then(list => {
                _this.options = list
                _this.value = code
            })
        },
        setValWithoutChange(code) {
            let _this = this
            if (!code) return
            // 用这种方式调用初始化, 不传递事件出去
            this.stopEmit = true
            this.getPage({ code: code })
            .then(list => {
                _this.options = list
                _this.value = code
                this.$nextTick(() => {
                    this.stopEmit = false
                })
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
@import './basestyle.scss';
</style>
