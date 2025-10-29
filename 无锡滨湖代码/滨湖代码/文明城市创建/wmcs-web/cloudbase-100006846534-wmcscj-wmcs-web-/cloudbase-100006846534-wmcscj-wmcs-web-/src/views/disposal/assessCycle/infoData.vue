<template>
    <baseKanbanInfo :title="useType == 'add' ? '添加考核周期' : ('考核周期 / ' + (item.name || ''))">
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
                        <el-form-item label="考核期数" prop="name">
                            <div v-if="!baseDisable">
                                第{{ item.number || '' }}期
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.name || '' }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="输入期数" prop="number">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.number" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.number || '' }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="开始时间" prop="beginTime">
                            <!-- 日期框 精确到时分秒 -->
                            <div v-if="!baseDisable" class="zr-100-el-wapper">
                                <el-date-picker
                                    v-model="item.beginTime"
                                    size="small"
                                    type="datetime"
                                    value-format="yyyy-MM-dd HH:mm:ss"
                                    placeholder="选择开始时间"
                                />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.beginTime }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="结束时间" prop="finishTime">
                            <!-- 日期框 精确到时分秒 -->
                            <div v-if="!baseDisable" class="zr-100-el-wapper">
                                <el-date-picker
                                    v-model="item.finishTime"
                                    size="small"
                                    type="datetime"
                                    value-format="yyyy-MM-dd HH:mm:ss"
                                    placeholder="选择结束时间"
                                />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.finishTime }}
                            </div>
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
import { infoAssessCycle, addAssessCycle, editAssessCycle, getLastNumber } from '@/api/workflow/assessCycle'
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
                number: '',
                name: '',
                type: 1,
                beginTime: '',
                finishTime: '',
                remark: ''
            },
            rules: {
                number: [{ required: true, message: '请输入考核期数', trigger: ['blur', 'change'] }],
                beginTime: [
                    { required: true, message: '请选择开始时间', trigger: ['blur', 'change'] },
                    {
                        // required: true,
                        validator: (rule, value, callback) => {
                            let _this = getThis()
                            // 首先是必填
                            if (!_this.item.beginTime) return callback(new Error('请选择开始时间!'))
                            if (!_this.item.finishTime) return callback()
                            if (_this.item.beginTime <= _this.item.finishTime) return callback()
                            callback(new Error('开始时间不能大于结束时间!'))
                        },
                        trigger: ['blur', 'change']
                    }
                ],
                finishTime: [
                    { required: true, message: '请选择结束时间', trigger: ['blur', 'change'] },
                    {
                        // required: true,
                        validator: (rule, value, callback) => {
                            let _this = getThis()
                            // 首先是必填
                            if (!_this.item.beginTime) return callback()
                            if (!_this.item.finishTime) return callback(new Error('请选择结束时间!'))
                            if (_this.item.beginTime <= _this.item.finishTime) return callback()
                            let fields = _this.$refs.ruleForm.fields
                            let v1 = fields.find(i => { return i.prop == 'beginTime' })
                            v1.validate('', () => {})
                            callback(new Error('结束时间不能小于开始时间!'))
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
        this.getNumber()
    },
    methods: {
        goBack() { this.$router.back(-1) },
        refushBack() {
            this.$router.push({ path: './list?_list_refush=true' })
        },
        getNumber() {
            let _this = this
            getLastNumber()
            .then(data => {
                console.log(data.data)
                _this.item.number = data.data + 1
            })
            .finally(e => {
                _this.v_loadig = false
            })
        },
        getInfo() {
            let _this = this
            // 获取详情接口
            infoAssessCycle(this.id)
            .then(data => {
                let item = data.data
                _this.item = item
            })
            .finally(e => {
                _this.v_loadig = false
            })
        },
        useTrim() {
            // 纯字符串去掉前后空格
            let arr = []
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
                    addAssessCycle(upData)
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
                    editAssessCycle(upData)
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
