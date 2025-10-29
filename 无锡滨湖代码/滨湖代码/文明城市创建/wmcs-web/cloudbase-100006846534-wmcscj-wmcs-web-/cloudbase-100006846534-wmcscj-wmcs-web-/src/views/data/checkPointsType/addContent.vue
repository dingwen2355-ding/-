<template>
    <el-dialog :title="useType == 'add' ? '添加检查内容' : '检查内容'" :visible.sync="show" append-to-body width="700px" :before-close="cancel">
        <div v-loading="upLoading" class="dia-bd-hei per-add-new-account">
            <el-form
                ref="ruleForm"
                :model="item"
                :rules="rules"
                class="td-form-content-wap"
                size="small"
                label-position="top"
            >
                <!-- <div class="td-form-in-title">基本信息</div> -->
                <el-row class="show-flex-r wrap" :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="检查项内容" prop="name">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.name" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.name || '' }}
                            </div>
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
                        <el-form-item label="责任单位" prop="deptId">
                            <!-- 多选框。取值于dept。全部type都显示 -->
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
        </div>
        <div slot="footer" class="dialog-footer">
            <t-button ghost @click="cancel()">取消</t-button>
            <t-button v-if="useType == 'add'" class="margin-left-20" @click="addNew()">确定</t-button>
            <t-button v-if="useType == 'edit'" class="margin-left-20" @click="editItem()">确定</t-button>
        </div>
    </el-dialog>
</template>
<script>
import { addPointsContent, editPointsContent } from '@/api/data/checkPointsType'
export default {
    props: {
        show: {
            type: Boolean,
            default: false
        },
        useType: {
            type: String,
            default: 'add'
        }
    },
    data() {
        return {
            upLoading: false,
            item: {
                id: null,
                name: '',
                typeId: '',
                deptId: '',
                orderNum: '',
                remark: ''
            },
            rules: {
                name: [{ required: true, message: '请输入检查项', trigger: ['blur', 'change'] }]
            },
            formLabelWidth: '130px',
            show_dia_employee_select: false
        }
    },
    computed: {
        baseDisable() {
            // 详情禁用
            return !!(this.useType == 'info')
        }
    },
    methods: {
        cancel() { this.$emit('cancel') },
        addInitItem(item) {
            this.item.typeId = item.typeId
        },
        initItem(item) {
            let useitem = JSON.parse(JSON.stringify(item))
            this.item = useitem
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
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    if (this.v_loadig) return
                    this.v_loadig = true
                    addPointsContent(upData)
                    .then(d => {
                        _this.$successMessage('操作成功!')
                        _this.cancel()
                        _this.$emit('finish')
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
                    editPointsContent(upData)
                    .then(d => {
                        _this.$successMessage('操作成功!')
                        _this.cancel()
                        _this.$emit('finish')
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
