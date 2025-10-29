<template>
  <div class="LedIndicatorModule">
    <div class="tab-container">
      <div class="tab-item" :class="{'active': currentTab === '0'}" @click="currentTab = '0'">指示/要求</div>
      <!-- <div class="tab-item" :class="{'active': currentTab === '1'}" @click="currentTab = '1'">落实情况</div> -->
    </div>
    <div class="tab-content" v-show="currentTab === '0'">
      <div class="table-dec-warp">
        <div class="ctrl-list">
          <div class="ctrl-btn" @click="ledCtrlBtnClick('0')"><i class="iconfont iconzhgl_xianshi"></i> 批量显示</div>
          <div class="ctrl-btn" @click="ledCtrlBtnClick('1')"><i class="iconfont iconzhgl_yincang"></i> 批量隐藏</div>
        </div>
        <div class="add-div">
          <div class="border-btn" @click="showAddLedDialog"><i class="el-icon-plus"></i> 新增</div>
        </div>
      </div>
      <div class="table-container">
        <el-table
          ref="ledMultipleTable"
          height="1340"
          :data="ledTableData.slice((ledCurrentPage - 1) * pageSize, ledCurrentPage * pageSize)"
          @selection-change="ledTableSelectionChange"
          :row-class-name="tableRowClassName"
          style="width: 100%">
          <el-table-column
            type="selection"
            width="160">
          </el-table-column>
          <el-table-column
            type="index"
            label="序号"
            width="280" :index="indexMethods">
          </el-table-column>
          <el-table-column
            label="领导批示"
            width="620">
            <template slot-scope="scope">
              <k-edit-text v-model="scope.row.leaderName"></k-edit-text>
            </template>
          </el-table-column>
          <el-table-column
            label="批示时间"
            width="620">
            <template slot-scope="scope">
              <el-date-picker
                v-model="scope.row.recordtime"
                format="yyyy-MM-dd HH:mm:ss"
                value-format="yyyy-MM-dd HH:mm:ss"
                type="datetime"
                placeholder="选择日期时间">
              </el-date-picker>
            </template>
          </el-table-column>
          <el-table-column
            label="批示内容">
            <template slot-scope="scope">
              <k-edit-text v-model="scope.row.instructionContent"></k-edit-text>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            width="200">
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
            <el-checkbox :true-label="'0'" v-model="ledCheckbox">全选</el-checkbox>
            <el-checkbox :true-label="'1'" v-model="ledCheckbox">反选</el-checkbox>
          </el-radio-group>
        </div>
        <div class="div-item r">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="ledCurrentPage"
            :page-size="pageSize"
            layout="total, prev, pager, next, jumper"
            :total="total1">
          </el-pagination>
        </div>
      </div>
      <div class="submit-btn-warp">
        <div class="link-btn btn-item">取消</div>
        <div class="blue-btn btn-item" @click="submitLedTableData">提交</div>
      </div>
    </div>
    <div class="tab-content" v-show="currentTab === '1'">
      <div class="table-dec-warp">
        <div class="ctrl-list">
          <div class="ctrl-btn" @click="implCtrlBtnClick('0')"><i class="iconfont iconzhgl_xianshi"></i> 批量显示</div>
          <div class="ctrl-btn" @click="implCtrlBtnClick('1')"><i class="iconfont iconzhgl_yincang"></i> 批量隐藏</div>
        </div>
        <div class="add-div">
          <div class="border-btn" @click="showAddImplementationDialog"><i class="el-icon-plus"></i> 新增</div>
        </div>
      </div>
      <div class="table-container">
        <el-table
          ref="implMultipleTable"
          height="1340"
          :data="implementationTableData.slice((implementationCurrentPage - 1) * pageSize, implementationCurrentPage * pageSize)"
          :row-class-name="tableRowClassName"
          @selection-change="implTableSelectionChange"
          style="width: 100%">
          <el-table-column
            type="selection"
            width="160">
          </el-table-column>
          <el-table-column
            type="index"
            label="序号"
            width="280">
          </el-table-column>
          <el-table-column
            label="上报委办局"
            width="620">
            <template slot-scope="scope">
              <k-edit-text v-model="scope.row.unitName"></k-edit-text>
            </template>
          </el-table-column>
          <el-table-column
            label="上报时间"
            width="620">
            <template slot-scope="scope">
              <el-date-picker
                v-model="scope.row.implementTime"
                format="yyyy-MM-dd HH:mm:ss"
                value-format="yyyy-MM-dd HH:mm:ss"
                type="datetime"
                placeholder="选择日期时间">
              </el-date-picker>
            </template>
          </el-table-column>
          <el-table-column
            label="落实情况">
            <template slot-scope="scope">
              <k-edit-text v-model="scope.row.reportContent"></k-edit-text>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            width="300">
            <template slot-scope="scope">
              <div class="link-btn" :class="{'red': scope.row.status !== '0'}" @click="tableHandleClick(scope.row)">
                <i class="iconfont iconzhgl_xianshi" v-if="scope.row.status === '0'"></i>
                <i class="iconfont iconzhgl_yincang" v-else></i>
              </div>
              <div class="link-btn red" @click="deleteClick(scope.row)"><i class="el-icon-delete"></i></div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="pagination-container">
        <div class="div-item l">
          <el-radio-group>
            <el-checkbox :true-label="'0'" v-model="implementationCheckbox">全选</el-checkbox>
            <el-checkbox :true-label="'1'" v-model="implementationCheckbox">反选</el-checkbox>
          </el-radio-group>
        </div>
        <div class="div-item r">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="implementationCurrentPage"
            :page-size="pageSize"
            layout="total, prev, pager, next, jumper"
            :total="total2">
          </el-pagination>
        </div>
      </div>
      <div class="submit-btn-warp">
        <div class="link-btn btn-item">取消</div>
        <div class="blue-btn btn-item" @click="submitImplementationTableData">提交</div>
      </div>
    </div>
  </div>
