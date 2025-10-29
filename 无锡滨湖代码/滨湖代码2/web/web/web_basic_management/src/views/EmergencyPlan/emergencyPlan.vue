<template>
  <div class="emergency-plan">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="待审核" name="second">
        <div style="height: 750px; overflow: scroll">
          <el-table
            v-loading="loading"
            class="tableInfo"
            border
            :data="tableNotAuditData"
            style="width: 100%"
          >
            <el-table-column type="index" label="编号" width="50"></el-table-column>
            <el-table-column prop="name" label="名称"></el-table-column>
            <el-table-column prop="emergencyTypeName" label="应对灾种"></el-table-column>
            <el-table-column prop="levelName" label="级别"></el-table-column>
            <el-table-column prop="eventTypeName" label="分类"></el-table-column>
            <el-table-column prop="status" label="发布状态"></el-table-column>
            <el-table-column prop="releaseTime" label="发布时间"></el-table-column>
            <el-table-column fixed="right" label="操作" width="180">
              <template slot-scope="scope">
                <el-button @click="handleView(scope.row)" type="text" size="medium">详情</el-button>
                <el-button @click="handleAudit(scope.row)" type="text" size="medium">审核</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <el-pagination
          background
          @size-change="handleSizeNotAuditChange"
          @current-change="handleCurrentNotAuditChange"
          :current-page="pageNotAudit"
          :page-size="pageSizeNotAudit"
          :page-sizes="[10, 20, 30, 40]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="totalNotAudit"
        ></el-pagination>
        <audit
          :dialogVisible.sync="showAudit"
          :formInfo="auditPlanId"
          @success="handleAuditSuccess"
          :isAuditBack="true"
        ></audit>
      </el-tab-pane>
      <el-tab-pane label="已审核" name="first">
        <section class="AlarmRecord">
          <el-row class="header">
            <el-col :span="44">
              <el-form :inline="true" :model="searchForm" ref="form">
                <el-form-item label="名称">
                  <el-input v-model="searchForm.planName" placeholder="请输入名称"></el-input>
                </el-form-item>
                <el-form-item label="应对灾种">
                  <el-cascader
                    filterable
                    v-model="searchForm.planType"
                    :options="options"
                    :props="props"
                    collapse-tags
                    placeholder="请选择应对灾种"
                    clearable
                    @change="handleChange"
                  ></el-cascader>
                </el-form-item>
                <el-form-item label="分类">
                  <el-select filterable clearable v-model="searchForm.planKind" placeholder="请选择分类">
                    <el-option
                      v-for="item in eventTypeList"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    ></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="级别">
                  <el-select
                    filterable
                    clearable
                    v-model="searchForm.storageLevel"
                    placeholder="请选择级别"
                  >
                    <el-option
                      v-for="item in manageLevelList"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    ></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="发布人">
                  <el-input v-model="searchForm.createName" placeholder="请输入发布人"></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="onSubmit">查询</el-button>
                </el-form-item>
              </el-form>
            </el-col>
          </el-row>
          <div style="height: 735px; overflow: scroll">
            <el-table
              v-loading="loading"
              class="tableInfo"
              border
              :data="tableAuditData"
              style="width: 100%"
            >
              <el-table-column type="index" label="编号" width="50"></el-table-column>
              <el-table-column prop="name" label="名称"></el-table-column>
              <el-table-column prop="emergencyTypeName" label="应对灾种"></el-table-column>
              <el-table-column prop="levelName" label="级别"></el-table-column>
              <el-table-column prop="eventTypeName" label="分类"></el-table-column>
              <el-table-column prop="createName" label="发布人"></el-table-column>
              <el-table-column prop="releaseTime" label="发布时间"></el-table-column>
              <el-table-column fixed="right" label="操作" width="180">
                <template slot-scope="scope">
                  <el-button @click="handleView(scope.row)" type="text" size="medium">详情</el-button>
                  <!-- <el-button
                    @click="handleShare(scope.row)"
                    v-if="scope.row.shared == 0 || scope.row.shared == null"
                    type="text"
                    size="medium"
                  >共享</el-button>
                  <el-button @click="handleShare(scope.row)" v-else type="text" size="medium">取消共享</el-button>
                  <el-button @click="handleDelete(scope.row)" type="text" size="medium">回退</el-button>-->
                </template>
              </el-table-column>
            </el-table>
          </div>
          <el-pagination
            background
            @size-change="handleSizeAuditChange"
            @current-change="handleCurrentAuditChange"
            :current-page="pageAudit"
            :page-size="pageSizeAudit"
            :page-sizes="[10, 20, 30, 40]"
            layout="total, sizes, prev, pager, next, jumper"
            :total="totalAudit"
          ></el-pagination>
          <audit
            :dialogVisible.sync="showAuditBack"
            :formInfo="auditPlanId"
            @success="handleAuditBackSuccess"
            :isAuditBack="false"
          ></audit>
        </section>
      </el-tab-pane>
      <plan-view-dialog :dialogVisible.sync="showViewDialog" :planId="planId" @cancel="afterCancel"></plan-view-dialog>
    </el-tabs>
  </div>
