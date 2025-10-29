<template>
    <baseKanbanTd title="志愿团队">
        <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
            <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                    <normalSearch ref="name" labelName="团队名称" keyName="name" placName="请输入团队名称" />
                    <dateMSelectM ref="activetime" labelName="成立时间" key1="beginTime" key2="endTime" />
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
                        <el-table-column label="团队名称" prop="name" min-width="300" />
                        <el-table-column label="团队类型" prop="type" width="200">
                            <template slot-scope="scope">
                                <dirtVoluTeamType
                                    noLabel
                                    useType="form"
                                    allWidth="100%"
                                    disabled
                                    onlyShow
                                    :defaultVal="scope.row.type"
                                />
                            </template>
                        </el-table-column>
                        <el-table-column label="街道/社区" prop="regionName" width="220" />
                        <!-- <el-table-column label="团队介绍" prop="introduction" min-width="400" /> -->
                        <el-table-column label="宣传标语" prop="slogan" width="300" />
                        <el-table-column label="成立时间" prop="establishDate" width="200" />
                        <el-table-column label="操作" width="330" fixed="right">
                            <template slot-scope="scope">
                                <div class="show-flex-r a-c">
                                    <div class="zr-link-p unline m-5" @click="toInfoItem(scope.row)">详情</div>
                                    <div class="zr-link-h unline m-5" @click="toEditItem(scope.row)">修改</div>
                                    <div class="zr-link-d unline m-5" @click="delItem(scope.row)">删除</div>
                                    <div class="zr-link-w unline m-5" @click="addUser(scope.row)">添加志愿者</div>
                                    <div class="zr-link-w unline m-5" @click="delUser(scope.row)">解除关联志愿者</div>
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>
                    <!-- 志愿者添加弹窗 -->
                    <diaSelectVolunteerUser
                        v-if="show_add_user"
                        :show="show_add_user"
                        title="添加志愿者"
                        mode="many"
                        :getMoreAttr="() => { return { teamNull: 1 } }"
                        :selecFunc="teamAddUser"
                        @cancel="() => { show_add_user = false }"
                        @refush="refushPage"
                    />
                    <!-- 志愿者解绑弹窗 -->
                    <diaSelectVolunteerUser
                        v-if="show_del_user"
                        :show="show_del_user"
                        title="解除关联志愿者"
                        mode="many"
                        :getMoreAttr="() => { return { teamId: nowTeam.id } }"
                        :selecFunc="teamDelUser"
                        @cancel="() => { show_del_user = false }"
                        @refush="refushPage"
                    />
                </div>
            </div>
        </baseSearchForm>
    </baseKanbanTd>
</template>
<script>
import { reportMixins } from '@/utils/mixins'
import { listVolunteerTeam, deleteVolunteerTeam, volunteerTeamAddUser, volunteerTeamDelUser } from '@/api/data/volunteerTeam'
export default {
    mixins: [reportMixins],
    data() {
        return {
            show_add_user: false,
            show_del_user: false,
            nowTeam: null
        };
    },
    methods: {
        getList(searchData) {
            this.getTableData(listVolunteerTeam, searchData);
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
                });
            })
                .then(d => {
                _this.useDia.hide()
                return deleteVolunteerTeam(item.id)
            })
                .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            });
        },
        addUser(item) {
            this.nowTeam = item
            this.show_add_user = true
        },
        teamAddUser(arr) {
            let _this = this
            return volunteerTeamAddUser({
                teamId: _this.nowTeam.id,
                userIds: arr.map(i => { return i.id })
            })
        },
        delUser(item) {
            this.nowTeam = item
            this.show_del_user = true
        },
        teamDelUser(arr) {
            let _this = this
            return volunteerTeamDelUser({
                teamId: _this.nowTeam.id,
                userIds: arr.map(i => { return i.id })
            })
        }
    }
}
</script>
