<template>
    <div class="baseTableSearch">
        <!-- 搜索栏 -->
        <div class="search-wapper show-flex-r">
            <div class="flex-1 show-flex-r wrap">
                <slot name="customQuery" />
            </div>
            <div class="search-button" style="margin-left:20px;margin-top:5px;">
                <t-button class="margin-right-10" @click="startS">查询</t-button>
                <t-button ghost @click="resetQ">重置</t-button>
            </div>
        </div>
        <!-- <el-divider></el-divider> -->
        <div class="table">
            <!-- 传入表格 或其他主体展示 -->
            <slot name="table" />
        </div>
        <div v-if="!noPage" class="page margin-top-20 hock-table-bi-page">
            <div class="flex-1" style="width:0;">
                <slot name="pageleft" />
            </div>
            <!-- 分页组件 -->
            <el-pagination
                :current-page.sync="currentPage"
                :page-size.sync="pageSize"
                :page-sizes="pageSizes"
                :total="total"
                layout="total, sizes, prev, pager, next, jumper"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
            />
            <div class="flex-1" style="width:0;">
                <slot name="pageright" />
            </div>
        </div>
    </div>
</template>
<script>
export default {
    props: {
        noPage: {
            type: Boolean,
            default: false
        },
        proLimit: {
            type: Number,
            default: 10
        }
    },
    data() {
        return {
            limit: this.proLimit,
            total: 0,
            page: 1,
            pageSizes: [10, 20, 40, 80],
            queryRef: {}
        }
    },
    computed: {
        currentPage: {
            get() {
                return this.page
            },
            set(val) {
                this.$emit('update:page', val)
            }
        },
        pageSize: {
            get() {
                return this.limit
            },
            set(val) {
                this.$emit('update:limit', val)
            }
        }
    },
    methods: {
        startS() { this.$emit('search') },
        resetQ() { this.$emit('reset') },
        resetQuery(searchRef) {
            // 只重置条件，不触发查询
            searchRef && (this.queryRef = searchRef)
            for (let item in this.queryRef) {
                // 所有查询组件依序执行重置
                this.queryRef[item]['resetSearchValue'] && this.queryRef[item]['resetSearchValue']()
            }
        },
        getQuery(searchRef) {
            // 缓存查询组件
            searchRef && (this.queryRef = searchRef)
            // 组装查询条件
            let searchData = {}
            for (let item in this.queryRef) {
                // 所有查询组件依序组装查询条件
                if (this.queryRef[item] && this.queryRef[item]['getSearchValue']) {
                    let query_data = this.queryRef[item]['getSearchValue']()
                    // 输入项前后去空格
                    for (let key in query_data) {
                        if (typeof query_data[key] == 'string') {
                            query_data[key] = query_data[key].trim()
                        }
                    }
                    Object.assign(searchData, query_data)
                }
            }
            return searchData
        },
        // 获取带层级关系的搜索项(团队):团队返回数组而不是最终选择
        getAllQuery(searchRef) {
            // 缓存查询组件
            searchRef && (this.queryRef = searchRef)
            // 组装查询条件
            let searchData = {}
            for (let item in this.queryRef) {
                // 优先allquery
                if (this.queryRef[item]['getAllSearchValue']) {
                    // 所有查询组件依序组装查询条件
                    let query_data = this.queryRef[item]['getAllSearch']()
                    Object.assign(searchData, query_data)
                } else if (this.queryRef[item]['getSearchValue']) {
                    // 所有查询组件依序组装查询条件
                    let query_data = this.queryRef[item]['getSearchValue']()
                    Object.assign(searchData, query_data)
                }
            }
            return searchData
        },
        getPageQuery() {
            // 组装分页条件
            let pageData = {
                page: this.page,
                limit: this.limit
            }
            return pageData
        },
        startSearch(searchRef, initPage) {
            // 外部不传，简化使用，默认初始化，组件内部需要通知非初始化
            if (!initPage) {
                // 只初始化页数
                this.page = 1
            }
            let queryFilter = this.getQuery(searchRef)
            let pageFilter = this.getPageQuery()
            if (!this.noPage) {
                Object.assign(queryFilter, pageFilter)
            }
            // console.log('搜索条件:', queryFilter)
            this.$emit('getList', queryFilter)
        },
        handleSizeChange(val) {
            // 页数改变
            this.limit = val;
            this.page = 1;
            this.startSearch(false, true)
        },
        handleCurrentChange(val) {
            // 当前页改变了
            this.page = val;
            this.startSearch(false, true)
        },
        updateTotal(total) {
            // 更新查询总数
            this.total = total
        }
    }
}
</script>
<style lang='scss' scoped>
::v-deep.baseTableSearch{
    width: 100%;
    text-align: center;
    .table{
        width: 100%;
        overflow-x: auto;
        margin-top:10px;
        .header-edit-wapper{
            width: 100%;
            padding-bottom: 10px;
            display: flex;
        }
    }
    .page{
        width: 100%;
        display: flex;
        flex-direction: row;
        justify-content: center;align-items: center;
    }
}
</style>