</template>
<script>
import Axios from '@/utils/request'
// import auditPlanViewDialog from './emergencyPlanOption/auditPlanViewDialog.vue'
import planViewDialog from './emergencyPlanOption/planViewDialog.vue'
import Audit from './emergencyPlanOption/audit.vue'
import znvTime from '../../utils/znvTime'
export default {
  components: { planViewDialog, Audit },
  name: 'emergencyPlan',
  data() {
    return {
      activeName: 'second',
      manageLevelList: [], // 管理级别
      eventTypeList: [],
      options: [],
      props: { multiple: true, emitPath: false },
      searchForm: {
        planName: null,
        planType: null,
        planKind: null,
        storageLevel: null,
        sharePeople: null
      },
      loading: false,
      tableAuditData: [],
      pageAudit: 1,
      pageSizeAudit: 10,
      totalAudit: 0,
      tableNotAuditData: [],
      pageNotAudit: 1,
      pageSizeNotAudit: 10,
      totalNotAudit: 0,
      showAudit: false,
      auditPlanId: '',
      showAuditBack: false,
      showViewDialog: false,
      planId: ''
    }
  },
  mounted() {
    this.getManageLevel() // 管理级别
    this.queryPlanType() // 类型获取
    this.queryEventType()
    this.queryNotAuditTableList()
    // this.queryAuditTableList()
  },
  methods: {
    handleClick(tab, event) {
      // console.log(tab, event)
      if (tab.name == 'first') {
        this.queryAuditTableList()
      }
      if (tab.name == 'second') {
        this.queryNotAuditTableList()
      }
    },
    handleChange(value) {
      // console.log(value)
    },
    // 管理级别
    getManageLevel() {
      let url = '/binhuapis/getLevel'
      Axios.get(url).then((r) => {
        this.manageLevelList = r.data.data
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
    // 类型获取
    queryPlanType() {
      let url = '/binhuapis/get-type-tree'
      Axios.get(url).then((r) => {
        // console.log('类型信息：', r.data.data)
        this.options = r.data.data
      })
    },
    // 分页管理
    handleCurrentAuditChange(val) {
      // console.log('当前页', val)
      this.pageAudit = val
      this.queryAuditTableList()
    },
    handleSizeAuditChange(val) {
      // console.log('一页展示', val, '条数据')
      this.pageSizeAudit = val
      this.queryAuditTableList()
    },
    queryAuditTableList() {
      this.loading = true
      let url = '/binhuapis/query-plan?userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")
      let params = {
        pageNum: this.pageAudit,
        pageSize: this.pageSizeAudit,
        userId: localStorage.getItem('userId'),
        status: ['2']
      }
      if (this.searchForm.planName) {
        params.name = this.searchForm.planName
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
      if (this.searchForm.storageLevel) {
        params.levelId = this.searchForm.storageLevel
      }
      if (this.searchForm.sharePeople) {
        params.createName = this.searchForm.sharePeople
      }
      if (this.searchForm.planKind) {
        params.eventTypeId = this.searchForm.planKind
      }
      Axios.post(url, params).then((res) => {
        this.tableAuditData = res.data.data.list
        this.totalAudit = res.data.data.total
        this.loading = false
      })
    },
    // 查询
    onSubmit() {
      this.pageAudit = 1
      this.queryAuditTableList()
    },
    // 详情
    handleView(row) {
      // console.log('row: ', row)
      this.showViewDialog = true
      this.planId = row.id
      // console.log('this.planId: ', this.planId)
      // this.formAuditInfo = row
      // if (row.emergencyTypeId && !Array.isArray(row.emergencyTypeId)) {
      //   row.emergencyTypeId = row.emergencyTypeId.split(',')
      // }
      // if (row.levelId) {
      //   row.levelId = Number(row.levelId)
      // }
      // this.showAuditAdd = true
    },
    afterCancel() {
      if (this.activeName == 'first') {
        // console.log('this.activeName: ', this.activeName)
        this.queryAuditTableList()
      }
      if (this.activeName == 'second') {
        // console.log('this.activeName: ', this.activeName)
        this.queryNotAuditTableList()
      }
    },
    // 共享
    handleShare(row) {
      // console.log('共享row: ', row)
      let url = '/binhuapis/share-plan'
      let params = {
        id: row.id,
        sharedTime: znvTime.getCurrentTimeByFormat('YYYY-MM-DD HH:mm:SS')
      }
      if (row.shared == 0) {
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
          this.queryAuditTableList()
        } else {
          if (row.shared == 0) {
            this.$message.error('共享失败')
          } else if (row.shared == 1) {
            this.$message.success('取消共享失败')
          }
        }
      })
    },
    // 退回
    handleDelete(row) {
      // console.log('删除row: ', row)
      this.showAuditBack = true
      this.auditPlanId = row.id
    },
    handleAuditBackSuccess() {
      this.pageAudit = 1
      this.queryAuditTableList()
    },
    // 分页管理
    handleCurrentNotAuditChange(val) {
      // console.log('当前页', val)
      this.pageNotAudit = val
      this.queryNotAuditTableList()
    },
    handleSizeNotAuditChange(val) {
      // console.log('一页展示', val, '条数据')
      this.pageSizeNotAudit = val
      this.queryNotAuditTableList()
    },
    queryNotAuditTableList() {
      this.loading = true
      let url = '/binhuapis/query-plan?userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")
      let params = {
        pageNum: this.pageNotAudit,
        pageSize: this.pageSizeNotAudit,
        userId: localStorage.getItem('userId'),
        status: ['1']
      }
      Axios.post(url, params).then((res) => {
        this.tableNotAuditData = res.data.data.list
        this.totalNotAudit = res.data.data.total
        this.loading = false
      })
    },
    // // 查看待审核详情
    // handleViewAudit(row) {
    //   console.log('row: ', row)
    //   this.showNotAuditAdd = true
    //   if (row.emergencyTypeId && !Array.isArray(row.emergencyTypeId)) {
    //     row.emergencyTypeId = row.emergencyTypeId.split(',')
    //   }
    //   if (row.levelId) {
    //     row.levelId = Number(row.levelId)
    //   }
    //   this.formNotAuditInfo = row
    // },
    // 审核按钮
    handleAudit(row) {
      // console.log('审核', row)
      this.showAudit = true
      this.auditPlanId = row.id
    },
    handleAuditSuccess() {
      this.pageNotAudit = 1
      this.queryNotAuditTableList()
    }
  }
}
</script>
<style scoped>
</style>
