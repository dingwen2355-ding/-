<template>
    <baseKanbanTd title="志愿活动">
        <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
            <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                    <normalSearch ref="name" labelName="活动名称" keyName="name" placName="请输入活动名称" />
                    <dateMSelectM ref="activetime" labelName="活动时间" key1="beginTime" key2="endTime" datetype="second" allWidth="450px" />
                    <!-- <normalEnum ref="enum" labelName="枚举类型" enumName="dept_status" keyName="v1" searchType="radio" allWidth="450px" /> -->
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
                        <el-table-column label="活动类型" prop="type" width="150">
                            <template slot-scope="scope">
                                <dirtVoluActiveType
                                    noLabel
                                    useType="form"
                                    allWidth="100%"
                                    disabled
                                    onlyShow
                                    :defaultVal="scope.row.type"
                                />
                            </template>
                        </el-table-column>
                        <el-table-column label="活动名称" prop="name" min-width="300" />
                        <el-table-column label="活动开始时间" prop="startTime" width="170" />
                        <el-table-column label="活动结束时间" prop="endTime" width="170" />
                        <el-table-column label="活动地址" prop="address" width="300" />
                        <!-- <el-table-column label="状态" prop="status" width="100" /> -->
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
import { listVolunteerActivity, deleteVolunteerActivity } from '@/api/data/volunteerActivity'
export default {
    mixins: [reportMixins],
    data() {
        return {}
    },
    methods: {
        getList(searchData) {
            this.getTableData(listVolunteerActivity, searchData)
        },
        delItem(item) {
            let _this = this
            new Promise((res, rej) => {
                _this.useDia = this.$dialog({
                    header: '系统提示',
                    body: `是否确认删除设备编号为"${item.registerId}"的数据项？`,
                    style: 'color: rgba(0, 0, 0, 0.6)',
                    onConfirm: ({ e }) => {
                        res()
                    }
                })
            })
            .then(d => {
                _this.useDia.hide()
                return deleteVolunteerActivity(item.id)
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        }
    }
}
</script>
