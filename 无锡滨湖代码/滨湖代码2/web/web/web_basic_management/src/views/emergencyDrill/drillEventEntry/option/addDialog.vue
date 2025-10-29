<template>
  <div class="addDialog">
    <el-dialog
      :title="isCode == 0 ? '修改' : isCode === 1 ? '详情' : '新增'"
      :visible.sync="dialogVisible"
      width="1122px"
      :modal-append-to-body="false"
      :append-to-body="false"
      :before-close="handleClose"
    >
      <div class="info_body" v-loading="loading">
        <div class="title-bar">
          <div class="bar-icon"></div>
          <p class="bar-text">基本信息</p>
        </div>
        <el-form
          :inline="true"
          ref="formLabel"
          :rules="rules"
          :model="form"
          label-width="110px"
          :disabled="isCode == 1"
        >
          <el-form-item label="事故标题" prop="eventTitle">
            <el-input clearable v-model="form.eventTitle" placeholder="请输入事故标题"></el-input>
          </el-form-item>
          <el-form-item label="事故类型" prop="typeName">
            <el-select
              filterable
              v-model="form.typeName"
              placeholder="请选择事故类型"
              clearable
              @change="changeType"
            >
              <el-option
                v-for="item in typeList"
                :value="item.dictName"
                :label="item.dictName"
                :key="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="街道名称" prop="streetName">
            <el-select
              filterable
              v-model="form.streetId"
              placeholder="请选择街道名称"
              clearable
              @change="changeStreet"
            >
              <el-option
                v-for="item in streetList"
                :value="item.precinctId"
                :label="item.precinctName"
                :key="item.precinctId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="事故来源" prop="sourceName">
            <el-select filterable v-model="form.sourceName" placeholder="请选择事故来源" clearable>
              <el-option
                v-for="item in sourceListSort"
                :value="item.dictName"
                :label="item.dictName"
                :key="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="社区名称" prop="communityName">
            <el-select
              filterable
              v-model="form.communityId"
              placeholder="请选择社区名称"
              clearable
              @change="changeCommunityId"
            >
              <el-option
                v-for="item in filterCommunityList"
                :value="item.precinctId"
                :label="item.precinctName"
                :key="item.precinctId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="时间" style="margin-bottom: 0" prop="eventTime">
            <el-date-picker
              type="datetime"
              placeholder="选择时间"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
              v-model="form.eventTime"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="小类名称" prop="smallTypeName">
            <el-select filterable v-model="form.smallTypeName" placeholder="请选择小类名称" clearable>
              <el-option
                v-for="item in typeList1"
                :value="item.dictName"
                :label="item.dictName"
                :key="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="事件级别" prop="levelName">
            <el-select filterable v-model="form.levelName" placeholder="请选择事件级别" clearable>
              <el-option
                v-for="item in levelList"
                :value="item.name"
                :label="item.name"
                :key="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="影响级别" prop="impactLevel">
            <el-select filterable v-model="form.impactLevel" placeholder="请选择影响级别" clearable>
              <el-option
                v-for="item in impactList"
                :value="item.name"
                :label="item.name"
                :key="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="监督单位" prop="monitorUnit">
            <el-input clearable v-model="form.monitorUnit" placeholder="请输入监督单位"></el-input>
          </el-form-item>
          <el-form-item label="轻伤人员" prop="minorNum">
            <el-input clearable type="number" min="0" v-model="form.minorNum" placeholder="请输入轻伤人员"></el-input>
          </el-form-item>
          <el-form-item label="重伤人员" prop="seriousNum">
            <el-input
              clearable
              type="number"
              min="0"
              v-model="form.seriousNum"
              placeholder="请输入重伤人员"
            ></el-input>
          </el-form-item>
          <el-form-item label="死亡人员" prop="deathNum">
            <el-input clearable type="number" min="0" v-model="form.deathNum" placeholder="请输入死亡人员"></el-input>
          </el-form-item>
          <el-form-item label="经济损失" prop="economicLoss">
            <el-input
              style="width: 194px; margin-right: 10px"
              clearable
              type="number"
              min="0"
              v-model="form.economicLoss"
              placeholder="请输入经济损失"
            >
              <template slot="append">元</template>
            </el-input>
          </el-form-item>
          <el-form-item label="是否总结" prop="isConclusion">
            <el-select filterable v-model="form.isConclusion" placeholder="请选择是否总结" clearable>
              <el-option
                v-for="item in siteList"
                :value="item.name"
                :label="item.name"
                :key="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="是否指挥" prop="isCommand">
            <el-select filterable v-model="form.isCommand" placeholder="请选择是否指挥" clearable>
              <el-option
                v-for="item in siteList"
                :value="item.name"
                :label="item.name"
                :key="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="经纬度:" required>
            <el-input
              :disabled="isCode === 1"
              placeholder="选择经纬度"
              v-model="form.gps"
              @input="changeMessage()"
            >
              <template slot="append">
                <div @click="isCode !== 1 ? jump('1') : ''">
                  <i class="el-icon-location"></i>
                </div>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item label="地址" prop="occurAddress">
            <el-input clearable v-model="form.occurAddress" placeholder="请输入地址"></el-input>
          </el-form-item>
          <el-form-item label="概述" prop="summary" style="display: flex;margin-right: 130px;">
            <el-input
              clearable
              v-model="form.summary"
              type="textarea"
              :rows="6"
              placeholder="请输入概述"
            ></el-input>
          </el-form-item>
          <el-form-item label="上传文件">
            <div style="display: flex; align-items: flex-end; margin-top: 20px">
              <div class="upload_box" v-show="isCode === 2">
                <input
                  :disabled="isCode === 1"
                  ref="videoFile"
                  @change="fileChange($event)"
                  type="file"
                  multiple
                  style="font-size: 14px; margin-left: 20px"
                  id="video_file"
                  accept=".mp4, .avr"
                />
                <div v-show="isCode === 2">
                  <i class="iconfont icon-shangchuanshipin" style="font-size: 26px; color: #0052d9"></i>
                  <p style="color: #0052d9">上传视频</p>
                  <p style="color: #5b6472">上传的文件格式为mp4/avr</p>
                  <el-progress
                    v-if="uploadProgress > 0"
                    :percentage="uploadProgress"
                    :text-inside="false"
                    :color="customColors"
                    :stroke-width="10"
                  />
                </div>
              </div>
              <div class="upload_box" v-show="isCode === 2">
                <input
                  :disabled="isCode === 1"
                  ref="pictureFile"
                  @change="picChange($event)"
                  type="file"
                  multiple
                  style="font-size: 14px; margin-left: 20px"
                  id="video_file"
                  accept=".png, .jpg"
                />
                <div>
                  <i class="iconfont icon-shangchuantupian" style="font-size: 26px; color: #0052d9"></i>
                  <p style="color: #0052d9">上传图片</p>
                  <p style="color: #5b6472">上传的文件格式为png/jpg</p>
                  <el-progress
                    v-if="uploadPictureProgress > 0"
                    :percentage="uploadPictureProgress"
                    :text-inside="false"
                    :color="customColors"
                    :stroke-width="10"
                  />
                </div>
                <div v-show="isCode === 1">{{ pictureFileName }}</div>
              </div>
              <div class="upload_box" v-show="isCode === 2">
                <input
                  :disabled="isCode === 1"
                  ref="audioFile"
                  @change="audioChange($event)"
                  type="file"
                  multiple
                  style="font-size: 14px; margin-left: 20px"
                  id="video_file"
                  accept=".mp3, .wav"
                />
                <div>
                  <i class="iconfont icon-yuyinwenjian" style="font-size: 26px; color: #0052d9"></i>
                  <p style="color: #0052d9">上传音频</p>
                  <p style="color: #5b6472">上传的文件格式为mp3/wav</p>
                  <el-progress
                    v-show="uploadAudioProgress !== 0"
                    :percentage="uploadAudioProgress"
                    :text-inside="false"
                    :color="customColors"
                    :stroke-width="10"
                  ></el-progress>
                </div>
              </div>
              <div v-show="isCode === 1" style="display: flex; gap: 10px; align-items: center">
                <video
                  class="video"
                  style="width: 210px; height: 140px"
                  controls
                  :src="form.fileVideo"
                />
                <video
                  v-for="(voice, index) in eventFileVoices"
                  class="video"
                  style="width: 210px; height: 40px"
                  controls
                  :src="voice"
                  :key="index"
                />
                <el-image
                  :fit="'cover'"
                  style="width: 210px; height: 140px"
                  :src="eventImages[0]"
                  :preview-src-list="eventImages"
                ></el-image>
              </div>
            </div>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
        <el-button type="primary" @click="handleOk" v-if="isCode !== 1">确 定</el-button>
      </div>
      <el-dialog
        :visible.sync="dialogMap"
        :modal="false"
        :title="'选择经纬度'"
        :modal-append-to-body="false"
        :append-to-body="false"
      >
        <chooseMap :gpsInfo="gpsInfo" @gpsChoose="gpsChoose" />
      </el-dialog>
    </el-dialog>
  </div>
