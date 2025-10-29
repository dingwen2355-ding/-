<template>
  <section class="AlarmRecord">
    <el-row class="header">
      <el-col :span="44">
        <el-form :inline="true" :model="searchForm" ref="form">
          <el-form-item label="名称">
            <el-input v-model="searchForm.planName" placeholder="请输入名称"></el-input>
          </el-form-item>
          <el-form-item label="类型">
            <el-cascader
              filterable
              v-model="searchForm.planType"
              :options="options"
              :props="props"
              placeholder="请选择类型"
              @change="handleChange"
              clearable
            ></el-cascader>
          </el-form-item>
          <!-- <el-form-item label="分类">
            <el-select filterable clearable v-model="searchForm.planKind" placeholder="请选择分类">
              <el-option
                v-for="item in eventTypeList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>-->
          <el-form-item label="级别">
            <el-select filterable clearable v-model="searchForm.storageLevel" placeholder="请选择级别">
              <el-option
                v-for="item in manageLevelList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">查询</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <div style="height: 725px; overflow: scroll">
      <el-table v-loading="loading" class="tableInfo" border :data="tableData" style="width: 100%">
        <el-table-column type="index" label="编号" width="50"></el-table-column>
        <el-table-column prop="name" label="名称"></el-table-column>
        <el-table-column prop="emergencyTypeName" label="类型"></el-table-column>
        <el-table-column prop="levelName" label="级别"></el-table-column>
        <el-table-column prop="eventTypeName" label="分类"></el-table-column>
        <el-table-column prop="status" label="发布状态"></el-table-column>
        <el-table-column prop="releaseTime" label="发布时间"></el-table-column>
        <el-table-column prop="auditOpinion" label="审核意见"></el-table-column>
        <el-table-column fixed="right" label="操作" width="180">
          <template slot-scope="scope">
            <!--            <el-button @click="handleView(scope.row)" type="text" size="medium">预案指挥体系</el-button>-->
            <el-button @click="handleView(scope.row)" type="text" size="medium">预案拆解</el-button>
            <!-- <el-button @click="handleRelease(scope.row, 0)" type="text" size="medium">发布</el-button> -->
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
      :page-sizes="[10, 20, 30, 40]"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    ></el-pagination>
    <!--    <release-plan-view-dialog :dialogVisible.sync="showView" :formInfo="formInfo"></release-plan-view-dialog>-->
    <plan-view-dialog :dialogVisible.sync="showViewDialog" :planId="planId"></plan-view-dialog>
  </section>
</template>
<script>
import Axios from '@/utils/request'
import releasePlanViewDialog from '../components/release/releasePlanViewDialog.vue'
import znvTime from '../../../utils/znvTime'
import planViewDialog from "@/views/EmergencyPlan/emergencyPlanOption/planViewDialog.vue";

export default {
  components: { planViewDialog },
  name: 'releasePlan',
  data() {
    return {
      manageLevelList: [], // 管理级别
      // eventTypeList: [],
      options: [], // 类型获取
      props: {},
      showViewDialog: false,
      planId: '',
      searchForm: {
        planName: null,
        planType: null,
        // planKind: null,
        storageLevel: null,
        sharePeople: null
      },
      loading: false,
      tableData: [],
      page: 1,
      pageSize: 10,
      total: 0,
      showView: false,
      formInfo: {
        id: '',
        name: '',
        emergencyTypeId: '',
        levelId: '',
        eventTypeId: '',
        status: '',
        releaseTime: '',
        auditOption: ''
      }
    }
  },
  mounted() {
    // this.getManageLevel() // 管理级别
    this.queryPlanType()
    this.getManageLevel()
    this.queryTableList()
  },
  methods: {
    // 管理级别
    getManageLevel() {
      let url = '/binhuapis/getLevel'
      Axios.get(url).then((r) => {
        this.manageLevelList = r.data.data
      })
    },
    handleChange(value) {
      console.log(value)
    },
    // 类型获取
    queryPlanType() {
      let url = '/binhuapis/get-type-tree'
      Axios.get(url).then((r) => {
        console.log('类型信息：', r.data.data)
        this.options = r.data.data
      })
    },
    // // 分类获取
    // queryEventType() {
    //   let url = '/binhuapis/get-event-type'
    //   Axios.get(url).then((r) => {
    //     console.log('分类获取', r.data.data)
    //     this.eventTypeList = r.data.data
    //   })
    // },
    queryTableList() {
      this.loading = true
      let url = '/binhuapis/query-plan?userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")
      let params = {
        pageNum: this.page,
        pageSize: this.pageSize,
        status: ['1', '2']
      }
      if (this.searchForm.planName) {
        params.name = this.searchForm.planName
      }
      if (this.searchForm.planType) {
        params.emergencyTypeId = this.searchForm.planType.toString()
      }
      if (this.searchForm.storageLevel) {
        params.levelId = this.searchForm.storageLevel
      }
      Axios.post(url, params).then((res) => {
        this.tableData = res.data.data.list
        this.total = res.data.data.total
        this.loading = false
      })
    },
    // 查询
    onSubmit() {
      this.page = 1
      this.queryTableList()
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
    // 详情
    handleView(row) {
      this.showViewDialog = true
      this.planId = row.id
      // this.showView = true
      // if (row.emergencyTypeId && !Array.isArray(row.emergencyTypeId)) {
      //   row.emergencyTypeId = row.emergencyTypeId.split(',')
      // }
      // if (row.levelId) {
      //   row.levelId = Number(row.levelId)
      // }
      // this.formInfo = row
    },
    // 共享
    handleShare(row) {
      console.log('共享row: ', row)
      let url = '/binhuapis/share-plan'
      let params = {
        id: row.id,
        sharedTime: znvTime.getCurrentTimeByFormat('YYYY-MM-DD HH:mm:SS')
      }
      if (row.shared == 0 || row.shared == null) {
        params.shared = '1'
        params.sharerId = localStorage.getItem('userId')
      } else if (row.shared == 1) {
        params.shared = '0'
        params.sharerId = localStorage.getItem('userId')
      }
      Axios.post(url, params).then((res) => {
        if (res.data.code === 200) {
          if (row.shared == 0) {
            this.$message.success('共享成功')
          } else if (row.shared == 1) {
            this.$message.success('取消共享成功')
          }
          this.page = 1
          this.queryTableList()
        } else {
          if (row.shared == 0) {
            this.$message.error('共享失败')
          } else if (row.shared == 1) {
            this.$message.success('取消共享失败')
          }
        }
      })
    }
    // 发布
    // handleRelease(row, code) {
    //   console.log('row: ', row)
    //   console.log('row: ', code)
    // }
  }
}
</script>
<style scoped>
</style>
