<template>
  <el-dialog
    :title="isCode === 0 ? '新增' : isCode === 1 ? '编辑' : '查看'"
    :visible.sync="dialogVisible"
    width="600px"
    :modal-append-to-body="false"
    :append-to-body="true"
    :before-close="handleClose"
    class="DictionaryListAdd"
  >
    <div class="info_body">
      <el-form :inline="true" ref="form" :rules="rules" :model="form" label-width="110px">
        <el-form-item label="字典标签" prop="standard">
          <el-input
            clearable
            v-model="form.standard"
            placeholder="请输入字典标签"
            :disabled="isCode === 2"
          ></el-input>
        </el-form-item>
        <el-form-item label="字典键值" prop="checkType">
          <el-input
            clearable
            v-model="form.checkType"
            placeholder="请输入字典键值"
            :disabled="isCode === 2"
          ></el-input>
        </el-form-item>
        <el-form-item label="字典类型" prop="standard">
          <el-input
            clearable
            v-model="form.standard"
            placeholder="请输入字典类型"
            :disabled="isCode === 2"
          ></el-input>
        </el-form-item>
        <el-form-item label="样式属性" prop="checkType">
          <el-input
            clearable
            v-model="form.checkType"
            placeholder="请输入样式属性"
            :disabled="isCode === 2"
          ></el-input>
        </el-form-item>
        <el-form-item label="字典排序" prop="standard">
          <el-input
            clearable
            v-model="form.standard"
            placeholder="请输入字典排序"
            :disabled="isCode === 2"
          ></el-input>
        </el-form-item>
        <el-form-item label="数据状态">
          <el-select v-model="form.operateModule" filterable clearable placeholder="请选择数据状态">
            <el-option
              v-for="item in operateModules"
              :key="item.index"
              :label="item.operateModule"
              :value="item.operateModule"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="系统默认">
          <el-radio-group v-model="form.radio">
            <el-radio :label="0">是</el-radio>
            <el-radio :label="1">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.radio">
            <el-radio :label="0">正常</el-radio>
            <el-radio :label="1">停用</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="备注">
          <el-input
            type="textarea"
            :autosize="{ minRows: 4, maxRows: 6 }"
            clearable
            v-model="form.producer"
            placeholder="请输入备注"
            :disabled="isCode === 2"
          ></el-input>
        </el-form-item>
      </el-form>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button @click="handleClose()">取 消</el-button>
      <el-button type="primary" @click="handleOk" v-if="isCode !== 2">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import axios from '@/utils/request'
export default {
  name: 'DangerTroubleshootingChecklistAdd',
  components: {},
  props: ['dialogVisible', 'formInfo', 'isCode'],
  data() {
    return {
      form: {
        standard: '',
        producer: '',
        checkType: '',
        radio: 0,
        operateModule: ''
      },
      rules: {
        standard: [{ required: true, message: '请输入标准', trigger: 'blur' }],
        checkType: [{ required: true, message: '请输入', trigger: 'blur' }]
      },
      operateModules: [
        { index: 1, operateModule: '所有' },
        { index: 2, operateModule: '正常' },
        { index: 3, operateModule: '停用' }
      ]
    }
  },
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
    },
    formInfo(v) {
      if (v && this.dialogVisible) {
        let forms = JSON.parse(JSON.stringify(v))
        if (forms.content) {
          this.contentList = forms.content.split(';').map((t) => {
            return {
              content: t
            }
          })
        }
        if (forms.standard) {
          this.form.standard = forms.standard
        }
        if (forms.riskLevel) {
          this.form.riskLevel = forms.riskLevel
        }
        if (forms.producer) {
          this.form.producer = forms.producer
        }
        if (forms.checkType) {
          this.form.checkType = forms.checkType
        }
        if (forms.riskPoint) {
          this.form.riskPoint = forms.riskPoint
        }
        if (forms.id) {
          this.form.id = forms.id
        }
      }
    }
  },
  methods: {
    handleClose(done) {
      this.reset()
      this.$emit('update:dialogVisible', false)
    },
    reset() {
      this.form = {
        standard: '',
        producer: '',
        checkType: '',
        radio: 0
      }
    },
    handleOk() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          let isShow = false
          this.contentList.forEach((v) => {
            if (!v.content) {
              isShow = true
            }
          })
          if (isShow) {
            this.$notify({
              title: '警告',
              message: '请检查隐患排查内容是否填写完整！！',
              type: 'warning',
              duration: 1000
            })
          } else {
            let params = {}
            if (this.contentList && this.contentList.length > 0) {
              params.content = this.contentList
                .map((v) => {
                  return v.content
                })
                .join(';')
            }
            params.status = '暂存'
            for (let key in this.form) {
              if (this.form[key]) params[key] = this.form[key]
            }
            let url = ''
            if (this.formInfo) {
              url = '/community/hiddenLevel/updateStandardInspection'
              axios.post(url, params).then((r) => {
                if (r.data.code === 200) {
                  this.$notify({
                    title: '成功',
                    message: '操作成功',
                    type: 'success',
                    duration: 1000
                  })
                  this.$emit('success')
                  this.reset()
                  this.$emit('update:dialogVisible', false)
                } else {
                  this.$notify.error({
                    title: '错误',
                    message: r.data.message,
                    duration: 1000
                  })
                }
              })
            } else {
              url = '/community/hiddenLevel/insertStandardInspection'
              axios.post(url, params).then((r) => {
                if (r.data.code === 200) {
                  this.$notify({
                    title: '成功',
                    message: '操作成功',
                    type: 'success',
                    duration: 1000
                  })
                  this.$emit('success')
                  this.reset()
                  this.$emit('update:dialogVisible', false)
                } else {
                  this.$notify.error({
                    title: '错误',
                    message: r.data.message,
                    duration: 1000
                  })
                }
              })
            }
          }
        } else {
          this.$notify({
            title: '警告',
            message: '请检查表单的数据合法性！！',
            type: 'warning',
            duration: 1000
          })
          return false
        }
      })
    },
    getChooseData(arr) {
      this.form.checkType = arr.fistName
      this.form.riskLevel = arr.riskLevel
    },
    handleClose1() {
      this.addDialog = false
    },
    add() {
      let obj = {
        content: ''
      }

      this.contentList.push(obj)
    },
    deleteA(index) {
      if (this.contentList && this.contentList.length === 1) {
        this.$notify({
          title: '警告',
          message: '不可删除！',
          type: 'warning',
          duration: 1000
        })
      } else {
        this.contentList.splice(index, 1)
      }
    }
  }
}
</script>

<style lang="scss">
.DictionaryListAdd {
  .el-form--inline {
    .el-select {
      display: block;
    }
    .el-form-item__content {
      width: 400px;
    }
    .whole-line {
      width: 100%;

      .el-form-item__content {
        width: 585px;
      }
    }
  }
}
</style>
