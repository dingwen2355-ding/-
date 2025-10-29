<template>
  <div class="emergencySupport">
    <el-tabs v-model="activeName" :tab-position="suportPosition" style="height: 500px;">
      <el-tab-pane label="应急专家" name="first">
        <el-form :inline="true" :model="yjzjForm" class="demo-form-inline" :disabled="isCode == 1">
          <el-form-item label="专家姓名">
            <el-input v-model="yjzjForm.name" clearable placeholder="专家姓名"></el-input>
          </el-form-item>
          <el-form-item label="行业领域">
            <el-select v-model="yjzjForm.type" clearable placeholder="行业领域">
              <el-option :label="item.name" v-for="(item,key) in expertTypes" :key="key" :value="item.dictOrder"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="yjzjOnSubmit">查询</el-button>
            <el-button type="primary" @click="resetSearch(activeName)">重置</el-button>
          </el-form-item>
        </el-form>
        <el-table
          :data="yjzjTableData" ref="yjzjTable"  @select="yjzjHandleSelect"
          style="width: 100%">
          <el-table-column v-if="isCode != 1"
            type="selection"
            width="55">
          </el-table-column>
          <el-table-column
            type="index"
            label="序号">
            <template slot-scope="scope">
              <span>{{(yjzjPage.currentPage - 1) * yjzjPage.pageSize + scope.$index + 1}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="name"
            label="专家姓名">
          </el-table-column>
          <el-table-column
            prop="phone"
            label="专家联系号码">
          </el-table-column>
          <el-table-column
            prop="expertTypeName"
            label="行业领域">
          </el-table-column>
          <el-table-column
            prop="company"
            label="专家工作单位">
          </el-table-column>
          <el-table-column
            prop="address"
            label="所述区别">
          </el-table-column>
        </el-table>
        <el-pagination
          background
          :current-page="yjzjPage.currentPage"
          @current-change="yjzjHandleCurrentChange"
          :page-size="yjzjPage.pageSize"
          layout="total, prev, pager, next, jumper"
          :total="yjzjPage.total"
        >
        </el-pagination>
      </el-tab-pane>
      <el-tab-pane label="救援队伍" name="second">
        <el-form :inline="true" :model="jydwForm" class="demo-form-inline" :disabled="isCode == 1">
          <el-form-item label="队伍名称">
            <el-input v-model="jydwForm.teamName" clearable placeholder="队伍名称"></el-input>
          </el-form-item>
          <el-form-item label="队伍类型">
            <el-select v-model="jydwForm.helpTypeName" placeholder="队伍类型" clearable>
              <el-option v-for="(item,key) in helpTypes" :key="key" :label="item.name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="jydwOnSubmit">查询</el-button>
            <el-button type="primary" @click="resetSearch(activeName)">重置</el-button>
          </el-form-item>
        </el-form>
        <el-table
          :data="jydwTableData" ref="jydwTable" @select="jydwHandleSelect"
          style="width: 100%">
          <el-table-column v-if="isCode != 1"
            type="selection"
            width="55">
          </el-table-column>
          <el-table-column
            type="index"
            label="序号">
            <template slot-scope="scope">
              <span>{{(jydwPage.currentPage - 1) * jydwPage.pageSize + scope.$index + 1}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="TEAM_NAME"
            label="队伍名称">
          </el-table-column>
          <el-table-column
            prop="HELP_TYPE"
            label="队伍类型">
          </el-table-column>
          <el-table-column
            prop="STORAGE_LEVEL"
            label="队伍级别">
          </el-table-column>
          <el-table-column
            prop="SPECIALITIES"
            label="适用场景">
          </el-table-column>
          <el-table-column
            prop="MANAGER"
            label="负责人">
          </el-table-column>
          <el-table-column
            prop="MANAGER_PHONE"
            label="负责人电话">
          </el-table-column>
          <el-table-column
            prop="REGION"
            label="所属区别">
          </el-table-column>
        </el-table>
        <el-pagination
          background
          :current-page="jydwPage.currentPage"
          @current-change="jydwHandleCurrentChange"
          :page-size="jydwPage.pageSize"
          layout="total, prev, pager, next, jumper"
          :total="jydwPage.total"
        >
        </el-pagination>
      </el-tab-pane>
      <el-tab-pane label="物资装备" name="third">
        <el-form :inline="true" :model="wzzbForm" class="demo-form-inline" :disabled="isCode == 1">
          <el-form-item label="物资名称">
            <el-input v-model="wzzbForm.stuffName" clearable placeholder="物资名称"></el-input>
          </el-form-item>
          <el-form-item label="物资类别">
            <el-select v-model="wzzbForm.stuffType" placeholder="物资类别" clearable>
              <el-option v-for="(item,key) in helpTypes" :key="key" :label="item.name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="wzzbOnSubmit">查询</el-button>
            <el-button type="primary" @click="resetSearch(activeName)">重置</el-button>
          </el-form-item>
        </el-form>
        <el-table
          :data="wzzbTableData" ref="wzzbTable" @select="wzzbHandleSelect" v-if="isCode != 1"
          style="width: 100%">
          <el-table-column
            type="selection"
            width="55">
          </el-table-column>
          <el-table-column
            type="index"
            label="序号">
            <template slot-scope="scope">
              <span>{{(wzzbPage.currentPage - 1) * wzzbPage.pageSize + scope.$index + 1}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="stuffName"
            label="物资名称">
          </el-table-column>
          <el-table-column
            prop="stuffTypeName"
            label="物资类别">
          </el-table-column>
          <el-table-column
            prop="address"
            label="存放地点">
          </el-table-column>
          <el-table-column
            prop="stuffNum"
            label="物资数量">
          </el-table-column>
          <el-table-column
            prop="dutyPerson"
            label="负责人">
          </el-table-column>
          <el-table-column
            prop="dutyPhone"
            label="负责人电话">
          </el-table-column>
          <el-table-column
            prop="regionName"
            label="所属区别">
          </el-table-column>
        </el-table>
        <el-pagination
          background
          :current-page="wzzbPage.currentPage"
          @current-change="wzzbHandleCurrentChange"
          :page-size="wzzbPage.pageSize"
          layout="total, prev, pager, next, jumper"
          :total="wzzbPage.total"
        >
        </el-pagination>
      </el-tab-pane>
      <el-tab-pane label="避难场所" name="fourth">
        <el-form :inline="true" :model="bncsForm" class="demo-form-inline" :disabled="isCode == 1">
          <el-form-item label="避难场所名称">
            <el-input v-model="bncsForm.shelterName" clearable placeholder="避难场所名称"></el-input>
          </el-form-item>
          <el-form-item label="场所类型">
            <el-select v-model="bncsForm.shelterCategory" placeholder="场所类型" clearable>
              <el-option v-for="(item,key) in shelterTypes" :key="key" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="bncsOnSubmit">查询</el-button>
            <el-button type="primary" @click="resetSearch(activeName)">重置</el-button>
          </el-form-item>
        </el-form>
        <el-table ref="bncsTable"
          :data="bncsTableData" @select="bncsHandleSelect"
          style="width: 100%">
          <el-table-column v-if="isCode != 1"
            type="selection"
            width="55">
          </el-table-column>
          <el-table-column
            type="index"
            label="序号">
            <template slot-scope="scope">
              <span>{{(bncsPage.currentPage - 1) * bncsPage.pageSize + scope.$index + 1}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="shelterName"
            label="场所名称">
          </el-table-column>
          <el-table-column
            prop="shelterType"
            label="场所类别">
          </el-table-column>
          <el-table-column
            prop="shelterAddress"
            label="场所地址">
          </el-table-column>
          <el-table-column
            prop="shelterPerson"
            label="联系人">
          </el-table-column>
          <el-table-column
            prop="shelterPhone"
            label="联系人号码">
          </el-table-column>
          <el-table-column
            prop="shelterOrga"
            label="所属区划">
          </el-table-column>
        </el-table>
        <el-pagination
          background
          :current-page="bncsPage.currentPage"
          @current-change="bncsHandleCurrentChange"
          :page-size="bncsPage.pageSize"
          layout="total, prev, pager, next, jumper"
          :total="bncsPage.total"
        >
        </el-pagination>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import request from '@/utils/request'

export default {
  name: 'emergencySupport',
  props: ['formInfo', 'viewData', 'planId', 'isCode', 'suportPosition'],
  data() {
    return {
      yjzjForm: {
        name: '',
        type: ''
      },
      activeName: 'first',
      jydwForm: {
        teamName: '',
        helpTypeName: ''
      },
      wzzbForm: {
        stuffName: '',
        stuffType: ''
      },
      bncsForm: {
        shelterName: '',
        shelterCategory: ''
      },
      yjzjTableData: [],
      jydwTableData: [],
      wzzbTableData: [],
      bncsTableData: [],
      yjzjPage: {
        currentPage: 1, // 当前页数
        pageSize: 3, // 每页条数
        total: 0 // 总条数
      },
      jydwPage: {
        currentPage: 1, // 当前页数
        pageSize: 3, // 每页条数
        total: 0 // 总条数
      },
      wzzbPage: {
        currentPage: 1, // 当前页数
        pageSize: 3, // 每页条数
        total: 0 // 总条数
      },
      bncsPage: {
        currentPage: 1, // 当前页数
        pageSize: 3, // 每页条数
        total: 0 // 总条数
      },
      yjzjValue: [],
      jydwValue: [],
      wzzbValue: [],
      bncsValue: [],
      reForm: {

      },
      expertTypes: [],
      helpTypes: [],
      shelterTypes: [
        {
          label: '安置点',
          value: '安置点'
        },
        {
          label: '室内场所',
          value: '室内场所'
        }
      ]
    }
  },
  mounted() {
    this.getBasicExpertType()
    this.getHelpType()
    this.loadData()
  },
  methods: {
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`)
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`)
    },
    yjzjHandleCurrentChange(val) {
      this.yjzjPage.currentPage = val
      this.getBasicExpertList()
    },
    jydwHandleCurrentChange(val) {
      this.jydwPage.currentPage = val
      this.queryRescueTeamInfo()
    },
    wzzbHandleCurrentChange(val) {
      this.wzzbPage.currentPage = val
      this.queryResourceStuff()
    },
    bncsHandleCurrentChange(val) {
      this.bncsPage.currentPage = val
      this.queryBasicShelter()
    },
    loadData() {
      this.getBasicExpertList()
      this.queryRescueTeamInfo()
      this.queryResourceStuff()
      this.queryBasicShelter()
    },
    clearAllTable() {
      this.$refs.yjzjTable.clearSelection()
      this.$refs.jydwTable.clearSelection()
      this.$refs.wzzbTable.clearSelection()
      this.$refs.bncsTable.clearSelection()
    },
    yjzjOnSubmit() {
      this.getBasicExpertList()
    },
    jydwOnSubmit() {
      this.queryRescueTeamInfo()
    },
    wzzbOnSubmit() {
      this.queryResourceStuff()
    },
    bncsOnSubmit() {
      this.queryBasicShelter()
    },
    // 重置
    resetSearch(v) {
      if (v === 'first') {
        this.yjzjForm = {
          name: '',
          type: ''
        }
      } else if (v === 'second') {
        this.jydwForm = {
          teamName: '',
          helpTypeName: ''
        }
      } else if (v === 'third') {
        this.wzzbForm = {
          stuffName: '',
          stuffType: ''
        }
      } else {
        this.bncsForm = {
          shelterName: '',
          shelterCategory: ''
        }
      }
    },
    // 应急专家
    getBasicExpertList() {
      if (this.yjzjForm.name !== '') {
        this.yjzjPage.currentPage = 1
      }
      let url = `/binhuapis/basicExpert/getBasicExpertList?pageLen=${this.yjzjPage.pageSize}&pageStart=${this.yjzjPage.currentPage}&name=${this.yjzjForm.name}&type=${this.yjzjForm.type}` + '&userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")
      if (this.planId) {
        url = url + `&planId=${this.planId}`
      }
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          this.yjzjTableData = res.data.data.data || []
          this.yjzjPage.total = res.data.data.count
        }
      }).then(() => {
        this.yjzjTableData.forEach(t => {
          if (this.viewData.planSupportGroups.experts.includes(t.id)) {
            this.$refs.yjzjTable.toggleRowSelection(t, true)
          }
        })
      })
    },
    // 救援队伍
    queryRescueTeamInfo() {
      if (this.jydwForm.teamName !== '') {
        this.jydwPage.currentPage = 1
      }
      let url = '/binhuapis/teamInfo/queryRescueTeamInfo?userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName") + `&pageLen=${this.jydwPage.pageSize}&pageStart=${this.jydwPage.currentPage}&teamName=${this.jydwForm.teamName}&helpTypeName=${this.jydwForm.helpTypeName}`
      if (this.planId) {
        url = url + `&planId=${this.planId}`
      }
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          this.jydwTableData = res.data.data.teamList || []
          this.jydwPage.total = res.data.data.total
        }
      }).then(() => {
        this.jydwTableData.forEach(t => {
          console.log('商家纷纷为看来我', this.viewData.planSupportGroups.teams.includes(t.ID))
          let data = []
          this.viewData.planSupportGroups.teams.forEach(item => {
            data.push(Number(item))
          })
          this.viewData.planSupportGroups.teams = data
          if (this.viewData.planSupportGroups.teams.includes(t.ID)) {
            this.$refs.jydwTable.toggleRowSelection(t, true)
          }
        })
      })
    },
    // 物资装备
    queryResourceStuff() {
      if (this.wzzbForm.stuffName !== '') {
        this.wzzbPage.currentPage = 1
      }
      let url = `/binhuapis/resourceStuff/query?page=${this.wzzbPage.currentPage}&pageSize=${this.wzzbPage.pageSize}&stuffName=${this.wzzbForm.stuffName}&stuffType=${this.wzzbForm.stuffType}` + '&userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")
      if (this.planId) {
        url = url + `&planId=${this.planId}`
      }
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          this.wzzbTableData = res.data.data.list || []
          this.wzzbPage.total = res.data.data.total
        }
      }).then(() => {
        this.wzzbTableData.forEach(t => {
          let data = []
          this.viewData.planSupportGroups.stuffs.forEach(item => {
            data.push(Number(item))
          })
          this.viewData.planSupportGroups.stuffs = data
          if (this.viewData.planSupportGroups.stuffs.includes(t.id)) {
            this.$refs.wzzbTable.toggleRowSelection(t, true)
          }
        })
      })
    },
    // 避难场所
    queryBasicShelter() {
      if (this.bncsForm.shelterName !== '') {
        this.bncsPage.currentPage = 1
      }
      let url = `/binhuapis/siteManage/queryForResource?pageLen=${this.bncsPage.pageSize}&pageStart=${this.bncsPage.currentPage}&shelterCategory=${this.bncsForm.shelterCategory}&shelterName=${this.bncsForm.shelterName}`
      if (this.planId) {
        url = url + `&planId=${this.planId}`
      }
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          this.bncsTableData = res.data.data.data || []
          this.bncsPage.total = res.data.data.count
        }
      }).then(() => {
        this.bncsTableData.forEach(t => {
          let data = []
          this.viewData.planSupportGroups.shelters.forEach(item => {
            data.push(Number(item))
          })
          this.viewData.planSupportGroups.shelters = data
          if (this.viewData.planSupportGroups.shelters.includes(t.id)) {
            this.$refs.bncsTable.toggleRowSelection(t, true)
          }
        })
      })
    },
    // 专家类型
    getBasicExpertType() {
      let url = '/binhuapis/basicExpert/getBasicExpertType'
      request.post(url).then((res) => {
        if (res.data.code === 200) {
          this.expertTypes = res.data.data
        }
      })
    },
    // 救援类别 // 物资类别
    getHelpType() {
      let url = '/binhuapis/menu/getHelpType'
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          this.helpTypes = res.data.data.data
        }
      })
    },
    yjzjHandleSelect(selection, row) {
      let exist1 = this.viewData.planSupportGroups.experts.filter(t => t === row.id)
      if (exist1.length === 0) {
        this.viewData.planSupportGroups.experts.push(row.id)
      } else {
        this.viewData.planSupportGroups.experts = this.viewData.planSupportGroups.experts.filter(t => t !== row.id)
      }
    },
    jydwHandleSelect(selection, row) {
      let exist1 = this.viewData.planSupportGroups.teams.filter(t => t === row.ID)
      if (exist1.length === 0) {
        this.viewData.planSupportGroups.teams.push(row.ID)
      } else {
        this.viewData.planSupportGroups.teams = this.viewData.planSupportGroups.teams.filter(t => t !== row.ID)
      }
    },
    wzzbHandleSelect(selection, row) {
      let exist1 = this.viewData.planSupportGroups.stuffs.filter(t => t === row.id)
      if (exist1.length === 0) {
        this.viewData.planSupportGroups.stuffs.push(row.id)
      } else {
        this.viewData.planSupportGroups.stuffs = this.viewData.planSupportGroups.stuffs.filter(t => t !== row.id)
      }
    },
    bncsHandleSelect(selection, row) {
      let exist1 = this.viewData.planSupportGroups.shelters.filter(t => t === row.id)
      if (exist1.length === 0) {
        this.viewData.planSupportGroups.shelters.push(row.id)
      } else {
        this.viewData.planSupportGroups.shelters = this.viewData.planSupportGroups.shelters.filter(t => t !== row.id)
      }
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
