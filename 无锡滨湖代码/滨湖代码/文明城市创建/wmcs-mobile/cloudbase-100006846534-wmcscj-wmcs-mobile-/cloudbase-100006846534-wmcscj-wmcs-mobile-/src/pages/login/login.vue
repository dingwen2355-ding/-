<template>
    <view class="login-page">
        <div class="logo">
        </div>
        <uni-forms ref="form" :modelValue="formData" label-position="left" :rules="rules">
            <uni-forms-item required label="用户名" name="username">
                <uni-easyinput type="text" v-model="formData.username" placeholder="请输入用户名" />
            </uni-forms-item>
            <uni-forms-item required name="password" label="密码">
                <uni-easyinput type="password" v-model="formData.password" placeholder="请输入密码" />
            </uni-forms-item>
        </uni-forms>
        <button style="background-color: #4770bf;" @click="login()">登录</button>
    </view>
</template>

<script>
import uniEasyinput from "@/plugins/uni-easyinput/uni-easyinput.vue"
import uniForms from "@/plugins/uni-forms/uni-forms.vue"
import uniFormsItem from "@/plugins/uni-forms-item/uni-forms-item.vue"
export default {
    components: { uniEasyinput, uniForms, uniFormsItem },
    data() {
        return {
            account: "",
            password: "",
            formData: {
                username: '',
                password: ''
            },
            rules: {
                username: {
                    rules: [{
                        required: true,
                        errorMessage: '请输入用户名',
                    },
                    ]
                },
                password: {
                    rules: [{
                        required: true,
                        errorMessage: '请输入密码',
                    },
                    ]
                },
            }
        }
    },
    methods: {
        login() {
            this.$refs.form.validate().then(res => {
                this.$api.Login(JSON.parse(JSON.stringify(this.formData))).then(res => {
                    if (res.code == "200") {
                        uni.setStorageSync("username", this.formData.username)
                        uni.setStorageSync("access_token", res.data.access_token)
                        uni.redirectTo({
                            url: "./../gongdan/workDesk"
                        });
                    }
                })
                console.log('表单数据信息：', res);
            }).catch(err => {
                console.log('表单错误信息：', err);
            })
        }
    }
}
</script>
  
<style scoped lang="scss">
page {
    background: #f4f5fd;
}

.login-page {
    height: 100vh;
    padding: 200upx 30upx;

    .logo {
        width: 100%;
        height: 200upx;

        img {
            width: 180upx;
            height: 260upx;
        }
    }
}
</style>