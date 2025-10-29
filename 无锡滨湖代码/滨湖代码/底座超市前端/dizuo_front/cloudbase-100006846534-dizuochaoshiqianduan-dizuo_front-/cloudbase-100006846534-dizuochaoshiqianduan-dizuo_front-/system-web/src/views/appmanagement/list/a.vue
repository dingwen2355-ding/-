<template>
  <!-- 应用列表 -->
  <div class="appwrap">
    <!-- 头部筛选条件 -->
    <el-row>
      <el-col :span="5">
        应用名称:
        <el-input v-model="sAppName" placeholder="请输入应用名称/APP ID" style="width: 70%;"></el-input>
      </el-col>
      <el-col :span="5">
        应用模块:
        <el-select v-model="sAppModule" placeholder="请选择应用模块" @change="checkAppModule">
          <el-option v-for="item in dict.type.app_module" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
      </el-col>
      <el-col :span="5">
        应用类别:
        <el-select v-model="sAppSort" placeholder="请选择应用类别" @change="fChangeAppSort">
          <el-option v-for="item in aAppSort" :key="item.id" :label="item.typeName" :value="item.id">
          </el-option>
        </el-select>
      </el-col>
      <el-col :span="5">
        是否上架:
        <el-select v-model="sIsSj" placeholder="请选择是否上架" @change="fChangeIsSj">
          <el-option v-for="item in aIsSj" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
      </el-col>
      <el-col :span="5">
        是否推荐:
        <el-select v-model="sIsTj" placeholder="请选择是否推荐" @change="fChangeIsTj">
          <el-option v-for="item in aIsTj" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
      </el-col>
      <el-col :span="4">
        <el-button type="primary" @click="fSearch()">查询</el-button>
        <el-button @click="fClearQuery()">重置</el-button>
      </el-col>
    </el-row>
    <br/>
    <br/>
    <!--  -->
    <el-row>
      <!-- <el-button type="primary" @click="bDialogVisible = true">新增</el-button>
      <el-button type="danger">删除</el-button> -->
      <el-button type="primary" @click="fOpenLayer()">新增</el-button>
      <el-button type="danger" @click="fDeleteDataAll()">删除</el-button>
    </el-row>
    <br/>
    <el-table ref="multipleTable" :data="aTableData" stripe tooltip-effect="dark" style="width: 100%"
              @selection-change="fSelectionChange">
      <el-table-column type="selection" width="55" align="center">
      </el-table-column>
      <el-table-column align="center" prop="appSerialNo" label="应用编号"></el-table-column>
      <el-table-column align="center" prop="appName" label="应用名称"></el-table-column>
      <el-table-column align="center" prop="appType" label="应用类别">
        <template slot-scope="scope">{{
            aAppSort.filter(item => item.id === scope.row.appType)[0].typeName
          }}
        </template>
      </el-table-column>
      <el-table-column align="center" prop="status" label="是否上架">
        <template slot-scope="scope">{{ fSetSattus(aIsSj, scope.row.status) }}</template>
      </el-table-column>
      <el-table-column align="center" prop="recommendFlag" label="是否推荐">
        <template slot-scope="scope">{{ fSetSattus(aIsTj, scope.row.recommendFlag) }}</template>
      </el-table-column>
      <el-table-column align="center" label="操作" width="300px">
        <div class="wrap" slot-scope="scope">
          <el-button size="mini" type="primary" plain @click="fGetDetails(scope.row.id, true)">详情</el-button>
          <!-- <el-button size="mini" type="primary" plain @click="fGetDetails(scope.row.id, false)">编辑</el-button> -->
          <!-- <el-button size="mini" type="success" plain
              @click="fChangeStatus(scope.row, 0, '启用')">启用</el-button> -->
          <el-button v-if="scope.row.status == 0" size="mini" type="warning" plain
                     @click="fChangeStatus(scope.row, 3, '下架')">下架
          </el-button>
          <el-button size="mini" type="danger" v-if="scope.row.status == 3" plain
                     @click="fChangeStatus(scope.row, 0, '上架')">上架
          </el-button>
          <el-button size="mini" type="success" plain @click="fSetAppType(scope.row.id)">认证</el-button>
          <el-button size="mini" type="danger" v-if="scope.row.status == 3" plain
                     @click="fDeleteData(scope.row.id)">删除
          </el-button>
        </div>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination @current-change="fCurrentChange" :current-page="nCurrent" :page-size="15"
                   layout="total, prev, pager, next, jumper" :total="nTotal">
    </el-pagination>

    <!-- 新增弹框 -->
    <el-dialog :title="sLayerTitle" :visible.sync="bDialogVisible" width="50%">
      <el-form ref="oAddFormRules" :rules="oAddFormRules" :model="oAddForm" label-width="100px" :disabled="disabled">
        <el-row>
          <el-col :span="12">
            <el-form-item label="应用编号">
              <el-input v-model="oAddForm.appSerialNo" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="图标" prop="appPic">
              <el-image v-if="oAddForm.appPic" style="width: 100px; height: 100px" :src="oAddForm.appPic"
                        :preview-src-list="[oAddForm.appPic]">
              </el-image>
              <el-upload action="#" :http-request="requestUpload" :show-file-list="false"
                         :before-upload="beforeUpload">
                <el-button size="small">
                  选择
                  <i class="el-icon-upload el-icon--right"></i>
                  <div slot="tip" class="el-upload__tip">支持png/jpg格式、尺寸(宽*高):200x200、不超过2M;</div>
                </el-button>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="应用名称" prop="appName">
              <el-input v-model="oAddForm.appName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="显示顺序" prop="sortBy">
              <el-input type="number" v-model="oAddForm.sortBy"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="所属模块" prop="appModule">
              <!-- <el-input v-model="oAddForm.appModule"></el-input> -->
              <el-select v-model="oAddForm.appModule" placeholder="请选择应用模块" width="100%" @change="checkAppModule">
                <el-option v-for="item in dict.type.app_module" :key="item.value" :label="item.label" :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="应用类别" prop="appType">
              <!-- <el-input v-model="oAddForm.appType"></el-input> -->
              <el-select v-model="oAddForm.appType" placeholder="请选择应用类别">
                <el-option v-for="item in fAppSort" :key="item.id" :label="item.typeName" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="是否上架" prop="status">
              <el-select v-model="oAddForm.status" placeholder="请选择是否上架" @change="fChangeIsSj">
                <el-option v-for="item in aIsSj" :key="item.value" :label="item.label" :value="item.value">
                </el-option>
              </el-select>
              <!-- <el-radio v-model="oAddForm.isSj" label="1">是</el-radio>
              <el-radio v-model="oAddForm.isSj" label="2">否</el-radio> -->
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否推荐" prop="recommendFlag">
              <el-select v-model="oAddForm.recommendFlag" placeholder="请选择是否推荐" @change="fChangeIsTj">
                <el-option v-for="item in aIsTj" :key="item.value" :label="item.label" :value="item.value">
                </el-option>
              </el-select>
              <!-- <el-radio v-model="oAddForm.recommendFlag" label="1">是</el-radio>
              <el-radio v-model="oAddForm.recommendFlag" label="2">否</el-radio> -->
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="所属部门" prop="dept">
              <el-cascader v-model="oAddForm.dept" clearable filterable :options="aSsbm"
                           @change="fSsbmSelectChange" :props="{ value: 'id', label: 'title' }"
                           placeholder="请选择所属部门"></el-cascader>
            </el-form-item>

          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="应用链接" prop="appLink">
              <el-input v-model="oAddForm.appLink" placeholder="请输入应用链接"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="应用介绍" prop="remark">
              <el-input type="textarea" :rows="3" placeholder="请输入内容" v-model="oAddForm.remark"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="应用截图" prop="appScreenshot">
              <!-- <el-image v-for="(url, idx) in oAddForm.appScreenshot" :key="idx"
                  style="width: 100px; height: 100px" :src="url" :preview-src-list="oAddForm.appScreenshot">
              </el-image> -->
              <el-upload action="#" :http-request="requestUpload" :file-list="aPicList"
                         list-type="picture-card" :on-change="beforeUploadV1" :on-remove="fRemoveLogo"
                         :on-preview="fPictureCardPreview">
                <!--  :before-upload="beforeUploadV1"  -->
                <!-- <el-button size="small"> -->
                <i slot="default" class="el-icon-plus"></i>
                <div slot="tip" class="el-upload__tip">支持png/jpg格式、尺寸(宽*高):200x200、不超过2M;</div>
              </el-upload>
              <!-- 预览 -->
              <el-dialog :visible.sync="dialogVisible">
                <img width="100%" :src="sDialogImageUrl" alt="">
              </el-dialog>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item>
          <el-button type="primary" @click="fOnSubmit">确 认</el-button>
          <el-button>取 消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!-- 详情弹框 -->
    <!-- <el-dialog title="新增应用类别" :visible.sync="bDialogVisible" width="50%">
        <span>这是一段信息</span>
        <span slot="footer" class="dialog-footer">
            <el-button @click="bDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="bDialogVisible = false">确 定</el-button>
        </span>
    </el-dialog> -->
    <el-dialog :visible.sync="bImageUrlVisible">
      <img width="100%" :src="sDialogImageUrl" alt="">
    </el-dialog>
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>

    <el-dialog title="提示" :visible.sync="bAppType" width="30%">
      <span>这是一段信息</span>
      <span slot="footer" class="dialog-footer">
                <el-button @click="bAppType = false">取 消</el-button>
                <el-button type="primary" @click="bAppType = false">认 证</el-button>
            </span>
    </el-dialog>

  </div>
