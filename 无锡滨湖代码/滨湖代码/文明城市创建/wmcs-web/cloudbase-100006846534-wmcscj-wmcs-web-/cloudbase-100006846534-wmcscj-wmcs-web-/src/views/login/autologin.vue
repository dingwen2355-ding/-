<template>
    <div />
</template>

<script>
import Cookies from 'js-cookie'
import { authorization, tohome } from '@/api/user'
export default {
    data() {
        return {
            code: this.$route.query.code,
            reditUrl: this.$route.query.redit,
            token: this.$route.query.token,
            ticket: this.$route.query.ticket
        }
    },
    mounted() {
        this.checkLogin()
    },
    methods: {
        closePage(message) {
            console.log(message)
            // this.$alert(message, '自动登录失败', {
            //     confirmButtonText: '确定',
            //     callback: action => {
            //         this.$router.push({ path: '/login' })
            //     }
            // })
        },
        checkLogin() {
            let _this = this
            console.log(111)
            if (!this.code) {
                authorization().then(data => {
                    if (!data) {
                        return
                    } else {
                        console.log(data)
                        window.open(data.data, '_self')
                    }
                }).catch(err => {
                    console.log(err);
                    _this.closePage(err)
                });
            } else {
                console.log(222)
                tohome(this.code).then(data => {
                    if (!data) {
                        return
                    } else {
                        Cookies.set('token', data.data.access_token, { expires: 7200 })
                        _this.$router.push('/home')
                    }
                }).catch(err => {
                    console.log(err);
                    _this.closePage(err)
                })
            }
        }
    }
}
</script>
