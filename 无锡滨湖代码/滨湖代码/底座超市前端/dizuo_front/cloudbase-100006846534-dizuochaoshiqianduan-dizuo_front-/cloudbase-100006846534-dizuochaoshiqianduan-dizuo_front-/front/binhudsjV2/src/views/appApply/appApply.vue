<template>
  <!-- 上架申请 -->
  <div>
    <!-- <div class="home-banner">
            <div class="search-wrap">
                <div class="search-title">上架申请</div>
            </div>
        </div> -->
    <div class="box">
      <div class="box-block _bg">
        <div class="box-block-tit">入驻申请</div>
      </div>
    </div>

    <!-- 表单 -->
    <div class="box _flex">
      <div class="box-block _wid">
        <el-form
          ref="oAddFormRules"
          :rules="oAddFormRules"
          :model="oAddForm"
          label-width="100px"
        >
          <el-row>
            <el-col :span="12">
              <el-form-item label="申请表上传" prop="moban" label-width="120px">
                <el-upload
                  class="upload-demo1"
                  :action="sUploadUrl"
                  :headers="{ Authorization: 'Bearer ' + token }"
                  :on-preview="handlePreview"
                  :on-success="fUploadFileV2"
                  :on-remove="handleRemoveV2"
                  :multiple="false"
                  :file-list="fileListV2"
                >
                  <!-- <el-button size="small" type="primary">点击上传</el-button> -->
                  <el-button
                    class="btn-upload"
                    size="small"
                    type=""
                    icon="el-icon-plus"
                  ></el-button>
                  <el-button
                    size="small"
                    type="info"
                    icon="el-icon-download"
                    @click.stop="fClickExcel"
                    >下载模板</el-button
                  >
                </el-upload>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="应用图标" prop="appPic">
                <div class="" style="display: flex;justify-content: start;">
                  <el-image
                    v-if="oAddForm.appPic"
                    style="width: 148px; height: 148px"
                    :src="oAddForm.appPic"
                    :preview-src-list="[oAddForm.appPic]"
                  >
                  </el-image>
                  <el-upload
                    action="#"
                    :http-request="requestUpload"
                    :show-file-list="false"
                    :before-upload="beforeUpload"
                  >
                    <!-- 选择 -->
                    <div class="pic-wrap">
                      <i slot="default" class="el-icon-plus"></i>
                    </div>
                    <div slot="tip" class="el-upload__tip">
                      支持png/jpg格式、尺寸(宽*高):200x200、不超过2M;
                    </div>
                  </el-upload>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="应用名称" prop="appName">
                <el-input
                  v-model="oAddForm.appName"
                  show-word-limit
                  maxlength="50"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="应用分类" prop="appModule">
                <!-- <el-input v-model="oAddForm.appModule"></el-input> -->
                <el-select
                  v-model="oAddForm.appModule"
                  placeholder="请选择应用分类"
                  width="100%"
                >
                  <el-option
                    v-for="item in aAppModule"
                    :key="item.dictValue"
                    :label="item.dictLabel"
                    :value="item.dictValue"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="12">
              <el-form-item label="应用来源" prop="source">
                <el-select
                  v-model="oAddForm.source"
                  placeholder="请选择应用来源"
                >
                  <el-option
                    v-for="item in aAppSource"
                    :key="item.dictValue"
                    :label="item.dictLabel"
                    :value="item.dictValue"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="场景分类" prop="appType">
                <!-- <el-input v-model="oAddForm.appType"></el-input> -->
                <el-select
                  v-model="oAddForm.appType"
                  placeholder="请选择场景分类"
                  :multiple="true"
                 
                >
                  <el-option
                    v-for="item in aAppType"
                    :key="item.dictValue"
                    :label="item.dictLabel"
                    :value="item.dictValue"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="应用链接">
                <el-input v-model="oAddForm.appLink"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="应用介绍" prop="remark">
                <el-input
                  type="textarea"
                  :rows="3"
                  placeholder="请输入内容"
                  show-word-limit
                  maxlength="500"
                  v-model="oAddForm.remark"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="特色亮点" prop="liangdian">
                <el-input
                  type="textarea"
                  :rows="3"
                  placeholder="请输入内容(请用分号(';')分割)"
                  show-word-limit
                  maxlength="500"
                  v-model="oAddForm.liangdian"
                ></el-input>
              </el-form-item>
            </el-col>

            <el-col :span="24">
              <el-form-item label="应用截图" prop="appScreenshot">
                <el-upload
                  action="#"
                  :http-request="requestUpload"
                  :file-list="aPicList"
                  list-type="picture-card"
                  :on-change="beforeUploadV1"
                  :on-remove="fRemoveLogo"
                  :on-preview="fPictureCardPreview"
                >
                  <i slot="default" class="el-icon-plus"></i>
                  <div slot="tip" class="el-upload__tip">
                    支持png/jpg格式、不超过2M;
                  </div>
                </el-upload>
                <el-dialog :visible.sync="dialogVisible">
                  <img width="100%" :src="sDialogImageUrl" alt="" />
                </el-dialog>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="亮点截图">
                <el-upload
                  action="#"
                  :http-request="requestUpload"
                  :file-list="aPicListV2"
                  list-type="picture-card"
                  :on-change="beforeUploadV2"
                  :on-remove="fRemoveLogoV2"
                  :on-preview="fPictureCardPreview"
                >
                  <i slot="default" class="el-icon-plus"></i>
                  <div slot="tip" class="el-upload__tip">
                    支持png/jpg格式、不超过2M;
                  </div>
                </el-upload>
                <el-dialog :visible.sync="dialogVisibleV2">
                  <img width="100%" :src="sDialogImageUrl" alt="" />
                </el-dialog>
              </el-form-item>
            </el-col>

            <el-col :span="12">
              <el-form-item label="联系人" prop="principalName">
                <el-input v-model="oAddForm.principalName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="联系人电话" prop="principalPhone" label-width="120px">
                <el-input v-model="oAddForm.principalPhone"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="所属部门" prop="dept">
                <el-cascader
                  v-model="oAddForm.dept"
                  clearable
                  filterable
                  :options="aSsbm"
                  @change="fSsbmSelectChange"
                  :props="{ value: 'id', label: 'title' }"
                  placeholder="请选择所属部门"
                ></el-cascader>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="单位类型" prop="danweiType">
                <el-radio-group v-model="oAddForm.danweiType">
                  <el-radio
                    v-for="(item, idx) in aUnitType"
                    :key="'aUnitType' + idx"
                    :label="item.dictValue"
                    >{{ item.dictLabel }}</el-radio
                  >
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="申请理由" prop="applicationReason">
                <el-input
                  type="textarea"
                  :rows="3"
                  placeholder="请输入内容"
                  show-word-limit
                  maxlength="100"
                  v-model="oAddForm.applicationReason"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="其他材料">
                <el-upload
                  class="1"
                  :action="sUploadUrl"
                  :headers="{ Authorization: 'Bearer ' + token }"
                  :on-preview="handlePreview"
                  :on-success="fUploadFile"
                  :on-remove="handleRemove"
                  :multiple="false"
                  :file-list="fileList"
                  :before-upload="handleBeforeUpload"
                >
                  <el-button size="small" type="primary">点击上传</el-button>
                </el-upload>
                <div class="el-upload__tip">文件大小不能超过100M</div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-form-item>
            <el-button type="primary" @click="fOnSubmit">提 交</el-button>
            <el-button @click="fResetFormNew">重 置</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import {
  URLS_USERINFO,
  URLS_SZBH,
  URLS_APPMARKET,
  URLS_SZDZ,
  URLS_APPAPPLY
} from "@/api/url.js";
import axios from "axios";
import { KeepAlive } from "vue";

