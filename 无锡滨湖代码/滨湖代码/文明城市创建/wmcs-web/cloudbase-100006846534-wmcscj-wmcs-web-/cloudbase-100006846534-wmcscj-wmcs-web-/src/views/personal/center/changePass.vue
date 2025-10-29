<template>
    <el-dialog title="密码修改" :visible.sync="show" append-to-body width="520px" :before-close="cancel">
        <el-form ref="ruleForm" :model="userInfo" :rules="rules">
            <el-form-item label="原密码" prop="pwdOld" :label-width="formLabelWidth">
                <el-input v-model="userInfo.pwdOld" size="small" type="password" autocomplete="off" />
            </el-form-item>
            <el-form-item label="新密码" prop="pwdNew" :label-width="formLabelWidth">
                <el-input v-model="userInfo.pwdNew" size="small" type="password" autocomplete="off" />
            </el-form-item>
            <el-form-item label="再次输入新密码" prop="passAgain" :label-width="formLabelWidth">
                <el-input v-model="userInfo.passAgain" size="small" type="password" autocomplete="off" />
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <t-button ghost class="margin-right-20" @click="cancel">取 消</t-button>
            <t-button theme="primary" @click="submitForm('ruleForm')">确 定</t-button>
            <!-- <el-button size="mini" @click="cancel">取 消</el-button> -->
            <!-- <el-button size="mini" type="bi" @click="submitForm('ruleForm')">确 定</el-button> -->
        </div>
    </el-dialog>
</template>
<script>
export default {
    props: {
        show: {
            type: Boolean,
            default: false
        }
    },
    data() {
        var validatePass2 = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.userInfo.pwdNew) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        };
        return {
            rules: {
                pwdOld: [
                    { required: true, message: '请输入密码', trigger: 'blur' }
                ],
                pwdNew: [
                    { required: true, message: '请输入新密码', trigger: 'blur' },
                    { min: 3, max: 20, message: '长度在3到20个字符', trigger: 'blur' }
                ],
                passAgain: [
                    { required: true, message: '请再次输入新密码', trigger: 'blur' },
                    { min: 3, max: 20, message: '长度在3到20个字符', trigger: 'blur' },
                    { validator: validatePass2, trigger: 'blur' }
                ]
            },
            userInfo: {
                 pwdOld: '',
                 pwdNew: '',
                 passAgain: ''
            },
            formLabelWidth: '130px'
        }
    },
    mounted() {
        console.log('获取基本信息', this.userInfo)
    },
    methods: {
        cancel() {
            this.$emit('cancel')
        },
        submitForm() {
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    this.$emit('sure', this.userInfo)
                } else {
                    return false
                }
            })
        }
    }
}
</script>
