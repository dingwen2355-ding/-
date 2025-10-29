<template>
    <baseKanbanInfo :title="useType == 'add' ? '添加志愿活动' : ('志愿活动 / ' + (item.name || ''))">
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
                    <el-col :span="24">
                        <el-form-item label="活动名称" prop="name">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.name" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.name || '' }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="活动类型" prop="type">
                            <!-- 字典下拉框。data_volunteer_activity_type -->
                            <dirtVoluActiveType
                                ref="dirtVoluActiveType"
                                noLabel
                                useType="form"
                                allWidth="100%"
                                :disabled="baseDisable"
                                @change="(v) => { item.type = v['voluActiveType'] || '' }"
                            />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="所属区域" prop="regionCode">
                            <!-- 下拉选择框。取值于dept。type=2  -->
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
                        <el-form-item label="开始时间" prop="startTime">
                            <!-- 日期框 精确到时分秒 -->
                            <div v-if="!baseDisable" class="zr-100-el-wapper">
                                <el-date-picker
                                    v-model="item.startTime"
                                    size="small"
                                    type="datetime"
                                    value-format="yyyy-MM-dd HH:mm:ss"
                                    placeholder="选择开始时间"
                                />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.startTime }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="结束时间" prop="endTime">
                            <!-- 日期框 精确到时分秒 -->
                            <div v-if="!baseDisable" class="zr-100-el-wapper">
                                <el-date-picker
                                    v-model="item.endTime"
                                    size="small"
                                    type="datetime"
                                    value-format="yyyy-MM-dd HH:mm:ss"
                                    placeholder="选择结束时间"
                                />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.endTime }}
                            </div>
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
                        <el-form-item label="活动地址" prop="address">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.address" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.address || '' }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="活动详情" prop="details">
                            <!-- 富文本框 -->
                            <baseQuill v-model="item.details" :disable="baseDisable" />
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
                                    tableName="data_volunteer_activity"
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
                                    :limit="5"
                                    :srclist="item.attachment"
                                    :showAdd="!baseDisable"
                                    :showDel="!baseDisable"
                                    tableName="data_volunteer_activity"
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
import { infoVolunteerActivity, addVolunteerActivity, editVolunteerActivity } from '@/api/data/volunteerActivity'
// const LZString = require('lz-string')
export default {
    props: {
        useType: { type: String, default: '' }
    },
    data() {
        let getThis = () => { return this }
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
                name: [{ required: true, message: '请输入活动名称', trigger: ['blur', 'change'] }]
                // startTime: [
                //     { required: true, message: '请选择活动开始时间', trigger: ['blur', 'change'] },
                //     {
                //         // required: true,
                //         validator: (rule, value, callback) => {
                //             let _this = getThis()
                //             // 首先是必填
                //             if (!_this.item.startTime) return callback(new Error('请选择活动开始时间!'))
                //             if (!_this.item.endTime) return callback()
                //             if (_this.item.startTime <= _this.item.endTime) return callback()
                //             // 顺手调用一下结束的验证: 不能都写,会互相调用,仅作为示例
                //             // let fields = _this.$refs.ruleForm.fields
                //             // let v1 = fields.find(i => { return i.prop == 'endTime' })
                //             // v1.validate('', () => {})
                //             callback(new Error('开始时间不能大于结束时间!'))
                //         },
                //         trigger: ['blur', 'change']
                //     }
                // ],
                // endTime: [
                //     { required: true, message: '请选择活动结束时间', trigger: ['blur', 'change'] },
                //     {
                //         // required: true,
                //         validator: (rule, value, callback) => {
                //             let _this = getThis()
                //             // 首先是必填
                //             if (!_this.item.startTime) return callback()
                //             if (!_this.item.endTime) return callback(new Error('请选择活动结束时间!'))
                //             if (_this.item.startTime <= _this.item.endTime) return callback()
                //             let fields = _this.$refs.ruleForm.fields
                //             let v1 = fields.find(i => { return i.prop == 'startTime' })
                //             v1.validate('', () => {})
                //             callback(new Error('结束时间不能小于开始时间!'))
                //         },
                //         trigger: ['blur', 'change']
                //     }
                // ]
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
            infoVolunteerActivity(this.id)
            .then(data => {
                let v = data.data
                v.icon = v.icon ? (v.icon.split(',').filter(i => { return i })) : []
                v.icon = v.iconList && v.iconList.length > 0 && v.iconList || []
                v.attachment = v.attachmentList && (!v.attachment || v.attachment.length > 0) && v.attachmentList || []
                delete v['iconList']
                delete v['attachmentList']
                // 解压富文本
                // v.details = LZString.decompress(v.details)
                _this.item = v
                _this.$refs.regionId.setValWithChange(v.regionCode)
                _this.$refs.dirtVoluActiveType.setValWithChange(v.type)
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
            // 压缩富文本
            // upData.details = LZString.compress(upData.details)
            console.log(upData)
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    if (this.v_loadig) return
                    this.v_loadig = true
                    addVolunteerActivity(upData)
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
            // 压缩富文本
            // upData.details = LZString.compress(upData.details)
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    if (this.v_loadig) return
                    this.v_loadig = true
                    editVolunteerActivity(upData)
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
