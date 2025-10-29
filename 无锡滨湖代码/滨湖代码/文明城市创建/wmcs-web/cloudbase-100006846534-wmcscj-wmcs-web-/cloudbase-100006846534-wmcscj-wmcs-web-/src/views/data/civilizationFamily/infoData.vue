<template>
    <baseKanbanInfo :title="useType == 'add' ? '添加文明家庭' : ('文明家庭 / ' + (item.name || ''))">
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
                        <el-form-item label="家庭名称" prop="name">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.name" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.name || '' }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="联系电话" prop="recommendTel">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.recommendTel" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.recommendTel || '' }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="层级" prop="level">
                            <dirtCivilizationSelectLevel
                                ref="civilizationSelectLevel"
                                noLabel
                                useType="form"
                                allWidth="100%"
                                :disabled="baseDisable"
                                @change="(v) => { item.level = v['civilizationSelectLevel'] || '' }"
                            />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="评选季度" prop="selectDate">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.selectDate" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.selectDate || '' }}
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
                        <el-form-item label="详细地址" prop="address">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.address" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.address || '' }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="荣誉奖励" prop="selectReason">
                            <baseQuill v-model="item.honors" :disable="baseDisable" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="事迹描述" prop="selectDesc">
                            <!-- 富文本框 -->
                            <baseQuill v-model="item.selectDesc" :disable="baseDisable" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="是否显示" prop="showFlag">
                            <!-- 单选框。0显示；1不显示。默认为0 -->
                            <div v-if="!baseDisable">
                                <el-radio-group v-model="item.showFlag">
                                    <el-radio :label="0">显示</el-radio>
                                    <el-radio :label="1">不显示</el-radio>
                                </el-radio-group>
                            </div>
                            <div v-else class="td-form-val">
                                <span v-if="item.showFlag == 0">显示</span>
                                <span v-if="item.showFlag == 1">不显示</span>
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="是否置顶" prop="topFlag">
                            <!-- 单选框。0置顶；1不置顶。默认为1 -->
                            <div v-if="!baseDisable">
                                <el-radio-group v-model="item.topFlag">
                                    <el-radio :label="0">置顶</el-radio>
                                    <el-radio :label="1">不置顶</el-radio>
                                </el-radio-group>
                            </div>
                            <div v-else class="td-form-val">
                                <span v-if="item.topFlag == 0">置顶</span>
                                <span v-if="item.topFlag == 1">不置顶</span>
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
                    <el-col :span="24">
                        <el-form-item label="宣传图" prop="icon">
                            <!-- 图片上传 -->
                            <div class="w-100">
                                <baseUpFile
                                    ref="fileList"
                                    :limit="1"
                                    :srclist="item.icon"
                                    :showAdd="!baseDisable"
                                    :showDel="!baseDisable"
                                    onlyImg
                                    tableName="data_civilization_family"
                                    fieldName="icon"
                                />
                                <div v-if="!baseDisable" class="file-ps-text">* 可上传图片,最多上传5张图片,每张图片不超过10M</div>
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="附件" prop="attachment">
                            <div class="w-100">
                                <baseUpFile
                                    ref="attachmentList"
                                    :limit="10"
                                    :srclist="item.attachment"
                                    :showAdd="!baseDisable"
                                    :showDel="!baseDisable"
                                    tableName="data_civilization_family"
                                    fieldName="attachment"
                                />
                                <div v-if="!baseDisable" class="file-ps-text">* 可上传文件,最多上传5个文件,每个文件不超过10M</div>
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
import { infoCivilizationFamily, addCivilizationFamily, editCivilizationFamily } from '@/api/data/civilizationFamily'
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
                showFlag: 0,
                topFlag: 1,
                icon: [],
                attachment: []
            },
            rules: {
                name: [{ required: true, message: '请输入评选名称', trigger: ['blur', 'change'] }],
                level: [{ required: true, message: '请选择获评层级', trigger: ['blur', 'change'] }]
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
            infoCivilizationFamily(this.id)
            .then(data => {
                let v = data.data
                v.icon = v.icon ? (v.icon.split(',').filter(i => { return i })) : []
                v.icon = v.iconList && v.iconList.length > 0 && v.iconList || []
                v.attachment = v.attachmentList && (!v.attachment || v.attachment.length > 0) && v.attachmentList || []
                delete v['iconList']
                delete v['attachmentList']
                _this.item = v
                // 回显独立的组件
                _this.$refs.regionId.setValWithChange(v.regionCode)
                _this.$refs.civilizationSelectLevel.setValWithChange(v.level)
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
            upData.icon = upData.icon.map(i => { return i.id }).join(',')
            upData.attachment = upData.attachment.map(i => { return i.id }).join(',')
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    if (this.v_loadig) return
                    this.v_loadig = true
                    addCivilizationFamily(upData)
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
            upData.icon = upData.icon.map(i => { return i.id }).join(',')
            upData.attachment = upData.attachment.map(i => { return i.id }).join(',')
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    if (this.v_loadig) return
                    this.v_loadig = true
                    editCivilizationFamily(upData)
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
