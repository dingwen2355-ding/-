<template>
  <div class="verifyDialog">
    <el-dialog
      title="核查要求"
      :visible.sync="dialogVisible"
      width="540px"
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
          <el-form-item label="核查人" prop="checkPersonName">
            <el-cascader
              popper-class="my-cascader"
              ref="deptcascader"
              v-model="form.checkPersonName"
              :show-all-levels="false"
              :options="options"
              :placeholder="form.checkPersonName"
              @change="handleSelectionChange"
              :props="props"
              clearable
              style="width: 300px"
            ></el-cascader>
<!--            <el-select-->
<!--              filterable-->
<!--              v-model="form.checkPersonName"-->
<!--              style="width: 300px"-->
<!--              placeholder="请选择核查人"-->
<!--              clearable-->
<!--            >-->
<!--              <el-option-->
<!--                v-for="item in checkUserList"-->
<!--                :value="item.trueName"-->
<!--                :label="item.trueName"-->
<!--                :key="item.userId"-->
<!--              ></el-option>-->
<!--            </el-select>-->
          </el-form-item>
          <el-form-item label="下发时间" prop="issueTime">
            <el-date-picker
              type="datetime"
              style="width: 300px"
              placeholder="选择时间"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
              v-model="form.issueTime"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="下发要求" prop="requirement" style="display: flex">
            <el-input
              clearable
              v-model="form.requirement"
              style="width: 300px"
              type="textarea"
              :rows="6"
              placeholder="请输入下发要求"
            ></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
        <el-button type="primary" @click="handleOk" v-if="isCode !== 1">保 存</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import request from '@/utils/request'
export default {
  name: 'verifyDialog',
  props: ['dialogVisible', 'formInfo', 'isCode'],
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
      if (v) {
        this.queryVerifyInfo(this.formInfo.eventId)
        // this.form = Object.assign({}, JSON.parse(JSON.stringify(this.formInfo)))
        this.getUser()
      }
    }
  },
  data() {
    return {
      rules: {
        checkPersonName: [{ required: true, message: '请输入核查人', trigger: 'blur' }],
        issueTime: [{ required: true, message: '选择时间', trigger: 'blur' }],
        requirement: [{ required: true, message: '请输入核查要求', trigger: 'blur' }]
      },
      loading: false,
      form: {
        checkPersonName: [],
        issueTime: '',
        requirement: ''
      },
      selected: [],
      options: [
        // {
        //   precinctId: '3702030250000000000',
        //   precinctKind: '100',
        //   precinctName: '滨湖区'
        // }
      ],
      timeout: null,
      videoHidden: false,
      checkUserList: [],
      props: {
        multiple: false,
        emitPath: false,
        expandTrigger: 'click',
        value: 'precinctId',
        label: 'precinctName',
        children: 'precinctSon',
        lazy: true,
        lazyLoad(node, resolve) {
          if (node.type === 0) return
          let upPrecinctId = node?.value || '0'
          // 查询子级节点
          let url = '/binhuapis/regional/precinct?upPrecinctId=' + upPrecinctId
          const getPrectinct = request.get(url)
          // 查询下属人员
          url = '/auth/user?userType=0&precinctId=' + upPrecinctId
          const getSubUsers = request.get(url)
          Promise.all([getPrectinct, getSubUsers]).then(([precinctRes, userRes]) => {
            let nodes = []
            if (precinctRes.data.code === 200) {
              let precinctList = precinctRes.data.data || []
              const precinctNodes = precinctList.map((item) => ({
                precinctId: item.precinctId,
                precinctName: item.precinctName,
                type: 1,
                leaf: false
              }))
              nodes.push(...precinctNodes)
            }

            if (userRes.data.result === 0) {
              let userList = userRes.data.data || []
              const userNodes = userList.map((item) => ({
                precinctId: item.userId,
                precinctName: item.trueName,
                type: 0,
                leaf: true
              }))
              nodes.push(...userNodes)
            }
            // 通过调用resolve将子节点数据返回，通知组件数据加载完成
            if (nodes.length === 0) {
              resolve([])
            }
            resolve(nodes)
          })
        }
      }
    }
  },
  methods: {
    queryVerifyInfo(id) {
      let url = '/binhuapis/eventCheck/query-check'
      url += '?eventId=' + id
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          let data = res.data.data.list || []
          const verifyInfo = data[0] || {}
          this.formInfo.id = verifyInfo.id
          this.form = Object.assign({}, JSON.parse(JSON.stringify(verifyInfo)))
          let url = '/auth/user?userIds=' + this.form.checkPersonName
          request.get(url).then((res) => {
            if (res.data.result === 0) {
              this.form.checkPersonName = res.data.data[0].trueName
            }
          })
        }
      })
    },
    getUser() {
      let url = '/auth/user?userIds=' + this.form.checkPersonName
      request.get(url).then((res) => {
        if (res.data.result === 0) {
          let data = res.data.data || []
          this.checkUserList = data
          // console.log(data)
        }
      })
    },
    handleSelectionChange(value) {
      this.selected = value
      console.log(value)
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
        checkPersonName: this.form.checkPersonName,
        issueTime: this.form.issueTime,
        requirement: this.form.requirement
      }
      // const checkPersonName = this.form.checkPersonName
      // console.log('分威风威风',checkPersonName )
      // const [{ userId }] = this.checkUserList.filter((item) => {
      //   if (item.trueName === checkPersonName) {
      //     return item
      //   }
      // })
      // newForm.checkUserId = Number(userId)
      newForm.checkUserId = Number(this.selected)
      newForm.eventId = this.formInfo.eventId
      let url = ''
      if (this.formInfo && this.formInfo.id) {
        url = '/binhuapis/eventCheck/edit-check?userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")
        newForm.id = this.formInfo.id
        // console.log('参数: ', newForm, 'url: ', url)
      } else {
        url = '/binhuapis/eventCheck/add-check'
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
<style lang="scss">
.verifyDialog {
  .el-input {
    line-height: 32px;
    // width: 200px;
    box-sizing: border-box;
  }
  .el-form--inline .el-form-item__content {
    flex-grow: 1;
  }
  .upload_box {
    box-sizing: border-box;
    width: 210px;
    height: 140px;
    border: 1px solid rgb(220, 220, 220);
    position: relative;
    text-align: center;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 14px;
    p {
      margin: 0;
      line-height: 25px;
      font-size: 14px;
    }
    #video_file {
      font-size: 14px;
      width: 100%;
      height: 100%;
      margin: 0 !important;
      opacity: 0;
      position: absolute;
      left: 0;
      top: 0;
      z-index: 1;
    }
  }
}
.el-cascader .el-input .el-input__inner::placeholder {
  color: #606266;
}
</style>
