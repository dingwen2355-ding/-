<template>
  <section class="AlarmRecord">
    <el-row class="header">
      <el-col :span="22">
        <el-form :inline="true" :model="form" ref="form">
          <el-form-item label="预案名称">
            <el-input v-model="form.planName"></el-input>
          </el-form-item>
          <el-form-item label="预案类别">
            <el-select v-model="form.planTypeName" placeholder="请选择" clearable>
              <el-option
                v-for="item in planType"
                :key="item.label"
                :label="item.label"
                :value="item.label"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="预案级别">
            <el-select v-model="form.planLevelName" placeholder="请选择" clearable>
              <el-option
                v-for="item in planLevel"
                :key="item.label"
                :label="item.label"
                :value="item.label"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="适用领域">
            <el-select v-model="form.planAreaName" placeholder="请选择" clearable>
              <el-option
                v-for="item in planArea"
                :key="item.label"
                :label="item.label"
                :value="item.label"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="预案事故类别">
            <el-select v-model="form.alarmTypeName" placeholder="请选择" clearable>
              <el-option
                v-for="item in alarmType"
                :key="item.label"
                :label="item.label"
                :value="item.label"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">查询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="addPlan">新增</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="danger" @click="delPlan">删除</el-button>
          </el-form-item>
          <el-button type="primary" @click="reset('form')">重置</el-button>
        </el-form>
      </el-col>
    </el-row>
    <el-table
      v-loading="loading"
      :data="tableData"
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column label="选择" type="selection"></el-table-column>
      <!-- <el-table-column label="..." type="index"></el-table-column> -->
      <el-table-column prop="name" label="预案名称"></el-table-column>
      <el-table-column prop="type" label="预案类别"></el-table-column>
      <el-table-column prop="level" label="预案级别"></el-table-column>
      <el-table-column prop="applicable_field" label="适用领域"></el-table-column>
      <el-table-column prop="plan_accident_type" label="预案事故类别"></el-table-column>
      <el-table-column prop="release_department" label="编辑单位(部门)"></el-table-column>
      <el-table-column prop="release_time" label="发布日期"></el-table-column>
      <el-table-column fixed="right" label="操作" width="180">
        <template slot-scope="scope">
          <el-button @click="handleView(scope.row)" type="text" size="medium">查看</el-button>
          <el-button @click="handleEdit(scope.row)" type="text" size="medium">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- <div class="data-title">共<span class="blue">{{total}}</span>条数据 | 最近更新时间：{{updateTime}}</div> -->
    <el-pagination
      @current-change="handleCurrentChange"
      :current-page.sync="page"
      :page-size.sync="pageSize"
      layout="prev, pager, next, jumper"
      :total="total"
    ></el-pagination>

    <GovernmentPlanAdd
      :dialogVisible.sync="showAdd"
      :infomation="eventInfo"
      :planType="planType"
      :planLevel="planLevel"
      :planArea="planArea"
      :alarmType="alarmType"
      @success="addCom"
    />
    <GovernmentPlanEdit
      :dialogVisible.sync="showReset"
      :infomation="eventInfo"
      :planType="planType"
      :planLevel="planLevel"
      :planArea="planArea"
      :alarmType="alarmType"
      @success="editCom"
    />
    <GovernmentPlanView
      :dialogVisible.sync="showView"
      :infomation="eventInfo"
      :planType="planType"
      :planLevel="planLevel"
      :planArea="planArea"
      :alarmType="alarmType"
      @success="editCom"
    />
  </section>
</template>

