<template>
  <div class="addStandardSpecification">
    <el-dialog
      :title="isCode == 0 ? '修改' : isCode === 1 ? '查看' : '新增'"
      :visible.sync="dialogVisible"
      width="1100px"
      :modal-append-to-body="false"
      :append-to-body="false"
      :before-close="handleClose"
    >
      <div class="info_body" v-loading="loading">
        <el-form
          :inline="true"
          ref="formLabel"
          :rules="rules"
          :model="form"
          label-width="110px"
          :disabled="isCode == 1"
        >
          <el-form-item label="文件名称" prop="fileName">
            <el-input clearable v-model="form.fileName" placeholder="请输入文件名称"></el-input>
          </el-form-item>
          <el-form-item label="编制单位" prop="preparationUnit">
            <el-input clearable v-model="form.preparationUnit" placeholder="请输入编制单位"></el-input>
          </el-form-item>
          <el-form-item label="安全管理类型" prop="managementType">
            <el-select filterable v-model="form.managementType" placeholder="请选择安全管理类型" clearable>
              <el-option
                v-for="item in managementTypeList"
                :value="item.dictName"
                :label="item.dictName"
                :key="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="监管行业" prop="regulatoryIndustry">
            <el-select filterable v-model="form.regulatoryIndustry" placeholder="请选择监管行业" clearable>
              <el-option
                v-for="item in regulatoryIndustryList"
                :value="item.dictName"
                :label="item.dictName"
                :key="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="编制日期" prop="preparedDate">
            <el-date-picker
              v-model="form.preparedDate"
              type="date"
              placeholder="选择时间"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="生效日期" prop="effectiveDate">
            <el-date-picker
              v-model="form.effectiveDate"
              type="date"
              placeholder="选择时间"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="文档类型" prop="documentType">
            <el-select filterable v-model="form.documentType" placeholder="请选择文档类型" clearable>
              <el-option
                v-for="item in documentTypeList"
                :value="item.dictName"
                :label="item.dictName"
                :key="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="内容摘要" prop="contentSummary">
            <el-input
              clearable
              type="textarea"
              :rows="2"
              v-model="form.contentSummary"
              style="width: 830px"
              placeholder="请输入内容摘要"
            ></el-input>
          </el-form-item>
          <el-form-item label="备注" prop="notes">
            <el-input
              clearable
              type="textarea"
              :rows="2"
              v-model="form.notes"
              style="width: 830px"
              placeholder="请输入备注"
            ></el-input>
          </el-form-item>
          <el-form-item label="附件" prop="photo">
            <AttachmentUploadNew
              @success="siteImg"
              :title="title"
              :imgUrl="form.annexUrl"
              :type="tpye"
            />
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
        <el-button type="primary" @click="handleOk" v-if="isCode !== 1">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import request from '@/utils/request'
import AttachmentUploadNew from '../../../../components/attachmentUploadNew.vue'

