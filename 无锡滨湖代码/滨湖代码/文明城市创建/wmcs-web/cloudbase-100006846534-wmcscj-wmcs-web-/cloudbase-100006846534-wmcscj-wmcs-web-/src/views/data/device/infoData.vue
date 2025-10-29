<template>
    <baseKanbanInfo :title="useType == 'add' ? '添加视频设备' : ('视频设备 / ' + (item.name || ''))">
        <div slot="headbtn" class="show-flex-r a-c padding-left-20 padding-right-20">
            <div class="show-flex-r a-c">
                <!-- <el-button type="warning" size="mini" @click="goBack()">取消</el-button> -->
            </div>
        </div>
        <div slot="body" v-loading="v_loadig" class="td-form-content-wap">
            <el-form
                ref="ruleForm"
                :model="item"
                :rules="rules"
                class="td-form-content-wap"
                size="small"
                label-position="top"
            >
                <div class="td-form-in-title">基本信息</div>
                <el-row class="show-flex-r wrap" :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="设备标识" prop="code">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.code" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.code || '' }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="组织名称" prop="organizeName">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.organizeName" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.organizeName }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="设备名称" prop="name">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.name" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.name }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="通道名称" prop="channelName">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.channelName" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.channelName }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="通道状态" prop="channelStatus">
                            <div v-if="!baseDisable">
                                <el-radio-group v-model="item.channelStatus">
                                    <el-radio :label="0">正常</el-radio>
                                    <el-radio :label="1">异常</el-radio>
                                </el-radio-group>
                            </div>
                            <div v-else class="td-form-val">
                                <span v-if="item.channelStatus == 0">正常</span>
                                <span v-if="item.channelStatus == 1" style="color: #f78989;">异常</span>
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="通道类型" prop="channelType">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.channelType" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.channelType }}
                            </div>
                        </el-form-item>
                    </el-col>
                </el-row>
                <div class="td-form-in-title">位置信息</div>
                <el-row class="show-flex-r wrap" :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="经度">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.longitude" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.longitude }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="纬度">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.latitude" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.latitude }}
                            </div>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <div class="td-subform-wapper">
                <t-button v-if="useType == 'add'" @click="addNew()">确定</t-button>
                <t-button v-if="useType == 'edit'" @click="editItem()">确定</t-button>
                <t-button v-if="useType == 'add' || useType =='edit'" ghost class="margin-left-20" @click="goBack()">取消</t-button>
            </div>
        </div>
    </baseKanbanInfo>
</template>
<script>
import { infoDevice, addDevice, editDevice } from '@/api/device'
export default {
    props: {
        useType: { type: String, default: '' }
    },
    data() {
        return {
            id: 0,
            v_loadig: false,
            formLabelWidth: '130px',
            item: {
                // ========================基本信息
                id: null,
                code: '',
                organizeName: '',
                name: '',
                channelName: '',
                channelType: '',
                channelStatus: 0,
                longitude: '',
                latitude: ''
            },
            rules: {
                code: [{ required: true, message: '请输入设备标识', trigger: ['blur', 'change'] }],
                organizeName: [{ required: true, message: '请输入组织名称', trigger: ['blur', 'change'] }],
                name: [{ required: true, message: '请输入设备名称', trigger: ['blur', 'change'] }],
                channelName: [{ required: true, message: '请输入通道名称', trigger: ['blur', 'change'] }],
                channelType: [{ required: true, message: '请输入通道类型', trigger: ['blur', 'change'] }]
            }
        }
    },
    computed: {
        baseDisable() {
            // 详情禁用
            return !!(this.useType == 'info')
        }
    },
    mounted() {
        if (this.useType != 'add') {
            this.id = this.$route.query.id
            this.getInfo()
        }
    },
    methods: {
        goBack() { this.$router.back(-1) },
        refushBack() {
            this.$router.push({ path: './list?_list_refush=true' })
        },
        getInfo() {
            let _this = this
            this.v_loadig = true
            // 获取详情接口
            infoDevice(this.id)
            .then(data => {
                let v = data.data
                _this.item = v
            })
            .finally(e => {
                _this.v_loadig = false
            })
        },
        useTrim() {
            // 纯字符串去掉前后空格
            let arr = ['name', 'code']
            arr.forEach(k => {
                this.item[k] = this.item[k].trim()
            })
        },
        addNew() {
            let _this = this
            this.useTrim()
            let upData = JSON.parse(JSON.stringify(this.item))
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    if (this.v_loadig) return
                    this.v_loadig = true
                    addDevice(upData)
                    .then(d => {
                        _this.$successMessage('操作成功!')
                        _this.refushBack()
                    })
                    .finally(e => {
                        _this.v_loadig = false
                    })
                } else {
                    return false
                }
            })
        },
        editItem() {
            let _this = this
            this.useTrim()
            let upData = JSON.parse(JSON.stringify(this.item))
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    if (this.v_loadig) return
                    this.v_loadig = true
                    editDevice(upData)
                    .then(d => {
                        _this.$successMessage('操作成功!')
                        // _this.$emit('changeUseType', 'info')
                        _this.goBack()
                    })
                    .finally(e => {
                        _this.v_loadig = false
                    })
                } else {
                    return false
                }
            })
        }
    }
}
</script>