</template>

<script>
import KEditText from '../../components/KEditText'
import Axios from '@/utils/request'
export default {
  name: 'LedIndicatorModule',
  components: { KEditText },
  data() {
    return {
      currentTab: '0', // 0-领导批示 1-落实情况
      eventMonitorPublic: '',
      eventMonitorPublicList: [],
      ledTableData: [],
      ledTableDataSecond: [],
      implementationTableData: [],
      ledCheckbox: '',
      implementationCheckbox: '',
      ledCurrentPage: 1,
      implementationCurrentPage: 1,
      ledSelectionData: [],
      implSelectionData: [],
      total1: 0,
      total2: 0,
      pageSize: 6
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
    'ledCheckbox': {
      handler (v) {
        this.$nextTick(() => {
          if (v === '0') {
            this.$refs.ledMultipleTable.clearSelection()
            this.$refs.ledMultipleTable.toggleAllSelection()
          } else if (v === '1') {
            this.ledTableData.forEach(row => {
              this.$refs.ledMultipleTable.toggleRowSelection(row)
            })
          } else {
            this.$refs.ledMultipleTable.clearSelection()
          }
        })
      },
      immediate: true
    },
    'implementationCheckbox': {
      handler (v) {
        this.$nextTick(() => {
          if (v === '0') {
            this.$refs.implMultipleTable.clearSelection()
            this.$refs.implMultipleTable.toggleAllSelection()
          } else if (v === '1') {
            this.implementationTableData.forEach(row => {
              this.$refs.implMultipleTable.toggleRowSelection(row)
            })
          } else {
            this.$refs.implMultipleTable.clearSelection()
          }
        })
      },
      immediate: true
    }
  },
  mounted() {
    this.$root.eventBus.$on('closeAddLedOrImplementationDialog', (data) => {
      if (data) this.getTableData(this.$store.getters.eventId.id)
    })
    this.$root.eventBus.$on('showLeaders', (data) => {
      if (data) {
        console.log(data)
        this.ledTableData.push(data)
        this.ledTableDataSecond.push(data)
        this.total1 = this.ledTableData.length
      }
    })
  },
  methods: {
    indexMethods(index) {
      return (this.ledCurrentPage - 1) * this.pageSize + index + 1
    },
    getTableData(eventId) {
      if (!eventId) return
      this.ledTableData = []
      let url = '/apis/leaderInstruction/instructionContent?eventId=' + eventId
      Axios.get(url).then((res) => {
        let data = res.data.data
        this.ledTableData = data.leaderInstruction || []
        this.total1 = this.ledTableData.length
        this.implementationTableData = data.implemention || []
        this.total2 = this.implementationTableData.length
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
      if (this.currentTab === '0') {
        this.ledCurrentPage = val
      }
      if (this.currentTab === '1') {
        this.implementationCurrentPage = val
      }
      console.log(`当前页: ${val}`)
    },
    tableHandleClick(row) {
      row.status = row.status === '0' ? '1' : '0'
    },
    deleteClick(row) {
      let url = '/apis/leaderInstruction/deleteInstructionImplement?id=' + row.id
      Axios.post(url).then(res => {
        // eslint-disable-next-line eqeqeq
        if (res.data.code == 200) {
          this.$message('删除成功')
          this.getTableData(this.$store.getters.eventId.id)
        }
      })
    },
    ledCtrlBtnClick(status) {
      this.ledSelectionData.forEach(row => {
        row.status = status
      })
    },
    ledTableSelectionChange(selection) {
      this.ledSelectionData = selection
    },
    implCtrlBtnClick(status) {
      this.implSelectionData.forEach(row => {
        row.status = status
      })
    },
    implTableSelectionChange(selection) {
      this.implSelectionData = selection
    },
    showAddLedDialog() {
      this.$root.eventBus.$emit('showAddLedDialog', {})
    },
    showAddImplementationDialog() {
      this.$root.eventBus.$emit('showAddImplementationDialog', {})
    },
    submitLedTableData() {
      if (!this.$store.getters.eventId.id) return
      let list = []
      for (let i = 0; i < this.ledTableData.length; i++) {
        let data = this.ledTableData[i]
        let item = {
          instructionContent: data.instructionContent,
          recordtime: data.recordtime,
          eventId: this.$store.getters.eventId.id,
          leaderName: data.leaderName,
          status: data.status
        }
        list.push(item)
      }
      let listStr = JSON.stringify(list)
      let postParam = {
        eventId: this.$store.getters.eventId.id,
        lists: listStr,
        isAdd: 1
      }
      let url = '/apis/leaderInstruction/updateLeaderInstructionNew'
      Axios.post(url, postParam).then((res) => {
        let data = res.data
        // eslint-disable-next-line eqeqeq
        if (data.code == 200) {
          this.$message({
            message: '更新成功！',
            type: 'success'
          })
          this.ledTableDataSecond = []
          this.$root.eventBus.$emit('refreshIframe', {})
          this.getTableData(this.$store.getters.eventId.id)
        } else {
          this.$message({
            message: '更新失败！',
            type: 'warning'
          })
        }
      })
    },
    submitImplementationTableData() {
      if (!this.$store.getters.eventId.id) return
      let list = []
      for (let i = 0; i < this.implementationTableData.length; i++) {
        let data = this.implementationTableData[i]
        let item = {
          reportContent: data.reportContent,
          recordtime: data.implementTime,
          eventId: this.$store.getters.eventId.id,
          unitName: data.unitName,
          status: data.status
        }
        list.push(item)
      }
      let listStr = JSON.stringify(list)
      let postParam = {
        eventId: this.$store.getters.eventId.id,
        lists: listStr,
        isAdd: 1
      }
      let url = '/apis/leaderInstruction/updateLeaderInstructionImplementNew'
      Axios.post(url, postParam).then((res) => {
        let data = res.data
        // eslint-disable-next-line eqeqeq
        if (data.code == 200) {
          this.$message({
            message: '更新成功！',
            type: 'success'
          })
          this.getTableData(this.$store.getters.eventId.id)
          this.$root.eventBus.$emit('refreshIframe', {})
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

<style lang="scss" scoped>
  .LedIndicatorModule {
    position: relative;
    width: 100%;

    .tab-container {
      position: relative;
      margin-top: 76px;
      line-height: 80px;
      margin-bottom: 40px;
      margin-left: 62px;

      .tab-item {
        display: inline-block;
        width: 234px;
        height: 80px;
        border-radius: 4px;
        text-align: center;
        cursor: pointer;

        &.active {
          color: #fff;
          background: rgba(12, 153, 219, 1);
        }
      }
    }

    .tab-content {
      margin-bottom: 80px;
      margin-left: 62px;
      margin-right: 62px;

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
  }
</style>