export default {
  name: 'addStandardSpecification',
  components: { AttachmentUploadNew },
  props: ['dialogVisible', 'formInfo', 'isCode'],
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
      if (v) {
        this.title = this.isCode == 0 ? '修改' : this.isCode === 1 ? '详情' : '新增'

        this.form = Object.assign({}, JSON.parse(JSON.stringify(this.formInfo)))
      }
    }
  },
  mounted() {
    this.getManagementTypeList()
    this.getRegulatoryIndustryList()
    this.getDocumentTypeList()
  },
  data() {
    return {
      title: '',
      rules: {
        fileName: [{ required: true, message: '请输入文件名称', trigger: 'blur' }],
        preparationUnit: [{ required: true, message: '请输入编制单位', trigger: 'blur' }],
        managementType: [{ required: true, message: '请选择安全管理类型', trigger: 'blur' }],
        regulatoryIndustry: [{ required: true, message: '请选择监管行业', trigger: 'blur' }],
        preparedDate: [{ required: true, message: '选择编制日期', trigger: 'blur' }],
        effectiveDate: [{ required: true, message: '选择生效日期', trigger: 'blur' }],
        documentType: [{ required: true, message: '请输入文档类型', trigger: 'blur' }]
      },
      loading: false,
      form: {
        fileName: '',
        preparationUnit: '',
        managementType: '',
        regulatoryIndustry: '',
        effectiveDate: '',
        preparedDate: '',
        documentType: '',
        contentSummary: '',
        notes: '',
        annexUrl: ''
      },
      gpsInfo: '',
      dialogMap: false,
      files: [],
      imgDialogVisible: false,
      dialogImageUrl: '',
      communityList: [],
      isChange: false,
      managementTypeList: [],
      regulatoryIndustryList: [],
      documentTypeList: [],
      tpye: '标准规范'
    }
  },
  methods: {
    getManagementTypeList() {
      request.get('/binhuapis/dict/query-condition?parentId=' + '73').then((res) => {
        if (res.data.code === 200) {
          this.managementTypeList = res.data.data.list
        }
      })
    },
    getRegulatoryIndustryList() {
      request.get('/binhuapis/dict/query-condition?parentId=' + '92').then((res) => {
        if (res.data.code === 200) {
          this.regulatoryIndustryList = res.data.data.list
        }
      })
    },
    getDocumentTypeList() {
      request.get('/binhuapis/dict/query-condition?parentId=' + '98').then((res) => {
        if (res.data.code === 200) {
          this.documentTypeList = res.data.data.list
        }
      })
    },
    siteImg(r) {
      this.isChange = true
      this.files = r
      console.log(this.form.files)
    },
    handleClose() {
      if (this.isCode !== 1) {
        this.$confirm('尚未保存，是否在离开页面前保存？', '提示', {
          distinguishCancelAndClose: true,
          confirmButtonText: '提交保存',
          cancelButtonText: '关闭窗口',
          type: 'warning'
        })
          .then(() => {
            this.handleOk()
          })
          .catch((e) => {
            if (e === 'cancel') {
              this.$emit('update:dialogVisible', false)
            }
          })
      } else {
        this.$emit('update:dialogVisible', false)
      }
      this.reset()
    },
    reset() {
      this.loading = false
      this.form = {}
      // 清空表单校验，避免再次进来会出现上次校验的记录
      this.$refs.formLabel.clearValidate()
    },
    handleOk() {
      this.$refs.formLabel.validate((valid) => {
        if (valid) {
          this.submitAll()
        } else {
          this.$message.warning('检查表单输入内容的合法性！')
          // console.log('error submit!!')
          return false
        }
      })
    },
    submitAll() {
      this.loading = true
      if (this.form.gps) {
        let gps = this.form.gps.split(',')
        this.form.gpsx = gps[0].trim()
        this.form.gpsy = gps[1].trim()
      }
      let newForm = {
        fileName: this.form.fileName,
        preparationUnit: this.form.preparationUnit,
        managementType: this.form.managementType,
        regulatoryIndustry: this.form.regulatoryIndustry,
        effectiveDate: this.form.effectiveDate,
        preparedDate: this.form.preparedDate,
        documentType: this.form.documentType,
        contentSummary: this.form.contentSummary,
        notes: this.form.notes,
        annexUrl: this.form.annexUrl
      }
      let url = ''
      let formData = new FormData()
      if (this.formInfo && this.formInfo.id) {
        newForm.id = this.formInfo.id
        url = '/binhuapis/Specification/updateByPrimaryKeySelective'
        if (this.files.length === 0 && this.isChange) {
          this.form.annexUrl = ''
        } else {
          for (var i = 0; i < this.files.length; i++) {
            formData.append('files', this.files[i].raw)
          }
        }
      } else {
        url = '/binhuapis/Specification/insertSelective'
        for (var i = 0; i < this.files.length; i++) {
          formData.append('files', this.files[i].raw)
        }
      }
      formData.append('specification ', JSON.stringify(newForm))
      request.post(url, formData).then((r) => {
        this.loading = false
        if (r.data.code === 200) {
          if (this.formInfo && this.formInfo.id) {
            this.$message.success('修改成功！')
          } else {
            this.$message.success('新增成功！')
          }
          this.$emit('success')
          this.reset()
          this.$emit('update:dialogVisible', false)
        } else {
          this.$message.error(r.data.message)
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.addStandardSpecification {
  // .up_load {
  //   display: flex;
  .up_load_img {
    border: 1px solid #d9d9d9;
    margin-bottom: 5px;
  }
  .el-date-editor.el-input,
  .el-date-editor.el-input__inner {
    width: 196px;
  }
  // }
}
</style>
