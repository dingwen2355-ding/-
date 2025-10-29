<template>
  <el-dialog
    title="修改物资仓库"
    :visible.sync="dialogVisible"
    width="720px"
    :modal-append-to-body="false"
    :append-to-body="false"
    :before-close="handleClose"
  >
    <el-form :inline="true" ref="form" :rules="rules" :model="form" class="storage-form" label-width="100px">
      <el-form-item label="仓库名称" prop="stockName">
        <el-input clearable v-model="form.stockName" placeholder="请输入仓库名称"></el-input>
      </el-form-item>
      <el-form-item label="所属辖区" prop="regionId">
        <el-select v-model="form.regionId" placeholder="所属辖区">
          <el-option v-for="(v, i) in regionList" :key="i" :label="v.name" :value="v.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="管理类型" prop="beManagedTypeId">
        <el-select v-model="form.beManagedTypeId" placeholder="管理类型">
          <el-option v-for="(v, i) in manageTypeList" :key="i" :label="v.name" :value="v.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="管理单位" prop="belongUnitId">
        <el-select v-model="form.belongUnitId" filterable placeholder="管理类型">
          <el-option v-for="(v, i) in unitList" :key="i" :label="v.name" :value="v.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="管理级别" prop="storageLevelId">
        <el-select v-model="form.storageLevelId" placeholder="请输入管理级别">
          <el-option v-for="(v, i) in manageLevelList" :key="i" :label="v.name" :value="v.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="存储类型" prop="storageTypeId">
        <el-select v-model="form.storageTypeId" placeholder="请输入存储类型">
          <el-option v-for="(v, i) in storageTypeList" :key="i" :label="v.name" :value="v.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="救援类型" prop="helpTypeId">
        <el-select v-model="form.helpTypeId" placeholder="请输入救援类型" multiple collapse-tags>
          <el-option v-for="(v, i) in saveTypeList" :key="i" :label="v.name" :value="v.id"></el-option>
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="所属行业" prop="industryId">
        <el-select multiple v-model="form.industryId" placeholder="请输入所属行业" collapse-tags>
          <el-option v-for="(v, i) in industryList" :key="i" :label="v.name" :value="v.id"></el-option>
        </el-select>
      </el-form-item> -->
      <el-form-item label="详细地址" prop="address">
        <el-input clearable v-model="form.address" placeholder="请输入详细地址"></el-input>
      </el-form-item>
      <el-form-item label="仓库类型" prop="stockType">
        <el-select v-model="form.stockType" placeholder="请输入仓库类型">
          <el-option label="物资库" value="物资库"></el-option>
          <el-option label="装备库" value="装备库"></el-option>
          <!-- <el-option label="其他" value="其他"></el-option>
          <el-option label="救援队伍" value="救援队伍"></el-option>
          <el-option label="社会力量" value="社会力量"></el-option>
          <el-option label="监管企业" value="监管企业"></el-option> -->
        </el-select>
      </el-form-item>
      <el-form-item label="经度" prop="longitude">
        <el-input clearable v-model="form.longitude" placeholder="请选择经度位置">
          <el-button slot="append" icon="el-icon-location" @click="handlePlace(false)"></el-button>
        </el-input>
      </el-form-item>
      <el-form-item label="纬度" prop="latitude">
        <el-input clearable v-model="form.latitude" placeholder="请选择纬度位置">
          <el-button slot="append" icon="el-icon-location" @click="handlePlace(false)"></el-button>
        </el-input>
      </el-form-item>
      <el-form-item label="主要责任人" prop="manager">
        <el-input clearable v-model="form.manager" placeholder="请输入主要责任人"></el-input>
      </el-form-item>
      <el-form-item label="联系电话" prop="managerPhone">
        <el-input clearable v-model="form.managerPhone" placeholder="请输入联系电话"></el-input>
      </el-form-item>
      <el-form-item label="值班人" prop="mainPeople">
        <el-input clearable v-model="form.mainPeople" placeholder="请输入值班人"></el-input>
      </el-form-item>
      <el-form-item label="值班电话" prop="dutyPhone">
        <el-input clearable v-model="form.dutyPhone" placeholder="请输入值班电话"></el-input>
      </el-form-item>
      <el-form-item label="队员人数" prop="memberNumber">
        <el-input clearable v-model.number="form.memberNumber" placeholder="请输入队员人数"></el-input>
      </el-form-item>
      <el-form-item label="排序号" prop="stockOrder">
        <el-input clearable v-model="form.stockOrder" placeholder="请输入排序号"></el-input>
      </el-form-item>
      <el-form-item label="用途" prop="introduction" class="whole-line">
        <el-input clearable v-model="form.introduction" type="textarea" :rows="4" placeholder="请输入用途"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="handleClose()">取 消</el-button>
      <el-button type="primary" @click="handleOk">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import axios from 'axios'

