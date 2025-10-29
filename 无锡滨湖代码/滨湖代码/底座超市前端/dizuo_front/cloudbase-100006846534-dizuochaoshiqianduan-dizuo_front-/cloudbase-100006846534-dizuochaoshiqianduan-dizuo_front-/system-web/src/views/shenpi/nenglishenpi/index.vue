<template>
    <div class="flex-col page">

        <!-- 筛选条件 -->
        <div class="flex-row header">
            <el-form class="elform" :inline="true" :model="queryParams">
                <el-form-item label="联系人">
                    <el-input v-model="queryParams.principalName" clearable></el-input>
                </el-form-item>
                <el-form-item label="联系人电话">
                    <el-input v-model="queryParams.principalPhone" clearable></el-input>
                </el-form-item>
                <el-form-item label="申请单位">
                    <el-cascader v-model="queryParams.danwei" clearable filterable :options="aSsbm"
                        @change="fSsbmSelectChange" :props="{ value: 'id', label: 'title' }"
                        placeholder="请选择所属部门"></el-cascader>
                </el-form-item>
                <el-form-item label="单位类型">
                    <!-- <el-input v-model="queryParams.userPhone" clearable></el-input> -->
                    <el-select v-model="queryParams.danweiType" clearable placeholder="请选择单位类型">
                        <el-option v-for="item in dict.type.unit_type" :key="item.value" :label="item.label"
                            :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label-width="0">
                    <el-button type="primary" size="small" @click="getList">
                        <i class="el-icon-search"></i>查询
                    </el-button>
                    <!-- <el-button size="small" @click="fResect">
                        重置
                    </el-button> -->
                </el-form-item>
            </el-form>
        </div>
        <!-- 列表 + 分页 -->
        <div class="flex-col main">
            <el-table class="eltable" row-key="id" height="0" border v-loading="loading" :data="tableList">
                <!-- <el-table-column prop="userPhone" label="应用名称" align="center"></el-table-column> -->
                <el-table-column prop="principalName" label="联系人" align="center"></el-table-column>
                <el-table-column prop="principalPhone" label="联系人电话" align="center"></el-table-column>
                <el-table-column prop="danwei" label="申请单位" align="center">
                    <template slot-scope="{row}">
                        {{ fSetSsbm(row.danwei) }}
                    </template>
                </el-table-column>
                <el-table-column prop="danweiType" label="单位类型" align="center">
                    <template slot-scope="{row}">
                        {{ fSetLabel(dict.type.unit_type, row.danweiType, 'value', 'label') }}
                    </template>
                </el-table-column>

                <el-table-column prop="createTime" label="申请时间" align="center"></el-table-column>
                <el-table-column prop="reason" label="审核意见" align="center"></el-table-column>
                <el-table-column prop="status" label="审核状态" align="center" width="120px">
                    <template slot-scope="{row}">
                        <span :class="['status-item', '_bg' + row.status]"> {{ fSetZhuangtaiNengli(row.status) }}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="userPhone" label="操作" align="center">
                    <template slot-scope="{row}">
                        <el-button type="primary" v-if="row.status == 1" size="small" @click="openLayer(row)">
                            审核
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>

            <div class="empty"></div>

            <el-pagination class="elpagination" layout="total, sizes, prev, pager, next, jumper" :total="total"
                :page-sizes="[5, 10, 20, 30, 50]" :page-size="queryParams.size" :current-page="queryParams.current"
                @current-change="handleCurrentChange" @size-change="handleSizeChange" />
        </div>
        <el-dialog :title="layerTitle" :visible.sync="bDialogDetails" width="50%" :before-close="fCloseLayer">
            <el-form ref="oAddFormRules" :rules="oAddFormRules" :model="oAddForm" label-width="100px">
                <div class="block">
                    <div class="block-title">申请信息</div>
                    <el-row>
                        <el-col :span="24">
                            <el-form-item label="能力列表">
                                <template v-if="fSetNengLiList(oDetails.abilityList).length > 0">
                                    <div class="" v-for="(item, idx) in fSetNengLiList(oDetails.abilityList)" :key="idx">
                                        {{ item }}
                                    </div>
                                </template>
                                <template v-else>--</template>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="联系人">
                                {{ oDetails.principalName }}
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="联系人电话">
                                {{ oDetails.principalPhone }}
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="申请单位">
                                {{ fSetSsbm(oDetails.danwei) }}
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="单位类型">
                                {{ fSetLabel(dict.type.unit_type, oDetails.danweiType, 'value', 'label') }}
                            </el-form-item>
                        </el-col>
                        <el-col :span="24">
                            <el-form-item label="申请理由">
                                {{ oDetails.applicationReason }}
                            </el-form-item>
                        </el-col>
                        <el-col :span="24">
                            <el-form-item label="申请表">
                                <el-button @click="fDonLoad(oDetails.moban)">下载</el-button>
                               <p v-for="(item,index) in oDetails.moban" :key="index">
                                    {{ item.originalFilename }}
                               </p>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </div>

                <div class="block">
                    <div class="block-title">审核</div>
                    <el-row>
                        <el-col :span="12">
                            <el-form-item label="审核人" prop="shenheren">
                                <el-input v-model="oAddForm.shenheren" placeholder="请输入审核人"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="审核状态" prop="examineType">
                                <el-radio-group v-model="oAddForm.examineType">
                                    <el-radio :label="0">通过</el-radio>
                                    <el-radio :label="2">驳回</el-radio>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>
                        <el-col :span="24">
                            <el-form-item label="审核时间" prop="shenheTime">
                                <el-input v-model="oAddForm.shenheTime" disabled></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="24">
                            <el-form-item id="oAddFormUpload" label="上传附件">
                                <el-upload class="upload-demo" :action="sUploadUrl"
                                    :headers="{ 'Authorization': 'Bearer ' + token }" :on-preview="handlePreview"
                                    :on-success="fUploadFile" :on-remove="handleRemove" :multiple="false"
                                    :file-list="fileList">
                                    <el-button size="small" type="primary">点击上传</el-button>
                                </el-upload>
                            </el-form-item>
                        </el-col>
                        <el-col :span="24">
                            <el-form-item label="审核意见" prop="reason">
                                <el-input type="textarea" :rows="3" placeholder="不超过200字" :max="200"
                                    v-model="oAddForm.reason"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </div>
                <el-form-item>
                    <el-button type="primary" @click="fOnSubmit">确 认</el-button>
                    <el-button @click="fCloseLayer">取 消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
