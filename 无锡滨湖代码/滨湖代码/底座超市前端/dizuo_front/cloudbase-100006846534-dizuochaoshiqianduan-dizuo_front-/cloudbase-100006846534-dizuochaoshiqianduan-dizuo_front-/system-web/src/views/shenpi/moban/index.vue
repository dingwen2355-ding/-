<template>
    <div class="flex-col page">

        <!-- 筛选条件 -->
        <div class="flex-row header">
            <el-form class="elform" :inline="true" :model="queryParams">
                <el-form-item label="模板类型">
                    <el-select v-model="queryParams.type" clearable placeholder="请选择模板类型">
                        <el-option :label="'入驻申请模板'" :value="0"></el-option>
                        <el-option :label="'能力申请模板'" :value="1"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="模板名称">
                    <el-input v-model="queryParams.name" clearable></el-input>
                </el-form-item>
                <el-form-item label="是否使用">
                    <el-select v-model="queryParams.isUse" clearable placeholder="请选择模板类型">
                        <el-option :label="'否'" :value="0"></el-option>
                        <el-option :label="'是'" :value="1"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label-width="0">
                    <el-button type="primary" size="small" @click="getList">
                        <i class="el-icon-search"></i>查询
                    </el-button>
                    <el-button type="success" size="small" @click="fAddMoban">
                        <i class="el-icon-add"></i>新增
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
                <el-table-column prop="name" label="模板名称" align="center"></el-table-column>
                <el-table-column prop="type" label="模板类型" align="center">
                    <template slot-scope="{row}">
                        {{ row.type == 1 ? '能力申请模板' : '入驻申请模板' }}
                    </template>
                </el-table-column>
                <el-table-column prop="isUse" label="是否使用" align="center">
                    <template slot-scope="{row}">
                        {{ row.isUse == 1 ? '是' : '否' }}
                    </template>
                </el-table-column>
                <!-- <el-table-column prop="yonghu" label="上传用户" align="center"></el-table-column> -->
                <el-table-column prop="createTime" label="上传时间" align="center"></el-table-column>
                <el-table-column prop="userPhone" label="操作" align="center">
                    <template slot-scope="{row}">
                        <el-button type="primary" size="small" @click="openLayer(row)">
                            编辑
                        </el-button>
                        <el-button type="danger" size="small" @click="delFile(row)">
                            删除
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
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="模板编号">
                            <el-input disabled v-model="oAddForm.id"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="模板名称" prop="name">
                            <el-input v-model="oAddForm.name" placeholder="请输入模板名称"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="模板类型" prop="type">
                            <el-select v-model="oAddForm.type" placeholder="请选择模板类型">
                                <el-option :label="'入驻申请模板'" value="0"></el-option>
                                <el-option :label="'能力申请模板'" value="1"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <!-- <el-col :span="24">
                        <el-form-item label="上传用户" prop="yonghu">
                            <el-input disabled v-model="oAddForm.yonghu" placeholder="请输入上传用户"></el-input>
                        </el-form-item>
                    </el-col> -->
                    <el-col :span="12">
                        <el-form-item label="是否使用" prop="isUse">
                            <el-radio-group v-model="oAddForm.isUse">
                                <el-radio label="0">否</el-radio>
                                <el-radio label="1">是</el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>
                    <!-- <el-col :span="24">
                        <el-form-item label="审核时间" prop="shenheTime">
                            <el-input v-model="oAddForm.shenheTime" disabled></el-input>
                        </el-form-item>
                    </el-col> -->
                    <el-col :span="24">
                        <el-form-item label="上传附件">
                            <el-upload class="upload-demo" :action="sUploadUrl"
                                :headers="{ 'Authorization': 'Bearer ' + token }" :on-preview="handlePreview"
                                :on-success="fUploadFile" :on-remove="handleRemove" :multiple="false" :file-list="fileList">
                                <el-button size="small" type="primary">点击上传</el-button>
                            </el-upload>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="模板说明" prop="miaoshu">
                            <el-input type="textarea" :rows="3" placeholder="请输入模板说明" v-model="oAddForm.miaoshu"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
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
    cmMobanAllList,
    cmMobanSave,
    cmMobanGet,
    cmMobanDetele,
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
            layerTitle: '模板管理',
            bDialogDetails: false,
            oAddForm: {
                name: '', // 模板名称
                type: '', // 模板类型
                isUse: '', // 是否使用
                yonghu: '111',// 上传用户
                // shenheTime: this.fSetDate(),// 审核时间
                upload: '',
                miaoshu: '', // 模板说明
            },
            oAddFormRules: {
                name: [
                    { required: true, message: '请输入模板名称', trigger: 'blur' }
                ],
                // yonghu: [
                //     { required: true, message: '请选择上传用户', trigger: 'blur' }
                // ],
                miaoshu: [
                    { required: true, message: '请输入模板说明', trigger: 'blur' }
                ],
                type: [
                    { required: true, message: '请选择模板类型', trigger: 'change' }
                ],
                isUse: [
                    { required: true, message: '请选择是否使用', trigger: 'change' }
                ],
                // shenheTime: [
                //     { required: true, message: '请选择审核时间', trigger: 'change' }
                // ],
            },
            oDetails: {},
        }
    },
    computed: {
        name() {
            console.log(localStorage.getItem('name'))
            this.oAddForm.yonghu = localStorage.getItem('name') || ''
            return localStorage.getItem('name') || ''
        },
    },
    watch: {},
    created() {

    },
    mounted() {
        console.log(localStorage.getItem('name'))
        this.oAddForm.yonghu = localStorage.getItem('name')
        this.$set(this.oAddForm,'yonghu',localStorage.getItem('name'))
        console.log(this.oAddForm)
        // console.log(this.dict.type.unit_type)
        this.getList();
        // this.getAllList();
        this.fGetDeptName();
    },
    beforeDestroy() {

    },
    methods: {
        // 新增模板
        fAddMoban() {
            this.fileList = []
            this.oAddForm = {}
            this.bDialogDetails = true;
            this.layerTitle = '模板新增'
        },
        // 查询列表
        async getList() {
            this.loading = true
            let res = await cmMobanAllList(this.queryParams)
            console.log(res)
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
            // console.log(res, '所有的应用')
            this.aAllAppList = res.data.records;
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
                            // cmAbilityId: this.oDetails.id
                            // upload: JSON.stringify(this.oAddForm.upload)
                        }
                        cmMobanSave({ ...oData }).then(res => {
                            // console.log(res)
                            this.nCurrent = 1;
                            this.getList();
                            this.$message({
                                message: '新增成功',
                                type: 'success'
                            });
                            this.oAddForm.upload = []
                            this.fileList = []
                            this.bDialogDetails = false;
                            // this.sId = '';
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
            this.oAddForm = {}
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
            if (ids) {
                let text = []
                ids.split(',').forEach(id => {
                    this.aAllAppList.forEach(data => {
                        if (id == data?.casAppId) {
                            text.push(data.appName)
                        }
                    })
                })
                return text;
            } else {
                return []
            }
        },
        fDonLoad(arr) {
            if (arr && arr.length > 0) {
                downloadResource({ resource: arr[0].response.url }).then(res => {
                    this.downloadFile(res, arr[0].response.newFileName)
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
            this.oDetails = data;
            data.upload.forEach(item => {
                if (!item.name) {
                    item.name = item.newFileName
                }
            })
            this.fileList = data.upload
            this.oAddForm = {
                ...data
            }
            this.bDialogDetails = true;
            this.layerTitle = '模板管理'
        },
        // 删除按钮
        delFile(row) {
            console.log('删除')
            this.$confirm('您确定要删除吗, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                cmMobanDetele({ id:row.id }).then(res => {
                    this.nCurrent = 1;
                    this.getList();
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                })

            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
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
        fResect() {

        },
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
</style>
