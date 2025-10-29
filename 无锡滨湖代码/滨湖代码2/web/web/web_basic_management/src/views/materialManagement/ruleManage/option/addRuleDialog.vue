<template>
  <div class="addRuleDialog">
    <el-dialog
      :title="isCode == 0 ? '修改' : isCode === 1 ? '查看' : '新增'"
      :visible.sync="dialogVisible"
      width="950px"
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
          label-width="100px"
          :disabled="isCode == 1"
        >
          <el-form-item label="预警名称" prop="ruleName">
            <el-input clearable v-model="form.ruleName" style="width: 770px" placeholder="请输入预警名称"></el-input>
          </el-form-item>
          <el-form-item label="预警类型" prop="type">
            <el-select filterable v-model="form.type" placeholder="请选择预警类型" clearable>
              <el-option v-for="item in wariningList" :value="item.id" :label="item.name" :key="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="物资类型" prop="stuffType">
            <el-select filterable v-model="form.stuffType" placeholder="请选择物资类型" clearable>
              <el-option v-for="item in kindList" :value="item.id" :label="item.dictName" :key="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="规则状态" prop="status">
            <el-switch v-model="form.status" active-value="1" inactive-value="0" style="width: 100px;"></el-switch>
          </el-form-item>
          <el-form-item label="预警提醒" prop="remind">
            <div v-if="isCode !== 1">
              <el-tag @click="editText('社区')">社区</el-tag>
              <el-tag @click="editText('场所')">场所</el-tag>
              <el-tag @click="editText('物资')">物资</el-tag>
            </div>
            <el-input
              style="width: 770px"
              clearable
              type="textarea"
              @blur="textAreaBlur"
              :rows="5"
              v-model="form.remind"
              placeholder="例子：经数据分析发现{社区}的{场所}的{物资}不足，请负责人及时处理！"
            ></el-input>
            <!-- <el-select v-model="form.remind" placeholder="请选择类型" clearable>
                            <el-option v-for="item in typeList" :value="item.id" :label="item.name" :key="item.id"></el-option>
            </el-select>-->
          </el-form-item>
          <el-form-item label="触发频率" prop="threshold">
            <el-input
              clearable
              type="number"
              min="0"
              v-model="form.threshold"
              style="width: 160px;"
              placeholder="请输入触发频率"
            ></el-input>
          </el-form-item>
          <el-form-item label="修改人员" prop="reviser">
            <el-input clearable v-model="form.reviser" placeholder="请输入修改人员" style="width: 160px;"></el-input>
          </el-form-item>
          <el-form-item label="时间" prop="createTime">
            <el-date-picker
              v-model="form.createTime"
              style="width: 100%"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
              type="datetime"
              placeholder="请选择时间"
            ></el-date-picker>
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
  name: 'addRuleDialog',
  props: ['dialogVisible', 'formInfo', 'isCode'],
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
      if (v) {
        this.getKindList()
        this.form = Object.assign({}, JSON.parse(JSON.stringify(this.formInfo)))
        console.log('新增编辑查看this.form', this.form)
      }
    }
  },
  data() {
    return {
      rules: {
        ruleName: [{ required: true, message: '请输入预警名称', trigger: 'blur' }],
        type: [{ required: true, message: '请选择预警类型', trigger: 'blur' }],
        remind: [{ required: true, message: '请输入预警提醒', trigger: 'blur' }],
        threshold: [{ required: true, message: '请输入触发频率', trigger: 'blur' }],
        stuffType: [{ required: true, message: '请选择物资类型', trigger: 'blur' }],
        status: [{ required: true, message: '请选择规则状态', trigger: 'blur' }],
        reviser: [{ required: true, message: '请输入修改人员', trigger: 'blur' }],
        createTime: [{ required: true, message: '请输入时间', trigger: 'blur' }]
      },
      loading: false,
      kindList: [],
      wariningList: [
        { id: '1', name: '保质期预警' },
        { id: '2', name: '库存数量预警' }
      ],
      form: {
        ruleName: '',
        type: '',
        remind: '',
        threshold: '',
        stuffType: '',
        status: '',
        reviser: '',
        createTime: ''
      },

      blurIndex: null // 光标位置

    }
  },
  methods: {
    getKindList() {
      let url = '/binhuapis/dict/query-condition?parentId=1'
      request.get(url).then(res => {
        this.kindList = res.data.data.list
      })
    },
    textAreaBlur(e) {
      this.blurIndex = e.srcElement.selectionStart
    },
    editText(editTip) {
      let index = this.blurIndex
      let str = this.form.remind
      this.form.remind = str.slice(0, index) + '{' + editTip + '}' + str.slice(index)
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
        ruleName: this.form.ruleName,
        remind: this.form.remind,
        type: this.form.type,
        stuffType: this.form.stuffType,
        threshold: this.form.threshold,
        status: this.form.status,
        reviser: this.form.reviser,
        createTime: this.form.createTime
      }
      let url = ''
      if (this.formInfo && this.formInfo.id) {
        url = '/binhuapis/resourceRule/edit'
        newForm.id = this.formInfo.id
        // console.log('参数: ', newForm, 'url: ', url)
      } else {
        url = '/binhuapis/resourceRule/add'
        // console.log('参数: ', newForm, 'url: ', url)
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
.addRuleDialog {
  .el-tag {
    cursor: pointer;
    margin: 5px;
  }
}
</style>
