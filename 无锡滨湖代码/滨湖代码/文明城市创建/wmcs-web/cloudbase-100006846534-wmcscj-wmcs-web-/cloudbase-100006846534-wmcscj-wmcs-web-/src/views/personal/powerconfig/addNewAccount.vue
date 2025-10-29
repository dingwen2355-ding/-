<template>
    <el-dialog :title="confirmType==='Create'?'新增账号':'编辑账号'" :visible.sync="show" append-to-body width="500px" :before-close="cancel">
        <div class="dia-bd-hei per-add-new-account">
            <el-form ref="ruleForm" :model="accInfo" :rules="rules">
                <el-form-item label="登录账号:" prop="username" :label-width="formLabelWidth">
                    <div style="width:178px">
                        <el-input v-model="accInfo.username" placeholder="请填写登录账号" size="mini" autocomplete="off" />
                    </div>
                </el-form-item>
                <el-form-item label="登录密码:" prop="password" :label-width="formLabelWidth">
                    <div style="width:178px">
                        <el-input v-model="accInfo.password" type="password" placeholder="请填写登录密码" size="mini" autocomplete="off" />
                    </div>
                </el-form-item>
                <el-form-item label="确认密码:" prop="repassword" :label-width="formLabelWidth">
                    <div style="width:178px">
                        <el-input v-model="accInfo.repassword" type="password" placeholder="请确认登录密码" size="mini" autocomplete="off" />
                    </div>
                </el-form-item>
                <!-- <el-form-item v-if="accInfo.roleGroupId" label="所属角色:" prop="roleIds" :label-width="formLabelWidth">
                    <el-checkbox-group v-model="accInfo.roleIds">
                        <el-checkbox v-for="(item,index) in roles" :key="index" :label="item.id">{{ item.name }}</el-checkbox>
                    </el-checkbox-group>
                </el-form-item> -->
            </el-form>
        </div>
        <div slot="footer" class="dialog-footer">
            <el-button size="mini" @click="cancel">取 消</el-button>
            <el-button size="mini" type="bi" @click="submitForm('ruleForm')">确 定</el-button>
        </div>
    </el-dialog>
</template>
<script>
// import { getEmpDetail } from '@/api/personal'
export default {
    props: {
        show: {
            type: Boolean,
            default: false
        },
        confirmType: {
            type: String,
            default: 'Create'
        },
        account: {
            type: Object,
            default: () => {
                return {}
            }
        }
    },
    data() {
        var validatePass2 = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.accInfo.password) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        }
        return {
            rules: {
                username: [
                    { required: true, message: '请输入登录账号', trigger: 'blur' },
                    { min: 3, max: 20, message: '长度在3到20个字符', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '请输入登录密码', trigger: 'blur' },
                    { min: 3, max: 20, message: '长度在3到20个字符', trigger: 'blur' }
                ],
                repassword: [
                    { required: true, message: '请确认登录密码', trigger: 'blur' },
                    { min: 3, max: 20, message: '长度在3到20个字符', trigger: 'blur' },
                    { validator: validatePass2, trigger: 'blur' }
                ]
            },
            accInfo: {
                username: '',
                password: '',
                repassword: ''
            },
            formLabelWidth: '130px'
        }
    },
    mounted() {
        if (this.confirmType === 'Update') {
            // zr-todo:编辑
        }
    },
    methods: {
        cancel() {
            this.$emit('cancel')
        },
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    this.$emit('submitForm', this.accInfo)
                } else {
                    return false
                }
            });
        }
    }
}
</script>
<style lang="scss" scoped>
.power-kanban-wapper{
    width: 100%;
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
}
</style>
