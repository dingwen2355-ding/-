<template>
    <!-- 上架申请 -->
    <div>
        <!-- <div class="home-banner">
            <div class="search-wrap">
                <div class="search-title">上架申请</div>
            </div>
        </div> -->
        <div class="box">
            <div class="box-block _bg">
                <div class="box-block-tit">能力申请</div>
            </div>
        </div>

        <!-- 表单 -->
        <div class="box _flex">
            <div class="box-block _wid">
                <el-form ref="oAddFormRules" :rules="oAddFormRules" :model="oAddForm" label-width="120px">
                    <el-row>
                        <el-col :span="24">
                            <el-form-item label="申请表上传" prop="moban">
                                <el-upload class="upload-demo" :action="sUploadUrl"
                                    :headers="{ 'Authorization': 'Bearer ' + token }" :on-preview="handlePreview"
                                    :on-success="fUploadFileV2" :on-remove="handleRemoveV2" :multiple="false"
                                    :file-list="fileListV2">
                                    <el-button class="btn-upload" size="small" type="" icon="el-icon-plus"></el-button>
                                    <el-button size="small" type="info" icon="el-icon-download"
                                        @click.stop="fClickExcel">下载模板</el-button>
                                </el-upload>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="联系人" prop="principalName">
                                <el-input v-model="oAddForm.principalName"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="联系人电话" prop="principalPhone">
                                <el-input v-model="oAddForm.principalPhone"></el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="12">
                            <el-form-item label="申请单位" prop="danwei">
                                <el-cascader v-model="oAddForm.danwei" clearable filterable :options="aSsbm"
                                    @change="fSsbmSelectChange" :props="{ value: 'id', label: 'title' }"
                                    placeholder="请选择所属部门"></el-cascader>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="单位类型" prop="danweiType">
                                <el-radio-group v-model="oAddForm.danweiType">
                                    <el-radio v-for="(item, idx) in aUnitType" :key="'aUnitType' + idx"
                                        :label="item.dictValue">{{ item.dictLabel }}</el-radio>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>

                        <el-col :span="24">
                            <el-form-item label="申请理由" prop="applicationReason">
                                <el-input type="textarea" :rows="6" placeholder="请输入内容"
                                          show-word-limit
                                          maxlength="100"
                                    v-model="oAddForm.applicationReason"></el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="24">
                            <el-form-item label="能力列表" prop="abilityList">
                                <!-- <el-select @change="fChangeNengLiList" v-model="oAddForm.abilityList" clearable filterable
                                    multiple placeholder="请选择能力列表">
                                    <el-option v-for="item in aMyAppData" :key="item.casAppId" :label="item.appName"
                                        :value="item.casAppId">
                                    </el-option>
                                </el-select> -->
                                <el-checkbox-group v-model="oAddForm.abilityList" style="display:flex;flex-wrap:wrap; flex-direction: row;">
                                    <el-checkbox v-for="item in aMyAppData" :key="item.casAppId" :label="item.appName" style="width: 40%; align-items: center;"
                                        :value="item.casAppId"></el-checkbox>
                                </el-checkbox-group>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-form-item>
                        <el-button type="primary" @click="fOnSubmit">提 交</el-button>
                        <el-button @click="fResetFormNew">重 置</el-button>
                    </el-form-item>
                </el-form>
            </div>

        </div>


    </div>
</template>

<script>
import { URLS_USERINFO, URLS_SZBH, URLS_APPMARKET, URLS_SZDZ, URLS_APPAPPLY } from '@/api/url.js';
import axios from "axios";


