<template>
    <baseKanbanTd title="典型先进人物">
        <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
            <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                    <dirtRecommendType ref="recommendType" labelName="类型" keyName="recommendType" pplacName="请选类型" />
                    <searchRegionDept ref="regionCode" labelName="街道/社区" keyName="regionCode"/>
                    <dirtCivilizationSelectLevel ref="level" labelName="层级" keyName="level" placName="请选择层级" />
                    <normalSearch ref="name" labelName="姓名" keyName="name" placName="姓名" />
                    <normalSearch ref="selectDate" labelName="评选时间" keyName="selectDate" placName="评选时间" />
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
                        <el-table-column label="先进类型" prop="recommendType"  show-overflow-tooltip width="120">
                            <template slot-scope="scope">
                                <dirtRecommendType
                                    noLabel
                                    useType="form"
                                    allWidth="100%"
                                    disabled
                                    onlyShow
                                    :defaultVal="scope.row.recommendType"
                                />
                            </template>
                        </el-table-column>
                        <el-table-column label="姓名" prop="name" show-overflow-tooltip min-width="100" />
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
                        <el-table-column label="评选时间" prop="selectDate"  show-overflow-tooltip width="120" />
                        <el-table-column label="推荐街道" prop="regionName"  show-overflow-tooltip width="200" />
                        <el-table-column label="所属单位" prop="company"  show-overflow-tooltip  min-width="150" />
                        <el-table-column label="获评荣誉" prop="selectType"  show-overflow-tooltip width="100" />
                        <el-table-column label="出生年月" prop="birth"  show-overflow-tooltip width="100" />
                        <el-table-column label="是否展示" prop="showFlag"  show-overflow-tooltip width="100">
                            <template slot-scope="scope">
                                <div>
                                    <el-tag v-if="scope.row.showFlag == 0" type="primary" size="small">展示</el-tag>
                                    <el-tag v-if="scope.row.showFlag == 1" type="danger" size="small">不展示</el-tag>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column label="是否置顶" prop="topFlag"  show-overflow-tooltip width="100">
                            <template slot-scope="scope">
                                <div>
                                    <el-tag v-if="scope.row.topFlag == 0" type="primary" size="small">置顶</el-tag>
                                    <el-tag v-if="scope.row.topFlag == 1" type="danger" size="small">不置顶</el-tag>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column label="备注" prop="remark" show-overflow-tooltip width="100" />
                        <el-table-column label="操作" width="250" fixed="right">
                            <template slot-scope="scope">
                                <div class="show-flex-r a-c">
                                    <div class="zr-link-p unline m-5" @click="toInfoItem(scope.row)">查看</div>
                                    <div class="zr-link-h unline m-5" @click="toEditItem(scope.row)">编辑</div>
                                    <div class="zr-link-d unline m-5" @click="delItem(scope.row)">删除</div>
                                    <div v-if="scope.row.level == '2'" class="zr-link-p unline m-5" @click="copyItem(scope.row, 2)">上报至国家级</div>
                                    <div v-if="scope.row.level == '3'" class="zr-link-p unline m-5" @click="copyItem(scope.row, 3)">上报至省级</div>
                                    <div v-if="scope.row.level == '4'" class="zr-link-p unline m-5" @click="copyItem(scope.row, 4)">上报至市级</div>
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
import { listCivilizationAdvanced, deleteCivilizationAdvanced, copyCivilizationAdvanced } from '@/api/data/civilizationAdvanced'
export default {
    mixins: [reportMixins],
    data() {
        return {}
    },
    methods: {
        getList(searchData) {
            this.getTableData(listCivilizationAdvanced, searchData)
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
                return deleteCivilizationAdvanced(item.id)
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        },
        copyItem(item, level) {
            let _this = this
            new Promise((res, rej) => {
                _this.useDia = this.$dialog({
                    header: '系统提示',
                    body: `是否确认复制"${item.name}"的数据项上报至上级？`,
                    style: 'color: rgba(0, 0, 0, 0.6)',
                    onConfirm: ({ e }) => {
                        res()
                    }
                })
            })
            .then(d => {
                _this.useDia.hide()
                let data = {}
                data.id = item.id;
                if(level == 2) {
                    data.level = 1
                } else if(level == 3) {
                    data.level = 2
                } else {
                    data.level = 3
                }
                return copyCivilizationAdvanced(data)
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        }
    }
}
</script>
