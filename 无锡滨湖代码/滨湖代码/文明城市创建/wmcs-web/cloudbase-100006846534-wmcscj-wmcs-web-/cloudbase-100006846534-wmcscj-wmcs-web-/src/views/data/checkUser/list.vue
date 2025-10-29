<template>
    <baseKanbanTd title="督察人员">
        <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
            <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                    <normalSearch ref="name" labelName="姓名" keyName="name" placName="请输入姓名" />
                    <normalSearch ref="phone" labelName="手机号" keyName="name" placName="请输入手机号" />
                    <searchDept ref="deptId" labelName="所属单位"  filterType="all"/>
                    <asyncSearchPoint ref="pointsId" labelName="负责点位" keyName="pointsId" />
                </div>
            </div>
            <div slot="table">
                <div class="show-flex-r j-e-2 margin-bottom-10">
                    <t-button @click="toAddNew()">+ 新增</t-button>
                   <div style="font-size: 14px;line-height: 30px;height: 30px;margin-left: 20px;"> 正常<span style="color: green;font-size: 20px;">{{ normalCount }}</span>人；停用<span style="color: red;font-size: 20px;">{{ lockCount }}</span>人</div>
                </div>
                <div>
                    <el-table
                        ref="listtable"
                        v-loading="tableListLoding"
                        stripe
                        :data="tableData"
                    >
                        <el-table-column label="姓名" prop="name" show-overflow-tooltip min-width="120" />
                        <el-table-column label="手机号" prop="phone" show-overflow-tooltip min-width="200" />
                        <!-- <el-table-column label="身份号码" prop="cardnumber" min-width="200" /> -->
                        <!-- <el-table-column label="详细住址" prop="address" width="200" /> -->
                        <el-table-column label="部门" prop="deptName" show-overflow-tooltip min-width="200" />
                        <el-table-column label="街道/社区" prop="regionName" show-overflow-tooltip min-width="220" />
                        <el-table-column label="负责督查类型" show-overflow-tooltip min-width="180">
                            <template slot-scope="scope">
                                <dirtCheckUserType
                                    noLabel
                                    useType="form"
                                    allWidth="100%"
                                    disabled
                                    onlyShow
                                    :defaultVal="scope.row.type"
                                />
                            </template>
                        </el-table-column>
                        <el-table-column label="状态" show-overflow-tooltip width="100">
                            <template slot-scope="scope">
                                <div>
                                    <el-tag v-if="scope.row.status == 0" type="success" size="small">正常</el-tag>
                                    <el-tag v-if="scope.row.status == 1" type="danger" size="small">停用</el-tag>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column label="电子工牌" show-overflow-tooltip prop="userName" min-width="200" />
                        <el-table-column label="操作" width="270" fixed="right">
                            <template slot-scope="scope">
                                <div class="show-flex-r a-c">
                                    <div class="zr-link-p unline m-5" @click="toInfoItem(scope.row)">查看</div>
                                    <div class="zr-link-h unline m-5" @click="toEditItem(scope.row)">编辑</div>
                                    <div class="zr-link-d unline m-5" @click="delItem(scope.row)">删除</div>
                                    <div v-if="scope.row.status == 0" class="zr-link-h unline m-5" @click="doStop(scope.row)">锁定</div>
                                    <div v-if="scope.row.status == 1" class="zr-link-h unline m-5" @click="doStart(scope.row)">开启</div>
                                    <div class="zr-link-s unline m-5" v-if="scope.row.userId == null || scope.row.userId == ''" @click="bindCard(scope.row)">绑定工牌</div>
                                    <div class="zr-link-w unline m-5" v-if="scope.row.userId != null && scope.row.userId != ''" @click="unbindCard(scope.row)">解除绑定</div>
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>
                    <!-- 用户选择 -->
                    <diaSelectUserCard
                        v-if="dia_selectuser"
                        :show="dia_selectuser"
                        :selecFunc="selectCard"
                        @cancel="() => { dia_selectuser = false }"
                    />
                </div>
            </div>
        </baseSearchForm>
    </baseKanbanTd>
</template>
<script>
import { reportMixins } from '@/utils/mixins'
import { listCheckUser, deleteCheckUser, stopCheckUser, startCheckUser, bindUser, unbindUser, countByStatus } from '@/api/data/checkUser'
import xjbg from './xjbg.vue'
export default {
    components: { xjbg },
    mixins: [reportMixins],
    data() {
        return {
            dia_show_xjbg: false,
            nowItem: null,
            dia_selectuser: false,
            normalCount: 0,
            lockCount: 0
        }
    },
    mounted() {
        this.userCount()
    },
    methods: {
        userCount(){
            let _this = this;
            countByStatus({}).then(data => {
                let res = data.data;
                console.log("=========用户统计数：")
                console.log(res)
                _this.normalCount = res.normal
                _this.lockCount = res.lock
            });
        },
        getList(searchData) {
            this.getTableData(listCheckUser, searchData)
            this.userCount()
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
                return deleteCheckUser(item.id)
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        },
        doStop(item) {
            let _this = this
            new Promise((res, rej) => {
                _this.useDia = this.$dialog({
                    header: '系统提示',
                    body: `是否确认锁定名称为"${item.name}"的数据项？`,
                    style: 'color: rgba(0, 0, 0, 0.6)',
                    onConfirm: ({ e }) => {
                        res()
                    }
                })
            })
            .then(d => {
                _this.useDia.hide()
                return stopCheckUser(item.id)
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        },
        doStart(item) {
            let _this = this
            new Promise((res, rej) => {
                _this.useDia = this.$dialog({
                    header: '系统提示',
                    body: `是否确认开启名称为"${item.name}"的数据项？`,
                    style: 'color: rgba(0, 0, 0, 0.6)',
                    onConfirm: ({ e }) => {
                        res()
                    }
                })
            })
            .then(d => {
                _this.useDia.hide()
                return startCheckUser(item.id)
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        },
        bindCard(item) {
            // 绑定电子工牌
            this.nowItem = item
            this.dia_selectuser = true
        },
        unbindCard(item) {
            let _this = this
            new Promise((res, rej) => {
                _this.useDia = this.$dialog({
                    header: '系统提示',
                    body: `是否确认解除"${item.name}"的绑定账号？`,
                    style: 'color: rgba(0, 0, 0, 0.6)',
                    onConfirm: ({ e }) => {
                        res()
                    }
                })
            })
            .then(d => {
                _this.useDia.hide()
                return unbindUser({id: item.id})
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        },
        selectCard(user) {
            let _this = this
            return new Promise((res, rej) => {
                // 模拟接口
                bindUser({
                    id: _this.nowItem.id,
                    userId: user.id
                })
                .then(d => {
                    _this.refushPage()
                    res()
                    _this.$successMessage('操作成功!')
                })
            })
        }
    }
}
</script>
