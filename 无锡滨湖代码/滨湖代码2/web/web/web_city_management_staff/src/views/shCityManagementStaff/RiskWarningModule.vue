<template>
  <div class="RiskWarningModule">
    <div class="table-dec-warp">
      <div class="ctrl-list">
        <div class="ctrl-btn" @click="ctrlBtnClick('0')"><i class="iconfont iconzhgl_xianshi"></i> 批量显示</div>
        <div class="ctrl-btn" @click="ctrlBtnClick('1')"><i class="iconfont iconzhgl_yincang"></i> 批量隐藏</div>
      </div>
      <div class="add-div">
        <div class="border-btn" @click="showAddRiskDialog"><i class="el-icon-plus"></i> 新增</div>
      </div>
    </div>
    <div class="table-container">
      <el-table ref="multipleTable" height="1340" :data="showData" :row-class-name="tableRowClassName"
        @selection-change="tableSelectionChange" style="width: 100%">
        <el-table-column type="selection" width="160">
        </el-table-column>
        <el-table-column type="index" label="序号" width="280">
        </el-table-column>
        <el-table-column label="风险类型" width="620">
          <template slot-scope="scope">
            <k-edit-text v-model="scope.row.riskType"></k-edit-text>
          </template>
        </el-table-column>
        <el-table-column label="风险发现时间" width="620">
          <template slot-scope="scope">
            <el-date-picker v-model="scope.row.foundTime" format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss" type="datetime" placeholder="选择日期时间">
            </el-date-picker>
          </template>
        </el-table-column>
        <el-table-column label="风险预警">
          <template slot-scope="scope">
            <k-edit-text v-model="scope.row.riskInsight"></k-edit-text>
          </template>
        </el-table-column>
        <el-table-column label="处置建议">
          <template slot-scope="scope">
            <k-edit-text v-model="scope.row.dealSuggest"></k-edit-text>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template slot-scope="scope">
            <div class="link-btn" :class="{'red': scope.row.status !== '0'}" @click="tableHandleClick(scope.row)">
              <i class="iconfont iconzhgl_xianshi" v-if="scope.row.status === '0'"></i>
              <i class="iconfont iconzhgl_yincang" v-else></i>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="pagination-container">
      <div class="div-item l">
        <el-radio-group>
          <el-checkbox :true-label="'0'" v-model="checkbox">全选</el-checkbox>
          <el-checkbox :true-label="'1'" v-model="checkbox">反选</el-checkbox>
        </el-radio-group>
      </div>
      <div class="div-item r">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
          :page-size="pageSize" layout="total, prev, pager, next, jumper" :total="total">
        </el-pagination>
      </div>
    </div>
    <div class="submit-btn-warp">
      <div class="link-btn btn-item">取消</div>
      <div class="blue-btn btn-item" @click="submitLedTableData">提交</div>
    </div>
  </div>
</template>

<script>
import Axios from '@/utils/request'
import KEditText from '../../components/KEditText'
export default {
  name: 'RiskWarningModule',
  components: { KEditText },
  data () {
    return {
      eventMonitorPublic: '',
      eventMonitorPublicList: [],
      tableData: [],
      showData: [],
      selectionData: [],
      implementationTableData: [],
      checkbox: '',
      currentPage: 1,
      pageSize: 6,
      total: 0
    }
  },
  watch: {
    '$store.getters.eventId': {
      handler (v) {
        this.$nextTick(() => {
          this.getTableData(v.id)
        })
      },
      immediate: true
    },
    'checkbox': {
      handler (v) {
        this.$nextTick(() => {
          if (v === '0') {
            this.$refs.multipleTable.clearSelection()
            this.$refs.multipleTable.toggleAllSelection()
          } else if (v === '1') {
            this.tableData.forEach(row => {
              this.$refs.multipleTable.toggleRowSelection(row)
            })
          } else {
            this.$refs.multipleTable.clearSelection()
          }
        })
      },
      immediate: true
    }
  },
  mounted () {
    this.$root.eventBus.$on('closeAddRiskDialog', (data) => {
      if (data) this.getTableData(this.$store.getters.eventId.id)
    })
  },
  methods: {
    getTableData (eventId) {
      if (!eventId) return
      this.ledTableData = []
      let url = '/apis/risk/queryInfo?eventId=' + eventId
      Axios.get(url).then((res) => {
        let data = res.data.data
        this.tableData = data || []
        this.showData = this.tableData.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
        this.total = this.tableData.length
      })
    },
    tableRowClassName ({ row, rowIndex }) {
      if (rowIndex % 2 === 0) {
        return 'even-row'
      } else if (rowIndex % 2 === 1) {
        return 'odd-row'
      }
      return ''
    },
    handleSizeChange (val) {
      console.log(`每页 ${val} 条`)
    },
    handleCurrentChange (val) {
      this.currentPage = val
      this.showData = this.tableData.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
      console.log(`当前页: ${val}`)
    },
    showAddRiskDialog () {
      this.$root.eventBus.$emit('showAddRiskDialog', {})
    },
    tableHandleClick (row) {
      row.status = row.status === '0' ? '1' : '0'
    },
    ctrlBtnClick (status) {
      this.selectionData.forEach(row => {
        row.status = status
      })
    },
    tableSelectionChange (selection) {
      this.selectionData = selection
    },
    submitLedTableData () {
      if (!this.$store.getters.eventId.id) return
      let list = []
      for (let i = 0; i < this.tableData.length; i++) {
        let data = this.tableData[i]
        let item = {
          deal_suggest: data.dealSuggest,
          risk_insight: data.riskInsight,
          event_id: this.$store.getters.eventId.id,
          found_time: data.foundTime,
          risk_type: data.riskType,
          status: data.status
        }
        list.push(item)
      }
      let listStr = JSON.stringify(list)
      let data = {
        eventId: this.$store.getters.eventId.id,
        lists: listStr,
        isAdd: 1
      }
      let url = '/apis/risk/updateRiskInsightInfo'
      Axios.post(url, data).then((res) => {
        let data = res.data
        // eslint-disable-next-line eqeqeq
        if (data.code == 200) {
          this.$message({
            message: '更新成功！',
            type: 'success'
          })
          this.$root.eventBus.$emit('refreshIframe', {})
          this.getTableData(this.$store.getters.eventId.id)
        } else {
          this.$message({
            message: '更新失败！',
            type: 'warning'
          })
        }
      })
    }
  }
}
</script>

<style lang="scss">
.RiskWarningModule {
  position: relative;
  width: 100%;
  padding: 76px 62px 80px;

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
}
</style>
