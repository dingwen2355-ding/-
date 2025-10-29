<template>
  <div class="addSiteManage">
    <el-dialog
      :title="isCode == 0 ? '修改' : isCode === 1 ? '查看' : '新增'"
      :visible.sync="dialogVisible"
      width="1100px"
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
          <el-form-item label="避难场所名称" prop="name">
            <el-input clearable v-model="form.name" style="width: 830px;" placeholder="请输入避难场所名称"></el-input>
          </el-form-item>
          <el-form-item label="类型" prop="type">
            <el-select filterable v-model="form.type" placeholder="请选择类型" clearable>
              <el-option
                v-for="item in typeList"
                :value="item.id"
                :label="item.name"
                :key="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="类别" prop="category">
            <el-select filterable v-model="form.category" placeholder="请选择类别" clearable>
              <el-option
                v-for="item in kindList"
                :value="item.id"
                :label="item.name"
                :key="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="最大容纳人数" prop="maxGalleryful">
            <el-input
              clearable
              type="number"
              min="0"
              v-model="form.maxGalleryful"
              placeholder="请输入最大容纳人数"
            ></el-input>
          </el-form-item>
          <!-- <el-form-item label="当前人数" prop="alreadyGalleryful" v-if="isCode == 0">
            <el-input clearable type="number" min="0" v-model="form.alreadyGalleryful" disabled placeholder="请输入当前人数"></el-input>
          </el-form-item>-->
          <el-form-item label="管理人员" prop="person">
            <el-input clearable v-model="form.person" placeholder="请输入管理人员"></el-input>
          </el-form-item>
          <el-form-item label="管理员手机" prop="phone">
            <el-input clearable v-model="form.phone" placeholder="请输入管理员手机"></el-input>
          </el-form-item>
          <el-form-item label="所属社区" prop="community">
            <!-- <el-input clearable v-model="form.community" placeholder="请输入所属社区"></el-input> -->
            <el-select filterable v-model="form.community" placeholder="请选择社区名称" clearable>
              <el-option
                v-for="item in communityList"
                :value="item.precinctName"
                :label="item.precinctName"
                :key="item.precinctId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="面积" prop="acreage">
            <el-input clearable v-model="form.acreage" style="width: 510px;" placeholder="请输入面积">
              <template slot="append">
                m
                <sup>2</sup>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item label="状态" prop="state">
            <el-select filterable v-model="form.state" placeholder="请选择状态" clearable>
              <el-option
                v-for="item in stateList"
                :value="item.id"
                :label="item.name"
                :key="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="应急职责" prop="responsibility">
            <el-input
              clearable
              v-model="form.responsibility"
              style="width: 830px;"
              placeholder="请输入应急职责"
            ></el-input>
          </el-form-item>
          <el-form-item label="经纬度:" prop="gps">
            <div @click="jump('1')">
              <el-input
                :disabled="isCode === 1"
                placeholder="选择经纬度"
                prefix-icon="el-icon-location-outline"
                v-model="form.gps"
              ></el-input>
            </div>
          </el-form-item>
          <el-form-item label="设备ID:" prop="deviceId">
            <el-input clearable v-model="form.deviceId" placeholder="请输入设备ID"></el-input>
          </el-form-item>
          <el-form-item label="地址" prop="address">
            <el-input clearable v-model="form.address" style="width: 830px;" placeholder="请输入地址"></el-input>
          </el-form-item>
          <el-form-item label="图片" prop="photo">
            <div class="up_load">
              <div class="up_load_img" v-for="(item, key) in fileList" :key="key">
                <img :src="item.url" alt srcset style="max-height: 100px;max-width: 100px;" />
                <span class="up_load_name">{{ item.name }}</span>
                <div class="up_load_img_hover">
                  <i
                    class="el-icon-zoom-in"
                    v-if="
                  item.url.includes('.jpg') ||
                  item.url.includes('.png') ||
                  item.url.includes('.gif') ||
                  item.url.includes('.bmp') ||
                  item.url.includes('.jpeg') ||
                  item.url.includes('.JPG') ||
                  item.url.includes('.PNG') ||
                  item.url.includes('.GIF') ||
                  item.url.includes('.BMP') ||
                  item.url.includes('.JPEG')
                "
                    @click="handlePictureCardPreview(item.url)"
                  ></i>
                  <i class="el-icon-download" @click="getFileAndDownload(item.name, item.url)"></i>
                  <i class="el-icon-delete" @click="handleRemove(item, key)"></i>
                </div>
              </div>
              <div class="up_load_file" v-if="fileList.length === 0">
                <i class="el-icon-plus avatar-uploader-icon"></i>
                <input
                  class="up_load_input"
                  type="file"
                  :files="files"
                  @change="uploading"
                  ref="fileUpload"
                />
              </div>
            </div>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
        <el-button type="primary" @click="handleOk" v-if="isCode !== 1">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 放大图片 -->
    <el-dialog :visible.sync="imgDialogVisible" :modal-append-to-body="true" append-to-body>
      <img width="100%" :src="dialogImageUrl" alt />
    </el-dialog>
    <el-dialog
      :visible.sync="dialogMap"
      :modal="false"
      :title="'选择经纬度'"
      :modal-append-to-body="false"
      :append-to-body="false"
    >
      <chooseMap :gpsInfo="gpsInfo" @gpsChoose="gpsChoose" />
    </el-dialog>
  </div>
