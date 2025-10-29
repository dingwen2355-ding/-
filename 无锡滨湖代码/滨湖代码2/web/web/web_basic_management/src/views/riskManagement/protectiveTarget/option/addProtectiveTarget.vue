<template>
  <div class="protectiveTarget">
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
          <el-form-item label="防护名称" prop="protectionName">
            <el-input clearable v-model="form.protectionName" placeholder="请输入防护名称"></el-input>
          </el-form-item>
          <el-form-item label="防护类型" prop="protectionType">
            <el-select filterable v-model="form.protectionType" placeholder="请选择防护类型" clearable>
              <el-option
                v-for="item in protectionTypeList"
                :value="item.dictName"
                :label="item.dictName"
                :key="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="最近巡查时间" prop="lastInspection">
            <el-date-picker
              v-model="form.lastInspection"
              type="date"
              placeholder="选择时间"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="目标地址" prop="destinationAddress">
            <el-input clearable v-model="form.destinationAddress" style="width: 920px;" placeholder="请输入目标地址"></el-input>
          </el-form-item>
          <el-form-item label="管理员姓名" prop="managerName">
            <el-input clearable v-model="form.managerName" placeholder="请输入管理员姓名"></el-input>
          </el-form-item>
          <el-form-item label="管理员电话" prop="managerPhone">
            <el-input clearable v-model="form.managerPhone" placeholder="请输入管理员电话"></el-input>
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
  name: 'protectiveTarget',
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
    this.getProtectionTypeList()
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
        protectionName: [{ required: true, message: '请输入防护名称', trigger: 'blur' }],
        protectionType: [{ required: true, message: '请选择防护类型', trigger: 'blur' }],
        destinationAddress: [{ required: true, message: '请输入目标地址', trigger: 'blur' }],
        /*managerName: [{ required: true, message: '请输入管理员姓名', trigger: 'blur' }],
        managerPhone: [{ required: true, message: '请输入管理员电话', trigger: 'blur' },
        { validator: phoneCheck, trigger: 'blur' }],*/
        lastInspection: [{ required: true, message: '请选择最近巡查时间', trigger: 'blur' }]
      },
      loading: false,
      form: {
        protectionName: '',
        protectionType: '',
        lastInspection: '',
        destinationAddress: '',
        managerName: '',
        managerPhone: ''
      },
      fileList: [],
      files: [],
      imgDialogVisible: false,
      dialogImageUrl: '',
      communityList: [],
      protectionTypeList: []
    }
  },
  methods: {
    getProtectionTypeList() {
      request.get('/binhuapis/dict/query-condition?parentId=' + '65').then(res => {
        if (res.data.code === 200) {
          this.protectionTypeList = res.data.data.list
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
          return false
        }
      })
    },
    submitAll() {
      this.loading = true
      let newForm = {
        protectionName: this.form.protectionName,
        protectionType: this.form.protectionType,
        lastInspection: this.form.lastInspection,
        destinationAddress: this.form.destinationAddress,
        managerName: this.form.managerName,
        managerPhone: this.form.managerPhone
      }
      let url = ''
      if (this.formInfo && this.formInfo.id) {
        url = '/binhuapis/Protection/updateByPrimaryKeySelective'
        newForm.id = this.formInfo.id
      } else {
        url = '/binhuapis/Protection/insertSelective'
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
