<template>
    <div class="app-version">
        <baseKanban title="APP版本提交" noTitle nopadding>
            <div class="form-wapper">
                <div v-loading="androidLoading" class="app-item">
                    <div class="title" style="background: #409EFF;">Android</div>
                    <div class="kanban-wapper">
                        <el-form ref="android" :model="nextAndroid" :rules="androidRules" label-width="100px">
                            <!-- <el-form-item label="APPID">
                                <el-input v-model="android.appId" disabled />
                            </el-form-item> -->
                            <el-form-item label="当前版本号">
                                <div class="form-page-info-val">{{ nowAndroid.version || '暂无' }}</div>
                            </el-form-item>
                            <el-form-item label="下载地址">
                                <div class="form-page-info-val">{{ nowAndroid.url || '暂无' }}</div>
                            </el-form-item>
                            <el-form-item label="发布版本号" prop="version">
                                <el-input v-model="nextAndroid.version" />
                            </el-form-item>
                            <el-form-item label="下载地址" prop="url">
                                <el-input v-model="nextAndroid.url" />
                            </el-form-item>
                            <div class="btn-wapper">
                                <el-button type="primary" @click="pushAndroidVersion()">提交</el-button>
                            </div>
                        </el-form>
                    </div>
                </div>
                <div v-loading="iosLoading" class="app-item">
                    <div class="title" style="background: #909399;">IOS</div>
                    <div class="kanban-wapper">
                        <el-form ref="ios" :model="ios" :rules="iosRules" label-width="100px">
                            <!-- <el-form-item label="APPID">
                                <el-input v-model="ios.appId" disabled />
                            </el-form-item> -->
                            <el-form-item label="当前版本号">
                                <div class="form-page-info-val">{{ ios.nowVersion || '暂无' }}</div>
                            </el-form-item>
                            <el-form-item label="下载地址">
                                <div class="form-page-info-val">{{ ios.nowUrl || '暂无' }}</div>
                            </el-form-item>
                            <el-form-item label="发布版本号" prop="version">
                                <el-input v-model="ios.version" disabled />
                            </el-form-item>
                            <el-form-item label="下载地址" prop="url">
                                <el-input v-model="ios.url" disabled />
                            </el-form-item>
                            <div class="btn-wapper">
                                <el-button type="info">提交</el-button>
                            </div>
                        </el-form>
                    </div>
                </div>
            </div>
        </baseKanban>
    </div>
</template>

<script>
import { getAppPubInfo, publishAppVersion } from '@/api/basic'
export default {
    data() {
        return {
            androidLoading: false,
            nowAndroid: {
                plat: 'android',
                url: '',
                version: ''
            },
            nextAndroid: {
                plat: 'android',
                url: '',
                version: ''
            },
            iosLoading: false,
            ios: {
                plat: 'ios',
                url: '',
                version: ''
            },
            androidRules: {
                version: [
                    { required: true, message: '请输入版本号', trigger: 'blur' }
                ],
                url: [
                    { required: true, message: '请输入地址', trigger: 'blur' }
                ]
            },
            iosRules: {
                version: [
                    { required: true, message: '请输入版本号', trigger: 'blur' }
                ],
                url: [
                    { required: true, message: '请输入地址', trigger: 'blur' }
                ]
            }
        }
    },
    mounted() {
        this.getNowVersionInfo()
    },
    methods: {
        getNowVersionInfo() {
            let _this = this
            this.androidLoading = true
            this.iosLoading = true
            getAppPubInfo()
            .then(d => {
                if (d.data) {
                    _this.nowAndroid.version = d.data.version
                    _this.nowAndroid.url = d.data.url
                }
                _this.nextAndroid.version = ''
                _this.nextAndroid.url = ''
            })
            .finally(e => {
                _this.androidLoading = false
                _this.iosLoading = false
            })
        },
        pushAndroidVersion() {
            let _this = this
            this.$refs.android.validate((valid) => {
                if (valid) {
                    _this.androidLoading = true
                    publishAppVersion(this.nextAndroid)
                    .then(d => {
                        _this.$message({
                            message: '更新成功',
                            type: 'success'
                        })
                        _this.getNowVersionInfo()
                    })
                    .finally(e => {
                        _this.androidLoading = false
                    })
                } else {
                    return false
                }
            })
        }
    }
}
</script>

<style lang="scss" scoped>
.app-version{
    .form-wapper{
        width: 100%;
        display: flex;
        flex-direction: row;
        padding-top:20px;
        .app-item{
            flex: 1;
            max-width: 50%;
            display: flex;
            flex-direction: column;
            margin: 0 20px;
            .title{
                width: 100%;
                text-align: center;
                // background-color: #42b19f;
                color: #fff;
                height: 40px;
                display: flex;
                justify-content: center;
                align-items: center;
            }
            .kanban-wapper{
                padding: 20px;
                border: 1px solid #EBEEF5;
                border-top: 0px;
                .btn-wapper{
                    display: flex;
                    justify-content: center;
                    align-items: center;
                }
            }
        }
    }
}
</style>
