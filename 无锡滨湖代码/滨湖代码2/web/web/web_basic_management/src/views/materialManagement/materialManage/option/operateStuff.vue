<template>
  <div class="operateStuff">
    <el-dialog
      :title="form.type === 1 ? '入库' : '出库'"
      :visible.sync="dialogVisible"
      width="1100px"
      :modal-append-to-body="false"
      :append-to-body="false"
      :before-close="handleClose"
    >
      <div class="info_body" v-loading="loading">
        <el-form :inline="true" ref="formLabel" :rules="rules" :model="form" label-width="80px">
          <el-form-item label="类型" size="large" prop="type" v-show="false">
            <el-input clearable v-model="form.type" placeholder="请输入类型"></el-input>
          </el-form-item>
          <el-form-item label="数量" prop="num">
            <el-input clearable v-model="form.num" style="width: 405px" type="number" placeholder="请输入数量"></el-input>
          </el-form-item>
          <el-form-item :label="form.type === 1 ? '物资来源' : '领用人员'" prop="stuffModel">
            <el-input
              clearable
              v-model="form.stuffModel"
              style="width: 405px"
              :placeholder="form.type === 1 ? '请输入物资来源' : '请输入领用人员'"
            ></el-input>
          </el-form-item>
          <el-form-item label="备注" prop="note" style="width: 100%">
            <el-input type="textarea" style="width: 900px" clearable v-model="form.typeName" placeholder="请输入备注"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
        <el-button type="primary" @click="handleOk">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import request from '@/utils/request'
export default {
  name: 'operateStuff',
  props: ['dialogVisible', 'formInfo', 'isCode'],
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
      if (v) {
        this.form = Object.assign({}, JSON.parse(JSON.stringify(this.formInfo)))
        console.log('新增编辑查看this.form', this.form)
      }
    }
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
        num: [{ required: true, message: '请完善数据', trigger: 'blur' }],
        stuffModel: [{ required: true, message: '请完善数据', trigger: 'blur' }]
      },
      loading: false,
      form: {
        rsId: '',
        type: '',
        num: '',
        info: '',
        note: ''
      },
      sitesList: []
    }
  },
  mounted() {
  },
  methods: {
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
      this.doInOrOut()
    },
    doInOrOut() {
      this.loading = true
      let newForm = {
        rsId: this.form.rsId,
        type: this.form.type,
        num: this.form.num,
        info: this.form.info,
        note: this.form.note
      }
      let url = '/binhuapis/resourceStuff/operate'
      request.post(url, newForm).then((r) => {
        this.loading = false
        if (r.data.code === 200) {
          if (this.form.type === 1) {
            this.$message.success('入库成功！')
          } else {
            this.$message.success('出库成功！')
          }
          this.$emit('success')
          this.reset()
          this.$emit('update:dialogVisible', false)
        } else {
          this.$message.error(r.data.message)
        }
      })
    },
    getSites() {
      let url = '/binhuapis/siteManage/query'
      request.get(url).then((res) => {
        this.sitesList = res.data.data.list.map(t => {
          t.id = t.id + ''
          return t
        })
      })
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
