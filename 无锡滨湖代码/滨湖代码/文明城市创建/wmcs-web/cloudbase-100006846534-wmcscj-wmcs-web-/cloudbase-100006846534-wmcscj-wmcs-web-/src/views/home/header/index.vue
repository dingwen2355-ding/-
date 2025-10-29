<template>
    <header class="header">
        <div class="title">
            <div class="backBox" :class="{'shou': isCollapse}" @click="goHome">
                <div class="logo-wapper">
                    <img src="~@/assets/images/new_logo.png" alt="" />
                </div>
                <span v-if="!isCollapse">{{ plaTitle || '' }}</span>
            </div>
        </div>
        <el-tooltip class="collapse-item" effect="dark" content="展开菜单" placement="right">
            <i v-show="isCollapse" class="el-i el-icon-s-unfold" @click="handleClick(true)" />
        </el-tooltip>
        <el-tooltip class="collapse-item" effect="dark" content="收起菜单" placement="right">
            <i v-show="!isCollapse" class="el-i el-icon-s-fold" @click="handleClick(false)" />
        </el-tooltip>
        <!-- 一级菜单组 -->
        <div class="first-menu-wapper">
            <firstmenulist />
        </div>
        <div class="userInfo">
            <!-- <div class="message-wapper" @click="toMessage">
                <i class="el-icon-bell msg-icon" />
                消息
                <div v-if="messageList.length > 0" class="badge">{{ messageList.length | max99 }}</div>
            </div> -->
            <!-- <div class="message-wapper" @click="toMarket()">
                应用超市底座
            </div> -->
            <div class="base-user-info-wapper">
                <div>您好，{{ userInfo && userInfo.user &&  userInfo.user.nickName || '管理员' }}</div>
                <!-- <div class="power-group">{{ userInfo.roleGroup ? userInfo.roleGroup.name : '未分配角色组' }}</div> -->
            </div>
            <div class="user-info">
                <img v-if="userInfo && userInfo.user && userInfo.user.avatar" :src="userInfo && userInfo.avatar" alt="" />
                <img v-else :src="userAvatar" alt="" />
                <i class="el-icon-arrow-down down-icon" style="color: #ffffff;" />
                <div class="down_menu">
                    <div class="in-wapper">
                        <div class="menu-line" @click="toCenter">
                            <i class="icon el-icon-user-solid" />
                            <span class="m-txt">个人中心</span>
                        </div>
                        <div v-if="pow('MenuConfig')" class="menu-line" @click="toConfig">
                            <i class="icon el-icon-s-operation" />
                            <span class="m-txt">配置中心</span>
                        </div>
                        <!-- <div v-if="pow('SettingTask')" class="menu-line" @click="toTaskCenter">
                            <i class="icon fa fa-tasks" />
                            <span class="m-txt">任务中心</span>
                        </div> -->
                        <div class="menu-line" @click="userLogout">
                            <i class="icon fa fa-sign-out" />
                            <span class="m-txt">退出登录</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </header>