</template>

<script>

import {
  cmAppList,//查询应用列表
  cmAppDetail,//应用列表详情
  cmAppSave,//新增应用列表
  cmAppDetele,//删除应用列表
  cmAppTypeList,// 应用类别
  cmAppUpdateStatus,// 应用列表下架
} from "@/api/management/management";
import {uploadAvatar} from "@/api/system/user";


import userAvatar from '../../system/user/profile/userAvatar.vue'

import {getToken} from '@/utils/auth'
import axios from 'axios'
import dict from "@/utils/dict";
import {addApplicationDept} from "@/api/cas/cas";
import item from "@/layout/components/Sidebar/Item.vue";


export default {
  name: '',
  dicts: ['app_module', 'sys_user_sex'],
  components: {
    userAvatar
  },
  props: [''],
  data() {
    return {
      sId: '',// 应用id
      sIconIamgeUrl: '',
      sAppName: '',// 应用名称
      // 应用类别
      sAppSort: '',
      sAppModule: '',
      aAppSort: [
        {value: '1', label: '1'},
        {value: '2', label: '2'},
      ],
      fAppSort: [
        {value: '1', label: '1'},
        {value: '2', label: '2'},
      ],
      // 是否上架
      sIsSj: '',
      aIsSj: [
        {value: 0, label: '上架'},
        // { value: 1, label: '待审核' },
        // { value: 2, label: '驳回' },
        {value: 3, label: '下架'},
      ],
      // 是否推荐
      sIsTj: '',
      aIsTj: [
        {value: 0, label: '是'},
        {value: 1, label: '否'},
      ],
      // 表格数据
      aTableData: [],
      aMultipleSelection: [],
      nCurrent: 1, // 分页页码
      nTotal: 0, // 总条数
      bDialogVisible: false,
      oAddForm: {
        appSerialNo: '',// 编号
        appPic: '',// 应用图标
        appName: '',// 应用名称
        appModule: '',// 所属模块
        sortBy: '', // 显示顺序
        status: null,// 是否上架
        recommendFlag: null,// 是否推荐
        appType: '',// 应用类别
        aSsbm: '',// 所属部门
        appLink: '',// 应用链接
        remark: "",// 应用介绍
        appScreenshot: [],
      },
      oAddFormRules: {
        appPic: [
          {required: true, message: '请上传应用图标', trigger: 'blur,change'}
        ],
        appName: [
          {required: true, message: '请输入应用名称', trigger: 'blur'}
        ],
        appModule: [
          {required: true, message: '请选择所属模块', trigger: 'change'}
        ],
        sortBy: [
          {required: true, message: '请输入排序', trigger: 'blur'}
        ],
        status: [
          {required: true, message: '请选择是否上架', trigger: 'change'}
        ],
        recommendFlag: [
          {required: true, message: '请选择是否推荐', trigger: 'change'}
        ],
        appType: [
          {required: true, message: '请选择应用类别', trigger: 'change'}
        ],
        dept: [
          {required: true, message: '请选择所属部门', trigger: 'change'}
        ],
        appLink: [
          {required: true, message: '请输入应用链接', trigger: 'blur'}
        ],
        appScreenshot: [
          {required: true, message: '请上传应用截图', trigger: 'blur,change'}
        ],

      },
      sDialogImageUrl: '',
      bImageUrlVisible: false,
      dialogImageUrl: '',
      dialogVisible: false,
      disabled: false,
      sLayerTitle: '新增应用列表',
      aPicList: [],
      aYylb: [], // 应用分类
      bAppType: false, // 认真弹框
    }
  },
  computed: {},
  watch: {},
  created() {

  },
  mounted() {
    // console.log(process)
    this.getAppType();
    this.getList();
    this.fRequestApplicationDept();// 请求蓝创的所属部门
  },
  beforeDestroy() {

  },
  methods: {
    dict,
    // 认证按钮
    fSetAppType(id) {
      this.$alert('这是一段内容', '标题名称', {
        confirmButtonText: '确定',
        callback: action => {
          this.$message({
            type: 'info',
            message: `action: ${action + id}`
          });
        }
      });
    },
    // 请求蓝创的所属部门
    fRequestApplicationDept() {
      console.info("请求蓝创的所属部门")
      addApplicationDept().then((res) => {
        // console.log(res)
        this.aSsbm = res.data;
      })
    },
    // 搜索按钮
    fSearch() {
      this.nCurrent = 1;
      this.getList();
    },
    // 清除列表搜索条件
    fClearQuery() {
      this.sAppName = '';
      this.sAppSort = '';
      this.sAppModule = '';
      this.sIsSj = '';
      this.sIsTj = '';
      this.fSearch();
    },
    // 打开新增弹框
    fOpenLayer() {
      this.fAppSort=[]
      this.sLayerTitle = '新增应用列表'
      this.oAddForm = {
        appSerialNo: '',// 编号
        appPic: '',// 应用图标
        appName: '',// 应用名称
        appModule: '',// 所属模块
        sortBy: '', // 显示顺序
        status: null,// 是否上架
        recommendFlag: null,// 是否推荐
        appType: '',// 应用类别
        // dept: '',// 应用单位
        remark: '',// 应用介绍
        appLink: '',// 应用链接
        appScreenshot: [],
      }
      this.aPicList = [];
      this.bDialogVisible = true;
      this.disabled = false;
    },
    checkAppModule(e) {
      console.info("checkAppModule:")
      console.info(e)
      this.oAddForm.appType = null
      this.fAppSort = this.aAppSort.filter(item => {
        return item.appModule === e;
      })
    },
    /** 请求应用类别 */
    getAppType(appModule) {
      this.loading = true;
      cmAppTypeList({
        typeName: '',// 类型名称
        appModule: '',// 所属模块
        status: '',// 状态
        pageNum: 1,// 页码
        pageSize: 1000,// 每页的数量
      }).then(res => {
          let records = res.data.records;
          this.aAppSort = records
        }
      );
    },
    /** 查询参数列表 */
    getList() {
      this.loading = true;
      cmAppList({
        appName: this.sAppName,// 应用名称
        appType: this.sAppSort,// 应用类别
        appModule: this.sAppModule,// 应用类别
        status: this.sIsSj,// 是否上架
        recommendFlag: this.sIsTj,// 是否推荐
        pageNum: this.nCurrent,// 页码
        pageSize: 13,// 每页的数量
      }).then(res => {
          console.log(res)
          debugger
          this.aTableData = res.data.records;
          this.nTotal = res.data.total;
        }
      );
    },
    // 获取详情
    fGetDetails(id, status) {
      this.fAppSort=[]
      cmAppDetail({id}).then(res => {
        // console.log(res);
        for (const key in this.oAddForm) {
          this.oAddForm[key] = res.data[key]
        }
        this.oAddForm.appScreenshot = JSON.parse(this.oAddForm.appScreenshot)
        this.aPicList = this.fSetPicArr(this.oAddForm.appScreenshot)
        // console.log(this.oAddForm)
        this.disabled = status;
        if (this.disabled) {
          this.sLayerTitle = '应用列表详情'
        } else {
          this.sLayerTitle = '修改应用列表'
          this.sId = id;
        }
        this.bDialogVisible = true
      })
    },
    // 批量删除
    fDeleteDataAll() {
      console.log(this.aMultipleSelection)
      if (this.aMultipleSelection.length > 0) {
        //
        this.fDeleteData(this.aMultipleSelection.join(','))
      } else {
        this.$message({
          type: 'warning',
          message: '请选择需要删除的数据!'
        });
      }
    },
    // 删除
    fDeleteData(id) {
      this.$confirm('您确定要删除吗, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        cmAppDetele({id}).then(res => {
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
    // 下架按钮
    fChangeStatus(row, status, text) {
      cmAppUpdateStatus({
        id: row.id,// 类型名称
        status: status,// 状态
      }).then(res => {
        // console.log(res)
        this.nCurrent = 1;
        this.getList();
        this.$message({
          message: text + '成功',
          type: 'success'
        });
      })
    },
    // 设置文字
    fSetSattus(data, text) {
      let stext = ''
      if (data.filter(item => item.value == text)[0]) {
        stext = data.filter(item => item.value == text)[0].label;
      } else {
        stext = '--';
      }
      return stext;
    },
    // 设置图片回显
    fSetPicArr(data) {
      let array = []
      data.forEach((item, idx) => {
        array.push({
          name: idx,
          url: item
        })
      })
      return array;
    },
    // 应用类别切换
    fChangeAppSort(val) {
      console.log(val)
    },
    // 是否上架切换
    fChangeIsSj(val) {
      console.log(val)
    },
    // 是否推荐切换
    fChangeIsTj(val) {
      console.log(val)
    },
    // 所属部门下拉框切换
    fSsbmSelectChange(item) {
      if (this.oAddForm.dept) {
        this.oAddForm.dept = this.oAddForm.dept[this.oAddForm.dept.length - 1]
      }

    },
    // 表格多选框
    fSelectionChange(data) {
      data.forEach(item => {
        this.aMultipleSelection.push(item.id);
      })
      // this.aMultipleSelection = val;
    },
    // 分页切换
    fCurrentChange() {
      console.log(`当前页: ${val}`);
      this.nCurrent = val
      this.getList();
    },
    // 删除图片
    fRemoveImg(file, fileList) {
      console.log(file, fileList);
    },
    // 预览图片
    fPictureCardPreview(file) {
      this.sDialogImageUrl = file.url;
      this.bImageUrlVisible = true;
    },
    // 新增弹框确定按钮
    fOnSubmit() {
      this.$nextTick(() => {
        this.$refs['oAddFormRules'].validate((valid) => {
          if (valid) {
            let oData = {
              ...this.oAddForm, appScreenshot: JSON.stringify(this.oAddForm.appScreenshot)
            }
            if (this.sId) {
              oData.id = this.sId
            }
            cmAppSave({...oData}).then(res => {
              // console.log(res)
              this.nCurrent = 1;
              this.getList();
              this.$message({
                message: this.sId ? '修改成功' : '新增成功',
                type: 'success'
              });
              this.bDialogVisible = false;
              this.sId = '';
            })
          } else {
            console.log('error submit!!');
            this.bDialogVisible = false;
            return false;
          }
        });
      })
    },
    // 覆盖默认的上传行为
    requestUpload() {
    },
    //
    beforeUpload(file) {
      if (file.type.indexOf("image/") == -1) {
        this.$modal.msgError("文件格式错误，请上传图片类型,如：JPG，PNG后缀的文件。");
      } else {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => {
          this.oAddForm.appPic = reader.result;
        };
        let formData = new FormData();
        formData.append("avatarfile", file);
        uploadAvatar(formData).then(response => {
          this.oAddForm.appPic = process.env.VUE_APP_BASE_API + response.imgUrl;
          this.$modal.msgSuccess("上传成功");
        });
      }
    },
    beforeUploadV1(file, fileList) {
      // this.oAddForm.appScreenshot = [];
      console.log(file, fileList)

      if (file.raw.type.indexOf("image/") == -1) {
        this.$modal.msgError("文件格式错误，请上传图片类型,如：JPG，PNG后缀的文件。");
      } else {
        // const reader = new FileReader();
        // reader.readAsDataURL(file.raw);
        // reader.onload = () => {
        //     // this.oAddForm.appScreenshot.push(reader.result);
        // };
        let formData = new FormData();
        formData.append("avatarfile", file.raw);
        uploadAvatar(formData).then(response => {
          console.log(response, 111)
          this.oAddForm.appScreenshot.push(process.env.VUE_APP_BASE_API + response.imgUrl);
          // this.oAddForm.appScreenshot = process.env.VUE_APP_BASE_API + response.imgUrl;
          // this.$modal.msgSuccess("上传成功");
        });
        console.log(this.oAddForm.appScreenshot)
      }
    },
    fRemoveLogo(file, fileList) {
      this.oAddForm.appScreenshot = []
      // return
      let index;
      fileList.forEach((data, idx) => {
        this.oAddForm.appScreenshot.push(data.url)
        // if (data.uid === file.uid || data.url === file.url) {
        //     index = idx
        // }
      });
      // if (index) {
      //     this.oAddForm.appScreenshot.splice(index, 1)
      // }
    },
    handleRemove(file) {
      console.log(file);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    // fBeforeUpload(file) {
    //     console.log(file)
    //     let formData = new FormData();
    //     formData.append("avatarfile", file);
    //     uploadAvatar(formData).then(response => {
    //         this.open = false;
    //         this.options.img = process.env.VUE_APP_BASE_API + response.imgUrl;
    //         store.commit('SET_AVATAR', this.options.img);
    //         this.$modal.msgSuccess("修改成功");
    //         this.visible = false;
    //     });
    // },
  }
}
</script>
<style lang='scss' scoped>
.appwrap {
  height: 100%;
  width: 100%;
  padding: 30px;
}

// ::v-deep .el-col {
//     display: flex;
//     align-items: center;
//     justify-content: center;
// }

::v-deep .el-select {
  width: 70%;
}

::v-deep .el-pagination {
  display: flex;
  align-items: center;
  justify-content: end;
  margin-top: 20px;
}
</style>
