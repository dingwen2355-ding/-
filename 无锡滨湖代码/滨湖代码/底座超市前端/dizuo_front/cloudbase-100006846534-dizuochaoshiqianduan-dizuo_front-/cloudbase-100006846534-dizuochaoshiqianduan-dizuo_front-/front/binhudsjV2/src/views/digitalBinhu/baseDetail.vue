<template>
    <!-- 应用详情 -->
    <div>
        <div class="home-banner">
            <div class="search-wrap">
                <div class="search-title">应用详情</div>
            </div>
        </div>

        <div class="box">
            <div class="box-con _flex">
                <div class="page-icon"></div>
                应用市场 >> 应用详情
            </div>
        </div>

        <div class="appdetails-box" v-if="oAppDetails">
            <div class="appdetails-box-con block">
                <div class="appdetails-box-top ">
                    <!-- <img v-if="oAppDetails.icon" :src="item.icon" alt="" class="userinfo-item-image" /> -->
                    <img :src="sImage" class="userinfo-item-image" alt="">
                    <div class="userinfo-item-con">
                        <div class="userinfo-item-title">{{ oAppDetails.name ||
                            oAppDetails.title }}</div>
                        <div class="userinfo-item-detail">
                            {{ oAppDetails.description }}
                        </div>
                        <div class="item-box-unit">所属部门：
                            {{ fSetSsbm(oAppDetails.dept) }}
                        </div>
                        <div class="item-box-unit">负责人:{{ oAppDetails.contacts || oAppDetails.principalName }}</div>
                        <div class="item-box-unit">电话:{{ oAppDetails.telephone || oAppDetails.principalPhone }}</div>
                    </div>
                </div>

                <hr width="90%" />
                
                <template v-if="oAppDetails.appScreenshot">
                    <el-carousel indicator-position="none" :loop="true" :interval="5000" height="700px"
                        class="appdetails-box-image">
                        <el-carousel-item v-for="(item, idx) in JSON.parse(oAppDetails.appScreenshot)" :key="'1' + idx">
                            <img :src="item" class="appdetails-box-image" alt="">
                        </el-carousel-item>
                        <el-carousel-item v-for="(item, idx) in JSON.parse(oAppDetails.appScreenshot)" :key="'2' + idx">
                            <img :src="item" class="appdetails-box-image" alt="">
                        </el-carousel-item>
                    </el-carousel>

                </template>
                <img :src="oAppDetails.logo" v-else class="appdetails-box-image" alt="">
                <!-- 附件 -->
                <div class="file-wrap" v-if="oAppDetails.upload && oAppDetails.upload.length > 0">
                    <div v-for="(item, idx) in oAppDetails.upload" :key="idx" class="file-item">
                        <div class="file-item" v-if="item.newFileName" @click="fDownloadFile(item)">
                            <div class="file-item-image">
                                <svg t="1698654914185" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                    xmlns="http://www.w3.org/2000/svg" p-id="5723" width="32" height="32">
                                    <path d="M512 512m-448 0a448 448 0 1 0 896 0 448 448 0 1 0-896 0Z" fill="#55A7F7"
                                        p-id="5724"></path>
                                    <path
                                        d="M256 256m64 0l384 0q64 0 64 64l0 384q0 64-64 64l-384 0q-64 0-64-64l0-384q0-64 64-64Z"
                                        fill="#E2BF7A" p-id="5725"></path>
                                    <path d="M332 302h360v420H332z" fill="#FFFFFF" p-id="5726"></path>
                                    <path d="M322 342h380v360H322z" fill="#E9EBF1" p-id="5727"></path>
                                    <path d="M302 382h420v300H302z" fill="#FFFFFF" p-id="5728"></path>
                                    <path d="M282 422h460v300H282z" fill="#E9EBF1" p-id="5729"></path>
                                    <path
                                        d="M768 412.192V704c0 35.346-28.654 64-64 64H320c-35.346 0-64-28.654-64-64V412.187a32 32 0 0 0 15.27 27.278C380.543 506.488 460.787 540 512 540c51.212 0 131.456-33.512 240.73-100.535A32 32 0 0 0 768 412.192z"
                                        fill="#FFD786" p-id="5730"></path>
                                    <path d="M512 604m-32 0a32 32 0 1 0 64 0 32 32 0 1 0-64 0Z" fill="#CBA559" p-id="5731">
                                    </path>
                                </svg>
                            </div>
                            <div class="file-item-box">
                                <div class="item-box">{{ item.newFileName }}</div>
                                <!-- <el-button type="primary" icon="el-icon-download" >下载附件</el-button> -->
                            </div>
                        </div>
                    </div>

                </div>
            </div>

            <el-dialog title="权限申请" :visible.sync="bQxsqLayer" width="40%" :append-to-body="true">
                <el-input type="textarea" :rows="5" v-model="sReason" placeholder="请输入申请理由"></el-input>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="bQxsqLayer = false">取 消</el-button>
                    <el-button type="primary" @click="fQxsqLayer()">确 定</el-button>
                </span>
            </el-dialog>
        </div>

    </div>
