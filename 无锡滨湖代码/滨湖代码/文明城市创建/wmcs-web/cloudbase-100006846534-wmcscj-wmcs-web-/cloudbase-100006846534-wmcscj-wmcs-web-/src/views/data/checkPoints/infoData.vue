<template>
    <baseKanbanInfo :title="useType == 'add' ? '添加督察点位' : ('督察点位 / ' + (item.name || ''))">
        <div slot="headbtn" class="show-flex-r a-c padding-left-20 padding-right-20">
            <div class="show-flex-r a-c">
                <!-- <el-button type="warning" size="mini" @click="goBack()">取消</el-button> -->
            </div>
        </div>
        <div slot="body" v-loading="v_loadig" class="td-form-content-wap">
            <el-form ref="ruleForm" :model="item" :rules="rules" class="td-form-content-wap" size="small"
                label-position="top">
                <div class="td-form-in-title">基本信息</div>
                <el-row class="show-flex-r wrap" :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="点位名称" prop="name">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.name" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.name || '' }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="所属区划" prop="regionCode">
                            <!-- 下拉选择框。取值于dept。type=2 -->
                            <searchRegionDept ref="regionId" noLabel useType="form" allWidth="100%" :disabled="baseDisable"
                                @change="(v) => { item.regionCode = v['regionId'] || '' }" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="点位类型" prop="typeId">
                            <searchCheckPointsType ref="checkPointsTypeId" noLabel useType="form" allWidth="100%"
                                :disabled="baseDisable" @change="(v) => { item.typeId = v['checkPointsTypeId'] || '' }" />
                        </el-form-item>
                    </el-col>
                    <!-- <el-col :span="24">
                        <el-form-item label="关联摄像头" prop="devices">
                            <div class="show-flex-r">
                                <div class="margin-right-10 td-form-val" style="width: fit-content;">
                                    <div v-if="item.devices && item.devices.length > 0">
                                        <t-tag v-for="(device, index) in item.devices" :key="index" theme="primary"
                                            variant="light" :closable="!baseDisable" class="margin-right-5 margin-bottom-5"
                                            @close="delDevice(index)">{{ device.name }}</t-tag>
                                    </div>
                                    <div v-else class="select-form show-flex-r d-c" style="height: 20px;">暂未设置</div>
                                </div>
                                <div v-if="!baseDisable" class="zr-link-p select-form show-flex-r d-c"
                                    style="width: 50px;height: 20px;" @click="diaSelectDevice = true">添加</div>
                                <diaSelectDevice v-if="diaSelectDevice" :show="diaSelectDevice" :selecFunc="selectDevice"
                                    mode="many" @cancel="diaSelectDevice = false" />
                            </div>
                        </el-form-item>
                    </el-col> -->
                    <el-col :span="12">
                        <el-form-item label="经度">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.longitude" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.longitude }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="纬度">
                            <div v-if="!baseDisable">
                                <el-input v-model="item.latitude" />
                            </div>
                            <div v-else class="td-form-val">
                                {{ item.latitude }}
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <minemap style="height: 300px;" :disabled="baseDisable" :lng.sync="item.longitude" :lat.sync="item.latitude"></minemap>
                    </el-col>
                    <el-col :span="24">
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
                    <!-- <el-col :span="24">
                        <el-form-item label="附件" prop="fileList">
                            <div class="w-100">
                                <baseUpFile
                                    ref="fileList"
                                    :limit="5"
                                    :srclist="item.fileList"
                                    :showAdd="!baseDisable"
                                    :showDel="!baseDisable"
                                    tableName="data_check_points"
                                    fieldName="attachment"
                                    :useToken="true"
                                />
                                <div v-if="!baseDisable" class="file-ps-text">* 可上传文件,最多上传5个文件,每个文件不超过10M</div>
                            </div>
                        </el-form-item>
                    </el-col> -->
                </el-row>
            </el-form>
            <div class="td-subform-wapper">
                <t-button v-if="useType == 'add'" @click="addNew()">确定</t-button>
                <t-button v-if="useType == 'edit'" @click="editItem()">确定</t-button>
                <t-button v-if="useType == 'add' || useType == 'edit'" ghost class="margin-left-20"
                    @click="goBack()">取消</t-button>
            </div>
        </div>
    </baseKanbanInfo>
</template>
<script>
import { infoCheckPoints, addCheckPoints, editCheckPoints } from '@/api/data/checkPoints'
import minemap from '@/components/minemap/minemap.vue'
export default {
    props: {
        useType: { type: String, default: '' }
    },
    components: { minemap },
    data() {
        return {
            id: 0,
            v_loadig: false,
            formLabelWidth: '130px',
            diaSelectDevice: false,
            item: {
                // ========================基本信息
                id: null,
                name: '',
                regionCode: '',
                typeId: '',
                longitude: '',
                latitude: '',
                address: '',
                devices: [],
                remark: '',
                fileList: []
            },
            rules: {
                name: [{ required: true, message: '请输入点位名称', trigger: ['blur', 'change'] }],
                typeId: [{ required: true, message: '请选择所属类型', trigger: ['blur', 'change'] }]
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
            infoCheckPoints(this.id)
                .then(data => {
                    let v = data.data
                    v.devices = v.deviceList
                    delete v.devideList
                    _this.item = v
                    // 回显
                    _this.$refs.regionId.setValWithChange(v.regionCode)
                    _this.$refs.checkPointsTypeId.setValWithChange(v.typeId)
                })
                .finally(e => {
                    _this.v_loadig = false
                })
        },
        selectDevice(deviceArr) {
            deviceArr.forEach(i => {
                if (!this.item.devices.find(d => { return d.id == i.id })) {
                    this.item.devices.push({
                        id: i.id, name: i.name
                    })
                }
            })
            return new Promise((res, rej) => { res() })
        },
        delDevice(index) {
            let _this = this
            let item = this.item.devices[index]
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
                    _this.item.devices.splice(index, 1)
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
            upData.devices = upData.devices.map(i => { return i.id })
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    if (this.v_loadig) return
                    this.v_loadig = true
                    addCheckPoints(upData)
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
            upData.devices = upData.devices.map(i => { return i.id })
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    if (this.v_loadig) return
                    this.v_loadig = true
                    editCheckPoints(upData)
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
