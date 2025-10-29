<!-- 物联网服务器配置 -->
<template>
    <baseKanban title="物联网服务器配置管理" noTitle class="ios-server">
        <baseSearchForm ref="searchTable" noPage>
            <div slot="table">
                <div class="form-wapper">
                    <div v-loading="vLoading" class="app-item">
                        <div class="title" style="background: #3c8dbc;">IOT服务器配置</div>
                        <div class="kanban-wapper">
                            <el-form ref="mqtt" :model="iotServer" :rules="rules" label-width="100px">
                                <el-form-item label="MQTT地址" prop="url">
                                    <el-input v-model="iotServer.url" />
                                </el-form-item>
                                <el-form-item label="MQTTTopic" prop="topic">
                                    <el-input v-model="iotServer.topic" />
                                </el-form-item>
                                <el-form-item label="MQTT账号" prop="account">
                                    <el-input v-model="iotServer.account" />
                                </el-form-item>
                                <el-form-item label="MQTT秘钥" prop="secret">
                                    <el-input v-model="iotServer.secret" />
                                </el-form-item>
                                <div class="btn-wapper">
                                    <el-button type="primary" @click="iotServerPush">提交</el-button>
                                </div>
                            </el-form>
                        </div>
                    </div>
                </div>
            </div>
        </baseSearchForm>
    </baseKanban>
</template>
<script>
// import { getIOTServerInfo } from '@/api/iot'
export default {
    data() {
        return {
            vLoading: false,
            iotServer: {
                url: '',
                topic: '',
                account: '',
                secret: ''
            },
            rules: {
                url: [
                    { required: true, message: '请输入服务器地址', trigger: 'blur' }
                ],
                topic: [
                    { required: true, message: '请输入topic', trigger: 'blur' }
                ],
                account: [
                    { required: true, message: '请输入验证账号', trigger: 'blur' }
                ],
                secret: [
                    { required: true, message: '请输入验证秘钥', trigger: 'blur' }
                ]
            }
        }
    },
    mounted() {
        this.getIotServeInfo()
    },
    methods: {
        getIotServeInfo() {
            // let _this = this
            // getIOTServerInfo()
            // .then(d => {
            //     _this.iotServer = d
            // })
        },
        iotServerPush() {}
    }
}
</script>
<style lang="scss" scoped>
.ios-server{
    .form-wapper{
        width: 100%;
        display: flex;
        flex-direction: row;
        padding-top:20px;
        .app-item{
            flex: 1;
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
