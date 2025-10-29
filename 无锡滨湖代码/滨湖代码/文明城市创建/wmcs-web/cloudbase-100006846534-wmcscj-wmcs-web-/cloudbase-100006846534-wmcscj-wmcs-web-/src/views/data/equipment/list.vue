<template>
    <baseKanbanTd title="设备管理">
        <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
            <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                    <normalSearch ref="code" labelName="设备编号" keyName="name" placName="请输入设备编号" />
                    <normalSearch ref="userName" labelName="人员姓名" keyName="userName" placName="请输入人员姓名" />
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
                        <el-table-column label="设备名称" prop="name" min-width="300" />
                        <el-table-column label="设备编号" prop="code" min-width="200" />
                        <el-table-column label="绑定用户" prop="userName" min-width="200" />
                        <el-table-column label="联系电话" prop="phonenumber" min-width="200" />
                        <el-table-column label="创建时间" prop="createTime" width="170" />
                        <el-table-column label="操作" width="200" fixed="right">
                            <template slot-scope="scope">
                                <div class="show-flex-r a-c">
                                    <div class="zr-link-p unline m-5" @click="toEditItem(scope.row)">修改</div>
                                    <div class="zr-link-d unline m-5" @click="delItem(scope.row)">删除</div>
                                    <div class="zr-link-s unline m-5" v-if="scope.row.userId == null || scope.row.userId == ''" @click="toBindUser(scope.row)">绑定用户</div>
                                    <div class="zr-link-w unline m-5" v-if="scope.row.userId != null && scope.row.userId != ''" @click="unbind(scope.row)">解除绑定</div>
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>
                    <!-- 用户选择 -->
                    <diaSelectUserCard
                        v-if="show_dia_bind"
                        :show="show_dia_bind"
                        :selecFunc="selectCard"
                        @cancel="() => { show_dia_bind = false }"
                    />
                </div>
            </div>
        </baseSearchForm>
    </baseKanbanTd>
</template>
<script>
import { reportMixins } from '@/utils/mixins'
import { listEquipment, deleteEquipment, bindUser, unbindUser } from '@/api/data/equipment'
export default {
    mixins: [reportMixins],
    data() {
        return {
            show_dia_bind: false,
            nowItem: null
        }
    },
    methods: {
        getList(searchData) {
            this.getTableData(listEquipment, searchData)
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
                return deleteEquipment(item.id)
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        },
        toBindUser(item) {
            this.nowItem = item
            this.show_dia_bind = true
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
        },
        unbind(item) {
            let _this = this
            new Promise((res, rej) => {
                _this.useDia = this.$dialog({
                    header: '系统提示',
                    body: `是否确认解除"${item.name}"的绑定`,
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
        }
    }
}
</script>
