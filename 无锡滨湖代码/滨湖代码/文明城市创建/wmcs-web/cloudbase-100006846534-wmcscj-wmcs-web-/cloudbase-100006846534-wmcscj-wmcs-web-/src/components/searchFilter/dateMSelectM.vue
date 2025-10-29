<template>
    <!-- 多选日期的搜索:整体选 -->
    <div class="search-item-td" :style="{ 'width': allWidth }">
        <div v-if="!noLabel" class="label-title" :style="{ 'width': diyWidth }">
            <label>{{ labelName }} :</label>
        </div>
        <div class="search-wapper">
            <el-date-picker
                v-model="value1"
                size="mini"
                :type="useDateType"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :value-format="useValFormat"
                :picker-options="pickerOptions"
            />
        </div>
    </div>
</template>
<script>
const moment = require('moment')
export default {
    props: {
        noLabel: { type: Boolean, default: false },
        labelName: { type: String, default: '关键词' },
        key1: { type: String, default: 'startDate' },
        key2: { type: String, default: 'endDate' },
        // 选中第一个后,确定第二个日期的可选范围在前后over天内
        over: { type: Number, default: 10000 },
        diyWidth: {
            type: String,
            default: '85px'
        },
        allWidth: {
            type: String,
            default: '350px'
        },
        // 扩展时分秒的选项
        datetype: {
            // day: 年月日 second: 年月日时分秒
            type: String,
            default: 'day'
        }
    },
    data() {
        return {
            value1: []
        }
    },
    computed: {
        useDateType() {
            if (this.datetype == 'day') {
                return 'daterange'
            }
            if (this.datetype == 'second') {
                return 'datetimerange'
            }
            return 'daterange'
        },
        useValFormat() {
            if (this.datetype == 'day') {
                return 'yyyy-MM-dd'
            }
            if (this.datetype == 'second') {
                return 'yyyy-MM-dd HH:mm:ss'
            }
            return 'yyyy-MM-dd'
        },
        pickerOptions() {
            let _this = this
            let v1 = ''
            return {
                onPick(v) {
                    v1 = v.maxDate ? '' : moment(v.minDate)
                },
                disabledDate(t) {
                    if (v1) {
                        let minD = moment(v1).add(0 - _this.over, 'days').format('YYYY-MM-DD')
                        let maxD = moment(v1).add(_this.over, 'days').format('YYYY-MM-DD')
                        let v_t = moment(t).format('YYYY-MM-DD')
                        return v_t < minD || v_t > maxD
                    } else {
                        return false
                    }
                }
            }
        }
    },
    watch: {
        value1(val) {
            console.log('时间1变化了', val)
            this.$emit('change', this.getSearchValue())
        }
    },
    methods: {
        resetSearchValue() {
            this.value1 = []
        },
        getSearchValue() {
            let returnVal = {}
            if (this.value1 && this.value1.length == 2) {
                returnVal[this.key1] = this.value1[0]
                returnVal[this.key2] = this.value1[1]
            }
            return returnVal
        }
    }
}
</script>
<style scoped lang="scss">
@import './basestyle.scss';
</style>