</template>
<script>
// import { getNoticeList } from '@/api/personal
import { logout, me } from '@/api/user'
import Cookies from 'js-cookie'
import firstmenulist from './firstmenulist.vue'
import {
    getAccessCodeList,
    menu
} from '@/api/user'
const config = require('@/config/index')
export default {
    components: {
        // quicklink,
        firstmenulist
    },
    filters: {
        max99(val) {
            // return '99+'
            return val < 99 ? val : '99+'
        }
    },
    data() {
        return {
            messageList: [],
            plaTitle: config.platformTitle,
            time_top: '',
            time_bottom: '',
            userInfo: {},
            userAvatar: require('@/assets/images/avatar.jpg'),
            isCollapse: false
        }
    },
    watch: {
        '$store.state.userInfo.data'(newVal, oldVal) {
            this.userInfo = newVal
        },
        '$store.state.allMenuList'(newVal, oldVal) {
            this.toFirstUrl()
        }
    },
    activated() {
        // 这个钩子是当组件被keep-alive时重新加载触发的, 当keep-alive时不需要刷新啥
        // this.userInfo = JSON.parse(sessionStorage.getItem('userinfo'))
    },
    created() {
        this.getNotifyCount();
        // 更新用户信息
        this.getUserInfo()
        // 新版更新菜单信息移到此处, 考虑到也许有些项目不需要把一级菜单置顶, 就不在firstMenu内做此操作
        this.getMenuList()
    },
    methods: {
        toMessage() {
            this.$router.push({
                path: '/pa/personal/center',
                query: {
                    tab: 'notice'
                }
            })
        },
        getNotifyCount() {
            // getNoticeList({ read: 1 }).then((data) => {
            //     this.messageList = data.data
            // })
        },
        getUserInfo() {
            // 把用户信息缓存到vuex, 这样在进行路由切换时, 不用频繁重新请求
            let token = Cookies.get('token')
            let now_user_info = this.$store.state.userInfo
            console.log(now_user_info)
            if (token && now_user_info['token'] == token) {
                this.userInfo = now_user_info.data
            } else {
                me().then((data) => {
                    // 这里不直接赋值, 考虑到未来统一在vuex端做用户信息本地化处理, 等处理完成再监听vuex赋值
                    // this.userInfo = data.data.data
                    this.$store.commit('setUserInfo', {
                        data: data.data.loginInfo,
                        token: token
                    })
                })
            }
        },
        getMenuList() {
            let _this = this
            // 把菜单信息缓存到vuex, 这样在进行路由切换时, 不用重新请求, 同时缓存全部菜单, 一级菜单, 对应的二级菜单
            // 菜单是需要进行前端权限过滤的
            // 菜单和权限码是一起的,需要做过滤的
            let token = Cookies.get('token')
            let now_menu_list = this.$store.state.menuInfo
            // 只有需要刷新的时候请求, 其他地方自行从vuex内获取
            if (now_menu_list['token'] != token) {
                Promise.all([
                    menu(),
                    getAccessCodeList()
                ])
                .then(data => {
                    let n_menu_list = data[0].menuList
                    let access_code_list = data[0].functionList || []
                    // 直接统一交给vuex处理结果, 此处不做额外判断
                    _this.$store.commit('setMenuCode', {
                        data: {
                            'menu_list': n_menu_list,
                            'access_code': access_code_list
                        },
                        token: token
                    })
                    // 判断如果当前是/home则需要手动跳转
                    this.$nextTick(() => {
                        this.toFirstUrl()
                    })
                })
            }
        },
        toFirstUrl() {
            let _this = this
            let now_url = this.$route.path
            function tof() {
                if (_this.$store.state.allMenuList.length > 0) {
                    _this.$router.push(_this.$store.state.allMenuList[0])
                } else {
                    setTimeout(() => {
                        tof()
                    }, 200)
                }
            }
            if (now_url == '/home') {
                tof()
                return
            }
            let menuList = this.$store.state.allMenuList
            if (menuList.length == 0) return
            // 这个机制只有首次刷新页面然后第一次请求菜单权限后会触发
            // 当前在非home页面且菜单缓存被刷新了,则判断当前路由是否合规
            // 路径在菜单内 路由的name 或者 path在白名单 路由的path包含白名单字符串
            if (menuList.indexOf(now_url) > -1) return
            if (config.loginRouterWhiteList.indexOf(this.$route.name) > -1) return
            if (config.loginRouterWhiteList.indexOf(this.$route.path) > -1) return
            // 白名单字符串
            let whiteArr = config.noRefushRouterPath.filter(i => {
                return this.$route.path.indexOf(i) > -1
            })
            if (whiteArr.length > 0) return
            this.$router.push(this.$store.state.allMenuList[0])
        },
        userLogout() {
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
        },
        edit() {
            this.$message({
                message: '功能暂未开通！',
                type: 'error',
                offset: 100
            })
        },
        toCenter() {
            this.$router.push('/pa/personal/center')
        },
        toConfig() {
            this.$router.push('/pa/personal/config')
        },
        toTaskCenter() {
            this.$router.push('/pa/personal/taskcenter')
        },
        handleClick(i) {
            this.isCollapse = !i
            this.$emit('collapse', i)
        },
        goHome() {
            let goPath = this.$store.state.allMenuList[0]
            if (this.$route.path == goPath) {
                return
            }
            this.$router.push(goPath)
        },
        toMarket() {
            let _this = this
            // 前往应用超市底座
            new Promise((res, rej) => {
                _this.useDia = this.$dialog({
                    header: '系统提示',
                    body: `是否确认前往应用超市底座？`,
                    style: 'color: rgba(0, 0, 0, 0.6)',
                    onConfirm: ({ e }) => {
                        res()
                    }
                })
            })
            .then(d => {
                window.open('http://2.21.137.206:11511/binhudsj', '_self')
            })
        }
    }
}
</script>

