<template>
  <div>
    <div class="NotificationManagerAdd" v-if="dialogVisible">
      <div class="informationTitle">
        <span class="title">{{ title }}</span>
        <span class="icon_font" @click="handleClose">×</span>
      </div>
      <div class="info_body">
        <el-form :inline="true" :model="formData" ref="formData" label-width="110px">
          <el-form-item
            label="标题名称"
            prop="messageTitle"
            :rules="[{ required: true, message: '请输入标题名称', trigger: 'blur' }]"
          >
            <el-input
              clearable
              v-model="formData.messageTitle"
              placeholder="请输入标题名称"
              :readonly="readonlyForm"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="消息级别"
            prop="messageLevel"
            :rules="[{ required: true, message: '请选择消息级别', trigger: 'change' }]"
          >
            <el-select v-model="formData.messageLevel" placeholder="请选择消息级别" clearable>
              <el-option
                v-for="item in levelList"
                :key="item.id"
                :label="item.dictName"
                :value="item.dictName"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item
            label="消息类别"
            prop="messageCategory"
            :rules="[{ required: true, message: '请选择消息类别', trigger: 'change' }]"
          >
            <el-select v-model="formData.messageCategory" placeholder="请选择消息类别" clearable>
              <el-option
                v-for="item in categoryList"
                :key="item.id"
                :label="item.dictName"
                :value="item.dictName"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item
            label="事件详情"
            prop="messageContent"
            :rules="[{ required: true, message: '请输入事件详情', trigger: 'blur' }]"
          >
            <el-input
              type="textarea"
              placeholder="请输入事件详情"
              :rows="2"
              v-model="formData.messageContent"
              style="width: 920px"
              :readonly="readonlyForm"
            ></el-input>
          </el-form-item>
          <div v-if="title === '新增消息'">
            <el-form-item label="发送人科室">
              <el-input clearable v-model="formData.sendPrecinctName" :readonly="true"></el-input>
            </el-form-item>
            <el-form-item label="发送人联系方式">
              <el-input
                clearable
                v-model="formData.sendPhone"
                placeholder="请输入发送人联系方式"
                style="width: 220px"
                :readonly="true"
              ></el-input>
            </el-form-item>
          </div>
          <!-- 附件 -->
          <div class="fileList" v-if="title === '新增消息' || fileList.length !== 0">
            <span>文件上传</span>
            <el-upload
              class="attachmentUpload"
              action="#"
              list-type="picture-card"
              :http-request="uploading"
              :file-list="fileList"
              accept=".jpg, .png, .xlsx, .xlc, .xlm, .xls, .xlt, .xlw, .csv, .doc, .docx, .pdf, .PDF"
              ref="upload"
              :before-upload="beforeAvatarUpload"
              :disabled="attachFilesDisabled"
              :class="objClass"
            >
              <i class="el-icon-plus"></i>
              <div slot="file" slot-scope="{ file }">
                <img
                  class="el-upload-list__item-thumbnail"
                  style="width: 100%; height: 50%; margin-top: 5px; object-fit: contain; object-position: 50% 20%"
                  :src="file | picFilter"
                />
                <div style="margin-top: -10px">{{ file.name }}</div>
                <span class="el-upload-list__item-actions">
                  <span
                    class="el-upload-list__item-preview"
                    @click="handlePictureCardPreview(file)"
                    v-if="file.isShow"
                  >
                    <i class="el-icon-zoom-in"></i>
                  </span>
                  <span class="el-upload-list__item-delete" @click="handleDownload(file)">
                    <i class="el-icon-download"></i>
                  </span>
                  <span
                    class="el-upload-list__item-delete"
                    @click="handleRemove(file)"
                    v-if="title === '新增消息'"
                  >
                    <i class="el-icon-delete"></i>
                  </span>
                </span>
              </div>
            </el-upload>
          </div>
          <div v-if="title === '未读消息详情' || title === '已发消息详情' || title === '已读消息详情'">
            <el-form-item label="发送人科室">
              <el-input
                clearable
                v-model="formData.sendPrecinctName"
                placeholder="请输入发送人科室"
                :readonly="readonlyForm"
                style="width: 220px"
              ></el-input>
            </el-form-item>
            <el-form-item label="发送人联系方式">
              <el-input
                clearable
                v-model="formData.sendPhone"
                placeholder="请输入发送人联系方式"
                :readonly="readonlyForm"
                style="width: 220px"
              ></el-input>
            </el-form-item>
            <el-form-item label="发送时间">
              <el-input
                clearable
                v-model="formData.sendTime"
                placeholder="请输入发送时间"
                :readonly="readonlyForm"
              ></el-input>
            </el-form-item>
          </div>
          <div v-if="title === '新增消息'">
            <div
              class="receivingItem"
              style="display: flex; margin-bottom: 8px"
              v-for="(item, index) in formData.contents"
              :key="index + 's'"
            >
              <!-- <el-form-item
                label="接收科室"
                :prop="'contents.' + index + '.receivePrecinctName'"
                :rules="{ required: true, message: '请选择接收科室', trigger: 'blur' }"
              >
                <el-select
                  @change="receivePrecinctNameChange(item, index)"
                  @input="updateValue"
                  filterable
                  v-model="item.receivePrecinctName"
                  placeholder="请选择接收科室"
                  clearable
                  style="width: 220px"
                >
                  <el-option
                    v-for="item in queryPrecinctList"
                    :value="item.precinctName"
                    :label="item.precinctName"
                    :key="item.precinctName"
                  />
                </el-select>
              </el-form-item>
              <el-form-item label="接受人员">
                <el-input
                  @input="updateValue"
                  clearable
                  v-model="item.receivePerson"
                  placeholder="请输入接受人员"
                  :readonly="true"
                ></el-input>
              </el-form-item>-->
              <el-form-item
                label="接收科室"
                :prop="'contents.' + index + '.receivePrecinctName'"
                :rules="{ required: true, message: '请选择接收科室', trigger: 'blur' }"
              >
                <el-select
                  @change="receivePrecinctNameChange(item, index)"
                  @input="updateValue"
                  filterable
                  v-model="item.receivePrecinctName"
                  placeholder="请选择接收科室"
                  clearable
                  style="width: 220px"
                >
                  <el-option
                    v-for="item in queryPrecinctList"
                    :value="item.precinctName"
                    :label="item.precinctName"
                    :key="item.precinctId"
                  />
                </el-select>
              </el-form-item>
              <el-form-item label="接受人员">
                <el-select
                  filterable
                  @change="receivePrecinctNameChange1(item, index)"
                  v-model="item.receivePerson"
                  placeholder="请选择接接受人员"
                  clearable
                  style="width: 220px"
                >
                  <el-option
                    v-for="item in receivePersonList"
                    :value="item.trueName"
                    :label="item.trueName"
                    :key="item.userId"
                  />
                </el-select>
              </el-form-item>
              <span class="addTime" @click="addContent(item, index)">+</span>
              <span class="delTime" @click="delContent(item, index)">-</span>
            </div>
          </div>
          <div v-if="title === '已发消息详情'">
            <div
              class="receivingItem"
              style="display: flex; margin-bottom: 8px"
              v-for="(item, index) in messageList"
              :key="index + 's'"
            >
              <el-form-item label="接收科室">
                <el-input
                  clearable
                  v-model="item.receivePrecinctName"
                  style="width: 140px"
                  :readonly="readonlyForm"
                ></el-input>
              </el-form-item>
              <el-form-item label="接受人员">
                <el-input
                  clearable
                  v-model="item.receivePerson"
                  style="width: 140px"
                  :readonly="readonlyForm"
                ></el-input>
              </el-form-item>
              <el-form-item label="接受人联系方式">
                <el-input
                  clearable
                  v-model="item.receivePhone"
                  style="width: 140px"
                  :readonly="readonlyForm"
                ></el-input>
              </el-form-item>
              <el-form-item label="接受时间">
                <el-input
                  clearable
                  v-model="item.receiveTime"
                  style="width: 140px"
                  :readonly="readonlyForm"
                ></el-input>
              </el-form-item>
            </div>
          </div>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" v-if="title === '未读消息详情'" @click="readCilck">已读</el-button>
        <el-button type="primary" v-if="title === '新增消息'" @click="sendMessage">发送</el-button>
      </div>
    </div>
    <!-- 附件查看 -->
    <el-dialog :visible.sync="fileDialogVisible" :append-to-body="true">
      <div class="imgItem">
        <img class="img img-object" :src="dialogImageUrl" alt />
      </div>
    </el-dialog>
  </div>