<script>
// 事故预警记录查询
import GovernmentPlanAdd from './GovernmentPlanAdd'
import GovernmentPlanEdit from './GovernmentPlanEdit'
import GovernmentPlanView from './GovernmentPlanView'
import axios from '@/utils/request'
export default {
  name: "GovernmentPlan",
  components: {
    GovernmentPlanAdd,
    GovernmentPlanEdit,
    GovernmentPlanView
  },
  data() {
    return {
      showReset: false,
      loading: false,
      showAdd: false,
      showView: false,
      selectData: '',
      startTime: '',
      endTime: '',
      page: 1,
      pageSize: 10,
      total: 0,
      form: {
        planName: null,
        planTypeName: null,
        planLevelName: null,
        planAreaName: null,

        alarmTypeName: null
      },
      tableData: [],
      alarmType: [{
        value: '选项1',
        label: '危险品事故'
      }, {
        value: '选项2',
        label: '民用航空事故'
      }, {
        value: '选项3',
        label: '建筑工程事故'
      }, {
        value: '选项4',
        label: '爆炸物事故'
      }, {
        value: '选项5',
        label: '交通事故'
      }, {
        value: '选项6',
        label: '重大火灾事故'
      }, {
        value: '选项7',
        label: '海上搜救'
      }, {
        value: '选项8',
        label: '其它事故'
      }, {
        value: '选项9',
        label: '重污染天气'
      }, {
        value: '选项10',
        label: '渔业生产事故'
      }, {
        value: '选项11',
        label: '燃气/热力事故'
      }, {
        value: '选项12',
        label: '特种设备事故'
      }, {
        value: '选项13',
        label: '海上溢油事件'
      }, {
        value: '选项14',
        label: '大面积停电事件'
      }, {
        value: '选项15',
        label: '石油天然气储运事件'
      }, {
        value: '选项16',
        label: '突发环境事件'
      }],
      planArea: [{
        value: '选项1',
        label: '自然灾害'
      }, {
        value: '选项2',
        label: '事故灾害'
      }, {
        value: '选项3',
        label: '公共卫生事故'
      }, {
        value: '选项4',
        label: '社会安全事故'
      }],
      planLevel: [{
        value: '选项1',
        label: '政府预案级别'
      }, {
        value: '选项2',
        label: '企业预案级别'
      }],
      planType: [{
        value: '1',
        label: '综合预案'
      }, {
        value: '2',
        label: '专项预案'
      }, {
        value: '3',
        label: '现场预案'
      }, {
        value: '4',
        label: '卡式预案'
      }],
      updateTime: '',
      datetimerange: [],
      eventInfo: ''

    }
  },
  mounted() {
    this.getTableData()
  },
  watch: {
    tableData() {
      if (this.tableData.length === 0 && this.total !== 0) {
        this.page -= 1
        this.getTableData()
      }
    }
  },
  methods: {
    onSubmit() {
      this.getTableData()
    },
    addPlan(e) {
      this.showAdd = true
    },
    delPlan() {
      this.$confirm('此操作将删除选中文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let arr = ''
        this.selectData.forEach(v => {
          arr += v['id']
          arr += ','
        })
        arr = arr.slice(0, arr.length - 1)
        let url = '/binhuapis/deleteManyGPlan' + '?id=' + arr
        axios.get(url).then(r => {
          this.$message.success('删除成功')
          this.getTableData()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    addCom() {
      this.getTableData()
      this.showAdd = false
    },
    editCom() {
      this.getTableData()
      this.showReset = false
    },
    handleCurrentChange(val) {
      this.form.page = val
      this.getTableData()
    },
    getTableData() {
      this.loading = true
      let url = '/binhuapis/selectGPlanByDesignated'
      axios.get(url, {
        params: {
          name: this.form.planName,
          type: this.form.planTypeName,
          level: this.form.planLevelName,
          applicableField: this.form.planAreaName,
          planAccidentType: this.form.alarmTypeName,
          pageStart: this.page,
          pageLen: this.pageSize
        }
      }).then(r => {
        this.tableData = r.data.data.data
        this.total = r.data.data.count
        this.updateTime = this.timeChange(new Date())
        this.loading = false
      })
    },
    handleView(e) {
      // console.log(e)
      this.showView = true
      this.eventInfo = e
    },
    handleEdit(e) {
      this.showReset = true
      this.eventInfo = e
    },
    handleSelectionChange(e) {
      this.selectData = e
    },
    timeChange(t) {
      let d = new Date(t)
      let theMonth = d.getMonth() + 1
      let theDate = d.getDate()
      let theHours = d.getHours()
      let theMinutes = d.getMinutes()
      let theSeconds = d.getSeconds()
      if (theMonth < 10) {
        theMonth = '0' + theMonth
      }
      if (theDate < 10) {
        theDate = '0' + theDate
      }
      if (theHours < 10) {
        theHours = '0' + theHours
      }
      if (theMinutes < 10) {
        theMinutes = '0' + theMinutes
      }
      if (theSeconds < 10) {
        theSeconds = '0' + theSeconds
      }
      let date = d.getFullYear() + '-' + theMonth + '-' + theDate + ' '
      let time = theHours + ':' + theMinutes + ':' + theSeconds
      return date + time
    },
    reset(formName) {
      for (let key in this.form) {
        this.form[key] = null
      }
      // this.$refs.formDetail.resetFields()
      this.$refs[formName].resetFields()
    }
  }
}
</script>

<style lang="scss">
</style>
