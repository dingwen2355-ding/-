<template>
    <baseKanbanInfo :title="useType == 'add' ? '添加督察人员' : ('督察人员 / ' + (item.name || ''))">
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
                        <el-form-item label="姓名" prop="name">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.name" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.name || '' }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <!-- <el-col :span="12">
                        <el-form-item label="身份号码" prop="cardnumber">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.cardnumber" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.cardnumber }}
                            </div>
                        </el-form-item>
                    </el-col> -->
                    <el-col :span="12">
                        <el-form-item label="手机号" prop="phone">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.phone" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.phone }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="负责督查类型" prop="type">
                            <!-- 字典下拉框。data_checkUser_type -->
                            <dirtCheckUserType
                                ref="checkUserType"
                                noLabel
                                useType="form"
                                allWidth="100%"
                                :disabled="baseDisable"
                                @change="(v) => { item.type = v['checkUserType'] || '' }"
                            />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="状态" prop="status">
                            <!-- 单选框。0正常；1停用 -->
                            <div v-if="!baseDisable">
                                <el-radio-group v-model="item.status">
                                    <el-radio :label="0">正常</el-radio>
                                    <el-radio :label="1">停用</el-radio>
                                </el-radio-group>
                            </div>
                            <div v-else class="td-form-val">
                                <span v-if="item.status == 0">正常</span>
                                <span v-if="item.status == 1" style="color: #f78989;">停用</span>
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
                        <el-form-item label="所属部门" prop="deptId">
                            <searchDept
                                ref="deptId"
                                noLabel
                                useType="form"
                                allWidth="100%"
                                :disabled="baseDisable"
                                filterType="all"
                                @change="(v) => { item.deptId = v['deptId'] || '' }"
                            />
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="住址" prop="address">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.address" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.address }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="负责点位" prop="points">
                            <!-- 多选择框。取值于 checkPoints -->
                            <div class="show-flex-r">
                                <div class="margin-right-10 td-form-val" style="width: fit-content;">
                                    <div v-if="item.points && item.points.length > 0">
                                        <t-tag
                                            v-for="(point, index) in item.points"
                                            :key="index"
                                            theme="primary"
                                            variant="light"
                                            :closable="!baseDisable"
                                            class="margin-right-5 margin-bottom-5"
                                            @close="delPoint(index)"
                                        >{{ point.name }}</t-tag>
                                    </div>
                                    <div v-else class="select-form show-flex-r d-c" style="height: 20px;">暂未设置</div>
                                </div>
                                <div v-if="!baseDisable" class="zr-link-p select-form show-flex-r d-c" style="width: 50px;height: 20px;" @click="diaSelectPoint = true">添加</div>
                                <diaSelectPoint
                                    v-if="diaSelectPoint"
                                    :show="diaSelectPoint"
                                    :selecFunc="selectPoint"
                                    mode="many"
                                    @cancel="diaSelectPoint = false"
                                />
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="备注" prop="remark">
                            <!-- 多行文本域 -->
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
import { infoCheckUser, addCheckUser, editCheckUser } from '@/api/data/checkUser'
export default {
    props: {
        useType: { type: String, default: '' }
    },
    data() {
        return {
            id: 0,
            v_loadig: false,
            formLabelWidth: '130px',
            diaSelectPoint: false,
            item: {
                // ========================基本信息
                id: null,
                name: '',
                cardnumber: '',
                phone: '',
                type: '',
                status: 0,
                address: '',
                points: [],
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
            // 获取详情接口
            infoCheckUser(this.id)
            .then(data => {
                let v = data.data
                v.points = v.pointsList
                delete v.pointsList
                _this.item = v
                // 回显
                _this.$refs.checkUserType.setValWithChange(v.type)
                _this.$refs.regionId.setValWithoutChange(v.regionCode)
                _this.$refs.deptId.setValWithoutChange(v.deptId)
            })
            .finally(e => {
                _this.v_loadig = false
            })
        },
        selectPoint(pointArr) {
            pointArr.forEach(i => {
                if (!this.item.points.find(d => { return d.id == i.id })) {
                    this.item.points.push({
                        id: i.id, name: i.name
                    })
                }
            })
            return new Promise((res, rej) => { res() })
        },
        delPoint(index) {
            let _this = this
            let item = this.item.points[index]
            new Promise((res, rej) => {
                _this.useDia = this.$dialog({
                    header: '系统提示',
                    body: `是否确认删除名称为"${item.name}"的数据项？`,
                    style: 'color: rgba(0, 0, 0, 0.6)',
                    onConfirm: ({ e }) => {
                        res()
                    }
                })
            })
            .then(d => {
                _this.useDia.hide()
                _this.item.points.splice(index, 1)
            })
        },
        useTrim() {
            // 纯字符串去掉前后空格
            let arr = ['name', 'cardnumber', 'phone']
            arr.forEach(k => {
                this.item[k] = this.item[k].trim()
            })
        },
        addNew() {
            let _this = this
            this.useTrim()
            let upData = JSON.parse(JSON.stringify(this.item))
            upData.points = upData.points.map(i => { return i.id })
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    if (this.v_loadig) return
                    this.v_loadig = true
                    addCheckUser(upData)
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
            if(upData.points != null) {
                upData.points = upData.points.map(i => { return i.id })
            }
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    if (this.v_loadig) return
                    this.v_loadig = true
                    editCheckUser(upData)
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
