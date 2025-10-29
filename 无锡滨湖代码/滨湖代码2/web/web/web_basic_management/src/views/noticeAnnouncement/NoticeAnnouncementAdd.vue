<template>
  <el-dialog
    :title="isCode === 0 ? '新增' : isCode === 1 ? '编辑' : '查看'"
    :visible.sync="dialogVisible"
    width="600px"
    :modal-append-to-body="false"
    :append-to-body="true"
    :before-close="handleClose"
    class="NoticeAnnouncementAdd"
  >
    <div class="info_body">
      <el-form :inline="true" ref="form" :rules="rules" :model="form" label-width="110px">
        <el-form-item label="公告标题" prop="announcementTitle">
          <el-input clearable v-model="form.announcementTitle" placeholder="请输入公告标题" :disabled="isCode === 2"></el-input>
        </el-form-item>
        <el-form-item label="公告类型" prop="announcementType">
          <el-select v-model="form.announcementType" filterable clearable placeholder="请选择公告类型">
            <el-option v-for="item in operateModules" :key="item.index" :label="item.operateModule" :value="item.index"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="公告状态">
          <el-radio-group v-model="form.announcementState">
            <el-radio :label="'1'">正常</el-radio>
            <el-radio :label="'2'">关闭</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="公告内容">
          <el-input
            type="textarea"
            :autosize="{ minRows: 4, maxRows: 6 }"
            clearable
            v-model="form.announcementContent"
            placeholder="请输入"
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
  name: 'NoticeAnnouncementAdd',
  components: {},
  props: ['dialogVisible', 'formInfo', 'isCode'],
  data() {
    return {
      form: {
        announcementTitle: '',
        announcementType: '',
        announcementState: '1',
        announcementContent: '',
        noticeId: ''
      },
      rules: {
        announcementTitle: [{ required: true, message: '请输入', trigger: 'blur' }]
        // checkType: [{ required: true, message: '请输入', trigger: 'blur' }]
      },
      operateModules: [
        { index: '1', operateModule: '通知' },
        { index: '2', operateModule: '公告' }
      ]
    }
  },
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
      if (v) {
        if (this.formInfo) {
          let obj = JSON.parse(JSON.stringify(this.formInfo))
          this.form.announcementTitle = obj.announcementTitle
          this.form.announcementState = obj.announcementState
          this.form.announcementContent = obj.announcementContent
          this.form.announcementType = obj.announcementType
          this.form.noticeId = obj.noticeId
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
        announcementTitle: '',
        announcementType: '',
        announcementState: '1',
        announcementContent: '',
        noticeId: ''
      }
      this.formInfo = null
    },
    handleOk() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          // let params = {}
          // for (let key in this.form) {
          //   if (this.form[key]) params[key] = this.form[key]
          // }
          let url = ''
          if (this.formInfo) {
            // url = '/auth/updateNoticeAnnouncement'
            url =
              '/auth/updateNoticeAnnouncement?announcementTitle=' +
              this.form.announcementTitle +
              '&announcementType=' +
              this.form.announcementType +
              '&announcementState=' +
              this.form.announcementState +
              '&announcementContent=' +
              this.form.announcementContent +
              '&creator=' +
              sessionStorage.getItem('userName') +
              '&noticeId=' +
              this.form.noticeId
            axios.put(url).then((r) => {
              if (r.data.remark === 'success' && r.data.result === 0) {
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
                this.$notify.info({
                  title: '消息',
                  message: r.data.message,
                  duration: 1000
                })
              }
            })
          } else {
            url =
              '/auth/insertNoticeAnnouncement?announcementTitle=' +
              this.form.announcementTitle +
              '&announcementType=' +
              this.form.announcementType +
              '&announcementState=' +
              this.form.announcementState +
              '&announcementContent=' +
              this.form.announcementContent +
              '&creator=' +
              sessionStorage.getItem('userName')
            axios.post(url).then((r) => {
              if (r.data.remark === 'success' && r.data.result === 0) {
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
                this.$notify.info({
                  title: '消息',
                  message: r.data.message,
                  duration: 1000
                })
              }
            })
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
    }
  }
}
</script>

<style lang="scss">
.NoticeAnnouncementAdd {
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
