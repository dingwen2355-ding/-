<template>
    <baseKanbanInfo :title="useType == 'add' ? '添加用户' : ('用户 / ' + (item.nickName || ''))">
        <div slot="headbtn" class="show-flex-r a-c padding-left-20 padding-right-20">
            <div class="show-flex-r a-c">
                <!-- <el-button type="warning" size="mini" @click="goBack()">取消</el-button> -->
            </div>
        </div>
        <div slot="body" v-loading="v_loadig" class="td-form-content-wap">
            <el-form
                ref="ruleForm"
                :model="item"
                :rules="rules"
                class="td-form-content-wap"
                size="small"
                label-position="top"
            >
                <div class="td-form-in-title">基本信息</div>
                <el-row class="show-flex-r wrap" :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="头像" prop="avatar">
                            <!-- 图片上传 -->
                            <div class="w-100">
                                <baseUpFile
                                    ref="fileList"
                                    :limit="1"
                                    :srclist="item.avatar"
                                    :showAdd="!baseDisable"
                                    :showDel="!baseDisable"
                                    onlyImg
                                    tableName="sys_user"
                                    fieldName="attachment"
                                />
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="选择账号" prop="unifyUserId">
                            <!-- 多选择框。取值于device -->
                            <div class="show-flex-r">
                                <div class="margin-right-10 td-form-val" style="width: fit-content;">
                                    <div v-if="item.unifyUserId">
                                        <t-tag
                                            theme="primary"
                                            variant="light"
                                            :closable="!baseDisable"
                                            class="margin-right-5 margin-bottom-5"
                                            @close="delUnifyUserT()"
                                        >{{ item.unifyUserName }}</t-tag>
                                    </div>
                                    <div v-else class="select-form show-flex-r d-c" style="height: 20px;">暂未设置</div>
                                </div>
                                <div v-if="!baseDisable" class="zr-link-p select-form show-flex-r d-c" style="width: 50px;height: 20px;" @click="diaSelectUser = true">添加</div>
                                <diaSelectUser
                                    v-if="diaSelectUser"
                                    :show="diaSelectUser"
                                    :selecFunc="selectUser"
                                    mode="single"
                                    @cancel="diaSelectUser = false"
                                />
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="登录账号" prop="userName">
                            <div v-if="useType == 'add'">
                                <el-input v-model="item.userName" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.userName || '' }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="用户状态" prop="status">
                            <div v-if="!baseDisable">
                                <el-radio-group v-model="item.status">
                                    <el-radio :label="0">启用</el-radio>
                                    <el-radio :label="1">禁用</el-radio>
                                </el-radio-group>
                            </div>
                            <div v-else class="td-form-val">
                                <span v-if="item.status == 0">启用</span>
                                <span v-if="item.status == 1">禁用</span>
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="姓名" prop="nickName">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.nickName" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.nickName || '' }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="性别" prop="sex">
                            <div v-if="!baseDisable">
                                <el-radio-group v-model="item.sex">
                                    <el-radio :label="1">男</el-radio>
                                    <el-radio :label="2">女</el-radio>
                                    <el-radio :label="0">未知</el-radio>                                                                                                                                                                           
                                </el-radio-group>
                            </div>
                            <div v-else class="td-form-val">
                                <span v-if="item.sex == 1">男</span>
                                <span v-if="item.sex == 2">女</span>
                                <span v-if="item.sex == 0">未知</span>
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="联系电话" prop="phonenumber">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.phonenumber" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.phonenumber || '' }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="邮箱" prop="email">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.email" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.email || '' }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="所属机构" prop="deptId">
                            <searchDept
                                ref="deptId"
                                noLabel
                                useType="form"
                                allWidth="100%"
                                :disabled="baseDisable"
                                filterType="all"
                                @change="(v) => { item.deptId = v['deptId'] || '' }"
                            />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="所属职位" prop="position">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.position" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.position || '' }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="所属角色" prop="roles">
                            <searchRoleM
                                ref="roles"
                                keyName="roles"
                                noLabel
                                useType="form"
                                allWidth="100%"
                                :disabled="baseDisable"
                                filterType="all"
                                @change="(v) => { item.roles = v['roles'] || '' }"
                            />
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <div class="td-subform-wapper">
                <t-button v-if="useType == 'add'" @click="addNew()">确定</t-button>
                <t-button v-if="useType == 'edit'" @click="editItem()">确定</t-button>
                <t-button ghost class="margin-left-20" @click="goBack()">取消</t-button>
            </div>
        </div>
    </baseKanbanInfo>
