<template>
    <!-- 事件类型选择弹框 -->
    <el-dialog :title="title" :visible.sync="show" append-to-body :width="rowWidth" :before-close="cancel">
        <div class="dia-bd-hei">
            <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery" noPage>
                <div slot="customQuery" class="listSearch">
                    <div class="search-line">
                        <normalSearch ref="name" labelName="类别名称" keyName="name" placName="请输入类别名称" />
                    </div>
                </div>
                <div slot="table">
                    <!-- 已经选择的 -->
                    <div v-if="mode == 'many'" class="show-flex-r wrap margin-bottom-10">已选择:</div>
                    <div
                        v-if="mode == 'many'"
                        class="show-flex-r wrap margin-bottom-10 padding-top-10 padding-left-10"
                        style="min-height:50px;max-height: 100px;overflow: auto;border:1px solid #EBEEF5;border-radius: 5px;"
                    >
                        <el-tag
                            v-for="(t, index) in selectList"
                            :key="index"
                            closable
                            size="small"
                            class="margin-right-10 margin-bottom-10"
                            @close="removeTag(index)"
                        >
                            {{ t.name }}
                        </el-tag>
                    </div>
                    <el-table
                        v-loading="tableListLoding"
                        stripe
                        size="mini"
                        :data="tableData"
                        row-key="id"
                        :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
                        :cell-class-name="treeCellClassName"
                        default-expand-all
                    >
                        <el-table-column label="类别名称" prop="name" min-width="500">
                            <template slot-scope="scope">
                                <div
                                    class="show-flex-r a-c"
                                    :class="{'first-indent': scope.row.zr_first_level && (!scope.row.children || scope.row.children.length == 0)}"
                                    style="display: inline-flex;"
                                >{{ scope.row.name }}</div>
                            </template>
                        </el-table-column>
                        <el-table-column label="排序" prop="orderNum" width="80" />
                        <el-table-column label="操作" width="100" fixed="right">
                            <template slot-scope="scope">
                                <!-- 只有最子级能加 -->
                                <div v-if="!(scope.row.children && scope.row.children.length > 0)" class="show-flex-r d-c">
                                    <div v-if="mode == 'single'" class="zr-link-p unline m-5" @click="selectUserSingle(scope.row)">选择</div>
                                    <div v-if="mode == 'many'" class="zr-link-p unline m-5" @click="addUserSelect(scope.row)">添加</div>
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
            </baseSearchForm>
        </div>
        <div slot="footer" class="dialog-footer">
            <t-button ghost class="margin-right-20" @click="cancel">取消</t-button>
            <t-button v-if="mode == 'many'" theme="primary" @click="manySelect()">确定</t-button>
            <!-- <el-button size="mini" @click="cancel">取 消</el-button>
            <el-button v-if="mode == 'many'" size="mini" type="bi" @click="manySelect()">确定选择</el-button> -->
        </div>
    </el-dialog>
</template>
<script>
import { reportMixins } from '@/utils/mixins'
import { pageEventType } from '@/api/workflow/eventType'
export default {
    mixins: [reportMixins],
    props: {
        usenotice: {
            // 是否通知
            type: Boolean,
            default: false
        },
        title: {
            type: String,
            default: '选择事件类型'
        },
        rowWidth: {
            // 整体宽度设置
            type: String,
            default: '80%'
        },
        show: {
            type: Boolean,
            default: false
        },
        selecFunc: {
            type: Function,
            // 外部执行方法
            default: () => {
                return new Promise((res, rej) => {
                    res()
                })
            }
        },
        getMoreAttr: {
            type: Function,
            // 使调用方可以添加额外搜索参数的能力
            default: () => { return {} }
        },
        mode: {
            type: String,
            default: 'single' // single 单选 many 多选
        },
        backType: {
            type: String,
            default: 'all'
        }
    },
    data() {
        return {
            noStart: true,
            selectList: [] // 多选模式下选择的内容
        }
    },
    mounted() {
        // 这里看是否需要初始化一些条件:如果不想等组件,可以直接去调用getList方法,组件后台自己装显示
        // 看后续需要扩展,这里先这样
        this.$nextTick(() => {
            // dia里面的内容会在mounted之后,延迟一下
            this.startSearch()
        })
    },
    methods: {
        getList(searchData) {
            // 合并外部使用参数
            let moreAttr = this.getMoreAttr()
            Object.assign(searchData, moreAttr)
            this.getTableData(pageEventType, searchData)
        },
        cancel() {
            this.$emit('cancel')
        },
        addUserSelect(item) {
            // 已经选择的就不能重复选择了
            if (this.selectList.find(i => { return i.id == item.id })) {
                return
            }
            this.selectList.push(JSON.parse(JSON.stringify(item)))
        },
        removeTag(index) {
            this.selectList.splice(index, 1)
        },
        manySelect() {
            if (this.tableListLoding) return
            this.tableListLoding = true
            let id = []
            if (this.backType == 'id') {
                id = this.selectList.map(i => { return i.id })
            } else {
                id = JSON.parse(JSON.stringify(this.selectList))
            }
            this.outerEmit(id)
        },
        selectUserSingle(user) {
            if (this.tableListLoding) return
            this.tableListLoding = true
            if (this.backType == 'id') {
                this.outerEmit(user.customerId)
            } else {
                this.outerEmit(user)
            }
        },
        outerEmit(id) {
            let _this = this
            // 执行外部操作: id 可能是单选 或者多选数组
            // 这里可以考虑几种模式:
            // 1 直接关掉,如果遇到错误,重新打开重新选
            // 2 外面执行报错了,单独调用组件内部还原转圈
            // 3(选择) 外部把执行方法作为参数传进来,内部执行完处理,成功就通知外面刷新并关闭
            this.selecFunc(id)
            .then(data => {
                if (this.usenotice) {
                    _this.$successMessage('操作成功!')
                }
                _this.$emit('refush')
                _this.$emit('cancel')
            })
            .finally(e => {
                _this.tableListLoding = false
            })
        }
    }
}
</script>
