<template>
  <div class="addRiskDanger">
    <el-dialog
      :title="isCode == 0 ? '修改' : isCode === 1 ? '查看' : '新增'"
      :visible.sync="dialogVisible"
      width="1150px"
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
          label-width="130px"
          :disabled="isCode == 1"
        >
          <el-form-item label="行业" prop="industry">
            <el-input clearable v-model="form.industry" placeholder="请输入行业"></el-input>
          </el-form-item>
          <el-form-item label="发现日期" prop="discoveryDate">
            <el-date-picker
              v-model="form.discoveryDate"
              type="date"
              placeholder="选择时间"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="企业名称" prop="enterpriseName">
            <el-input
              clearable
              v-model="form.enterpriseName"
              style="width: 870px"
              placeholder="请输入企业名称"
            ></el-input>
          </el-form-item>
          <el-form-item label="隐患类型" prop="dangersType">
            <el-select filterable v-model="form.dangersType" placeholder="请选择隐患类型" clearable>
              <el-option
                v-for="item in dangersTypeList"
                :value="item.dictName"
                :label="item.dictName"
                :key="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="隐患来源" prop="dangersSources">
            <el-select filterable v-model="form.dangersSources" placeholder="请选择隐患来源" clearable>
              <el-option
                v-for="item in dangersSourcesList"
                :value="item.dictName"
                :label="item.dictName"
                :key="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="隐患明细" prop="dangersDetails">
            <el-input
              clearable
              v-model="form.dangersDetails"
              style="width: 870px"
              placeholder="请输入避难场所名称"
            ></el-input>
          </el-form-item>
          <el-form-item label="整改措施" prop="rectificationMeasures">
            <el-input
              clearable
              v-model="form.rectificationMeasures"
              style="width: 870px"
              placeholder="请输入整改措施"
            ></el-input>
          </el-form-item>
          <el-form-item label="整改时限" prop="rectificationDate">
            <el-date-picker
              v-model="form.rectificationDate"
              type="date"
              placeholder="选择时间"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="监管责任部门" prop="regulatoryDepartment">
            <el-input clearable v-model="form.regulatoryDepartment" placeholder="请输入监管责任部门"></el-input>
          </el-form-item>
          <el-form-item label="整改监管责任人" prop="responsiblePerson" v-if="isCode == 0">
            <el-input clearable v-model="form.responsiblePerson" disabled placeholder="请输入整改监管责任人"></el-input>
          </el-form-item>
          <el-form-item label="责任人联系方式" prop="responsiblePersonPhone">
            <el-input clearable v-model="form.responsiblePersonPhone" placeholder="请输入责任人联系方式"></el-input>
          </el-form-item>
          <el-form-item label="整改进度" prop="rectificationProgress">
            <el-select
              filterable
              v-model="form.rectificationProgress"
              placeholder="请选择整改进度"
              popper-append-to-body
              clearable
            >
              <el-option
                v-for="item in rectificationProgressList"
                :value="item.dictName"
                :label="item.dictName"
                :key="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="是否立案" prop="isCase">
            <el-select filterable v-model="form.isCase" placeholder="请选择是否立案" clearable>
              <el-option v-for="item in isOkList" :value="item.name" :label="item.name" :key="item.name"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="罚款" prop="isFine">
            <el-select filterable v-model="form.isFine" placeholder="请选择是否罚款" clearable>
              <el-option v-for="item in isOkList" :value="item.name" :label="item.name" :key="item.name"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="是否约谈" prop="isAppointment">
            <el-select filterable v-model="form.isAppointment" placeholder="请选择是否约谈" clearable>
              <el-option v-for="item in isOkList" :value="item.name" :label="item.name" :key="item.name"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="是否曝光" prop="isExposure">
            <el-select filterable v-model="form.isExposure" placeholder="请选择是否曝光" clearable>
              <el-option v-for="item in isOkList" :value="item.name" :label="item.name" :key="item.name"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="是否停产整顿" prop="isRectification">
            <el-select filterable v-model="form.isRectification" placeholder="请选择是否停产整顿" clearable>
              <el-option v-for="item in isOkList" :value="item.name" :label="item.name" :key="item.name"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="是否关闭取缔" prop="isClose">
            <el-select filterable v-model="form.isClose" placeholder="请选择是否关闭取缔" clearable>
              <el-option v-for="item in isOkList" :value="item.name" :label="item.name" :key="item.name"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="是否移送司法机关追究刑事责任" prop="isTransfer">
            <el-select filterable v-model="form.isTransfer" placeholder="请选择是否移送司法机关追究刑事责任" clearable>
              <el-option v-for="item in isOkList" :value="item.name" :label="item.name" :key="item.name"></el-option>
            </el-select>
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
export default {
  name: 'addRiskDanger',
  components: {},
  props: ['dialogVisible', 'formInfo', 'isCode'],
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
      if (v) {
        this.form = Object.assign({}, JSON.parse(JSON.stringify(this.formInfo)))
      }
    }
  },
  mounted() {
    this.getDangersTypeList()
    this.getDangersSourcesList()
    this.getRectificationProgressList()
  },
  data() {
    const phoneCheck = (rule, value, callback) => {
      let isPhone = /^[1][3-9]{1}[0-9]{9}$/ // 手机号码
      let isMob = /^([0-9]{3,4}-)?[0-9]{7,8}$/ // 座机格式
      if (!(value === null || value === undefined || value === '')) {
        if (isMob.test(value) || isPhone.test(value)) {
          callback()
        } else {
          callback(new Error('非有效电话号码！'))
        }
      } else {
        callback()
      }
    }
    return {
      rules: {
        industry: { required: true, message: '请输入行业', trigger: 'blur' },
        enterpriseName: { required: true, message: '请输入企业名称', trigger: 'blur' },
        dangersType: { required: true, message: '请选择隐患类型', trigger: 'blur' },
        dangersDetails: { required: true, message: '请输入隐患明细', trigger: 'blur' },
        rectificationProgress: { required: true, message: '请选择整改进度', trigger: 'blur' },
        responsiblePersonPhone: [{ validator: phoneCheck, trigger: 'blur' }]
      },
      loading: false,
      isOkList: [
        { id: '1', name: '是' },
        { id: '2', name: '否' }
      ],
      form: {
        industry: '',
        discoveryDate: '',
        enterpriseName: '',
        dangersType: '',
        dangersSources: '',
        dangersDetails: '',
        rectificationMeasures: '',
        rectificationDate: '',
        regulatoryDepartment: '',
        responsiblePerson: '',
        responsiblePersonPhone: '',
        rectificationProgress: '',
        isCase: '',
        isFine: '',
        isAppointment: '',
        isExposure: '',
        isRectification: '',
        isClose: '',
        isTransfer: ''
      },
      files: [],
      imgDialogVisible: false,
      dialogImageUrl: '',
      dangersTypeList: [],
      dangersSourcesList: [],
      rectificationProgressList: []
    }
  },
  methods: {
    getRectificationProgressList() {
      request.get('/binhuapis/dict/query-condition?parentId=' + '45').then((res) => {
        if (res.data.code === 200) {
          this.rectificationProgressList = res.data.data.list
        }
      })
    },
    getDangersTypeList() {
      let url = '/binhuapis/dict/query-condition?parentId=' + '49'
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          this.dangersTypeList = res.data.data.list
        }
      })
    },
    getDangersSourcesList() {
      let url = '/binhuapis/dict/query-condition?parentId=' + '60'
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          this.dangersSourcesList = res.data.data.list
        }
      })
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
              this.reset()
              this.$emit('update:dialogVisible', false)
            }
          })
      } else {
        this.$emit('update:dialogVisible', false)
      }
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
      let newForm = {
        industry: this.form.industry,
        discoveryDate: this.form.discoveryDate,
        enterpriseName: this.form.enterpriseName,
        dangersType: this.form.dangersType,
        dangersSources: this.form.dangersSources,
        dangersDetails: this.form.dangersDetails,
        rectificationMeasures: this.form.rectificationMeasures,
        rectificationDate: this.form.rectificationDate,
        regulatoryDepartment: this.form.regulatoryDepartment,
        responsiblePerson: this.form.responsiblePerson,
        responsiblePersonPhone: this.form.responsiblePersonPhone,
        rectificationProgress: this.form.rectificationProgress,
        isCase: this.form.isCase,
        isFine: this.form.isFine,
        isAppointment: this.form.isAppointment,
        isExposure: this.form.isExposure,
        isRectification: this.form.isRectification,
        isClose: this.form.isClose,
        isTransfer: this.form.isTransfer
      }
      let url = '/binhuapis/Risk/insertSelective'
      if (this.formInfo && this.formInfo.id) {
        newForm.id = this.formInfo.id
      }
      request.post(url, newForm).then((r) => {
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
.addRiskDanger {
  .el-date-editor.el-input,
  .el-date-editor.el-input__inner {
    width: 196px;
  }
}
</style>