</template>

<script>
import { URLS_USERINFO, URLS_SZDZ, URLS_APPMARKET ,URLS_APPAPPLY} from '@/api/url.js';
import fGetAppListAsync from '@/api/setAppList.js'
import axios from "axios";

export default {
    name: '',
    components: {},
    props: [''],
    data() {
        return {
            // sImage: JSON.parse(this.$route.query.data).details.logo || require('../../assets/image/home/appmarket/szzw_img.png'),
            // oAppDetails: JSON.parse(this.$route.query.data).details,
            aAllAppComUtil: [],
            sBtnType: '',// 按钮的类型
            bQxsqLayer: false,
            sReason: '',
            aBmData: [],
            aAllDept: [],
        }
    },
    computed: {
        oUserInfo() {
            if (this.$store.state.oUserInfo.account) {
                return this.$store.state.oUserInfo;
            } else {
                return JSON.parse(localStorage.getItem('userInfo'));
            }
        },
        oAppDetails(){
            return JSON.parse(localStorage.getItem('appDeatils'));
        },
        sImage(){
            return JSON.parse(localStorage.getItem('appDeatils')).logo || require('../../assets/image/home/appmarket/szzw_img.png');
        },
    },
    watch: {
        $route: {
            handler(newVal, oldVal) {
                // console.log(newVal)
            },
            // 深度观察监听
            immediate: true,
            deep: true
        },
    },
    created() {
        this.fRequestApplicationDept()
    },
    mounted() {
        console.log(111, this.oAppDetails)
        this.fRequestAllpp();
        // console.log(window.location)
        console.log(JSON.parse(this.$route.query.data))
    },
    beforeDestroy() {

    },
    methods: {
         // 请求所属部门
         fRequestApplicationDept() {
            // 
            this.$fRequest({
                url: URLS_APPAPPLY.u_addApplicationDept
            }).then(res => {
                // console.log(res, '请求所属部门');
                this.aBmData = res.data
                this.aAllDept = []
                // this.aBmData = [...res.data[0].children, ...res.data[1].children,]
                this.findNode(this.aBmData)
            })
        },
        findNode(arr) {
            arr.forEach(item => {
                this.aAllDept.push(item)
                item.children && item.children.length > 0 ? this.findNode(item.children) : ""
                item.children = ''
            })
        },
        fSetSsbm(id) {
            let aData = this.aAllDept.filter(item => item.id == id)
            if (this.oAppDetails.deptName || this.oAppDetails.manageDeptName) {
                return this.oAppDetails.deptName || this.oAppDetails.manageDeptName
            } else {
                return aData.length > 0 ? aData[0].title : '--'
            }
        },
        downloadFile(blob, fileName) {
            let blobUrl = window.URL.createObjectURL(new Blob([blob], { type: 'application/force-download' }))
            let a = document.createElement('a')
            a.style.display = 'none'
            a.setAttribute("href", blobUrl)
            a.setAttribute('download', `${fileName}`);
            document.body.appendChild(a)
            a.click() //执行下载
            window.URL.revokeObjectURL(blobUrl)
            document.body.removeChild(a)
        },
        // 下载附件
        fDownloadFile(item) {
            axios.get('https://2.21.138.89:11511/common/download/resource?resource=' + item.url, {
                headers: {
                    'Authorization': JSON.parse(window.localStorage.getItem('userInfo')).apiToken,
                    'ApiToken': JSON.parse(window.localStorage.getItem('userInfo')).apiToken,
                },
                responseType: "blob"
            }).then(result => {
                console.log(result)
                this.downloadFile(result.data, item.newFileName)
            }).catch(err => {
                console.log(err)
            })
        },
        // 权限申请按钮
        fCliclSq(data) {
            // console.log("权限申请")
            this.bQxsqLayer = true;
        },
        fQxsqLayer() {
            this.fRequestAddUserAuthority();

        },
        fGoToAdmin(data) {
            window.open(data.url);
            // let bFlag = false;
            // this.aAllAppComUtil.forEach(item => {
            //     if (item.url == data.url) {
            //         bFlag = true;
            //         window.open(data.url);
            //     }
            // });
            // if (!bFlag) this.$message.error('非常抱歉！您没有访问的权限！');
        },
        // 权限申请
        fRequestAddUserAuthority() {
            $.ajax({
                url: URLS_USERINFO.u_addUserAuthority,
                method: 'post',
                headers: {
                    'Authorization': JSON.parse(window.localStorage.getItem('userInfo')).apiToken,
                    'ApiToken': JSON.parse(window.localStorage.getItem('userInfo')).apiToken,
                },
                data: {
                    account: JSON.parse(window.localStorage.getItem('userInfo')).account,
                    applicant: JSON.parse(window.localStorage.getItem('userInfo')).account,
                    reason: this.sReason,
                    applicationManagementId:this.oAppDetails.casAppId,
                }
            }).then(res => {
                // console.log(res, '权限申请')
                this.$message({
                    message: '申请成功！',
                    type: 'success'
                });
                setTimeout(() => {
                    this.sBtnType = 'applying';
                    this.bQxsqLayer = false;
                }, 1000);
            })
        },
        async fRequestAllpp() {
            const res = await fGetAppListAsync();
            let aData = res.filter(item => item.sTypeName == '应用');
            let aAllData = res
            this.$store.commit('fSetAllAppDataRes', res.filter(item => item.sTypeName == '应用'));
            this.$store.commit('fSetComUtilsData', [...res.filter(item => item.sTypeName == '组件'), ...res.filter(item => item.sTypeName == '工具')]);

            this.$store.commit('fSetAllAppData', aData);// 请求全部应用
            this.$store.commit('fSetAllApp', aData);// 请求全部应用
            this.fRequestMyApp(aAllData.length);// 请求我的应用
        },
        // 请求所有应用 / 工具 / 组件
        // fRequestAllpp() {
        //     this.$fRequest({
        //         url: URLS_USERINFO.u_applicationList,
        //     }).then(res => {
        //         // console.log(111, res)
        //         let aData = []
        //         let aAllData = []
        //         this.$store.commit('fSetAllAppDataRes', res.data[2].children);
        //         this.$store.commit('fSetComUtilsData', [res.data[0], res.data[1]]);
        //         res.data.forEach(item => {
        //             aAllData.push(...this.fSetStoreAllApp(item))
        //         })

        //         // this.$store.commit('fSetAllAppComUtil',aAllData) // 

        //         let aComData = res.data.filter(item => item.title.indexOf('应用') >= 0)[0];// 获取所有组件
        //         aComData.children.forEach(item => {
        //             if (item.children && item.children.length > 0) {
        //                 aData.push(...item.children)
        //             }
        //         })
        //         this.$store.commit('fSetAllAppData', aData);// 请求全部应用
        //         this.$store.commit('fSetAllApp', aData);// 请求全部应用
        //         this.fRequestMyApp(aAllData.length);// 请求我的应用
        //         // this.fRequestAllApp(aAllData.length);// 请求全部应用 - 无分类
        //     })
        // },
        // 请求当前登录用户的应用
        fRequestMyApp(len) {
            this.$fRequest({
                url: URLS_USERINFO.u_userApplication,
                data: {
                    account: this.oUserInfo.account,
                    keyword: '',
                    current: 1,// 当前页
                    size: len + 50,// 每页数量
                }
            }).then(res => {
                // console.log(res.data[0].appList.records)
                this.aAllAppComUtil = res.data[0].appList.records
                this.aAllAppComUtil.forEach(item => {
                    if (item.url == this.oAppDetails.url) {
                        this.sBtnType = 'admin';
                        console.log('可以跳转')
                    } else {
                        this.sBtnType = '';
                    }
                });
                this.$store.commit('fSetAllAppComUtil', res.data[0].appList.records)
                this.$store.commit('fSetUserAppData', res.data[0].appList.records.sort(this.fSetDataSort('loginNumber', false)))
            })
        },
        // 设置所有组件应用工会的集合
        fSetStoreAllApp(data) {
            let aData = []
            data.children.forEach(item => {
                if (item.children && item.children.length > 0) {
                    aData.push(...item.children)
                }
            })
            return aData;
        },
        // 数组排序
        fSetDataSort(attr, rev) {
            //第二个参数没有传递 默认升序排列
            if (rev == undefined) {
                rev = 1;
            } else {
                rev = (rev) ? 1 : -1;
            }
            return function (a, b) {
                a = a[attr];
                b = b[attr];
                if (a < b) {
                    return rev * -1;
                }
                if (a > b) {
                    return rev * 1;
                }
                return 0;
            }
        },
    }
}
</script>
<style lang='less' scoped>
.home-banner {
    position: relative;
    width: 100%;
    height: 617px;
    margin-bottom: 100px;
    background: url('../../assets/image/home/bg.png') no-repeat center / 100% 617px;
}

