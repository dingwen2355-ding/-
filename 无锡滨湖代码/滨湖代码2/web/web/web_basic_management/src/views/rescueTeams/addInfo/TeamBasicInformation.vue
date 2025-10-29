<template>
  <div class="TeamBasicInformation">
    <el-form
      :inline="true"
      ref="form"
      :rules="rules"
      class="storage-form"
      :model="form"
      label-width="100px"
      :disabled="isCode == 1"
    >
      <el-form-item label="队伍名称" prop="teamName">
        <el-input clearable v-model="form.teamName" placeholder="请输入队伍名称"></el-input>
      </el-form-item>
      <el-form-item label="管理单位" prop="belongUnit">
        <el-select filterable v-model="form.belongUnit" placeholder="请选择管理单位" clearable>
          <el-option v-for="item in unitList" :value="item.id" :label="item.name" :key="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="所在辖区" prop="region">
        <el-select filterable v-model="form.region" placeholder="请选择所在辖区" clearable>
          <el-option
            v-for="item in areaList"
            :value="item.regionId"
            :label="item.regionName"
            :key="item.regionId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="队伍性质" prop="beManagedType">
        <el-select filterable v-model="form.beManagedType" placeholder="请选择队伍性质" clearable>
          <el-option
            v-for="item in manageTypeList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="专兼类型" prop="apType">
        <el-select filterable v-model="form.apType" placeholder="请选择专兼类型" clearable>
          <el-option
            v-for="item in fullPartTimeList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="管理级别" prop="storageLevel">
        <el-select filterable clearable v-model="form.storageLevel" placeholder="请选择管理级别">
          <el-option
            v-for="item in manageLevelList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="联系人" prop="manager">
        <el-input clearable v-model="form.manager" placeholder="请输入联系人"></el-input>
      </el-form-item>
      <el-form-item label="联系人电话" prop="managerPhone">
        <el-input clearable v-model="form.managerPhone" placeholder="请输入联系人电话"></el-input>
      </el-form-item>
      <el-form-item label="主要负责人" prop="mainPeople">
        <el-input clearable v-model="form.mainPeople" placeholder="请输入主要负责人"></el-input>
      </el-form-item>
      <el-form-item label="值班电话" prop="dutyPhone">
        <el-input clearable v-model="form.dutyPhone" placeholder="请输入值班电话"></el-input>
      </el-form-item>
      <el-form-item label="服务区域" prop="serviceArea">
        <el-input clearable v-model="form.serviceArea" placeholder="请输入服务区域"></el-input>
      </el-form-item>
      <el-form-item label="队员人数" prop="memberNumber">
        <el-input clearable v-model="form.memberNumber" placeholder="请输入队员人数"></el-input>
      </el-form-item>
      <el-form-item label="所属行业" prop="industry">
        <el-select
          filterable
          multiple
          v-model="form.industry"
          placeholder="请选择所属行业"
          clearable
          collapse-tags
        >
          <el-option
            v-for="item in industryList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="队伍类型" prop="specialities">
        <el-select
          filterable
          multiple
          v-model="form.specialities"
          placeholder="请选择队伍类型"
          clearable
          collapse-tags
        >
          <el-option v-for="item in majorList" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="经度" prop="longitude" class="tude-line">
        <el-input clearable v-model="form.longitude" placeholder="请选择经度位置" @input="changeMessage()">
          <template slot="append">
            <div @click="isCode !== 1 ? jump('1') : ''">
              <i class="el-icon-location"></i>
            </div>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item label="纬度" prop="latitude" class="tude-line">
        <el-input clearable v-model="form.latitude" placeholder="请选择纬度位置">
          <template slot="append">
            <div @click="isCode !== 1 ? jump('1') : ''">
              <i class="el-icon-location"></i>
            </div>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item label="救援类别" prop="helpType">
        <el-select
          filterable
          multiple
          v-model="form.helpType"
          placeholder="请选择救援类别"
          clearable
          collapse-tags
        >
          <el-option
            v-for="item in saveTypeList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="详细地址" prop="address" class="address-line">
        <el-input clearable v-model="form.address" placeholder="请输入详细地址"></el-input>
      </el-form-item>
      <el-form-item label="机构设置简介" prop="orgSet" class="whole-line">
        <el-input clearable type="textarea" :rows="6" v-model="form.orgSet" placeholder="请输入机构设置简介"></el-input>
      </el-form-item>
      <el-form-item label="队伍信息简介" prop="teamInfo" class="whole-line">
        <el-input
          clearable
          type="textarea"
          :rows="6"
          v-model="form.teamInfo"
          placeholder="请输入队伍信息简介"
        ></el-input>
      </el-form-item>
      <el-form-item label="参与重大灾害救援及获得资质证书情况" prop="aptitudeInfo" class="whole-line">
        <el-input
          clearable
          type="textarea"
          :rows="6"
          v-model="form.aptitudeInfo"
          placeholder="请输入参与重大灾害救援及获得资质证书情况"
        ></el-input>
      </el-form-item>
    </el-form>
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
import chooseMap from '@/components/chooseMapDialog'

