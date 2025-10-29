<template>
    <baseKanbanTd title="文明校园">
        <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
            <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                    <searchRegionDept ref="regionCode" labelName="街道/社区" keyName="regionCode"/>
                    <normalSearch ref="name" labelName="学校名称" keyName="name" placName="请输入学校名称" />
                    <normalSearch ref="address" labelName="详细地址" keyName="address" placName="请输入详细地址" />
                    <dirtCivilizationSchoolType ref="civilizationSchoolType" labelName="校园类型" keyName="civilizationSchoolType" pplacName="请选择校园类型" />
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
                        <el-table-column label="校园名称" prop="name" show-overflow-tooltip min-width="300" />
                        <el-table-column label="校园性质" prop="attribute" show-overflow-tooltip width="100">
                            <template slot-scope="scope">
                                <dirtCivilizationSchoolAttribute
                                    noLabel
                                    useType="form"
                                    allWidth="100%"
                                    disabled
                                    onlyShow
                                    :defaultVal="scope.row.attribute"
                                />
                            </template>
                        </el-table-column>
                        <el-table-column label="获评层级" prop="level" show-overflow-tooltip width="100">
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
                        <el-table-column label="评选季度" prop="selectDate"  show-overflow-tooltip min-width="150" />
                        <el-table-column label="校园类型" prop="selectType" show-overflow-tooltip width="120">
                            <template slot-scope="scope">
                                <dirtCivilizationSchoolType
                                    noLabel
                                    useType="form"
                                    allWidth="100%"
                                    disabled
                                    onlyShow
                                    :defaultVal="scope.row.selectType"
                                />
                            </template>
                        </el-table-column>
                        <el-table-column label="详细地址" prop="address" show-overflow-tooltip min-width="250" />
                        <!-- <el-table-column label="法定代表人" prop="legalLeader" show-overflow-tooltip width="120" /> -->
                        <!-- <el-table-column label="创建负责人" prop="creator" show-overflow-tooltip width="100" /> -->
                        <!-- <el-table-column label="联系人" prop="contact" show-overflow-tooltip width="100" /> -->
                        <el-table-column label="街道/社区" prop="regionName" show-overflow-tooltip width="220" />
                        <el-table-column label="是否展示" prop="showFlag" show-overflow-tooltip width="100">
                            <template slot-scope="scope">
                                <div>
                                    <el-tag v-if="scope.row.showFlag == 0" type="primary" size="small">展示</el-tag>
                                    <el-tag v-if="scope.row.showFlag == 1" type="danger" size="small">不展示</el-tag>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column label="是否置顶" prop="topFlag" width="100">
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
import { listCivilizationSchool, deleteCivilizationSchool } from '@/api/data/civilizationSchool'
export default {
    mixins: [reportMixins],
    data() {
        return {}
    },
    methods: {
        getList(searchData) {
            this.getTableData(listCivilizationSchool, searchData)
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
                return deleteCivilizationSchool(item.id)
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        }
    }
}
</script>