import {
    cmAppList,
    getAllAbility,//查询所有审批的应用
    downloadResource,
    cmAbilityExamineSave,
} from "@/api/management/management";
import { addApplicationDept } from "@/api/cas/cas";
import { getToken } from '@/utils/auth'

export default {
    name: '',
    dicts: ['app_module', 'sys_user_sex', 'app_source', 'unit_type'],
    components: {},
    props: [''],
    data() {
        return {
            token: getToken(),
            sUploadUrl: 'https://2.21.138.89:11511/system_server/common/upload',
            fileList: [],
            aAllAppList: [],
            queryParams: {
                current: 1,
                size: 10,
                "principalName": "",
                "principalPhone": "",
                "danwei": "",
                "danweiType": ""
            },
            tableList: [],
            loading: false,
            aAllDept: [],
            aSsbm: [],// 所属部门
            total: 0,
            layerTitle: '能力申请审核',
            bDialogDetails: false,
            oAddForm: {
                shenheren: '', // 审核人
                examineType: '',// 审核状态
                shenheTime: this.fSetDate(),// 审核时间
                upload: '',
                reason: '',
            },
            oAddFormRules: {
                shenheren: [
                    { required: true, message: '请输入审核人', trigger: 'blur' }
                ],
                examineType: [
                    { required: true, message: '请选择审核状态', trigger: 'change' }
                ],
                shenheTime: [
                    { required: true, message: '请选择审核时间', trigger: 'change' }
                ],
                reason: [
                    { required: true, message: '请输入审核意见', trigger: 'blur' }
                ],
            },
            oDetails: {},
            moban: null
        }
    },
    computed: {},
    watch: {},
    created() {

    },
    mounted() {
        // console.log(this.dict.type.unit_type)
        this.getList();
        this.getAllList();
        this.fGetDeptName();
        console.log(this.$store.getters.name)
        this.oAddForm.shenheren = this.$store.getters.name;
    },
    beforeDestroy() {

    },
    methods: {
        // 查询列表
        async getList() {
            this.loading = true
            let res = await getAllAbility(this.queryParams)
            this.tableList = res.data.records
            this.total = res.data.total
            this.loading = false
            // console.log(res)
        },
        async getAllList() {
            let res = await cmAppList({
                pageNum: 1,// 页码
                pageSize: 999999,// 每页的数量
            });
            console.log(res, '所有的应用')
            this.aAllAppList = res.data;
        },
        // 弹框确认按钮
        fOnSubmit() {
            this.$nextTick(() => {
                this.$refs['oAddFormRules'].validate((valid) => {
                    if (valid) {
                        if (this.oAddForm.upload && this.oAddForm.upload.length > 0) { } else {
                            this.$message.error('请上传材料!');
                            return
                        }
                        let oData = {
                            ...this.oAddForm,
                            cmAbilityId: this.oDetails.id
                            // upload: JSON.stringify(this.oAddForm.upload)
                        }


                        cmAbilityExamineSave({ ...oData }).then(res => {
                            // console.log(res)
                            this.nCurrent = 1;
                            this.getList();
                            this.$message({
                                message: '审核成功',
                                type: 'success'
                            });
                            this.oAddForm.upload = []
                            this.fileList = []
                            this.bDialogDetails = false;
                            this.sId = '';
                        })
                    } else {
                        console.log('error submit!!');
                        // this.bDialogVisible = false;
                        return false;
                    }
                });
            })
        },
        fSetDate() {
            var currentDate = new Date();
            var year = currentDate.getFullYear();
            var month = ("0" + (currentDate.getMonth() + 1)).slice(-2);
            var day = ("0" + currentDate.getDate()).slice(-2);
            var hours = ("0" + currentDate.getHours()).slice(-2);
            var minutes = ("0" + currentDate.getMinutes()).slice(-2);
            var seconds = ("0" + currentDate.getSeconds()).slice(-2);
            return year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
        },
        // 弹框关闭前
        fCloseLayer() {
            console.log('弹框关闭前')
            this.fileList = [];
            this.bDialogDetails = false;
        },
        // 附件信息删除
        handleRemove(file, fileList) {
            // console.log(file,fileList);
            fileList.forEach(item => {
                this.oAddForm.upload.push({
                    path: item.response.fileName,
                    newFileName: item.response.newFileName,
                    name: item.response.newFileName,
                    url: item.response.url,
                    originalFilename: item.response.originalFilename,
                })
            })
        },
        // 文件上传成功
        fUploadFile(res, file, fileList) {
            // console.log(res,file,fileList)
            this.oAddForm.upload = []
            fileList.forEach(item => {
                this.oAddForm.upload.push({
                    path: item.response.fileName,
                    newFileName: item.response.newFileName,
                    name: item.response.newFileName,
                    url: item.response.url,
                    originalFilename: item.response.originalFilename,
                })
            })
        },
        // 文件预览
        handlePreview(data) {
            downloadResource({ resource: data.response.url }).then(res => {
                this.downloadFile(res, data.response.newFileName)
            })
        },
        fSetNengLiList(ids) {
            // console.log(ids)
            if (ids) {
                let text = []
                ids.split(',').forEach(id => {
                    // console.log(id)

                    if(id.indexOf("1")>-1){
                     this.aAllAppList.forEach(data => {
                        if (id == data?.casAppId) {
                            text.push(data.appName)
                        }
                    })
                    }else{
                        text.push(id)
                        // console.log("00000")
                    }


                })
                return text;
            } else {
                return []
            }
        },
        fDonLoad(arr) {
            // console.log(arr, this.oDetails)
            // console.log(this.moban)
            if (arr && arr.length > 0) {
                downloadResource({ resource: arr[0].url }).then(res => {
                    this.downloadFile(res, arr[0].newFileName)
                })
            }
        },
        downloadFile(blob, fileName) {
            let blobUrl = window.URL.createObjectURL(new Blob([blob], { type: 'application/force-download' }))
            let a = document.createElement('a')
            a.style.display = 'none'
            a.setAttribute("href", blobUrl)
            a.setAttribute('download', `${fileName}`);
            document.body.appendChild(a)
            a.click() //执行下载
            window.URL.revokeObjectURL(blobUrl)
            document.body.removeChild(a)
        },
        // 所属部门下拉框切换
        fSsbmSelectChange(item) {
            if (this.queryParams.dept) {
                this.queryParams.dept = this.queryParams.dept[this.queryParams.dept.length - 1]
            }

        },
        openLayer(data) {
            this.oDetails = this.tableList.filter(item => item.id == data.id)[0]
            this.moban = JSON.parse(JSON.stringify(this.oDetails)).moban;
            this.oDetails = JSON.parse(JSON.stringify(this.oDetails))
            // this.oDetails = data;
            this.bDialogDetails = true;
            this.layerTitle = '能力申请查看'
        },
        fGetDeptName() {
            addApplicationDept().then(res => {
                this.aAllDept = []
                this.aSsbm = res.data;
                this.findNode(res.data)
            })
        },
        findNode(arr) {
            arr.forEach(item => {
                this.aAllDept.push(item)
                item.children && item.children.length > 0 ? this.findNode(item.children) : ""
                item.children = ''
            })
        },
        fSetSsbm(id, arr) {
            let aData = this.aAllDept.filter(item => item.id == id)
            // console.log(aData)
            return aData.length > 0 ? aData[0].title : '--'
        },
        fSetLabel(data, value, key, label) {
            if (value) {
                return data.filter(item => item[key] == value)[0][label] || '--'
            } else {
                return '--'
            }
        },
        fSetZhuangtaiNengli(type) {
            if (type == 0) {
                return '上架';
            } else if (type == 1) {
                return '待审核';
            } else if (type == 2) {
                return '驳回';
            } else if (type == 3) {
                return '下架';
            } else if (type == 4) {
                return '撤销';
            } else {
                return '--';
            }
        },
        // 搜索条件重置
        fResect() { },
        handleCurrentChange(current) {
            this.queryParams = {
                ...this.queryParams,
                current
            }
            this.getList()
        },
        handleSizeChange(size) {
            this.queryParams = {
                ...this.queryParams,
                size
            }
            this.getList()
        }
    }
}
</script>
<style lang='scss' scoped>
.flex-row {
    display: flex;
}