export default {
  name: 'TeamBasicInformation',
  components: { chooseMap },
  props: ['form', 'isCode'],
  data() {
    const phoneCheck = (rule, value, callback) => {
      let isPhone = /^1(3|4|5|7|8)\d{9}$/ // 手机号码
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
        teamName: [{ required: true, message: '请输入队伍名称', trigger: 'blur' }],
        region: [{ required: true, message: '请选择所在辖区', trigger: 'change' }],
        beManagedType: [{ required: true, message: '请选择队伍性质', trigger: 'change' }],
        manager: [{ required: true, message: '请输入联系人', trigger: 'blur' }],
        managerPhone: [
          { required: true, message: '请输入联系人电话', trigger: 'blur' },
          { validator: phoneCheck, trigger: 'blur' }
        ],
        mainPeople: [{ required: true, message: '请输入主要负责人', trigger: 'blur' }],
        dutyPhone: [
          { required: true, message: '请输入值班电话', trigger: 'blur' },
          { validator: phoneCheck, trigger: 'blur' }
        ],
        memberNumber: [{ required: true, message: '请输入队员人数', trigger: 'blur' }],
        address: [{ required: true, message: '请输入详细地址', trigger: 'blur' }],
        longitude: [{ required: true, message: '请选择经度位置', trigger: 'blur' }],
        latitude: [{ required: true, message: '请选择纬度位置', trigger: 'blur' }]
      },
      unitList: [], // 管理单位
      areaList: [], // 所属辖区
      manageTypeList: [], // 管理类型
      fullPartTimeList: [], // 专兼类型
      manageLevelList: [], // 管理级别
      industryList: [], // 所属行业
      saveTypeList: [], // 救援类别
      majorList: [], // 擅长领域
      gpsInfo: '',
      dialogMap: false
    }
  },
  mounted() {
    this.queryManageUnit() // 管理单位
    this.getAreaList() // 所属辖区
    this.getManageType() // 管理类型
    this.getFullPartTimeDict() // 专兼类型
    this.getManageLevel() // 管理级别
    this.getIndustry() // 所属行业
    this.getMajor() // 擅长领域
    this.getSaveType() // 救援类别
    // 获取经纬度
    this.$root.eventBus.$on('sendGps' + 'EssentialInformation', (data) => {
      console.log(data)
      data = data.split(',')
      this.form.longitude = data[0]
      this.form.latitude = data[1]
    })
  },
  methods: {
    // 管理单位
    queryManageUnit() {
      let url = '/binhuapis/menu/getBelongUnit'
      Axios.get(url).then((r) => {
        this.unitList = r.data.data[0].data
      })
    },
    // 所属辖区
    getAreaList() { // ?userLevel=${localStorage.level}&userId=${localStorage.userId}
      let url = '/binhuapis/companyDict/queryRegion'
      Axios.get(url).then((res) => {
        let data = res.data.data || []
        this.areaList = data
      })
    },
    // 管理类型
    getManageType() {
      let url = '/binhuapis/menu/getBeManagedType'
      Axios.get(url).then((r) => {
        this.manageTypeList = r.data.data.data
      })
    },
    gpsChoose(e) {
      this.form.longitude = e.value.split(',')[0]
      this.form.latitude = e.value.split(',')[1]
      this.dialogMap = false
      console.log(e)
    },
    jump(e) {
      this.gpsInfo = this.form.longitude + ',' + this.form.latitude
      this.dialogMap = true
    },
    changeMessage() {
      this.$forceUpdate()
    },
    // 专兼类型
    getFullPartTimeDict() {
      let url = '/binhuapis/teamInfo/teamInfoFullPartTimeDict'
      Axios.get(url).then((r) => {
        this.fullPartTimeList = r.data.data
      })
    },
    // 管理级别
    getManageLevel() {
      let url = '/binhuapis/menu/getStorageLevel'
      Axios.get(url).then((r) => {
        this.manageLevelList = r.data.data.data
      })
    },
    // 所属行业
    getIndustry() {
      let url = '/binhuapis/menu/getIndustry'
      Axios.get(url).then((r) => {
        this.industryList = r.data.data.data
      })
    },
    // 擅长领域
    getMajor() {
      let url = '/binhuapis/teamInfo/teamInfoMajorsDict'
      Axios.get(url).then((r) => {
        this.majorList = r.data.data
      })
    },
    // 救援类别
    getSaveType() {
      let url = '/binhuapis/menu/getHelpType'
      Axios.get(url).then((r) => {
        this.saveTypeList = r.data.data.data
      })
    }
  }
}
</script>

<style lang="scss">
.TeamBasicInformation {
  height: 100%;
  .storage-form.el-form--inline {
    .el-form-item__content {
      width: calc(100% - 100px);
    }
    // .tude-line {
    //   margin-right: -48px;
    //   .el-form-item__content {
    //     width: calc(100% - 155px);
    //   }
    // }
    .address-line {
      width: calc(100% - 470px);
      .el-form-item__content {
        width: calc(100% - 130px);
      }
    }
    .whole-line {
      // width: 100%;
      width: calc(100% - 100px);
      .el-form-item__content {
        width: calc(100% - 191px);
      }
    }
  }
}
</style>
