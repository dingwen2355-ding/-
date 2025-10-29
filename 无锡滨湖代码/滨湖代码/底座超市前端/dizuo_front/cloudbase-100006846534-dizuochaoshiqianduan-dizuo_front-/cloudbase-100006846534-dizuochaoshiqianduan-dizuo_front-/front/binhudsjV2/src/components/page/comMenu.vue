<template v-model="messageTotal">
    <!-- menu - 顶部导航 -->
    <div>
        <div class="header">
            <div class="header-box _wid">
                <div class="header-box-logo" @click="fChangeRouter(oPathHome)"></div>
                <div class="_flot">
                    <div v-for="(item, idx) in aHeaderNav" :key="'aHeaderNav' + idx" class="">
                        <div class="header-box-item" :class="item.chose ? '_act' : ''" @click="fChangeRouter(item)"
                            v-if="!item.children">
                            {{ item.name }}
                        </div>
                        <el-popover v-else placement="bottom" trigger="hover">
                            <div class="" v-for="(data, idx) in item.children" :key="'children' + idx">
                                <div class="header-box-item" @click="fChangeRouter(data)">
                                    {{ data.name }}
                                </div>
                            </div>
                            <div class="header-box-item" :class="item.chose ? '_act' : ''" slot="reference">{{ item.name }}
                            </div>
                        </el-popover>
                    </div>
                </div>


            </div>
            <div class="header-box">
                <el-popover placement="bottom" width="100" trigger="click">
                    <!--  -->
                    <!-- <div class="popover-item" @click="bChangePasswordlayer = true">修改密码</div> -->
                    <div class="popover-item"
                        @click="fChangeRouter({ name: '个人中心', path: '/userCenter', key: 'userCenter', page: 'userCenter', chose: false })">
                        个人中心</div>
                    <div v-if="bShowAdminBtn" class="popover-item" @click="fGoToAdmin()">管理后台
                    </div>
                    <div class="popover-item" @click="fLoginOut">退出登录</div>
                    <!-- 按钮 -->
                    <div class="header-box-item _total" slot="reference">
                        <div div class="total" v-if="messageTotal>0" >{{ messageTotal}}</div>
                        <svg t="1686708203525" class="icon" viewBox="0 0 1024 1024" version="1.1"
                            xmlns="http://www.w3.org/2000/svg" p-id="3157" width="40" height="40">
                            <path
                                d="M512 993.962667A481.962667 481.962667 0 1 1 993.962667 512 482.304 482.304 0 0 1 512 993.962667z"
                                fill="" p-id="3158"></path>
                            <path
                                d="M512 60.074667A451.925333 451.925333 0 1 1 60.074667 512 451.925333 451.925333 0 0 1 512 60.074667M512 0a512 512 0 1 0 512 512A512 512 0 0 0 512 0z"
                                fill="" p-id="3159"></path>
                            <path
                                d="M794.282667 755.029333a145.749333 145.749333 0 0 0-10.922667-13.653333 87.04 87.04 0 0 0-11.946667-11.264l-10.581333-8.192a34.133333 34.133333 0 0 0-8.192-4.778667L750.933333 716.8l-58.368-38.229333a247.466667 247.466667 0 0 0-27.306666-15.36l-78.848-38.229334v-37.546666a151.552 151.552 0 0 0 52.906666-53.248 178.858667 178.858667 0 0 0 20.138667-73.728v-28.672a245.76 245.76 0 0 0-2.730667-51.2l-2.389333-16.384a166.229333 166.229333 0 0 0-46.762667-113.664 140.288 140.288 0 0 0-116.053333-44.032 119.125333 119.125333 0 0 0-80.213333 45.397333 211.968 211.968 0 0 0-46.08 110.250667s-3.072 52.565333-2.730667 59.050666l2.730667 36.864a249.856 249.856 0 0 0 19.797333 73.728 130.389333 130.389333 0 0 0 53.248 53.248v37.546667l-78.506667 38.912q-8.874667 4.437333-17.408 9.898667L273.066667 716.8l-5.802667 3.413333a108.544 108.544 0 0 0-13.312 9.898667 136.533333 136.533333 0 0 0-16.725333 16.042667 95.232 95.232 0 0 0-13.312 20.138666A52.224 52.224 0 0 0 228.693333 819.2a344.064 344.064 0 0 0 74.069334 48.810667 427.349333 427.349333 0 0 0 467.626666-25.6 123.562667 123.562667 0 0 0 24.576-23.210667 50.858667 50.858667 0 0 0 10.581334-31.402667 52.224 52.224 0 0 0-3.072-17.749333 68.266667 68.266667 0 0 0-8.192-15.018667z"
                                fill="#FFFFFF" p-id="3160"></path>
                        </svg>
                        &nbsp;&nbsp;
                        {{ oUserInfo ? oUserInfo.name : '' }}&nbsp;欢迎您!
                    </div>
                </el-popover>

            </div>
        </div>
        <el-dialog title="修改密码" :visible.sync="bChangePasswordlayer" :append-to-body="true" width="30%"
            :before-close="handleClose">
            <!-- <span>这是一段信息</span> -->
            <div class="layer">
                <el-form label-width="100px" ref="ruleForm" :model="oChangePassWord" :rules="rules">
                    <el-form-item label="账号名：" prop="account">
                        <el-input v-model="oChangePassWord.account" :disabled="false"></el-input>
                    </el-form-item>
                    <el-form-item label="旧密码：" prop="oldPassword">
                        <el-input v-model="oChangePassWord.oldPassword" show-password></el-input>
                    </el-form-item>
                    <el-form-item label="新密码：" prop="newPassword">
                        <el-input v-model="oChangePassWord.newPassword" show-password></el-input>
                    </el-form-item>
                    <el-form-item>
                        <!-- <el-button @click="bChangePasswordlayer = false">关闭页面</el-button> -->
                        <el-button type="primary" @click="fChangePassword('ruleForm')">立即修改</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import { URLS_USERINFO, URLS_SZBH, URLS_APPMARKET, URLS_SZDZ } from '@/api/url.js';