</template>
<script>
import { infoEmployee, addEmployee, editEmployee } from '@/api/system/employee'
export default {
    props: {
        useType: { type: String, default: '' }
    },
    data() {
        return {
            id: 0,
            v_loadig: false,
            formLabelWidth: '130px',
            diaSelectUser: false,
            item: {
                // ========================基本信息
                id: null,
                avatar: [],
                sex: 0,
                status: 0,
                userName: '',
                nickName: '',
                phonenumber: '',
                roles: [],
                position: '',
                email: '',
                roleId: '',
                unifyUserId: '',
                unifyUserName: ''
            },
            rules: {
                userName: [{ required: true, message: '请输入用户账号', trigger: ['blur', 'change'] }],
                nickName: [{ required: true, message: '请输入用户名称', trigger: ['blur', 'change'] }],
                deptName: [{ required: true, message: '请选择所属组织', trigger: ['blur', 'change'] }],
                roles: [{ type: 'array', required: true, message: '请选择角色', trigger: ['blur', 'change'] }]
            }
        }
    },
    computed: {
        baseDisable() {
            // 详情禁用
            return !!(this.useType == 'info')
        }
    },
    mounted() {
        if (this.useType != 'add') {
            this.id = this.$route.query.id
            this.getInfo()
        }
    },
    methods: {
        goBack() { this.$router.back(-1) },
        refushBack() {
            this.$router.push({ path: './list?_list_refush=true' })
        },
        getInfo() {
            let _this = this
            this.v_loadig = true
            // 获取详情接口
            infoEmployee(this.id)
            .then(data => {
                let v = data.data
                v.avatar = []
                _this.item = v
                _this.$refs.deptId.setValWithChange(v.deptId)
                _this.$refs.roles.setValWithChange(v.roles)
            })
            .finally(e => {
                _this.v_loadig = false
            })
        },
        useTrim() {
            // 纯字符串去掉前后空格
            let arr = ['userName', 'nickName']
            arr.forEach(k => {
                this.item[k] = this.item[k].trim()
            })
        },
        addNew() {
            let _this = this
            this.useTrim()
            let upData = JSON.parse(JSON.stringify(this.item))
            upData.avatar = (upData.avatar && upData.avatar.length > 0) ? upData.avatar[0] : ''
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    if (this.v_loadig) return
                    this.v_loadig = true
                    addEmployee(upData)
                    .then(d => {
                        _this.$successMessage('操作成功!')
                        _this.refushBack()
                    })
                    .finally(e => {
                        _this.v_loadig = false
                    })
                } else {
                    return false
                }
            })
        },
        editItem() {
            let _this = this
            this.useTrim()
            let upData = JSON.parse(JSON.stringify(this.item))
            upData.avatar = (upData.avatar && upData.avatar.length > 0) ? upData.avatar[0] : ''
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    if (this.v_loadig) return
                    this.v_loadig = true
                    editEmployee(upData)
                    .then(d => {
                        _this.$successMessage('操作成功!')
                        // _this.$emit('changeUseType', 'info')
                        _this.goBack()
                    })
                    .finally(e => {
                        _this.v_loadig = false
                    })
                } else {
                    return false
                }
            })
        },
        selectUser(userArr) {
            if (userArr) {
                this.item.unifyUserId = userArr.id
                this.item.unifyUserName = userArr.name
                this.item.userName = userArr.account
                this.item.nickName = userArr.name
                this.item.phonenumber = userArr.majorMobile
                this.item.position = userArr.majorPosition
            }
            return new Promise((res, rej) => { res() })
        },
        delUnifyUserT() {
            let _this = this
            new Promise((res, rej) => {
                _this.useDia = this.$dialog({
                    header: '系统提示',
                    body: `是否确认删除名称为"${_this.item.unifyUserName}"的数据项？`,
                    style: 'color: rgba(0, 0, 0, 0.6)',
                    onConfirm: ({ e }) => {
                        res()
                    }
                })
            })
            .then(d => {
                _this.useDia.hide()
                _this.item.unifyUserId = ''
                _this.item.unifyUserName = ''
                _this.item.userName = ''
                _this.item.nickName = ''
                _this.item.phonenumber = ''
                _this.item.position = ''
            })
        }
    }
}
</script>
