<template>
  <section class="AlarmRecord">
    <el-row class="header" v-if="roleId !== '14'">
      <el-col :span="44">
        <el-form :inline="true" :model="form" ref="form">
          <el-form-item label="所在辖区:">
            <el-select filterable v-model="form.region" placeholder="请选择所在辖区" clearable>
              <el-option
                v-for="item in areaList"
                :value="item.regionId"
                :label="item.regionName"
                :key="item.regionId"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="队伍性质:">
            <el-select filterable v-model="form.beManagedType" placeholder="请选择队伍性质" clearable>
              <el-option
                v-for="item in manageTypeList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="专兼类型:">
            <el-select filterable v-model="form.fullPartTimeType" placeholder="请选择专兼类型" clearable>
              <el-option
                v-for="item in fullPartTimeList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="管理级别:">
            <el-select filterable clearable v-model="form.storageLevel" placeholder="请选择管理级别">
              <el-option
                v-for="item in manageLevelList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="所属行业:">
            <el-select filterable clearable v-model="form.industry" placeholder="请选择所属行业">
              <el-option
                v-for="item in industryList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="队伍类型:">
            <el-select filterable v-model="form.majorType" placeholder="请选择队伍类型" clearable>
              <el-option
                v-for="item in majorList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="救援类别:">
            <el-select filterable v-model="form.helpType" placeholder="请选择救援类别" clearable>
              <el-option
                v-for="item in saveTypeList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="队伍名称:">
            <el-input v-model="form.teamName" placeholder="请选择队伍名称"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size="small" @click="onSubmit">查询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size="small" @click="addPlan">新增</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="danger" size="small" @click="delPlan">删除</el-button>
          </el-form-item>
          <el-form-item>
            <el-upload
              class="btn2"
              ref="upload"
              action="/"
              size="small"
              :file-list="fileList"
              :show-file-list="false"
              :on-change="handleChange"
              :auto-upload="false"
              style="display: inline-block"
            >
              <el-button type="primary" size="small" icon="el-icon-upload2">导入</el-button>
            </el-upload>
            <a download="救援队伍导入模板" href="template/救援队伍导入模板.xlsx" class="sel_btn">
              点击下载导入模板
              <i class="el-icon-download"></i>
            </a>
          </el-form-item>
          <el-form-item>
            <el-button
              class="btn3"
              type="success"
              size="small"
              icon="el-icon-download"
              @click="exportData()"
            >导出</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <div>
      <el-table
        v-loading="loading"
        class="tableInfo"
        border
        :data="tableData"
        style="width: 100%"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="REGION" label="所在辖区"></el-table-column>
        <el-table-column prop="TEAM_NAME" label="队伍名称"></el-table-column>
        <el-table-column prop="member_number" label="队员人数"></el-table-column>
        <el-table-column prop="HELP_TYPE" label="救援类别"></el-table-column>
        <el-table-column prop="SPECIALITIES" label="擅长领域"></el-table-column>
        <el-table-column prop="STORAGE_LEVEL" label="管理级别"></el-table-column>
        <el-table-column prop="MANAGER" label="联系人"></el-table-column>
        <el-table-column prop="MANAGER_PHONE" label="联系人电话"></el-table-column>
        <el-table-column fixed="right" label="操作" width="220">
          <template slot-scope="scope">
            <el-button @click="handleEdit(scope.row, 1)" type="text" size="medium">查看</el-button>
            <!-- <el-button @click="handleStock(scope.row)" type="text" size="medium">物资管理</el-button> -->
            <el-button @click="handleEdit(scope.row, 0)" type="text" size="medium">编辑</el-button>
            <el-button
              @click="handleDelete(scope.row)"
              type="text"
              size="medium"
              v-if="roleId !== '14'"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-pagination
      background
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="page"
      :page-size="pageSize"
      :page-sizes="[10, 50, 100, 500, 1000]"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    ></el-pagination>
    <rescue-teams-add :dialogVisible.sync="showAdd" @success="handleSuccess" :formInfo="formInfo" />
    <material-management-dialog
      :dialogVisible.sync="showMaterialManagement"
      :wareHouseName="wareHouseName"
      :wareHouseId="wareHouseId"
      @cancel="afterCancel"
    ></material-management-dialog>
  </section>
