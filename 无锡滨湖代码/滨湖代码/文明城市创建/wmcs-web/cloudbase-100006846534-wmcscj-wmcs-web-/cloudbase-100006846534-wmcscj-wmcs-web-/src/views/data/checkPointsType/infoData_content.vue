<template>
    <baseKanbanInfo :title="useType == 'add' ? '添加检查内容' : '检查内容'" styleType="A">
        <div slot="body" v-loading="v_loadig" class="td-form-content-wap">
            <baseInfoWapperA class="margin-bottom-40">
                <div>
                    <div class="form-page-tab-title">督察点位类型</div>
                    <div class="show-flex-r wrap">
                        <baseInfoWapperAItemA>
                            <span slot="lab">点位类型名称</span>
                            <span slot="con">{{ typeItem.name }}</span>
                        </baseInfoWapperAItemA>
                        <baseInfoWapperAItemA>
                            <span slot="lab">所属工单类型</span>
                            <span slot="con">{{ typeItem.eventTypeName }}</span>
                        </baseInfoWapperAItemA>
                    </div>
                    <div>
                        <baseInfoWapperAItemA style="width: 100%;">
                            <span slot="lab">备注</span>
                            <span slot="con">{{ typeItem.remark }}</span>
                        </baseInfoWapperAItemA>
                    </div>
                </div>
            </baseInfoWapperA>
            <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
                <div slot="customQuery" class="listSearch padding-left-10">
                    <div class="search-line">
                        <normalSearch ref="name" labelName="检查内容" keyName="name" placName="请输入检查内容名称" />
                    </div>
                </div>
                <div slot="table">
                    <div class="show-flex-r j-e-2 margin-bottom-10">
                        <t-button @click="addItem()">+ 新增</t-button>
                    </div>
                    <div>
                        <el-table
                            ref="listtable"
                            v-loading="tableListLoding"
                            stripe
                            :data="tableData"
                        >
                            <el-table-column label="检查内容" prop="name" min-width="500" />
                            <el-table-column label="点位类型" prop="typeName" min-width="150" />
                            <el-table-column label="分数" prop="score" min-width="80">
                                <template slot-scope="scope">
                                    <div class="show-flex-r">
                                        <span v-if="scope.row.score > 0" class="zr-link-s noc">{{ scope.row.score }}</span>
                                        <span v-if="scope.row.score < 0" class="zr-link-d noc">{{ scope.row.score }}</span>
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column label="排序" prop="orderNum" min-width="100" />
                            <el-table-column label="创建时间" prop="createTime" min-width="170" />
                            <el-table-column label="操作" width="100" fixed="right">
                                <template slot-scope="scope">
                                    <div class="show-flex-r a-c">
                                        <div class="zr-link-p unline m-5" @click="editItem(scope.row)">修改</div>
                                        <div class="zr-link-d unline m-5" @click="delItem(scope.row)">删除</div>
                                    </div>
                                </template>
                            </el-table-column>
                        </el-table>
                        <!-- 添加 -->
                        <addContent
                            v-if="show_add_new"
                            ref="addcontent"
                            useType="add"
                            :show="show_add_new"
                            @cancel="show_add_new = false"
                            @finish="startSearch"
                        />
                        <!-- 编辑 -->
                        <addContent
                            v-if="show_edit_new"
                            ref="edittype"
                            useType="edit"
                            :show="show_edit_new"
                            @cancel="show_edit_new = false"
                            @finish="refushPage"
                        />
                    </div>
                </div>
            </baseSearchForm>
            <!-- <div class="td-subform-wapper">
                <t-button v-if="useType == 'add'" @click="addNew()">确定</t-button>
                <t-button v-if="useType == 'edit'" @click="editItem()">确定</t-button>
                <t-button ghost class="margin-left-20" @click="goBack()">取消</t-button>
            </div> -->
        </div>
    </baseKanbanInfo>
</template>
<script>
import { reportMixins } from '@/utils/mixins'
import { infoCheckPointsType, getPointsContentList, delPointsContent } from '@/api/data/checkPointsType'
import addContent from './addContent.vue'
export default {
    components: { addContent },
    mixins: [reportMixins],
    props: {
        useType: { type: String, default: '' }
    },
    data() {
        return {
            noStart: true,
            typeId: 0,
            id: 0,
            v_loadig: false,
            typeItem: {},
            // =====
            show_add_new: false,
            show_edit_new: false,
            show_info_new: false
        }
    },
    mounted() {
        this.typeId = this.$route.query.id
        this.getTypeInfo()
        this.startSearch()
    },
    methods: {
        goBack() { this.$router.back(-1) },
        getList(searchData) {
            searchData['typeId'] = this.typeId
            this.getTableData(getPointsContentList, searchData)
        },
        refushBack() {
            this.$router.push({ path: './list?_list_refush=true' })
        },
        getTypeInfo() {
            let _this = this
            infoCheckPointsType(this.typeId)
            .then(data => {
                let v = data.data
                _this.typeItem = v
            })
        },
        addItem() {
            let _this = this
            this.show_add_new = true
            this.$nextTick(() => {
                _this.$refs.addcontent.addInitItem({
                    typeId: _this.typeId
                })
            })
        },
        // infoItem(item) {
        //     this.show_info_new = true
        //     this.$nextTick(() => {
        //         this.$refs.infotype.initItem(item)
        //     })
        // },
        editItem(item) {
            this.show_edit_new = true
            this.$nextTick(() => {
                this.$refs.edittype.initItem(item)
            })
        },
        delItem(item) {
            let _this = this
            new Promise((res, rej) => {
                _this.useDia = this.$dialog({
                    header: '系统提示',
                    body: `是否确认删除名称为"${item.name}"的数据项？`,
                    style: 'color: rgba(0, 0, 0, 0.6)',
                    onConfirm: ({ e }) => {
                        res()
                    }
                })
            })
            .then(d => {
                _this.useDia.hide()
                return delPointsContent(item.id)
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        }
    }
}
</script>