</template>
<script>
import axios from '@/utils/request'
import qs from 'qs'
export default {
  name: 'planSet',
  props: ['detailsDialog', 'detailsDialogData', 'dialogTitle'],
  components: {},
  data() {
    // var valiDreceivePrecinctName = (rule, value, callback) => {
    //   let data = 'ok'
    //   console.log(this.formData.contents)
    //   this.formData.contents.some((e) => {
    //     if (e.receivePrecinctName === '') {
    //       debugger
    //       // callback(new Error('请选择接收科室'))
    //       data = 'no'
    //       return true
    //     }
    //   })
    //   if (data === 'no') {
    //     callback(new Error('请选择接收科室'))
    //   } else {
    //     callback()
    //   }
    // }
    return {
      receivePersonList: [],
      levelList: [],
      categoryList: [],
      dialogVisible: false,
      tableData: {},
      title: '',
      active: ['1', '2'],
      formData: {
        messageLevel: '',
        messageCategory: '',
        messageTitle: '', // 标题名称
        messageContent: '', // 事件详情
        sendPrecinctName: sessionStorage.getItem('departmentName'), // 发送人科室
        // sendPrecinctName: '',
        sendPhone: sessionStorage.getItem('phone'), // 发送人联系方式
        sendTime: '', // 发送时间
        receivePrecinctName: '', // 接收科室
        receivePerson: '', // 接受人员
        receivingPeople: '', // 接受人联系方式
        receiveTime: '', // 接受时间
        contents: [
          {
            receivePrecinctName: '', // 接收科室
            receivePerson: '', // 接受人员
            receivePhone: '' // 接受人员电话
          }
        ]
      },
      rules: {
        messageTitle: [{ required: true, message: '请输入标题名称', trigger: 'blur' }],
        messageContent: [{ required: true, message: '请输入事件详情', trigger: 'blur' }]
      },
      dialogImageUrl: '',
      fileDialogVisible: false,
      fileList: [],
      attachFilesDisabled: false,
      objClass: {
        upLoadShow: true,
        upLoadHide: false
      },
      queryPrecinctList: [],
      readonlyForm: false,
      messageList: []
    }
  },
  computed: {},
  watch: {
    dialogTitle(v) {
      this.title = v
      if (v === '新增消息') {
        this.readonlyForm = false
        this.attachFilesDisabled = false
        this.objClass = {
          upLoadShow: true,
          upLoadHide: false
        }
      } else {
        this.readonlyForm = true
        this.attachFilesDisabled = true
        this.objClass = {
          upLoadShow: false,
          upLoadHide: true
        }
      }
    },
    detailsDialog: {
      handler(v) {
        this.dialogVisible = v
        if (v) {
          if (this.title !== '新增消息') {
            this.formData = JSON.parse(JSON.stringify(this.detailsDialogData))
            if (this.formData.imgUrl === '') {
              this.fileList = []
            } else {
              let data = this.formData.imgUrl.split(',')
              data.some((e) => {
                let fileName = e.split('/')
                let files = {
                  name: fileName[fileName.length - 1],
                  url: e
                }
                this.fileList.push(files)
              })
            }
            if (this.title === '已发消息详情') {
              this.getSendMessage()
            }
          }
        }
      }
    },
    detailsDialogData: {
      handler(v) { }
    }
  },
  filters: {
    picFilter(flie) {
      console.log(flie)
      if (
        flie.name.includes('.jpg') ||
        flie.name.includes('.png') ||
        flie.name.includes('.gif') ||
        flie.name.includes('.bmp') ||
        flie.name.includes('.jpeg') ||
        flie.name.includes('.JPG') ||
        flie.name.includes('.PNG') ||
        flie.name.includes('.GIF') ||
        flie.name.includes('.BMP') ||
        flie.name.includes('.JPEG')
      ) {
        flie.isShow = true
        return flie.url
      } else if (flie.name.includes('.docx')) {
        flie.isShow = false
        return require('../../assets/fileImg.png')
      } else if (flie.name.includes('.pptx')) {
        flie.isShow = false
        return require('../../assets/power.png')
      } else if (flie.name.includes('.xlsx')) {
        flie.isShow = false
        return require('../../assets/excel.png')
      } else if (flie.name.includes('.pdf') || flie.name.includes('.PDF')) {
        flie.isShow = false
        return require('../../assets/pdf.png')
      } else {
        flie.isShow = false
        return require('../../assets/other.png')
      }
    }
  },
  mounted() {
    this.getQueryPrecinctList()
    this.getLevel()
    this.getCategory()
  },
  beforeDestroy() {
    this.$root.eventBus.$off('messageUpdate')
  },
  methods: {
    getLevel() {
      axios.get('/binhuapis/dict/query-condition?parentId=' + '125').then((res) => {
        if (res.data.code === 200) {
          this.levelList = res.data.data.list
        }
      })
    },
    getCategory() {
      axios.get('/binhuapis/dict/query-condition?parentId=' + '129').then((res) => {
        if (res.data.code === 200) {
          this.categoryList = res.data.data.list
        }
      })
    },
    // 获取已发消息详情
    getSendMessage() {
      console.log(this.formData, '++++++++++++')
      let param = {
        messageId: this.formData.messageId
      }
      axios.get('/binhuapis/queryMessage1?' + qs.stringify(param)).then((res) => {
        if (res.data.code === 200) {
          console.log(res.data.data[0].messagePrecinctBeans)
          this.messageList = res.data.data[0].messagePrecinctBeans
          console.log(this.formData)
        } else {
          this.$notify({
            title: '警告',
            message: '查询失败，' + res.data.message,
            type: 'warning',
            duration: 1000
          })
        }
      })
    },
    // 发送消息
    sendMessage() {
      this.$refs.formData.validate((valid) => {
        if (valid) {
          let formData = new FormData()
          formData.append('messageTitle', this.formData.messageTitle)
          formData.append('messageLevel', this.formData.messageLevel)
          formData.append('messageCategory', this.formData.messageCategory)
          formData.append('messageContent', this.formData.messageContent)
          formData.append('sendPrecinctName', this.formData.sendPrecinctName)
          formData.append('sendPhone', this.formData.sendPhone)
          formData.append('messagePrecinctBean', JSON.stringify(this.formData.contents))
          let is50M = 0
          if (this.fileList.length !== 0) {
            for (var i = 0; i < this.fileList.length; i++) {
              is50M += this.fileList[i].size
              formData.append('files', this.fileList[i].raw)
            }
          }
          const is50MBoolen = is50M / 1024 / 1024 < 50
          // 限制文件上传大小
          if (!is50MBoolen) {
            this.$notify.error({
              title: '错误',
              message: '上传文件总和大小不能超过 50MB!',
              duration: 1000
            })
          } else {
            axios.post('/binhuapis/saveMessageInfo', formData).then((res) => {
              if (res.data.code === 200) {
                this.handleClose()
                this.$emit('success')
                this.$notify({
                  title: '成功',
                  message: '发送成功！',
                  type: 'success',
                  duration: 1000
                })
              } else {
                this.$notify({
                  title: '警告',
                  message: '错误，' + res.data.message,
                  type: 'warning',
                  duration: 1000
                })
              }
            })
          }
        } else {
          this.$notify({
            title: '警告',
            message: '检查表单输入内容的合法性！',
            type: 'warning',
            duration: 1000
          })
          return false
        }
      })
    },
    // 已读
    readCilck() {
      this.$confirm('是否将此消息置为已读?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          let param = {
            Id: this.formData.id,
            messageType: '2'
          }
          axios.post('/binhuapis/updateMessageType?' + qs.stringify(param)).then((res) => {
            if (res.data.code === 200) {
              this.handleClose()
              this.$root.eventBus.$emit('messageUpdate', true)
              this.$emit('success')
              this.$notify({
                title: '成功',
                message: '已成功将此消息置为已读',
                type: 'success',
                duration: 1000
              })
            } else {
              this.$notify({
                title: '警告',
                message: '错误，' + res.data.message,
                type: 'warning',
                duration: 1000
              })
            }
          })
        })
        .catch(() => {
          this.$notify.info({
            title: '消息',
            message: '已取消',
            duration: 1000
          })
        })
    },
    // 所属科室 带出对应人员
    receivePrecinctNameChange(v, index) {
      console.log(v, index)
      this.receivePersonList = []
      this.formData.contents[index].receivePerson = ''
      if (v.receivePrecinctName) {
        this.receivePersonList =
          this.queryPrecinctList.filter((item) => item.precinctName === v.receivePrecinctName)[0].users || []
        console.log(this.receivePersonList)
      }
    },
    receivePrecinctNameChange1(v, index) {
      console.log(v, index)
      this.formData.contents[index].receivePhone = this.receivePersonList.filter(
        (item) => item.receivePerson === v.trueName
      )[0].phone
    },
    // // 所属科室 带出对应人员
    // receivePrecinctNameChange(v, index) {
    //   if (this.formData.contents[index]) {
    //     this.formData.contents[index].receivePerson = this.queryPrecinctList.filter(
    //       (item) => item.precinctName === v.receivePrecinctName
    //     )[0].userName
    //     this.formData.contents[index].receivePhone = this.queryPrecinctList.filter(
    //       (item) => item.precinctName === v.receivePrecinctName
    //     )[0].phone
    //   }
    // },
    // 所属科室
    // getQueryPrecinctList() {
    //   axios.get('/binhuapis/queryPrecinct').then((res) => {
    //     if (res.data.code === 200) {
    //       this.queryPrecinctList = res.data.data
    //     }
    //   })
    // },
    getQueryPrecinctList() {
      let url = '/binhuapis/queryMessageUser?roleId=' + sessionStorage.getItem('roleId')
      axios.get(url).then((res) => {
        if (res.data.code === 200) {
          this.queryPrecinctList = res.data.data
        }
      })
    },
    addContent(item, index) {
      let obj = {
        receivePrecinctName: '', // 接收科室
        receivePerson: '', // 接受人员
        receivePhone: '' // 接受人员电话
      }
      this.formData.contents.push(obj)
      this.$forceUpdate()
    },
    delContent(item, index) {
      console.log(this.formData.contents)
      if (this.formData.contents.length === 1) {
        this.$message('无法删除')
        return
      }
      console.log(index)
      this.formData.contents.splice(index, 1)
      this.$forceUpdate()
    },
    updateValue() {
      this.$forceUpdate()
    },
    // 确定按钮
    handleOk() { },
    // 附件
    uploading(e) {
      console.log(e)
      // let obj = {
      //   fileName: e.file.name,
      //   fileUrl: '',
      //   step: 2,
      //   uploader: localStorage.getItem('user') || ''
      // }
      // let url = '/binhuapis/lawBookMain/uploadFile'
      // let formData = new FormData()
      // formData.append('multipartFile', e.file)
      // formData.append('lawBookFile', new Blob([JSON.stringify(obj)], { type: 'application/json' }))
      // axios({
      //   method: 'POST',
      //   url: url,
      //   data: formData,
      //   headers: {
      //     'Content-Type': 'multipart/form-data; boundary=<calculated when request is sent>'
      //   }
      // }).then((res) => {
      //   // console.log(res.data)
      //   if (res.data.code === 200) {
      //     let data = res.data.data
      //     let files = {
      //       name: data.fileName,
      //       url: data.fileUrl,
      //       id: data.id
      //     }
      //   }
      // })
      this.fileList = this.$refs.upload.uploadFiles
    },
    handleRemove(file) {
      this.$confirm('此操作将永久删除该条数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          // let fileUrl = ''
          // if (this.fileList && this.fileList.length > 0) {
          //   this.fileList.forEach((item) => {
          //     if (item.name === file.name) {
          //       fileUrl = item.data
          //     } else {
          //       fileUrl = file.url
          //     }
          //   })
          // } else {
          //   fileUrl = file.url
          // }
          let arr = this.$refs.upload.uploadFiles
          let index = arr.indexOf(file)
          // this.formInfo.main.attachFiles.splice(index, 1)
          arr.splice(index, 1)
          this.fileList.splice(index, 1)
          this.$message.success('删除成功！')
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    handlePictureCardPreview(file) {
      console.log(file)
      this.dialogImageUrl = file.url
      this.fileDialogVisible = true
    },
    beforeAvatarUpload(file) {
      console.log(file)
      // 1MB=1024*1024(1MB=1024KB 1KB=1024MB)
      const is20M = file.size / 1024 / 1024 < 20
      // 限制文件上传大小
      if (!is20M) {
        this.$notify.error({
          title: '错误',
          message: '上传文件大小不能超过 20MB!',
          duration: 1000
        })
        return false
      }
      return true
    },
    handleDownload(file) {
      console.log(file)
      let url2 = file.url.replace(/\\/g, '/')
      let xhr = new XMLHttpRequest()
      xhr.open('GET', url2, true)
      xhr.responseType = 'blob'
      xhr.onload = () => {
        if (xhr.status === 200) {
          this.saveAs(xhr.response, file.name)
        }
      }
      xhr.send()
    },
    saveAs(data, name) {
      let urlObject = window.URL || window.webkitURL || window
      // eslint-disable-next-line camelcase
      let export_blob = new Blob([data])
      // eslint-disable-next-line camelcase
      let save_link = document.createElementNS('http://www.w3.org/1999/xhtml', 'a')
      save_link.href = urlObject.createObjectURL(export_blob)
      save_link.download = name
      save_link.click()
    },
    handleClose() {
      this.formData = {
        messageTitle: '', // 标题名称
        messageLevel: '',
        messageCategory: '',
        messageContent: '', // 事件详情
        sendPrecinctName: sessionStorage.getItem('departmentName'), // 发送人科室
        // sendPrecinctName: '',
        sendPhone: sessionStorage.getItem('phone'), // 发送人联系方式
        sendTime: '', // 发送时间
        receivePrecinctName: '', // 接收科室
        receivePerson: '', // 接受人员
        receivingPeople: '', // 接受人联系方式
        receiveTime: '', // 接受时间
        contents: [
          {
            receivePrecinctName: '', // 接收科室
            receivePerson: '', // 接受人员
            receivePhone: '' // 接受人员电话
          }
        ]
      }
      this.fileList = []
      this.$emit('update:detailsDialog', false)
      if (this.title !== '新增消息') {
        this.$emit('update:detailsDialogData', {})
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.NotificationManagerAdd {
  position: absolute;
  top: 460px;
  left: 45%;
  width: 85%;
  height: 90%;
  z-index: 99;
  transform: translate(-50%, -55%);
  background-color: #fff;
  border: 1px solid #f3f3f3;
  padding: 1px;
  .informationTitle {
    cursor: pointer;
    display: flex;
    justify-content: space-between;
    line-height: 64px;
    padding: 0 15px;
    background-color: #f3f3f3;
    margin-bottom: 10px;
  }
  .fileList {
    margin-left: 50px;
    font-size: 14px;
    position: relative;
    height: 180px;
    width: 90%;
    overflow-x: hidden;
    overflow-y: scroll;
    .attachmentUpload {
      position: absolute;
      top: 0;
      left: 70px;
    }
  }

  .info_body {
    height: 75%;
    overflow-x: hidden;
    overflow-y: scroll;
    .receivingItem {
      .addTime {
        cursor: pointer;
        font-size: 28px;
        background: #fff;
        color: #000;
        width: 22px;
        height: 22px;
        font-weight: 900;
        line-height: 18px;
        text-align: center;
        border-radius: 5px;
        margin-right: 10px;
        margin-top: 10px;
      }
      .delTime {
        cursor: pointer;
        font-size: 28px;
        background: #000;
        color: #fff;
        width: 22px;
        height: 22px;
        font-weight: 900;
        line-height: 14px;
        text-align: center;
        border-radius: 5px;
        border: #fff solid 2px;
        margin-top: 10px;
      }
    }
  }
  .dialog-footer {
    display: flex;
    justify-content: space-around;
  }
  // z-index: 10;
}
.imgItem {
  display: flex;
  justify-content: center;
  align-items: center;
}
.img {
  width: 90%;
  height: 450px;
  margin: 0 12px;
  /* border: black 1px solid;
  background-color: darkgrey; */
}
/* 图片自适应的样式 */
.img-object {
  object-fit: contain;
  object-position: 50% 20%;
}
</style>
