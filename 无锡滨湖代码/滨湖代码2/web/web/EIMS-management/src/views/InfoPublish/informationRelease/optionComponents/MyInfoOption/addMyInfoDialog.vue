<template>
  <div class="addMyInfoDialog" v-if="dialogVisible">
    <el-dialog
      :title="isCode === 0 ? '编辑我的草稿' : '新增我的草稿'"
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
          <el-form-item label="标题" prop="mesName">
            <el-input clearable v-model="form.mesName" placeholder="请输入标题"></el-input>
          </el-form-item>
          <el-form-item label="类型" prop="typeId">
            <el-cascader filterable v-model="form.typeId" :options="options" placeholder="请选择类型" clearable></el-cascader>
          </el-form-item>
          <el-form-item label="发布方式" prop="jsonObject">
            <!-- <el-form-item label="发布方式" prop="relId">
            <el-select filterable clearable v-model="form.relId" placeholder="请选择发布方式" style="width: 100%">
               <el-option
                v-for="item in releaseWayList"
                :key="item.id"
                :label="item.relName"
                :value="item.id"
              ></el-option>
            </el-select>-->
            <el-select
              filterable
              clearable
              value-key="id"
              v-model="form.jsonObject"
              placeholder="请选择发布方式"
              style="width: 100%"
            >
              <el-option v-for="item in releaseWayList" :key="item.id" :label="item.relName" :value="item"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="发布对象" prop="relObjectIds">
            <!-- 融合通信==> 通讯录    站内信=> 系统用户 -->
            <el-button
              class="searchBtn"
              type="primary"
              v-show="form.jsonObject.typeName == '站内信'"
              @click="sysUserObj()"
            >系统用户</el-button>
            <el-button
              class="searchBtn"
              type="primary"
              v-show="form.jsonObject.typeName == '融合通讯'"
              @click="groupPeopleObj()"
            >通讯录</el-button>
          </el-form-item>
          <el-form-item label="发布人" prop="relPerson">
            <el-input clearable v-model="form.relPerson" placeholder="请输入发布人"></el-input>
          </el-form-item>
          <el-form-item label="发布时间" prop="relTime">
            <el-date-picker
              style="width: 100%"
              v-model="form.relTime"
              value-format="yyyy-MM-dd hh:mm:ss"
              type="date"
              placeholder="请输入发布时间"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="内容" prop="remark" class="whole-line">
            <el-input clearable type="textarea" :rows="8" v-model="form.remark" placeholder="请输入内容"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
        <el-button type="primary" @click="handleOk">确 定</el-button>
      </div>
    </el-dialog>
    <address-book-dialog
      :dialogVisible.sync="showAddressBook"
      :getChooseGroupMemberId.sync="getChooseGroupMemberId"
      @success="getChooseGroupMemberIdSuccess"
    ></address-book-dialog>
    <system-user-dialog
      :dialogVisible.sync="showSystemUser"
      :getChooseSysUserId.sync="getChooseSysUserId"
      @success="getChooseGroupMemberIdSuccess"
    ></system-user-dialog>
  </div>
