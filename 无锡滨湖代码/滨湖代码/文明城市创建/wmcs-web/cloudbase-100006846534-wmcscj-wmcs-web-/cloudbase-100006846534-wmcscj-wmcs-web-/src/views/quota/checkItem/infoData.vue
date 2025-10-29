<template>
    <baseKanbanInfo :title="useType == 'add' ? '添加指标类型' : '指标类型'">
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
                        <el-form-item label="点位类型" prop="typeId">
                            <searchCheckPointsType
                                ref="checkPointsTypeId"
                                noLabel
                                useType="form"
                                allWidth="100%"
                                :disabled="baseDisable"
                                @change="(v) => { item.typeId = v['checkPointsTypeId'] || '' }"
                            />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="排序" prop="orderNum">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.orderNum" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.orderNum || '' }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="分数" prop="score">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.score" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.score || '' }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="检查内容" prop="name">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.name" type="textarea" :rows="3" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.name || '' }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="责任单位" prop="depts">
                            <div class="show-flex-r">
                                <div class="margin-right-10 td-form-val" style="width: fit-content;">
                                    <div v-if="item.depts && item.depts.length > 0">
                                        <t-tag
                                            v-for="(dept, index) in item.depts"
                                            :key="index"
                                            theme="primary"
                                            variant="light"
                                            :closable="!baseDisable"
                                            class="margin-right-5 margin-bottom-5"
                                            @close="delDept(index)"
                                        >{{ dept.name }}</t-tag>
                                    </div>
                                    <div v-else class="select-form show-flex-r d-c" style="height: 20px;">暂未设置</div>
                                </div>
                                <div v-if="!baseDisable" class="zr-link-p select-form show-flex-r d-c" style="width: 50px;height: 20px;" @click="diaSelectDept = true">添加</div>
                                <diaSelectDept
                                    v-if="diaSelectDept"
                                    :show="diaSelectDept"
                                    :selecFunc="selectDept"
                                    mode="many"
                                    @cancel="diaSelectDept = false"
                                />
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="事项类型" prop="eventTypeId">
                            <div class="show-flex-r">
                                <div class="margin-right-10 td-form-val" style="width: fit-content;">
                                    <div v-if="item.eventTypeId">
                                        <t-tag
                                            theme="primary"
                                            variant="light"
                                            :closable="!baseDisable"
                                            class="margin-right-5 margin-bottom-5"
                                            @close="delEventT()"
                                        >{{ item.eventTypeName }}</t-tag>
                                    </div>
                                    <div v-else class="select-form show-flex-r d-c" style="height: 20px;">暂未设置</div>
                                </div>
                                <div v-if="!baseDisable" class="zr-link-p select-form show-flex-r d-c" style="width: 50px;height: 20px;" @click="diaSelectEventT = true">选择</div>
                                <diaSelectEventT
                                    v-if="diaSelectEventT"
                                    :show="diaSelectEventT"
                                    :selecFunc="selectEventT"
                                    mode="single"
                                    @cancel="diaSelectEventT = false"
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
                <t-button ghost class="margin-left-20" @click="goBack()">取消</t-button>
            </div>
        </div>
    </baseKanbanInfo>
</template>
<script>
import { getPointsContentInfo, addPointsContent, editPointsContent } from '@/api/data/checkPointsType'
export default {
    props: {
        useType: { type: String, default: '' }
    },
    data() {
        return {
            id: 0,
            v_loadig: false,
            formLabelWidth: '130px',
            diaSelectDept: false,
            diaSelectEventT: false,
            item: {
                // ========================基本信息
                id: null,
                name: '',
                typeId: '',
                depts: '',
                deptIds: '',
                deptNames: '',
                eventTypeId: '',
                orderNum: '',
                remark: ''
            },
            rules: {
                name: [{ required: true, message: '请输入检查项', trigger: ['blur', 'change'] }]
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
            getPointsContentInfo(this.id)
            .then(data => {
                let v = data.data
                v.depts = v.deptList
                delete v.deptList
                _this.item = v
                _this.$refs.checkPointsTypeId.setValWithoutChange(v.typeId)
            })
            .finally(e => {
                _this.v_loadig = false
            })
        },
        selectDept(deptArr) {
            let _this = this
            deptArr.forEach(i => {
                if (!_this.item.depts.find(d => { return d.id == i.id })) {
                    _this.item.depts.push({
                        id: i.id, name: i.name
                    })
                }
            })
            return new Promise((res, rej) => { res() })
        },
        delDept(index) {
            let _this = this
            let item = this.item.depts[index]
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
                _this.item.depts.splice(index, 1)
            })
        },
        selectEventT(eArr) {
            // eArr.forEach(i => {
            //     if (!this.item.eventTypeIds.find(d => { return d.id == i.id })) {
            //         this.item.eventTypeIds.push({
            //             id: i.id, name: i.name
            //         })
            //     }
            // })
            if (eArr) {
                this.item.eventTypeId = eArr.id
                this.item.eventTypeName = eArr.name
            }
            return new Promise((res, rej) => { res() })
        },
        delEventT() {
            let _this = this
            // let item = this.item.eventTypeIds[index]
            new Promise((res, rej) => {
                _this.useDia = this.$dialog({
                    header: '系统提示',
                    body: `是否确认删除名称为"${_this.item.eventTypeName}"的数据项？`,
                    style: 'color: rgba(0, 0, 0, 0.6)',
                    onConfirm: ({ e }) => {
                        res()
                    }
                })
            })
            .then(d => {
                _this.useDia.hide()
                _this.item.eventTypeId = ''
                _this.item.eventTypeName = ''
            })
        },
        useTrim() {
            // 纯字符串去掉前后空格
            let arr = ['name']
            arr.forEach(k => {
                this.item[k] = this.item[k].trim()
            })
        },
        addNew() {
            let _this = this
            this.useTrim()
            let upData = JSON.parse(JSON.stringify(this.item))
            upData.deptIds = upData.depts.map(i => { return i.id }).join(',')
            upData.deptNames = upData.depts.map(i => { return i.name }).join(',')
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    if (this.v_loadig) return
                    this.v_loadig = true
                    addPointsContent(upData)
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
            upData.deptIds = upData.depts.map(i => { return i.id }).join(',')
            upData.deptNames = upData.depts.map(i => { return i.name }).join(',')
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    if (this.v_loadig) return
                    this.v_loadig = true
                    editPointsContent(upData)
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