export default {
    name: '',
    components: {},
    props: [''],
    data() {
        return {
            oAppApplyForm: {
                name: '',//应用名称
                categoryId: '',// 应用类别
                url: '',//应用地址
                description: '',//应用介绍
                deptId: '',// 所属部门
                isGround: 0,// 是否上架
                isRecommend: 0,// 是否推荐
                isSendSms: 0,// 是否短信推送
                contacts: '',// 联系人
                telephone: '',// 电话
                logo: '',// 应用截图的地址
                logoId: '',// 应用截图的id
                icon: '',// 应用图标的地址
                iconId: '',// 应用图标的id
                applicant: JSON.parse(window.localStorage.getItem('userInfo')).account,// 申请人账号
                reason: '',// 申请理由
            },
            sUploadUrl: URLS_APPAPPLY.u_uploadFile,// 文件上传地址
            aSsbm: [], // 所属部门
            aAppLogoData: [],// 应用截图的数据

            sUploadUrl: 'https://2.21.138.89:11511/system_server/common/upload',
            // sUploadUrl: 'http://10.10.29.141:8080/common/upload',
            token: JSON.parse(window.localStorage.getItem('userInfo')).apiToken,
            fileList: [],
            fileListV2: [],

            oAddForm: {
                principalName: JSON.parse(localStorage.getItem('userInfo')).name,// 联系人
                principalPhone: JSON.parse(localStorage.getItem('userInfo')).phone,// 联系人电话号码
                danwei: '', // 所属部门
                applicationReason: "",// 应用介绍
                moban: [],
                danweiType: '',
                abilityList: [],
            },
            oAddFormRules: {
                moban: [
                    { required: true, message: '请上传申请表', trigger: 'change' }
                ],
                principalName: [
                    { required: true, message: '请输入联系人', trigger: 'blur' }
                ],
                principalPhone: [
                    { required: true, message: '请输入联系人电话号码', trigger: 'blur' },
                    {
                        required: true,
                        pattern: /^[1][3,5,8]\d{9}$|^\d{7,8}$|^(\d{4}|\d{3})-(\d{7,8}$)|^\d{3,5}$|^(\d{4}|\d{3})-(\d{7,8})-(\d{4})$/,
                        message: '请输入正确的联系方式',
                        trigger: 'blur',
                    },
                ],
                danweiType: [
                    { required: true, message: '请选择单位类型', trigger: 'change' }
                ],
                danwei: [
                    { required: true, message: '请选择所属部门', trigger: 'change' }
                ],
                applicationReason: [
                    { required: true, message: '请输入申请理由', trigger: 'blur' }
                ],
                appScreenshot: [
                    { required: true, message: '请上传应用成果', trigger: 'blur,change' }
                ],
                abilityList: [
                    { required: true, message: '请选择能力列表', trigger: 'change' }
                ]
            },
            aMyAppData: [],
            aUnitType: [],
        }
    },
    computed: {
        apitoken() {
            if (this.$store.state.oUserInfo.apiToken) {
                return this.$store.state.oUserInfo.apiToken;
            } else {
                return JSON.parse(localStorage.getItem('userInfo')).apiToken;
            }
        },
        userInfo() {
            if (this.$store.state.oUserInfo) {
                return this.$store.state.oUserInfo;
            } else {
                return JSON.parse(localStorage.getItem('userInfo'));
            }
        },
    },
    watch: {},
    created() {

    },
    mounted() {
        this.fRequestApplicationDept();// 请求所属部门
        this.fGetAllAppList();
        this.fGetUnitType();
    },
    beforeDestroy() {

    },
    methods: {
        // 请求字典 单位类型
        fGetUnitType() {
            this.$fRequest({
                url: this.$ip.server_ocn + 'system/dict/data/type/unit_type'
            }).then(res => {
                this.aUnitType = res.data || []
            })
        },
        // 请求已上架的应用
        fGetAllAppList() {
            this.$fRequest({
                method: 'post',
                url: this.$ip.server_ocn + 'cmApp/allAppList',
                data: {
                    appType: '',
                    appModule: '1669301968486559746',
                    source: '',
                    shangjiaTime: '', // 上架时间 
                }
            }).then(res => {
                this.aMyAppData = res.data;
            })
        },
        fClickExcel() {
            this.$fRequest({
                url: this.$ip.server_ocn + 'cmMoban/mobanUrl?type=1',
                // method:'get',
            }).then(res => {
                console.log(res)
                // this.aAppSource = res.data || []
                axios.get(this.$ip.server_ocn + '/common/download/resource?resource=' + res.data[0].upload[0].url, {
                    headers: {
                        'Authorization': JSON.parse(window.localStorage.getItem('userInfo')).apiToken,
                        'ApiToken': JSON.parse(window.localStorage.getItem('userInfo')).apiToken,
                    },
                    responseType: "blob"
                }).then(resource => {
                    this.downloadFile(resource.data, res.data[0].upload[0].originalFilename)
                })
            })
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
        // 文件上传成功
        fUploadFileV2(res, file, fileList) {
            // console.log(res,file,fileList)
            console.log(fileList)
            this.oAddForm.moban = []
            fileList.forEach(item => {
                this.oAddForm.moban.push({
                    path: item.response.fileName,
                    newFileName: item.response.newFileName,
                    name: item.response.newFileName,
                    url: item.response.url,
                    originalFilename: item.response.originalFilename,
                })
            })
        }, // 文件上传成功
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
        // 文件预览
        handlePreview(data) {
            axios.get('https://2.21.138.89:11511/common/download/resource?resource=' + data.response.url, {
                headers: {
                    'Authorization': JSON.parse(window.localStorage.getItem('userInfo')).apiToken,
                    'ApiToken': JSON.parse(window.localStorage.getItem('userInfo')).apiToken,
                },
                responseType: "blob"
            }).then(result => {
                console.log(result)
                this.downloadFile(result.data, data.response.newFileName)
            }).catch(err => {
                console.log(err)
            })
            return
        },

        handleRemoveV2(file, fileList) {
            // console.log(file,fileList);
            fileList.forEach(item => {
                this.oAddForm.moban.push({
                    path: item.response.fileName,
                    newFileName: item.response.newFileName,
                    name: item.response.newFileName,
                    url: item.response.url,
                    originalFilename: item.response.originalFilename,
                })
            })
        },
        // 所属部门下拉框切换
        fSsbmSelectChange(item) {
            if (this.oAddForm.danwei) {
                this.oAddForm.danwei = this.oAddForm.danwei[this.oAddForm.danwei.length - 1]
            }
        },

        // 请求所属部门
        fRequestApplicationDept() {
            this.$fRequest({
                url: URLS_APPAPPLY.u_addApplicationDept
            }).then(res => {
                // console.log(res, '请求所属部门');
                this.aSsbm = res.data;
            })
        },
        fChangeNengLiList(data) {
            console.log(data)
        },
        // 重置表单
        fResetFormNew() {
            this.$refs['oAddFormRules'].resetFields();
            this.oAddForm.moban = [];
            this.fileListV2 = [];
        },
        // 新增弹框确定按钮
        fOnSubmit() {
            this.$nextTick(() => {
                this.$refs['oAddFormRules'].validate((valid) => {
                    if (valid) {
                        let oData = {
                            ...this.oAddForm,
                            // id: 'id-' + window.performance.now().toString().replace('.', ''),
                            // moban: JSON.stringify(this.oAddForm.moban),
                            abilityList: this.oAddForm.abilityList.join(',')
                        }
                        if (this.oAddForm.moban && this.oAddForm.moban.length > 0) { } else {
                            this.$message.error('请上传模板材料!');
                            return
                        }
                        $.ajax({
                            url: 'https://2.21.138.89:11511/cmAbilitymoban/save',
                            method: 'post',
                            headers: {
                                'Authorization': JSON.parse(window.localStorage.getItem('userInfo')).apiToken,
                                'ApiToken': JSON.parse(window.localStorage.getItem('userInfo')).apiToken,
                                // "Content-Type": "application/json"
                            },
                            dataType: 'json',
                            contentType: 'application/json;charset=utf-8',
                            data: JSON.stringify({
                                ...oData
                            })
                        }).then(res => {
                            // console.log(res)
                            this.nCurrent = 1;
                            if (res.code == '200') {
                                this.$emit('fSetSqjlList', this.oAppApplyForm)
                                window.localStorage.setItem('nengliobj', JSON.stringify(this.oAppApplyForm))
                                this.$message({
                                    message: '恭喜你，提交成功',
                                    type: 'success'
                                });
                                this.fResetFormNew();
                                // this.fResetForm('oAddFormRules');// 重置清空表单
                            }
                        })
                    } else {
                        console.log('error submit!!');
                        this.bDialogVisible = false;
                        return false;
                    }
                });
            })
        },
        // 重置按钮
        fResetForm(formName) {
            this.oAppApplyForm.icon = '';
            this.oAppApplyForm.logo = '';
            this.$refs.iconId.clearFiles()
            this.$refs.logoId.clearFiles()
            this.$refs[formName].resetFields();
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
    background: url('../../assets/image/home/sjsqbg.png') no-repeat center / 100% 617px;
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

.box {
    padding-top: 0;

    &._flex {
        margin-bottom: 30px;
    }
}

.usercenter-banner {
    width: 100%;
    height: 617px;
    padding: 109px 0 0 0;
    margin-bottom: 50px;
    // margin-top: -65px;
    background: url('../../assets/image/home/bg.png') no-repeat center / 100% 617px;
}

.usercenter-banner-title {
    text-align: center;
    margin-bottom: 28px;
    color: #fff;
    font-weight: 500;
    font-size: 60px;
}

.usercenter-banner-detail {
    text-align: center;
    width: 50%;
    margin: 0 auto;
    font-size: 18px;
    text-indent: 2rem;
    color: #fff;
}


.box-block {
    width: 100%;

    &._bg {
        margin-bottom: 29px;
        margin-top: 100px;
        text-align: center;
        font-size: 36px;
        color: #1A1B1F;
        // background: url('../../assets/image/comments/tit_bg2.png') no-repeat center center;
    }

    &._wid {
        width: 80%;
        margin: 0 auto;
    }

    &._width {
        width: 80%;
        margin: 0 auto;
    }
}

.box-block-tit {
    // margin-top: -20px !important;
}

/deep/.el-step__icon-inner {
    font-size: 50px;
}

/deep/.el-step__icon {
    height: 50px;
    width: 50px;
    margin-top: -12px;
    color: #fff;
    background: linear-gradient(152deg, #5CA4FF 0%, #2F6BFF 100%);
}

/deep/.el-step__title {
    font-size: 25px;
}

/deep/.el-cascader {
    width: 100%;
}

.pic-wrap {
    width: 148px;
    height: 148px;
    background-color: #fbfdff;
    border: 1px dashed #c0ccda;
    box-sizing: border-box;
    border-radius: 0.3125vw;
    cursor: pointer;
    line-height: 148px;
    vertical-align: top;
}
/deep/ .el-form-item__label{
    font-size: unset;
}

/deep/.el-image__inner {
    width: 148px;
    height: 148px;
}

.btn-upload {
    width: 100px;
    height: 100px;
    font-size: 30px;
}

/deep/.el-upload {
    display: inline-flex;
    align-items: end;
}

/deep/.el-checkbox-group {
    display: flex;
    flex-direction: column;
}
/deep/.el-checkbox__label{
    font-size:  0.9rem;
}
/deep/ .el-radio__label{
   font-size: 0.9rem !important;
}
 
</style>