</template>
<script>
import request from '@/utils/request'
import chooseMap from '@/components/chooseMapDialog'
export default {
  name: 'addSiteManage',
  components: { chooseMap },
  props: ['dialogVisible', 'formInfo', 'isCode'],
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
      if (v) {
        // this.getAreaList()
        this.getSiteList3()
        this.form = Object.assign({}, JSON.parse(JSON.stringify(this.formInfo)))
        this.fileList = []
        console.log(this.form)
        if (this.form.gpsx) {
          this.form.gps = this.form.gpsx + ',' + this.form.gpsy
        }
        if (this.form.photo) {
          let photoStrs = this.form.photo.split('-')
          let photoName = photoStrs[photoStrs.length - 1].split('.')[0]
          this.fileList.push({
            url: this.form.photo,
            name: photoName
          })
        }

        console.log('新增编辑查看this.form', this.form)
      }
    }
  },
  data() {
    const phoneCheck = (rule, value, callback) => {
      let isPhone = /^[1][3-9]{1}[0-9]{9}$/ // 手机号码
      let isMob = /^([0-9]{3,4}-)?[0-9]{7,8}$/ // 座机格式
      if (!(value === null || value === undefined || value === '')) {
        if (isMob.test(value) || isPhone.test(value)) {
          callback()
        } else {
          callback(new Error('非有效电话号码！'))
        }
      } else {
        callback()
      }
    }
    return {
      rules: {
        name: [{ required: true, message: '请输入避难场所名称', trigger: 'blur' }],
        type: [{ required: true, message: '请选择类型', trigger: 'blur' }],
        category: [{ message: '请选择类别', trigger: 'blur' }],
        roomCount: [{ required: true, message: '请输入房间数量', trigger: 'blur' }],
        responsibility: [{ message: '请输入应急职责', trigger: 'blur' }],
        person: [{ required: true, message: '请输入管理人员', trigger: 'blur' }],
        phone: [
          { required: true, message: '请输入管理员手机', trigger: 'blur' },
          { validator: phoneCheck, trigger: 'blur' }
        ],
        community: [{ required: true, message: '请输入所属社区', trigger: 'blur' }],
        address: [{ required: true, message: '请输入地址', trigger: 'blur' }],
        acreage: [{ message: '请输入面积', trigger: 'blur' }],
        state: [{ required: true, message: '请选择状态', trigger: 'blur' }],
        maxGalleryful: [{ message: '请输入最大容纳人数', trigger: 'blur' }]
      },
      loading: false,
      kindList: [
        { id: '学校', name: '学校' },
        { id: '公园', name: '公园' },
        { id: '绿地', name: '绿地' },
        { id: '广场', name: '广场' },
        { id: '体育设施', name: '体育设施' },
        { id: '其他', name: '其他' }
      ],
      typeList: [
        { id: '室内场所', name: '室内场所' },
        { id: '室外场所', name: '室外场所' }
      ],
      stateList: [
        { id: '1', name: '开放' },
        { id: '2', name: '有人' },
        { id: '3', name: '告警' },
        { id: '4', name: '未开放' },
        { id: '5', name: '暂不使用' }
      ],
      form: {
        name: '', // 避难场所名称
        type: '', // 类型
        category: '', // 类别
        roomCount: '', // 房间数量
        responsibility: '', // 应急职责
        person: '', // 管理人员
        phone: '', // 管理员手机
        community: '', // 所属社区
        address: '', // 地址
        acreage: '', // 面积
        state: '', // 状态
        maxGalleryful: '', // 最大容纳人数,
        // alreadyGalleryful: '', // 当前人数,
        photo: '',
        gps: ''
      },
      gpsInfo: '',
      dialogMap: false,
      fileList: [],
      files: [],
      imgDialogVisible: false,
      dialogImageUrl: '',
      communityList: []
    }
  },
  methods: {
    gpsChoose(e) {
      this.dialogMap = false
      this.form.gps = e.value
      console.log(e)
    },
    jump(e) {
      this.gpsInfo = this.form.gps
      console.log(this.gpsInfo)
      this.dialogMap = true
    },
    getFileAndDownload(fileName, url) {
      console.log(url)
      var x = new XMLHttpRequest()
      x.open('GET', url, true)
      x.responseType = 'blob'
      x.onload = function (e) {
        var blob = x.response
        if ('msSaveOrOpenBlob' in navigator) {
          // IE导出
          window.navigator.msSaveOrOpenBlob(blob, fileName)
        } else {
          var a = document.createElement('a')
          a.download = fileName
          a.href = URL.createObjectURL(blob)
          $('body').append(a)
          a.click()
          $(a).remove()
        }
      }
      x.send()
    },
    getSiteList3() {
      let url = '/auth/v2/precinct/queryPrecinctByKind?precinctKind=110'
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          let data = res.data.data || []
          this.communityList = data
        }
      })
    },
    uploading(e) {
      let inputDOM = this.$refs.fileUpload
      // 通过DOM取文件数据
      let file = inputDOM.files[0]
      let url = '/binhuapis/siteManage/uploadFile'
      let formData = new FormData()
      formData.append('uploadFile', file)
      formData.append('group', 'system')
      request.post(url, formData).then(res => {
        inputDOM.value = ''
        let obj = {
          url: res.data.data,
          name: file.name
        }
        this.fileList.push(obj)
      })
    },
    handleRemove(file, key) {
      this.$confirm('确认删除吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          let url = '/binhuapis/siteManage/deleteFile?fileUrl=' + file.url
          request.post(url).then((res) => {
            this.fileList.splice(key, 1)
            this.$message.success('删除成功！')
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    handlePictureCardPreview(file) {
      this.imgDialogVisible = true
      this.dialogImageUrl = file
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
          // console.log('error submit!!')
          return false
        }
      })
    },
    submitAll() {
      this.loading = true
      if (this.form.gps) {
        let gps = this.form.gps.split(',')
        this.form.gpsx = gps[0].trim()
        this.form.gpsy = gps[1].trim()
      }
      let newForm = {
        name: this.form.name,
        type: this.form.type,
        category: this.form.category,
        responsibility: this.form.responsibility,
        person: this.form.person,
        phone: this.form.phone,
        community: this.form.community,
        address: this.form.address,
        acreage: this.form.acreage,
        maxGalleryful: this.form.maxGalleryful,
        // alreadyGalleryful: this.form.alreadyGalleryful,
        state: this.form.state,
        gpsx: this.form.gpsx,
        gpsy: this.form.gpsy,
        deviceId: this.form.deviceId
      }
      if (this.fileList.length > 0) {
        newForm.photo = this.fileList[0].url
      }
      let url = '/binhuapis/siteManage/save'
      if (this.formInfo && this.formInfo.id) {
        // url = '/binhuapis/siteManage/save'
        newForm.id = this.formInfo.id
        // console.log('参数: ', newForm, 'url: ', url)
      } else {
        // url = '/community/trainingOrgan/add'
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
<style lang="scss" scoped>
// .addSiteManage{}
</style>
