<template>
  <div class="editInfoDialog">
    <el-dialog
      title="编辑摄像头信息"
      :visible.sync="dialogVisible"
      width="800px"
      :before-close="handleClose"
      :modal-append-to-body="false"
      :append-to-body="false"
    >
      <div class="info_body" v-loading="loading">
        <el-form
          ref="formLabel"
          :rules="rules"
          :model="form"
          style="margin-right: 35px"
          label-width="110px"
        >
          <el-form-item label="摄像机名称" prop="deviceName">
            <el-input
              clearable
              disabled
              v-model="form.deviceName"
              placeholder="请输入摄像机名称"
              style="width: 630px"
            ></el-input>
          </el-form-item>
          <el-form-item label="摄像机类型">
            <el-select
              filterable
              v-model="form.deviceType"
              placeholder="请选择摄像机类型"
              clearable
              style="width: 630px"
            >
              <el-option
                v-for="item in deviceTypeList"
                :value="item.id"
                :label="item.name"
                :key="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="所在辖区">
            <el-select
              filterable
              v-model="form.precinctId"
              placeholder="请选择所在辖区"
              clearable
              style="width: 630px"
            >
              <el-option
                v-for="item in areaList"
                :value="item.regionId"
                :label="item.regionName"
                :key="item.regionId"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="所属单位">
            <el-input
              clearable
              v-model="form.regionName"
              placeholder="请输入所属单位"
              style="width: 630px"
            ></el-input>
          </el-form-item>
          <el-form-item label="经度" prop="gpsx">
            <el-input clearable v-model="form.gpsx" placeholder="请选择经度位置" style="width: 630px">
              <el-button slot="append" icon="el-icon-location" @click="handlePlace(false)"></el-button>
            </el-input>
          </el-form-item>
          <el-form-item label="纬度" prop="gpsy">
            <el-input clearable v-model="form.gpsy" placeholder="请选择纬度位置" style="width: 630px">
              <el-button slot="append" icon="el-icon-location" @click="handlePlace(false)"></el-button>
            </el-input>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
        <el-button type="primary" @click="handleOk">确 定</el-button>
      </div>
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
import Axios from '@/utils/request'
import znvTime from '../../utils/znvTime'
import chooseMap from '@/components/chooseMapDialog'
export default {
  name: 'editInfoDialog',
  props: ['dialogVisible', 'formInfo'],
  components: { chooseMap },
  watch: {
    dialogVisible(v) {
      console.log(v)
      this.dialogVisible = v
      this.form = Object.assign(this.form, JSON.parse(JSON.stringify(this.formInfo)))
      console.log('this.form : ', this.form)
    }
  },
  data() {
    return {
      rules: {
        deviceName: [{ required: true, message: '请输入摄像机名称', trigger: 'blur' }],
        gpsx: [{ required: true, message: '请选择经度位置', trigger: 'blur' }],
        gpsy: [{ required: true, message: '请选择纬度位置', trigger: 'blur' }]
      },
      areaList: [],
      loading: false,
      form: {
        deviceId: '',
        deviceName: '',
        deviceType: '',
        precinctId: '',
        regionName: '',
        gpsx: '',
        gpsy: ''
      },
      deviceTypeList: [
        { id: 1, name: '高清' },
        { id: 2, name: '高清云台' }
      ],
      gpsInfo: '',
      dialogMap: false,
    }
  },
  mounted() {
    this.getAreaList()
  },
  methods: {
    // 所属辖区
    getAreaList() {//?userLevel=${localStorage.level}&userId=${localStorage.userId}
      let url = `/binhuapis/companyDict/queryRegion`
      Axios.get(url).then((res) => {
        let data = res.data.data || []
        this.areaList = data
      })
    },
    // 获取经纬度
    handlePlace() {
      this.gpsInfo = this.form.gpsx + ',' + this.form.gpsy
      this.dialogMap = true
    },
    gpsChoose(e) {
      this.form.gpsx = e.value.split(',')[0]
      this.form.gpsy = e.value.split(',')[1]
      this.dialogMap = false
    },
    reset() {
      this.loading = false
      this.form = {
        deviceId: '',
        deviceName: '',
        deviceType: '',
        precinctId: '',
        regionName: '',
        gpsx: '',
        gpsy: ''
      }
    },
    // 取 消
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
    // 确 定
    handleOk() {
      this.$refs.formLabel.validate((valid) => {
        if (valid) {
          this.submitAll()
        } else {
          this.$message.warning('检查表单输入内容的合法性！')
          console.log('error submit!!')
          return false
        }
      })
    },
    submitAll() {
      this.loading = true
      console.log('提交保存：', this.form)
      let params = {
        deviceId: this.form.deviceId,
        deviceName: this.form.deviceName,
        deviceType: this.form.deviceType,
        gpsx: this.form.gpsx,
        gpsy: this.form.gpsy,
        precinctId: this.form.precinctId,
        unit: this.form.regionName,
        replaceDate: znvTime.getCurrentTimeByFormat('YYYY-MM-DD HH:mm:ss') // 更新的时间
      }
      let url = '/binhuapis/editCameraDevices'
      console.log('提交保存url:', url, '参数：', params)
      Axios.post(url, params)
        .then((res) => {
          this.loading = false
          if (res.data.code == 200) {
            this.$message.success('修改成功')
            this.$emit('editSuccess')
            this.$emit('update:dialogVisible', false)
          } else {
            this.$message.success('修改失败')
          }
        })
        .catch(() => {
          this.$message.success('修改失败')
        })
    }
  }
}
</script>
<style lang="scss">
</style>
