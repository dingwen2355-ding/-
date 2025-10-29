<template>
  <div class="auditInfoDialog" v-if="dialogVisible">
    <el-dialog
      :title="isCode == 2 ? '查看我的信息' : '审核我的信息'"
      :visible.sync="dialogVisible"
      width="800px"
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
          style="margin-right: 35px"
          label-width="110px"
        >
          <el-form-item label="标题">
            <el-input clearable v-model="form.mesName" placeholder="请输入标题" disabled></el-input>
          </el-form-item>
          <el-form-item label="类型">
            <el-cascader
              filterable
              style="width: 100%"
              v-model="form.typeId"
              :options="options"
              placeholder="请选择类型"
              clearable
              disabled
            ></el-cascader>
          </el-form-item>
          <el-form-item label="发布方式">
            <el-select
              filterable
              clearable
              v-model="form.relId"
              placeholder="请选择发布方式"
              style="width: 100%"
              disabled
            >
              <el-option
                v-for="item in releaseWayList"
                :key="item.id"
                :label="item.relName"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="发布对象">
            <!-- 融合通信==> 通讯录    站内信=> 系统用户 -->
            <el-button
              class="searchBtn"
              type="primary"
              v-show="form.relObjectName == '站内信'"
              @click="sysUserObj()"
              disabled
              >系统用户</el-button
            >
            <el-button
              class="searchBtn"
              type="primary"
              v-show="form.relObjectName == '融合通讯'"
              @click="groupPeopleObj()"
              disabled
              >通讯录</el-button
            >
          </el-form-item>
          <el-form-item label="发布人">
            <el-input clearable v-model="form.relPerson" placeholder="请输入发布人" disabled></el-input>
          </el-form-item>
          <el-form-item label="发布时间">
            <el-date-picker
              style="width: 100%"
              v-model="form.relTime"
              value-format="yyyy-MM-dd hh:mm:ss"
              type="date"
              placeholder="请输入发布时间"
              disabled
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="内容" class="whole-line">
            <el-input
              clearable
              type="textarea"
              :rows="8"
              v-model="form.remark"
              placeholder="请输入内容"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item label="处理结果" prop="status">
            <el-radio v-model="form.status" label="2" :disabled="isCode == 2">通过</el-radio>
            <el-radio v-model="form.status" label="3" :disabled="isCode == 2">退回</el-radio>
          </el-form-item>
          <el-form-item label="处理意见" prop="auditOpinion" class="whole-line">
            <el-input
              clearable
              type="textarea"
              :disabled="isCode == 2"
              :rows="8"
              v-model="form.auditOpinion"
              placeholder="请输入处理意见"
            ></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
        <el-button type="primary" @click="handleOk('formLabel')" v-if="isCode != 2">确 定</el-button>
      </div>
    </el-dialog>

    <address-book-dialog :dialogVisible.sync="showAddressBook"></address-book-dialog>
    <system-user-dialog :dialogVisible.sync="showSystemUser"></system-user-dialog>
  </div>
</template>
<script>
import Axios from 'axios'
import addressBookDialog from '../optionComponents/MyInfoOption/addressBookDialog.vue'
import systemUserDialog from '../optionComponents/MyInfoOption/SystemUserDialog.vue'
export default {
  name: 'auditInfoDialog',
  props: ['dialogVisible', 'formInfo', 'isCode'],
  components: { addressBookDialog, systemUserDialog },
  watch: {
    dialogVisible(v) {
      // console.log(v)
      this.dialogVisible = v
      this.form = Object.assign(this.form, JSON.parse(JSON.stringify(this.formInfo)))
      console.log('this.form', this.form)
      if (this.form.status == 1) {
        // console.log('this.form.status', this.form.status)
        this.form.status = ''
      }
    }
  },
  data() {
    return {
      rules: {
        auditOpinion: [{ required: true, message: '请输入处理意见', trigger: 'blur' }],
        status: [{ required: true, message: '请选择处理结果', trigger: 'blur' }]
      },
      loading: false,
      options: [], // 类型
      releaseWayList: [], // 发布方式
      form: {
        mesName: '',
        typeId: '',
        relId: '',
        relObjectName: '',
        relPerson: '',
        relTime: '',
        remark: '',
        status: '',
        auditOpinion: ''
      },
      showAddressBook: false,
      showSystemUser: false
    }
  },
  mounted() {
    this.getTypeList()
    this.getReleaseWayList()
  },
  methods: {
    // 获取类型
    getTypeList() {
      let url = '/gtw/EIMS-management/get-mes-ype-tree'
      Axios.get(url).then((res) => {
        this.options = res.data.data
      })
    },
    // 获取发布方式
    getReleaseWayList() {
      let url = '/gtw/EIMS-management/get-mes-rel'
      Axios.get(url).then((res) => {
        this.releaseWayList = res.data.data.list
      })
    },
    // 系统用户
    sysUserObj() {
      this.showSystemUser = true
    },
    // 通讯录
    groupPeopleObj() {
      this.showAddressBook = true
    },
    // 取 消
    handleClose() {
      this.form = {
        mesName: '',
        typeId: '',
        relId: '',
        relObjectName: '',
        relPerson: '',
        relTime: '',
        remark: '',
        status: '',
        auditOpinion: ''
      }
      this.$emit('update:dialogVisible', false)
    },
    handleOk(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // console.log('确定提交', this.form)
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
      let params = {
        id: this.formInfo.id, // 信息id
        status: this.form.status, // 提交审核：2：通过 3：退回
        auditorId: localStorage.getItem('userId'), // 审核人：仅审核操作需要传值
        auditOpinion: this.form.auditOpinion, // 审核意见：仅审核操作需要传值
        userId: localStorage.getItem('userId'),
        mesName: this.form.mesName
      }
      let url = '/gtw/EIMS-management/audit-mes-info'
      Axios.post(url, params)
        .then((res) => {
          this.loading = false
          if (res.data.code == 200) {
            this.$message.success('审核成功')
            this.$emit('success')
            this.$emit('update:dialogVisible', false)
          } else {
            this.$message.error('审核失败')
          }
        })
        .catch(() => {
          this.$message.error('审核失败')
        })
    }
  }
}
</script>
<style lang="scss">
.auditInfoDialog {
  .info_body {
    .whole-line {
      width: 100%;
      .el-form-item__content {
        width: calc(100% - 150px);
      }
    }
  }
}
</style>
