// 普通报表封装模块
import FileSaver from 'file-saver'
import XLSX from 'xlsx'
export const reportMixins = {
    mounted() {
        // console.log('触发mounted')
        if (!this.noStart) {
            this.startSearch()
        }
    },
    activated() {
        console.log('触发actived')
        let _this = this
        this.refushPage()
        setTimeout(() => {
            _this.$refs.listtable && (this.$refs.listtable.doLayout())
        }, 300)
    },
    data() {
        return {
            allTotalDataSize: 0,
            tableData: [],
            tableSortColumn: '',
            tableSortSort: '',
            tableListLoding: null,
            searchData: {},
            Composition: null,
            // 当表格由于其他原因改变自身高度时
            tableChangeHeight: 10,
            // 详情dialog显示
            dialogInfoVisible: false,
            // 详情内传入的行信息
            infoRow: {},
            // 全选
            zrAllSelect: false,
            // 半选
            zrisIndeterminate: false,
            // 重置阻断: 部分条件自身变更会触发重新搜索, 重置的时候, 会触发多次重新搜索, 设置flag, 重置未完成期间, 阻断搜索
            requeryFlag: false
        }
    },
    computed: {
        tableHasSelectNum() {
            // 已选择数量
            return this.tableData.filter(i => {
                return i.zr_select
            }).length
        }
    },
    methods: {
        treeCellClassName({ row, column, rowIndex, columnIndex }) {
            if (columnIndex == 0) return 'cell-left'
        },
        // 清空所有选项的快捷方法
        clearZrAllSelect() {
            this.tableData.forEach(i => { i['zr_select'] = false })
            this.zrAllSelect = false
            this.zrisIndeterminate = false
        },
        changeZrSelect() {
            let arr = Array.from(new Set(this.tableData.map(i => { return i['zr_select'] })))
            this.zrAllSelect = (arr.length == 1 && arr[0] == true)
            this.zrisIndeterminate = (arr.length == 2)
        },
        handleCheckAllChange() {
            // 变更后的值, 所以是反的
            if (this.zrAllSelect) {
                this.tableData.forEach(i => { i.zr_select = true })
            } else {
                this.tableData.forEach(i => { i.zr_select = false })
            }
            this.changeZrSelect()
        },
        getZrSelectKey(key) {
            let arr = []
            arr = this.tableData.filter(i => { return i.zr_select })
            if (key) {
                arr = arr.map(i => { return i[key] })
            }
            return arr
        },
        startSearch() {
            // 开始搜索
            if (!this.requeryFlag) {
                this.$refs.searchTable.startSearch(this.$refs)
            } else {
                console.log('重置阻断搜索')
            }
        },
        resetQuery() {
            this.requeryFlag = true
            // 重置搜索内容
            this.$refs.searchTable.resetQuery(this.$refs)
            this.requeryFlag = false
            // 重置后重新搜索
            this.startSearch()
        },
        refushPage() {
            // 刷新本页面
            this.$refs.searchTable.startSearch(this.$refs, true)
        },
        getTableData(api, searchData, dataKey, oneFuc, noSetList) {
            dataKey = dataKey || 'rows'
            let _this = this
            _this.tableData = []
            // 如果有排序,则增加排序字段
            if (_this.tableSortColumn) {
                searchData['list_field'] = this.tableSortColumn
                searchData['list_type'] = this.tableSortSort
            }
            // 1 增加缓冲效果 2 当请求未返回时,禁止下一次请求
            if (_this.tableListLoding) return
            _this.tableListLoding = true
            return new Promise((resolve, reject) => {
                // 获取报表数据
                api(searchData)
                .then((res) => {
                    // dataNum: 返回数据data嵌套层级
                    // 默认: data.data.data
                    let originData = []
                    let total = 0
                    if (dataKey == 'tree') {
                        originData = res['data'] || []
                    } else {
                        originData = res['data']['records']
                        total = res['data']['total']
                    }
                    try {
                        originData.forEach(i => {
                            i['zr_select'] = false
                            i['zr_first_level'] = true
                            // 扩展给外部自定义赋值前增加每个执行的方法
                            if (oneFuc) {
                                oneFuc(i)
                            }
                        })
                        // 这里当有全选时,遇到内部刷新按钮,或者下一页后,全选半选状态可能需要清空,不然会保留,需要测试
                        _this.zrAllSelect = false
                        _this.zrisIndeterminate = false
                    } catch (error) {
                        console.log(error)
                    }
                    if (!noSetList) {
                        // 在非分页场景下, 需要额外对tableData进行过滤, 设置noSetList, 不进行赋值
                        _this.tableData = originData
                    }
                    _this.$refs.searchTable && (_this.$refs.searchTable.updateTotal(total))
                    _this.allTotalDataSize = total || 0
                    // 这里延迟处理loding是为了让表格加载完成后,重新计算表格的高度,方便计算表格到底部的距离做固定表头
                    _this.$nextTick(() => {
                        _this.tableListLoding = false
                    })
                    // 返回原始数据
                    resolve(res)
                })
                .catch((err) => {
                    console.log(err);
                    _this.$nextTick(() => {
                        _this.tableListLoding = false
                    })
                    _this.$refs.searchTable && (_this.$refs.searchTable.updateTotal(0))
                })
            })
        },
        filterCondition(obj, rest = ['page', 'per_page']) {
            rest.forEach((item) => {
                delete obj[item]
            })
            return obj
        },
        tableSortChange(sortObj) {
            this.tableSortColumn = sortObj.prop;
            this.tableSortSort = 'desc'
            if (sortObj.order) {
                sortObj.order == 'descending' && (this.tableSortSort = 'desc')
                sortObj.order == 'ascending' && (this.tableSortSort = 'asc')
            } else {
                this.tableSortSort = ''
                this.tableSortColumn = ''
            }
            this.startSearch()
        },
        // 常规的跳转
        toAddNew() {
            this.$router.push('./_add')
        },
        toEditItem(item, key, val) {
            key = key || 'id'
            val = val || item[key] || ''
            this.$router.push(`./_edit?${key}=${val}`)
        },
        toInfoItem(item, key, val) {
            key = key || 'id'
            val = val || item[key] || ''
            this.$router.push(`./_info?${key}=${val}`)
        },
        export_downExcel(name, body, style) {
            // body格式: [ { 表头1: v1, 表头2: v2 ... } ... ]
            // var wopts = {
            //     bookType: 'xlsx',
            //     bookSST: false,
            //     type: 'binary'
            // };
            let workBook = {
                SheetNames: ['Sheet1'], Sheets: {}, Props: {}
            }
            workBook.Sheets['Sheet1'] = XLSX.utils.json_to_sheet(body)
            // 设置样式
            if (style) {
                // 设置列宽
                if (style['col']) {
                    workBook.Sheets['Sheet1']['!cols'] = style['col'].map(i => {
                        return { wch: i }
                    })
                }
            }
            var wbout = XLSX.write(workBook, {
                bookType: 'xlsx',
                bookSST: true,
                type: 'array'
            })
            FileSaver.saveAs(new Blob([wbout], { type: 'application/octet-stream' }), name + '.xlsx')
        }
    }
}
