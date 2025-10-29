<template>
    <baseKanbanInfo :title="useType == 'add' ? '添加指标类型' : ('指标类型 / ' + (item.name || ''))">
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
                        <el-form-item label="指标名称" prop="name">
                            <div v-if="!baseDisable" >
                                <el-input v-model="item.name"  />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.name || '' }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="启用状态" prop="status">
                            <div v-if="!baseDisable">
                                <el-radio-group v-model="item.status">
                                    <el-radio :label="0">启用</el-radio>
                                    <el-radio :label="1">不启用</el-radio>
                                </el-radio-group>
                            </div>
                            <div v-else class="td-form-val">
                                <span v-if="item.status == 0">启用</span>
                                <span v-if="item.status == 1" style="color: #f78989;">不启用</span>
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="类型" prop="type">
                            <dirtEventSource
                                ref="eventSourceId"
                                noLabel
                                useType="form"
                                allWidth="100%"
                                :disabled="baseDisable"
                                @change="(v) => { item.type = v['eventSourceId'] || '' }"
                            />
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="指标描述" prop="description">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.description" type="textarea" :rows="3" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.description || '' }}
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
import { infoIndicatorType, addIndicatorType, editIndicatorType } from '@/api/quota/indicatorType'
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
                type: '',
                description: ''
            },
            rules: {
                name: [{ required: true, message: '请输入指标名称', trigger: ['blur', 'change'] }]
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
            infoIndicatorType(this.id)
            .then(data => {
                let item = data.data
                _this.item = item
                _this.$refs.eventSourceId.setValWithChange(item.type)
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
                    addIndicatorType(upData)
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
                    editIndicatorType(upData)
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
