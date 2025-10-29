<template>
    <baseKanbanInfo :title="useType == 'add' ? '添加单位' : ('单位 / ' + (item.name || ''))">
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
                        <el-form-item label="单位名称" prop="name">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.name" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.name || '' }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="上级单位" prop="parentId">
                            <searchDept
                                ref="parentId"
                                keyName="parentId"
                                noLabel
                                useType="form"
                                allWidth="100%"
                                filterType="all"
                                :disabled="baseDisable"
                                @change="(v) => { item.parentId = v['parentId'] || '' }"
                            />
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
                    <!-- <el-col :span="12">
                        <el-form-item label="联系人" prop="leader">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.leader" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.leader || '' }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="联系电话" prop="phone">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.phone" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.phone || '' }}
                            </div>
                        </el-form-item>
                    </el-col> -->
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
import { infoDept, addDept, editDept } from '@/api/system/dept'
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
                parentId: '0',
                orderNum: 0,
                type: 2,
                leader: '',
                phone: ''
            },
            rules: {
                name: [{ required: true, message: '请输入部门名称', trigger: ['blur', 'change'] }]
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
        } else {
            // add时特别处理一下
            let nowParent = this.$route.query.parentId
            if (nowParent) {
                this.$refs.parentId.setValWithChange(nowParent)
            }
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
            infoDept(this.id)
            .then(data => {
                let v = data.data
                _this.item = v
                // 回显
                if (v.parentId && v.parentId !== '0') {
                    _this.$refs.parentId.setValWithChange(v.parentId)
                }
            })
            .finally(e => {
                _this.v_loadig = false
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
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    if (this.v_loadig) return
                    this.v_loadig = true
                    addDept(upData)
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
                    editDept(upData)
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