import axios from 'axios';
export default {
    name: '',
    components: {},
    props: [''],
    data() {
        return {
            // oUserInfo: this.$store.state.oUserInfo,
            oPathHome: { name: '数字滨湖', path: '/', key: 'digitalBinhu', page: 'home', },
            aHeaderNav: [
                { name: '数字滨湖', path: '/', key: 'digitalBinhu', page: 'home', chose: true },
                // { name: '数字底座', path: '/digitalBase', key: 'digitalBase', page: 'digitalBase', chose: false },
                { name: '应用市场', path: '/appMarket', key: 'appMarket', page: 'appMarket', chose: false },
                // { name: '个人中心', path: '/userCenter', key: 'userCenter', page: 'userCenter', chose: false },
                {
                    name: '申请中心', key: 'appApply', page: 'appApply', chose: false, children: [
                        { name: '入驻申请', path: '/appApply', key: 'appApply', page: 'appApply', chose: false },
                        { name: '能力申请', path: '/appNengLiApply', key: 'appApply', page: 'appApply', chose: false },
                    ]
                },
            ],
            bChangePasswordlayer: false,
            oChangePassWord: {
                account: '',//账号
                oldPassword: '',// 旧密码
                newPassword: '',// 新密码
            },// 修改密码
            // 表单验证
            rules: {
                account: [
                    { required: true, message: '请输入账号名称', trigger: 'blur' },
                ],
                oldPassword: [
                    { required: true, message: '请输入旧密码', trigger: 'blur' },
                ],
                newPassword: [
                    { required: true, message: '请输入新密码  ', trigger: 'blur' },
                    { min: 5, max: 25, message: '长度在 5 到 25个字符' },
                    { pattern: /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[._~!@#$^&*])[A-Za-z0-9._~!@#$^&*]{8,20}$/, message: '只能输入8-25个字母、数字、下划线' }
                ],
            },
            bShowAdminBtn: false,
            messageTotal: null,
        }
    },
    computed: {
        // messageTotal() {
        //     return localStorage.getItem('messageTotal') || 0
        // },
        oUserInfo() {
            // this.fRequestUserInfo();
            if (this.$store.state.oUserInfo.account) {
                // this.bShowAdminBtn = this.$store.state.oUserInfo.adminFlag
                return this.$store.state.oUserInfo;
            } else {
                // this.bShowAdminBtn = JSON.parse(localStorage.getItem('userInfo')).adminFlag
                // console.log(JSON.parse(localStorage.getItem('userInfo')))
                return JSON.parse(localStorage.getItem('userInfo'));
            }
        },
        bShowAdminButton() {
            return localStorage.getItem('bShowAdminBtn')

        },

        apitoken() {
            if (this.$store.state.oUserInfo.apiToken) {
                return this.$store.state.oUserInfo.apiToken;
            } else {
                return JSON.parse(localStorage.getItem('userInfo')).apiToken;
            }
        },
    },
    watch: {
        $route: {
            handler(newVal, oldVal) {
                // console.log(11112222222222222111)
                this.bShowAdminBtn = this.oUserInfo?.adminFlag || JSON.parse(localStorage.getItem('bShowAdminBtn'))
                if (newVal.meta.key == 'appNengLiApply') {
                    this.aHeaderNav.forEach(item => {
                        item.chose = false;
                    });
                    this.aHeaderNav[2].chose = true
                } else {
                    if (newVal.meta.key) {
                        this.aHeaderNav.forEach(item => {
                            if (newVal.meta.key === item.key) {
                                item.chose = true;
                            } else {
                                item.chose = false;
                            }
                        });
                    }
                }

            },
            // 深度观察监听
            immediate: true,
            deep: true
        },
        bShowAdminButton: {
            handler(newVal) {
                this.bShowAdminBtn = JSON.parse(newVal)
            },
            // 深度观察监听
            immediate: true,
            deep: true
        },
    },
    async created() {
        this.messageTotal = await this.$fRequest({ // 已读
            method: 'post',
            url: this.$ip.server_ocn + 'system/notice/list?pageNum=1&pageSize=999999',
            data: {
                read: '0'
            },
        }).then(res => res.total)
    },
    mounted() {
        this.$EventBus.$on('setTotal',async (num) => {
            this.messageTotal = num;
        })
        // this.bShowAdminBtn = window.localStorage.getItem('bShowAdminBtn')
        // this.fGetUserInfo();

        this.$EventBus.$on('fGetUserBtn', (obj) => {
            this.bShowAdminBtn = obj.adminFlag;
        })
        // 是否显示管理后台按钮
        // this.$EventBus.$on('fShowAdminBtn', (flag) => {
        //     this.bShowAdminBtn = flag;
        // })
        // 是否为初始密码
        this.$EventBus.$on('fShowChangePasswordlayer', (flag) => {
            // console.log('权限', flag)
            this.bChangePasswordlayer = flag;
        })
    },
    beforeDestroy() {

    },
    methods: {
        fGetUserInfo() {
            if (JSON.parse(localStorage.getItem('userInfo')).apiToken) {
                // console.log('======>>>',JSON.parse(localStorage.getItem('userInfo')))
                this.bShowAdminBtn = JSON.parse(localStorage.getItem('userInfo')).adminFlag;
                return
            } else {
                setTimeout(() => {
                    this.fGetUserInfo();
                }, 100);
            }
        },
        // 请求用户信息
        fRequestUserInfo() {
            // console.log('请求用户信息')
            let oUserInfo = JSON.parse(localStorage.getItem('userInfo')) || null;
            let oData = {
                // code: new URL(location.href).searchParams.get('code')
            };
            if (oUserInfo && oUserInfo.account) {
                return
            } else {
                oData.code = this.$store.state.sCode;
                this.$get(URLS_USERINFO.u_getCasUserInfo, oData).then(res => {
                    // console.log(res)
                    localStorage.setItem('userInfo', JSON.stringify(res.data.userInfo))
                    this.$store.commit('fSetUserInfo', res.data.userInfo)
                }).catch(res => {
                })
            }
        },
        // 修改密码
        fChangePassword(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    this.$fRequest({
                        url: URLS_USERINFO.u_changePassword,
                        data: { ...this.oChangePassWord }
                    }).then(res => {
                        // console.log(res)

                        this.$message({
                            message: '修改成功,将于2S后跳转到登录页面!',
                            type: 'success'
                        });
                        setTimeout(() => {
                            this.fLoginOut();// 退出登录并跳转登录页面
                        }, 2000);
                        // if (res.code == 200) {
                        //   this.$message({
                        //     message: '修改成功,将于2S后跳转到登录页面!',
                        //     type: 'success'
                        //   });
                        //   this.fLoginOut();// 退出登录并跳转登录页面
                        // } else {
                        //   this.$message({
                        //     message: res.message,
                        //     type: 'warning'
                        //   });
                        // }
                    })
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
            return

        },
        // 跳转页面
        fChangeRouter(data) {
            if (!this.$store.state.bRequestState) return;
            this.$router.push(data.path)
        },
        fGoToAdmin() {
            if (!this.$store.state.bRequestState) return;
            // window.open("http://10.10.24.118:80/?code=eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6IjVjOWVkYmQ4LTU0NzUtNDhiMC04M2VlLWZjZWY3NTQyMzQ4YSJ9.M5ONWen66e3psR7DiWr8RyNLjZa3-p2wurpRVaOd4bbL1sDAzbEC3uwP1u2LYyx5roR6JWD9abSJeq8TEFTyFQ")
            window.open('https://2.21.138.89:11511/system-web/?code=' + this.apitoken + '&name=' + this.oUserInfo.name || '管理员')
            // window.open('http://2.21.137.200/api/sso/login')
        },
        openPostWindow(url, data, name) {
            var tempForm = document.createElement("form");
            tempForm.id = "tempForm1";
            tempForm.method = "get";
            //url  
            tempForm.action = url;
            /*
             * open方法不能设置请求方式，一般网页的post都是通过form来实现的。
             * 如果仅仅模拟form的提交方式，那么open方法里那种可设置窗体属性的参数又不能用。  
             * 最后想办法整了这么一个两者结合的方式，将form的target设置成和open的name参数一样的值，通过浏览器自动识别实现了将内容post到新窗口中  
             */
            tempForm.target = name;

            var hideInput = document.createElement("input");
            hideInput.type = "hidden";
            hideInput.name = "content";

            //传入传入数据，只传递了一个参数内容，实际可传递多个。  
            hideInput.value = data;

            tempForm.appendChild(hideInput);

            window.open('about:blank', name, 'height=400, width=400, top=0, left=0, toolbar=yes, menubar=yes, scrollbars=yes, resizable=yes,location=yes, status=yes');

            //添加临时form
            document.body.appendChild(tempForm);
            //必须手动的触发，否则只能看到页面刷新而没有打开新窗口  
            tempForm.submit();

            //移除临时创建的form
            document.body.removeChild(tempForm);

        },
        // 退出登录
        fLoginOut() {
            window.location.replace('https://2.21.138.89:11511/system-web/logout');
            localStorage.removeItem('userInfo');
            localStorage.clear();
            sessionStorage.clear();
            window.close();
            return

            // if (this.$router) {

            // }
            this.$router.go(-this.$router.history.current.indexOf)
            // window.location.replace('https://2.21.138.89:11511/system-web/logout');
            // axios.get(`https://2.21.138.89:11511/cmApp/replayList?pageNum=${1}&pageSize=${999}`).then(res=>{
            //     window.location.replace('https://2.21.138.89:11511/api/login');
            // })
            window.location.replace('http://2.22.51.26:30002/logout?logoutUrl=https://2.21.138.89:11511/api/login');
            // window.open('https://2.21.138.89:11511/api/login')
        },
        handleClose(done) {
            this.$confirm('确认关闭？')
                .then(_ => {
                    done();
                })
                .catch(_ => { });
        },
    }
}
</script>
<style lang='less' scoped>
.header {
    // position: absolute;
    // left: 0;
    // top: 0;
    display: flex;
    align-items: center;
    justify-content: space-between;
    height: 65px;
    width: 100%;
    background: rgba(255, 255, 255, 0.66);
    box-shadow: 0px 30px 60px rgba(0, 0, 0, 0.16);
    z-index: 999;
}

.header-box {
    display: flex;
    align-items: center;
    width: 300px;
    justify-content: end;
    min-width:300px;

    &._wid {
        justify-content: space-between;
        width: calc(100vw - 300px);
        min-width:1000px;
        // overflow: auto;
    }
}

&._flot {
    display: flex;
    align-items: center;
    justify-content: end;
    padding-right: 20px;
}

.header-box-logo {
    margin-left: 21px;
    margin-right: 15px;
    width: 288px;
    height: 40px;
    background: url('../../assets/image/home/logo.png') no-repeat center / 288px 40px;
    cursor: pointer;
    user-select: none;

    &:hover {
        color: #266FE8;
    }
}

.total {
    position: absolute;
    left: 60px;
    top: 10px;
    // width: 30px;
    height: 20px;
    color: #fff;
    text-align: center;
    font-weight: bold;
    border-radius: 10px;
    padding: 0 10px;
    font-size: 14px;
    background: red;

}

.header-box-item {
    display: flex;
    align-items: center;
    padding: 15px 40px;
    cursor: pointer;
    user-select: none;
    font-weight: 500;
    font-size: 16px;

    &._total {
        position: relative;
        // left: 10px;
        // top: 10px;
    }

    &._act {
        color: #266FE8;
        position: relative;

        &::after {
            content: '';
            position: absolute;
            bottom: 0;
            left: 0;
            width: 100%;
            height: 3px;
            background-color: #266FE8;
        }
    }

    &:hover {
        opacity: .75;
    }
}

.popover-item {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    height: 30px;
    color: #000;
    cursor: pointer;
    user-select: none;

    &:hover {
        opacity: .75;
        color: #5378b5;
    }
}
</style>