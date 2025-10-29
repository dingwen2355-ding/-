<template>
    <baseKanbanTd title="文明村镇">
        <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
            <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                    <normalSearch ref="name" labelName="村镇名称" keyName="name" placName="请输入村镇名称" />
                    <dirtCivilizationSelectLevel ref="level" labelName="层级" keyName="level" placName="请选择层级" />
                    <normalSearch ref="selectDate" labelName="评选季度" keyName="selectDate" placName="评选季度" />
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
                        <el-table-column label="村镇名称" prop="name" show-overflow-tooltip min-width="250" />
                        <el-table-column label="村镇类别" prop="selectType" show-overflow-tooltip width="150">
                            <template slot-scope="scope">
                                <dirtCivilizationTownType
                                    noLabel
                                    useType="form"
                                    allWidth="100%"
                                    disabled
                                    onlyShow
                                    :defaultVal="scope.row.selectType"
                                />
                            </template>
                        </el-table-column>
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
                        <el-table-column label="评选季度" prop="selectDate"  show-overflow-tooltip min-width="150" />
                        <!-- <el-table-column label="党组织负责人" prop="partyLeader" show-overflow-tooltip width="120" /> -->
                        <!-- <el-table-column label="创建负责人" prop="creator" show-overflow-tooltip width="120" /> -->
                        <el-table-column label="街道/社区" prop="regionName" show-overflow-tooltip min-width="200" />
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
                        <el-table-column label="操作" show-overflow-tooltip width="250" fixed="right">
                            <template slot-scope="scope">
                                <div class="show-flex-r a-c">
                                    <div class="zr-link-p unline m-5" @click="toInfoItem(scope.row)">详情</div>
                                    <div class="zr-link-h unline m-5" @click="toEditItem(scope.row)">修改</div>
                                    <div class="zr-link-d unline m-5" @click="delItem(scope.row)">删除</div>
                                    <div v-if="scope.row.level == '2'" class="zr-link-p unline m-5" @click="copyItemCountry(scope.row)">上报至国家级</div>
                                    <div v-if="scope.row.level == '3'" class="zr-link-p unline m-5" @click="copyItemProvince(scope.row)">上报至省级</div>
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
import { listCivilizationTown, deleteCivilizationTown, copyCivilizationTown } from '@/api/data/civilizationTown'
export default {
    mixins: [reportMixins],
    data() {
        return {}
    },
    methods: {
        getList(searchData) {
            this.getTableData(listCivilizationTown, searchData)
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
                return deleteCivilizationTown(item.id)
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        },
        copyItemCountry(item) {
            let _this = this
            new Promise((res, rej) => {
                _this.useDia = this.$dialog({
                    header: '系统提示',
                    body: `是否确认复制"${item.name}"的数据项上报至国家级？`,
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
                data.level = 1;
                return copyCivilizationTown(data)
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        },
        copyItemProvince(item) {
            let _this = this
            new Promise((res, rej) => {
                _this.useDia = this.$dialog({
                    header: '系统提示',
                    body: `是否确认复制"${item.name}"的数据项上报至国家级？`,
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
                data.level = 2;
                return copyCivilizationTown(data)
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        }
    }
}
</script>
