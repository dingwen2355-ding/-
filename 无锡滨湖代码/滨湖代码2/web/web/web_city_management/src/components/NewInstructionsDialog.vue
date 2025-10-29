<template>
  <el-dialog
    :title="'新增指令'"
    :visible.sync="dialogVisible"
    :modal="false"
    width="1931rem"
    height="725rem"
    class="NewInstructionsDialog default-dialog-class large-screen"
    :append-to-body="true"
    :before-close="handleClose"
  >
    <div class="dd-title-back">
      <div class="dd-title-back-s"></div>
    </div>
    <div class="dd-back-area top-right-area"></div>
    <div class="dd-back-angle top-left-angle"></div>
    <div class="dd-back-angle top-right-angle"></div>
    <div class="dd-back-angle bottom-left-angle"></div>
    <div class="dd-back-angle bottom-right-angle"></div>
    <div>
      <el-form :model="form" :rules="rules" ref="ruleForm" label-width="566rem" class="NewInstructions-ruleForm">
        <el-form-item label="任务名称" prop="taskTitle">
          <el-input
            v-model="form.taskTitle"
            placeholder="请输入任务标题"
            class="large-screen"
            popper-class="large-screen"
            style="width: 500rem"
          ></el-input>
        </el-form-item>
        <el-form-item label="节点名称" prop="contactId">
          <el-select
            v-model="form.contactId"
            placeholder="事件类型选择"
            filterable
            clearable
            popper-class="large-screen"
          >
            <el-option v-for="item in nodeList" :key="item.contactId" :label="item.nodeName" :value="item.contactId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="下发时间" prop="issueTime">
          <el-date-picker
            popper-class="large-screen"
            v-model="form.issueTime"
            type="datetime"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择下发时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="任务内容" prop="taskContent">
          <el-input
            class="large-screen"
            popper-class="large-screen"
            v-model="form.taskContent"
            :rows="5"
            type="textarea"
            placeholder="请输入任务内容"
          ></el-input>
        </el-form-item>
        <el-form-item label="处置人" prop="person">
          <el-cascader
            class="large-screen"
            popper-class="large-screen"
            ref="deptcascader"
            v-model="selected"
            :placeholder="form.person"
            :show-all-levels="false"
            :options="options"
            @change="handleSelectionChange"
            :props="props"
            clearable
          >
          </el-cascader>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleOk" style="margin-left: 154rem; width: 520rem !important"
            >提交</el-button
          >
          <el-button @click="resetForm('ruleForm')" style="margin-left: 200rem; width: 520rem !important">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </el-dialog>
</template>

<script>
import request from '@/utils/request'
export default {
  name: 'NewInstructionsDialog',
  props: ['dialogVisibleFlag'],
  watch: {
    dialogVisibleFlag(v) {
      this.dialogVisible = v
    }
  },
  data() {
    return {
      dialogVisible: false,
      form: {
        contactId: '',
        // person: '', // 手填
        issueTime: '',
        taskContent: '',
        // nodeName: '',‘
        personInfoList: [],
        taskTitle: ''
      },
      rules: {
        taskContent: [{ required: true, message: '请选择任务内容', trigger: 'change' }],
        contactId: [{ required: true, message: '请选择节点名称', trigger: 'change' }],
        taskTitle: [{ required: true, message: '请选择任务名称', trigger: 'change' }],
        issueTime: [{ required: true, message: '请选择下发时间', trigger: 'change' }],
        personInfoList: [{ required: true, message: '请选择处置人', trigger: 'change' }]
      },
      selected: [],
      options: [],
      nodeList: [],
      props: {
        multiple: true,
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
          let url = '/community/regional/precinct?upPrecinctId=' + upPrecinctId
          const getPrectinct = request.get(url)
          // 查询下属人员
          url = '/spi/user?userType=0&precinctId=' + upPrecinctId
          const getSubUsers = request.get(url, {
            headers: {
              Authorization: '12CBD9B708D887A41AFAB97DAC46AAC6'
            }
          })
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
  mounted() {
    this.getContactByEventId()
  },
  methods: {
    handleOk() {
      if (this.$refs['deptcascader']) {
        let personInfoList = []
        const checkedNodesList = this.$refs['deptcascader'].getCheckedNodes()
        this.selected.forEach((t) => {
          checkedNodesList.forEach((s) => {
            if (s.value === t) {
              const personInfo = {
                department: '',
                personId: '',
                personName: ''
              }
              let [user, ...departments] = s.pathLabels.reverse()
              personInfo.personId = s.value
              personInfo.personName = user
              personInfo.department = departments.shift()
              personInfoList.push(personInfo)
            }
          })
        })
        this.form.personInfoList = this.form.personInfoList.concat(personInfoList)
      }
      this.$refs.ruleForm.validate((valid) => {
        if (valid) {
          if (this.form.personInfoList.length === 0) {
            this.$message.warning('检查表单输入内容的合法性！')
          } else {
            this.addEventNodeContact()
          }
        } else {
          this.$message.warning('检查表单输入内容的合法性！')
          return false
        }
      })
    },
    addEventNodeContact() {
      let params = this.form
      let url = '/community/dapeng/eventTask/add'
      request.post(url, params).then((res) => {
        this.loading = false
        if (res.data.code === 200) {
          this.$emit('update:dialogVisibleFlag', false)
          this.$message.success('新增成功！')
          this.$emit('success')
          this.reset()
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    reset() {
      this.selected = []
      this.form = {
        contactId: '',
        id: '',
        // person: '', // 手填
        issueTime: '',
        taskContent: '',
        // nodeName: '',‘
        personInfoList: [],
        taskTitle: ''
      }
      // 清空表单校验，避免再次进来会出现上次校验的记录
      this.$refs.form.clearValidate()
    },
    getContactByEventId() {
      let url = '/community/dapeng/event/getContactByEventId'
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          this.nodeList = res.data.data
        }
      })
    },
    handleSelectionChange(value) {
      this.selected = value
      console.log(value)
    },
    handleClose() {
      this.dialogVisible = false
      this.$emit('update:dialogVisibleFlag', false)
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.addEventNodeContact()
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    }
  }
}
</script>

<style lang="scss" scoped>
.NewInstructionsDialog {
  ::v-deep .el-dialog {
    width: 2678rem !important;
    height: 2281rem !important;
  }

  ::v-deep .NewInstructions-ruleForm {
    .el-form-item {
      .el-form-item__label {
        font-size: 60rem;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: #6fd1ff;
        line-height: 167rem;
      }
      .el-form-item__content {
        min-height: 167rem;
        padding-top: 35rem;
        .el-select {
          .el-input {
            .el-input__inner {
              width: 1976rem;
            }
          }
        }
      }
    }
  }
}
</style>
