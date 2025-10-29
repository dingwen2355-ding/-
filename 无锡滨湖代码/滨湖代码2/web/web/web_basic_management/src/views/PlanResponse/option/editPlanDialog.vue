<template>
  <el-dialog
    :title="code == 0 ? '编辑' : code === 1 ? '查看' : '新增'"
    :visible="dialogVisibleFlag"
    width="1400px"
    custom-class="editPlanDialog"
    :modal-append-to-body="false"
    :append-to-body="false"
    @open="onOpened"
    :before-close="handleClose"
  >
    <div class="info_body" v-loading="loading">
      <div class="title-bar">
        <div class="bar-icon"></div>
        <span class="bar-text">{{ formInfo.eventTitle }}</span>
      </div>
      <el-form
        v-show="planId === null"
        :inline="true"
        ref="formLabel"
        :rules="rules"
        :model="form"
        label-width="110px"
        :disabled="code == 1"
      >
        <el-form-item label="预案名称" prop="name">
          <el-input clearable v-model="form.name" placeholder="请输入预案名称"></el-input>
        </el-form-item>
        <el-form-item label="应对灾害" prop="emergencyTypeId">
          <el-cascader v-model="form.emergencyTypeId" :options="typeTree" :props="{}" clearable></el-cascader>
        </el-form-item>
        <el-form-item label="响应级别" prop="levelId">
          <el-select v-model="form.levelId" style="width: 220px" placeholder="请选择响应级别" clearable>
            <el-option v-for="(item, key) in levelTypes" :value="item.value" :label="item.label" :key="key"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item style="width: 100%; display: flex; flex-direction: row-reverse; padding-right: 55px">
          <el-button type="primary" size="mini" @click="onSearch">查询</el-button>
          <el-button size="mini" @click="onChoose()">确认选择</el-button>
        </el-form-item>
      </el-form>
      <div class="dis-table">
        <el-table
          ref="planTable"
          :data="allPlans"
          tooltip-effect="dark"
          style="width: 100%"
          @selection-change="handleSelectionChange"
        >
          <el-table-column v-if="code != 1" type="selection" width="55"> </el-table-column>
          <el-table-column label="序号" width="120">
            <template slot-scope="scope">
              <span>{{ scope.row.index + (topPage.currentPage - 1) * topPage.pageSize + 1 }} </span>
            </template>
          </el-table-column>
          <el-table-column prop="regionName" label="属地" width="120"> </el-table-column>
          <el-table-column prop="name" label="预案名称" width="120"> </el-table-column>
          <el-table-column prop="unitTypeName" label="发布单位分类" width="120"> </el-table-column>
          <el-table-column prop="unitName" label="发布单位名称" width="120"> </el-table-column>
          <el-table-column prop="eventTypeName" label="预案分类" width="120"> </el-table-column>
          <el-table-column prop="emergencyTypeName" label="应对灾种" width="120"> </el-table-column>
          <el-table-column prop="releaseTime" label="发布时间" width="120"> </el-table-column>
        </el-table>
        <el-pagination
          background
          :current-page="topPage.currentPage"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :page-size="topPage.pageSize"
          :page-sizes="[5, 10, 20, 30, 40]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="topPage.total"
        >
        </el-pagination>
      </div>
      <div class="dis-tab">
        <el-tabs v-model="editableTabsValue" type="card">
          <el-tab-pane label="指挥体系" name="1">
            <organizational-module
              :isCode="code"
              ref="organizationalModule"
              :planId="formInfo.eventPlanId"
            ></organizational-module>
          </el-tab-pane>
          <el-tab-pane label="应急响应" name="2">
            <emergency-response :isCode="code" ref="emergencyResponse" :viewData="viewData"></emergency-response>
          </el-tab-pane>
          <el-tab-pane label="应急保障" name="3">
            <emergency-support
              :isCode="code"
              :suport-position="position"
              ref="emergencySupport"
              :formInfo="formInfo"
              :planId="planId"
              :viewData="viewData"
            ></emergency-support>
          </el-tab-pane>
          <el-tab-pane label="终止条件" name="5">
            <termination-conditions
              :isCode="code"
              ref="terminationConditions"
              :viewData="viewData"
            ></termination-conditions>
          </el-tab-pane>
          <el-tab-pane label="预案资料" name="6">
            <plan-materials
              :isCode="code"
              ref="planMaterials"
              :formInfo="formInfo"
              :viewData="viewData"
            ></plan-materials>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button @click="handleClose()">取 消</el-button>
      <el-button type="primary" v-show="planId != null" @click="handleOk">确 定</el-button>
    </div>
  </el-dialog>