export default {
  name: "",
  components: {},
  props: [""],
  data() {
    return {
      oAppApplyForm: {
        name: "", //应用名称
        categoryId: "", // 应用类别
        url: "", //应用地址
        description: "", //应用介绍
        deptId: "", // 所属部门
        isGround: 0, // 是否上架
        isRecommend: 0, // 是否推荐
        isSendSms: 0, // 是否短信推送
        contacts: "", // 联系人
        telephone: "", // 电话
        logo: "", // 应用截图的地址
        logoId: "", // 应用截图的id
        icon: "", // 应用图标的地址
        iconId: "", // 应用图标的id
        applicant: JSON.parse(window.localStorage.getItem("userInfo")).account, // 申请人账号
        reason: "" // 申请理由
      },
      sUploadUrl: URLS_APPAPPLY.u_uploadFile, // 文件上传地址
      dialogImageUrl: "",
      dialogVisible: false,
      dialogVisibleV2: false,
      aYylb: [], // 应用类别
      aSsbm: [], // 所属部门
      aAppLogoData: [], // 应用截图的数据
      app_module: [],

      sUploadUrl: "https://2.21.138.89:11511/system_server/common/upload",
      // sUploadUrl: 'http://10.10.29.141:8080/common/upload',
      token: JSON.parse(window.localStorage.getItem("userInfo")).apiToken,
      fileList: [],
      fileListV2: [],
      oAddForm: {
        appSerialNo: "", // 编号
        principalName: JSON.parse(localStorage.getItem("userInfo")).name, // 联系人
        principalPhone: JSON.parse(localStorage.getItem("userInfo")).phone, // 联系人电话号码
        appPic: "", // 应用图标
        appName: "", // 应用名称
        appModule: "", // 所属模块
        // sortBy: '', // 显示顺序
        // status: null,// 是否上架
        // recommendFlag: null,// 是否推荐
        appType: "", // 应用类别
        // appUnit: '',// 应用单位
        dept: "", // 所属部门
        appLink: "", // 应用链接
        remark: "", // 应用介绍
        applicationReason: "", // 申请理由
        appScreenshot: [],
        upload: [],
        moban: [],
        source: "",
        liangdian: "",
        danweiType: "",
        liangdianpic: []
      },
      oAddFormRules: {
        principalName: [
          { required: true, message: "请输入联系人", trigger: "blur" }
        ],
        moban: [
          { required: true, message: "请上传材料申请表", trigger: "change" }
        ],
        principalPhone: [
          { required: true, message: "请输入联系人电话号码", trigger: "blur" },
          {
            required: true,
            pattern: /^[1][3,5,8]\d{9}$|^\d{7,8}$|^(\d{4}|\d{3})-(\d{7,8}$)|^\d{3,5}$|^(\d{4}|\d{3})-(\d{7,8})-(\d{4})$/,
            message: "请输入正确的联系方式",
            trigger: "blur"
          }
        ],
        appPic: [
          { required: true, message: "请上传应用图标", trigger: "blur,change" }
        ],
        appName: [
          { required: true, message: "请输入应用名称", trigger: "blur" }
        ],
        appModule: [
          { required: true, message: "请选择应用分类", trigger: "change" }
        ],
        source: [
          { required: true, message: "请选择应用来源", trigger: "change" }
        ],
        // sortBy: [
        //     { required: true, message: '请输入排序', trigger: 'blur' }
        // ],
        // status: [
        //     { required: true, message: '请选择是否上架', trigger: 'change' }
        // ],
        // recommendFlag: [
        //     { required: true, message: '请选择是否推荐', trigger: 'change' }
        // ],
        danweiType: [
          { required: true, message: "请选择单位类型", trigger: "change" }
        ],
        appType: [
          { required: true, message: "请选择场景分类", trigger: "change,blur" }
        ],
        dept: [
          { required: true, message: "请选择所属部门", trigger: "change" }
        ],
        // appUnit: [
        //     { required: true, message: '请输入应用单位', trigger: 'blur' }
        // ],
        appLink: [
          { required: true, message: "请输入应用链接", trigger: "blur" }
        ],
        remark: [
          { required: true, message: "请输入应用介绍", trigger: "blur" }
        ],
        liangdian: [
          { required: true, message: "请输入应用亮点", trigger: "blur" }
        ],
        applicationReason: [
          { required: true, message: "请输入申请理由", trigger: "blur" }
        ],
        appScreenshot: [
          { required: true, message: "请上传应用成果", trigger: "blur,change" }
        ]
      },
      sDialogImageUrl: "",
      bImageUrlVisible: false,
      dialogImageUrl: "",
      dialogVisible: false,
      aPicList: [],
      aPicListV2: [],
      aAppSource: [],
      aAppType: [], // 应用分类
      aAppModel: [], // 应用模块
      aUnitType: [] // 应用模块
    };
  },
  computed: {
    apitoken() {
      if (this.$store.state.oUserInfo.apiToken) {
        return this.$store.state.oUserInfo.apiToken;
      } else {
        return JSON.parse(localStorage.getItem("userInfo")).apiToken;
      }
    },
    userInfo() {
      if (this.$store.state.oUserInfo) {
        return this.$store.state.oUserInfo;
      } else {
        return JSON.parse(localStorage.getItem("userInfo"));
      }
    }
  },
  watch: {},
  created() {},
  mounted() {
    this.fRequestApplicationDept(); // 请求所属部门
    this.fRequestZd();
    this.fGetAppSource();
    this.fGetAppModule();
    this.fGetAppType();
    this.fGetUnitType();
  },
  beforeDestroy() {},
  methods: {
    // 请求字典 应用模块
    fGetAppModule() {
      this.$fRequest({
        url: this.$ip.server_ocn + "system/dict/data/type/app_module"
      }).then(res => {
        this.aAppModule = res.data || [];
      });
    },
    // 请求字典 单位类型
    fGetUnitType() {
      this.$fRequest({
        url: this.$ip.server_ocn + "system/dict/data/type/unit_type"
      }).then(res => {
        this.aUnitType = res.data || [];

        this.aUnitType.push(
          this.aUnitType.splice(
            this.aUnitType.findIndex(key => key.dictLabel == "其他单位"),
            1
          )[0]
        );
        console.log(this.aUnitType);
      });
    },
    // 请求字典 应用分类
    fGetAppType() {
      this.$fRequest({
        url: this.$ip.server_ocn + "system/dict/data/type/app_type"
      }).then(res => {
        this.aAppType = res.data || [];
      });
    },
    // 请求字典 应用来源
    fGetAppSource() {
      this.$fRequest({
        url: this.$ip.server_ocn + "system/dict/data/type/app_source"
      }).then(res => {
        this.aAppSource = res.data || [];
      });
    },
    fClickExcel() {
      // axios.get()
      this.$fRequest({
        url: this.$ip.server_ocn + "cmMoban/mobanUrl?type=0"
        // method:'get',
      }).then(res => {
        console.log(res);
        // this.aAppSource = res.data || []
        axios
          .get(
            this.$ip.server_ocn +
              "/common/download/resource?resource=" +
              res.data[0].upload[0].url,
            {
              headers: {
                Authorization: JSON.parse(
                  window.localStorage.getItem("userInfo")
                ).apiToken,
                ApiToken: JSON.parse(window.localStorage.getItem("userInfo"))
                  .apiToken
              },
              responseType: "blob"
            }
          )
          .then(resource => {
            this.downloadFile(
              resource.data,
              res.data[0].upload[0].originalFilename
            );
          });
      });
    },
    downloadFile(blob, fileName) {
      let blobUrl = window.URL.createObjectURL(
        new Blob([blob], { type: "application/force-download" })
      );
      let a = document.createElement("a");
      a.style.display = "none";
      a.setAttribute("href", blobUrl);
      a.setAttribute("download", `${fileName}`);
      document.body.appendChild(a);
      a.click(); //执行下载
      window.URL.revokeObjectURL(blobUrl);
      document.body.removeChild(a);
    },
    fUploadFile(res, file, fileList) {
      // console.log(res,file,fileList)
      this.oAddForm.upload = [];
      fileList.forEach(item => {
        this.oAddForm.upload.push({
          path: item.response.fileName,
          newFileName: item.response.newFileName,
          name: item.response.newFileName,
          url: item.response.url,
          originalFilename: item.response.originalFilename
        });
      });
    }, // 文件上传成功
    fUploadFileV2(res, file, fileList) {
      // console.log(res,file,fileList)
      this.oAddForm.moban = [];
      fileList.forEach(item => {
        this.oAddForm.moban.push({
          path: item.response.fileName,
          newFileName: item.response.newFileName,
          name: item.response.newFileName,
          url: item.response.url,
          originalFilename: item.response.originalFilename
        });
      });
    }, // 文件上传成功
    downloadFile(blob, fileName) {
      let blobUrl = window.URL.createObjectURL(
        new Blob([blob], { type: "application/force-download" })
      );
      let a = document.createElement("a");
      a.style.display = "none";
      a.setAttribute("href", blobUrl);
      a.setAttribute("download", `${fileName}`);
      document.body.appendChild(a);
      a.click(); //执行下载
      window.URL.revokeObjectURL(blobUrl);
      document.body.removeChild(a);
    },
    // 文件预览
    handlePreview(data) {
      axios
        .get(
          "https://2.21.138.89:11511/common/download/resource?resource=" +
            data.response.url,
          {
            headers: {
              Authorization: JSON.parse(window.localStorage.getItem("userInfo"))
                .apiToken,
              ApiToken: JSON.parse(window.localStorage.getItem("userInfo"))
                .apiToken
            },
            responseType: "blob"
          }
        )
        .then(result => {
          console.log(result);
          this.downloadFile(result.data, data.response.newFileName);
        })
        .catch(err => {
          console.log(err);
        });
      return;
    },
    handleBeforeUpload(file) {
      // 校检文件大小
      const isLt = file.size / 1024 / 1024 < 100;
      if (!isLt) {
        this.$message.error(`上传文件大小不能超过 100 MB!`);
        return false;
      }
      return true;
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
          originalFilename: item.response.originalFilename
        });
      });
    },
    handleRemoveV2(file, fileList) {
      // console.log(file,fileList);
      fileList.forEach(item => {
        this.oAddForm.moban.push({
          path: item.response.fileName,
          newFileName: item.response.newFileName,
          name: item.response.newFileName,
          url: item.response.url,
          originalFilename: item.response.originalFilename
        });
      });
    },
    // 请求字典
    fRequestZd() {
      $.ajax({
        url: "https://2.21.138.89:11511/system/dict/data/type/app_module",
        method: "get",
        headers: {
          Authorization: JSON.parse(window.localStorage.getItem("userInfo"))
            .apiToken,
          ApiToken: JSON.parse(window.localStorage.getItem("userInfo")).apiToken
          // "Content-Type": "application/json"
        },
        dataType: "json",
        contentType: "application/json;charset=utf-8"
      }).then(res => {
        // console.log(res)
        this.app_module = res.data;
      });
    },

    // 所属部门下拉框切换
    fSsbmSelectChange(item) {
      if (this.oAddForm.dept) {
        this.oAddForm.dept = this.oAddForm.dept[this.oAddForm.dept.length - 1];
      }
    },
    // 请求所属部门
    fRequestApplicationDept() {
      this.$fRequest({
        url: URLS_APPAPPLY.u_addApplicationDept
      }).then(res => {
        // console.log(res, '请求所属部门');
        this.aSsbm = res.data;
      });
    },
    // 重置表单
    fResetFormNew() {
      this.$refs["oAddFormRules"].resetFields();
      this.oAddForm.remark = "";
      this.oAddForm.upload = [];
      this.aPicList = [];
      this.fileList = [];
      this.aPicListV2 = [];
    },
    // 新增弹框确定按钮
    fOnSubmit() {
      this.$nextTick(() => {
        this.$refs["oAddFormRules"].validate(valid => {
          if (valid) {
            
            this.oAddForm.appType = JSON.parse(
              JSON.stringify(this.oAddForm.appType)
            ).join(",");
            let oData = {
              ...this.oAddForm,
              appScreenshot: JSON.stringify(this.oAddForm.appScreenshot),
              liangdianpic: JSON.stringify(this.oAddForm.liangdianpic)
            };

            if (this.oAddForm.moban && this.oAddForm.moban.length > 0) {
            } else {
              this.$message.error("请上传模板材料!");
              return;
            }

            $.ajax({
              url: "https://2.21.138.89:11511/cmApp/save",
              method: "post",
              headers: {
                Authorization: JSON.parse(
                  window.localStorage.getItem("userInfo")
                ).apiToken,
                ApiToken: JSON.parse(window.localStorage.getItem("userInfo"))
                  .apiToken
                // "Content-Type": "application/json"
              },
              dataType: "json",
              contentType: "application/json;charset=utf-8",
              data: JSON.stringify({
                ...oData
              })
            }).then(res => {
              // console.log(res)
              this.nCurrent = 1;
              if (res.code == "200") {
                this.$emit("fSetSqjlList", this.oAppApplyForm);
                window.localStorage.setItem(
                  "obj",
                  JSON.stringify(this.oAppApplyForm)
                );
                this.$message({
                  message: "恭喜你，提交成功",
                  type: "success"
                });
                this.fResetFormNew();
                // this.fResetForm('oAddFormRules');// 重置清空表单
              }
            });
          } else {
            console.log("error submit!!");
            this.bDialogVisible = false;
            return false;
          }
        });
      });
    },
    // 覆盖默认的上传行为
    requestUpload() {},
    //
    beforeUpload(file) {
      if (file.type.indexOf("image/") == -1) {
        this.$message({
          message: "文件格式错误，请上传图片类型,如：JPG，PNG后缀的文件。",
          type: "error"
        });
      } else {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => {
          this.oAddForm.appPic = reader.result;
        };
        let formData = new FormData();
        formData.append("avatarfile", file);

        $.ajax({
          url: "https://2.21.138.89:11511/system/user/profile/avatar",
          method: "post",
          contentType: false,
          processData: false,
          headers: {
            Authorization: JSON.parse(window.localStorage.getItem("userInfo"))
              .apiToken,
            ApiToken: JSON.parse(window.localStorage.getItem("userInfo"))
              .apiToken
            // "Content-Type": "application/json"
          },
          dataType: "json",
          // contentType: 'application/json;charset=utf-8',
          data: formData
        }).then(response => {
          this.oAddForm.appPic = "https://2.21.138.89:11511" + response.imgUrl;
          this.$message({
            message: "上传成功",
            type: "success"
          });
        });
      }
    },
    beforeUploadV1(file, fileList) {
      // this.oAddForm.appScreenshot = [];
      // console.log(file, fileList)
      // console.log(file.raw.size)
      let formData = new FormData();
      formData.append("avatarfile", file.raw);
      $.ajax({
        url: "https://2.21.138.89:11511/system/user/profile/avatar",
        method: "post",
        contentType: false,
        processData: false,
        headers: {
          Authorization: JSON.parse(window.localStorage.getItem("userInfo"))
            .apiToken,
          ApiToken: JSON.parse(window.localStorage.getItem("userInfo")).apiToken
          // "Content-Type": "application/json"
        },
        // dataType: 'json',
        // contentType: 'application/json;charset=utf-8',
        data: formData
      }).then(response => {
        // console.log(response, 111)
        this.oAddForm.appScreenshot.push(
          "https://2.21.138.89:11511" + response.imgUrl
        );
        this.$message({
          message: "上传成功",
          type: "success"
        });
      });
      return;
      const isLt2M = file.raw.size / 1024 / 1024 < 2;
      if (!isLt2M) {
        this.$message.error("上传文件大小不能超过 2MB!");
        this.aPicList = [];
      } else {
        if (file.raw.type.indexOf("image/") == -1) {
          this.$message({
            message: "文件格式错误，请上传图片类型,如：JPG，PNG后缀的文件。",
            type: "error"
          });
        } else {
          // const reader = new FileReader();
          // reader.readAsDataURL(file.raw);
          // reader.onload = () => {
          //     // this.oAddForm.appScreenshot.push(reader.result);
          // };
          let formData = new FormData();
          formData.append("avatarfile", file.raw);
          $.ajax({
            url: "https://2.21.138.89:11511/system/user/profile/avatar",
            method: "post",
            contentType: false,
            processData: false,
            headers: {
              Authorization: JSON.parse(window.localStorage.getItem("userInfo"))
                .apiToken,
              ApiToken: JSON.parse(window.localStorage.getItem("userInfo"))
                .apiToken
              // "Content-Type": "application/json"
            },
            // dataType: 'json',
            // contentType: 'application/json;charset=utf-8',
            data: formData
          }).then(response => {
            // console.log(response, 111)
            this.oAddForm.appScreenshot.push(
              "https://2.21.138.89:11511" + response.imgUrl
            );
            this.$message({
              message: "上传成功",
              type: "success"
            });
          });
          // console.log(this.oAddForm.appScreenshot)
        }
      }
    },
    beforeUploadV2(file, fileList) {
      // this.oAddForm.appScreenshot = [];
      // console.log(file, fileList)
      // console.log(file.raw.size)
      let formData = new FormData();
      formData.append("avatarfile", file.raw);
      $.ajax({
        url: "https://2.21.138.89:11511/system/user/profile/avatar",
        method: "post",
        contentType: false,
        processData: false,
        headers: {
          Authorization: JSON.parse(window.localStorage.getItem("userInfo"))
            .apiToken,
          ApiToken: JSON.parse(window.localStorage.getItem("userInfo")).apiToken
          // "Content-Type": "application/json"
        },
        // dataType: 'json',
        // contentType: 'application/json;charset=utf-8',
        data: formData
      }).then(response => {
        // console.log(response, 111)
        this.oAddForm.liangdianpic.push(
          "https://2.21.138.89:11511" + response.imgUrl
        );
        this.$message({
          message: "上传成功",
          type: "success"
        });
        console.log(this.oAddForm.liangdianpic);
      });
      return;
      const isLt2M = file.raw.size / 1024 / 1024 < 2;
      if (!isLt2M) {
        this.$message.error("上传文件大小不能超过 2MB!");
        this.aPicListV2 = [];
      } else {
        if (file.raw.type.indexOf("image/") == -1) {
          this.$message({
            message: "文件格式错误，请上传图片类型,如：JPG，PNG后缀的文件。",
            type: "error"
          });
        } else {
          // const reader = new FileReader();
          // reader.readAsDataURL(file.raw);
          // reader.onload = () => {
          //     // this.oAddForm.appScreenshot.push(reader.result);
          // };
          let formData = new FormData();
          formData.append("avatarfile", file.raw);
          $.ajax({
            url: "https://2.21.138.89:11511/system/user/profile/avatar",
            method: "post",
            contentType: false,
            processData: false,
            headers: {
              Authorization: JSON.parse(window.localStorage.getItem("userInfo"))
                .apiToken,
              ApiToken: JSON.parse(window.localStorage.getItem("userInfo"))
                .apiToken
              // "Content-Type": "application/json"
            },
            // dataType: 'json',
            // contentType: 'application/json;charset=utf-8',
            data: formData
          }).then(response => {
            // console.log(response, 111)
            this.oAddForm.liangdianpic.push(
              "https://2.21.138.89:11511" + response.imgUrl
            );
            this.$message({
              message: "上传成功",
              type: "success"
            });
            console.log(this.oAddForm.liangdianpic);
          });
          // console.log(this.oAddForm.appScreenshot)
        }
      }
    },
    fRemoveLogo(file, fileList) {
      this.oAddForm.appScreenshot = [];
      // return
      let index;
      fileList.forEach((data, idx) => {
        this.oAddForm.appScreenshot.push(data.url);
        // if (data.uid === file.uid || data.url === file.url) {
        //     index = idx
        // }
      });
      // if (index) {
      //     this.oAddForm.appScreenshot.splice(index, 1)
      // }
    },
    fRemoveLogoV2(file, fileList) {
      this.oAddForm.liangdianpic = [];
      fileList.forEach((data, idx) => {
        this.oAddForm.liangdianpic.push(data.url);
      });
    },
    // 预览图片
    fPictureCardPreview(file) {
      this.dialogVisible = true;
      this.sDialogImageUrl = file.url;
      // this.bImageUrlVisible = true;
    }
  }
};
</script>
<style lang="less" scoped>
.home-banner {
  position: relative;
  width: 100%;
  height: 617px;
  margin-bottom: 100px;
  background: url("../../assets/image/home/sjsqbg.png") no-repeat center / 100%
    617px;
}