</template>
<script>
import Axios from '@/utils/request'
// import xlsx from 'xlsx'
import RescueTeamsAdd from './RescueTeamsAdd.vue'
import MaterialManagementDialog from '../../components/MaterialManagementOption/MaterialManagementDialog.vue'
import axios from "@/utils/request";
export default {
  components: { RescueTeamsAdd, MaterialManagementDialog },
  name: 'rescueTeams',
  data() {
    return {
      areaList: [], // 所属辖区
      manageTypeList: [], // 管理类型
      fullPartTimeList: [], // 专兼类型
      manageLevelList: [], // 管理级别
      industryList: [], // 所属行业
      saveTypeList: [], // 救援类别
      majorList: [], // 擅长领域
      roleId: localStorage.roleId,
      userName: localStorage.user,
      form: {
        region: null, // 所属辖区
        beManagedType: null, // 管理类型
        fullPartTimeType: null, // 专兼类型
        storageLevel: null, // 管理级别
        industry: null, // 所属行业
        majorType: null, // 擅长领域
        helpType: null, // 救援类别
        teamName: null // 队伍名称
      },
      loading: false,
      tableData: [],
      page: 1,
      pageSize: 10,
      total: 0,
      showAdd: false,
      formInfo: {},
      newForm: {},
      fileList: [],
      multipleSelection: '',
      showMaterialManagement: false,
      wareHouseName: '',
      wareHouseId: ''
    }
  },
  mounted() {
    this.getAreaList() // 所属辖区
    this.getManageType() // 管理类型
    this.getFullPartTimeDict() // 专兼类型
    this.getManageLevel() // 管理级别
    this.getIndustry() // 所属行业
    this.getMajor() // 擅长领域
    this.getSaveType() // 救援类别
    this.queryTeamInfo() // 救援队伍信息列表
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
    // 管理类型
    getManageType() {
      let url = '/binhuapis/menu/getBeManagedType'
      Axios.get(url).then((r) => {
        this.manageTypeList = r.data.data.data
      })
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
    },
    // 救援队伍信息列表
    queryTeamInfo() {
      this.loading = true
      let params = {
        pageStart: this.page,
        pageLen: this.pageSize
      }
      if (this.form.region) {
        params.area = this.form.region
      } else {
        if (this.userName !== 'admin') {
          params.area = localStorage.region
        }
      }
      if (this.form.beManagedType) {
        params.teamType = this.form.beManagedType
      }
      if (this.form.fullPartTimeType) {
        params.apType = this.form.fullPartTimeType
      }
      if (this.form.storageLevel) {
        params.storageLevel = this.form.storageLevel
      }
      if (this.form.industry) {
        params.industry = this.form.industry
      }
      if (this.form.majorType) {
        params.specialities = this.form.majorType
      }
      if (this.form.helpType) {
        params.helpTypeName = this.form.helpType
      }
      if (this.form.teamName) {
        params.teamName = this.form.teamName
      }
      let url = '/binhuapis/teamInfo/queryRescueTeamInfo?userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName") + '&userLevel=' + localStorage.level
      let url2 = `/binhuapis/teamInfo/queryRescueTeamInfoByIdNew?teamId=${localStorage.stockId}`
      if (localStorage.roleId === '14') {
        Axios.get(url2).then((r) => {
          let arr = []
          arr.push(r.data.data)
          this.tableData = arr
          this.total = arr.length
          this.loading = false
        })
      } else {
        Axios.get(url, { params }).then((r) => {
          this.tableData = r.data.data.teamList
          this.total = r.data.data.total
          this.loading = false
        })
      }
    },
    handleStock(row) {
      this.showMaterialManagement = true
      this.wareHouseId = row.ID
      this.wareHouseName = row.TEAM_NAME
      // let name = row.TEAM_NAME
      // this.$router.push({
      //   path: `/storageInventory?stockName=${name}`
      // })
    },
    afterCancel() {
      this.page = 1
      this.queryTeamInfo()
    },
    // 导入
    handleChange(file, fileList) {
      this.file = file
      this.fileList = fileList
      this.importExcel()
    },
    importExcel() {
      this.file = this.fileList[0]
      console.log('导入文件file', this.file)
      const types = this.file.name.split('.')[1]
      console.log('导入文件类型', types)
      const fileType = ['xlsx', 'xls'].some((item) => item === types)
      if (!fileType) {
        this.$message.warning('格式错误！请重新选择')
        return
      }
      let formData = new FormData()
      formData.append('file', this.file.raw)
      let url = '/binhuapis/teamInfo/teamExcel?userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")
      console.log('导入url', url, 'formData', formData)
      let file = formData
      Axios.post(url, file)
        .then((res) => {
          if (res.data.code === 200) {
            this.$message.success('导入成功')
            this.page = 1
            this.queryTeamInfo() // 调用查询接口
            this.$refs.upload.clearFiles()
          } else {
            this.$message({
              type: 'error',
              message: res.data.message
            })
            this.$refs.upload.clearFiles()
          }
        })
        .catch((err) => {
          console.log(err)
          this.$message.error('导入失败')
        })
    },
    handleSelectionChange(v) {
      this.multipleSelection = v
    },
    // 导出
    exportData() {
      let url = '/binhuapis/teamInfo/exportTeamExcel?userLevel=' + localStorage.level
      if (this.form.region) {
        url = url + '&area=' + this.form.region
      }
      if (this.form.beManagedType) {
        url = url + '&teamType=' + this.form.beManagedType
      }
      if (this.form.fullPartTimeType) {
        url = url + '&apType=' + this.form.fullPartTimeType
      }
      if (this.form.storageLevel) {
        url = url + '&storageLevel=' + this.form.storageLevel
      }
      if (this.form.industry) {
        url = url + '&industry=' + this.form.industry
      }
      if (this.form.majorType) {
        url = url + '&specialities=' + this.form.majorType
      }
      if (this.form.helpType) {
        url = url + '&helpTypeName=' + this.form.helpType
      }
      if (this.form.teamName) {
        url = url + '&teamName=' + this.form.teamName
      }
      Axios.post(url)
        .then((res) => {
          if (res.data.code == 200) {
            let fileUrl = res.data.data
            window.open(fileUrl, '_bank')
            this.$message.success('导出成功')
          } else {
            this.$message({
              type: 'error',
              message: res.data.message
            })
          }
        })
        .catch(() => {
          this.$message.error('导出失败')
        })
    },
    // 查询
    onSubmit() {
      this.page = 1
      this.queryTeamInfo()
    },
    // 新增
    addPlan() {
      this.showAdd = true
      this.reset()
      this.$root.eventBus.$emit('isCode', 2)
    },
    reset() {
      this.formInfo = {
        id: '',
        city: 1, // 所属地市 默认为1 即淄博市
        teamName: '', // 队伍名称
        belongUnit: '', // 管理单位
        region: '', // 所属辖区
        beManagedType: '', // 管理类型
        apType: '', // 专兼类型
        storageLevel: '', // 管理级别
        manager: '', // 联系人
        managerPhone: '', // 联系人电话
        mainPeople: '', // 主要负责人
        dutyPhone: '', // 值班电话
        serviceArea: '', // 服务领域
        memberNumber: '', // 队员人数
        longitude: '', // 经度
        latitude: '', // 纬度
        industry: '', // 所属行业
        specialities: '', // 擅长领域
        helpType: '', // 救援类别
        address: '', // 详细地址
        orgSet: '', // 机构设置简介
        teamInfo: '', // 队伍信息简介
        aptitudeInfo: '', // 参与重大灾害救援及获得资质证书情况
        // 分队信息列表
        teamGroupInfos: [],
        // 核心人员信息列表
        resourceTeamMembers: [],
        // 年度训练情况列表
        annualTrainingInfos: [],
        isUseAble: 'Y' // 是否可用 默认为'Y'即可用
      }
    },
    handleSuccess() {
      this.page = 1
      this.queryTeamInfo()
    },
    handleEdit(row, code) {
      console.log('row.ID: ', row.ID)
      let url = '/binhuapis/teamInfo/queryRescueTeamInfoById?teamId=' + row.ID
      Axios.get(url).then((res) => {
        if (res.data.code === 200) {
          let newObj = res.data.data || {}
          this.newForm = JSON.parse(JSON.stringify(newObj))
          if (this.newForm.apType) {
            this.newForm.apType = Number(this.newForm.apType)
          }
          if (this.newForm.industry) {
            this.newForm.industry = this.newForm.industry.split(',').map(Number)
          }
          if (this.newForm.specialities) {
            this.newForm.specialities = this.newForm.specialities.split(',').map(Number)
          }
          if (this.newForm.helpType) {
            this.newForm.helpType = this.newForm.helpType.split(',').map(Number)
          }
          if (this.newForm.belongUnit == 0) {
            this.newForm.belongUnit = ''
          }
          if (this.newForm.storageLevel == 0) {
            this.newForm.storageLevel = ''
          }
          this.formInfo = this.newForm
          console.log(this.formInfo, 'this.formInfo this.formInfo ')
        } else {
          this.$message.error(res.data.message)
        }
      })
      this.$root.eventBus.$emit('isCode', code)
      this.showAdd = true
    },
    delPlan() {
      this.$confirm('此操作将删除选中的队伍, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let ids = this.multipleSelection.map((item) => {
          return item.ID
        })
        let url = `/binhuapis/teamInfo/batchDeleteTeam?ids=${ids.join(',')}`
        Axios.get(url).then((r) => {
          if (r.data.code === 200) {
            this.$message.success('删除成功')
            this.onSubmit()
          } else {
            this.$message(r.data.message)
          }
        })
        console.log(ids.join(','))
      })
    },
    handleDelete(row) {
      console.log('row', row)
      console.log('row.id', row.id)
      this.$confirm('此操作将删除该信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          let url = '/binhuapis/teamInfo/deleteTeam?id=' + row.ID
          Axios.get(url).then((res) => {
            if (res.data.code === 200) {
              this.$message.success('删除成功')
              this.currentPage = 1
              this.form = {
                region: null, // 所属辖区
                beManagedType: null, // 管理类型
                fullPartTimeType: null, // 专兼类型
                storageLevel: null, // 管理级别
                industry: null, // 所属行业
                majorType: null, // 擅长领域
                helpType: null, // 救援类别
                teamName: null // 队伍名称
              }
              this.queryTeamInfo()
            } else {
              this.$message.error(res.data.message)
            }
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    handleCurrentChange(val) {
      console.log('当前页', val)
      this.page = val
      this.queryTeamInfo()
    },
    handleSizeChange(val) {
      console.log('一页展示', val, '条数据')
      this.pageSize = val
      this.queryTeamInfo()
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
