<template>
    <baseKanbanInfo :title="useType == 'add' ? '添加街道' : ('街道 / ' + (item.regionName || ''))">
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
                        <el-form-item label="街道名称" prop="regionName">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.regionName" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.regionName || '' }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="街道编码" prop="regionCode">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.regionCode" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.regionCode || '' }}
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
                        <el-form-item label="上级街道" prop="parentId">
                            <searchRegion
                                ref="regionId"
                                noLabel
                                useType="form"
                                allWidth="100%"
                                :disabled="baseDisable"
                                @change="(v) => { item.parentId = v['regionId'] || '' }"
                            />
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
import { infoRegion, addRegion, editRegion } from '@/api/system/region'
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
                regionName: '',
                regionCode: '',
                orderNum: 0
            },
            rules: {
                regionName: [{ required: true, message: '请输入街道名称', trigger: ['blur', 'change'] }],
                regionCode: [{ required: true, message: '请输入街道编码', trigger: ['blur', 'change'] }]
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
                this.$refs.regionId.setValWithChange(nowParent)
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
            infoRegion(this.id)
            .then(data => {
                let v = data.data
                _this.item = v
                // 回显
                if (v.parentId && v.parentId !== '0') {
                    _this.$refs.regionId.setValWithChange(v.parentId)
                }
            })
            .finally(e => {
                _this.v_loadig = false
            })
        },
        useTrim() {
            // 纯字符串去掉前后空格
            let arr = ['regionName', 'regionCode']
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
                    addRegion(upData)
                    .then(d => {
                        _this.$successMessage('操作成功!')
                        // 刷新缓存数据
                        _this.$store.commit('addSearchFilter', 'getRegionList')
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
                    editRegion(upData)
                    .then(d => {
                        _this.$successMessage('操作成功!')
                        // 刷新缓存数据
                        _this.$store.commit('addSearchFilter', 'getRegionList')
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
