<template>
    <baseKanbanTd title="文明家庭">
        <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
            <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                    <searchRegionDept ref="regionCode" labelName="街道/社区" keyName="regionCode"/>
                    <normalSearch ref="name" labelName="家庭名称" keyName="name" placName="请输入家庭名称" />
                    <dirtCivilizationSelectLevel ref="level" labelName="层级" keyName="level" placName="请选择层级" />
                    <normalSearch ref="address" labelName="详细地址" keyName="address" placName="请输入详细地址" />
                </div>
            </div>
            <div slot="table">
                <div class="show-flex-r j-e-2 margin-bottom-10">
                    <t-button @click="toAddNew()">+ 新增</t-button>
                </div>
                <div>
                    <el-table
                        ref="listtable"
                        v-loading="tableListLoding"
                        stripe
                        :data="tableData"
                    >
                        <el-table-column label="家庭名称" prop="name" show-overflow-tooltip min-width="150" />
                        <el-table-column label="获评层级" prop="level"  show-overflow-tooltip width="100">
                            <template slot-scope="scope">
                                <dirtCivilizationSelectLevel
                                    noLabel
                                    useType="form"
                                    allWidth="100%"
                                    disabled
                                    onlyShow
                                    :defaultVal="scope.row.level"
                                />
                            </template>
                        </el-table-column>
                        <el-table-column label="详细地址" prop="address" show-overflow-tooltip min-width="250" />
                        <el-table-column label="推荐单位联系电话" prop="recommendTel" show-overflow-tooltip width="200" />
                        <el-table-column label="备注" prop="remark" show-overflow-tooltip width="300" />
                        <el-table-column label="街道/社区" prop="regionName" show-overflow-tooltip width="220" />
                        <el-table-column label="是否展示" prop="showFlag" show-overflow-tooltip width="100">
                            <template slot-scope="scope">
                                <div>
                                    <el-tag v-if="scope.row.showFlag == 0" type="primary" size="small">展示</el-tag>
                                    <el-tag v-if="scope.row.showFlag == 1" type="danger" size="small">不展示</el-tag>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column label="是否置顶" prop="topFlag" show-overflow-tooltip width="100">
                            <template slot-scope="scope">
                                <div>
                                    <el-tag v-if="scope.row.topFlag == 0" type="primary" size="small">置顶</el-tag>
                                    <el-tag v-if="scope.row.topFlag == 1" type="danger" size="small">不置顶</el-tag>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" width="150" fixed="right">
                            <template slot-scope="scope">
                                <div class="show-flex-r a-c">
                                    <div class="zr-link-p unline m-5" @click="toInfoItem(scope.row)">详情</div>
                                    <div class="zr-link-h unline m-5" @click="toEditItem(scope.row)">修改</div>
                                    <div class="zr-link-d unline m-5" @click="delItem(scope.row)">删除</div>
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
            </div>
        </baseSearchForm>
    </baseKanbanTd>
</template>
<script>
import { reportMixins } from '@/utils/mixins'
import { listCivilizationFamily, deleteCivilizationFamily } from '@/api/data/civilizationFamily'
export default {
    mixins: [reportMixins],
    data() {
        return {}
    },
    methods: {
        getList(searchData) {
            this.getTableData(listCivilizationFamily, searchData)
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
                return deleteCivilizationFamily(item.id)
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        }
    }
}
</script>