</template>
<script>
import request from '@/utils/request'
import organizationalModule from '@/views/PlanResponse/option/module/organizationalModule'
import emergencyResponse from '@/views/PlanResponse/option/module/emergencyResponse'
import emergencySupport from '@/views/PlanResponse/option/module/emergencySupport'
import terminationConditions from '@/views/PlanResponse/option/module/terminationConditions'
import planMaterials from '@/views/PlanResponse/option/module/planMaterials'
export default {
  name: 'editPlanDialog',
  components: { organizationalModule, emergencyResponse, emergencySupport, terminationConditions, planMaterials },
  props: ['dialogVisible', 'isCode', 'formInfo'],
  watch: {
    dialogVisible(v) {
      this.dialogVisibleFlag = v
    }
  },
  computed: {
    code() {
      if (this.isCode === null) {
        return 0
      } else {
        return this.isCode
      }
    }
  },
  data() {
    return {
      editableTabsValue: '1',
      dialogVisibleFlag: false,
      loading: false,
      rules: {},
      position: 'left',
      form: {
        createId: '',
        createName: '',
        emergencyTypeId: '',
        eventId: '',
        eventTypeId: '',
        levelId: '',
        name: '',
        shared: '',
        status: []
      },
      forms: {},
      tableData: [],
      multipleSelection: [],
      topPage: {
        currentPage: 1, // 当前页数
        pageSize: 5, // 每页条数
        total: 0 // 总条数
      },
      allPlans: [],
      typeTree: [],
      levelTypes: [],
      choosedPlanId: '',
      // 用于界面显示的对象
      viewData: {
        planId: null,
        planResponseGroups: {
          // 应急响应
          levelId: '',
          remark: '',
          extendRemark: ''
        },
        planSupportGroups: {
          // 应急支持
          experts: [],
          shelters: [],
          stuffs: [],
          teams: []
        },
        planStopGroups: {
          // 终止条件
          remark: '',
          extendRemark: ''
        },
        planDataBeans: [] // 预案资料
      },
      // 后台传来的对象，传回后台的对象
      transData: {},
      planId: null
    }
  },
  mounted() {
    console.log('refs', this.$refs)
    this.getTypeTree()
    this.getLevelType()
  },
  methods: {
    onOpened() {
      this.loadData()
    },
    loadData() {
      this.queryPlanEvent(this.formInfo.id).then((res) => {
        if (res.data.code === 200) {
          if (res.data.data.list.length > 0) {
            this.allPlans = res.data.data.list || []
            this.allPlans = this.allPlans.map((t, i) => {
              t.index = i
              return t
            })
            this.planId = this.allPlans[0].id
            this.topPage.total = res.data.data.total

            // 查询分组和相关资料信息-事件相关
            this.queryGroupDataEvent().then(() => {
              this.$refs.emergencySupport.loadData()
            })
          } else {
            this.queryPlan()
          }
        }
      })
    },
    onSearch() {
      this.topPage.currentPage = 1
      this.queryPlan()
    },
    onChoose() {
      let eventId = this.formInfo.id
      let planId = this.multipleSelection.id
      this.formInfo.eventPlanId = planId
      let url = `/binhuapis/add-plan-event?eventId=${eventId}&planId=${planId}`
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          this.$message.success('选择预案成功')
          this.planId = planId
          this.loadData()
        }
      })
    },
    getTypeTree() {
      let url = '/binhuapis/get-type-tree'
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          this.typeTree = res.data.data || []
        }
      })
    },
    queryPlanEvent(eventId) {
      let url = '/binhuapis/query-plan-event'
      let params = {
        eventId: eventId
      }
      return request.post(url, params)
    },
    queryPlan(planId) {
      let url = '/binhuapis/query-plan?userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")
      let params = Object.assign(
        {},
        {
          pageNum: this.topPage.currentPage,
          pageSize: this.topPage.pageSize,
          name: this.form.name,
          emergencyTypeId: this.form.emergencyTypeId ? this.form.emergencyTypeId.join(',') : '',
          levelId: this.form.levelId
        }
      )
      if (planId) {
        params.planId = planId
      }
      return request.post(url, params).then((res) => {
        if (res.data.code === 200) {
          this.allPlans = res.data.data.list || []
          this.allPlans = this.allPlans.map((t, i) => {
            t.index = i
            return t
          })
          this.topPage.total = res.data.data.total
        }
      })
    },
    savePlanGroupEvent() {},
    changeType() {},
    changeStreet() {},
    toggleSelection(rows) {
      if (rows) {
        rows.forEach((row) => {
          this.$refs.multipleTable.toggleRowSelection(row)
        })
      } else {
        this.$refs.multipleTable.clearSelection()
      }
    },
    handleSelectionChange(val) {
      if (val.length > 1) {
        this.$refs.planTable.clearSelection()
        this.$refs.planTable.toggleRowSelection(val.pop())
      } else {
        this.multipleSelection = val.pop()
      }

      if (this.multipleSelection) {
        // 更新指挥体系内容
        this.$root.eventBus.$emit('updateEventPlanLeaderAfter', this.multipleSelection.id)
        this.queryGroupData(this.multipleSelection.id)
      }
    },
    handleClose() {
      if (this.code !== 1) {
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
          .finally(() => {
            this.$parent.queryDataTable()
          })
      } else {
        this.$emit('update:dialogVisible', false)
      }
    },
    reset() {
      // 用于界面显示的对象
      this.viewData = Object.assign(this.viewData, {
        planId: null,
        planResponseGroups: {
          // 应急响应
          levelId: '',
          remark: '',
          extendRemark: ''
        },
        planSupportGroups: {
          // 应急支持
          experts: [],
          shelters: [],
          stuffs: [],
          teams: []
        },
        planStopGroups: {
          // 终止条件
          remark: '',
          extendRemark: ''
        },
        planDataBeans: [] // 预案资料
      })
      this.transData = {}
      this.planId = null
      this.$refs.emergencySupport.clearAllTable()
      this.$refs.formLabel.clearValidate()
    },
    handleOk() {
      this.updatePlanGroupEvent()
    },
    submitAll() {
      this.loading = true
    },
    handleSizeChange(val) {
      this.topPage.pageSize = val
    },
    handleCurrentChange(val) {
      this.topPage.currentPage = val
      this.queryPlan()
    },
    getLevelType() {
      let url = '/binhuapis/get-level-type'
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          this.levelTypes = res.data.data
        }
      })
    },
    // 查询分组和相关资料信息-事件相关
    queryGroupDataEvent() {
      let eventId = this.formInfo.eventPlanId
      let url = `/binhuapis/query-group-data-event?id=${eventId}`
      return request.get(url).then((res) => {
        if (res.data.code === 200) {
          this.transData = res.data.data
          this.translateDataRequest(this.transData)
        }
      })
    },
    // 查询分组和相关资料信息
    queryGroupData(planId) {
      let url = `/binhuapis/query-group-data?id=${planId}`
      return request.get(url).then((res) => {
        if (res.data.code === 200) {
          this.transData = res.data.data
          this.translateDataRequest(this.transData)
        }
      })
    },
    // 更新我的预案分组-事件相关
    updatePlanGroupEvent() {
      const result = this.$refs.organizationalModule.getOrganizationInfo()
      console.log(result.params)
      const updateOrg = request.post(result.url, result.params)
      this.translateDataResponse(this.viewData)
      if (!this.transData.planId) {
        this.transData.planId = result.params.planId
      }
      debugger
      let url = '/binhuapis/update-plan-group-event'
      const updategroup = request({
        method: 'post',
        url: url,
        data: JSON.stringify(this.transData),
        headers: {
          'Content-Type': 'application/json;charset=UTF-8'
        }
      })
      Promise.all([updateOrg, updategroup]).then(([orgRes, groupRes]) => {
        if (groupRes.data.code === 200 && orgRes.data.code === 200) {
          this.reset()
          this.$emit('update:dialogVisible', false)
        }
      })
    },
    translateDataRequest(transData) {
      this.viewData = {
        planId: transData.planId,
        planDataBeans: transData.planDataBeans,
        planResponseGroups: transData.planResponseGroups[0] ? transData.planResponseGroups[0] : {},
        planStopGroups: transData.planStopGroups[0] ? transData.planStopGroups[0] : {},
        planSupportGroups: transData.planSupportGroups
      }
    },
    translateDataResponse(viewData) {
      this.transData = {
        planId: viewData.planId,
        planDataBeans: viewData.planDataBeans,
        planResponseGroups: Object.keys(viewData.planResponseGroups).length === 0 ? [] : [viewData.planResponseGroups],
        planStopGroups: Object.keys(viewData.planStopGroups).length === 0 ? [] : [viewData.planStopGroups],
        planSupportGroups: viewData.planSupportGroups
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.editPlanDialog {
  .info_body {
    .title-bar {
      width: 100%;
      display: flex;
      align-items: center;
      padding-left: 42px;
      height: 72px;
      .bar-icon {
        width: 40px;
        height: 40px;
        border: none;
        background-image: url('../../../assets/common/多页标签@2x.png');
      }
      .bar-text {
        margin-left: 16px;
        font-size: 16px;
        font-family: Microsoft YaHei;
        font-weight: bold;
        color: #0052d9;
      }
    }
    .dis-table {
      margin-left: 32px;
      margin-right: 32px;
    }
    .dis-tab {
      margin-top: 32px;
      margin-left: 32px;
      margin-right: 32px;
      :deep(.el-tabs) {
        .el-tabs__nav {
          border: none;
        }
        .el-tabs__item {
          border-top: none;
          border-right: none;
          border-left: none;
        }
        .el-tabs__item.is-active {
          background: #0052d9;
          border-radius: 5px 5px 0px 0px;
          border: none;

          font-size: 16px;
          font-family: Microsoft YaHei;
          font-weight: 600;
          color: #ffffff;
        }
      }
    }
  }
}
</style>
