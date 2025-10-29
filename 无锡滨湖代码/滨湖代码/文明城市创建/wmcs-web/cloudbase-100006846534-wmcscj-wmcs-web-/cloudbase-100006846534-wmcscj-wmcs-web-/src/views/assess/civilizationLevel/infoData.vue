<template>
    <baseKanbanInfo :title="useType == 'add' ? '生成文明程度指数' : ('文明程度指数信息 / ' + (item.name || ''))">
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
                <div class="td-form-in-title" style="font-size: 14px;line-height: 30px;height: 30px;margin-left: 20px;">点击确认生成该月份的指数信息。</div>
                <el-row class="show-flex-r wrap" :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="所属月份" prop="month">
                            <div v-if="!baseDisable" class="zr-100-el-wapper">
                                <el-date-picker
                                    v-model="item.month"
                                    size="small"
                                    type="month"
                                    value-format="yyyy-MM-dd"
                                    placeholder="选择所属月份"
                                />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.month }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="类型" prop="type">
                            <div v-if="!baseDisable" class="zr-100-el-wapper">
                                <el-select v-model="item.type">
                                    <el-option label="区划部门" :value="2" />
                                    <el-option label="职能部门" :value="1" />
                                </el-select>
                            </div>
                            <div v-else class="td-form-val">
                                <span v-if="item.type == 2">区划部门</span>
                                <span v-if="item.type == 1">职能部门</span>
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
import { infoCivilizationLevel, addCivilizationLevel, editCivilizationLevel } from '@/api/assess/civilizationLevel'
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
                month: '',
                score: '',
                status: 0,
                description: ''
            },
            rules: {
                month: [{ required: true, message: '请选择所属月份', trigger: ['blur', 'change'] }],
                type: [{ required: true, message: '请选择类型', trigger: ['blur', 'change'] }]
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
            infoCivilizationLevel(this.id)
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
            let arr = ['month']
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
                    addCivilizationLevel(upData)
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
        }
    }
}
</script>
