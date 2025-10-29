<template>
  <section class="townshipLevel">
    <el-row class="header">
      <el-col :span="44">
        <el-form :inline="true" :model="searchForm" ref="form">
          <el-form-item label="预案名称">
            <el-input v-model="searchForm.planName" clearable placeholder="请输入预案名称"></el-input>
          </el-form-item>
          <el-form-item label="所属辖区" prop="regionId">
            <el-select
              v-model="searchForm.regionId"
              placeholder="所在辖区"
              clearable
              style="width: 100%"
            >
              <el-option
                v-for="item in areaList"
                :value="item.id"
                :label="item.name"
                :key="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="发布单位分类">
            <el-select filterable clearable v-model="searchForm.unitTypeId" placeholder="请选择发布单位分类">
              <el-option
                v-for="item in unitTypeList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="发布单位名称">
            <el-input v-model="searchForm.unitName" clearable placeholder="请输入发布单位名称"></el-input>
          </el-form-item>
          <el-form-item label="预案分类">
            <el-select filterable clearable v-model="searchForm.planKind" placeholder="请选择分类">
              <el-option
                v-for="item in eventTypeList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="应对灾种">
            <el-cascader
              filterable
              v-model="searchForm.planType"
              :options="options"
              :props="props"
              placeholder="请选择应对灾种"
              clearable
              collapse-tags
            ></el-cascader>
          </el-form-item>
          <el-form-item label="发布时间">
            <el-date-picker
              v-model="searchForm.time"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="yyyy-MM-dd"
              style="width: 380px"
            ></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size="small" @click="onSubmit">查询</el-button>
            <el-button type="primary" size="small" icon="el-icon-plus" @click="addPlan">新增</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <div style="height: 655px; overflow: scroll">
      <el-table v-loading="loading" class="tableInfo" border :data="tableData" style="width: 100%">
        <el-table-column type="index" label="序号" width="50"></el-table-column>
        <el-table-column prop="regionName" label="属地"></el-table-column>
        <el-table-column prop="name" label="预案名称"></el-table-column>
        <el-table-column prop="unitTypeName" label="发布单位分类"></el-table-column>
        <el-table-column prop="unitName" label="发布单位名称"></el-table-column>
        <el-table-column prop="eventTypeName" label="预案分类"></el-table-column>
        <el-table-column prop="emergencyTypeName" label="应对灾种"></el-table-column>
        <el-table-column prop="releaseTime" label="发布时间"></el-table-column>
        <el-table-column fixed="right" label="操作" width="180">
          <template slot-scope="scope">
            <el-button @click="handleView(scope.row)" type="text" size="medium">详情</el-button>
            <el-button @click="handleEdited(scope.row)" type="text" size="medium">编辑</el-button>
            <el-button @click="handleDelete(scope.row)" type="text" size="medium">删除</el-button>
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
      :page-sizes="[10, 50, 100, 500]"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    ></el-pagination>
    <plan-view-dialog :dialogVisible.sync="showViewDialog" :planId="planId"></plan-view-dialog>
    <!-- <EditedPrecinct :showEditedDialog.sync="showEditedDialog" :planId="planIdEdited" @success="onSubmit" /> -->
    <add-plan-dialog
      :dialogVisible.sync="showPlanAdd"
      @success="handlePlanSuccess"
      :rowId="rowId"
      :formInfo="formPlanInfo"
      :is-code="isPlanCode"
      :addShow="true"
      :levelId="tabLevel"
    ></add-plan-dialog>
    <show-history-plan
      :dialogVisible.sync="showHistoryPlane"
      @success="handlePlanSuccess"
      :rowId="rowIds"
      :formInfo="formPlanInfo"
      :is-code="isPlanCode"
      :addShow="true"
    ></show-history-plan>
  </section>
</template>
<script>
import Axios from '@/utils/request'
// import znvTime from '../../../utils/znvTime'
import planViewDialog from '../../EmergencyPlan/emergencyPlanOption/planViewDialog.vue'
import EditedPrecinct from './editedPrecinct.vue'
import AddPlanDialog from '@/views/EmergencyPlanNew/optionPlan/addPlanDialog.vue'
import znvTime from '@/utils/znvTime'
import ShowHistoryPlan from '@/views/EmergencyPlanNew/optionPlan/showHistoryPlan.vue'

