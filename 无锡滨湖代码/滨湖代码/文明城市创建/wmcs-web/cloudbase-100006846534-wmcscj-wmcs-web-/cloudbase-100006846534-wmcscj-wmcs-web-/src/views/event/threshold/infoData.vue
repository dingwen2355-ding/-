<template>
    <baseKanbanInfo :title="useType == 'add' ? '添加阈值信息' : ('阈值信息 / ' + (item.name || ''))">
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
                        <el-form-item label="AI告警类型" prop="alarmType">
                            <dirtWorkAlarmType
                                ref="alarmTypeId"
                                noLabel
                                useType="form"
                                allWidth="100%"
                                :disabled="baseDisable"
                                @change="(v) => { item.alarmType = v['alarmTypeId'] || '' }"
                            />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="对应工单类别" prop="eventType">
                            <selectEventType
                                ref="eventType"
                                keyName="eventType"
                                noLabel
                                allWidth="100%"
                                :disabled="baseDisable"
                                filterType="all"
                                @change="
                                    v => {
                                    item.eventTypeOne = v['eventType'][0] || '';
                                    item.eventTypeTwo = v['eventType'][1] || '';
                                    item.eventType = v['eventType'][2] || '';
                                    }
                                "
                            />
                        </el-form-item>   
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="平时告警次数" prop="normalOverCount">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.normalOverCount" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.normalOverCount || '' }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="检测类型" prop="type">
                            <div v-if="!baseDisable">
                                <el-radio-group v-model="item.type">
                                    <el-radio :label="1">每分钟</el-radio>
                                    <el-radio :label="2">每小时</el-radio>
                                    <el-radio :label="3">每天</el-radio>
                                </el-radio-group>
                            </div>
                            <div v-else class="td-form-val">
                                <span v-if="item.type == 1">每分钟</span>
                                <span v-if="item.type == 2">每小时</span>
                                <span v-if="item.type == 3">每天</span>
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="是否开始战时模式" prop="specialFlag">
                            <div v-if="!baseDisable">
                                <el-radio-group v-model="item.specialFlag">
                                    <el-radio :label="1">是</el-radio>
                                    <el-radio :label="0">否</el-radio>
                                </el-radio-group>
                            </div>
                            <div v-else class="td-form-val">
                                <span v-if="item.specialFlag == 1">是</span>
                                <span v-if="item.specialFlag == 0">否</span>
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="战时模式告警次数" prop="overCount">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.overCount" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.overCount || '' }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="选择时间段" prop="timeConfigId">
                            <searchTimeconfig
                                ref="timeconfigId"
                                noLabel
                                useType="form"
                                allWidth="100%"
                                :disabled="baseDisable"
                                @change="(v) => { item.timeConfigId = v['timeconfigId'] || '' }"
                            />
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="备注" prop="remark">
                            <!-- 多行文本域  -->
                            <div v-if="!baseDisable">
                                <el-input v-model="item.remark" type="textarea" :rows="3" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.remark || '' }}
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
import { addThreshold, editThreshold, infoThreshold } from '@/api/work/threshold'
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
                alarmType: '',
                type: 2,
                overCount: '',
                timeConfigId: '',
                remark: '',
                specialFlag: 0
            },
            rules: {
                alarmType: [{ required: true, message: '请选择告警类型', trigger: ['blur', 'change'] }],
                normalOverCount: [{ required: true, message: '请输入次数', trigger: ['blur', 'change'] }],
                eventType: [{ required: true, message: '请选择工单类别', trigger: ['blur', 'change'] }],
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
            infoThreshold(this.id)
            .then(data => {
                let item = data.data
                _this.item = item
                // 回显独立的组件
                _this.$refs.alarmTypeId.setValWithChange(item.alarmType || "");
                _this.$refs.timeconfigId.setValWithChange(item.timeConfigId)
                _this.$refs.eventType.setValWithChange(item.eventType)
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
                    addThreshold(upData)
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
            let upData = JSON.parse(JSON.stringify(this.item))
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    if (this.v_loadig) return
                    this.v_loadig = true
                    editThreshold(upData)
                    .then(d => {
                        _this.$successMessage('操作成功!')
                        _this.$emit('changeUseType', 'info')
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
