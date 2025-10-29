<template>
  <el-dialog
    title="编辑事件"
    :visible.sync="dialogVisible"
    width="1160px"
    :modal-append-to-body="false"
    :append-to-body="false"
    :before-close="handleClose">
    <div class="f-title">
      <div class="t">基本信息</div>
    </div>
    <el-form :inline="true" ref='form' :rules="rules" :model="form" class="EmergencyManagementEdit" label-width="120px">
      <el-form-item label="事件标题" prop="title">
        <el-input clearable v-model="form.title" placeholder="请输入事件标题"></el-input>
      </el-form-item>
      <!--<el-form-item label="一级事件类型" prop="type">
        <el-select v-model="form.type" placeholder="一级事件类型" clearable @change="firstItemChange">
          <el-option v-for="(item) in firstList" :value="item" :label="item" :key="item"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="二级事件类型" prop="type2">
        <el-select v-model="form.type2" placeholder="二级事件类型" clearable>
          <el-option v-for="(item) in secondList" :value="item" :label="item" :key="item"></el-option>
        </el-select>
      </el-form-item>-->
      <el-form-item label="事件类别" prop="typeSelect">
        <el-cascader ref="cascader"
          v-model="form.typeSelect" :props="{ checkStrictly: true }"
                     :options="typeEmergencyList"
          clearable></el-cascader>
      </el-form-item>
      <el-form-item label="事发时间" prop="happenTime">
        <el-date-picker
          v-model="form.happenTime"
          type="datetime"
          value-format='yyyy-MM-dd HH:mm:ss'
          placeholder="选择事发时间"
          clearable>
        </el-date-picker>
      </el-form-item>
      <el-form-item label="事件等级" prop="eventLevel">
        <el-select v-model="form.eventLevel" placeholder="事件等级" clearable>
          <el-option v-for="(v,i) in eventLevelDic" :key="i" :label="v" :value="v"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="事发地点" prop="address" class="whole-line">
        <el-input clearable v-model="form.address" placeholder="请输入事发地点"></el-input>
      </el-form-item>
      <el-form-item label="经度" prop="gpsx">
        <el-input clearable v-model="form.gpsx" placeholder="请点击此处选择经纬度位置">
          <el-button slot="append" icon="el-icon-location" @click="handlePlace(false)"></el-button>
        </el-input>
      </el-form-item>
      <el-form-item label="纬度" prop="gpsy">
        <el-input clearable v-model="form.gpsy" placeholder="请点击此处选择经纬度位置"
        ><el-button slot="append" icon="el-icon-location" @click="handlePlace(false)"></el-button
        ></el-input>
      </el-form-item>
      <el-form-item label="伤亡情况" prop="details" class="whole-line">
        死亡：<el-input clearable v-model="form.casualtyDeath"  style="width: 100px"></el-input>人

        受伤：<el-input clearable v-model="form.casualtyInjured"  style="width: 100px"></el-input>人

        涉险：<el-input clearable v-model="form.casualtyDanger"  style="width: 100px"></el-input>人
      </el-form-item>
      <el-form-item label="事件描述" prop="details" class="whole-line">
        <el-input clearable v-model="form.details" type="textarea" :rows="2" placeholder="请输入事件描述"></el-input>
      </el-form-item>
      <el-form-item label="上报人" prop="reportPeople">
        <el-input clearable v-model="form.reportPeople" placeholder="请输入上报人"></el-input>
      </el-form-item>
      <el-form-item label="联系方式" prop="reportPeoplePhone">
        <el-input clearable v-model="form.reportPeoplePhone" placeholder="请输入联系方式"></el-input>
      </el-form-item>
      <el-form-item label="所属单位" prop="handleUnit">
        <el-select filterable v-model="form.handleUnit" disabled placeholder="请选择办理单位" clearable>
          <el-option v-for="item in optionsResource" :value="item.precinctId" :label="item.precinctName" :key="item.precinctId"></el-option>
        </el-select>
        <!-- <el-cascader
          v-model="form.depart" :options="optionsResource"
          :props="resourceCascaderProps" @change="handleChanges"
          placeholder="请选择所属单位" clearable filterable disabled>
        </el-cascader> -->
      </el-form-item>
      <el-form-item label="附件" class="whole-line">
        <el-upload ref="upload" action="/" :multiple="false" :file-list="form.fileList" :show-file-list="true"
                   :on-change="(file, fileList) => { return handleChange(file, fileList, form) }"
                   :on-remove="(file, fileList) => { return handleRemove(file, fileList, form) }"
                   :auto-upload="false">
          <el-button slot="trigger" size="small" type="primary" >上传附件</el-button>
        </el-upload>
        <a class="link-a" :href="form.attachment" title="当前附件" v-if="form.attachment && form.attachmentFile">{{ form.attachmentFile }}</a>
      </el-form-item>
    </el-form>
    <div class="f-title" v-if="eventInfo.instructions && eventInfo.instructions.length">
      <div class="t">领导指示信息</div>
    </div>
    <el-form v-for="(form2, index) in instructions" :key="index + 'instructions'" :inline="true" ref="form2" :rules="rules2" :model="form2" class="EmergencyManagementEdit" label-width="120px">
      <el-form-item label="指示时间" prop="instructionsTime">
        <el-date-picker
          v-model="form2.instructionsTime"
          type="datetime"
          value-format='yyyy-MM-dd HH:mm:ss'
          placeholder="选择指示时间"
          clearable>
        </el-date-picker>
      </el-form-item>
      <el-form-item label="指示内容" prop="content" class="whole-line">
        <el-input clearable v-model="form2.content" type="textarea" :rows="2" placeholder="请输入指示内容"></el-input>
      </el-form-item>
      <el-form-item label="指示文件" prop="attachment" class="whole-line">
        <el-upload ref="upload2" action="/" :multiple="false" :file-list="form2.fileList" :show-file-list="true"
                   :on-change="(file, fileList) => { return handleChange(file, fileList, form2) }"
                   :on-remove="(file, fileList) => { return handleRemove(file, fileList, form2) }"
                   :auto-upload="false">
          <el-button slot="trigger" size="small" type="primary" :disabled="form2.fileList && form2.fileList.length !== 0">上传文档</el-button>
        </el-upload>
        <a class="link-a" :href="form2.attachment" title="当前指示文件" v-if="form2.attachment && form2.attachmentFile">{{ form2.attachmentFile }}</a>
      </el-form-item>
    </el-form>
    <div class="f-title" v-if="eventInfo.progress && eventInfo.progress.length">
      <div class="t">事件进展信息</div>
    </div>
    <el-form v-for="(form3, index) in progress" :key="index + 'progress'" :inline="true" ref="form3" :rules="rules3" :model="form3" class="EmergencyManagementEdit" label-width="120px">
      <el-form-item label="进展时间" prop="progressTime">
        <el-date-picker
          v-model="form3.progressTime"
          type="datetime"
          value-format='yyyy-MM-dd HH:mm:ss'
          placeholder="选择进展时间"
          clearable>
        </el-date-picker>
      </el-form-item>
      <el-form-item label="进展类型" prop="progressType">
        <el-select v-model="form3.progressType" placeholder="进展类型" clearable>
          <el-option v-for="(v,i) in typeList" :key="i" :label="v" :value="v"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="进展描述" prop="details" class="whole-line">
        <el-input clearable v-model="form3.details" type="textarea" :rows="2" placeholder="请输入进展描述"></el-input>
      </el-form-item>
      <el-form-item label="上报人" prop="reportPeople">
        <el-input clearable v-model="form3.reportPeople" placeholder="请输入上报人"></el-input>
      </el-form-item>
      <el-form-item label="联系方式" prop="reportPeoplePhone">
        <el-input clearable v-model="form3.reportPeoplePhone" placeholder="请输入联系方式"></el-input>
      </el-form-item>
      <el-form-item label="附件" prop="attachmentFile" class="whole-line">
        <el-upload ref="upload" action="/" :multiple="false" :file-list="form3.fileList" :show-file-list="true"
                   :on-change="(file, fileList) => { return handleChange(file, fileList, form3) }"
                   :on-remove="(file, fileList) => { return handleRemove(file, fileList, form3) }"
                   :auto-upload="false">
          <el-button slot="trigger" size="small" type="primary" :disabled="form3.fileList && form3.fileList.length !== 0">上传附件</el-button>
        </el-upload>
        <a class="link-a" :href="form3.attachment" title="当前附件" v-if="form3.attachment && form3.attachmentFile">{{ form3.attachmentFile }}</a>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="handleClose()">取 消</el-button>
      <el-button type="primary" @click="handleOk">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import axios from "axios";
