<template>
  <div class="addDialog">
    <el-dialog
      title="核查反馈"
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
          <el-form-item label="核查人" prop="eventTitle">
            <el-input clearable v-model="form.eventTitle" placeholder="请输入事故标题"></el-input>
          </el-form-item>
          <el-form-item label="下发时间" prop="eventTime">
            <el-date-picker
              type="datetime"
              placeholder="选择时间"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
              v-model="form.eventTime"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="下发要求" prop="summary" style="display: flex">
            <el-input
              clearable
              v-model="form.summary"
              type="textarea"
              :rows="6"
              placeholder="请输入概述"
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
  name: 'addDialog',
  //   components: { chooseMap },
  props: ['dialogVisible', 'formInfo', 'isCode'],
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
      if (v) {
        this.addForm.video = ''
        this.videoShow = ''
        this.videoHidden = false
        this.getSiteList()
        this.getSiteList1()
        this.getSiteList2()
        this.getSiteList3()
        this.getSiteList4()
        this.form = Object.assign({}, JSON.parse(JSON.stringify(this.formInfo)))
        if (this.formInfo.lon && this.formInfo.lat) {
          this.form.gps = this.formInfo.lon + ',' + this.formInfo.lat
        }
        console.log('新增编辑查看this.form', this.form)
        if (this.isCode === 0 || this.isCode === 1) {
          this.videoHidden = true
          this.form.file = this.formInfo.file
          this.videoShow = this.formInfo.file
          if (this.formInfo.file) {
            this.addForm.video = new window.File([this.formInfo.file], this.formInfo.file.split('/').pop(), {
              type: 'video/mp4'
            })
          }
          console.log(this.addForm.video, this.videoShow)
        }
      }
    }
  },
  data() {
    return {
      rules: {
        eventTitle: [{ required: true, message: '请输入事故标题', trigger: 'blur' }],
        typeName: [{ required: true, message: '请选择事故类型', trigger: 'blur' }],
        streetName: [{ required: true, message: '请选择街道名称', trigger: 'blur' }],
        sourceName: [{ required: true, message: '请选择事故来源', trigger: 'blur' }],
        communityName: [{ required: true, message: '请选择社区名称', trigger: 'blur' }],
        eventTime: [{ required: true, message: '选择时间', trigger: 'blur' }],
        smallTypeName: [{ required: true, message: '请选择小类名称', trigger: 'blur' }],
        levelName: [{ required: true, message: '请选择事件级别', trigger: 'blur' }],
        monitorUnit: [{ required: true, message: '请输入监督单位', trigger: 'blur' }],
        minorNum: [{ required: true, message: '请输入轻伤人员', trigger: 'blur' }],
        seriousNum: [{ required: true, message: '请输入重伤人员', trigger: 'blur' }],
        deathNum: [{ required: true, message: '请输入死亡人员', trigger: 'blur' }],
        economicLoss: [{ required: true, message: '请输入经济损失', trigger: 'blur' }],
        isConclusion: [{ required: true, message: '请选择是否总结', trigger: 'blur' }],
        isCommand: [{ required: true, message: '请选择是否指挥', trigger: 'blur' }],
        // gps: [{ required: true, message: '选择经纬度', trigger: 'blur' }],
        occurAddress: [{ required: true, message: '请输入地址', trigger: 'blur' }],
        summary: [{ required: true, message: '请输入概述', trigger: 'blur' }],
        impactLevel: [{ required: true, message: '请选择影响级别', trigger: 'blur' }]
      },
      loading: false,
      kindList: [],
      typeList: [],
      sourceList: [],
      typeList1: [],
      streetList: [],
      communityList: [],
      siteList: [
        { id: 0, name: '是' },
        { id: 1, name: '否' }
      ],
      impactList: [
        { id: 0, name: '1级' },
        { id: 1, name: '2级' },
        { id: 2, name: '3级' },
        { id: 3, name: '4级' }
      ],
      levelList: [
        { id: '35', name: '较大级别' },
        { id: '36', name: '一般级别' },
        { id: '37', name: '未达一般' },
        { id: '38', name: '无级别' }
      ],
      videoShow: '',
      addForm: {
        video: ''
      },
      form: {
        eventTitle: '',
        typeName: '',
        streetName: '',
        communityName: '', // 数量
        levelName: '', // 单位
        eventTime: '',
        smallTypeName: '',
        impactLevel: '', // 所属场所Id
        monitorUnit: '', // 所属场所Id
        occurAddress: '',
        minorNum: '',
        seriousNum: '',
        deathNum: '',
        economicLoss: '', // 备注
        isConclusion: '',
        summary: '',
        isCommand: '',
        eventNodeList: '',
        gps: '',
        file: '',
        level: '',
        sourceName: ''
      },
      gpsInfo: '',
      dialogMap: false,
      timeout: null,
      videoHidden: false
    }
  },
  // mounted() {
  //   this.getSiteList()
  // },
  computed: {
    sourceListSort() {
      if (this.isCode === 2) {
        return this.sourceList.filter((t) => t.dictName !== '突发事件')
      } else {
        return this.sourceList
      }
    }
  },
  methods: {
    changeType(val) {
      if (val) {
        this.typeList.forEach((item) => {
          if (val === item.dictName) {
            this.getSiteList2(item.id)
          }
        })
      }
    },
    changeStreet(val) {
      if (val) {
        this.streetList.forEach((item) => {
          if (val === item.precinctName) {
            this.getSiteList3(item.precinctId)
          }
        })
      }
    },
    fileChange(e) {
      var files = e.target.files || e.dataTransfer.files
      if (!files.length) return
      let name = files[0].name.toLowerCase()
      if (!/\.(avi|wmv|mpeg|mp4|mov|mkv|flv|f4v|m4v|rmvb|rm|3gp|dat|ts|mts|vob)$/.test(name)) {
        this.$message.warning('请上传视频')
        return
      }
      // 这里是file文件
      this.addForm.video = files[0]
      var reader = new FileReader()
      reader.readAsDataURL(files[0])
      this.videoHidden = false
      reader.onload = () => {
        // 这里是一段base64，用于视频回显用
        this.videoShow = reader.result
      }
      this.fileUpload(this.addForm.video)
    },
    fileUpload(v) {
      let url = '/binhuapis/file/uploadMp4'
      this.$message.warning('视频正在上传中。。。')
      let formData = new FormData()
      formData.append('file', v)
      formData.append('group', 'system')
      request.post(url, formData).then((res) => {
        if (res.data.code === 200) {
          this.form.file = res.data.data
          this.$message.success('视频上传成功')
        }
      })
    },
    gpsChoose(e) {
      this.form.gps = e.value
      this.dialogMap = false
      console.log(e)
    },
    jump(e) {
      this.gpsInfo = this.form.gps
      this.dialogMap = true
    },
    getSiteList() {
      let url = '/binhuapis/dict/dictTree?parentId=1'
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          let data = res.data.data || []
          this.typeList = data
        }
      })
    },
    // 来源
    getSiteList1() {
      let url = '/binhuapis/dict/dictTree?parentId=9'
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          let data = res.data.data || []
          this.sourceList = data.filter((item) => item.dictName === '演练事件' || item.dictName === '突发事件')
        }
      })
    },
    getSiteList2(typeId) {
      let url = '/binhuapis/dict/dictTree?parentId=4'
      if (typeId) {
        url = url + '&typeId=' + typeId
      }
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          let data = res.data.data || []
          this.typeList1 = data
        }
      })
    },
    getSiteList3(streetId) {
      let url = '/auth/v2/precinct/queryPrecinctByKind?precinctKind=120'
      if (streetId) {
        url = url + '&streetId=' + streetId
      }
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          let data = res.data.data || []
          this.communityList = data
        }
      })
    },
    getSiteList4() {
      let url = '/auth/v2/precinct/queryPrecinctByKind?precinctKind=110'
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          let data = res.data.data || []
          this.streetList = data
        }
      })
    },
    objPlanSearch(queryString, cb) {
      // console.log('objPlanSearch', queryString, cb)
      let restaurants = this.siteList
      // console.log('restaurants', restaurants)
      let results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants
      // // 调用 callback 返回建议列表的数据
      cb(results)
    },
    createFilter(queryString) {
      // console.log('createFilter', queryString)
      return (restaurant) => {
        // return restaurant.value.toUpperCase().match(queryString.toUpperCase())
        return restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) > -1
      }
    },
    checkObjPlan(v) {
      // console.log('checkObjPlan', v)
      this.form.siteId = v.id
      this.form.siteName = v.value
      this.form.person = v.person
      this.form.phone = v.phone
      this.form.address = v.address
    },
    afterClearable(v) {
      // console.log('afterClearable', v)
      if (!v) {
        this.form.siteName = '' // 对象名称
      }
      this.form.siteId = ''
      this.form.siteName = ''
      this.form.person = ''
      this.form.phone = ''
      this.form.address = ''
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
      //   this.addForm.video = ''
      //   this.videoShow = ''
      //   document.getElementById('video_file').value = ''
      this.form = {}
      // 清空表单校验，避免再次进来会出现上次校验的记录
      this.$refs.formLabel.clearValidate()
    },
    handleOk() {
      this.$refs.formLabel.validate((valid) => {
        if (valid) {
          if (this.form.gps) {
            this.submitAll()
          } else {
            this.$message.warning('经纬度不能为空')
          }
        } else {
          this.$message.warning('检查表单输入内容的合法性！')
          // console.log('error submit!!')
          return false
        }
      })
    },
    submitAll() {
      this.loading = true
      if (this.form.levelName) {
        this.levelList.forEach((item) => {
          if (this.form.levelName === item.name) {
            this.form.level = item.id
          }
        })
      }
      if (this.form.streetName) {
        this.streetList.forEach((item) => {
          if (this.form.streetName === item.precinctName) {
            this.form.streetId = item.precinctId
          }
        })
      }
      this.communityList.forEach((item) => {
        if (this.form.communityName === item.precinctName) {
          this.form.communityId = item.precinctId
        }
      })
      this.typeList.forEach((item) => {
        if (this.form.typeName === item.dictName) {
          this.form.typeId = item.id
        }
      })
      this.typeList1.forEach((item) => {
        if (this.form.smallTypeName === item.dictName) {
          this.form.smallTypeId = item.id
        }
      })
      this.sourceList.forEach((item) => {
        if (this.form.sourceName === item.dictName) {
          this.form.sourceId = item.id
        }
      })
      if (this.form.gps) {
        let gps = this.form.gps.split(',')
        this.form.lon = gps[0].trim()
        this.form.lat = gps[1].trim()
      }
      let newForm = {
        eventTitle: this.form.eventTitle,
        typeName: this.form.typeName,
        typeId: this.form.typeId,
        streetId: this.form.streetId,
        streetName: this.form.streetName,
        sourceId: this.form.sourceId,
        sourceName: this.form.sourceName,
        communityId: this.form.communityId, // 数量
        communityName: this.form.communityName, // 数量
        levelName: this.form.levelName, // 单位
        level: this.form.level,
        eventTime: this.form.eventTime,
        smallTypeName: this.form.smallTypeName,
        smallTypeId: this.form.smallTypeId,
        impactLevel: this.form.impactLevel, // 所属场所Id
        monitorUnit: this.form.monitorUnit, // 所属场所Id
        occurAddress: this.form.occurAddress,
        minorNum: this.form.minorNum,
        seriousNum: this.form.seriousNum,
        deathNum: this.form.deathNum,
        economicLoss: this.form.economicLoss, // 备注
        isConclusion: this.form.isConclusion,
        summary: this.form.summary,
        isCommand: this.form.isCommand,
        lon: this.form.lon,
        lat: this.form.lat,
        file: this.form.file
      }
      let url = ''
      if (this.formInfo && this.formInfo.id) {
        url = '/binhuapis/dapeng/event/edit'
        newForm.id = this.formInfo.id
        // console.log('参数: ', newForm, 'url: ', url)
      } else {
        url = '/binhuapis/dapeng/event/add?userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")
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
.addDialog {
  .el-input {
    line-height: 32px;
    width: 200px;
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
</style>
