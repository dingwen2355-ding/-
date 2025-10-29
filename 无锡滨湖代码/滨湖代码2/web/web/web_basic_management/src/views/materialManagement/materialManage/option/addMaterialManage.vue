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
          <el-form-item label="物资名称" prop="stuffName">
            <el-input clearable v-model="form.stuffName" placeholder="请输入物资名称"></el-input>
          </el-form-item>
          <el-form-item label="物资类型" prop="stuffType">
            <el-input clearable v-model="form.stuffType" placeholder="请输入物资类型"></el-input>
          </el-form-item>
          <el-form-item label="救援类型" prop="stuffType">
            <el-select v-model="form.helpType" filterable placeholder="请输入救援类型" clearable>
              <el-option
                v-for="(item,key) in helpTypes"
                :key="key"
                :label="item.name"
                :value="item.id+''"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="数量" prop="stuffNum">
            <el-input clearable type="number" v-model="form.stuffNum" placeholder="请输入数量"></el-input>
          </el-form-item>
          <el-form-item label="单位" prop="stuffUnit">
            <el-input
              clearable
              v-model="form.stuffUnit"
              placeholder="请输入单位"
              :disabled="isCode !== 2"
            ></el-input>
          </el-form-item>
          <el-form-item label="所属社区" prop="region">
            <el-select
              filterable
              v-model="form.region"
              placeholder="请输入所属社区"
              :disabled="isCode !== 2"
              clearable
            >
              <el-option
                v-for="item in communityList"
                :value="item.precinctId"
                :label="item.precinctName"
                :key="item.precinctId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="仓库名称" prop="siteAddress">
            <el-input clearable v-model="form.address" placeholder="请输入存放地址"></el-input>
          </el-form-item>
          <el-form-item label="经度" prop="gpsx">
            <el-input clearable v-model="form.gpsx" placeholder="请选择经度位置" style="width: 220px">
              <el-button slot="append" icon="el-icon-location" @click="handlePlace(false)"></el-button>
            </el-input>
          </el-form-item>
          <el-form-item label="纬度" prop="gpsy">
            <el-input clearable v-model="form.gpsy" placeholder="请选择纬度位置" style="width: 220px">
              <el-button slot="append" icon="el-icon-location" @click="handlePlace(false)"></el-button>
            </el-input>
          </el-form-item>
          <el-form-item label="负责人员" prop="sitePerson">
            <el-input
              clearable
              v-model="form.dutyPerson"
              placeholder="请输入负责人员"
              :disabled="isCode !== 2"
            ></el-input>
          </el-form-item>
          <el-form-item label="负责人手机" prop="sitePhone">
            <el-input clearable v-model="form.dutyPhone" placeholder="请输入负责人手机"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
        <el-button type="primary" @click="handleOk" v-if="isCode !== 1">确 定</el-button>
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
import request from '@/utils/request'
import chooseMap from '@/components/chooseMapDialog'
export default {
  components: { chooseMap },
  name: 'addMaterialManage',
  props: ['dialogVisible', 'formInfo', 'isCode'],
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
      if (v) {
        // this.getAreaList()
        this.form = Object.assign({}, JSON.parse(JSON.stringify(this.formInfo)))
        console.log('新增编辑查看this.form', this.form)
      }
    }
  },
  data() {
    return {
      rules: {
        stuffName: [{ required: true, message: '请输入物资名称', trigger: 'blur' }],
        helpType: [{ required: true, message: '请选择救援类型', trigger: 'blur' }],
        stuffNum: [{ required: true, message: '请输入数量', trigger: 'blur' }],
        stuffUnit: [{ required: true, message: '请输入单位', trigger: 'blur' }],
        address: [{ required: true, message: '请输入场所地址', trigger: 'blur' }],
        dutyPerson: [{ required: true, message: '请输入负责人员', trigger: 'blur' }],
        dutyPhone: [{ required: true, message: '请输入负责人手机', trigger: 'blur' }],
        gpsx: [{ required: true, message: '请选择经度', trigger: 'blur' }],
        gpsy: [{ required: true, message: '请选择纬度', trigger: 'blur' }]
      },
      loading: false,
      kindList: [
        { id: '公园', name: '公园' },
        { id: '学校', name: '学校' }
      ],
      typeList: [
        { id: '室内场所', name: '室内场所' },
        { id: '室外场所', name: '室外场所' }
      ],
      form: {
        id: '',
        stuffName: '',
        address: '',
        stuffNum: '',
        stuffUnit: '',
        stuffType: '',
        dutyPerson: '',
        dutyPhone: '',
        region: ''
      },
      communityList: [],
      resourceList: [],
      sitesList: [],
      typeNameList: [],
      helpTypes: [],
      gpsInfo: '',
      dialogMap: false,
    }
  },
  mounted() {
    this.getCommunityList()
    this.getTypeNameList()
    this.getHelpType()
  },
  methods: {
    // 救援类别 // 物资类别
    getHelpType() {
      let url = '/binhuapis/menu/getHelpType'
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          this.helpTypes = res.data.data.data
        }
      })
    },
    getCommunityList() {
      let url = '/auth/v2/precinct/queryPrecinctByKind?precinctKind=110'
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          let data = res.data.data || []
          this.communityList = data
        }
      })
    },
    getTypeNameList() {
      let url = '/binhuapis/dict/dictTree?parentId=1'
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          let data = res.data.data || []
          this.typeNameList = data
        }
      })
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
      if (this.isCode === 2) {
        this.doAdd()
      }
      if (this.isCode === 0) {
        this.doEdit()
      }
    },
    doEdit() {
      this.loading = true
      let newForm = {
        id: this.form.id,
        stuffName: this.form.stuffName,
        address: this.form.address,
        stuffNum: this.form.stuffNum,
        stuffType: this.form.stuffType,
        dutyPerson: this.form.dutyPerson,
        helpType: this.form.helpType,
        dutyPhone: this.form.dutyPhone,
        region: this.form.region,
        stuffUnit: this.form.stuffUnit,
        gpsx: this.form.gpsx,
        gpsy: this.form.gpsy,
        type: 1
      }
      let url = '/binhuapis/resourceStuff/edit'
      request.post(url, newForm).then((r) => {
        this.loading = false
        if (r.data.code === 200) {
          this.$message.success('修改成功！')
          this.$emit('success')
          this.reset()
          this.$emit('update:dialogVisible', false)
        } else {
          this.$message.error(r.data.message)
        }
      })
    },
    doAdd() {
      this.loading = true
      let newForm = {
        stuffName: this.form.stuffName,
        address: this.form.address,
        stuffNum: this.form.stuffNum,
        stuffType: this.form.stuffType,
        helpType: this.form.helpType,
        dutyPerson: this.form.dutyPerson,
        dutyPhone: this.form.dutyPhone,
        region: this.form.region,
        stuffUnit: this.form.stuffUnit,
        gpsx: this.form.gpsx,
        gpsy: this.form.gpsy,
        type: 1
      }
      let url = '/binhuapis/resourceStuff/add'
      request.post(url, newForm).then((r) => {
        this.loading = false
        if (r.data.code === 200) {
          this.$message.success('新增成功！')
          this.$emit('success')
          this.reset()
          this.$emit('update:dialogVisible', false)
        } else {
          this.$message.error(r.data.message)
        }
      })
    },
    queryResourceList() {
      let url = '/binhuapis/resourceList/query'
      request.get(url).then(res => {
        this.resourceList = res.data.data.list.map(t => {
          t.id = t.id + ''
          return t
        })
      })
    },
    getSites() {
      let url = '/binhuapis/siteManage/query'
      request.get(url).then((res) => {
        this.sitesList = res.data.data.list.map(t => {
          t.id = t.id + ''
          return t
        })
      })
    },
    changeSiteName(selectItem) {
      let selectArr = null
      if (selectItem && selectItem != '') {
        selectArr = this.sitesList.filter(t => t.id === selectItem)
      }
      this.form.siteUnit = selectArr[0].community
      this.form.siteAddress = selectArr[0].address
      this.form.sitePerson = selectArr[0].person
      this.form.sitePhone = selectArr[0].phone
    },
    handlePlace() {
      this.gpsInfo = this.form.gpsx + ',' + this.form.gpsy
      this.dialogMap = true
    },
    gpsChoose(e) {
      this.form.gpsx = e.value.split(',')[0]
      this.form.gpsy = e.value.split(',')[1]
      this.dialogMap = false
    },
  }
}
</script>
<style lang="scss" scoped>
// .addSiteManage{}
</style>
