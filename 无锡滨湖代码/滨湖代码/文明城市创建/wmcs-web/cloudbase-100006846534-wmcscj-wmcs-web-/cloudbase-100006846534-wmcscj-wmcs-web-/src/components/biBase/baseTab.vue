<template>
    <div class="base-tab-line">
        <div
            v-for="(tab, index) in tabPower"
            :key="index"
            class="tab-item"
            :class="{'select': tab.value == selected}"
            @click="change(tab)"
            v-html="tab.label"
        >
            <!-- {{ tab.label }} -->
        </div>
    </div>
</template>
<script>
export default {
    props: {
        selected: {
            type: String,
            default: ''
        },
        tabs: {
            type: Array,
            default: () => { return [] }
        },
        usePower: {
            type: Boolean,
            default: false
        }
    },
    computed: {
        // 如果使用了权限, 则需要对tabs进行过滤
        tabPower() {
            let allpow = this.$store.state.accessCodeList
            if (this.usePower) {
                let newArr = this.tabs.filter(i => {
                    return allpow.indexOf(i.value) > -1
                })
                this.usePowTab(newArr)
                return newArr
            } else {
                return this.tabs
            }
        }
    },
    methods: {
        change(tab) {
            this.$emit('change', tab)
        },
        usePowTab(arr) {
            // 如果当前选择的值已经被过滤掉了, 则通知外面更新新的值
            let pArr = arr.map(i => { return i.value })
            if (pArr.indexOf(this.selected) < 0) {
                if (arr.length == 0) {
                    this.$emit('useNewTab', '')
                } else {
                    this.$emit('useNewTab', arr[0].value)
                }
            }
        }
    }
}
</script>

<style lang="scss" scoped>
.base-tab-line{
    width: 100%;
    padding: 0 20px;
    border-bottom: 1px solid #DCDFE6;
    font-size: 14px;
    color: #303133;
    background: #fff;
    display: flex;
    flex-direction: row;
    overflow: auto;
    .tab-item{
        cursor: pointer;
        user-select: none;
        padding:15px 10px 10px 10px;
        border-bottom: 2px solid #fff;
        min-width: fit-content;
        display: flex;
        justify-content: center;
        align-items: center;
        text-align: center;
        &.select{
            color: var(--td-brand-color);
            border-bottom: 2px solid var(--td-brand-color);
        }
    }
}
</style>
