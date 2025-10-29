<template>
  <div class="addHistoricalEvent">
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
          <el-form-item label="事故名称" prop="accidentTitle">
            <el-input clearable v-model="form.accidentTitle" style="width: 830px;" placeholder="请输入事故名称"></el-input>
          </el-form-item>
          <el-form-item label="事故地点" prop="accidentAddress">
            <el-input clearable v-model="form.accidentAddress" style="width: 830px;" placeholder="请输入事故地点"></el-input>
          </el-form-item>
          <el-form-item label="事故案例类型" prop="accidentCasesType">
            <el-select filterable v-model="form.accidentCasesType" placeholder="请选择事故案例类型" clearable>
              <el-option
                v-for="item in accidentCasesTypeList"
                :value="item.dictName"
                :label="item.dictName"
                :key="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="事故类型" prop="accidentType">
            <el-select filterable v-model="form.accidentType" placeholder="请选择事故类型" clearable>
              <el-option v-for="item in accidentTypeList" :value="item.dictName" :label="item.dictName" :key="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="发生日期" prop="occurrenceDate">
            <el-date-picker
              v-model="form.occurrenceDate"
              type="date"
              placeholder="选择时间"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
            ></el-date-picker>
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
          <el-form-item label="事故模型" prop="accidentModel">
            <el-input clearable v-model="form.accidentModel" placeholder="请输入事故模型"></el-input>
          </el-form-item>
          <el-form-item label="轻伤人数" prop="minorNum">
            <el-input clearable type="number" min="0" v-model="form.minorNum" placeholder="请输入轻伤人数"></el-input>
          </el-form-item>
          <el-form-item label="重伤人数" prop="seriousNum">
            <el-input clearable type="number" min="0" v-model="form.seriousNum" placeholder="请输入重伤人数"></el-input>
          </el-form-item>
          <el-form-item label="死亡人数" prop="deathNum">
            <el-input clearable type="number" min="0" v-model="form.deathNum" placeholder="请输入死亡人数"></el-input>
          </el-form-item>
          <el-form-item label="经济损失" prop="economicLoss">
            <el-input clearable type="number" min="0" v-model="form.economicLoss" placeholder="请输入经济损失"></el-input>
          </el-form-item>
          <el-form-item label="事故原因" prop="accidentCause">
            <el-input
              clearable
              type="textarea"
              :rows="2"
              v-model="form.accidentCause"
              style="width: 830px;"
              placeholder="请输入事故原因"
            ></el-input>
          </el-form-item>
          <el-form-item label="预防措施" prop="preventiveMeasure">
            <el-input
              clearable
              type="textarea"
              :rows="2"
              v-model="form.preventiveMeasure"
              style="width: 830px;"
              placeholder="请输入预防措施"
            ></el-input>
          </el-form-item>
          <el-form-item label="附件" prop="photo">
            <AttachmentUploadNew @success="siteImg" :title="title" :imgUrl="form.annexUrl" :type="'历史事件'" />
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
  name: 'addHistoricalEvent',
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
  data() {
    return {
      title: '',
      rules: {
        accidentTitle: [{ required: true, message: '请输入事故名称', trigger: 'blur' }],
        accidentAddress: [{ required: true, message: '请输入事故地点', trigger: 'blur' }],
        accidentCasesType: [{ required: true, message: '请选择事故案例类型', trigger: 'blur' }],
        accidentType: [{ required: true, message: '请选择事故类型', trigger: 'blur' }],
        occurrenceDate: [{ required: true, message: '选择发生日期', trigger: 'blur' }],
        regulatoryIndustry: [{ required: true, message: '请选择监管行业', trigger: 'blur' }],
        accidentModel: [{ required: true, message: '请输入事故模型', trigger: 'blur' }],
        minorNum: [{ required: true, message: '请输入轻伤人数', trigger: 'blur' }],
        seriousNum: [{ required: true, message: '请输入重伤人数', trigger: 'blur' }],
        deathNum: [{ required: true, message: '请输入死亡人数', trigger: 'blur' }],
        economicLoss: [{ required: true, message: '请输入经济损失', trigger: 'blur' }],
        accidentCause: [{ required: true, message: '请输入事故原因', trigger: 'blur' }],
        preventiveMeasure: [{ required: true, message: '请输入预防措施', trigger: 'blur' }]
      },
      loading: false,
      form: {
        accidentTitle: '',
        accidentAddress: '',
        accidentCasesType: '',
        accidentType: '',
        occurrenceDate: '',
        regulatoryIndustry: '',
        accidentModel: '',
        minorNum: '',
        seriousNum: '',
        deathNum: '',
        economicLoss: '',
        accidentCause: '',
        preventiveMeasure: '',
        annexUrl: ''
      },
      files: [],
      isChange: false,
      accidentCasesTypeList: [],
      accidentTypeList: [],
      regulatoryIndustryList: []
    }
  },
  mounted() {
    this.getAccidentTypeList()
    this.getAcciCasesTypeList()
    this.getRegulatoryIndustryList()
  },
  methods: {
    getAccidentTypeList() {
      request.get('/binhuapis/dict/query-condition?parentId=' + '102').then(res => {
        if (res.data.code === 200) {
          this.accidentTypeList = res.data.data.list
        }
      })
    },
    getAcciCasesTypeList() {
      request.get('/binhuapis/dict/query-condition?parentId=' + '121').then(res => {
        if (res.data.code === 200) {
          this.accidentCasesTypeList = res.data.data.list
        }
      })
    },
    getRegulatoryIndustryList() {
      request.get('/binhuapis/dict/query-condition?parentId=' + '92').then(res => {
        if (res.data.code === 200) {
          this.regulatoryIndustryList = res.data.data.list
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
      this.form = {
        accidentTitle: '',
        accidentAddress: '',
        accidentCasesType: '',
        accidentType: '',
        occurrenceDate: '',
        regulatoryIndustry: '',
        accidentModel: '',
        minorNum: '',
        seriousNum: '',
        deathNum: '',
        economicLoss: '',
        accidentCause: '',
        preventiveMeasure: '',
        annexUrl: ''
      }
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
      let newForm = {
        accidentTitle: this.form.accidentTitle,
        accidentAddress: this.form.accidentAddress,
        accidentCasesType: this.form.accidentCasesType,
        accidentType: this.form.accidentType,
        occurrenceDate: this.form.occurrenceDate,
        regulatoryIndustry: this.form.regulatoryIndustry,
        accidentModel: this.form.accidentModel,
        minorNum: this.form.minorNum,
        seriousNum: this.form.seriousNum,
        deathNum: this.form.deathNum,
        economicLoss: this.form.economicLoss,
        accidentCause: this.form.accidentCause,
        preventiveMeasure: this.form.preventiveMeasure,
        annexUrl: this.form.annexUrl
      }
      let url = ''
      let formData = new FormData()
      if (this.formInfo && this.formInfo.id) {
        newForm.id = this.formInfo.id
        url = '/binhuapis/HistoricalEvent/updateByPrimaryKeySelective'
        if (this.files.length === 0 && this.isChange) {
          this.form.annexUrl = ''
        } else {
          for (var i = 0; i < this.files.length; i++) {
            formData.append("files", this.files[i].raw)
          }
        }
      } else {
        url = '/binhuapis/HistoricalEvent/insertSelective'
        for (var i = 0; i < this.files.length; i++) {
          formData.append("files", this.files[i].raw)
        }
      }
      formData.append('historicalEvent', JSON.stringify(newForm))

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
<style lang="scss">
.addHistoricalEvent{
  .el-date-editor.el-input, .el-date-editor.el-input__inner {
    width: 200px;
}
}
</style>
