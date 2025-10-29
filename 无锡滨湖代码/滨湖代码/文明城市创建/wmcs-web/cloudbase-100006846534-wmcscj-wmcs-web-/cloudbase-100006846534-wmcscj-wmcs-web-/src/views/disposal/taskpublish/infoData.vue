<template>
    <baseKanbanInfo :title="useType == 'add' ? '添加知识库' : ('知识库 / ' + (item.name || ''))">
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
                    <el-col :span="24">
                        <el-form-item label="任务类型" prop="type">
                            <div v-if="!baseDisable">
                                <el-radio-group v-model="item.type">
                                    <el-radio :label="1">常规类型</el-radio>
                                    <el-radio :label="2">特殊类型</el-radio>
                                </el-radio-group>
                            </div>
                            <div v-else class="td-form-val">
                                <span v-if="item.type == 1">常规类型</span>
                                <span v-if="item.type == 2">特殊类型</span>
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="巡检状态" prop="status">
                            <dirtCheckTaskStatus
                                ref="taskStatusId"
                                noLabel
                                useType="form"
                                allWidth="100%"
                                :disabled="baseDisable"
                                @change="(v) => { item.status = v['taskStatusId'] || '' }"
                            />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="点位类型" prop="pointsTypeId">
                            <searchCheckPointsType
                                ref="pointsTypeId"
                                keyName="pointsTypeId"
                                noLabel
                                useType="form"
                                allWidth="100%"
                                :disabled="baseDisable"
                                @change="
                                v => {
                                    item.pointsTypeId = v['pointsTypeId'] || '';
                                }
                                "
                                @changeNext="changeCheckPointType"
                            />
                        </el-form-item>
                    </el-col>
                    <el-col v-show="item.type == 2" :span="12">
                        <el-form-item label="事发点位" prop="pointsId">
                            <selectCheckPoints
                                ref="pointsId"
                                keyName="pointsId"
                                noLabel
                                allWidth="100%"
                                :disabled="baseDisable"
                                @change="
                                v => {
                                    item.pointsId = v['pointsId'] || '';
                                }
                                "
                            />
                        </el-form-item>
                    </el-col>
                    <el-col v-show="item.type == 2" :span="12">
                        <el-form-item label="巡查项" prop="checkItemId">
                            <selectCheckItem
                                ref="checkItemId"
                                keyName="checkItemId"
                                noLabel
                                allWidth="100%"
                                :disabled="baseDisable"
                                @change="
                                v => {
                                    item.checkItemId = v['checkItemId'] || ''
                                }
                                "
                            />
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="巡查指标" prop="content">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.content" type="textarea" :rows="5" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.content || '' }}
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
import { InfoCheckTask, addCheckTask, editCheckTask } from '@/api/data/taskPublish'
export default {
    props: {
        useType: { type: String, default: '' }
    },
    data() {
        let getThis = () => { return this }
        return {
            id: 0,
            v_loadig: false,
            formLabelWidth: '130px',
            item: {
                // ========================基本信息
                id: null,
                type: 1,
                pointsTypeId: '',
                pointsId: '',
                checkItemId: '',
                content: '',
                status: 0
            },
            rules: {
                type: [{ required: true, message: '请选择任务类型', trigger: ['blur', 'change'] }],
                pointsTypeId: [{ required: true, message: '请选择点位类型', trigger: ['blur', 'change'] }],
                pointsId: [
                    { required: false },
                    {
                        validator: (rule, value, callback) => {
                            let _this = getThis()
                            if (_this.item.type == 2 && !_this.item.pointsId) return callback(new Error('请选择点位'))
                            return callback()
                        },
                        trigger: ['blur', 'change']
                    }
                ],
                checkItemId: [
                    { required: false },
                    {
                        validator: (rule, value, callback) => {
                            let _this = getThis()
                            if (_this.item.type == 2 && !_this.item.checkItemId) return callback(new Error('请选择检查项目'))
                            return callback()
                        },
                        trigger: ['blur', 'change']
                    }
                ]
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
        changeCheckPointType(data) {
            // 切换点位类型后做联动
            // 事发点位
            this.$refs.pointsId.setValWithChange("", data?.id || "");
            // 检查项目:选择自动触发
            this.$refs.checkItemId.setValWithChange("", data?.id || "");
        },
        getInfo() {
            let _this = this
            this.v_loadig = true
            // 获取详情接口
            InfoCheckTask(this.id)
            .then(data => {
                let item = data.data
                _this.item = item
                _this.$refs.pointsTypeId.setValWithoutChange(item.pointsTypeId)
                _this.$refs.pointsId.setValWithoutChange(item.pointsId, item.pointsTypeId)
                _this.$refs.checkItemId.setValWithoutChange(item.checkItemId, item.pointsTypeId)
                _this.$refs.taskStatusId.setValWithoutChange(item.status)
            })
            .finally(e => {
                _this.v_loadig = false
            })
        },
        useTrim() {
            // 纯字符串去掉前后空格
            let arr = ['content']
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
                    addCheckTask(upData)
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
                    editCheckTask(upData)
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