.search-wrap {
  display: flex;
  align-items: center;
  // justify-content: center;
  flex-direction: column;
  padding-top: 102px;
  position: absolute;
  top: 0%;
  left: 0%;
  height: 100%;
  width: 100%;
  z-index: 999;
  font-weight: bold;
}

.search-title {
  color: #ffffff;
  font-size: 40px;
}

.box {
  padding-top: 0;

  &._flex {
    margin-bottom: 30px;
  }
}

.usercenter-banner {
  width: 100%;
  height: 617px;
  padding: 109px 0 0 0;
  margin-bottom: 50px;
  // margin-top: -65px;
  background: url("../../assets/image/home/bg.png") no-repeat center / 100%
    617px;
}

.usercenter-banner-title {
  text-align: center;
  margin-bottom: 28px;
  color: #fff;
  font-weight: 500;
  font-size: 60px;
}

.usercenter-banner-detail {
  text-align: center;
  width: 50%;
  margin: 0 auto;
  font-size: 18px;
  text-indent: 2rem;
  color: #fff;
}

.box-block {
  width: 100%;

  &._bg {
    margin-bottom: 29px;
    margin-top: 100px;
    text-align: center;
    font-size: 36px;
    color: #1a1b1f;
    // background: url('../../assets/image/comments/tit_bg2.png') no-repeat center center;
  }

  &._wid {
    width: 80%;
    margin: 0 auto;
  }

  &._width {
    width: 80%;
    margin: 0 auto;
  }
}

.box-block-tit {
  // margin-top: -20px !important;
}

/deep/.el-step__icon-inner {
  font-size: 50px;
}

/deep/.el-step__icon {
  height: 50px;
  width: 50px;
  margin-top: -12px;
  color: #fff;
  background: linear-gradient(152deg, #5ca4ff 0%, #2f6bff 100%);
}

/deep/.el-step__title {
  font-size: 25px;
}

/deep/.el-cascader {
  width: 100%;
}

.pic-wrap {
  width: 148px;
  height: 148px;
  background-color: #fbfdff;
  border: 1px dashed #c0ccda;
  box-sizing: border-box;
  border-radius: 0.3125vw;
  cursor: pointer;
  line-height: 148px;
  vertical-align: top;
}

/deep/.el-image__inner {
  width: 148px;
  height: 148px;
}

.btn-upload {
  width: 148px;
  height: 148px;
  font-size: 30px;
}

.upload-demo1 {
  /deep/.el-upload {
    display: inline-flex;
    align-items: end;
  }
}
/deep/.el-checkbox__label{
    font-size:  0.9rem;
}
/deep/ .el-radio__label{
   font-size: 0.9rem !important;
}
/deep/ .el-form-item__label{
    font-size: unset;
}
 
</style>
