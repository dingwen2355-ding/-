<template>
    <personalbody title="个人中心">
        <div class="personel-index">
            <div class="header-tab">
                <t-button theme="primary" class="margin-left-20" @click="changePass_show = true">修改密码</t-button>
            </div>
            <div class="per-main-wapper">
                <baseTab :selected="activeName" :tabs="tabs" @change="changeTab" />
                <div class="table-wapper">
                    <operaLoglist v-show="activeName=='operalog'" />
                    <loginlist v-show="activeName=='login'" />
                    <!-- <noticelist v-show="activeName=='notice'" /> -->
                </div>
                <!-- 修改密码 -->
                <changePass v-if="changePass_show" :show="changePass_show" @cancel="changePass_show = false" @sure="changePwd" />
            </div>
        </div>
    </personalbody>
</template>
<script>
import { modifyPwd } from '@/api/personal'
import { logout } from '@/api/user'
import personalbody from '../personalbody.vue'
import operaLoglist from './operaLoglist.vue'
import loginlist from './loginlist.vue'
// import noticelist from './noticelist.vue'
import changePass from './changePass.vue'
export default {
    components: {
        personalbody, operaLoglist, loginlist,
        // noticelist,
        changePass
    },
    data() {
        return {
            btnSize: 'small',
            activeName: 'operalog', // login notice
            tabs: [
                { label: '操作日志', value: 'operalog' },
                { label: '登录日志', value: 'login' }
                // { label: '消息通知', value: 'notice' }
            ],
            bindding_show: false,
            swzx_show: false,
            changePass_show: false
        }
    },
    methods: {
        changeTab(tab) {
            if (tab.value == this.activeName) return
            this.activeName = tab.value
        },
        changePwd(item) {
            modifyPwd({
                oldPassword: item.pwdOld,
                newPassword: item.pwdNew
            }).then((data) => {
                // this.changePass_show = false
                let _this = this
                logout()
                .then((data) => {
                    if (!data) {
                        return
                    } else {
                        console.log(data)
                        window.open(data.data, '_self')
                    }
                })
                .catch((err) => {
                    console.log(err);
                    _this.closePage(err)
                })
            })
        }
    }
}
</script>

<style lang="scss" scoped>
.personel-index{
    width: 100%;
    .header-tab{
        width: 100%;
        display: flex;
        // margin-bottom: 10px;
    }
    .per-main-wapper{
        width: 100%;
        background: #fff;
        .table-wapper{
            padding: 20px;
            padding-top:10px;
        }
    }
}
</style>