.flex-col {
    display: flex;
    flex-direction: column;
}

.page {
    box-sizing: border-box;
    padding: 20px;
    height: calc(100vh - 84px);
    // calc(100vh - 84px)

    .header {
        justify-content: space-between;
        align-items: center;
        width: 100%;
        margin-bottom: 10px;
    }

    .main {
        flex: 1;
        overflow: hidden;
    }

    .empty {
        height: 1px;
        margin-bottom: 35px;
    }

    ::v-deep .elpagination {
        bottom: 5px;
    }
}

.elform,
.eltable {
    flex: 1;
}

.status-item {
    display: inline-block;
    position: relative;
    font-size: 15px;
    color: #000;


}

.status-item::before {
    position: absolute;
    left: -15px;
    top: 6px;
    content: '';
    width: 10px;
    height: 10px;
}

._bg0::before {
    background: #3bd11d;
}

._bg1::before {
    background: #0d8ade;
}

._bg2::before {
    background: #dea026;
}

._bg3::before {
    background: #de4545;
}

._bg4::before {
    background: #848484;
}

.block {
    padding: 0 20px;
    margin-bottom: 20px;
    // box-shadow: 0px 10px 15px #bdbdbd;
}

.block-title {
    color: #000;
    font-weight: bold;
    font-size: 24px;
    text-indent: 2rem;
    margin-bottom: 20px;
}

::v-deep .el-pagination {
  display: flex;
  align-items: center;
  justify-content: end;
  margin-top: 20px;
}
/*#oAddFormUpload::before{
  content: "*";
  color: #ff4949;
  margin-right: 4px
}*/
</style>
