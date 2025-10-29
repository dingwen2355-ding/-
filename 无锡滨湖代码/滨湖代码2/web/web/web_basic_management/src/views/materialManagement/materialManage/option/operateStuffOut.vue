<template>
  <div class="operateStuffOut">
    <el-dialog
      :title="form.type === 1 ? '入库' : '出库'"
      :visible.sync="dialogVisible"
      width="1100px"
      :modal-append-to-body="false"
      :append-to-body="false"
      :before-close="handleClose"
    >
      <div class="info_body" v-loading="loading">
        <el-form :inline="true" ref="formLabel" :rules="rules" :model="form" label-width="100px">
          <div class="info-area">
            <span class="info-title">基础信息</span>
          </div>
          <el-form-item label="所属社区：" size="large">
            <el-input v-model="form.regionName" placeholder="请输入所属社区" disabled></el-input>
          </el-form-item>
          <el-form-item label="负责人员：" size="large">
            <el-input v-model="form.dutyPerson" placeholder="请输入负责人员" disabled></el-input>
          </el-form-item>
          <el-form-item label="联系方式：" size="large">
            <el-input v-model="form.dutyPhone" placeholder="负责人员联系方式" disabled></el-input>
          </el-form-item>
          <el-form-item label="仓库名称：" size="large">
            <el-input
              v-model="form.address"
              type="textarea"
              :rows="2"
              placeholder="请输入仓库名称"
              style="width: 510px"
              disabled
            ></el-input>
          </el-form-item>
          <div class="info-area">
            <div class="info-title">出库人信息</div>
          </div>
          <el-form-item label="出库人员：" size="large" prop="info">
            <el-autocomplete
              clearable
              class="inline-input"
              v-model="form.info"
              :fetch-suggestions="querySearch"
              placeholder="请输入出库人员"
              @select="handleSelect"
            ></el-autocomplete>
          </el-form-item>
          <el-form-item label="身份证号：" size="large" prop="idNumber">
            <el-input clearable v-model="form.idNumber" placeholder="请输入身份证号"></el-input>
          </el-form-item>
          <el-form-item label="联系方式：" size="large" prop="phone">
            <el-input clearable v-model="form.phone" placeholder="请输入联系方式"></el-input>
          </el-form-item>
          <!--                <el-form-item label="身份证号：" size="large" prop="idNumber">
                  <el-input clearable v-model="form.idNumber" placeholder="请输入身份证号"></el-input>
          </el-form-item>-->
          <el-form-item label="备注：" size="large" prop="note">
            <el-input clearable v-model="form.note" placeholder="请输入备注"></el-input>
          </el-form-item>
          <div class="info-area">
            <div class="info-title">出库物信息</div>
          </div>
          <el-form-item label="出库日期：" size="large" prop="createTime">
            <el-date-picker
              v-model="form.createTime"
              type="date"
              value-format="yyyy-MM-dd HH:mm:ss"
              placeholder="请选择出库日期"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="物资名称：" size="large" prop="stuffName">
            <el-input clearable v-model="form.stuffName" placeholder="请输入物资名称"></el-input>
          </el-form-item>
          <el-form-item label="出库数量：" size="large" prop="num">
            <el-input-number
              v-model="form.num"
              :min="1"
              :max="form.stuffNum"
              label="请输入出库数量"
            ></el-input-number>
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
  name: 'operateStuffOut',
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
    return {
      size: '',
      rules: {
        outboundType: [{ required: true, message: '请选择出库类别', trigger: 'blur' }],
        info: [{ required: true, message: '请选择出库人员', trigger: 'blur' }],
        phone: [{ required: true, message: '请输入联系方式', trigger: 'blur' }],
        // idNumber: [{ required: true, message: '请输入身份证号', trigger: 'blur' }],
        note: [{ required: true, message: '请选择备注', trigger: 'blur' }],
        createTime: [{ required: true, message: '请选择出库日期', trigger: 'blur' }],
        stuffName: [{ required: true, message: '请选择物资名称', trigger: 'blur' }],
        num: [{ required: true, message: '请选择出库数量', trigger: 'blur' }]
      },
      loading: false,
      form: {
        stuffName: '',
        address: '',
        stuffNum: '',
        stuffType: '',
        dutyPerson: '',
        dutyPhone: '',
        region: '',
        type: '2',
        rsId: '',
        num: '',
        info: '',
        note: '',
        createTime: '',
        outBoundType: '',
        phone: '',
        idNumber: ''
      },
      sitesList: [],
      outTypeList: []
    }
  },
  mounted() {
    this.getOutType()
  },
  methods: {
    getOutType() {
      let url = '/binhuapis/dict/query-condition?parentId=23'
      request.get(url).then(res => {
        this.outTypeList = res.data.data.list
      })
    },
    handleSelect(item) {
      this.form.info = item.name
      this.form.phone = item.phone
      this.form.idNumber = item.idNumber
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
      this.doInOrOut()
    },
    doInOrOut() {
      this.loading = true
      let newForm = {
        rsId: this.form.rsId,
        type: 2,
        num: this.form.num,
        info: this.form.info,
        note: this.form.note,
        createTime: this.form.createTime,
        phone: this.form.phone,
        idNumber: this.form.idNumber
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
.operateStuffOut {
  .info-area {
    width: 100%;
    border-bottom: 1px solid #eeeef0;
    height: 40px;
    .info-title {
      padding-top: 10px;
      font-size: 18px;
      padding-bottom: 20px;
      padding-left: 10px;
      padding-right: 10px;
    }
    margin-bottom: 20px;
  }
  ::v-deep .el-input__inner,
  .el-input--medium .el-input__inner,
  .el-input--suffix .el-input__inner {
    height: 40px;
    line-height: 40px;
  }
}
</style>