</template>
<script>
import Axios from 'axios'
import addressBookDialog from '../MyInfoOption/addressBookDialog.vue'
import systemUserDialog from '../MyInfoOption/SystemUserDialog.vue'
export default {
  name: 'addMyInfoDialog',
  props: ['dialogVisible', 'formInfo', 'isCode'],
  components: { addressBookDialog, systemUserDialog },
  watch: {
    dialogVisible(v) {
      // console.log('dialogVisible', v)
      this.dialogVisible = v
      this.getTypeList()
      this.getReleaseWayList()
      this.form = Object.assign(this.form, JSON.parse(JSON.stringify(this.formInfo)))
      // console.log('this.form', this.form)
      // console.log('this.form.jsonObject', this.form.jsonObject)
      this.form.relObjectIds = this.formInfo.personIds
      console.log(this.form.relObjectIds, ' this.form.relObjectIds ')
      if (this.form.relObjectIds) {
        if (this.form.jsonObject.typeName === '站内信') {
          this.getChooseSysUserId = this.form.relObjectIds
        } else {
          this.getChooseGroupMemberId = this.form.relObjectIds
        }
      }
      if (this.form.relPerson === '') {
        this.form.relPerson = localStorage.getItem('user')
      }
      // console.log('this.form.relObjectIds', this.form.relObjectIds)
    }
  },
  data() {
    return {
      rules: {
        mesName: [{ required: true, message: '请输入标题', trigger: 'blur' }],
        typeId: [{ required: true, message: '请选择类型', trigger: 'blur' }],
        // relId: [{ required: true, message: '请选择发布方式', trigger: 'blur' }],
        jsonObject: [{ required: true, message: '请选择发布方式', trigger: 'blur' }],
        relObjectIds: [{ required: true, message: '请点击此处选择发布对象', trigger: 'blur' }],
        // relPerson: [{ required: true, message: '请输入发布人', trigger: 'blur' }],
        relTime: [{ required: true, message: '请输入发布时间', trigger: 'blur' }],
        remark: [{ required: true, message: '请输入内容', trigger: 'blur' }]
      },
      loading: false,
      options: [], // 类型
      releaseWayList: [], // 发布方式

      form: {
        mesName: '',
        typeId: '',
        jsonObject: {},
        // relId: '',
        relObjectIds: [],
        allPersonIds: '',
        relPerson: '',
        relTime: '',
        remark: ''
      },
      showAddressBook: false,
      showSystemUser: false,
      getChooseGroupMemberId: [],
      getChooseSysUserId: []
    }
  },
  mounted() { },
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
      if (this.form.relObjectIds) {
        this.getChooseSysUserId = [...this.form.relObjectIds]
      }
      this.showSystemUser = true
    },
    getChooseSysUserIdSuccess(ids) {
      this.form.relObjectIds = JSON.parse(JSON.stringify(ids))
      this.getChooseSysUserId = []
      // console.log('勾选系统用户：', this.form.relObjectIds)
    },
    // 通讯录
    groupPeopleObj() {
      if (this.form.relObjectIds) {
        this.getChooseGroupMemberId = [...this.form.relObjectIds]
      }
      this.showAddressBook = true
    },
    getChooseGroupMemberIdSuccess(ids) {
      console.log(ids, 'ids');
      this.form.relObjectIds = JSON.parse(JSON.stringify(ids))
      this.getChooseGroupMemberId = []
      // console.log('勾选通讯录：', this.form.relObjectIds)
    },
    handleClose() {
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
    },
    reset() {
      this.loading = false
      this.form = {
        mesName: '',
        typeId: '',
        jsonObject: {},
        // relId: '',
        allPersonIds: '',
        relObjectIds: [],
        relPerson: '',
        relTime: '',
        remark: ''
      }
    },
    handleOk() {
      this.$refs.formLabel.validate((valid) => {
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
      // console.log('this.form', this.form)
      let url = ''
      let params = {
        mesName: this.form.mesName,
        typeId: '',
        relId: '', // 发布方式id
        relObjectId: '', // 发布对象id  1/2 系统用户/通讯录
        relTime: this.form.relTime, // 发布时间，若不传，默认当前时间
        relPerson: this.form.relPerson, // 发布人，若不传，默认当前创建人
        remark: this.form.remark,
        personIds: []
      }
      if (Array.isArray(this.form.typeId)) {
        params.typeId = this.form.typeId.join(',')
      }
      if (this.form.jsonObject) {
        params.relId = this.form.jsonObject.id
        if (this.form.jsonObject.typeName == '融合通讯') {
          params.relObjectId = '1'
        } else if (this.form.jsonObject.typeName == '站内信') {
          params.relObjectId = '2'
        }
      }
      if (this.formInfo && this.formInfo.id) {
        url = '/gtw/EIMS-management/edit-mes-info'
        params.id = this.formInfo.id
        // console.log('this.form.jsonObject.id', this.form.jsonObject.id)
        // console.log('this.formInfo.jsonObject.id', this.formInfo.jsonObject.id)
        if (this.form.jsonObject.id == this.formInfo.jsonObject.id) {
          // console.log('this.form.allPersonIds', this.form.allPersonIds)
          if (this.form.allPersonIds) {
            this.form.allPersonIds = this.form.allPersonIds.split(',')
            // console.log('this.form.allPersonIds', this.form.allPersonIds)
            this.form.relObjectIds = this.form.relObjectIds.concat(this.form.allPersonIds)
            // console.log('this.form.relObjectIds', this.form.relObjectIds)
            params.personIds = this.form.relObjectIds
          }
        } else {
          params.personIds = this.form.relObjectIds
        }
      } else {
        url = '/gtw/EIMS-management/add-mes-info'
        params.createId = localStorage.getItem('userId') // 创建人
        params.userId = localStorage.getItem('userId') // 创建人
        params.personIds = this.form.relObjectIds
      }
      // console.log('参数', params, url)
      Axios.post(url, params)
        .then((res) => {
          this.loading = false
          if (res.data.code == 200) {
            if (this.formInfo && this.formInfo.id) {
              this.$message.success('我的草稿修改成功！')
            } else {
              this.$message.success('我的草稿新增成功！')
            }
            this.$emit('success')
            this.reset()
            this.$emit('update:dialogVisible', false)
          } else {
            if (this.form && this.form.id) {
              this.$message.success('我的草稿修改失败！')
            } else {
              this.$message.success('我的草稿新增失败！')
            }
          }
        })
        .catch(() => {
          this.loading = false
          if (this.form && this.form.id) {
            this.$message.success('我的草稿修改失败！')
          } else {
            this.$message.success('我的草稿新增失败！')
          }
        })
    }
  }
}
</script>
<style lang="scss">
.addMyInfoDialog {
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
