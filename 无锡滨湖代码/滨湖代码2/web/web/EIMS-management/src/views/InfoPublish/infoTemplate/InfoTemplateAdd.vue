<template>
  <div v-if="dialogVisible" class="infoTemplate">
    <el-dialog :title="isCode === 0 ? '修改' : isCode === 1 ? '查看' : '新增'" :visible.sync="dialogVisible"
      width="1000px" :modal-append-to-body="true" :append-to-body="true" :before-close="handleClose">
      <div class="info_body" v-loading="loading">
        <el-form :inline="true" ref="form" :rules="rules" :model="form" label-width="110px" :disabled="isCode == 1">
          <el-form-item label="标题" prop="templateTitle" class="whole-line">
            <el-input clearable v-model="form.templateTitle" placeholder="请输入标题"></el-input>
          </el-form-item>
          <el-form-item label="类型" prop="typeSelect" placeholder="请选择类型" class="whole-line">
            <el-cascader
              v-model="form.typeSelect"
              :options="typeEmergencyList"
              :props="{ checkStrictly: true }" clearable></el-cascader>
          </el-form-item>
          <el-form-item label="制作人" prop="templateProducer" class="half-line">
            <el-input clearable v-model="form.templateProducer" placeholder="请输入制作人"></el-input>
          </el-form-item>
          <el-form-item label="制作时间" prop="produceTime" class="half-line">
            <el-date-picker
              v-model="form.produceTime" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss"
              type="datetime"
              placeholder="请输入制作时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="信息模板" prop="templateContent" class="whole-line">
            <div v-if="isCode !== 1">
              <el-tag @click="editText('发生时间')">发生时间</el-tag>
              <el-tag @click="editText('事件名称')">事件名称</el-tag>
              <el-tag @click="editText('事件详情')">事件详情</el-tag>
              <el-tag @click="editText('事件位置')">事件位置</el-tag>
            </div>
            <el-input clearable type="textarea" @blur="textAreaBlur"
                      :rows="15" v-model="form.templateContent" placeholder="请输入信息模板">
            </el-input>
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
import Axios from 'axios'
import moment from 'moment'

export default {
  name: 'InfoTemplateAdd',
  data () {
    return {
      rules: {
        templateContent: [{ required: true, message: '请输入信息模板', trigger: 'change' }],
        templateProducer: [{ required: true, message: '请输入制作者', trigger: 'change' }],
        templateTitle: [{ required: true, message: '请输入标题', trigger: 'change' }],
        typeSelect: [{ required: true, message: '请输入类型', trigger: 'change' }],
        produceTime: [{ required: true, message: '请输入制作时间', trigger: 'change' }]
      },
      loading: false,
      formDefault: {
        id: null,
        produceTime: "",
        templateContent: "",
        templateProducer: "",
        templateTitle: "",
        templateType1: "",
        templateType2: "",
        typeSelect: []
      },
      form: {
        id: null,
        produceTime: "",
        templateContent: "",
        templateProducer: "",
        templateTitle: "",
        templateType1: "",
        templateType2: "",
        typeSelect: []
      },
      isCodeDic: {
        isEdit: 0,
        isAdd: 2,
        isView: 1
      },
      blurIndex: null // 光标位置
    }
  },
  props: {
      'dialogVisible': {
        type: Boolean
      },
      'id': {
        type: Number
      },
      'isCode': {
        type: Number
      },
      'typeEmergencyList': {
        type: Array,
        default: []
      }
    },
  watch: {
    dialogVisible (v) {
      this.dialogVisible = v
      if (v) {
        this.getTemplateById(this.id)
      }
    },
    formInfo: {
      handler(v) {
      },
      deep: true
    }
  },
  mounted () {
  },
  methods: {
    textAreaBlur(e) {
      this.blurIndex = e.srcElement.selectionStart
    },
    editText(editTip) {
      let index = this.blurIndex
      let str = this.form.templateContent
      this.form.templateContent = str.slice(0, index) + "${" + editTip + "}" + str.slice(index)
    },
    getTemplateById(id) {
      if (!id) {
        this.form.templateProducer = localStorage.getItem('user')
        this.form.produceTime = moment().format('YYYY-MM-DD HH:mm:ss')
        return
      }
      let url = '/gtw/EIMS-management/commandMessage/getTemplate?id=' + id
      Axios.get(url).then(res => {
        let data = res.data.data.list || []
        this.form = Object.assign(this.form, this.formDefault, data[0])
        this.form.typeSelect = []
        if (this.form.templateType1) {
          this.form.typeSelect.push(this.form.templateType1)
        }
        if (this.form.templateType2) {
          this.form.typeSelect.push(this.form.templateType2)
        }
      })
    },
    handleClose (done) {
      if (this.isCode !== this.isCodeDic.isView) {
        this.$confirm('尚未保存，是否在离开页面前保存？', '提示', {
          distinguishCancelAndClose: true,
          confirmButtonText: '提交保存',
          cancelButtonText: '关闭窗口',
          type: 'warning'
        }).then(() => {
          this.handleOk()
        }).catch(e => {
          if (e === 'cancel') {
            this.reset()
            this.$emit('update:dialogVisible', false)
          }
        })
      } else {
        this.reset()
        this.$emit('update:dialogVisible', false)
      }
    },
    reset () {
      this.loading = false
      this.form = {
        id: null,
        produceTime: "",
        templateContent: "",
        templateProducer: "",
        templateTitle: "",
        templateType1: "",
        templateType2: "",
        typeSelect: []
      }
      this.form.typeSelect = []
    },
    handleOk () {
      this.$refs['form'].validate(valid => {
        if (valid) {
          this.submitAll()
        } else {
          this.$message.warning('检查表单输入内容的合法性！')
          console.log('error submit!!')
          return false
        }
      })
    },
    submitAll () {
      let url = ''
      if (this.isCode === this.isCodeDic.isEdit) {
        url = '/gtw/EIMS-management/commandMessage/updateTemplate'
      } else if (this.isCode === this.isCodeDic.isAdd) {
        url = '/gtw/EIMS-management/commandMessage/insertTemplate'
      }
      if (this.form.typeSelect.length > 0) {
        this.form.templateType1 = this.form.typeSelect[0]
      }
      if (this.form.typeSelect.length > 1) {
        this.form.templateType2 = this.form.typeSelect[1]
      }
      Axios.post(url, this.form).then(res => {
        if (res.data.code === 200) {
          this.$message.success('提交成功！')
          this.$emit('success')
          this.reset()
          this.$emit('update:dialogVisible', false)
        } else {
          this.$message.success('提交失败！')
        }
      }).catch(() => {
        this.$message.success('提交失败！')
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  .info_body{
    .el-form{
      .el-form-item.half-line{
        .el-form-item__content{
          width: 320px;
          .el-date-editor{
            width: 100%;
          }
        }
      }
      .el-form-item.whole-line{
        .el-form-item__content{
          width: 765px;
          .el-cascader{
            width: 100%;
          }
          div{
            .el-tag{
              cursor: pointer;
            }
          }
        }
      }
    }
  }
</style>
