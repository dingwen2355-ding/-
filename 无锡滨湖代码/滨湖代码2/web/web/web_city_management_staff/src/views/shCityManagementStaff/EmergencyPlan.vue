<template>
  <div class="EmergencyPlan">
    <div class="table-dec-warp">
      <div class="add-div" style="display: flex">
        <div class="border-btn" @click="showAddRiskDialog">
          <i class="el-icon-plus"></i> 新增
        </div>
        <!-- <div style="display: flex; margin-left: 50px" v-if="!precinctId">
          <el-select clearable v-model="value" placeholder="请选择区域">
            <el-option
              v-for="item in optionsw"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </div> -->
        <div style="display: flex; margin-left: 50px">
          <el-select clearable v-model="search" placeholder="请选择预案类型">
            <el-option
              v-for="item in options1"
              :key="item.typeId"
              :label="item.typeName"
              :value="item.typeId"
            >
            </el-option>
          </el-select>
        </div>
      </div>
    </div>
    <div class="table-container">
      <el-table
        ref="multipleTable"
        height="1340"
        :data="showData"
        :row-class-name="tableRowClassName"
        style="width: 100%"
      >
        <el-table-column
          prop="emergencyPlanName"
          label="预案名称"
        ></el-table-column>
        <el-table-column
          prop="planTypeName"
          label="预案类型"
        ></el-table-column>
        <el-table-column
          prop="planLevel"
          label="预案等级"
        ></el-table-column>
        <el-table-column
          prop="precinctName"
          label="所属区域"
        ></el-table-column>
        <el-table-column label="操作" width="200">
          <template slot-scope="scope">
            <div class="link-btn">
              <i
                class="el-icon-delete-solid"
                style="color: red"
                @click="deleteClick(scope.$index, scope.row)"
              ></i>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="pagination-container">
      <div class="div-item r">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-size="pageSize"
          layout="total, prev, pager, next, jumper"
          :total="total"
        ></el-pagination>
      </div>
    </div>
    <div class="submit-btn-warp">
      <div class="link-btn btn-item">取消</div>
      <div class="blue-btn btn-item" @click="submitLedTableData">提交</div>
    </div>
    <k-dialog
      dialog-title="新增应急预案"
      :dialog-visible="dialogVisible"
      @closeDialog="closeDialog"
    >
      <template>
        <div class="AddRiskDialog">
          <div class="form-container">
            <el-form ref="form" :model="form" label-width="260px">
              <el-form-item label="应急预案">
                <el-select
                  v-model="form.emergencyPlanId"
                  placeholder="请选择"
                  filterable
                >
                  <el-option
                    :disabled="planIdList.includes(item.emergencyPlanId)"
                    v-for="item in emergencyPlanOptions"
                    :key="item.emergencyPlanId"
                    :label="item.emergencyPlanName"
                    :value="item.emergencyPlanId"
                  ></el-option>
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="onSubmit">立即提交</el-button>
                <el-button @click="dialogVisible = false">取消</el-button>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </template>
    </k-dialog>
  </div>
</template>