</template>
<script>
import request from '@/utils/request'
import chooseMap from '@/components/chooseMapDialog'

export default {
  name: 'addDialog',
  components: { chooseMap },
  props: ['dialogVisible', 'formInfo', 'isCode'],
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
      if (v) {
        this.addForm.video = ''
        this.addForm.picture = ''
        this.addForm.audio = ''
        this.videoShow = ''
        this.videoHidden = false
        this.getSiteList()
        this.getSiteList1()
        this.getSiteList2()
        this.getRegionPrecinct()
        this.getRegionPrecinctCommunityList()
        // this.getSiteList3()
        // this.getSiteList4()
        this.form = Object.assign({}, JSON.parse(JSON.stringify(this.formInfo)))
        if (this.formInfo.lon && this.formInfo.lat) {
          this.form.gps = this.formInfo.lon + ',' + this.formInfo.lat
        }
        if (this.isCode === 0 || this.isCode === 1) {
          this.videoHidden = true
          this.form.fileVideo = this.formInfo.fileVideo
          this.form.filePicture = this.formInfo.filePicture
          this.form.fileVoice = this.formInfo.fileVoice
          this.videoFileName = this.findName(this.formInfo.fileVideo)
          this.pictureFileName = this.findName(this.formInfo.filePicture)
          this.audioFileName = this.findName(this.formInfo.fileVoice)
          // 播放视频
          // this.videoShow = this.formInfo.file
          if (this.formInfo.file) {
            this.addForm.video = new window.File([this.formInfo.file], this.formInfo.file.split('/').pop(), {
              type: 'video/mp4'
            })
          }
          console.log(this.addForm.video, this.videoShow)
        }
      }
    },
    'form.communityName'(val) {
      if (!val) return
      if (this.isCode === 1) return
      const [community] = this.communityList.filter((item) => {
        return item.precinctName === this.form.communityName
      })
      const [street] = this.streetList.filter((item) => item.precinctId === community.up_precinct_id)
      this.form.streetName = street.precinctName
    }
  },
  data() {
    // const phoneCheck = (rule, value, callback) => {
    //   let isPhone = /^[1][3-9]{1}[0-9]{9}$/ // 手机号码
    //   let isMob = /^([0-9]{3,4}-)?[0-9]{7,8}$/ // 座机格式
    //   if (!(value === null || value === undefined || value === '')) {
    //     if (isMob.test(value) || isPhone.test(value)) {
    //       callback()
    //     } else {
    //       callback(new Error('非有效电话号码！'))
    //     }
    //   } else {
    //     callback()
    //   }
    // }
    return {
      rules: {
        eventTitle: [{ required: true, message: '请输入事故标题', trigger: 'blur' }],
        typeName: [{ required: true, message: '请选择事故类型', trigger: 'blur' }],
        streetId: [{ required: true, message: '请选择街道名称', trigger: 'blur' }],
        sourceName: [{ required: true, message: '请选择事故来源', trigger: 'blur' }],
        communityId: [{ required: true, message: '请选择社区名称', trigger: 'blur' }],
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
      streetListCopy: [],
      communityList: [],
      filterCommunityList: [],
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
      pictureShow: '',
      audioShow: '',
      addForm: {
        video: '',
        picture: '',
        audio: ''
      },
      form: {
        eventTitle: '',
        typeName: '',
        streetId: '',
        streetName: '',
        communityId: '',
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
        fileVideo: [],
        filePicture: [],
        fileVoice: [],
        level: '',
        sourceName: ''
      },
      videoShuzu: [],
      pictureShuzu: [],
      audioShuzu: [],
      gpsInfo: '',
      dialogMap: false,
      timeout: null,
      videoHidden: false,
      pictureHidden: false,
      audioHidden: false,
      uploadProgress: 0,
      uploadPictureProgress: 0,
      uploadAudioProgress: 0,
      videoFileName: '',
      pictureFileName: '',
      audioFileName: '',
      customColors: [
        { color: '#E2F3D9', percentage: 0 },
        { color: '#67C23A', percentage: 100 }
      ]
    }
  },
  // mounted() {
  //   this.getSiteList()
  // },
  computed: {
    eventFileVoices() {
      if (!this.formInfo.fileVoice) return []
      return this.formInfo.fileVoice.split(',')
    },
    eventImages() {
      if (!this.formInfo.filePicture) return []
      return this.formInfo.filePicture.split(',')
    },
    sourceListSort() {
      // if (this.isCode === 2) {
      //   return this.sourceList.filter((t) => t.dictName !== '突发事件')
      // } else {
      return this.sourceList
      // }
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
      console.log(val);
      if (val) {
        this.filterCommunityList = this.communityList.filter(t => val === t.up_precinct_id)
        const [community] = this.filterCommunityList.filter((item) => {
          return item.precinctId === this.form.communityId
        })
        console.log(community);
        if (!community) {
          this.form.communityId = ''
        }
      } else {
        this.filterCommunityList = this.communityList
      }
    },
    changeCommunityId(val) {
      console.log(val);
      if (val) {
        let data = this.communityList.filter(t => val === t.precinctId)
        const [street] = this.streetList.filter(t => data[0].up_precinct_id === t.precinctId)
        this.form.streetId = street.precinctId
        this.changeStreet(street.precinctId)
      }
    },
    fileChange(e) {
      var files = []
      files = Array.from(e.target.files || e.dataTransfer.files)
      if (!files.length) return
      files.forEach((item) => {
        let name = item.name.toLowerCase()
        this.videoFileName = item.name
        // avi|wmv|mpeg|mp4|mov|mkv|flv|f4v|m4v|rmvb|rm|3gp|dat|ts|mts|vob
        if (!/\.(avr|mp4)$/.test(name)) {
          this.$message.warning('请上传视频')
          return
        }
        // 这里是file文件
        this.addForm.video = item
        const maxVideoSize = 1024 * 1024 * 50
        if (this.addForm.video.size > maxVideoSize) {
          this.$message.warning('视频不能大于50M')
          return
        }
        var reader = new FileReader()
        reader.readAsDataURL(item)
        this.videoHidden = false
        reader.onload = () => {
          // 这里是一段base64，用于视频回显用
          this.videoShow = reader.result
        }
        this.fileUpload(this.addForm.video)
      })
    },
    fileUpload(v) {
      // file/upload
      this.uploadProgress = 0
      let url = '/binhuapis/file/uploadMp4'
      this.$message.warning('视频正在上传中.......')
      let formData = new FormData()
      formData.append('file', v)
      formData.append('group', 'system')
      request({
        method: 'post',
        url,
        data: formData,
        onUploadProgress: (progressEvent) => {
          let progress = ((progressEvent.loaded / progressEvent.total) * 100) | 0
          this.uploadProgress = progress
          console.log('进度：', progress)
        }
      }).then((res) => {
        if (res.data.code === 200) {
          this.videoShuzu.push(res.data.data)
          this.$message.success('视频上传成功')
        }
      })
    },
    picChange(e) {
      var files = []
      files = Array.from(e.target.files || e.dataTransfer.files)
      if (!files.length) return
      files.forEach((item) => {
        let name = item.name.toLowerCase()
        this.pictureFileName = item.name
        // avi|wmv|mpeg|mp4|mov|mkv|flv|f4v|m4v|rmvb|rm|3gp|dat|ts|mts|vob
        if (!/\.(jpg|png)$/.test(name)) {
          this.$message.warning('请上传图片')
          return
        }
        // 这里是file文件
        this.addForm.picture = item
        const maxVideoSize = 1024 * 1024 * 10
        if (this.addForm.picture.size > maxVideoSize) {
          this.$message.warning('图片不能大于10M')
          return
        }
        var reader = new FileReader()
        reader.readAsDataURL(item)
        this.pictureHidden = false
        reader.onload = () => {
          // 这里是一段base64，用于视频回显用
          this.pictureShow = reader.result
        }
        this.picUpload(this.addForm.picture)
      })
    },
    picUpload(v) {
      this.uploadPictureProgress = 0
      let url = '/binhuapis/file/uploadImage'
      this.$message.warning('图片正在上传中.......')
      let formData = new FormData()
      formData.append('file', v)
      formData.append('group', 'system')
      request({
        method: 'post',
        url,
        data: formData,
        onUploadProgress: (progressEvent) => {
          let progress = ((progressEvent.loaded / progressEvent.total) * 100) | 0
          this.uploadPictureProgress = progress
          console.log('进度：', progress)
        }
      }).then((res) => {
        if (res.data.code === 200) {
          this.pictureShuzu.push(res.data.data)
          this.$message.success('图片上传成功')
        }
      })
    },
    audioChange(e) {
      var files = []
      files = Array.from(e.target.files || e.dataTransfer.files)
      if (!files.length) return
      files.forEach((item) => {
        let name = item.name.toLowerCase()
        this.audioFileName = item.name
        // avi|wmv|mpeg|mp4|mov|mkv|flv|f4v|m4v|rmvb|rm|3gp|dat|ts|mts|vob
        if (!/\.(mp3|wav)$/.test(name)) {
          this.$message.warning('请上传音频')
          return
        }
        // 这里是file文件
        this.addForm.audio = item
        const maxVideoSize = 1024 * 1024 * 50
        if (this.addForm.audio.size > maxVideoSize) {
          this.$message.warning('视频不能大于50M')
          return
        }
        var reader = new FileReader()
        reader.readAsDataURL(item)
        this.audioHidden = false
        reader.onload = () => {
          // 这里是一段base64，用于视频回显用
          this.audioShow = reader.result
        }
        this.audioUpload(this.addForm.audio)
      })
    },
    audioUpload(v) {
      this.uploadAudioProgress = 0
      let url = '/binhuapis/file/uploadMp3'
      this.$message.warning('音频正在上传中.......')
      let formData = new FormData()
      formData.append('file', v)
      formData.append('group', 'system')
      request({
        method: 'post',
        url,
        data: formData,
        onUploadProgress: (progressEvent) => {
          let progress = ((progressEvent.loaded / progressEvent.total) * 100) | 0
          this.uploadAudioProgress = progress
          console.log('进度：', progress)
        }
      }).then((res) => {
        if (res.data.code === 200) {
          this.audioShuzu.push(res.data.data)
          this.$message.success('音频上传成功')
        }
      })
    },
    findName(v) {
      let data = []
      if (v !== null) {
        if (v.indexOf(',') !== -1) {
          v.split(',').forEach((item) => {
            let da = item.split('/')
            let da2 = da[da.length - 1].split('-')
            da2.splice(0, 1)
            data.push(da2)
          })
        } else {
          let da = v.split('/')
          let da2 = da[da.length - 1].split('-')
          da2.splice(0, 1)
          data.push(da2)
          console.log(data)
        }
      }
      return data.toString()
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
    changeMessage() {
      this.$forceUpdate()
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
          this.sourceList = data//.filter((item) => item.dictName === '值班值守')
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
    getRegionPrecinct() {
      // 获取所有数据
      // let url = '/binhuapis/regional/precinct?forStreet=1'
      let url = '/binhuapis/regional/precinct?precinctKind=110'
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          let data = res.data.data || []
          const regionList = data
          this.streetList = regionList
          this.streetListCopy = regionList
        }
      })
    },
    getRegionPrecinctCommunityList() {
      // 获取所有数据
      // let url = '/binhuapis/regional/precinct'
      // request.get(url).then((res) => {
      //   if (res.data.code === 200) {
      //     let data = res.data.data || []
      //     const regionList = data
      //     this.communityList = regionList.filter((item) => item.precinctKind === 120)
      //   }
      // })
      let url = '/binhuapis/regional/precinct?precinctKind=120'
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          let data = res.data.data || []
          this.communityList = data
          this.filterCommunityList = data
        }
      })
    },
    objPlanSearch(queryString, cb) {
      let restaurants = this.siteList
      let results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants
      // // 调用 callback 返回建议列表的数据
      cb(results)
    },
    createFilter(queryString) {
      return (restaurant) => {
        // return restaurant.value.toUpperCase().match(queryString.toUpperCase())
        return restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) > -1
      }
    },
    checkObjPlan(v) {
      this.form.siteId = v.id
      this.form.siteName = v.value
      this.form.person = v.person
      this.form.phone = v.phone
      this.form.address = v.address
    },
    afterClearable(v) {
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
        this.videoFileName = ''
        this.pictureFileName = ''
        this.audioFileName = ''
        this.$emit('update:dialogVisible', false)
      }
    },

    reset() {
      this.loading = false
      this.addForm.video = ''
      this.addForm.picture = ''
      this.addForm.audio = ''
      this.videoShow = ''
      this.videoFileName = ''
      this.pictureFileName = ''
      this.audioFileName = ''
      document.getElementById('video_file').value = ''
      this.form = {}
      // 清空表单校验，避免再次进来会出现上次校验的记录
      this.$refs.formLabel.clearValidate()
    },
    handleOk() {
      this.$refs.formLabel.validate((valid) => {
        if (valid) {
          if (this.form.gps) {
            if (this.uploadProgress > 0 && this.uploadProgress !== 100) {
              this.$confirm('监测到您的视频还没有完成上传，本次提交将不包含视频内容，是否继续？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
              })
                .then(() => {
                  this.submitAll()
                })
                .catch(() => {
                  this.$message({
                    type: 'info',
                    message: '取消: 继续上传视频'
                  })
                })
            } else {
              this.submitAll()
            }
          } else {
            this.$message.warning('经纬度不能为空')
          }
        } else {
          this.$message.warning('检查表单输入内容的合法性！')
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
      if (this.form.streetId) {
        this.form.streetName = this.streetList.filter(t => t.precinctId === this.form.streetId)[0].precinctName
      }
      if (this.form.communityId) {
        this.form.communityName = this.communityList.filter(t => t.precinctId === this.form.communityId)[0].precinctName
      }
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
        fileVideo: this.videoShuzu.toString(),
        filePicture: this.pictureShuzu.toString(),
        fileVoice: this.audioShuzu.toString(),
        status: 40
      }
      let url = ''
      if (this.formInfo && this.formInfo.id) {
        url = '/binhuapis/dapeng/event/edit'
        newForm.id = this.formInfo.id
      } else {
        url = '/binhuapis/dapeng/event/add'
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

  .title-bar {
    width: 100%;
    display: flex;
    align-items: center;
    padding-left: 42px;
    height: 72px;

    .bar-icon {
      width: 40px;
      height: 40px;
      border: none;
      background-image: url('../../../../assets/common/多页标签@2x.png');
    }

    .bar-text {
      margin-left: 16px;
      font-size: 16px;
      font-family: Microsoft YaHei;
      font-weight: bold;
      color: #0052d9;
    }
  }

  .el-input__icon {
    line-height: 32px;
  }

  .el-form--inline .el-form-item__content {
    flex-grow: 1;
  }

  .el-input-group__append,
  .el-input-group__prepend {
    border: none;
  }

  .upload_box {
    overflow: scroll;
    box-sizing: border-box;
    width: 240px;
    height: 140px;
    margin-left: 50px;
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
