<template>
    <!-- 通用多选带全选组件 -->
    <div class="search-item-td w-100">
        <div v-if="!noLabel" class="label-title" :style="{ 'width': diyWidth }">
            <label>{{ labelName }}</label>
        </div>
        <div class="show-flex-r">
            <!-- <div>
                <el-checkbox v-model="checkAll" class="margin-right-20" :indeterminate="checkHalf" @change="changeAll">全选</el-checkbox>
            </div> -->
            <div>
                <el-checkbox v-model="checkNo" class="margin-right-20" @change="changeNo">全部</el-checkbox>
            </div>
            <div class="flex-1 show-flex-r" style="flex-wrap: wrap;">
                <el-checkbox
                    v-for="(item, index) in selectOptions"
                    :key="index"
                    v-model="item.select"
                    @change="changeItem"
                >
                    {{ item.label }}
                </el-checkbox>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    props: {
        noLabel: { type: Boolean, default: false },
        labelName: { type: String, default: '关键词' },
        keyName: { type: String, default: 'keyword' },
        diyWidth: {
            type: String,
            default: '80px'
        },
        selectOptions: {
            type: Array,
            default: () => { return [] }
        },
        defaultVal: {
            type: Array,
            default: () => { return [] }
        },
        useNone: {
            // 设置为true后, 即使没有值, key会传空数组出去
            type: Boolean,
            default: false
        }
    },
    data() {
        return {
            checkNo: true,
            checkAll: false,
            checkHalf: false
        }
    },
    mounted() {
        this.initDefaultVal()
    },
    methods: {
        changeDefaultAfter(arr) {
            if (arr.length == 0) return
            this.selectOptions.forEach(s => {
                if (arr.indexOf(s.value) > -1) {
                    s.select = true
                }
            })
            let checkedCount = this.selectOptions.filter(i => { return i.select }).length
            this.checkAll = checkedCount === this.selectOptions.length
            this.checkHalf = checkedCount > 0 && checkedCount < this.selectOptions.length
            if (checkedCount > 0) this.checkNo = false
            if (checkedCount == 0) this.checkNo = true
        },
        initDefaultVal() {
            if (this.defaultVal.length == 0) return
            this.selectOptions.forEach(s => {
                if (this.defaultVal.indexOf(s.value) > -1) {
                    s.select = true
                }
            })
            let checkedCount = this.selectOptions.filter(i => { return i.select }).length
            this.checkAll = checkedCount === this.selectOptions.length
            this.checkHalf = checkedCount > 0 && checkedCount < this.selectOptions.length
            if (checkedCount > 0) this.checkNo = false
            if (checkedCount == 0) this.checkNo = true
        },
        changeItem() {
            let checkedCount = this.selectOptions.filter(i => { return i.select }).length
            if (checkedCount > 0) this.checkNo = false
            if (checkedCount == 0) this.checkNo = true
            this.checkAll = checkedCount === this.selectOptions.length
            this.checkHalf = checkedCount > 0 && checkedCount < this.selectOptions.length
            console.log('查询组件选择值变化了')
            this.$emit('change', this.getSearchValue())
        },
        changeAll(val) {
            this.selectOptions.forEach(i => { i.select = val })
            let checkedCount = this.selectOptions.filter(i => { return i.select }).length
            this.checkAll = checkedCount === this.selectOptions.length
            this.checkHalf = false
            if (checkedCount > 0) this.checkNo = false
            if (checkedCount == 0) this.checkNo = true
            console.log('查询组件选择值变化了')
            this.$emit('change', this.getSearchValue())
        },
        changeNo(val) {
            this.selectOptions.forEach(i => { i.select = false })
            this.checkNo = true
            this.$emit('change', this.getSearchValue())
        },
        resetSearchValue() {
            this.changeAll(false)
        },
        getSearchValue() {
            let returnVal = {}
            let v = this.selectOptions.filter(i => {
                return i.select
            }).map(j => {
                return j.value
            })
            if (v.length > 0) {
                returnVal[this.keyName] = v
            } else {
                // 使用useNone后, 即使没有值,也要传key出去
                if (this.useNone) returnVal[this.keyName] = []
            }
            return returnVal
        }
    }
}
</script>
<style scoped lang="scss">
.search-normal-check-all{
    .one-line-wapper{
        min-height: 44px;
        line-height: 44px;
        display: flex;
        flex-direction: row;
        margin-left:-5px;
    }
}
</style>
<style scoped lang="scss">
@import './basestyle.scss';
</style>