export default {
  props: [
    'dialogVisible',
    'storageInfo',
    'manageTypeList',
    'industryList',
    'saveTypeList',
    'unitList',
    'regionList',
    'manageLevelList',
    'storageTypeList'
  ],
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
      if (v) {
        // console.log('this.form', this.form)
        Object.keys(this.form).forEach((key) => {
          if (key === 'helpTypeId') {
            this.form[key] = this.storageInfo[key].split(',').map(Number)
          } else if (key === 'industryId') {
            // let industryId = []
            // if (this.storageInfo[key].includes(',')) {
            //   this.form[key] = this.storageInfo[key].split(',').map(Number)
            // } else {
            //   industryId.push(Number(this.storageInfo[key]))
            //   this.form[key] = industryId
            // }
            // console.log('this.form', this.form)
          } else {
            if (!isNaN(this.storageInfo[key]) && this.storageInfo[key]) {
              this.form[key] = Number(this.storageInfo[key])
            } else {
              this.form[key] = this.storageInfo[key]
            }
          }
        })
      }
    }
  },
  data() {
    return {
      form: {
        id: '',
        regionId: '',
        beManagedTypeId: '',
        belongUnitId: '',
        storageLevelId: '',
        storageTypeId: '',
        helpTypeId: '',
        industryId: '',
        stockName: '',
        address: '',
        longitude: '',
        latitude: '',
        mainPeople: '',
        dutyPhone: '',
        manager: '',
        managerPhone: '',
        memberNumber: '',
        stockType: '',
        introduction: '',
        stockOrder: '1'
      },
      rules: {
        stockName: [{ required: true, message: '请输入后提交', trigger: 'blur' }],
        regionId: [{ required: true, message: '请选择', trigger: 'change' }],
        beManagedTypeId: [{ required: true, message: '请选择', trigger: 'change' }],
        belongUnitId: [{ required: true, message: '请选择', trigger: 'change' }],
        storageLevelId: [{ required: true, message: '请选择', trigger: 'change' }],
        storageTypeId: [{ required: true, message: '请选择', trigger: 'change' }],
        helpTypeId: [{ required: true, message: '请选择', trigger: 'change' }],
        industryId: [{ required: true, message: '请选择', trigger: 'change' }],
        address: [{ required: true, message: '请输入后提交', trigger: 'blur' }],
        stockType: [{ required: true, message: '请选择', trigger: 'change' }],
        longitude: [{ required: true, message: '请选择经度位置', trigger: 'change' }],
        latitude: [{ required: true, message: '请选择纬度位置', trigger: 'change' }],
        stockOrder: [{ required: true, message: '请输入后提交', trigger: 'blur' }]
        // manager: [
        //   { required: true, message: '请输入后提交', trigger: 'blur' }
        // ],
        // managerPhone: [
        //   { required: true, message: '请输入后提交', trigger: 'blur' }
        // ],
        // mainPeople: [
        //   { required: true, message: '请输入后提交', trigger: 'blur' }
        // ],
        // dutyPhone: [
        //   { required: true, message: '请输入后提交', trigger: 'blur' }
        // ],
        // memberNumber: [
        //   { required: true, message: '请输入后提交', trigger: 'blur' },
        //   { type: "number", trigger: "blur", message: "请输入数字" }
        // ]
      }
    }
  },
  mounted() {
    // 获取经纬度
    this.$root.eventBus.$on('sendGps' + 'EssentialInformation', (data) => {
      // console.log(data)
      data = data.split(',')
      this.form.longitude = data[0]
      this.form.latitude = data[1]
    })
  },
  methods: {
    handleClose(done) {
      this.$emit('update:dialogVisible', false)
    },
    reset() {
      this.$refs.form.resetFields()
    },
    handleOk() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          let data = JSON.parse(JSON.stringify(this.form))
          data.helpTypeId = data.helpTypeId.join(',')
          // data.industryId = data.industryId.join(',')
          let url = '/gtw/EIMS-management/stockInfoData/updateStockInfo'
          axios.post(url, data).then((r) => {
            if (r.data.code === 200) {
              this.$message.success('修改成功')
              this.$emit('success')
              this.handleClose()
            } else {
              this.$message.error(r.data.message)
            }
          })
        } else {
          // console.log('error submit!!')
          return false
        }
      })
    },
    // 获取经纬度
    handlePlace(type) {
      // console.log(this.form)
      this.$root.eventBus.$emit('getGps', {
        emitEventName: 'EssentialInformation',
        oriGpsInfo: this.form.longitude + ',' + this.form.latitude
      })
    }
  }
}
</script>
