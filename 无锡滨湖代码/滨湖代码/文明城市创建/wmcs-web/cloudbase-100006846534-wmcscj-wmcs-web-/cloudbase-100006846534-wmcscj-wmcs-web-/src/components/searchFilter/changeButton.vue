<template>
    <div class="zr-button-group" :class="{'noradis':noradis}">
        <div
            v-for="(item, index) in lists"
            :key="index"
            class="button-item"
            :class="{ 'active': outselect ? outselect==item.value : active == index, 'big':size=='big','w-disable':wdisable }"
            @click="clickButton(index, item)"
        >{{ item.label }}</div>
    </div>
</template>

<script>
export default {
    props: {
        keyName: {
            type: String,
            default: 'key'
        },
        defaultActive: {
            type: Number,
            default: 0
        },
        lists: {
            type: Array,
            default: () => []
        },
        // 首尾取消圆角
        noradis: {
            type: Boolean,
            default: false
        },
        // 大小 :mini :big
        size: {
            type: String,
            default: 'mini'
        },
        // 伪disable:看着灰,能点
        wdisable: {
            type: Boolean,
            default: false
        },
        // 多个组件共享选中状态
        outselect: {
            type: String,
            default: ''
        }
    },
    data() {
        return {
            active: this.defaultActive
        }
    },
    watch: {
        outselect(newVal, oldVal) {
            // 当多个组件共享值的时候会有一个BUG,active被缓存了,导致无法再次点击回来
            // 当共享值被改变时,判断一下如果选值在本组件选值范围外,更新active为-1
            let val_list = this.lists.map(i => { return i.value })
            if (val_list.indexOf(newVal) < 0) {
                this.outOfSelect()
            }
        }
    },
    methods: {
        outOfSelect() {
            // 共享值已经超出本组件范围了
            this.active = -1
        },
        // 点击事件，传递出两个参数（index和点击的项的row数据）
        clickButton(i, item) {
            if (this.outselect) {
                if (this.outselect == item.value) return
            } else {
                if (this.active == i) return
            }
            this.active = i
            this.$emit('BtnClick', i, item)
        },
        resetSearchValue() {
            this.active = this.defaultActive
        },
        getSearchValue() {
            let returnVal = {}
            // 如果是多个共享,则直接返回共享值
            if (this.outselect) {
                returnVal[this.keyName] = this.outselect
                return returnVal
            }
            // 非共享
            returnVal[this.keyName] = this.lists[this.active].value
            return returnVal
        }
    }
}
</script>

<style lang="scss">
.zr-button-group {
    display: flex;
    user-select: none;
    &.noradis{
        .button-item{
            margin-right: -1px;
            &:first-child {
                border-top-left-radius: 0px;
                border-bottom-left-radius: 0px;
            }
            &:last-child {
                border-top-right-radius: 0px;
                border-bottom-right-radius: 0px;
            }
        }
    }
    .button-item {
        cursor: pointer;
        padding: 5px 12px;
        background: #ffffff;
        color: #606266;
        border: 1px solid #e6e6e6;
        font-size: 16px;
        border-radius: 0;
        font-size: 12px;
        margin-right:-1px;
        &.big{
            font-size: 15px;
            padding: 5px 15px;
        }
        &.w-disable{
            background-color: #f2f6fc;
            color: #c0c4cc;
            border-color:#ebeef5;
            &:last-child {
                border-right: 1px solid #d1d1d1;
            }
            &:hover{
                cursor: not-allowed;
            }
        }
        &:first-child {
            border-top-left-radius: 5px;
            border-bottom-left-radius: 5px;
        }
        &:last-child {
            border-top-right-radius: 5px;
            border-bottom-right-radius: 5px;
            border-right: 1px solid #e6e6e6;
        }
        &:hover {
            background-color: #ffffff;
            color: var(--td-brand-color);
            border: 1px solid var(--td-brand-color);
            z-index: 10;
        }
        &.active {
            background-color: var(--td-brand-color);
            color: #ffffff;
            border: 1px solid var(--td-brand-color);
        }
    }
}
</style>