<script>
import KDialog from '../../components/KDialog'
import Axios from '@/utils/request'
export default {
  name: 'EmergencyPlan',
  components: { KDialog },
  data() {
    return {
      tableData: [],
      currentPage: 1,
      pageSize: 6,
      total: 0,
      dialogVisible: false,
      form: {
        emergencyPlanId: ''
      },
      search: '',
      value: '',
      optionsw: [],
      precinctId: this.$store.getters.precinctId,
      options1: [],
      emergencyPlanOptions: [],
      planIdList: []
    }
  },
  watch: {
    '$store.getters.eventId': {
      handler(v) {
        this.$nextTick(() => {
          this.getTableData(v.id)
        })
      },
      immediate: true
    },
    value: {
      handler(v) {
        this.getEmergencyPlanOptions()
        // this.getTableData(this.$store.getters.eventId.id)
      }
    },
    search: {
      handler(v) {
        this.getEmergencyPlanOptions()
        // this.getTableData(this.$store.getters.eventId.id)
      }
    },
    tableData: {
      handler(v) {
        this.total = v.length
        this.planIdList = v.map((e) => e.emergencyPlanId)
      }
    }
  },
  computed: {
    showData() {
      return this.tableData.slice(
        (this.currentPage - 1) * this.pageSize,
        this.currentPage * this.pageSize
      )
    }
  },
  mounted() {
    this.getEmergencyPlanOptions()
    this.getEmergencyPlanOptions1()
    let obj = {
      id: '510100',
      name: '无锡市'
    }
    this.optionsw = JSON.parse(JSON.stringify(sysConfig.area))
    this.optionsw.unshift(obj)
  },
  methods: {
    leftSearch() {
      console.log(this.search)
    },
    getEmergencyPlanOptions() {
      let url = '/apis/emergencyPlan/queryEmergencyNew'
      if (this.value) {
        url += url.includes('?') ? '&precinctId=' + this.value : '?precinctId=' + this.value
      }
      if (this.search) {
        url += url.includes('?') ? '&typeId=' + this.search : '?typeId=' + this.search
      }
      Axios.get(url).then((res) => {
        let data = res.data.data
        this.emergencyPlanOptions = data || []
      })
    },
    getEmergencyPlanOptions1() {
      let url = '/apis/emergencyPlan/queryPlanTypeList'
      Axios.get(url).then((res) => {
        let data = res.data.data
        this.options1 = data || []
      })
    },
    getTableData(eventId) {
      if (!eventId) return
      this.ledTableData = []
      let url = '/apis/emergencyPlan/queryEmergencyNew?eventId=' + eventId
      if (this.value) {
        url += '&precinctId=' + this.value
      }
      if (this.search) {
        url += '&typeName=' + this.search
      }
      Axios.get(url).then((res) => {
        let data = res.data.data
        this.tableData = data || []
        this.total = this.tableData.length
      })
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 === 0) {
        return 'even-row'
      } else if (rowIndex % 2 === 1) {
        return 'odd-row'
      }
      return ''
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`)
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.showData = this.tableData.slice(
        (this.currentPage - 1) * this.pageSize,
        this.currentPage * this.pageSize
      )
      console.log(`当前页: ${val}`)
    },
    deleteClick(index) {
      this.tableData.splice(index, 1)
    },
    showAddRiskDialog() {
      this.form.emergencyPlanId = ''
      this.dialogVisible = true
    },
    submitLedTableData() {
      let url =
        '/apis/manage/updateEvent?eventId=' +
        this.$store.getters.eventId.id +
        '&emergencyPlanIds=' +
        this.planIdList.join(',')
      Axios.post(url).then((res) => {
        let data = res.data
        if (data.code === 200) {
          this.$message({
            message: '更新成功！',
            type: 'success'
          })
          this.$root.eventBus.$emit('refreshIframe', {})
        } else {
          this.$message({
            message: '更新失败！',
            type: 'warning'
          })
        }
      })
    },
    onSubmit() {
      let tempPlan = this.emergencyPlanOptions.filter((e) => {
        return e.emergencyPlanId === this.form.emergencyPlanId
      })
      if (tempPlan[0]) {
        this.tableData.push(tempPlan[0])
      }
      this.dialogVisible = false
    },
    closeDialog: function (v) {
      this.dialogVisible = false
    }
  }
}
</script>

<style lang="scss">
.EmergencyPlan {
  position: relative;
  width: 100%;
  padding: 76px 62px 80px;
  .el-button {
    font-size: 40px;
    line-height: 80px;
    padding: 0 40px;
  }

  .table-dec-warp {
    line-height: 80px;
    margin-bottom: 40px;

    .ctrl-list {
      float: right;
      position: relative;

      .ctrl-btn {
        display: inline-block;
        width: 291px;
        height: 80px;
        text-align: center;
        background: rgba(255, 255, 255, 1);
        border: 2px solid;
        border-radius: 8px;
        margin: 0 20px;
        line-height: 76px;
        cursor: pointer;

        i {
          font-size: 40px;
        }

        &:last-child {
          color: #ff5858;
          border-color: #ff5858;
        }

        &:first-child {
          color: #0c99db;
          border-color: #0c99db;
        }
      }
    }

    .add-div {
      position: relative;
      margin-right: 800px;

      .el-select {
        width: 738px;
      }
    }
  }

  .table-container {
    position: relative;
    /*height: 1680px;*/
  }
  .pagination-container {
    height: 80px;
    line-height: 80px;
    margin-bottom: 40px;

    .l {
      float: left;
    }
    .r {
      float: right;
    }
  }

  .submit-btn-warp {
    position: relative;
    text-align: center;

    .btn-item {
      margin: 0 40px;
    }
  }
  .AddRiskDialog {
    position: relative;
    width: 100%;
    height: 100%;
    .el-select {
      width: 100%;
    }
    .form-container {
      padding: 80px;
    }

    .list {
      line-height: 80px;
      font-size: 40px;
      float: left;
    }
  }
}
</style>
