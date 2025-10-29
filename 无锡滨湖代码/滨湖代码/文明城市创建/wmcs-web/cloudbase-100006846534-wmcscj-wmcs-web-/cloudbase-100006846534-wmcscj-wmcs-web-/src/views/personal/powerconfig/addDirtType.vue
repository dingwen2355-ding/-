<template>
    <el-dialog :title="useType == 'add' ? '新增字典类型' : item.dictName" :visible.sync="show" append-to-body width="800px" :before-close="cancel">
        <div class="dia-bd-hei per-add-new-account">
            <el-form ref="ruleForm" :model="item" :rules="rules" size="small">
                <el-form-item label="字典名称:" prop="dictName" :label-width="formLabelWidth">
                    <div style="width:500px">
                        <el-input v-model="item.dictName" placeholder="请填写字典名称" autocomplete="off" />
                    </div>
                </el-form-item>
                <el-form-item label="字典类型:" prop="dictType" :label-width="formLabelWidth">
                    <div style="width:500px">
                        <el-input v-model="item.dictType" placeholder="请填写字典类型" autocomplete="off" :disabled="useType != 'add'" />
                    </div>
                </el-form-item>
                <el-form-item label="状态:" prop="status" :label-width="formLabelWidth">
                    <div style="width:300px">
                        <el-switch
                            v-model="item.status"
                            :active-value="0"
                            :inactive-value="1"
                        />
                    </div>
                </el-form-item>
                <el-form-item label="备注:" prop="remark" :label-width="formLabelWidth">
                    <div style="width:500px">
                        <el-input
                            v-model="item.remark"
                            placeholder="请填写字典备注"
                            autocomplete="off"
                            type="textarea"
                        />
                    </div>
                </el-form-item>
            </el-form>
        </div>
        <div slot="footer" class="dialog-footer">
            <t-button ghost class="margin-right-20" @click="cancel">取消</t-button>
            <t-button theme="primary" @click="submitForm('ruleForm')">确定</t-button>
            <!-- <el-button size="mini" @click="cancel">取 消</el-button>
            <el-button size="mini" type="bi" @click="submitForm('ruleForm')">确 定</el-button> -->
        </div>
    </el-dialog>
</template>
<script>
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
            rules: {
                dictName: [
                    { required: true, message: '请输入字典名称', trigger: 'blur' }
                ],
                dictType: [
                    { required: true, message: '请输入字典类型', trigger: 'blur' }
                ]
            },
            item: {
                dictName: '',
                dictType: '',
                status: 0,
                remark: ''
            },
            formLabelWidth: '130px'
        }
    },
    methods: {
        cancel() {
            this.$emit('cancel')
        },
        initItem(item) {
            this.item = item
        },
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    this.$emit('submitForm', this.item)
                } else {
                    return false
                }
            })
        }
    }
}
</script>