<style lang='scss' scoped>
.header {
    position: relative;
    user-select: none;
    height: 55px;
    padding-right: 20px;
    // background: #ffffff;
    background: #0052D9;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: row;
    align-items: center;
    z-index: 98;
    .el-i{
        color: #ffffff !important;
    }
    .collapse-item{
        padding-left:10px;
        font-size: 25px;
        color: #606266;
    }
    .title {
        display: flex;
        align-items: center;
        padding-right: 10px;
        .backBox {
            cursor: pointer;
            display: flex;
            flex-direction: row;
            justify-content: center;
            align-items: center;
            min-width: 230px;
            &.shou{
                min-width: 60px !important;
            }
            .logo-wapper{
                width: 30px;height: 30px;
                border-radius: 50%;
                background: #fff;
                display: flex;
                justify-content: center;
                align-items: center;
                margin: 0 10px;
                overflow: hidden;
                img {
                    width: 20px;
                    height: 20px;
                    vertical-align: middle;
                }
            }
            h1 {
                display: inline-block;
                color: $thHeaderTitle;
                margin-left: 10px;
                letter-spacing: 5px;
                font-size: 28px;
                vertical-align: middle;
            }
            span {
                font-size: 18px;
                // color: #333333;
                color: #ffffff;
                vertical-align: middle;
                font-weight: bold;
            }
        }
        i {
            font-size: 32px;
            vertical-align: middle;
            margin-left: 20px;
            // color: #ffffff;
        }
    }
    .first-menu-wapper{
        flex:1;
        padding-left:20px;
        width: 0;
        height: 100%;
    }
    .userInfo {
        display: flex;
        flex-direction: row;
        justify-content: flex-end;
        align-items: center;
        height: 100%;
        padding-left:20px;
        .message-wapper{
            cursor: pointer;
            justify-items: center;
            position: relative;
            color: #ffffff;
            font-size: 14px;
            padding-right: 22px;
            &:after{
                display: bolck;
                content: ' ';
                position: absolute;
                width: 1px;
                height: 40px;
                top: calc(50% - 20px);
                right: 0;
                background: #DCDFE6;
                z-index: -1;
            }
            .msg-icon{
                margin-right: 5px;
            }
            .badge{
                position: absolute;
                top:-10px;left:50px;
                font-size: 12px;
                padding: 3px 6px;
                border-radius: 20px;
                background: #F56C6C;
                // color: #fff;
            }
        }
        .base-user-info-wapper{
            height: 100%;
            display: flex;
            flex-direction: column;
            justify-content: center;
            padding: 0 10px;
            color: #ffffff;
            // color: #606266;
            font-size: 12px;
            z-index: 900;
            .power-group{
                margin-top:10px;
                padding: 3px 5px;
                border: 1px solid #fff;
                border-radius: 5px;
                text-align: center;
            }
        }
        .msg_box {
            // color: $thHeaderUser;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 14px;
        }
        .user-info {
            position: relative;
            display: inline-block;
            height: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
            img {
                width: 40px;
                height: 40px;
                border-radius: 10px;
                margin-right: 10px;
            }
            .down-icon{
                // color: #fff;
            }
            &:hover {
                .down_menu {
                    display: block;
                }
            }
            .down_menu {
                position: absolute;
                display: none;
                top: 50%;
                right: 0px;
                width: 150px;
                user-select: none;
                font-size: 14px;
                padding-top: 30px;
                .in-wapper{
                    background: #fff;
                    border-radius: 10px;
                    padding: 10px;
                    border: 1px solid #e4e7ed;
                    box-shadow: 0 0 2px 2px #00000010;
                    .menu-line{
                        cursor: pointer;
                        width: 100%;
                        padding: 10px 10px;
                        display: flex;
                        justify-items: center;
                        border-bottom: 1px solid #DCDFE6;
                        font-size: 14px;
                        color: #606266;
                        margin-bottom: 10px;
                        &:last-child{
                            border-bottom: none;
                            margin-bottom: 0;
                        }
                        .icon{
                            font-size: 16px;
                            margin-right: 10px;
                        }
                        &:hover{
                            color: var(--td-brand-color);
                        }
                    }
                }
                & :first-child {
                    border-top: none;
                }
                li {
                    padding: 5px 0;
                    text-align: center;
                    line-height: 30px;
                    cursor: pointer;
                    border-top: 1px solid $thHeaderUserLineSp;
                    background: $thHeaderUserLineBack;
                    color: $thHeaderUserLineText;
                    &:hover {
                        background-color: $thHeaderUserLineTHoverBack;
                        color: $thHeaderUserLineTHover;
                    }
                }
            }
        }
    }
}
</style>