.search-wrap {
    display: flex;
    align-items: center;
    // justify-content: center;
    flex-direction: column;
    padding-top: 102px;
    position: absolute;
    top: 0%;
    left: 0%;
    height: 100%;
    width: 100%;
    z-index: 999;
    font-weight: bold;
}

.search-title {
    color: #FFFFFF;
    font-size: 40px;
}

.appdetail-banner {
    width: 100%;
    height: 617px;
    background: url('../../assets/image/home/bg.png') no-repeat center / 100% 617px;
}

.appdetails-box {
    padding: 20px 130px 0;
    margin-bottom: 25px;

    &._flex {
        display: flex;
        justify-content: space-between;
    }

    &._flexc {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    &._flexw {
        display: flex;
        align-items: center;
        justify-content: space-between;
        flex-wrap: wrap;
    }
}

.appdetails-box-router {
    cursor: pointer;
    user-select: none;
    color: #000000;

    &:hover {
        opacity: .75;
    }

    &._color {
        color: #266FE8;

    }
}

.appdetails-box-con {
    display: flex;
    align-items: center;
    flex-direction: column;
    width: 100%;
    border-radius: 20px;
}

.appdetails-box-top {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 1640px;
    padding: 50px;
}

.userinfo-item-image {
    width: 223px;
    height: 223px;
    background: url('../../assets/image/appmarket/image.png') no-repeat center / 223px;
}

.userinfo-item-con {
    width: 1260px;
}

.userinfo-item-title {
    font-size: 26px;
    font-weight: 500;
    color: #000000;
}

.userinfo-item-detail {
    margin: 15px 0;
    font-size: 20px;
    font-weight: 300;
    color: #000000;
    text-indent: 2rem;
}

.userinfo-item-box {
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
}

.item-box-unit {
    margin-bottom: 10px;
    font-size: 14px;
    color: #000000;
}

.appmarket-more {
    font-size: 14px;
    color: #266FE8;
    cursor: pointer;
    user-select: none;

    &:hover {
        opacity: .75;
    }

    &._cen {
        text-align: center;
    }
}

.appdetails-box-details {
    width: 1530px;
    margin: 30px 0;
}

.appdetails-box-image {
    width: 1530px;
    height: 700px;
    margin-bottom: 20px;
    border-radius: 10px;
    background: url('../../assets/image/appmarket/image.png') no-repeat center / 1530px 450px;
}

/deep/.el-carousel__arrow {
    background-color: rgba(31, 45, 61, .71);
    font-size: 20px !important;
}

.box-con {
    display: flex;
    align-items: center;

    &._flex {
        align-items: center;
    }

    &._flexs {
        // align-items: center;
        justify-content: space-between;
        padding: 0 25px;
        // margin-bottom: 25px;
        background: #F3F3F3;
        height: 56px;
    }
}

.page-icon {
    width: 13px;
    height: 16px;
    margin-right: 20px;
    background: url('../../assets/image/appmarket/position.png') no-repeat center / 100%;
}

.file-wrap {
    width: 100%;
    display: flex;
    flex-wrap: wrap;
    margin: 30px;
}

.file-item {
    margin: 20px 2%;
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-around;
    cursor: pointer;
    user-select: none;

    &:hover {
        color: #266FE8;
    }
}

.file-item-image {
    width: 32px;
    height: 32px;
}

.file-item-box {
    width: calc(100% - 100px);
}

.item-box {
    line-height: 32px;
}
</style>