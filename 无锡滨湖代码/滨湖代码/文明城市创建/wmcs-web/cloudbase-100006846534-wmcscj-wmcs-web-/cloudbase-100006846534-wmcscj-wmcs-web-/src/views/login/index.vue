<template>
    <div class="login">
        <div class="login_box-wapper">
            <div class="line-img-wapper">
                <img src="~@/assets/images/login-bg.png" />
            </div>
            <div class="flex-2" />
            <div class="login_box_in">
                <img class="sys-logo" src="~@/assets/images/new_logo.png" />
                <p class="title">{{ platformTitle }}</p>
                <el-form ref="form" class="login-form margin-top-20" :model="form" :rules="rules" @submit.native.prevent="sbmt">
                    <el-form-item prop="account" class="margin-bottom-30">
                        <el-input v-model="form.account" placeholder="请输入账号" clearable />
                    </el-form-item>
                    <el-form-item prop="psd" class="margin-bottom-40">
                        <el-input v-model="form.psd" placeholder="请输入密码" type="password" show-password @keyup.enter.native.prevent="sbmt" />
                    </el-form-item>
                    <div class="show-flex-r j-s a-c margin-bottom-20">
                        <el-checkbox v-model="rember">记住密码</el-checkbox>
                        <div>
                            <el-tooltip class="item" effect="dark" content="请联系系统管理员重置密码" placement="top">
                                <div class="zr-link-d" style="font-size: 14px;">
                                    <i class="el-icon-question" />
                                    忘记密码
                                </div>
                            </el-tooltip>
                        </div>
                    </div>
                    <el-form-item>
                        <div class="sbmt" @click="sbmt">登录</div>
                    </el-form-item>
                </el-form>
            </div>
            <div class="flex-1" />
        </div>
        <!-- <div class="copy-footer">{{ platCopyRight }}</div> -->
    </div>
</template>

<script>
import { login } from '@/api/user'
import Cookies from 'js-cookie'
import { platCopyRight, platformTitle } from '@/config'
export default {
    data() {
        return {
            code: this.$route.query.code,
            reditUrl: this.$route.query.redit,
            token: this.$route.query.token,
            ticket: this.$route.query.ticket,
            rember: true,
            platCopyRight: platCopyRight,
            platformTitle: platformTitle,
            form: {
                account: '',
                psd: ''
            },
            rules: {
                account: [
                    {
                        required: true,
                        message: '请输入用户账号',
                        trigger: 'blur'
                    },
                    {
                        min: 3,
                        max: 20,
                        message: '长度在 3 到 20 个字符',
                        trigger: 'blur'
                    }
                ],
                psd: [
                    {
                        required: true,
                        message: '请输入用户密码',
                        trigger: 'blur'
                    },
                    {
                        min: 3,
                        max: 20,
                        message: '长度在 3 到 20 个字符',
                        trigger: 'blur'
                    }
                ]
            }
        }
    },
    mounted() {
        if (!!window.ActiveXObject || 'ActiveXObject' in window) {
            alert('请使用新版chrome浏览器打开')
        }
        // this.checkLogin() // 是否自动登录
        // 是否需要使用记住密码
        let remberPass = JSON.parse(localStorage.getItem('rememberPass') || '{}')
        if (remberPass.userember) {
            this.rember = remberPass['rember']
        } else {
            this.rember = true // 默认是勾选上的
        }
        if (this.rember) {
            this.form.account = remberPass['account'] || ''
            this.form.psd = remberPass['psd'] || ''
        }
    },
    methods: {
        sbmt() {
            // 登录
            let _this = this
            _this.$refs.form.validate((valid) => {
                if (valid) {
                    login({
                        username: _this.form.account,
                        password: _this.form.psd
                    })
                    .then((data) => {
                        console.log(data)
                        let dataInfo = data
                        _this.setUser(dataInfo)
                    })
                    .catch(e => {
                        console.log(e)
                    })
                    return false
                } else {
                    return false
                }
            })
            return false
        },
        setUser(dataInfo) {
            if (!dataInfo) return
            // expires: 这个属性因为不可读, 所以随便设大点
            Cookies.set('token', dataInfo.data.access_token, { expires: 7200 })
            // 是否需要记住密码
            if (this.rember) {
                localStorage.setItem('rememberPass', JSON.stringify({
                    userember: true,
                    rember: this.rember,
                    account: this.form.account,
                    psd: this.form.psd
                }))
            } else {
                localStorage.setItem('rememberPass', '{}')
            }
            this.$router.push('/home')
        }
    }
}
</script>

<style lang='scss' scoped>
.login {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    display: flex;
    flex-direction: column;
    background-color: #fff;
    ::v-deep.el-input__inner {
        height: 45px;
    }
    .copy-footer{
        width: 100%;
        height: 50px;
        display: flex;
        justify-content: center;
        align-items: center;
        color: #cccccc;
    }
    .login_box-wapper {
        position: relative;
        flex: 1;
        height: 0;
        display: flex;
        flex-direction: row;
        justify-content: center;
        align-items: center;
        background-color: #fff;
        .line-img-wapper{
            // filter: blur(10px);
            // opacity: 0.5;
            position: absolute;
            left: 0;
            right: 0;
            top: 10%;
            bottom: 10%;
            z-index: 10;
            img{
                width: 100%;
                // height: 100%;
                object-fit: width;
                background-repeat: repeat-x;
            }
        }
        .login_box_in {
            width: 400px;
            // height: 540px;
            padding: 30px;
            padding-top: 10px;
            border-top: 10px solid  var(--td-brand-color);
            box-shadow: 0 0 10px 2px rgba(0, 0, 0, 0.35);
            background-color: #fff;
            z-index: 50;
            display: flex;
            flex-direction: column;
            align-items: center;
            .sys-logo{
                width: 50px;
                height: 50px;
                margin-bottom: 20px;
            }
            .title{
                font-size: 22px;
                font-weight: bold;
                color: var(--td-brand-color);
                margin-bottom: 10px;
            }
            .login-form{
                width: 100%;
                .sbmt{
                    margin-top: 10px;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    background-color: var(--td-brand-color);
                    color: #ffffff;
                    cursor: pointer;
                    font-size: 16px;
                    letter-spacing: 3px;
                    &:hover{
                        background-color: var(--td-brand-color-hover);
                    }
                }
            }
        }
    }
}
</style>
