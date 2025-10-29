// 表格需要一键展开和收起, tableChangeHeight依赖reportMixins
export const tableExtendMixins = {
    data() {
        return {
            expend: false, // 展开和收起状态
            ExpendData: []
        }
    },
    methods: {
        expandChange(row) {
            let _this = this
            this.$nextTick(() => { _this.tableChangeHeight++ })
        },
        showExpend(key) {
            this.ExpendData = this.tableData.map(item => { return item[key] })
            this.expend = true
            this.expandChange()
        },
        closeExpend() {
            this.ExpendData = []
            this.expend = false
            this.expandChange()
        }
    }
}
