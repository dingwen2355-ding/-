<template>
    <baseSearchForm ref="searchTable" v-loading="v_page_loading" class="base-account-list-page" @getList="getList">
        <div slot="customQuery" class="listSearch per-account-list">
            <div class="search-line">
                <normalSearch
                    ref="acc"
                    keyName="username"
                    placName="请输入登录账号"
                    labelName="登录账号"
                />
                <!-- <sfNormalToF
                    ref="selec"
                    keyName="useStatus"
                    noLabel
                    showName="隐藏禁用"
                    defalutSelect
                /> -->
                <div class="search-button">
                    <el-button type="bi" size="mini" @click="startSearch">搜索</el-button>
                    <el-button type="warning" size="mini" @click="resetQuery">重置</el-button>
                    <el-button v-if="pow('zr-unknow')" type="primary" size="mini" @click="addNewAccount">新增账号</el-button>
                </div>
            </div>
        </div>
        <div slot="table">
            <el-table v-loading="tableListLoding" size="mini" :data="tableData" border style="width: 100%" stripe>
                <el-table-column v-if="$store.state.mtablel" label="操作" width="140" fixed="left">
                    <template slot-scope="scope">
                        <div v-if="scope.row.type != 1" class="show-flex-r d-c">
                            <div
                                v-if="userInfo.id != scope.row.id"
                                class="unline m-5"
                                :class="{'zr-link-h': scope.row.useStatus, 'zr-link-d': !scope.row.useStatus}"
                                @click="enableAcc(scope.row)"
                            >
                                {{ scope.row.useStatus ? '启用' : '禁用' }}
                            </div>
                            <div
                                v-if="userInfo.id != scope.row.id"
                                class="zr-link-w unline m-5"
                                @click="rePass(scope.row)"
                            >重置密码</div>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column label="登录账号" prop="username" min-width="120" />
                <el-table-column label="状态" min-width="80">
                    <template slot-scope="scope">
                        <span class="unline" :class="{'zr-link-h': !scope.row.useStatus, 'zr-link-d': scope.row.useStatus}">{{ scope.row.useStatus ? '禁用' : '有效' }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="创建时间" prop="createTime" min-width="140" />
                <el-table-column label="最后修改时间" prop="updateTime" min-width="140" />
                <el-table-column v-if="!$store.state.mtablel" label="操作" width="140" fixed="right">
                    <template slot-scope="scope">
                        <div v-if="!(scope.row.roleGroup && scope.row.roleGroup.admin)" class="show-flex-r d-c">
                            <div
                                v-if="userInfo.id != scope.row.id"
                                class="unline m-5"
                                :class="{'zr-link-h': scope.row.useStatus, 'zr-link-d': !scope.row.useStatus}"
                                @click="enableAcc(scope.row)"
                            >
                                {{ scope.row.useStatus ? '启用' : '禁用' }}
                            </div>
                            <div
                                v-if="userInfo.id != scope.row.id"
                                class="zr-link-w unline m-5"
                                @click="rePass(scope.row)"
                            >重置密码</div>
                        </div>
                    </template>
                </el-table-column>
            </el-table>
            <!-- 提示弹框 -->
            <!-- 新增账号 -->
            <addNewAccount v-if="addNew_show" :show="addNew_show" :account="nowAccount" :confirmType="confirmType" @cancel="addNew_show = false" @submitForm="submitAccount" />
        </div>
    </baseSearchForm>
</template>
<script>
// 通用配置组件统一维护,personal-api不再新增接口,直接在文件里写,方便复制
import { getEmployeeList, patchEmpStatus, resetPassword, createAccount } from '@/api/personal'
import { reportMixins } from '@/utils/mixins'
import addNewAccount from './addNewAccount.vue'
export default {
    components: { addNewAccount },
    mixins: [reportMixins],
    data() {
        return {
            addNew_show: false,
            nowAccount: {},
            confirmType: 'Create',
            v_page_loading: false
        }
    },
    computed: {
        userInfo() {
            return this.$store.state.userInfo.data
        }
    },
    methods: {
        getList(search) {
            if (search.useStatus) {
                search['useStatus'] = 0
            } else {
                delete search['useStatus']
            }
            this.getTableData(getEmployeeList, search)
        },
        addNewAccount() {
            this.addNew_show = true
            this.confirmType = 'Create'
        },
        // 启用
        enableAcc(item) {
            let message = `是否确认${!item.useStatus ? '禁用' : '启用'}该账号?`
            this.$confirm(message, '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            })
            .then(() => {
                patchEmpStatus({
                    id: item.id,
                    useStatus: item.useStatus ? 0 : 1
                }).then(() => {
                    this.startSearch()
                })
            })
        },
        // 重置密码
        rePass(item) {
            let _this = this
            let message = `是否确认重置密码?`
            this.$confirm(message, '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            })
            .then(() => {
                resetPassword(item.id).then(() => {
                    _this.$successMessage('操作成功!')
                    _this.startSearch()
                })
            })
        },
        submitAccount(account) {
            let _this = this
            if (this.v_page_loading) return
            this.v_page_loading = true
            if (this.confirmType === 'Create') {
                createAccount(account).then(() => {
                    this.addNew_show = false
                    this.v_page_loading = false
                    this.startSearch()
                })
                .finally(e => { _this.v_page_loading = false })
            }
        }
    }
}
</script>
<style lang="scss">
.per-account-list-table-name-wapper, .base-account-list-page{
    width: 100%;
    padding: 0 10px;
    display: flex;
    flex-direction: column;
    .tag-wapper{
        width: 100%;
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        .tag-item{
            max-width: 150px;
            margin-right:10px;
            margin-bottom: 5px;
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
        }
    }
    .file-upload-unshow{
        position: fixed;
        z-index: -1;
        opacity: 0;
        top: -1000000px;
        left: -100000px;
    }
}
</style>