export default {
  // eslint-disable-next-line vue/no-unused-components
  components: { ShowHistoryPlan, AddPlanDialog, planViewDialog, EditedPrecinct },
  name: 'townshipLevel',
  data() {
    return {
      rowIds: 0,
      showHistoryPlane: false,
      userId: sessionStorage.getItem('userId'),
      loading: false,
      tableData: [],
      showPlanAdd: false,
      page: 1,
      rowId: 0,
      showRightContent: false,
      pageSize: 10,
      total: 0,
      searchForm: {},
      options: [],
      props: { multiple: true, emitPath: false },
      eventTypeList: [], // 预案分类
      unitTypeList: [], // 发布单位分类
      manageLevelList: [], // 管理级别
      tabLevel: '985',
      areaList: [],
      showViewDialog: false,
      planId: '',
      showEditedDialog: false,
      planIdEdited: '',
      formPlanInfo: {
        id: '',
        name: '', // 名字
        levelId: '', // 级别
        levelName: '',
        eventTypeId: '', // 类型
        eventTypeName: '',
        emergencyTypeId: '', // 分类
        emergencyTypeName: '',
        status: '0', // 状态
        shared: '0', // 是否共享
        createId: localStorage.getItem('userId'),
        createName: localStorage.getItem('user'),
        createTime: znvTime.getCurrentTimeByFormat('YYYY-MM-DD HH:mm:SS'), // 上传时间
        fileUrls: [], // 上传文档
        unitTypeId: '',
        unitId: '',
        unitName: ''
      },
      isPlanCode: 0 // 0 编辑 2 新增
    }
  },
  mounted() {
    this.queryPlanType()
    this.queryEventType()
    this.queryUnitType()
    this.queryTableList()
    this.getAreaList()
  },
  methods: {
    // 获取所属区县
    getAreaList() {
      // ?userLevel=${localStorage.level}&userId=${localStorage.userId}
      let url = '/binhuapis/menu/getRegion'
      Axios.get(url).then(res => {
        let data = res.data.data || []
        this.areaList = data.data
        console.log(this.areaList, 'this.areaList')
      })
    },
    // 发布单位分类获取
    queryUnitType() {
      let url = '/binhuapis/getUnitType'
      Axios.get(url).then((r) => {
        // console.log('分类获取', r.data.data)
        this.unitTypeList = r.data.data
      })
    },
    // 分类获取
    queryEventType() {
      let url = '/binhuapis/get-event-type'
      Axios.get(url).then((r) => {
        // console.log('分类获取', r.data.data)
        this.eventTypeList = r.data.data
      })
    },
    // 应对灾种获取
    queryPlanType() {
      let url = '/binhuapis/get-type-tree'
      Axios.get(url).then((r) => {
        // console.log('类型信息：', r.data.data)
        this.options = r.data.data
      })
    },
    // 获取
    queryTableList() {
      this.loading = true
      let url = '/binhuapis/query-plan?userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")
      let params = {
        pageNum: this.page,
        pageSize: this.pageSize,
        //  userId: localStorage.getItem('userId'),
        levelId: this.tabLevel
      }
      if (this.searchForm.regionId) {
        params.regionId = this.searchForm.regionId
      }
      if (this.searchForm.planName) {
        params.name = this.searchForm.planName
      }
      if (this.searchForm.unitTypeId) {
        params.unitTypeId = this.searchForm.unitTypeId
      }
      if (this.searchForm.unitName) {
        params.unitName = this.searchForm.unitName
      }
      if (this.searchForm.planKind) {
        params.eventTypeId = this.searchForm.planKind
      }
      let typeParams = ''
      if (this.searchForm.planType) {
        if (this.searchForm.planType.length > 0) {
          // if (Array.isArray(this.searchForm.planType)) {
          this.searchForm.planType.forEach(item => {
            // console.log("类型--item", item);
            // typeParams.push(item[0])
            // typeParams.push(item[1])
            typeParams += item + ','
          })
          typeParams = typeParams.substring(0, typeParams.lastIndexOf(','))
          params.emergencyTypeId = typeParams
        }
      }
      if (this.searchForm.time) {
        params.beginTime = this.searchForm.time[0]
        params.endTime = this.searchForm.time[1]
      }
      Axios.post(url, params).then((res) => {
        console.log(res.data.data)
        this.tableData = res.data.data.list
        this.total = res.data.data.total
        this.loading = false
      })
    },
    handleEdited(row) {
      // this.$refs['edited'].getPrecinctTree()
      // console.log('row: ', row)
      // this.planIdEdited = row.id
      // this.showEditedDialog = true
      this.rowId = row.id
      this.showPlanAdd = true
      this.isPlanCode = 0
      if (row.emergencyTypeId && !Array.isArray(row.emergencyTypeId)) {
        row.emergencyTypeId = row.emergencyTypeId.split(',')
      }
      if (row.status === '未发布') {
        row.status = '0'
      } else if (row.status === '待审核') {
        row.status = '1'
      } else if (row.status === '通过') {
        row.status = '2'
      } else if (row.status === '退回') {
        row.status = '3'
      }
      this.formPlanInfo = row
    },
    handleDelete(row) {
      let url = '/binhuapis/del-plan'
      let params = [row.id]
      Axios.post(url, params).then((res) => {
        if (res.data.code === 200) {
          this.page = 1
          this.queryTableList()
          this.$message.success('删除成功！')
        } else {
          this.$message.error(res.data.message)
        }
      }).catch(e => {
      })
    },
    // 查询
    onSubmit() {
      // console.log("类型--searchForm", this.searchForm);
      this.page = 1
      this.queryTableList()
    },
    // 新增
    addPlan() {
      this.isPlanCode = 2
      this.showPlanAdd = true
      this.resetPlan()
    },
    resetPlan() {
      this.formPlanInfo = {
        id: '',
        name: '', // 名字
        levelId: '', // 级别
        levelName: '',
        eventTypeId: '', // 类型
        eventTypeName: '',
        emergencyTypeId: '', // 分类
        emergencyTypeName: '',
        status: '0', // 状态
        shared: '0', // 是否共享
        createId: localStorage.getItem('userId'),
        createName: localStorage.getItem('user'),
        createTime: znvTime.getCurrentTimeByFormat('YYYY-MM-DD HH:mm:SS'), // 上传时间
        fileUrls: [], // 上传文档
        unitTypeId: '',
        unitId: '',
        unitName: ''
      }
    },
    // 分页管理
    handleCurrentChange(val) {
      // console.log('当前页', val)
      this.page = val
      this.queryTableList()
    },
    handleSizeChange(val) {
      // console.log('一页展示', val, '条数据')
      this.pageSize = val
      this.queryTableList()
    },
    // 新增后回调函数
    handlePlanSuccess() {
      this.page = 1
      this.queryTableList()
      this.showRightContent = false
    },
    // 获取预案list
    queryMyPlanList() {
      this.loading = true
      let url = '/binhuapis/query-plan?userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")
      let params = {
        pageNum: this.page,
        pageSize: this.pageSize,
        userId: localStorage.getItem('userId'),
        status: ['0', '3']
      }
      if (this.searchform.planName) {
        params.name = this.searchform.planName
      }
      // console.log('params', params)
      Axios.post(url, params).then((res) => {
        this.planTableData = res.data.data.list
        this.total = res.data.data.total
        this.loading = false
      })
    },
    // 详情
    handleView(row) {
      // console.log('row: ', row)
      // this.showViewDialog = true
      // this.planId = row.id
      this.rowIds = row.id
      this.showHistoryPlane = true
      this.isPlanCode = 10
      if (row.emergencyTypeId && !Array.isArray(row.emergencyTypeId)) {
        row.emergencyTypeId = row.emergencyTypeId.split(',')
      }
      if (row.status === '未发布') {
        row.status = '0'
      } else if (row.status === '待审核') {
        row.status = '1'
      } else if (row.status === '通过') {
        row.status = '2'
      } else if (row.status === '退回') {
        row.status = '3'
      }
      this.formPlanInfo = row
      console.log('查看', row)
    }
  }
}
</script>
<style scoped>
</style>
