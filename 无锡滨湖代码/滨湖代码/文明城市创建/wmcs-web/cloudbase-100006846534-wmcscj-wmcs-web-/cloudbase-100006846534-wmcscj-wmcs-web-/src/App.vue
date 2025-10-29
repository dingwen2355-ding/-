<template>
    <div id="app">
        <router-view />
    </div>
</template>

<script>
import Vue from 'vue'
import { getVersion } from '@/config/version/index'
import { refushToken } from '@/api/refushToken'
import Cookies from 'js-cookie'
const config = require('@/config/index')
const searchApi = require('@/api/search')
const moment = require('moment')
let version = getVersion()
console.log('%cZR平台中心%cv' + version, 'padding: 2px 5px; background-color:#35495E; color: #fff;border-radius:3px 0 0 3px', 'padding: 2px 5px; background-color:#41B883; color: #fff;border-radius: 0 3px 3px 0');
export default {
    data() {
        return {
            plaTitle: config.platformTitle,
            queue_messge_id: 0,
            queue_refush_token_id: 0
        }
    },
    watch: {
        // 需要执行搜索条件的数组队列
        '$store.state.searchArrFirst'(val) {
            setTimeout(() => {
                if (val.length == 0) return
                let _this = this
                let arr = []
                while (val.length > 0) {
                    arr.push(val.pop())
                }
                // 去重
                arr = Array.from(new Set(arr))
                arr.forEach(api => {
                    searchApi[api]()
                        .then(data => {
                            _this.$store.commit('storeSearchFilter', {
                                key: api,
                                data: data.data
                            })
                        })
                })
            }, 100)
        }
    },
    created() {
        let _this = this
        // 修改标题名称
        document.title = this.plaTitle
        // 注册一个全局监听窗口变化的事件,利用store发送全局监听
        window.addEventListener('resize', function () {
            _this.$store.commit('setWindowChange', {
                height: document.body.clientHeight,
                width: document.body.clientWidth
            })
        })
        // 消息错误通知过滤
        this.registerMessage()
        // 刷token
        this.registerRefushToken()
        // 挂载全局配件
        // 全局弹框确定函数
        Vue.prototype.$zrComSure = (txt, title, infotype) => {
            let _this = this
            return new Promise((res, rej) => {
                _this.$confirm(txt, title || '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: infotype || 'warning'
                })
                    .then(r => {
                        res(r)
                    })
            })
        }
    },
    destroyed() {
        // 清理错误消息轮询
        clearInterval(this.queue_messge_id)
        // 清理刷新token
        // clearInterval(this.queue_refush_token_id)
    },
    methods: {
        collapse(i) {
            // 菜单横向收起事件
            // this.isCollapse = !i
            this.$diyWindowResize()
        },
        registerMessage() {
            let _this = this
            // 注册全局的消息弹框过滤: 同样的错误在500ms内不会重复弹出: 这里暂时只做同一批, 如果后续需要优化可以在加缓存500Ms对比机制,暂时没有必要
            this.queue_messge_id = setInterval(InverErrMessage, 500)
            function InverErrMessage() {
                // 错误的消息通知 + 正确的消息(主要为了统一弹出时间)
                // 正确的消息在头部添加'_type_success_'
                let messageList = window.queue_messageArr
                if (messageList.length == 0) return
                window.queue_messageArr = []
                messageList = Array.from(new Set(messageList))
                messageList.forEach(msg => {
                    if (msg.indexOf('_type_success_') > -1) {
                        _this.$message({
                            message: msg.substring(14),
                            type: 'success',
                            duration: 2 * 1000
                        })
                    } else {
                        _this.$message({
                            message: msg,
                            type: 'error',
                            duration: 2 * 1000
                        })
                    }
                })
            }
        },
        registerRefushToken() {
            let _this = this
            // 注册刷新token队列: 因为刷token的请求本身是异步的, 且时间大于循环时间, 所以不做Interval处理, 改为setTimeout
            let t_arr = window.queue_refushTokenArr
            // 没有, 下一轮
            if (t_arr.length == 0) {
                setTimeout(_this.registerRefushToken, 20)
                return
            }
            // 有
            refushToken()
                .then(data => {
                    if (data.data.code == 0) {
                        let tokenInfo = data.data.data
                        // expires: 这个属性因为不可读, 所以随便设大点
                        Cookies.set('token', tokenInfo.accessToken, { expires: 7200 })
                        Cookies.set('tokenOverTime', moment().add(tokenInfo.expireIn, 'seconds').format('YYYY-MM-DD HH:mm:ss'), { expires: 7200 })
                        Cookies.set('refreshToken', tokenInfo.refreshToken, { expires: 7200 })
                        _this.$store.state.commit('upNewToken', tokenInfo.accessToken)
                    }
                    while (window.queue_refushTokenArr.length > 0) {
                        let fuc = window.queue_refushTokenArr.pop()
                        try {
                            fuc()
                        } catch (e) {
                            console.log(e);
                        }
                    }
                    setTimeout(_this.registerRefushToken, 20)
                })
                .catch(e => {
                    while (window.queue_refushTokenArr.length > 0) {
                        let fuc = window.queue_refushTokenArr.pop()
                        try {
                            fuc()
                        } catch (e) {
                            console.log(e);
                        }
                    }
                    setTimeout(_this.registerRefushToken, 100)
                })
        }
    },
    beforeMount() {
        console.log(process.env.NODE_ENV);
        if (process.env.NODE_ENV == "development") {
            const oScript = document.createElement('script');
            oScript.type = 'text/javascript';
            oScript.src = '//minedata.com.cn/minemapapi/v2.1.0/minemap.js';
            document.head.appendChild(oScript);
            const link1 = document.createElement('link');
            link1.rel = 'stylesheet';
            link1.href = '//minedata.com.cn/support/static/api/demo/js-api/zh/css/demo.css';
            document.head.appendChild(link1);
            const link2 = document.createElement('link');
            link2.rel = 'stylesheet';
            link2.href = '//minedata.com.cn/minemapapi/v2.1.0/minemap.css';
            document.head.appendChild(link2);
        } else {
            const oScript = document.createElement('script');
            oScript.type = 'text/javascript';
            oScript.src = '//2.22.131.158:21009/minemapapi/v2.1.0/minemap.js';
            document.head.appendChild(oScript);
            const link1 = document.createElement('link');
            link1.rel = 'stylesheet';
            link1.href = '//2.22.131.158:21009/support/static/api/demo/js-api/zh/css/demo.css';
            document.head.appendChild(link1);
            const link2 = document.createElement('link');
            link2.rel = 'stylesheet';
            link2.href = '//2.22.131.158:21009/minemapapi/v2.1.0/minemap.css';
            document.head.appendChild(link2);
        }
    }
}
</script>

<style lang='scss'>
#app {
    background-color: $thBackColor;
    height: 100vh;
    width: 100vw;
}
</style>
