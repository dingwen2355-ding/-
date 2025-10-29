<template>
  <div class="addTask">
    <el-dialog
      :visible.sync="dialogVisible"
      title="编辑任务派遣"
      width="500px"
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
          <el-form-item label="事件ID" v-show="false">
            <el-input v-model="form.contactId" placeholder="请输入内容"></el-input>
          </el-form-item>
          <el-form-item label="任务名称" prop="taskTitle">
            <el-input v-model="form.taskTitle" placeholder="请输入任务标题" style="width: 300px"></el-input>
          </el-form-item>
          <el-form-item label="下发时间" prop="issueTime">
            <el-date-picker
              style="width: 300px"
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
              v-model="form.taskContent"
              :rows="5"
              type="textarea"
              placeholder="请输入任务内容"
              style="width: 300px"
            ></el-input>
          </el-form-item>
          <el-form-item label="处置状态" prop="status" v-show="false">
            <el-select v-model="form.status" placeholder="请输入处置状态" style="width: 200px">
              <el-option v-for="item in statusList" :key="item.key" :label="item.value" :value="item.key"> </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="处置时间" prop="dealTime" v-show="false">
            <el-date-picker
              v-model="form.dealTime"
              style="width: 200px"
              type="datetime"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
              placeholder="选择处置时间"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="处置人" prop="person">
            <el-input
              v-if="isCode !== 2"
              v-model="form.person"
              disabled
              placeholder="请输入处置人"
              style="width: 300px"
            ></el-input>
            <el-cascader
              v-else
              ref="deptcascader"
              v-model="selected"
              :placeholder="form.person"
              :show-all-levels="false"
              :options="options"
              @change="handleSelectionChange"
              :props="props"
              clearable
              style="width: 300px"
            >
            </el-cascader>
          </el-form-item>
          <el-form-item label="处置反馈" prop="feedback" v-show="false">
            <el-input v-model="form.feedback" placeholder="请输入处置反馈"></el-input>
          </el-form-item>
          <el-form-item label="处置附件地址" v-show="false">
            <span v-if="form.file && /^.+(\.mp4|\.MP4)$/.test(form.file)">
              <el-link target="_blank" :href="form.file" type="primary">视频</el-link>
            </span>
            <span v-else-if="form.file && form.file != ''">
              <el-link target="_blank" :href="form.file" type="primary">图片</el-link>
            </span>
            <input
              v-if="isCode !== 1"
              ref="videoFile"
              @change="fileChange($event)"
              type="file"
              style="font-size: 14px; margin-left: 20px"
              id="video_file"
            />
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
  name: 'addTask',
  props: ['dialogVisible', 'formInfo', 'isCode'],
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
      if (v) {
        this.form = Object.assign({}, JSON.parse(JSON.stringify(this.formInfo)))
        console.log('新增编辑查看this.form', this.form)
        this.form.personInfoList = []
        if (this.form.personId) {
          const personInfo = {
            department: this.form.department,
            personId: this.form.personId,
            personName: this.form.person
          }
          this.selected.push(this.form.personId)
          this.form.personInfoList.push(personInfo)
        }
      }
    }
  },
  data() {
    return {
      rules: {
        taskTitle: [{ required: true, message: '请输入任务名称', trigger: 'blur' }],
        issueTime: [{ required: true, message: '请选择下发时间', trigger: 'blur' }],
        taskContent: [{ required: true, message: '请输入任务内容', trigger: 'blur' }]
        // nodeName: [{ required: true, message: '请选择任务节点', trigger: 'blur' }]
        // dealTime: [{ required: true, message: '选择处置时间', trigger: 'blur' }],
        // person: [{ required: true, message: '请输入处置人', trigger: 'blur' }]
        // feedback: [{ required: true, message: '请输入处置反馈', trigger: 'blur' }]
      },
      loading: false,
      form: {
        contactId: '',
        eventId: '',
        // person: '', // 手填
        issueTime: '',
        taskContent: '',
        // nodeName: '',‘
        personInfoList: [],
        taskTitle: ''
      },
      nodeList: [],
      selected: [],
      options: [
        // {
        //   precinctId: '3702030250000000000',
        //   precinctKind: '100',
        //   precinctName: '滨湖区'
        // }
      ],
      props: {
        multiple: true,
        emitPath: false,
        expandTrigger: 'click',
        value: 'precinctId',
        label: 'precinctName',
        children: 'precinctSon',
        lazy: true,
        lazyLoad(node, resolve) {
          const { level } = node
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
      },
      dealManList: [],
      treeData: [
        {
          precinctId: '3702030250000000000',
          precinctKind: '100',
          precinctName: '滨湖区'
        }
      ],
      statusList: [
        {
          key: '0',
          value: '未处置'
        },
        {
          key: '1',
          value: '已处置'
        }
      ]
    }
  },
  methods: {
    handleSelectionChange(value) {
      this.selected = value
      console.log(value)
    },
    fileChange(e) {
      var files = e.target.files || e.dataTransfer.files
      if (!files.length) return
      let name = files[0].name.toLowerCase()
      if (!/\.(xlsx|xls|doc|docx|pdf|PDF|jpg|png|gif|bmp|jpeg|JPG|PNG|GIF|BMP|JPEG|pptx|mp4)$/.test(name)) {
        this.$message.warning('请上传文件')
        return
      }
      // 这里是file文件
      // this.addForm.video = files[0]
      var reader = new FileReader()
      reader.readAsDataURL(files[0])
      // this.videoHidden = false
      reader.onload = () => {
        // 这里是一段base64，用于视频回显用
        // this.videoShow = reader.result
      }
      this.fileUpload(files[0])
    },
    fileUpload(v) {
      let url = '/apis/file/upload'
      this.$message.warning('文件正在上传中。。。')
      let formData = new FormData()
      formData.append('file', v)
      formData.append('group', 'system')
      request.post(url, formData).then((res) => {
        if (res.data.code === 200) {
          this.form.file = res.data.data
          this.$message.success('文件上传成功')
        }
      })
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
      this.$parent.refreshData('node', this.form.eventId, this.form.contactId)
      this.loading = false
      this.selected = []
      this.form = {}
      // 清空表单校验，避免再次进来会出现上次校验的记录
      this.$refs.formLabel.clearValidate()
    },
    handleOk() {
      if (this.$refs['deptcascader']) {
        let personInfoList = []
        const checkedNodesList = this.$refs['deptcascader'].getCheckedNodes()
        this.selected.forEach(t => {
          checkedNodesList.forEach(s => {
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
      console.log(this.form.personInfoList)
      this.$refs.formLabel.validate((valid) => {
        if (valid) {
          if (this.form.personInfoList.length === 0) {
            this.$message.warning('检查表单输入内容的合法性！')
          } else {
            this.submitAll()
          }
        } else {
          this.$message.warning('检查表单输入内容的合法性！')
          return false
        }
      })
    },
    submitAll() {
      if (this.isCode === 2) {
        this.addEventNodeContact()
      }
      if (this.isCode === 0) {
        this.editEventNodeContact()
      }
    },
    addEventNodeContact() {
      this.loading = true
      let params = this.form
      params.status = '0'
      let url = '/binhuapis/dapeng/eventTask/add?userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")
      request.post(url, params).then((res) => {
        this.loading = false
        if (res.data.code === 200) {
          this.$message.success('新增成功！')
          this.$emit('success')
          this.reset()
          this.$emit('update:dialogVisible', false)
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    editEventNodeContact() {
      this.loading = true
      let params = this.form
      let url = '/binhuapis/dapeng/eventTask/edit?userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")
      request.post(url, params).then((res) => {
        this.loading = false
        if (res.data.code === 200) {
          this.$message.success('编辑成功！')
          this.$emit('success')
          this.reset()
          this.$emit('update:dialogVisible', false)
        } else {
          this.$message.error(res.data.message)
        }
      })
    }
  }
}
</script>
<style lang="scss">
.addTask {
  .dialog-footer {
    justify-content: center;
  }
}
</style>
