<template>
  <div class="addSiteManage">
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
          <el-form-item label="法律名称" prop="lawName">
            <el-input clearable v-model="form.lawName" style="width: 920px;" placeholder="请输入法律名称"></el-input>
          </el-form-item>
          <el-form-item label="法规分类" prop="lawClass">
            <el-input clearable v-model="form.lawClass" style="width: 920px;" placeholder="请输入法规分类"></el-input>
          </el-form-item>
          <el-form-item label="违法行为描述" prop="illegalDescription">
            <el-input
              clearable
              type="textarea"
              :rows="2"
              v-model="form.illegalDescription"
              style="width: 920px;"
              placeholder="请输入违法行为描述"
            ></el-input>
          </el-form-item>
          <el-form-item label="法律规定" prop="legalProvisions">
            <el-input
              clearable
              type="textarea"
              :rows="2"
              v-model="form.legalProvisions"
              style="width: 920px;"
              placeholder="请输入法律规定"
            ></el-input>
          </el-form-item>
          <el-form-item label="规定条款" prop="prescribedTerms">
            <el-input clearable v-model="form.prescribedTerms" style="width: 920px;" placeholder="请输入规定条款"></el-input>
          </el-form-item>
          <el-form-item label="处罚依据条款" prop="penaltyBasisClause">
            <el-input clearable v-model="form.penaltyBasisClause" style="width: 920px;" placeholder="请输入处罚依据条款"></el-input>
          </el-form-item>
          <el-form-item label="处罚依据名称" prop="penaltyBasisName">
            <el-input
              clearable
              type="textarea"
              :rows="2"
              v-model="form.penaltyBasisName"
              style="width: 920px;"
              placeholder="请输入处罚依据名称"
            ></el-input>
          </el-form-item>
          <el-form-item label="处罚依据" prop="penaltyBasis">
            <el-input
              clearable
              type="textarea"
              :rows="2"
              v-model="form.penaltyBasis"
              style="width: 920px;"
              placeholder="请输入处罚依据"
            ></el-input>
          </el-form-item>
          <el-form-item label="实施标准" prop="standards">
            <el-input
              clearable
              type="textarea"
              :rows="2"
              v-model="form.standards"
              style="width: 920px;"
              placeholder="请输入实施标准"
            ></el-input>
          </el-form-item>
          <el-form-item label="事故类型" prop="accidentType">
            <el-select filterable v-model="form.accidentType" placeholder="请选择事故类型" popper-append-to-body clearable>
              <el-option v-for="item in accidentTypeList" :value="item.dictName" :label="item.dictName" :key="item.id"></el-option>
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
  name: 'addSiteManage',
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
    this.getAccidentTypeList()
  },
  data() {
    return {
      rules: {
        lawName: [{ required: true, message: '请输入法律名称', trigger: 'blur' }],
        lawClass: [{ required: true, message: '请输入法规分类', trigger: 'blur' }],
        illegalDescription: [{ required: true, message: '请输入违法行为描述', trigger: 'blur' }],
        legalProvisions: [{ required: true, message: '请输入法律规定', trigger: 'blur' }],
        prescribedTerms: [{ required: true, message: '请输入规定条款', trigger: 'blur' }],
        penaltyBasisClause: [{ required: true, message: '请输入处罚依据条款', trigger: 'blur' }],
        penaltyBasisName: [{ required: true, message: '请输入处罚依据名称', trigger: 'blur' }],
        penaltyBasis: [{ required: true, message: '请输入处罚依据', trigger: 'blur' }],
        standards: [{ required: true, message: '请输入实施标准', trigger: 'blur' }],
        accidentType: [{ required: true, message: '请输入事故类型', trigger: 'blur' }]
      },
      loading: false,
      form: {
        lawName: '',
        lawClass: '',
        illegalDescription: '',
        legalProvisions: '',
        prescribedTerms: '',
        penaltyBasisClause: '',
        penaltyBasisName: '',
        penaltyBasis: '',
        standards: '',
        accidentType: ''
      },
      accidentTypeList: []
    }
  },
  methods: {
    getAccidentTypeList() {
      request.get('/binhuapis/dict/query-condition?parentId=' + '102').then(res => {
        if (res.data.code === 200) {
          this.accidentTypeList = res.data.data.list
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
        lawName: this.form.lawName,
        lawClass: this.form.lawClass,
        illegalDescription: this.form.illegalDescription,
        legalProvisions: this.form.legalProvisions,
        prescribedTerms: this.form.prescribedTerms,
        penaltyBasisClause: this.form.penaltyBasisClause,
        penaltyBasisName: this.form.penaltyBasisName,
        penaltyBasis: this.form.penaltyBasis,
        standards: this.form.standards,
        accidentType: this.form.accidentType
      }
      let url = ''
      if (this.formInfo && this.formInfo.id) {
        url = '/binhuapis/Law/updateByPrimaryKeySelective'
        newForm.id = this.formInfo.id
      } else {
        url = '/binhuapis/Law/insertSelective'
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
<style lang="scss" scoped>
// .addSiteManage{}
</style>
