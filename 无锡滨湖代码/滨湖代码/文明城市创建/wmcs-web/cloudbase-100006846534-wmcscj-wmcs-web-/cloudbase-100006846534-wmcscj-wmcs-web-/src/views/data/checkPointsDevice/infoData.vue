<template>
    <baseKanbanInfo :title="useType == 'add' ? '添加AI监控视频' : ('AI监控视频 / ' + (item.name || ''))">
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
                        <el-form-item label="点位类型" prop="checkPointsTypeId">
                            <searchCheckPointsType
                            ref="checkPointsTypeId"
                            keyName="checkPointsTypeId"
                            noLabel
                            useType="form"
                            allWidth="100%"
                            :disabled="baseDisable"
                            @change="
                                v => {
                                item.checkPointsTypeId = v['checkPointsTypeId'] || '';
                                }
                            "
                            @changeNext="changeCheckPointType"
                            />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="事发点位" prop="pointsId">
                            <selectCheckPoints
                            ref="checkPointsId"
                            noLabel
                            allWidth="100%"
                            :disabled="baseDisable"
                            @change="
                                v => {
                                item.pointsId = v['checkPointsId'] || '';
                                }
                            "
                            />
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="关联监控设备" prop="channelId">
                            <!-- 多选择框。取值于device -->
                            <div class="show-flex-r">
                                <div class="margin-right-10 td-form-val" style="width: fit-content;">
                                    <div v-if="item.channelId">
                                        <t-tag
                                            theme="primary"
                                            variant="light"
                                            :closable="!baseDisable"
                                            class="margin-right-5 margin-bottom-5"
                                            @close="delDevice()"
                                        >{{ item.channelName }}</t-tag>
                                    </div>
                                    <div v-else class="select-form show-flex-r d-c" style="height: 20px;">暂未设置</div>
                                </div>
                                <div v-if="!baseDisable" class="zr-link-p select-form show-flex-r d-c" style="width: 50px;height: 20px;" @click="diaSelectDevice = true">添加</div>
                                <diaSelectDevice
                                    v-if="diaSelectDevice"
                                    :show="diaSelectDevice"
                                    :selecFunc="selectDevice"
                                    mode="single"
                                    @cancel="diaSelectDevice = false"
                                />
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
import { saveAI } from '@/api/data/checkPointsDevice'
export default {
    props: {
        useType: { type: String, default: '' }
    },
    data() {
        return {
            id: 0,
            v_loadig: false,
            formLabelWidth: '130px',
            diaSelectDevice: false,
            item: {
                id: null,
                pointsId: '',
                channelId: '',
                channelName: ''
            },
            rules: {
                pointsId: [{ required: true, message: '请选择点位', trigger: ['blur', 'change'] }],
                channelId: [{ required: true, message: '请选择监控设备', trigger: ['blur', 'change'] }]
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
            this.v_loadig = true
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
            // 获取详情接口
            infoCivilizationAdvanced(this.id + '')
            .then(data => {
                let item = data.data
                item.icon = item.icon ? (item.icon.split(',').filter(i => { return i })) : []
                item.icon = item.iconList && item.iconList.length > 0 && item.iconList || []
                item.attachment = item.attachmentList && (!item.attachment || item.attachment.length) > 0 && item.attachmentList || []
                delete item['iconList']
                delete item['attachmentList']
                _this.item = item
                // 回显独立的组件
                _this.$refs.civilizationAdvancedType.setValWithChange(item.selectType)
                _this.$refs.regionId.setValWithChange(item.regionCode)
                _this.$refs.recommendTypeId.setValWithChange(item.recommendType)
                _this.$refs.civilizationSelectLevel.setValWithChange(item.level)
            })
            .finally(e => {
                _this.v_loadig = false
            })
        },
        addNew() {
            let _this = this
            let upData = JSON.parse(JSON.stringify(this.item))
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    if (this.v_loadig) return
                    this.v_loadig = true
                    saveAI(upData)
                    .then(d => {
                        _this.$successMessage('操作成功!')
                        _this.refushBack()
                    })
                    .finally(e => {
                        _this.v_loadig = false
                    })
                } else {
                    alert(1)
                    return false
                }
            })
        },
        changeCheckPointType(data) {
            // 切换点位类型后做联动
            // 事发点位
            this.$refs.checkPointsId.setValWithChange("", data?.id || "");
        },
        selectDevice(deviceArr) {
            if (deviceArr) {
                this.item.channelId = deviceArr.id
                this.item.channelName = deviceArr.name
            }
            return new Promise((res, rej) => { res() })
        },
        delDevice() {
            let _this = this
            new Promise((res, rej) => {
                _this.useDia = this.$dialog({
                    header: '系统提示',
                    body: `是否确认删除名称为"${_this.item.channelName}"的数据项？`,
                    style: 'color: rgba(0, 0, 0, 0.6)',
                    onConfirm: ({ e }) => {
                        res()
                    }
                })
            })
            .then(d => {
                _this.useDia.hide()
                _this.item.channelId = ''
                _this.item.channelName = ''
            })
        }
    }
}
</script>
