<!-- 本系统用: 统计本周,月,季度 -->
<template>
    <div class="show-flex-r">
        <!-- 标签选择 -->
        <div style="margin-right:10px;">
            <el-radio-group v-model="timeTag" size="small" @change="changeTag">
                <el-radio-button label="day">每日</el-radio-button>
                <el-radio-button label="week">每周</el-radio-button>
                <el-radio-button label="month">每月</el-radio-button>
            </el-radio-group>
        </div>
        <div class="zr-100-el-wapper" style="width: 260px;">
            <el-date-picker
                v-model="value"
                size="small"
                type="daterange"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                value-format="yyyy-MM-dd"
                :clearable="false"
                @change="changeTime"
            />
        </div>
    </div>
</template>
<script>
const moment = require('moment')
moment.locale('zh-cn', { week: { dow: 1, doy: 4 }})
export default ({
    props: {
        key1: {
            type: String,
            default: 'startTime'
        },
        key2: {
            type: String,
            default: 'endTime'
        },
        defaultTag: {
            type: String,
            default: 'day'
        }
    },
    data() {
        return {
            timeTag: this.defaultTag, // day week month
            theDay: [],
            theWeek: [],
            theMonth: [],
            theQ: [],
            value: []
        }
    },
    watch: {
        value() {
            this.$emit('change', this.getSearchValue())
        }
    },
    mounted() {
        // 本日
        let day1 = moment().format('YYYY-MM-DD')
        let day2 = moment().format('YYYY-MM-DD')
        this.theDay = [day1, day2]
        // 本周
        let week1 = moment().startOf('week').format('YYYY-MM-DD')
        let week2 = moment().endOf('week').format('YYYY-MM-DD')
        this.theWeek = [week1, week2]
        // 本月
        let month1 = moment().startOf('month').format('YYYY-MM-DD')
        let month2 = moment().endOf('month').format('YYYY-MM-DD')
        this.theMonth = [month1, month2]
        // 本季度
        let q1 = moment().startOf('quarter').format('YYYY-MM-DD')
        let q2 = moment().endOf('quarter').format('YYYY-MM-DD')
        this.theQ = [q1, q2]
        // 使用默认
        this.changeTag()
    },
    methods: {
        changeTag() {
            if (this.timeTag == 'day') {
                this.value = JSON.parse(JSON.stringify(this.theDay))
            }
            if (this.timeTag == 'week') {
                this.value = JSON.parse(JSON.stringify(this.theWeek))
            }
            if (this.timeTag == 'month') {
                this.value = JSON.parse(JSON.stringify(this.theMonth))
            }
            if (this.timeTag == 'q') {
                this.value = JSON.parse(JSON.stringify(this.theQ))
            }
        },
        changeTime() {
            let str = 'diy'
            if (this.value && this.value.length > 0) {
                if (this.value[0] == this.theDay[0] && this.value[1] == this.theDay[1]) str = 'day'
                if (this.value[0] == this.theWeek[0] && this.value[1] == this.theWeek[1]) str = 'week'
                if (this.value[0] == this.theMonth[0] && this.value[1] == this.theMonth[1]) str = 'month'
                if (this.value[0] == this.theQ[0] && this.value[1] == this.theQ[1]) str = 'q'
            }
            this.timeTag = str
        },
        resetSearchValue() {
            this.timeTag = this.defaultTag
            this.changeTime()
        },
        getSearchValue() {
            let returnVal = {}
            if (!this.value || this.value.length == 0) return returnVal
            returnVal[this.key1] = this.value[0]
            returnVal[this.key2] = this.value[1]
            return returnVal
        }
    }
})
</script>

