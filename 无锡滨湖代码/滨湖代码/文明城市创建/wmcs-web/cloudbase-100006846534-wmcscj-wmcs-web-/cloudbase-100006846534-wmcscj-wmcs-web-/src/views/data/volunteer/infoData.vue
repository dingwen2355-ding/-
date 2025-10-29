<template>
    <baseKanbanInfo :title="useType == 'add' ? '添加志愿人员' : ('志愿人员 / ' + (item.name || ''))">
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
                        <el-form-item label="申请时间" prop="applyDate">
                            <!-- 日期框 -->
                            <div v-if="!baseDisable" class="zr-100-el-wapper">
                                <el-date-picker
                                    v-model="item.applyDate"
                                    size="small"
                                    type="date"
                                    value-format="yyyy-MM-dd"
                                    placeholder="选择评选时间"
                                />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.applyDate }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="街道/社区" prop="regionCode">
                            <searchRegionDept
                                ref="regionId"
                                noLabel
                                useType="form"
                                allWidth="100%"
                                :disabled="baseDisable"
                                @change="(v) => { item.regionCode = v['regionId'] || '' }"
                            />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="真实姓名" prop="name">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.name" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.name || '' }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="身份号码" prop="cardnumber">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.cardnumber" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.cardnumber }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="联系电话" prop="phone">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.phone" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.phone }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="邮箱" prop="email">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.email" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.email }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="性别" prop="sex">
                            <!-- 单选框。0未知；0女；1男 -->
                            <div v-if="!baseDisable">
                                <el-radio-group v-model="item.sex">
                                    <el-radio :label="0">未知</el-radio>
                                    <el-radio :label="1">男</el-radio>
                                    <el-radio :label="2">女</el-radio>
                                </el-radio-group>
                            </div>
                            <div v-else class="td-form-val">
                                <span v-if="item.sex == 0">未知</span>
                                <span v-if="item.sex == 1">男</span>
                                <span v-if="item.sex == 2">女</span>
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="年龄" prop="age">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.age" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.age }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="固话" prop="tel">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.tel" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.tel }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="邮编" prop="zipCode">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.zipCode" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.zipCode }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="学历" prop="education">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.education" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.education }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="职业" prop="position">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.position" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.position }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="单位名称" prop="corporateName">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.corporateName" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.corporateName }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="详细住址" prop="address">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.address" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.address }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="工作简介" prop="jobResume">
                            <!-- 多行文本域 -->
                            <div v-if="!baseDisable">
                                <el-input v-model="item.jobResume" type="textarea" :rows="3" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.jobResume || '' }}
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
import { infoVolunteer, addVolunteer, editVolunteer } from '@/api/data/volunteer'
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
                name: '',
                applyDate: '',
                regionCode: '',
                cardnumber: '',
                phone: '',
                email: '',
                sex: 0,
                age: '',
                tel: '',
                zipCode: '',
                education: '',
                position: '',
                corporateName: '',
                address: '',
                jobResume: '',
                remark: ''
            },
            rules: {
                name: [{ required: true, message: '请输入真实姓名', trigger: ['blur', 'change'] }],
                cardnumber: [{ required: true, message: '请输入身份号码', trigger: ['blur', 'change'] }],
                phone: [{ required: true, message: '请输入联系电话', trigger: ['blur', 'change'] }]
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
            this.v_loadig = true
            // 获取详情接口
            infoVolunteer(this.id)
            .then(data => {
                let v = data.data
                _this.item = v
                _this.$refs.regionId.setValWithChange(v.regionCode)
            })
            .finally(e => {
                _this.v_loadig = false
            })
        },
        useTrim() {
            // 纯字符串去掉前后空格
            let arr = ['name', 'cardnumber', 'phone', 'email']
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
                    addVolunteer(upData)
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
                    editVolunteer(upData)
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
