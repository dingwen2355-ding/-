<template>
    <baseKanbanTd title="志愿人员">
        <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
            <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                    <normalSearch ref="name" labelName="真实姓名" keyName="name" placName="请输入真实姓名" />
                    <normalSearch ref="phone" labelName="联系电话" keyName="phone" placName="请输入联系电话" />
                    <normalSearch ref="cardnumber" labelName="身份号码" keyName="phone" placName="请输入身份号码" />
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
                        <!-- <el-table-column label="督察员类型" prop="typeName" width="150" /> -->
                        <el-table-column label="志愿者名称" prop="name" min-width="300" />
                        <el-table-column label="街道/社区" prop="regionName" width="220" />
                        <el-table-column label="联系电话" prop="phone" width="150" />
                        <el-table-column label="身份号码" prop="cardnumber" width="200" />
                        <el-table-column label="单位名称" prop="corporateName" width="300" />
                        <el-table-column label="详细住址" prop="address" width="300" />
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
import { listVolunteer, deleteVolunteer } from '@/api/data/volunteer'
export default {
    mixins: [reportMixins],
    data() {
        return {}
    },
    methods: {
        getList(searchData) {
            this.getTableData(listVolunteer, searchData)
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
                return deleteVolunteer(item.id)
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        }
    }
}
</script>