export default {
  name: "EmergencyManagementEdit",
  components: {},
  data() {
    return {
      eventInfo: {
        instructions: [],
        progress: [],
        info: {}
      },
      form: {
        typeSelect: [],
        reportPeople: "",
        type2: "",
        address: "",
        type: "",
        title: "",
        reportPeoplePhone: "",
        attachmentFile: "",
        attachment: "",
        details: "",
        id: "",
        gpsx: "",
        happenTime: "",
        status: "",
        gpsy: "",
        reportTime: "",
        fileList: [],
        file: null,
        kType: '1',
        eventLevel: '',
        casualtyDeath: '',
        casualtyInjured: '',
        casualtyDanger: '',
        handleUnit: ''
      },
      optionsResource: [],
      resourceCascaderProps: {
        checkStrictly: true,
        expandTrigger: 'hover',
        value: 'precinctId',
        label: 'precinctName'
      },
      instructions: [],
      progress: [],
      rules: {
        title: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ],
        typeSelect: [
          {required: true, message: '请选择后提交', trigger: 'blur'}
        ],
        happenTime: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ],
        address: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ],
        details: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ],
        eventLevel: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ],
        reportPeople: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ],
        reportPeoplePhone: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ],
        gpsx: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ],
        gpsy: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ]
        // handleUnit: [
        //   {required: true, message: '请输入后提交', trigger: 'blur'}
        // ]
      },
      rules2: {
        instructionsTime: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ],
        content: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ]
      },
      rules3: {
        details: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ],
        reportPeople: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ],
        progressTime: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ],
        progressType: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ],
        reportPeoplePhone: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ]
      },
      typeEmergencyList: [],
      typeList: ['续报', '终报'],
      eventLevelDic: []
    }
  },
  props: ['dialogVisible', 'eventId'],
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
      this.getEventInfo(this.eventId)
    }
  },
  mounted() {
    this.getTypeList()
    this.queryEventLevel()
    this.departList()
    this.$root.eventBus.$on('sendGps' + 'EssentialInformation', data => {
      console.log(data)
      data = data.split(',')
      this.form.gpsx = data[0]
      this.form.gpsy = data[1]
    })
  },
  methods: {
    getTypeList() {
      let url = '/gtw/EIMS-management/event/queryEventType'
      axios.get(url).then(r => {
        let data = r.data.data || []
        this.typeEmergencyList = this.changeTree(data)
      })
    },
    handleChanges (value) {
      this.$nextTick(() => {
        this.getInfoList()
      })
    },
    departList () {
      let self = this
      let url =
        localStorage.roleName === '超级管理员'
        ? `/community/precinctNew/select?type=2`
        : `/community/precinctNew/select?type=2&precinctId=${localStorage.streetId}`
      axios.get(url).then(function (response) {
        let data = response.data.data.data
        const obj = {}
        data.forEach((item) => {
          obj[item.precinctId] = item
        })
        const parentList = []
        data.forEach((item) => {
          const parent = obj[item.upPrecinctId]
          if (!parent) {
            parentList.push(item)
          }
        })
          // parentList.forEach((item) => {
          //   let arr = []
          //   if (item.children) {
          //     item.children.forEach((r) => {
          //       if (r.children) {
          //         r.children.forEach((s) => {
          //           arr.push(s)
          //         })
          //       }
          //     })
          //   }
          //   delete item.children
          //   item.children = arr
          // })
        self.optionsResource = parentList
      })
    },
    changeTree(data) {
      let arr = []
      if (typeof (data) === 'object') {
        for (let key in data) {
          let obj = {
            label: key,
            value: key,
            children: []
          }
          data[key].forEach(item => {
            let obj2 = {
              label: item,
              value: item
            }
            if (item) {
              obj.children.push(obj2)
            }
          })
          arr.push(obj)
        }
      }
      return arr
    },
    queryEventLevel() {
      let url = '/gtw/EIMS-management/event/queryEventLevel'
      axios.get(url).then(res => {
        this.eventLevelDic = res.data.data
      }).catch(() => {
      })
    },
    handlePlace(type) {
      console.log(this.form)
      this.$root.eventBus.$emit('getGps', {
        emitEventName: 'EssentialInformation',
        oriGpsInfo: this.form.gpsx + ',' + this.form.gpsy
      })
    },
    getEventInfo(eventId) {
      let url = '/gtw/EIMS-management/event/queryEventInfo?eventId=' + eventId
      axios.get(url).then(res => {
        let data = res.data.data
        this.eventInfo = data || this.eventInfo
        this.form = Object.assign(this.form, this.form, this.eventInfo.info)
        this.eventInfo.instructions.forEach(item => {
          item.file = null
          item.fileList = []
          item.kType = '2'
        })
        this.eventInfo.progress.forEach(item => {
          item.file = null
          item.fileList = []
          item.kType = '3'
        })
        this.form.typeSelect = []
        if (this.eventInfo.info.type) {
          this.form.typeSelect.push(this.eventInfo.info.type)
        }
        if (this.eventInfo.info.type2) {
          this.form.typeSelect.push(this.eventInfo.info.type2)
        }
        this.instructions = [...this.eventInfo.instructions]
        this.progress = [...this.eventInfo.progress]
        console.log(this.form)
        console.log(this.instructions)
        console.log(this.progress)
      })
    },
    handleChange(file, fileList, form) {
      form.file = file
      form.fileList = fileList
      console.log(form)
      if (form.file && form.fileList.length) {
        let formData = new FormData()
        form.file = form.fileList[0]
        // this.$refs.upload.clearFiles()
        const types = form.file.name.split('.')[1]
        const fileType = ['xlsx', 'xlc', 'xlm', 'xls', 'xlt', 'xlw', 'csv', 'doc', 'docx'].some(item => item === types)
        if (!fileType) {
          this.$message('格式错误！请重新选择')
          return
        }
        formData.append('file', form.file.raw)
        formData.append('id', form.id)
        formData.append('type', form.kType)
        let url = '/gtw/EIMS-management/event/editEventFile'
        axios.post(url, formData).then(r => {
          if (r.data.code === 200 || r.data.code === 201) {
            this.$message.success('文件上传成功')
            console.log(r.data.data)
            let data = r.data.data || {}
            if (data.type === 'eventInfo') {
              this.form.attachment = data.attachment
              this.form.attachmentFile = data.attachmentFile
            } else if (data.type === 'instructions') {
              this.instructions.forEach(form => {
                if (form.id === data.id) {
                  form.attachment = data.attachment
                  form.attachmentFile = data.attachmentFile
                }
              })
            } else {
              this.progress.forEach(form => {
                if (form.id === data.id) {
                  form.attachment = data.attachment
                  form.attachmentFile = data.attachmentFile
                }
              })
            }
          } else {
            this.$message.error(r.data.message)
          }
        })
      }
    },
    handleRemove(file, fileList, form) {
      form.file = file
      form.fileList = fileList
      console.log(form)
    },
    handleClose(done) {
      this.reset()
      this.$emit("update:dialogVisible", false)
    },
    reset() {
      this.$refs.form.resetFields()
      this.form.typeSelect = []
      this.instructions = []
      this.progress = []
    },
    handleOk() {
      if (this.form.typeSelect.length > 1) {
        this.form.type2 = this.form.typeSelect[1]
      }
      if (this.form.typeSelect.length > 0) {
        this.form.type = this.form.typeSelect[0]
      }
      this.$refs['form'].validate(valid => {
        if (valid) {
          let isValid2 = true
          this.instructions.forEach((form2, index2) => {
            this.$refs.form2[index2].validate(valid2 => {
              if (!valid2) {
                isValid2 = false
                console.log('error submit!!');
                return false;
              }
            })
          })
          setTimeout(() => {
            if (isValid2) {
              let isValid3 = true
              this.progress.forEach((form3, index3) => {
                this.$refs.form3[index3].validate(valid3 => {
                  if (!valid3) {
                    isValid3 = false
                    console.log('error submit!!');
                    return false;
                  }
                })
              })
              setTimeout(() => {
                if (isValid3) {
                  // 检测全部成功后执行网络请求
                  let formData = new FormData()
                  let data = {
                    eventInfo: this.form,
                    instructions: this.instructions,
                    progress: this.progress
                  }
                  formData.append('data', JSON.stringify(data))
                  let url = '/gtw/EIMS-management/event/editEvent'
                  axios.post(url, formData).then(r => {
                    if (r.data.code === 200 || r.data.code === 201) {
                      this.$message.success('操作成功')
                      this.$emit('success')
                      this.handleClose()
                    } else {
                      this.$message.error(r.data.message)
                    }
                  })
                }
              }, 500)
            }
          }, 500)
        } else {
          console.log('error submit!!');
          return false;
        }
      })
    }
  }
}
</script>

<style lang="scss">
.EmergencyManagementEdit.el-form--inline {
  .el-select {
    display: block;
  }
  .el-form-item__content {
    width: 225px;
  }

  .el-date-editor {
    width: 225px;
  }

  .whole-line {
    width: 100%;

    .el-form-item__content {
      position: relative;
      width: 945px;
    }
  }

  .half-line {

    .el-form-item__content {
      width: 585px;
    }
  }

  .link-a {
    position: absolute;
    top: 0;
    left: 100px;
  }
}
.f-title {
  position: relative;
  height: 30px;
  border-bottom: 2px solid #EFF2F5;
  margin: 0 55px 30px 55px;

  .t {
    float: left;
    font-size: 18px;
    font-weight: bold;
    color: #4E5A6E;
    line-height: 29px;
    border-bottom: 2px solid #0093FF;
  }
}
</style>
